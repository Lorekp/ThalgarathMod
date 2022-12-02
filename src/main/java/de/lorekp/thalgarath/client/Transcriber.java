package de.lorekp.thalgarath.client;

import de.lorekp.thalgarath.ThalgarathMain;
import edu.cmu.sphinx.api.SpeechResult;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class Transcriber extends Thread {
    private static final BooleanSupplier CHECK_CHAT_OPEN = () -> {
        return Minecraft.getInstance().screen instanceof ChatScreen;
    };
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
        ThalgarathMain.getLogger().trace("voice recognizer started");
        notifyPlayerOffThreadSound(SoundEvents.ANVIL_PLACE, CHECK_CHAT_OPEN);
        this.running.set(true);

        try {
            while(Minecraft.getInstance().screen instanceof ChatScreen) {
                SpeechResult result = ThalgarathMain.voiceRecognizer.getResult();
                if (result != null) {
                    String text = result.getHypothesis();
                    ThalgarathMain.getLogger().trace("User Voice Input: {}", text);
                    if (text.startsWith("slash")) {
                        text = text.replaceFirst("slash", "/");
                    }

                    String finalText = text;
                    notifyPlayerOffThread(() -> {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal(finalText));
                    }, CHECK_CHAT_OPEN);
                }
            }
        } finally {
            notifyPlayerOffThreadSound(SoundEvents.ANVIL_PLACE, () -> {
                return true;
            });
            ThalgarathMain.voiceRecognizer.stopRecognition();
            this.running.set(false);
            ThalgarathMain.getLogger().trace("voice recognizer stopped");
        }

    }

    private static void notifyPlayerOffThreadSound(SoundEvent sound, BooleanSupplier shouldExecute) {
        notifyPlayerOffThread(() -> {
            Minecraft.getInstance().player.playSound(sound, 1.0F, 1.0F);
        }, shouldExecute);
    }

    private static void notifyPlayerOffThread(Runnable task, BooleanSupplier shouldExecute) {
        Minecraft.getInstance().tell(() -> {
            if (shouldExecute.getAsBoolean()) {
                task.run();
            }

        });
    }
}