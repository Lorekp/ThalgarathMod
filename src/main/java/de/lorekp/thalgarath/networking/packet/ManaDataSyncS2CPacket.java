package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.client.ClientManaData;
import de.lorekp.thalgarath.mana.PlayerMana;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {
    private final int mana;
    private final int maxMana;
    public ManaDataSyncS2CPacket(int mana, int maxMana){
        this.mana = mana;
        this.maxMana = maxMana;
    }
    public ManaDataSyncS2CPacket(FriendlyByteBuf buf){

        this.mana = buf.readInt();
        this.maxMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){

        buf.writeInt(mana);
        buf.writeInt(maxMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //clientside
            ClientManaData.setPlayerMana(mana);
            ClientManaData.setMaxMana(maxMana);
        });
        return true;
    }
}
