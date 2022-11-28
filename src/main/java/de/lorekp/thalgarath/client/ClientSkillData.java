package de.lorekp.thalgarath.client;

public class ClientSkillData {
    private static int[] playerSkills = new int[8];

    public static void setPlayerSkill(int[] skills){
        ClientSkillData.playerSkills = skills;
    }
    public static void addPlayerSkill(int index, int addedSkillPoints){
        ClientSkillData.playerSkills[index] = ClientSkillData.playerSkills[index] + addedSkillPoints;
    }

    public static int[] getPlayerSkill(){
        return playerSkills;
    }
}
