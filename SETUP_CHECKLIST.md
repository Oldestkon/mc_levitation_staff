# 🚀 Quick Setup Checklist

Use this checklist to verify your Levitation Staff mod development environment is properly set up.

## ✅ Prerequisites Checklist

- [ ] **Java 17** installed and accessible via command line
  ```bash
  java -version    # Should show 17.x.x
  javac -version   # Should show 17.x.x
  ```

- [ ] **IntelliJ IDEA** Community Edition installed
  - [ ] Minecraft Development plugin installed
  - [ ] Gradle plugin enabled

- [ ] **Git** installed (optional but recommended)

## ✅ Project Setup Checklist

- [ ] **Project files** copied/cloned to development directory
- [ ] **File structure** is correct:
  ```
  LevitationStaff/
  ├── build.gradle ✓
  ├── settings.gradle ✓
  ├── gradlew / gradlew.bat ✓
  ├── src/main/java/ ✓
  └── src/main/resources/ ✓
  ```

## ✅ Initial Build Checklist

- [ ] **Run setup script** (optional):
  - Windows: `setup.bat`
  - Linux/macOS: `chmod +x setup.sh && ./setup.sh`

- [ ] **Manual setup** (if not using script):
  - [ ] `./gradlew genIntellijRuns` completed successfully
  - [ ] `./gradlew genEclipseRuns` completed successfully
  - [ ] `./gradlew build` completed successfully
  - [ ] JAR file exists: `build/libs/levitationstaff-1.0.0.jar`

## ✅ IDE Configuration Checklist

- [ ] **Import project** into IntelliJ IDEA
  - [ ] Open `build.gradle` as project
  - [ ] Use Gradle wrapper
  - [ ] Enable auto-import

- [ ] **Gradle sync** completed without errors
- [ ] **Dependencies downloaded** (may take 5-15 minutes first time)
- [ ] **No red underlines** in Java files
- [ ] **Run configurations** exist:
  - [ ] `runClient` configuration
  - [ ] `runServer` configuration

## ✅ Testing Checklist

- [ ] **Client run** test:
  - [ ] Select `runClient` configuration
  - [ ] Click Run button
  - [ ] Minecraft launches with Forge
  - [ ] Mod loads successfully (check console logs)
  - [ ] "Levitation Staff mod setup complete!" appears in logs

- [ ] **In-game verification**:
  - [ ] Creative mode works
  - [ ] Search "levitation" finds staff items
  - [ ] Spawn a mob (cow, pig, etc.)
  - [ ] Right-click mob with Novice staff
  - [ ] **Mob levitates with particles!** 🎉

- [ ] **Server run** test (optional):
  - [ ] Select `runServer` configuration  
  - [ ] Server starts without errors
  - [ ] Mod loads on server side

## ✅ Development Workflow Checklist

- [ ] **Hot reload** works (Ctrl+Shift+F9 in IntelliJ)
- [ ] **Debugging** can be enabled
- [ ] **Console logs** are visible and readable
- [ ] **Gradle tasks** can be run from IDE

## 🚨 Common Issues Checklist

If something isn't working, check these:

- [ ] **Java version** is exactly 17 (not 18, 19, etc.)
- [ ] **Internet connection** active (Gradle needs to download dependencies)
- [ ] **Antivirus/Firewall** not blocking Gradle downloads
- [ ] **IntelliJ Gradle JVM** set to JDK 17
- [ ] **Project encoding** set to UTF-8
- [ ] **Gradle cache** cleared if needed: `./gradlew clean`

## 🎯 Ready to Develop!

If all items above are checked ✅, you're ready to:

1. **Modify the code** and see changes immediately
2. **Add textures and sounds** to enhance the mod
3. **Test new features** with hot reload
4. **Build release JARs** with `./gradlew build`
5. **Share your mod** with the community!

---

### 🆘 Need Help?

If you're stuck on any step:

1. **Read the full guide**: `DEVELOPMENT_SETUP.md`
2. **Check the troubleshooting section** in the guide
3. **Search the issue** on Stack Overflow or Minecraft Forge forums
4. **Ask for help** on the MinecraftForge Discord

**Happy Modding!** ✨ 