package de.lorekp.thalgarath.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_THALGARATH = "key.category.thalgarath";
    public static final String KEY_EXAMPLE = "key.thalgarath.example";

    public static final String A_KEY = "key.thalgarath.activate";

    public static final KeyMapping EXAMPLE_KEY = new KeyMapping(KEY_EXAMPLE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_THALGARATH);

    public static final KeyMapping KEY_ACTIVATE_VOICE = new KeyMapping(A_KEY, 96, KEY_CATEGORY_THALGARATH);

}
