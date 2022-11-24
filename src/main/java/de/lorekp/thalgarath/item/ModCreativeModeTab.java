package de.lorekp.thalgarath.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab THALGARATH_TAB = new CreativeModeTab("thalgarathtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EMPTYVIAL.get());
        }
    };

}
