package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.mana.PlayerMana;
import de.lorekp.thalgarath.mana.PlayerManaProvider;
import de.lorekp.thalgarath.networking.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaC2SPacket {

    private static float amountToAdd;
    private static float newMaxMana;
    public ManaC2SPacket(float amountToAdd, float newMaxMana){
        ManaC2SPacket.newMaxMana = newMaxMana;
        ManaC2SPacket.amountToAdd = amountToAdd;
    }
    public ManaC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //serverside
            ServerPlayer player = context.getSender();

            assert player != null;
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                mana.addMana(amountToAdd);
                if (newMaxMana != 0)
                    mana.setMAX_MANA(newMaxMana);
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), player);
            });
        });
        return true;
    }
}
