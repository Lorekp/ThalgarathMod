package de.lorekp.thalgarath.client;

public class ClientSkillData {
    private static int[] playerSkills = new int[8];

    private static boolean[][] playerPath = new boolean[6][20];

    public static void setPlayerSkill(int[] skills){
        ClientSkillData.playerSkills = skills;
    }
    public static void addPlayerSkill(int index, int addedSkillPoints){
        ClientSkillData.playerSkills[index] = ClientSkillData.playerSkills[index] + addedSkillPoints;
    }

    public static void setPlayerPath(boolean[][] path) { ClientSkillData.playerPath = path;}

    public static void setPlayerPathAt(int pathType, int index, boolean set){
        ClientSkillData.playerPath[pathType][index] = set;
    }

    public static int[] getPlayerSkill(){
        return playerSkills;
    }

    public static int getPlayerSkillAt(int index){
        return playerSkills[index];
    }

    public static boolean[][] getPlayerPath() { return playerPath;}

    public static boolean getPlayerPathAt(int pathType, int index) {
        return ClientSkillData.playerPath[pathType][index];
    }
}
