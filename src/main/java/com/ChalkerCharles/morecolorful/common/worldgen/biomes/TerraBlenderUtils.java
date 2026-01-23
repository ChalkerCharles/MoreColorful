package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld.ModOverworldRegion;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class TerraBlenderUtils {
    public static void registerRegions() {
        Regions.register(new ModOverworldRegion(Config.overworldRegionWeight));
    }

    public static void registerSurfaceRules() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MoreColorful.MODID, ModSurfaceRuleData.overworld());
    }
}
