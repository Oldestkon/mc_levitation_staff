# Levitation Staff Mod

A magical Minecraft Forge mod that adds powerful levitation staffs to the game, allowing players to levitate mobs with varying degrees of power and range.

## Features

### 🪄 Five Staff Tiers
- **Novice Staff**: Basic levitation with 8-block range (100 magicka)
- **Apprentice Staff**: Improved range of 12 blocks (200 magicka)
- **Adept Staff**: First AOE staff with 16-block range and 4-block AOE radius (300 magicka)
- **Expert Staff**: Enhanced AOE with 20-block range (400 magicka)
- **Master Staff**: Ultimate power with 25-block range and strongest levitation (500 magicka)

### ⚡ Magicka System
- Each staff has a depletable magicka pool
- Casting levitation consumes magicka based on spell strength
- Visual magicka bar shows current/maximum capacity
- Replenish magicka using hot cauldrons with water and glowstone dust

### 🎯 Smart Targeting
- Visual targeting system shows cast viability
- Range, magicka, and mob size checks
- Different colored indicators:
  - **Green**: Ready to cast
  - **Red**: Out of range or insufficient magicka
  - **Yellow**: Target too heavy for current staff

### ✨ Particle Effects
- **Casting**: Spiraling magical particles around targets
- **Idle**: Gentle sparkles around levitating mobs
- **Replenishing**: Golden particles rising from cauldron

### 🎮 Animations
- **Successful Cast**: Smooth arm raising with magical tremor
- **Failed Cast**: Quick jerky motion
- **Levitating Mobs**: Circular limb rotation while floating

### 🔊 Sound Design
- Unique casting sounds with pitch variations
- Failure sound effects
- Magical replenishing audio
- Ambient magical humming for levitating entities

## Installation

1. Download and install Minecraft Forge 1.19.2 (version 43.3.0 or later)
2. Download the latest release of the Levitation Staff mod
3. Place the `.jar` file in your `mods` folder
4. Launch Minecraft with the Forge profile

## Crafting Recipes

### Novice Levitation Staff
```
  E    (E = Ender Pearl)
 S     (S = Stick)  
S
```

### Apprentice Levitation Staff
```
 E     (E = Eye of Ender)
 N     (N = Novice Staff)
 G     (G = Glowstone Dust)
```

### Adept Levitation Staff
```
GBG    (G = Gold Ingot, B = Blaze Rod)
 A     (A = Apprentice Staff)
 D     (D = Diamond)
```

### Expert Levitation Staff
```
ENE    (E = Emerald, N = Nether Star)
 A     (A = Adept Staff)
 C     (C = End Crystal)
```

### Master Levitation Staff
```
DSD    (D = Dragon Head, S = Shulker Shell)
NEN    (N = Netherite Ingot, E = Expert Staff)
 B     (B = Beacon)
```

## Usage

### Casting Levitation
1. Hold a levitation staff in your main or off hand
2. Look at a mob within range
3. Right-click to cast levitation
4. AOE staffs (Adept+) can target ground for area effect

### Replenishing Magicka
1. Place a cauldron over a heat source (fire, lava, magma block, campfire)
2. Fill the cauldron with water
3. Hold a levitation staff and have glowstone dust in inventory
4. Right-click the hot cauldron to replenish magicka

### Mob Limitations
- Larger mobs require stronger staffs
- Each staff tier can levitate progressively bigger creatures
- Levitated mobs lose movement control and float with rotating limbs

## Compatibility

- **Minecraft Version**: 1.19.2
- **Forge Version**: 43.3.0+
- **Client/Server**: Works on both sides
- **Modpacks**: Fully compatible with most modpacks

## Technical Details

### Staff Parameters
| Staff | Range | AOE | Magicka | Strength | Cost/Cast |
|-------|-------|-----|---------|----------|-----------|
| Novice | 8 | No | 100 | 1.0 | 20 |
| Apprentice | 12 | No | 200 | 1.5 | 30 |
| Adept | 16 | 4 blocks | 300 | 2.0 | 40 |
| Expert | 20 | 4 blocks | 400 | 2.5 | 50 |
| Master | 25 | 4 blocks | 500 | 3.0 | 60 |

### Performance
- Optimized particle system
- Efficient capability-based levitation tracking
- Client-side animations with server validation
- Minimal network traffic

## Contributing

Feel free to submit issues, feature requests, or pull requests to improve the mod!

## License

This mod is released under the MIT License.

---

*Created with magical programming powers* ✨


