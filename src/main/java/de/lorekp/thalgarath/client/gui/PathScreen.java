package de.lorekp.thalgarath.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.lorekp.thalgarath.ThalgarathMain;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PathScreen extends Screen {

    ResourceLocation PATH_SCREEN = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/path_gui_clean.png");
    ResourceLocation PATH_BUTTONS = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/path_buttons.png");
    protected int leftPos;
    protected int topPos;

    private final ImageButton[] buttons = new ImageButton[20];
    private final int pathType;

    protected PathScreen(Component pTitle, int pathType) {
        super(pTitle); this.pathType = pathType;
    }

    protected void init() {
        super.init();
        this.leftPos = (this.width - 256) / 2;
        this.topPos = (this.height - 256) / 2;
        switch (pathType) {
            case 0 -> initWindScreen();
        }
        for(int i = 0; i < 20; i++)
            this.addRenderableWidget(buttons[i]);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void renderBg(PoseStack pPoseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, PATH_SCREEN);
        int posX = this.leftPos;
        int posY = this.topPos;
        blit(pPoseStack, posX, posY, 0, 0, 256, 256, 256, 256);
    }

    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.renderBg(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public void initWindScreen(){
        //Fire apprentice
        buttons[0] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[1] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[2] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[3] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[4] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[5] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[6] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[7] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[8] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[9] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[10] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[11] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[12] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[13] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[14] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[15] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[16] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[17] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[18] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });
        //
        buttons[19] = new ImageButton(this.leftPos + 117, this.topPos + 10, 22, 22, 234, 0,100,  PATH_BUTTONS, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Fire Apprentice").withStyle(ChatFormatting.DARK_RED));
        });

    }
}
