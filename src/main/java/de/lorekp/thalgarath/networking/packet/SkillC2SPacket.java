package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.mana.PlayerMana;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.skill.PlayerSkill;
import de.lorekp.thalgarath.skill.PlayerSkillProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillC2SPacket {
    private static int index;

    public SkillC2SPacket(int index){
        SkillC2SPacket.index = index;
    }
    public SkillC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //serverside
            ServerPlayer player = context.getSender();
            player.getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(playerSkill -> {
                playerSkill.addSkill(index);
                ModMessages.sendToPlayer(new SkillDataSyncS2CPacket(playerSkill.getSkill(), playerSkill.getPlayerPath()), player);
            });
        });
        return true;
    }
}
