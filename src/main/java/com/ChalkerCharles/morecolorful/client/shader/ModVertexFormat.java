package com.ChalkerCharles.morecolorful.client.shader;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.Lazy;

@OnlyIn(Dist.CLIENT)
public class ModVertexFormat {
    public static final Lazy<VertexFormatElement> WAVE = register(VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 4);

    public static final Lazy<VertexFormat> WAVY_BLOCK = Lazy.of(() -> VertexFormat.builder()
            .add("Position", VertexFormatElement.POSITION)
            .add("Color", VertexFormatElement.COLOR)
            .add("UV0", VertexFormatElement.UV0)
            .add("UV2", VertexFormatElement.UV2)
            .add("Normal", VertexFormatElement.NORMAL)
            .add("Wave", WAVE.get())
            .padding(1)
            .build());

    public static Lazy<VertexFormatElement> register(VertexFormatElement.Type type, VertexFormatElement.Usage usage, int count) {
        return Lazy.of(() -> {
            int i = VertexFormatElement.ELEMENTS.size();
            while (VertexFormatElement.byId(i) != null) {
                if (++i >= VertexFormatElement.MAX_COUNT) {
                    throw new IndexOutOfBoundsException("Too many mods registering VertexFormatElements!");
                }
            }
            int index = (int) VertexFormatElement.ELEMENTS.stream().filter(e -> e.usage().equals(usage)).count();
            return VertexFormatElement.register(i, index, type, usage, count);
        });
    }
}
