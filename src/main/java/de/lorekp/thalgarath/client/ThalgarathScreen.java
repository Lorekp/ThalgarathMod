package de.lorekp.thalgarath.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.networking.packet.ManaC2SPacket;
import de.lorekp.thalgarath.networking.packet.SkillC2SPacket;
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

    protected void init(){
        super.init();
        this.leftPos = (this.width - 176) / 2;
        this.topPos = (this.height - 166) / 2;
        //InvButton
        this.addRenderableWidget(new ImageButton(this.width/2 - 88, this.height/2 - 107, 28, 26, 56, 0, BUTTONS_LOCATION, pButton -> {
            Minecraft.getInstance().setScreen(new InventoryScreen(Minecraft.getInstance().player));
        }));
        //ThalmButton
        this.addRenderableWidget(new ImageButton(this.width/2 - 60, this.height/2 - 107, 28, 28, 28, 0, BUTTONS_LOCATION, pButton -> {}));

        //StrengthButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 79, this.topPos + 9, 16, 17, 112, 0, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(0));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Current strength: " + ClientSkillData.getPlayerSkill()[0]));
        }));
        //DexterityButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 78, this.topPos + 27, 17, 15, 128, 0, BUTTONS_LOCATION, pButton -> {
            ModMessages.sendToServer(new SkillC2SPacket(1));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Current strength: " + ClientSkillData.getPlayerSkill()[1]));
        }));
        //EnduranceButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 79, this.topPos + 44, 16, 15, 145, 0, BUTTONS_LOCATION, pButton -> {Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button"));}));
        //DefenseButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 80, this.topPos + 60, 14, 16, 161, 0, BUTTONS_LOCATION, pButton -> {Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button"));}));
        //WisdomButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 123, this.topPos + 10, 16, 12, 112, 34, BUTTONS_LOCATION, pButton -> {Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button"));}));
        //IntelligenceButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 123, this.topPos + 25, 16, 15, 128, 30, BUTTONS_LOCATION, pButton -> {Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button"));}));
        //CharismaButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 123, this.topPos + 42, 16, 16, 144, 30, BUTTONS_LOCATION, pButton -> {Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button"));}));
        //LuckButton
        this.addRenderableWidget(new ImageButton(this.leftPos + 124, this.topPos + 59, 13, 17, 160, 32, BUTTONS_LOCATION, pButton -> {Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button"));}));
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, THALGARATH_SCREEN);
        int posX = this.leftPos;
        int posY = this.topPos;
        this.blit(pPoseStack, posX, posY, 0, 0, 176, 166, 256, 256);
        renderEntityInInventory(posX + 51, posY + 75, 30, (float)(posX + 51) - this.xMouse, (float)(posY + 75 - 50) - this.yMouse, this.minecraft.player);
    }

    protected void renderStats(PoseStack pPoseStack, float pPartialTick, int pX, int pY){
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
       // RenderSystem.setShaderTexture(0, );
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.renderBg(pPoseStack, pPartialTick, pMouseX, pMouseY);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.xMouse = (float)pMouseX;
        this.yMouse = (float)pMouseY;
    }

    public static void renderEntityInInventory(int pPosX, int pPosY, int pScale, float pMouseX, float pMouseY, LivingEntity pLivingEntity) {
        float f = (float)Math.atan((double)(pMouseX / 40.0F));
        float f1 = (float)Math.atan((double)(pMouseY / 40.0F));
        renderEntityInInventoryRaw(pPosX, pPosY, pScale, f, f1, pLivingEntity);
    }
    public static void renderEntityInInventoryRaw(int pPosX, int pPosY, int pScale, float angleXComponent, float angleYComponent, LivingEntity pLivingEntity) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)pPosX, (double)pPosY, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)pScale, (float)pScale, (float)pScale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        float f2 = pLivingEntity.yBodyRot;
        float f3 = pLivingEntity.getYRot();
        float f4 = pLivingEntity.getXRot();
        float f5 = pLivingEntity.yHeadRotO;
        float f6 = pLivingEntity.yHeadRot;
        pLivingEntity.yBodyRot = 180.0F + f * 20.0F;
        pLivingEntity.setYRot(180.0F + f * 40.0F);
        pLivingEntity.setXRot(-f1 * 20.0F);
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
}
