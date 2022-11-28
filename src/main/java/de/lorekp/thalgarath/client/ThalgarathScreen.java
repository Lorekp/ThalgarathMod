package de.lorekp.thalgarath.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.lorekp.thalgarath.ThalgarathMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ThalgarathScreen extends Screen {

    ResourceLocation THALGARATH_SCREEN = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/thalm_gui_clean.png")

    protected ThalgarathScreen(Component pTitle) {
        super(pTitle);
    }

    protected void init(){
        super.init();
        //this.addRenderableWidget()
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, THALGARATH_SCREEN);
        this.blit(pPoseStack, (Minecraft.getInstance().getWindow().getWidth() - 176)/2, (Minecraft.getInstance().getWindow().getHeight() - 166)/2, 0, 0, 0, 176, 166, 176, 166);
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
    }
}
