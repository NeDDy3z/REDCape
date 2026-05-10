# RED:Cape - User Manual
- Author: Erik Vaněk
- Semester project at CTU FEE - Software Engineering in the PJV course

## Table of Contents
1. [Introduction](#introduction)
2. [Game Installation](#game-installation)
3. [Game Objective](#game-objective)
4. [How to Play](#how-to-play)
    - [Controls](#controls)
    - [Game Mechanics](#game-mechanics)
    - [Items](#items)
    - [Saving & Loading](#saving--loading)
5. [Game Modes & Console Options](#game-modes--console-options)
6. [Level Building Guide](#level-building-guide)
    - [Map Format](#map-format)
    - [Tile Legend](#tile-legend)
    - [Map Construction Rules](#map-construction-rules)
7. [Credits](#credits)

---

## Introduction
RED:Cape is a 2D top-down adventure game inspired by the Little Red Riding Hood fairy tale. As Little Red Riding Hood, you must navigate through a dangerous forest while being pursued by a wolf, collecting items that will help you survive and ultimately escape.


## Game Installation
The game can be run directly from the console with various launch options _(see [Game Modes & Console Options](#game-modes--console-options) section_


## Game Objective
- **Survive** by managing your health and collecting resources
- **Gather the key blade and key ring** to construct the key needed to unlock the exit
- **Escape the forest** by unlocking the exit door
- **Avoid being eaten** by the wolf

## How to Play
### Controls
- **Movement:** WASD keys for movement in eight directions
- **Interact/Pick up:** Stand over an item to pick it up automatically
- **Item Usage:** Number keys 1-5 to use items based on inventory position
- **Save Game:** Walk on camp fire to save your progress

### Game Mechanics
#### Health System
- Player has a health system that can be replenished by collecting food items
- Getting hit by wolves reduces health
- Death occurs when health reaches zero

#### Movement
- Basic movement in eight directions
- Speed can be temporarily increased with potions

#### Enemies
- Wolf patrols the forest
- Contact with wolves causes damage to the player
- Must be avoided to survive

### Items
#### Key Items
- **Key Blade (`b`):** One of two parts needed to construct the escape key
- **Key Ring (`r`):** The second part needed to construct the escape key
- **Key (`k`):** Assembled automatically when both parts are collected - used to escape the forest

#### Consumables
- **Food (`f`):** Restores player health
- **Speed Potion (`p`):** Temporarily increases movement speed
- **Camp Fire (`c`):** Save point (single-use)

### Saving & Loading
- Save your progress by interacting with a camp fire
- Each camp fire can only be used once
- Multiple camp fires are scattered throughout the level
- The game automatically loads your last save when starting
- The game is saved in your systems local storage - _(see [technical documentation](technical_documentation.md) for more details)_

---

## Game Modes & Console Options
Launch the game with these command-line options:
- `--debug`: Shows hitboxes for debugging
- `--log`: Enables console logging
- `--savelog`: Saves logs to a file (requires `--log` to be enabled)
- `--godmode`: Activates god mode for the player

## Level Building Guide
### Map Format
- Maps are 128 x 128 tiles in size
- Each tile must be separated by ", " (comma and space)
- No extra characters should be present at the beginning or end of lines
- Map edges must be marked with 'T' (the map builder will handle edge cases)

### Tile Legend
| Symbol | Description           |
|--------|-----------------------|
| X      | Invisible blockade    |
| -      | Grass                 |
| T      | Tree                  |
| D      | Door/Exit             |
| S      | Player spawn point    |
| W      | Wolf spawn point      |
| F      | Food item             |
| P      | Potion                |
| C      | Campfire (checkpoint) |
| B      | Key blade             |
| R      | Key ring              |
| K      | Key                   |

### Map Construction Rules
1. **Tile Separation:** All tiles must be separated with ", " (comma followed by a space)
2. **Line Formatting:** Lines should not have any leading or trailing characters
3. **Map Boundaries:** The map edges must use the 'T' tile
4. **Size:** All maps must have **128 x 128 tiles !!**
5. **Essential Elements:** Each map must include:
   - A border of trees (T)
   - One player spawn point (s)
   - One wolf spawn point (w)
   - One exit door (d)
   - Key components (b, r) or a complete key (k)

Example (simplified 5x5 section):
```plain
T, T, T, T, T
T, -, s, D, T
T, -, -, -, T
T, w, -, K, T
T, T, T, T, T
```

## Credits
- Erik Vaněk - Game Design and Development
- CTU FEE - Software Engineering Course (PJV subject)
- Summer semester - 2025

---

## Legal
- This game is a work of fiction and any resemblance to actual persons, living or dead, or actual events is purely coincidental.
- Do not redistribute or modify the game without permission from the author.
- All rights reserved.