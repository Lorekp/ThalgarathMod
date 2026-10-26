# Thalgarath

An experimental RPG-style mod for **Minecraft 1.19.2 / Forge 43.1.7**. Thalgarath combines a mana HUD, player attributes, custom inventory screens, and a microphone transcription experiment built with CMU Sphinx.

The repository contains an early development prototype, version `0.0.1-1.19.2`. It is useful for exploring Forge capabilities, events, GUI rendering, and speech integration; it is not a finished gameplay mod or a verified multiplayer release.

## What is implemented

- **Mana state and HUD:** player capability with NBT persistence, a default capacity of 100, periodic regeneration, and a textured meter.
- **Eight attributes:** Strength, Dexterity, Endurance, Defense, Wisdom, Intelligence, Awareness, and Luck, with a custom screen for displaying and incrementing them. Endurance and Defense are capped at 100.
- **Gameplay hooks:** attack speed, luck, outgoing damage, damage reduction, mana capacity, and regeneration experiments. These mechanics still need correction and balancing.
- **Networking:** registered client/server message types, with serialized server-to-client mana and skill snapshots. Client-to-server payload handling remains unfinished.
- **Speech transcription:** a background thread reads microphone input through Sphinx and displays recognized text as local player system messages. It does not cast spells, invoke commands, or send recognized text to multiplayer chat.
- **Custom assets:** inventory tabs, attribute/path screens, mana textures, and an Empty Vial item in the Thalgarath creative tab.

Six elemental paths have data/UI scaffolding. They are not completed skill trees.

## Development setup

The following instructions are derived from the checked-in configuration; a clean build and game launch have **not been verified**.

Prerequisites:

- A JDK 17 installation.
- Git and network access for the Gradle wrapper, Forge, and Parchment dependencies.
- A graphical environment for the development client; a microphone for transcription.

The repository includes Gradle 7.5's wrapper. The build targets Forge `1.19.2-43.1.7` and Parchment mappings `2022.11.27-1.19.2`. ForgeGradle and the Parchment plugin use dynamic version selectors, so dependency resolution is not fully pinned.

1. Clone the repository and enter it:

   ```sh
   git clone https://github.com/Lorekp/ThalgarathMod.git
   cd ThalgarathMod
   ```

2. Edit `gradle.properties`: remove or update the machine-specific `org.gradle.java.home` setting to match your JDK 17 installation. Set `JAVA_HOME` appropriately if removing that override.

3. From the repository root, build or launch the development client:

   ```powershell
   .\gradlew.bat build
   .\gradlew.bat runClient
   ```

   On macOS/Linux, use `./gradlew build` and `./gradlew runClient` (make the wrapper executable if needed).

4. For IDE run configurations, use `genIntellijRuns` or `genEclipseRuns`, as described in the original [Forge setup notes](README.txt).

The build includes a local `libs/sphinx4-core-5prealpha-SNAPSHOT.jar`, adds it to development runs, and unpacks the library into the mod JAR. English speech-model resources also exist under `libs/edu/cmu/sphinx/models/en-us/`. Keep the bundled library and resources; packaged model loading has not been independently checked.

Build output goes to `build/libs/`. The archive base name is still the template value `modid`, so the configured artifact name is `modid-0.0.1-1.19.2.jar`.

## Exploring the prototype

After a successful development launch, create a disposable single-player world:

- Open the player inventory and select the added Thalgarath tab to inspect attributes.
- Look for the mana meter above the hotbar.
- Press **O** to toggle transcription. The registered control currently appears as **ExampleKey** in the Thalgarath key category. Recognized English text is displayed locally; debug start/stop messages and anvil sounds are still present.
- The Wind path button opens an unfinished screen. Other path buttons currently print diagnostic messages.

Speech recognition requires successful microphone/model initialization. The thread blocks while waiting for recognition results, so stopping may not respond immediately. Initialization failure and leaving a world during recording are not robustly handled.

## Source layout

| Location | Role |
| --- | --- |
| `src/main/java/de/lorekp/thalgarath/ThalgarathMain.java` | Mod initialization, packet registration, Sphinx setup |
| `client/` | Client state caches, mana overlay, transcription, GUI screens |
| `mana/`, `skill/` | Player capability data and NBT persistence |
| `event/` | Inventory/key handlers, player lifecycle, ticks, combat hooks |
| `networking/` | Forge SimpleChannel registration and packet classes |
| `item/` | Item and creative-tab registration |
| `src/main/resources/` | Mod metadata, translations, models, and textures |
| `libs/` | Bundled Sphinx binary, extracted classes, and English model resources |

Java subdirectories in the table are relative to `src/main/java/de/lorekp/thalgarath/`.

## Known limitations and validation

- Both client-to-server packets use static fields and empty byte encoders/decoders. Requests do not transmit their intended values between separate processes; multiplayer is incomplete.
- Server event handlers reference client-only state and APIs. Dedicated-server compatibility is not established.
- Attribute buttons have no experience or skill-point cost. Awareness has a stored/displayed value but no gameplay effect yet.
- Combat calculations need fixes: zero Strength currently scales outgoing damage to zero, and damage reduction subtracts a fixed amount rather than the percentage described in comments.
- Elemental path screens, expanded tooltips, and parts of the metadata retain placeholders. Only the first node of each path is saved to NBT.
- No project test suite or CI workflow is included. The configured GameTestServer run has no supplied GameTests and is not evidence of validation. No verified release artifact is available.

A useful manual validation pass would cover attribute persistence across reconnect/death, mana regeneration, packet behavior between separate client/server processes, baseline combat damage, and speech start/stop across world transitions.

## Attribution and license

Mod metadata names **Lorekp** as author. The project uses the Minecraft Forge development kit, Mojang/Parchment mappings, and CMU Sphinx; the speech engine and acoustic models are third-party work.

The mod declares **GNU GPL v3**; see [LICENSE.txt](LICENSE.txt). Preserve the existing notices for bundled dependencies and resources, including the Alpha Cephei acoustic-model notice in [the model README](libs/edu/cmu/sphinx/models/en-us/en-us/README). The original [README.txt](README.txt) also records the mapping-license notice.

