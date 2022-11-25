package de.lorekp.thalgarath.client;

import com.mojang.blaze3d.systems.RenderSystem;
import de.lorekp.thalgarath.ThalgarathMain;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation MANA_BAR_EMPTY = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/mana_bar_empty.png");
    private static final ResourceLocation MANA_BAR_FULL = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/mana_bar_full.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth/2;
        int y =screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MANA_BAR_EMPTY);
        GuiComponent.blit(poseStack, x - 94, y - 54, 0, 0, 81, 7, 81, 7 );

        RenderSystem.setShaderTexture(0, MANA_BAR_FULL);

    });
}
