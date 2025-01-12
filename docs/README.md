# LDTS_T02_G04 - Blackjack Royale

## Game Description 
Blackjack Royale is a text-based casino application where you can play a game selected from the main menu (more specifically Blackjack), place bets and interact with the cards. Depending on the outcome of a game round and the amount of tokens placed in a bet, your token balance will vary accordingly.

This project was developed by Sebastião Vizcaíno (up202305009), Tobias Blechschmidt (up202401393) and Rafael Rodrigues (up202303855) for LDTS 2024/25.

## IMPLEMENTED FEATURES

- **Main Menu** - When starting up the application, the user is directed to the Main Menu, from where they can select a game option or exit the casino.
- **Keyboard Control** - Keyboard inputs are received through the proper class and interpreted according to the current state of the application.
- **Betting and Token Management** - When starting a game, the player will have a default amount of tokens to start with, which he can then use to place bets at the beginning of a game round. According to the outcome of the game, the token balance can change.
- **Blackjack Game** - After placing a bet, the player is directed to the Blackjack game, which has working game logic and the Hit/Stay actions implemented.
- **Different Games/ Game Variations** - Different editions of the Blackjack game (Normal and XMAS edition) allow for more variety in gameplay and aesthetic, such as different betting logic and screen backgrounds for different games.

## PLANNED FEATURES

All the essential features were implemented, however, due to time constraints, we could not implement the username feature, which would be an initial input from the user when starting a game that would be displayed in the game table (instead of the default "Player").

## DESIGN

### General Structure
#### Problem in Context

After making the initial draft of the game, our main concerns were adapting it to a proper architecture and how the general structure of the game would turn out. Since we are using a GUI and states to distinguish between the menu and the game, we used specific patterns to improve the design of the project.

#### The Pattern:

The main pattern applied to our project was the **_MVC architectural pattern_**, to help manage the interactions with the GUI and separate the different "tasks" into proper packages/classes. Another important design pattern used is the **_State pattern_** , a behavioral pattern that, together with the MVC architecture, allowed us to create different controllers/viewers for the respective states.

#### Implementation:

In regard to the implementation, we created different packages for the different purposes needed for the game to work. We have classes primarily used to store data (model), classes responsible for the visual effects and drawing things on screen (viewer), classes that control the logic (controller), and since Blackjack is a very logic heavy game, we felt the need to create a logic module which has most of the "raw" logic needed for the game. Additionally, we also implemented different types of bet and points logic, which can be found in the strategy package.

![General Structure UML](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/UML%20Diagrams/GeneralStructure.drawio.png)

As for the states, they were divided in much the same way as the viewers and controllers, allowing the game to alter its behavior in a clear and efficient manner.
The general structure UML with the methods listed can be found [here](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/UML%20Diagrams/DetailedStructure.drawio.png).

#### Consequences:

Using these patterns for the final design allowed for:

- Clear difference between the Main Menu and the Game itself;
- Good organization of the code regarding the segregation of the different responsibilities;
- More flexibility in adding new features, such as a new game.

### GUI
#### Problem in Context:

In order to avoid strange code and depending directly on the raw Lanterna library, we decided to create our own GUI and Game Screen, importing only the functions needed from the Lanterna library. This way, by only adding useful methods to the interface, it doesn't have unused methods, ensuring compliance with the **Interface Segregation Principle**. 

#### The Pattern:

Since the GUI acts essentially as a simplified interface to acess the Lanterna library, the **_Facade pattern_** was applied, since only a small portion of the library's functionality was needed.

#### Implementation:

The following diagram shows how the GUI interface and the BlackjackScreen are being applied with the Facade pattern:

![GUI](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/UML%20Diagrams/GUI.drawio.png)

#### Consequences:

By using the GUI we implemented, it allowed for:

- Improved efficiency compared to importing the entire Lanterna library;
- Properly receiving input;
- Better organization of the methods related to the viewer and the screen;

### Blackjack Game and Logic
#### Problem in Context:

As mentioned before, the Blackjack game requires a lot of "raw" logic in order for it to work properly due to the various aspects of the game (Card values, win/lose conditions, player's possible actions, etc). As such, we decided that implementing all the logic directly in the Blackjack controller wouldn't suffice, and felt the need to create a separate logic module, which would then be accessed by the controller.

#### The Pattern:

We've applied the **_Mediator pattern_**. The Blackjack Controller acts as a mediator between various classes, coordinating interactions between the View, the Model and the Logic class. This is also kept consistent with the MVC architecture, mentioned in the General Structure subsection.

#### Implementation:

The following diagram roughly demonstrates the use of the Mediator pattern in our implementation:

![Blackjack Controller](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/UML%20Diagrams/Mini%20UML%20Controller.drawio.png)


#### Consequences:

By utilizing this approach, we were able to:

- Decouple the code, making it easier to maintain;
- Delegate concrete responsibilities to each component, which also aligns with the **Single Responsibility Principle**;

### Betting system
#### Problem in Context:

At first, we had some difficulty deciding on where to implement it, seeing as we wanted it to be separate from the game itself, in order to create better modularity. This way we would be able to apply different betting logic to the game (For example, Blackjack XMAS has different betting logic). Consequently, we decided to implement it as its separate logic module, much like the Blackjack logic.

#### The Pattern:

We applied the **_Strategy Pattern_**. Seeing as we wanted to create different betting rules and encapsulate them in different classes, this pattern seemed the most appropriate.

#### Implementation:

The following diagram demonstrates the use of the Strategy pattern in our implementation:

![Bet UML](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/UML%20Diagrams/BetUML.drawio.png)

#### Consequences:

The use of the Strategy Pattern allows for the following benefits:

- Alignment with the **Open-Closed Principle**, since new betting classes can be created without the need of modifying the existing ones, while also ensuring the Controller doesn't need modification regarding the bets;
- Better flexibility and extensibility of the code.

## Known code smells:

The following code smells are present in our current implementation:
- To handle Blackjack's internal "state" and behaviour, we are using conditions with the string attribute "STATE", that serves as a flag. We are aware this falls under **primitive obsession**, however, due to time constraints, simplicity of use and the relatively small scale of the project, we decided to keep this approach in;
- At first, the Controller class was doing too much, which violates the **Single Responsibility Principle**. To alleviate this, we created the Blackjack Logic module, to help separate part of the responsibilities regarding the logic;
- The betInput() method isn't an efficient way of receiving the Player's bet inputs. However, due to the **complexity** of changing this method and keeping the game running smoothly, we decided to leave it this way.

## Testing

- Coverage report screenshot

![Coverage Report](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/TestReports/Coverage.png)

- Mutation testing report screenshot

![Mutation Testing Report](https://github.com/FEUP-LDTS-2024/project-t02g04/blob/refactoring/docs/Images/TestReports/Pit%20Test%20Coverage%20Report.png)


## Self-evaluation

All members contributed to the project, dividing tasks and doing regular meet-ups to ensure everyone's vision of the project is aligned.

- Sebastião Vizcaíno: 30%
- Tobias Blechschimdt: 35%
- Rafael Rodrigues: 35%
