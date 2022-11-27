package de.lorekp.thalgarath.client;

public class ClientManaData {
    private static int playerMana;

    private static int MAX_MANA;

    public static void setPlayerMana(int mana){
        ClientManaData.playerMana = mana;
    }

    public static void setMaxMana(int maxMana){
        ClientManaData.MAX_MANA = maxMana;
    }

    public static int getMaxMana(){
        return MAX_MANA;
    }
    public static int getPlayerMana(){
        return playerMana;
    }
}
