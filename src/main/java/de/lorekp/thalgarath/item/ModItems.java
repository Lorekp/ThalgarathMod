package de.lorekp.thalgarath.item;

import de.lorekp.thalgarath.ThalgarathMain;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ThalgarathMain.MOD_ID);

    public static final RegistryObject<Item> EMPTYVIAL = ITEMS.register("empty_vial", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THALGARATH_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
