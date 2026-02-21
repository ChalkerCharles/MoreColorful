package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.util.Colour;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.*;

@Mixin(DyeColor.class)
@Implements(@Interface(iface = Colour.class, prefix = "morecolorful$"))
public abstract class DyeColorMixin {
    @Shadow
    public abstract String getName();
    @Shadow
    public abstract String getSerializedName();

    @Intrinsic(displace = true)
    public String morecolorful$getName() {
        return this.getName();
    }

    @Intrinsic(displace = true)
    public String morecolorful$getSerializedName() {
        return this.getSerializedName();
    }
}
