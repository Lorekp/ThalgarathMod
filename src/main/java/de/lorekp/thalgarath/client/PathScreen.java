package de.lorekp.thalgarath.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.lorekp.thalgarath.ThalgarathMain;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class PathScreen extends Screen {

    ResourceLocation PATH_SCREEN = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/path_gui_clean.png");
    ResourceLocation PATH_BUTTONS = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/path_buttons.png");
    protected int leftPos;
    protected int topPos;
    private float xMouse;
    private float yMouse;

    private int pathType;
    protected PathScreen(Component pTitle, int pathType) {
        super(pTitle); this.pathType = pathType;
    }

    protected void init() {
        super.init();
        this.leftPos = (this.width - 176) / 2;
        this.topPos = (this.height - 166) / 2;
        switch (pathType) {
            case 0:
                initWindScreen();
        }
    }

    protected void renderTooltip(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick){

    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, PATH_SCREEN);
        int posX = this.leftPos;
        int posY = this.topPos;
        blit(pPoseStack, posX, posY, 0, 0, 176, 166, 256, 256);
    }

    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.xMouse = (float)pMouseX;
        this.yMouse = (float)pMouseY;
    }

    public void initWindScreen(){
        //Fire apprentice
        this.addRenderableWidget(new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, 256, 256,new Button.OnPress() {
            @Override
            public void onPress(Button pButton) {
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
            }
        }, new Button.OnTooltip() {
            @Override
            public void onTooltip(Button pButton, PoseStack pPoseStack, int pMouseX, int pMouseY) {

            }
        }, Component.literal("Fire Apprentice")));

    }
}
