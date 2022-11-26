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
        //draw the empty mana bar
        GuiComponent.blit(poseStack, x +10, y - 48, 0, 0, 81, 7, 81, 7 );
        //get the current mana and draw the filled mana bar on top
        RenderSystem.setShaderTexture(0, MANA_BAR_FULL);
        GuiComponent.blit(poseStack, x+10, y-48, 0, 0, Math.round((81.f/100.f)*ClientManaData.getPlayerMana()), 7, 81, 7);

    });
}
