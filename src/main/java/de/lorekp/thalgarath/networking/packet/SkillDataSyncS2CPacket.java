package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.client.ClientSkillData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillDataSyncS2CPacket {
    private int[] skill = new int[8];

    public SkillDataSyncS2CPacket(int[] skill){
        this.skill = skill;
    }
    public SkillDataSyncS2CPacket(FriendlyByteBuf buf){
        this.skill[0] = buf.readInt();
        this.skill[1] = buf.readInt();
        this.skill[2] = buf.readInt();
        this.skill[3] = buf.readInt();
        this.skill[4] = buf.readInt();
        this.skill[5] = buf.readInt();
        this.skill[6] = buf.readInt();
        this.skill[7] = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        if (skill != null) {
            buf.writeInt(skill[0]);
            buf.writeInt(skill[1]);
            buf.writeInt(skill[2]);
            buf.writeInt(skill[3]);
            buf.writeInt(skill[4]);
            buf.writeInt(skill[5]);
            buf.writeInt(skill[6]);
            buf.writeInt(skill[7]);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //clientside
            ClientSkillData.setPlayerSkill(skill);

        });
        return true;
    }
}
