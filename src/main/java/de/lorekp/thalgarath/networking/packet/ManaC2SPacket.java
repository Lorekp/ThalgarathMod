package de.lorekp.thalgarath.networking.packet;

import de.lorekp.thalgarath.mana.PlayerMana;
import de.lorekp.thalgarath.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaC2SPacket {

    private static int amountToAdd;
    private static int newMaxMana;
    public ManaC2SPacket(int amountToAdd, int newMaxMana){
        if (newMaxMana!=0)
            this.newMaxMana = newMaxMana;
        this.amountToAdd = amountToAdd;
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

            player.getCapability(PlayerMana.PLAYER_MANA).ifPresent(mana -> {
                mana.addMana(amountToAdd);
                mana.setMAX_MANA(newMaxMana);
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), player);
            });
        });
        return true;
    }
}
