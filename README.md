# Stick Hero Game

Welcome to the Stick Hero Game, where you step into the shoes of a skilled ninja and test your agility, timing, and strategic thinking as you navigate a dynamically generated world.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [How to Play](#how-to-play)
- [Game Controls](#game-controls)
- [Features](#features)
- [Game Rules](#game-rules)
- [Design Patterns](#design-patterns)
- [Testing](#testing)
- [Multithreading](#multithreading)
- [License](#license)
- [Authors](#authors)

## Prerequisites

To run the Stick Hero Game, you need the following:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) installed on your machine.
- [JavaFX library](https://openjfx.io/) (included in JavaFX SDK or can be added as a separate library).

## Installation

1. Clonethe Stick Hero Game repository to your local machine.
    ```bash
    git clone https://github.com/Vikranth3140/StickHero.git
    ```
2. Ensure that you have the required Java Development Kit (JDK) installed.
3. Open the project in your preferred Java Integrated Development Environment (IDE).
4. Set up the JavaFX library in your IDE (if not included).
5. Run the `HelloController` class to start the game.

## How to Play

1. Launch the game by running the application.
2. Use the `SPACEBAR` to extend the stick.
3. Release the `SPACEBAR` to drop the stick.
4. Press the `SPACEBAR` to move the character upside down.
5. Collect cherries to increase your score and use them as currency for self-revival.
6. Cross the gap between platforms by extending the stick.
7. Avoid obstacles and aim for the highest score.

## Game Controls

- **Stick Extension:** `SPACEBAR`
- **Ninja Flip:** Use `SPACEBAR` while moving
- **Revive Ninja:** Spend 10 cherries after defeat

## Features

- **Stick Extension:** Press the `SPACEBAR` key to extend your stick and bridge the gaps between platforms. Timing is crucial!
- **Ninja Flip:** Use `SPACEBAR` while moving to execute a nimble flip, avoiding deadly shurikens and adding a stylish touch to your traversal.
- **Cherry Collection:** Collect cherries to increase your score and accumulate currency for self-revival.
- **Self-Revival:** After facing defeat, revive your ninja by spending 10 cherries.
- **Dynamic Stick Extension:** Controlled by the player.
- **Character Movement and Obstacle Avoidance:** Adds challenge and excitement.
- **Obstacle Generation:** With random widths and gaps for unpredictability.
- **Real-Time Scoring System:** Keep track of your progress.
- **Character Upside-Down Movement:** For added difficulty.

## Game Rules

- The player must extend the stick to cross the gap between platforms.
- The stick must be long enough to reach the next platform.
- If the character falls down, press the `SPACEBAR` to go to the end.
- Colliding with obstacles ends the game.
- Collecting cherries increases the player's score.
- The character can move upside down for added challenge.

## Design Patterns

- **Singleton Pattern:** Implemented in the Block class to ensure a single instance, optimizing resource usage and promoting consistent behavior.
- **Flyweight Pattern:** Utilized in the Audio class for efficient management of audio resources, enhancing overall performance.

## Testing

- **JUnit Test:** A comprehensive JUnit test validates the functionality of the score loading feature, ensuring robust performance.

## Multithreading

- **Dynamic Movement:** Employing multithreading in the GameController class, two threads work concurrently. One thread moves objects backward, enhancing the game's dynamism. The other thread manages the revival process.

## License

This project is licensed under the [MIT License](LICENSE).

## Authors

- [Vikranth Udandarao](https://github.com/Vikranth3140)
- [Anushka Korlapati](https://github.com/anushka-korlapati)
