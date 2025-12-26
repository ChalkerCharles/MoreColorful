package com.ChalkerCharles.morecolorful.util;

import com.google.common.base.Splitter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public final class StringParser {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    private static final Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);

    public static boolean isBlock(String block) {
        if (block == null) return false;
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.parse(block));
    }

    public static Block getBlock(String block) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(block));
    }

    public static BlockEntry parseBlockEntry(String s) {
        String s1 = s.replaceAll(" ", "");
        if (!s1.contains("=")
                || StringUtils.countMatches(s1, '[') > 1
                || StringUtils.countMatches(s1, ']') > 1)
            return null;
        boolean flag = s1.contains("[") && s1.contains("]");
        try {
            String blockWithStates = s1.substring(0, s1.lastIndexOf('='));
            String block = flag ? blockWithStates.substring(0, blockWithStates.indexOf('[')) : blockWithStates;
            String states = flag ? blockWithStates.substring(block.length() + 1, blockWithStates.indexOf(']')) : null;
            String value = s1.substring(blockWithStates.length() + 1);
            return new BlockEntry(block, states, value);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public record BlockEntry(String block, @Nullable String states, String value) {
        public int parseValue() {
            return Integer.parseInt(this.value);
        }

        public static boolean validate(BlockEntry entry) {
            return entry != null && isBlock(entry.block) && isValidState(entry) && StringUtils.isNumeric(entry.value);
        }

        public static boolean validate(Object o) {
            return o instanceof String s && validate(parseBlockEntry(s));
        }

        private static boolean isValidState(BlockEntry entry) {
            if (entry.states == null) return true;
            Map<Property<?>, Comparable<?>> map = getProperties(entry);
            StateDefinition<Block, BlockState> definition = getBlock(entry.block).getStateDefinition();
            return definition.getPossibleStates().stream().anyMatch(stateMatches(map));
        }

        private static Predicate<BlockState> stateMatches(Map<Property<?>, Comparable<?>> map) {
            return state -> {
                if (map == null || map.isEmpty()) return false;
                for (Map.Entry<Property<?>, Comparable<?>> e : map.entrySet()) {
                    if (!Objects.equals(state.getValue(e.getKey()), e.getValue())) {
                        return false;
                    }
                }
                return true;
            };
        }

        private static Map<Property<?>, Comparable<?>> getProperties(BlockEntry entry) {
            if (entry.states == null) return null;
            Map<Property<?>, Comparable<?>> map = new HashMap<>();
            Block block = getBlock(entry.block);
            StateDefinition<Block, BlockState> definition = block.getStateDefinition();
            for (String s : COMMA_SPLITTER.split(entry.states)) {
                Iterator<String> iterator = EQUAL_SPLITTER.split(s).iterator();
                if (iterator.hasNext()) {
                    String s1 = iterator.next();
                    Property<?> property = definition.getProperty(s1);
                    if (property != null && iterator.hasNext()) {
                        String s2 = iterator.next();
                        Comparable<?> comparable = property.getValue(s2).orElse(null);
                        if (comparable == null) {
                            return null;
                        }
                        map.put(property, comparable);
                    } else if (!s1.isEmpty()) {
                        return null;
                    }
                }
            }
            return map;
        }

        public static List<BlockState> getStates(BlockEntry entry) {
            Block block = getBlock(entry.block);
            List<BlockState> allStates = block.getStateDefinition().getPossibleStates();
            if (entry.states == null) return allStates;
            Map<Property<?>, Comparable<?>> map = getProperties(entry);
            return allStates.stream().filter(stateMatches(map)).toList();
        }
    }
}
