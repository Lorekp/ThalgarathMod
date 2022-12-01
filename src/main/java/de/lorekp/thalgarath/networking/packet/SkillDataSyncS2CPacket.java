package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.client.ClientSkillData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillDataSyncS2CPacket {
    private int[] skill = new int[8];

    private boolean[][] playerPath = new boolean[6][20];

    public SkillDataSyncS2CPacket(int[] skill, boolean[][] playerPath){
        this.skill = skill;
        this.playerPath = playerPath;
    }
    public SkillDataSyncS2CPacket(FriendlyByteBuf buf){
        for (int i = 0; i < 8; i++)
            this.skill[i] = buf.readInt();
        for (int i = 0; i < 6; i++)
            for (int j = 0; j <20; j++)
                this.playerPath[i][j] = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        if (skill != null) {
            for (int i = 0; i < 8; i++)
                buf.writeInt(this.skill[i]);

        }
        if (playerPath != null){
            for (int i = 0; i < 6; i++)
                for (int j = 0; j <20; j++)
                    buf.writeBoolean(this.playerPath[i][j]);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //clientside
            ClientSkillData.setPlayerSkill(skill);
            ClientSkillData.setPlayerPath(playerPath);
        });
        return true;
    }
}
