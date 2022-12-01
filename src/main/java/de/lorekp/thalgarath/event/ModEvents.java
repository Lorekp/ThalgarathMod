package de.lorekp.thalgarath.event;

import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.client.ClientSkillData;
import de.lorekp.thalgarath.mana.PlayerMana;
import de.lorekp.thalgarath.mana.PlayerManaProvider;
import de.lorekp.thalgarath.networking.ModMessages;
import de.lorekp.thalgarath.networking.packet.ManaDataSyncS2CPacket;
import de.lorekp.thalgarath.networking.packet.SkillDataSyncS2CPacket;
import de.lorekp.thalgarath.skill.PlayerSkill;
import de.lorekp.thalgarath.skill.PlayerSkillProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;


@Mod.EventBusSubscriber(modid = ThalgarathMain.MOD_ID)
public class ModEvents {
    static int tickCounter = 0;

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            if(!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()){
                event.addCapability(new ResourceLocation(ThalgarathMain.MOD_ID, "mana"), new PlayerManaProvider());}
            if(!event.getObject().getCapability(PlayerSkillProvider.PLAYER_SKILL).isPresent()){
                event.addCapability(new ResourceLocation(ThalgarathMain.MOD_ID, "skill"), new PlayerSkillProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event){
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(PlayerMana.class);
        event.register(PlayerSkill.class);
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().reviveCaps();
            //clone capabilities here
            event.getOriginal().getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(newStore -> {
                    newStore.copyForRespawn(oldStore);
                });
            });
            //clone capabilities above
            event.getOriginal().invalidateCaps();
        }

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side.isServer() && (Minecraft.getInstance().player != null)){
            //adjust player attack speed(1%perlvl), luck(1% of luck potion per level)
            event.player.getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(playerSkill -> {
                Objects.requireNonNull(event.player.getAttribute(Attributes.ATTACK_SPEED)).setBaseValue(4 + (playerSkill.getSkillAt(1) * 0.01F) * 4);
                Objects.requireNonNull(event.player.getAttribute(Attributes.LUCK)).setBaseValue(0.01F * playerSkill.getSkillAt(7));
            });

        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event){
        //every second (all players):
        if (event.phase.equals(TickEvent.Phase.START))
        {
            if (tickCounter % 20 == 0) {
                for (int i = 0; i < event.getServer().getPlayerList().getPlayerCount(); i++) {
                    int finalI = i;
                    event.getServer().getPlayerList().getPlayers().get(i).getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        mana.addMana(1 + ClientSkillData.getPlayerSkillAt(5) * 0.01F);
                        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), event.getServer().getPlayerList().getPlayers().get(finalI));
                    });
                }
                tickCounter = 0;
            }
        }else
            tickCounter++;
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide){
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMAX_MANA()), player);
                });

                player.getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(skill -> {
                    ModMessages.sendToPlayer(new SkillDataSyncS2CPacket(skill.getSkill()), player);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingHurtEvent event){
        if(event.getSource().getEntity() instanceof ServerPlayer player){
            //adjust player attack damage
            player.getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(playerSkill -> {
                event.setAmount(event.getAmount() * (playerSkill.getSkillAt(0) * 0.01F));
            });
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event){
        if (event.getEntity() instanceof ServerPlayer player){
            //adjust damage received (max 50% damage reduction at 100endurance/defense)
            player.getCapability(PlayerSkillProvider.PLAYER_SKILL).ifPresent(playerSkill -> {
                if (isSpecialDamageType(event.getSource()))
                    event.setAmount(event.getAmount() - playerSkill.getSkillAt(2) * 0.005F);
                else
                    event.setAmount(event.getAmount() - playerSkill.getSkillAt(3) * 0.005F);
            });
        }
    }

    private static boolean isSpecialDamageType(DamageSource source){
        return source == DamageSource.MAGIC || source == DamageSource.FREEZE || source == DamageSource.WITHER || source == DamageSource.DRAGON_BREATH || source == DamageSource.DROWN || source == DamageSource.LIGHTNING_BOLT || source == DamageSource.HOT_FLOOR || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE || source == DamageSource.LAVA || source == DamageSource.DRY_OUT;
    }
}


