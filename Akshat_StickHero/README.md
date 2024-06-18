# Ninja Adventure Game

Welcome to the Stick-Hero Game, where you step into the shoes of a skilled ninja and test your agility, timing, and strategic thinking as you navigate a dynamically generated world.

## Prerequisites

To run the Stick Hero Game, you need the following:

- Java Development Kit (JDK) installed on your machine.
- JavaFX library (included in JavaFX SDK or can be added as a separate library).

## Objective

Your primary goal is to cross platforms by extending your stick using the `SPACE` key. Be cautious of randomly generated shurikens that can swiftly end your journey. Gather cherries to accumulate points and, more importantly, to use as currency for self-revival.

## Features

- **Stick Extension:** Press the `SPACE` key to extend your stick and bridge the gaps between platforms. Timing is crucial!
- **Ninja Flip:** Use `SPACE` while moving to execute a nimble flip, avoiding deadly shurikens and adding a stylish touch to your traversal.
- **Cherry Collection:** Collect cherries to increase your score and accumulate currency for self-revival.
- **Self-Revival:** After facing defeat, revive your ninja by spending 10 cherries. 

## Design Patterns

- **Singleton Pattern:** Implemented in the Block class to ensure a single instance, optimizing resource usage and promoting consistent behavior.
- **Flyweight Pattern:** Utilized in the Audio Class for efficient management of audio resources, enhancing overall performance.

## Testing

- **JUnit Test:** A comprehensive JUnit test validates the functionality of the score loading feature, ensuring robust performance.

## Multithreading

- **Dynamic Movement:** Employing multithreading in the GameController Class , two threads work concurrently. One thread moves objects backward, enhancing the game's dynamism. The other thread manages the revival process.

## Controls

- **Stick Extension:** `SPACE` key
- **Ninja Flip:** Use `SPACE` while moving
- **Revive Ninja:** Spend 10 cherries after defeat

## Credits

- Vikranth Udandarao
- Anushka Korlapati