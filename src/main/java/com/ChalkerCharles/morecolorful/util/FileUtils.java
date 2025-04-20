package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.MoreColorful;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class FileUtils {
    private static final String packMCMETA = """
            {
                "pack": {
                    "description": {
                        "translate": "morecolorful.datapack.morecolorful_override.description"
                    },
                    "pack_format": 48
                }
            }
            """;
    private static final String emptyModifier = """
            {
                "type": "neoforge:none"
            }
            """;
    private static final Path savesFolder = Path.of("saves");
    private static final Path serverProperties = Path.of("server.properties");

    public static void generateDatapack(Path dir, List<String> modifiers, Consumer<Path> consumer) {
        String datapackRoot = dir + "/morecolorful_override";
        String biomeModifierDir = datapackRoot + "/data/morecolorful/neoforge/biome_modifier";
        Path packDir = Path.of(biomeModifierDir);
        Path packFile = Path.of(datapackRoot + "/pack.mcmeta");
        try {
            if (Files.notExists(packDir)) {
                Files.createDirectories(packDir);
            }
            Files.write(packFile, packMCMETA.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            MoreColorful.LOGGER.warn("Failed to create directories or pack.mcmeta file. Probable cause: {}", String.valueOf(e));
        }
        for (String s : modifiers) {
            Path file = Path.of(biomeModifierDir + "/" + s + ".json");
            consumer.accept(file);
        }
    }

    private static void generateDatapackForEachLevel(List<String> modifiers, Consumer<Path> consumer) {
        if (Files.exists(serverProperties)) {
            generateDatapackForServer(modifiers, consumer);
        } else {
            try (Stream<Path> saves = Files.list(savesFolder)) {
                saves.filter(Files::isDirectory)
                        .filter(path -> {
                            try {
                                return Files.isSameFile(path.getParent(), savesFolder);
                            } catch (IOException e) {
                                MoreColorful.LOGGER.warn("Failed to find saves folder. Probable cause: {}", String.valueOf(e));
                            }
                            return false;
                        })
                        .map(path -> path.getFileName().toString())
                        .forEach(level -> {
                            Path dir = Path.of(savesFolder + "/" + level + "/datapacks");
                            generateDatapack(dir, modifiers, consumer);
                        });
            } catch (IOException e) {
                MoreColorful.LOGGER.warn("Failed to find saves folder. Probable cause: {}", String.valueOf(e));
            }
        }
    }

    private static void generateDatapackForServer(List<String> modifiers, Consumer<Path> consumer) {
        Path dir = Path.of(getServerWorldName() + "/datapacks");
        generateDatapack(dir, modifiers, consumer);
    }

    private static String getServerWorldName() {
        Optional<String> levelName = Optional.empty();
        try {
            levelName = Files.readAllLines(serverProperties).stream().filter(s -> s.startsWith("level-name=")).findFirst();
        } catch (IOException e) {
            MoreColorful.LOGGER.warn("Failed to find \"level-name\" in server.properties. Probable cause: {}", String.valueOf(e));
        }
        return levelName.map(s -> s.substring("level-name=".length())).orElse("world");
    }

    public static Consumer<Path> writeFiles() {
        return path -> {
            try {
                Files.write(path, emptyModifier.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                MoreColorful.LOGGER.warn("Failed to write in empty biome modifier files. Probable cause: {}", String.valueOf(e));
            }
        };
    }

    public static void disableBiomeModifiers(List<String> modifiers) {
        generateDatapackForEachLevel(modifiers, writeFiles());
    }

    public static void enableBiomeModifiers(List<String> modifiers) {
        generateDatapackForEachLevel(modifiers, path -> {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                MoreColorful.LOGGER.warn("Failed to delete empty biome modifier files. Probable cause: {}", String.valueOf(e));
            }
        });
    }
}
