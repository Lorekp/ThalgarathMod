package de.lorekp.thalgarath.skill;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

@AutoRegisterCapability
public class PlayerSkill {

    public static Capability<de.lorekp.thalgarath.skill.PlayerSkill> PLAYER_SKILL = CapabilityManager.get(new CapabilityToken<de.lorekp.thalgarath.skill.PlayerSkill>() { });
    private int[] skill = new int[8];

    public int[] getSkill(){
        return skill;
    }

    public int getSkillAt(int index){
        return skill[index];
    }

    public void addSkill(int index){
        if (index == 2 || index == 3){
            this.skill[index] = Math.min(this.skill[index] + 1, 100);
        }
        else
            this.skill[index] = this.skill[index] + 1;
    }
    public void copyFrom(de.lorekp.thalgarath.skill.PlayerSkill source){
        this.skill = source.skill;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("Strength", skill[0]);
        nbt.putInt("Dexterity", skill[1]);
        nbt.putInt("Endurance", skill[2]);
        nbt.putInt("Defense", skill[3]);
        nbt.putInt("Wisdom", skill[4]);
        nbt.putInt("Intelligence", skill[5]);
        nbt.putInt("Awareness", skill[6]);
        nbt.putInt("Luck", skill[7]);

    }

    public void loadNBTData(CompoundTag nbt){
        skill[0] = nbt.getInt("Strength");
        skill[1] = nbt.getInt("Dexterity");
        skill[2] = nbt.getInt("Endurance");
        skill[3] = nbt.getInt("Defense");
        skill[4] = nbt.getInt("Wisdom");
        skill[5] = nbt.getInt("Intelligence");
        skill[6] = nbt.getInt("Awareness");
        skill[7] = nbt.getInt("Luck");
    }
}
