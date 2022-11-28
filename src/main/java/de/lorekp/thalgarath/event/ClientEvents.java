package de.lorekp.thalgarath.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.client.ClientManaData;
import de.lorekp.thalgarath.client.ManaHudOverlay;
import de.lorekp.thalgarath.client.ThalgarathScreen;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.networking.packet.ManaC2SPacket;
import de.lorekp.thalgarath.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ThalgarathMain.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        private static final ResourceLocation TABS_LOCATION = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/inventory_buttons.png");
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.EXAMPLE_KEY.consumeClick()){
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed \"O\" Key!"));
                ModMessages.sendToServer(new ManaC2SPacket());
            }
        }

        @SubscribeEvent
        public static void onInitGui(ScreenEvent.Init event){
            if (event.getScreen() instanceof InventoryScreen) {

                ImageButton thalmButton = new ImageButton(event.getScreen().width / 2 - 60, event.getScreen().height / 2 - 107, 28, 24, 0, 0, TABS_LOCATION, pButton -> {
                    Minecraft.getInstance().setScreen(new ThalgarathScreen(Component.literal("ThalgarathScreen")));
                });
                event.getScreen().renderables.add(thalmButton);
                event.addListener(thalmButton);

                ImageButton dInvButton = new ImageButton(event.getScreen().width / 2 - 88, event.getScreen().height / 2 - 107, 28, 29, 84, 0, TABS_LOCATION, pButton -> {
                });
                event.getScreen().renderables.add(dInvButton);
                event.addListener(dInvButton);
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
