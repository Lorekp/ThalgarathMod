package de.lorekp.thalgarath.event;

import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.client.ManaHudOverlay;
import de.lorekp.thalgarath.client.ThalgarathScreen;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.networking.packet.ManaC2SPacket;
import de.lorekp.thalgarath.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ThalgarathMain.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.EXAMPLE_KEY.consumeClick()){
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed \"O\" Key!"));
                ModMessages.sendToServer(new ManaC2SPacket());
            }
        }

        @SubscribeEvent
        public void onOpenScreen(ScreenEvent.Init.Pre event)
        {
            if (event.getScreen() instanceof InventoryScreen) {
                event.getScreen().getClass().getMethod("addWidget", GuiEventListener.class, new Button(100, 200, 56, 52, "", button -> {
                    //Minecraft.getInstance().setScreen(new ThalgarathScreen());
                }));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = ThalgarathMain.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.EXAMPLE_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);
        }
    }
}
