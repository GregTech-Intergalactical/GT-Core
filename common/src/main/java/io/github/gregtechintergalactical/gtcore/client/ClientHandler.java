package io.github.gregtechintergalactical.gtcore.client;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.data.GTCoreBlocks;
import muramasa.antimatter.client.ModelUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ClientHandler {
    public static void init() {
        ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_SAPLING, RenderType.cutout());
        ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_LEAVES, RenderType.cutout());
        ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_TRAPDOOR, RenderType.cutout());
        ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_DOOR, RenderType.cutout());
        ModelUtils.setRenderLayer(GTCoreBlocks.SAP_BAG, RenderType.cutout());
        Sheets.SIGN_MATERIALS.put(GTCoreBlocks.RUBBER_WOOD_TYPE, createSignMaterial(GTCoreBlocks.RUBBER_WOOD_TYPE));
    }

    private static Material createSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, new ResourceLocation(GTCore.ID, "entity/signs/" + woodType.name()));
    }
}
