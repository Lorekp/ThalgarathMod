package de.lorekp.thalgarath.client;

public class ClientManaData {
    private static float playerMana;

    private static float MAX_MANA;

    public static void setPlayerMana(float mana){
        ClientManaData.playerMana = mana;
    }

    public static void setMaxMana(float maxMana){
        ClientManaData.MAX_MANA = maxMana;
    }

    public static float getMaxMana(){
        return MAX_MANA;
    }
    public static float getPlayerMana(){
        return playerMana;
    }
}
