package de.lorekp.thalgarath;

import com.mojang.logging.LogUtils;
import de.lorekp.thalgarath.item.ModItems;
import de.lorekp.thalgarath.networking.ModMessages;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.logging.Handler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ThalgarathMain.MOD_ID)
public class ThalgarathMain
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "thalgarath";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Configuration sphinxConfig = new Configuration();
    public static volatile LiveSpeechRecognizer voiceRecognizer;

    public ThalgarathMain()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static org.slf4j.Logger getLogger() {
        return LOGGER;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public  static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            LOGGER.debug("loading speech recognizer configuration");
            sphinxConfig.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            sphinxConfig.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            sphinxConfig.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
            LOGGER.debug("initializing speech recognizer");

            try {
                voiceRecognizer = new LiveSpeechRecognizer(sphinxConfig);
            } catch (IOException var4) {
                LOGGER.error("unable to initialize speech listener", var4);
            }
        }
    }




}
