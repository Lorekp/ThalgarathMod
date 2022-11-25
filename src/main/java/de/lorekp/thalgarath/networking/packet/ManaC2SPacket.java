package de.lorekp.thalgarath.networking.packet;

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
        });
        return true;
    }
}
