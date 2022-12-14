package de.lorekp.thalgarath.client.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.client.ClientSkillData;
import de.lorekp.thalgarath.client.gui.PathScreen;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.networking.packet.ManaC2SPacket;
import de.lorekp.thalgarath.networking.packet.SkillC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ThalgarathScreen extends Screen {

    private static final ResourceLocation THALGARATH_SCREEN = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/thalm_gui_clean.png");
    private static final ResourceLocation BUTTONS_LOCATION = new ResourceLocation(ThalgarathMain.MOD_ID, "textures/gui/inventory_buttons.png");
    private float xMouse;
    private float yMouse;
    private int leftPos;
    private int topPos;

    public ThalgarathScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void init(){
        super.init();
        this.leftPos = (this.width - 176) / 2;
        this.topPos = (this.height - 166) / 2;
        //InvButton
        this.addRenderableWidget(new ImageButton(this.width/2 - 88, this.height/2 - 107, 28, 26, 56, 0, BUTTONS_LOCATION, pButton -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().setScreen(new InventoryScreen(Minecraft.getInstance().player));
        }));
        //ThalmButton
        this.addRenderableWidget(new ImageButton(this.width/2 - 60, this.height/2 - 107, 28, 28, 28, 0, BUTTONS_LOCATION, pButton -> {}));

        //SkillButtons:
        //StrengthButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 79, this.topPos + 9, 16, 17, 112, 0, 100, BUTTONS_LOCATION, 256, 256, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(0));
        }, (pButton, pPoseStack, pMouseX, pMouseY) -> {
            this.renderTooltip(pPoseStack, Component.literal("test"), pMouseX, pMouseY);
        }, Component.empty()));
        //DexterityButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 78, this.topPos + 27, 17, 15, 128, 0, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(1));
        }));
        //EnduranceButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 79, this.topPos + 44, 16, 15, 145, 0, BUTTONS_LOCATION, pButton -> {
            if (ClientSkillData.getPlayerSkillAt(2) < 100)
                ModMessages.sendToServer(new SkillC2SPacket(2));
            else {
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Max Endurance of 100 reached!").withStyle(ChatFormatting.DARK_RED));
            }
        }));
        //DefenseButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 80, this.topPos + 60, 14, 16, 161, 0, BUTTONS_LOCATION, pButton -> {
            if (ClientSkillData.getPlayerSkillAt(3) < 100)
                ModMessages.sendToServer(new SkillC2SPacket(3));
            else {
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Max Defense of 100 reached!").withStyle(ChatFormatting.DARK_RED));
            }
        }));
        //WisdomButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 123, this.topPos + 10, 16, 12, 112, 34, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(4));
            ModMessages.sendToServer(new ManaC2SPacket(0, 100 + ClientSkillData.getPlayerSkillAt(4)));
        }));
        //IntelligenceButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 123, this.topPos + 25, 16, 15, 128, 30, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(5));
        }));
        //AwarenessButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 123, this.topPos + 42, 16, 16, 144, 30, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(6));
        }));
        //LuckButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 124, this.topPos + 59, 13, 17, 160, 32, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(7));
        }));

        //PathButtons:
        //WindButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 10, this.topPos + 87, 50, 21, 106, 172, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("WindButton").withStyle(ChatFormatting.AQUA));
            Minecraft.getInstance().setScreen(new PathScreen(Component.literal("WindScreen"), 0));
        }));
        //WaterButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 63, this.topPos + 87, 50, 21, 156, 172, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("WaterButton").withStyle(ChatFormatting.DARK_BLUE));
        }));
        //FireButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 10, this.topPos + 112, 50, 21, 206, 172, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("FireButton").withStyle(ChatFormatting.RED));
        }));
        //EarthButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 63, this.topPos + 112, 50, 21, 106, 214, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("EarthButton").withStyle(ChatFormatting.DARK_GRAY));
        }));
        //LightButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 116, this.topPos + 87, 50, 21, 156, 214, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("LightButton").withStyle(ChatFormatting.YELLOW));
        }));
        //DarkButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 116, this.topPos + 112, 50, 21, 206, 214, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("DarkButton").withStyle(ChatFormatting.DARK_PURPLE));
        }));
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, THALGARATH_SCREEN);
        int posX = this.leftPos;
        int posY = this.topPos;
        blit(pPoseStack, posX, posY, 0, 0, 176, 166, 256, 256);
        assert this.minecraft != null;
        renderEntityInInventory(posX + 51, posY + 75, 30, (float)(posX + 51) - this.xMouse, (float)(posY + 75 - 50) - this.yMouse, this.minecraft.player);
    }

    protected void renderStats(PoseStack pPoseStack, float pPartialTick, int pX, int pY){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BUTTONS_LOCATION);
        int posX = this.leftPos;
        int posY = this.topPos + 12;
        int drawHeight = 0;
        for (int i = 0; i < 8; i++) {
            int skill = ClientSkillData.getPlayerSkillAt(i);
            int secondRow = 0;
            if (drawHeight > 3) {drawHeight = 0;}
            if (i > 3){secondRow = 44;}
            blit(pPoseStack, posX + secondRow + 114, posY + drawHeight*17, this.lastDigit(skill) * 7, 248, 6, 8, 256, 256);
            blit(pPoseStack, posX + secondRow + 105, posY + drawHeight*17, this.secondToLastDigit(skill) * 7, 248, 6, 8, 256, 256);
            blit(pPoseStack, posX + secondRow + 96, posY + drawHeight*17, this.thirdToLastDigit(skill) * 7, 248, 6, 8, 256, 256);
            drawHeight++;
        }

    }

    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        this.renderStats(pPoseStack, pPartialTick, pMouseX, pMouseY);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.xMouse = (float)pMouseX;
        this.yMouse = (float)pMouseY;
    }

    public static void renderEntityInInventory(int pPosX, int pPosY, int pScale, float pMouseX, float pMouseY, LivingEntity pLivingEntity) {
        float f = (float)Math.atan(pMouseX / 40.0F);
        float f1 = (float)Math.atan(pMouseY / 40.0F);
        renderEntityInInventoryRaw(pPosX, pPosY, pScale, f, f1, pLivingEntity);
    }
    public static void renderEntityInInventoryRaw(int pPosX, int pPosY, int pScale, float angleXComponent, float angleYComponent, LivingEntity pLivingEntity) {
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(pPosX, pPosY, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)pScale, (float)pScale, (float)pScale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(angleYComponent * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        float f2 = pLivingEntity.yBodyRot;
        float f3 = pLivingEntity.getYRot();
        float f4 = pLivingEntity.getXRot();
        float f5 = pLivingEntity.yHeadRotO;
        float f6 = pLivingEntity.yHeadRot;
        pLivingEntity.yBodyRot = 180.0F + angleXComponent * 20.0F;
        pLivingEntity.setYRot(180.0F + angleXComponent * 40.0F);
        pLivingEntity.setXRot(-angleYComponent * 20.0F);
        pLivingEntity.yHeadRot = pLivingEntity.getYRot();
        pLivingEntity.yHeadRotO = pLivingEntity.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        pLivingEntity.yBodyRot = f2;
        pLivingEntity.setYRot(f3);
        pLivingEntity.setXRot(f4);
        pLivingEntity.yHeadRotO = f5;
        pLivingEntity.yHeadRot = f6;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    private int lastDigit(int number) { return number % 10; }
    private int secondToLastDigit(int number) {
        if (number < 10)
            return 10;
        return (number/10) % 10;
    }

    private int thirdToLastDigit(int number) {
        if (number < 100)
            return 10;
        return (number/100) % 10;
    }
}
