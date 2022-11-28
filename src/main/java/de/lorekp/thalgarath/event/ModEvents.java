package de.lorekp.thalgarath.event;

import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.client.ClientManaData;
import de.lorekp.thalgarath.mana.PlayerMana;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.networking.packet.ManaDataSyncS2CPacket;
import de.lorekp.thalgarath.networking.packet.SkillDataSyncS2CPacket;
import de.lorekp.thalgarath.skill.PlayerSkill;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
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
                event.addCapability(new ResourceLocation(ThalgarathMain.MOD_ID, "mana"), provider);
            }
            if(!event.getObject().getCapability(PlayerSkill.PLAYER_SKILL).isPresent()){
                PlayerSkill playerSkill = new PlayerSkill();
                LazyOptional<PlayerSkill> optional = LazyOptional.of(() -> playerSkill);

                ICapabilityProvider provider = new ICapabilityProvider() {
                    @Override
                    public <T> LazyOptional<T> getCapability( Capability<T> cap, @Nullable Direction direction) {
                        if (cap == PlayerSkill.PLAYER_SKILL){
                            return optional.cast();
                        }
                        return LazyOptional.empty();
                    }
                };
                event.addCapability((new ResourceLocation(ThalgarathMain.MOD_ID, "skill")), provider);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(!event.isWasDeath()){
            event.getOriginal().getCapability(PlayerMana.PLAYER_MANA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerMana.PLAYER_MANA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
        event.getOriginal().getCapability(PlayerSkill.PLAYER_SKILL).ifPresent(oldStore -> {
            event.getOriginal().getCapability(PlayerSkill.PLAYER_SKILL).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER){
            event.player.getCapability(PlayerMana.PLAYER_MANA).ifPresent(mana -> {
                if(mana.getMana() < 100 && event.player.getRandom().nextFloat() < 0.005f){
                    mana.addMana(10);
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), (ServerPlayer) event.player);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide){
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerMana.PLAYER_MANA).ifPresent(mana -> {
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), player);
                });

                player.getCapability(PlayerSkill.PLAYER_SKILL).ifPresent(skill -> {
                    ModMessages.sendToPlayer(new SkillDataSyncS2CPacket(skill.getSkill()), player);
                });
            }
        }
    }
}


