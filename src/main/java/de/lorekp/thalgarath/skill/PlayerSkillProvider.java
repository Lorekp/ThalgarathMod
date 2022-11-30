package de.lorekp.thalgarath.skill;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerSkillProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSkill> PLAYER_SKILL = CapabilityManager.get(new CapabilityToken<PlayerSkill>() { });

    private PlayerSkill skill = null;
    private final LazyOptional<PlayerSkill> optional = LazyOptional.of(this::createPlayerSkill);

    private PlayerSkill createPlayerSkill() {
        if(this.skill == null) {
            this.skill = new PlayerSkill();
        }

        return this.skill;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SKILL) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSkill().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSkill().loadNBTData(nbt);
    }
}