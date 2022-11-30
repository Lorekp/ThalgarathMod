package de.lorekp.thalgarath.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

@AutoRegisterCapability
public class PlayerMana {

    public static Capability<PlayerMana> PLAYER_MANA = CapabilityManager.get(new CapabilityToken<PlayerMana>() { });
    private float mana;
    private final int MIN_MANA = 0;
    public float MAX_MANA = 100;

    public float getMana(){
        return mana;
    }

    public float getMAX_MANA(){
        return MAX_MANA;
    }

    public void setMAX_MANA(float maxMana){
        this.MAX_MANA = maxMana;
    }

    public void addMana(float add){
        this.mana = Math.min(mana + add, MAX_MANA);
    }

    public void subMana(int sub){
        this.mana = Math.max(mana - sub, MIN_MANA);
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putFloat("mana", mana);
    }

    public void loadNBTData(CompoundTag nbt){
        mana = nbt.getFloat("mana");
    }
}
