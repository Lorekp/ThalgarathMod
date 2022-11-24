package de.lorekp.thalgarath.init;

import de.lorekp.thalgarath.ThalgarathMain;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ThalgarathMain.MOD_ID);

}
