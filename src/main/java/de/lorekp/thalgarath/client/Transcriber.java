package de.lorekp.thalgarath.client;

import de.lorekp.thalgarath.ThalgarathMain;
import de.lorekp.thalgarath.event.ClientEvents;
import edu.cmu.sphinx.api.SpeechResult;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class Transcriber extends Thread {

    private final AtomicBoolean running = new AtomicBoolean(false);

    public Transcriber() {
        this.setName("Thalgarath Voice Recorder");
        this.setDaemon(true);
    }

    public boolean isRunning() {
        return this.running.get();
    }

    public void run() {
        ThalgarathMain.voiceRecognizer.startRecognition(true);
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Tstart"));
        notifyPlayerOffThreadSound(SoundEvents.ANVIL_PLACE);
        this.running.set(true);

        try {
            while(ClientEvents.ClientForgeEvents.getPressed()) {
                SpeechResult result = ThalgarathMain.voiceRecognizer.getResult();
                if (result != null) {
                    String text = result.getHypothesis();
                    ThalgarathMain.getLogger().trace("User Voice Input: {}", text);
                    notifyPlayerOffThread(() -> {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal(text));
                    });
                }
            }
        } finally {
            notifyPlayerOffThreadSound(SoundEvents.ANVIL_PLACE);
            ThalgarathMain.voiceRecognizer.stopRecognition();
            this.running.set(false);
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Tstop"));
        }

    }

    private static void notifyPlayerOffThreadSound(SoundEvent sound) {
        notifyPlayerOffThread(() -> {
            Minecraft.getInstance().player.playSound(sound, 1.0F, 1.0F);
        });
    }

    private static void notifyPlayerOffThread(Runnable task) {
        Minecraft.getInstance().tell(task);
    }
}