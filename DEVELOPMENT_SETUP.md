# Minecraft Mod Development Setup Guide
## Levitation Staff Mod - Development Environment

This guide will walk you through setting up a complete development environment for the Levitation Staff mod.

## 📋 Prerequisites

### Required Software
- **Java Development Kit (JDK) 17** - Required for Minecraft 1.19.2
- **IntelliJ IDEA** (Community Edition is free) or **Eclipse IDE**
- **Git** (for version control)
- **Minecraft Java Edition** (for testing)

### System Requirements
- **OS**: Windows 10/11, macOS 10.14+, or Linux
- **RAM**: Minimum 8GB (16GB recommended)
- **Storage**: At least 5GB free space
- **Java Heap**: 4GB allocated to IDE

---

## 🔧 Step 1: Install Java Development Kit (JDK) 17

### Windows:
1. Download **OpenJDK 17** from [Adoptium](https://adoptium.net/temurin/releases/)
2. Choose **Windows x64** installer
3. Run the installer with administrator privileges
4. **Important**: Check "Add to PATH" during installation
5. Verify installation:
   ```cmd
   java -version
   javac -version
   ```
   Both should show version 17.x.x

### macOS:
```bash
# Using Homebrew (recommended)
brew install openjdk@17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

# Verify installation
java -version
javac -version
```

### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
javac -version
```

---

## 🎯 Step 2: Install IntelliJ IDEA

### Download and Install:
1. Go to [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/)
2. Download **Community Edition** (free)
3. Install with default settings
4. **Important**: Install the following plugins:
   - **Minecraft Development** (for mod development)
   - **Gradle** (should be included)

### Configure IntelliJ:
1. **File** → **Settings** → **Build, Execution, Deployment** → **Build Tools** → **Gradle**
2. Set **Gradle JVM** to your JDK 17 installation
3. **File** → **Settings** → **Editor** → **File Encodings**
4. Set all encodings to **UTF-8**

---

## 📂 Step 3: Set Up the Mod Workspace

### Option A: Clone from Repository (if using Git)
```bash
git clone <your-repository-url>
cd LevitationStaff
```

### Option B: Copy the Mod Files
1. Create a new folder: `MinecraftMods/LevitationStaff`
2. Copy all the mod files we created into this folder
3. Ensure the folder structure looks like this:
```
LevitationStaff/
├── build.gradle
├── gradle/
├── gradlew
├── gradlew.bat
├── settings.gradle
├── src/
│   └── main/
│       ├── java/
│       │   └── com/levitationstaff/mod/
│       └── resources/
│           ├── META-INF/
│           ├── assets/
│           └── data/
└── README.md
```

### Import into IntelliJ:
1. **File** → **Open**
2. Navigate to your `LevitationStaff` folder
3. Select the **build.gradle** file
4. Click **Open**
5. Choose **Open as Project**
6. **Import Settings**:
   - ✅ Use auto-import
   - ✅ Create directories for empty content roots
   - Select **Use gradle 'wrapper' task configuration**

---

## ⚙️ Step 4: Initial Gradle Setup

### First Time Setup:
1. Open **Terminal** in IntelliJ (View → Tool Windows → Terminal)
2. Run the setup commands:

```bash
# Windows
./gradlew genEclipseRuns
./gradlew genIntellijRuns

# macOS/Linux  
./gradlew genEclipseRuns
./gradlew genIntellijRuns
```

### If you get permission errors (macOS/Linux):
```bash
chmod +x gradlew
./gradlew genEclipseRuns
./gradlew genIntellijRuns
```

### Refresh Gradle Project:
1. **Gradle** tab (right side of IntelliJ)
2. Click **Refresh** button
3. Wait for dependencies to download (this may take 5-15 minutes)

---

## 🏗️ Step 5: Build the Mod

### Test Build:
```bash
./gradlew build
```

**Expected output:**
- Build should complete successfully
- JAR file created in `build/libs/levitationstaff-1.0.0.jar`
- No compilation errors

### If Build Fails:
1. **Check Java version**: Ensure you're using JDK 17
2. **Clean and rebuild**:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```
3. **Check internet connection**: Gradle needs to download dependencies

---

## 🎮 Step 6: Set Up Run Configurations

IntelliJ should automatically create run configurations. Verify these exist:

### Run Configurations Panel:
1. **Run** → **Edit Configurations**
2. You should see:
   - **runClient** - Runs Minecraft client with your mod
   - **runServer** - Runs dedicated server with your mod

### Configure Client Run:
1. Select **runClient**
2. **VM Options**: Add `-Xmx4G -Xms1G`
3. **Program Arguments**: Add `--username YourTestUsername`
4. **Working Directory**: Should be `$PROJECT_DIR$/run`

### Configure Server Run:
1. Select **runServer** 
2. **VM Options**: Add `-Xmx2G -Xms1G`
3. **Working Directory**: Should be `$PROJECT_DIR$/run`

---

## 🧪 Step 7: Test the Development Environment

### Run the Client:
1. Select **runClient** configuration
2. Click **Run** (green play button)
3. **Expected behavior**:
   - Minecraft launches with Forge
   - Mod loads successfully
   - See "Levitation Staff mod setup complete!" in logs
   - Staffs appear in Creative inventory

### Run the Server:
1. Select **runServer** configuration  
2. Click **Run**
3. **Expected behavior**:
   - Server starts successfully
   - Mod loads on server side
   - No errors in console

### First Test:
1. **In Creative Mode**:
   - Open inventory
   - Search for "levitation"
   - Get a Novice Levitation Staff
   - Spawn a mob (cow, pig, etc.)
   - Right-click the mob with the staff
   - **Expected**: Mob should levitate with particles!

---

## 🛠️ Step 8: Development Workflow

### File Structure Understanding:
```
src/main/java/com/levitationstaff/mod/
├── LevitationStaffMod.java          # Main mod class
├── item/
│   ├── StaffType.java               # Staff configurations
│   └── LevitationStaff.java         # Staff item logic
├── capability/                      # Levitation state management
├── init/                           # Registration classes
├── network/                        # Client-server communication
├── client/                         # Client-side only code
└── server/                         # Server-side event handling
```

### Resource Files:
```
src/main/resources/
├── META-INF/mods.toml              # Mod metadata
├── assets/levitationstaff/         # Client-side resources
│   ├── models/item/                # Item models
│   ├── particles/                  # Particle definitions
│   ├── sounds.json                 # Sound registry
│   └── lang/en_us.json            # Localization
└── data/levitationstaff/          # Server-side data
    └── recipes/                    # Crafting recipes
```

### Development Tips:
1. **Hot Reload**: Use IntelliJ's "Recompile" (Ctrl+Shift+F9) for quick testing
2. **Debugging**: Set breakpoints and use Debug mode
3. **Logs**: Check IntelliJ console for mod loading issues
4. **Clean Builds**: Run `./gradlew clean build` when things get weird

---

## 🔍 Step 9: Adding Textures and Sounds

The mod is currently missing actual texture and sound files. Here's how to add them:

### Item Textures:
1. Create 16x16 PNG files for each staff:
   ```
   src/main/resources/assets/levitationstaff/textures/item/
   ├── novice_levitation_staff.png
   ├── apprentice_levitation_staff.png
   ├── adept_levitation_staff.png
   ├── expert_levitation_staff.png
   └── master_levitation_staff.png
   ```

### Sound Files:
1. Add OGG audio files:
   ```
   src/main/resources/assets/levitationstaff/sounds/
   ├── magic/
   │   ├── staff_cast1.ogg
   │   ├── staff_cast2.ogg
   │   ├── staff_cast3.ogg
   │   ├── staff_fail1.ogg
   │   ├── staff_fail2.ogg
   │   ├── replenish1.ogg
   │   └── replenish2.ogg
   └── ambient/
       └── magical_hum.ogg
   ```

### Resource Tools:
- **Blockbench**: For 3D models (optional)
- **GIMP/Photoshop**: For textures
- **Audacity**: For sound editing

---

## 🚨 Troubleshooting Common Issues

### Issue: "Module not specified"
**Solution**: 
1. **File** → **Project Structure** → **Modules**
2. Remove and re-import the module
3. **Gradle** → **Refresh**

### Issue: "Could not find or load main class"
**Solution**:
1. Check JDK version is 17
2. **Build** → **Clean Project**
3. **Build** → **Rebuild Project**

### Issue: "Cannot resolve symbol" errors
**Solution**:
1. **File** → **Invalidate Caches and Restart**
2. **Gradle** → **Refresh**
3. Check `build.gradle` dependencies

### Issue: Minecraft won't launch
**Solution**:
1. Check run configuration VM options
2. Ensure enough RAM allocated
3. Check mod loading logs for errors

### Issue: Mod not loading in game
**Solution**:
1. Check `mods.toml` syntax
2. Verify mod ID matches everywhere
3. Check main mod class `@Mod` annotation

---

## 🔄 Step 10: Building for Release

### Create Distribution JAR:
```bash
./gradlew build
```

### JAR Location:
```
build/libs/levitationstaff-1.0.0.jar
```

### Test the JAR:
1. Copy JAR to `.minecraft/mods/` folder
2. Launch Minecraft with Forge
3. Verify mod loads correctly

---

## 📚 Additional Resources

### Documentation:
- [Minecraft Forge Documentation](https://docs.minecraftforge.net/)
- [Forge Community Wiki](https://forge.gemwire.uk/)
- [ModdingByKaupenjoe Tutorials](https://www.youtube.com/c/ModdingByKaupenjoe)

### Tools:
- [MCreator](https://mcreator.net/) - Visual mod maker
- [Blockbench](https://blockbench.net/) - 3D model editor
- [Mappings](https://parchmentmc.org/) - Better deobfuscation

### Community:
- [MinecraftForge Discord](https://discord.gg/UvedJ9m)
- [r/feedthebeast](https://reddit.com/r/feedthebeast)
- [Minecraft Modding Community](https://discord.gg/EP4GnPp)

---

## ✅ Setup Complete!

You now have a fully functional Minecraft mod development environment! 

### Next Steps:
1. ✅ Environment is ready
2. 🎨 Add custom textures and sounds
3. 🧪 Test all mod features
4. 🐛 Debug and refine
5. 📦 Build and distribute

**Happy Modding!** 🚀 