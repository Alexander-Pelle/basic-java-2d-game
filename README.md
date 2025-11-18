# Java 2D Game - Player vs AI Coin Collection

A basic 2D tile-based game built with Java Swing where you race against an intelligent AI opponent (Traveler) to collect coins before time runs out, while avoiding randomly moving enemies.

## Game Overview

This is a real-time strategy game where:
- **You (Player)** control a character using arrow keys to collect coins
- **The Traveler (AI)** autonomously collects coins using intelligent pathfinding
- **Enemies** roam the board randomly - if they touch you, the game ends immediately
- **Goal**: Collect more coins than the Traveler before the timer reaches zero!

### Winner Determination
- **Traveler Wins**: If the traveler has more points than you OR if you collide with an enemy
- **Player Wins**: If you have more points than the traveler when time runs out

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Windows OS (for batch scripts)

### Compilation and Execution

**Using the provided batch scripts (Windows):**

1. **Compile the project:**
```bash
compile.bat
```

2. **Run the game:**
```bash
run.bat
```

**Manual compilation and execution:**

1. **Compile:**
```bash
javac -d . -sourcepath src src/App.java
```

2. **Run:**
```bash
java App
```

## Features

### 1. Player Controls
- **Arrow Keys**: Move your character up, down, left, and right
- Grid-based movement system
- Boundary detection to prevent moving off the board

### 2. AI Traveler with BFS Pathfinding
The Traveler is an intelligent AI opponent that uses **Breadth-First Search (BFS)** algorithm to navigate the game board:

#### BFS Implementation Details:
- **Nearest Coin Detection**: Uses Manhattan distance to identify the closest coin
- **Optimal Path Finding**: BFS guarantees the shortest path to the target coin
- **Path Reconstruction**: Backtracks from target to find the immediate next step
- **Grid Validation**: Ensures all moves stay within board boundaries
- **Fallback Strategy**: If BFS fails, uses simple heuristic movement

#### Algorithm Flow:
1. Every 15 game ticks, the Traveler evaluates all coins on the board
2. Calculates Manhattan distance to each coin and selects the nearest one
3. Runs BFS from current position to target coin position:
   - Explores all 4 directions (up, right, down, left)
   - Maintains visited set to avoid cycles
   - Uses queue for level-order traversal
   - Tracks path using a `cameFrom` HashMap
4. Reconstructs the shortest path and moves one step toward the coin
5. If the Traveler reaches a coin, it collects it and gains 100 points

**Code Reference**: See `Traveler.java` lines 120-169 for the complete BFS implementation

### 3. Enemy System
- **Random Movement**: Enemies move unpredictably across the board
- **Collision Detection**: Contact with an enemy ends the game immediately
- **Slower Movement**: Enemies move every 20 ticks (slower than player but faster than traveler)
- **Multiple Threats**: 3 enemies spawn at random positions

### 4. Coin Collection Mechanics
- Coins spawn at random positions across the board
- **Player Score**: +100 points per coin collected
- **Traveler Score**: +100 points per coin collected
- **Auto-Respawn**: When a coin is collected, a new one immediately spawns elsewhere
- **Optimized Collision**: Uses HashMap for O(1) coin collision detection instead of O(n) iteration

### 5. Game Systems
- **Countdown Timer**: Race against the clock!
- **Score Display**: Real-time tracking of both player and traveler scores
- **Game Over Screen**: Displays the winner when time expires or collision occurs
- **Checkered Background**: Classic game board aesthetic

## Project Structure

```
java_2d_game-master/
├── src/
│   ├── App.java                    # Main entry point
│   ├── entities/
│   │   ├── Entity.java             # Base entity class
│   │   ├── Player.java             # Player character (arrow key controls)
│   │   ├── Traveler.java           # AI opponent with BFS pathfinding
│   │   └── Enemy.java              # Randomly moving hostile entities
│   ├── game/
│   │   ├── Coin.java               # Collectible coin objects
│   │   └── Clock.java              # Game timer system
│   ├── ui/
│   │   ├── Board.java              # Main game board and logic
│   │   ├── UI.java                 # UI rendering utilities
│   │   └── Window.java             # Game window setup
│   └── utils/
│       ├── Contstants.java         # Game constants (board size, tile size, etc.)
│       └── Logger.java             # Error logging utilities
├── images/                          # Game sprites and graphics
├── compile.bat                      # Windows compilation script
├── run.bat                          # Windows execution script
└── README.md                        # This file
```

## Technical Highlights

### Pathfinding Algorithm (BFS)
The Traveler's movement showcases a classic **Breadth-First Search** implementation:
- **Time Complexity**: O(V + E) where V is vertices (grid cells) and E is edges
- **Space Complexity**: O(V) for the queue and visited set
- **Optimality**: Guarantees shortest path in unweighted grid
- **Graph Representation**: Implicit 2D grid with 4-directional connectivity

### Performance Optimizations
- **HashMap Collision Detection**: O(1) lookup for coin collection vs O(n) iteration
- **Timer-Based Updates**: Fixed tick rate using Swing Timer
- **Efficient Rendering**: Only redraws on state changes

### Design Patterns
- **Entity System**: Inheritance-based entity hierarchy (Entity → Player/Enemy/Traveler)
- **Observer Pattern**: KeyListener for player input
- **Model-View Separation**: Game logic (Board) separate from rendering (UI)

## Gameplay Strategy Tips

1. **Stay Alert**: Keep an eye on enemy positions - they move randomly!
2. **Plan Ahead**: The Traveler is smart - you need to be strategic
3. **Risk vs Reward**: Sometimes it's worth taking a risky path for a coin cluster
4. **Watch the Clock**: Manage your time effectively
5. **Defensive Play**: If you're ahead in score, focus on avoiding enemies

## Customization

Want to modify the game? Check out `utils/Contstants.java` for easy configuration:
- Board dimensions (ROWS, COLUMNS)
- Tile size
- Number of coins
- Game colors
- Timer duration

## License

This is an educational project. Feel free to use and modify for learning purposes.

---

**Built with Java Swing | Featuring BFS Pathfinding AI | Educational Game Development Project**

