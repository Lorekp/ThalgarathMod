package de.lorekp.thalgarath.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    private float mana = 0;
    private final int MIN_MANA = 0;
    private float MAX_MANA = 100;

    public float getMana() {
        return this.mana;
    }

    public void addMana(float add) {
        this.mana = Math.min(mana + add, this.MAX_MANA);
    }

    public void subMana(float sub) {
        this.mana = Math.max(mana - sub, MIN_MANA);
    }

    public float getMAX_MANA(){
        return MAX_MANA;
    }

    public void setMAX_MANA(float newMaxMana ){
        if (newMaxMana > 0)
            this.MAX_MANA = newMaxMana;
    }

    public void copyFrom(PlayerMana source) {
        this.mana = source.mana;
        this.MAX_MANA = source.MAX_MANA;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("mana", mana);
        nbt.putFloat("MaxMana", MAX_MANA);
    }

    public void loadNBTData(CompoundTag nbt) {
        mana = nbt.getFloat("mana");
        MAX_MANA = nbt.getFloat("MaxMana");
    }
}