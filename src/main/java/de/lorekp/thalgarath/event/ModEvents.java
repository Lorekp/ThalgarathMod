package de.lorekp.thalgarath.event;

import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.mana.PlayerMana;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Mod.EventBusSubscriber(modid = ThalgarathMain.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            if(!event.getObject().getCapability(PlayerMana.PLAYER_MANA).isPresent()){
                PlayerMana mana = new PlayerMana();
                LazyOptional<PlayerMana> optional = LazyOptional.of(() -> mana);

                ICapabilityProvider provider = new ICapabilityProvider() {
                    @Override
                    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction direction) {
                        if (cap == PlayerMana.PLAYER_MANA) {
                            return optional.cast();
                        }
                        return LazyOptional.empty();
                    }
                };
                event.addCapability(new ResourceLocation(ThalgarathMain.MOD_ID, "properties"), provider);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(PlayerMana.PLAYER_MANA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerMana.PLAYER_MANA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER){
            event.player.getCapability(PlayerMana.PLAYER_MANA).ifPresent(mana -> {
                if(mana.getMana() < 100 && event.player.getRandom().nextFloat() < 0.005f){
                    mana.addMana(1);
                    event.player.sendSystemMessage(Component.literal("added Mana"));
                }
            });
        }
    }
}


