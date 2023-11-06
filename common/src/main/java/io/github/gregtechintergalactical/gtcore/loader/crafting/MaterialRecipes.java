package io.github.gregtechintergalactical.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.GTCoreConfig;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.datagen.builder.AntimatterCookingRecipeBuilder;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.material.MaterialType;
import muramasa.antimatter.material.MaterialTypeItem;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.material.MaterialTags.*;

public class MaterialRecipes {

    public static void loadMaterialRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider){
        final CriterionTriggerInstance in = provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag());
        int craftingMultiplier = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? 1 : 2;
        DUST.all().forEach(m -> {
            provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_small_dust", "antimatter_materials", DUST_SMALL.get(m, 4),
                    of('D', DUST.getMaterialTag(m)), " D");
            provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_tiny_dust", "antimatter_materials", DUST_TINY.get(m, 9),
                    of('D', DUST.getMaterialTag(m)), "D ");
        });
        AntimatterMaterialTypes.ROD.all().forEach(m -> {
            if (m.has(AntimatterMaterialTypes.INGOT)) {
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rod", "antimatter_material", AntimatterMaterialTypes.ROD.get(m, craftingMultiplier), of('F', AntimatterDefaultTools.FILE.getTag(), 'I', AntimatterMaterialTypes.INGOT.getMaterialTag(m)), "F", "I");
            }
            if (m.has(AntimatterMaterialTypes.BOLT)) {
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_bolt", "antimatter_material", AntimatterMaterialTypes.BOLT.get(m, 2 * craftingMultiplier), of('F', SAW.getTag(), 'I', AntimatterMaterialTypes.ROD.getMaterialTag(m)), "F ", " I");
                if (m.has(AntimatterMaterialTypes.SCREW)) {
                    String[] pattern = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? new String[]{"FI", "I "} : new String[]{"F", "I"};
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_screw", "antimatter_material", AntimatterMaterialTypes.SCREW.get(m, 1), of('F', AntimatterDefaultTools.FILE.getTag(), 'I', AntimatterMaterialTypes.BOLT.getMaterialTag(m)), pattern);
                }
            }
            if (m.has(AntimatterMaterialTypes.RING)) {
                if (!m.has(NOSMASH)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_ring", "antimatter_material",
                            AntimatterMaterialTypes.RING.get(m, craftingMultiplier), ImmutableMap.of('H', AntimatterDefaultTools.HAMMER.getTag(), 'W', AntimatterMaterialTypes.ROD.getMaterialTag(m)), "H ", " W");
                }
            }
            if (m.has(ROD_LONG)){
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rod_from_long_rod", "rods", ROD.get(m, 2),
                        ImmutableMap.of('S', SAW.getTag(), 'R', ROD_LONG.getMaterialTag(m)), "SR");
                if (!m.has(NOSMASH)){
                    provider.addStackRecipe(consumer, GTCore.ID, "", "rods", ROD_LONG.get(m, 1),
                            ImmutableMap.of('S', HAMMER.getTag(), 'R', ROD.getMaterialTag(m)), "RSR");
                }
            }
        });
        AntimatterMaterialTypes.ROTOR.all().forEach(m -> {
            provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rotors", "antimatter_material",
                    AntimatterMaterialTypes.ROTOR.get(m, 1), ImmutableMap.<Character, Object>builder()
                            .put('S', SCREWDRIVER.getTag())
                            .put('F', AntimatterDefaultTools.FILE.getTag())
                            .put('H', AntimatterDefaultTools.HAMMER.getTag())
                            .put('P', AntimatterMaterialTypes.PLATE.getMaterialTag(m))
                            .put('W', AntimatterMaterialTypes.SCREW.getMaterialTag(m))
                            .put('R', AntimatterMaterialTypes.RING.getMaterialTag(m))
                            .build(),
                    "PHP", "WRF", "PSP");
        });
        AntimatterMaterialTypes.PLATE.all().forEach(m -> {
            if (!m.has(NOSMASH)){
                if (m.has(AntimatterMaterialTypes.INGOT)){
                    Object[] array = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? new Object[]{AntimatterDefaultTools.HAMMER.getTag(), AntimatterMaterialTypes.INGOT.getMaterialTag(m), AntimatterMaterialTypes.INGOT.getMaterialTag(m)} : new Object[]{AntimatterDefaultTools.HAMMER.getTag(), AntimatterMaterialTypes.INGOT.getMaterialTag(m)};
                    provider.shapeless(consumer, GTCore.ID, "", "antimatter_material", AntimatterMaterialTypes.PLATE.get(m, 1), array);
                }
                if (m.has(AntimatterMaterialTypes.GEAR_SMALL)) {
                    provider.addStackRecipe(consumer, GTCore.ID, "", "antimatter_material",
                            AntimatterMaterialTypes.GEAR_SMALL.get(m, 1), ImmutableMap.of('H', AntimatterDefaultTools.HAMMER.getTag(),'P', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), "P ", " H");
                }
                if (m.has(AntimatterMaterialTypes.ITEM_CASING)) {
                    provider.addStackRecipe(consumer, GTCore.ID, "", "antimatter_material",
                            AntimatterMaterialTypes.ITEM_CASING.get(m, 1), ImmutableMap.of('H', AntimatterDefaultTools.HAMMER.getTag(),'P', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), "H P");
                }
                if (m.has(FOIL)){
                    provider.addStackRecipe(consumer, GTCore.ID, "", "antimatter_materials",
                            FOIL.get(m, 2), of('H', HAMMER.getTag(), 'P', PLATE.getMaterialTag(m)), "HP");
                    if (m.has(WIRE_FINE)){
                        provider.addItemRecipe(consumer, GTCore.ID, "", "antimatter_materials",
                                WIRE_FINE.get(m), of('F', FOIL.getMaterialTag(m), 'W', WIRE_CUTTER.getTag()), "FW");
                    }
                }
            }
            if (m.has(AntimatterMaterialTypes.GEAR)){
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_gear", "antimatter_material",
                        AntimatterMaterialTypes.GEAR.get(m, 1), ImmutableMap.<Character, Object>builder()
                                .put('W', AntimatterDefaultTools.WRENCH.getTag())
                                .put('P', AntimatterMaterialTypes.PLATE.getMaterialTag(m))
                                .put('R', AntimatterMaterialTypes.ROD.getMaterialTag(m))
                                .build(),
                        "RPR", "PWP", "RPR");
            }
            if (m.has(AntimatterMaterialTypes.RING)) {
                if (m.has(RUBBERTOOLS)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_ring", "antimatter_material",
                            AntimatterMaterialTypes.RING.get(m, craftingMultiplier), ImmutableMap.of('H', AntimatterDefaultTools.WIRE_CUTTER.getTag(), 'W', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), "H ", " W");
                }
            }
        });
    }

    public static void addSmeltingRecipe(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider, MaterialType<?> input, MaterialTypeItem<?> output, int amount, Material in){
        addSmeltingRecipe(consumer, provider, input, output, amount, in, in);
    }

    public static void addSmeltingRecipe(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider, MaterialType<?> input, MaterialTypeItem<?> output, int amount, Material in, Material out){
        AntimatterCookingRecipeBuilder.blastingRecipe(RecipeIngredient.of(input.getMaterialTag(in), 1), new ItemStack(output.get(out), MaterialTags.SMELTING_MULTI.getInt(in) * amount), 2.0F, 100)
                .addCriterion("has_material_" + in.getId(), provider.hasSafeItem(output.getMaterialTag(out)))
                .build(consumer, provider.fixLoc(Ref.ID, in.getId().concat("_" + input.getId() + "_to_" + output.getId())));
        AntimatterCookingRecipeBuilder.smeltingRecipe(RecipeIngredient.of(input.getMaterialTag(in), 1), new ItemStack(output.get(out), MaterialTags.SMELTING_MULTI.getInt(in) * amount), 2.0F, 200)
                .addCriterion("has_material_" + in.getId(), provider.hasSafeItem(output.getMaterialTag(out)))
                .build(consumer, provider.fixLoc(Ref.ID, in.getId().concat("_" + input.getId() + "_to_" + output.getId() + "_smelting")));
    }
}
