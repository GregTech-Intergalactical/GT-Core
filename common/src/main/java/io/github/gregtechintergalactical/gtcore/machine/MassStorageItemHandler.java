package io.github.gregtechintergalactical.gtcore.machine;


import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMassStorage;
import io.github.gregtechintergalactical.gtcore.data.SlotTypes;
import muramasa.antimatter.capability.item.FakeTrackedItemHandler;
import muramasa.antimatter.capability.item.SidedCombinedInvWrapper;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.gui.SlotType;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tesseract.api.item.ExtendedItemContainer;

import java.util.Optional;

public class MassStorageItemHandler extends MachineItemHandler<BlockEntityMassStorage> {

    public MassStorageItemHandler(BlockEntityMassStorage tile) {
        super(tile);
        inventories.put(SlotType.DISPLAY, new FakeTrackedItemHandler<>(tile, SlotType.DISPLAY, 1, SlotType.DISPLAY.output, SlotType.DISPLAY.input, SlotType.DISPLAY.tester));
        inventories.put(SlotTypes.UNLIMITED, new InfiniteSlotTrackedHandler<>(tile, SlotTypes.UNLIMITED, 1, SlotTypes.UNLIMITED.output, SlotTypes.UNLIMITED.input, SlotTypes.UNLIMITED.tester, ((MassStorageMachine)tile.getMachineType()).getCapacity()));
    }

    /*public void drawInfo(MatrixStack stack, FontRenderer renderer, int left, int top) {
        // TODO: Replace by new TranslationTextComponent()
        renderer.draw(stack,"Item amount: " + digitalCount, left + 10, top + 19, 16448255);
    }*/

    @Override
    public boolean allowsInput(Direction side) {
        return super.allowsInput(side) && (side != Direction.DOWN || !tile.isOutput());
    }
}
