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
    public ManaC2SPacket(){

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

            player.sendSystemMessage(Component.translatable("Packet Successfully sent!").withStyle(ChatFormatting.AQUA));

            player.getCapability(PlayerMana.PLAYER_MANA).ifPresent(mana -> {
                mana.subMana(1);
                player.sendSystemMessage(Component.literal("Current Mana: " + mana.getMana()).withStyle(ChatFormatting.GOLD));
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), player);
            });
        });
        return true;
    }
}
