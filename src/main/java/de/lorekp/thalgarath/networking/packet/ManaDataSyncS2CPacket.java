package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {
    private final float mana;
    private final float maxMana;
    public ManaDataSyncS2CPacket(float mana, float maxMana){
        this.mana = mana;
        this.maxMana = maxMana;
    }
    public ManaDataSyncS2CPacket(FriendlyByteBuf buf){

        this.mana = buf.readFloat();
        this.maxMana = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf){

        buf.writeFloat(mana);
        buf.writeFloat(maxMana);
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
