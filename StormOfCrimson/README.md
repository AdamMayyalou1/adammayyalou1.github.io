<h2> ReadMe for Homework 5 </h2>
<h3> Arthur & Adam </h3>

<h3> Structure </h3>

For this project we first analyzed, what can we modify and what can we not modify?
This simple question led us to many of our design choices with many records that hold and preserve
data throughout the state of the game. This design also allowed us to delegate work a lot better
as behaviors are extremely well compartmentalized. The largest design choice which we believe
makes our design distinct is our strict handling of the GameState using RuleKeeper as well as many
more uses of static methods to conserve the records.

<h3> Homework Questions </h3>

<h3> Overview: </h3>   

We wrote this code with assumptions that Card, Cell, and Deck will be all extended. Knowing that
there would be more abilities in the future allowed us to make very knowledgeable decisions
while we were building. For instance, InfluenceMask in the future could be extended to also
hold the "abilities" talked about in the assignment. This could then be further delegated to execute
abilities possible with a command pattern, allowing multiple abilities to be handled identically.
Not only did we deal with assumptions during this build, but as a coding duo we wanted to make sure
that our vision was aligned, as well as being able to code off of each other without holding each other
back. This is why we strictly adhered to records and behavior based static methods to modify these 
states. For both of us, we agreed that this would be the optimal solution to deal with the games
logic, while we code together.


<h3>Quick start: </h3>
As a Quick start to entering this codebase, I would first look at GameState and each of its fields.
This is the fastest way to understand the structure of the data as GameState holds all the key
data for user iteration. Next, I would look at RuleKeeper, Scoring, and GameRules. These are the
implementations of the instructions for the game. Last, I would look at BasicSanguineModel as well
as the sub-packages to get a better idea of how the packages work together to create the entire game.

<h3> Key components: </h3>
The three core components of this codebase are the RuleKeeper, the GameState, and
lastly the board. These three components work vertically together to hold the state of the entire
game, as well as modify the game from start to finish. 

1.  The Board holds a 2d list of Cells that are always instantiated as one of the three
implementations. We made sure to handle the Cell to Cell conversion within the Cell classes as
there was a linear behavior from Empty Cell -> Pawn Cell -> Card Cell.
2. The GameState is the integral record for the entire Game. The GameState holds all information
necessary to continue or start a new game. This allows games to start from the beginning, 
middle or end.
3. The RuleKeeper holds every Static Method necessary to alter the GameState from start to end.
This allows the GameState to be independent from the ruleset and the modification. For us, this
was an important design decision if either GameState was to be modified in the future, 
or RuleKeeper.

<h3> Key subcomponents: </h3>

1. Not directly subcomponents, but the RuleKeeper Class uses Scoring and GameRules and their static
methods for two very important uses. We separated the scoring mechanics of the game if possibly
in the future, there is a different mechanism. As well, GameRules validates the GameState before the
game is started. This allows for many strict class invariants that we will talk about later. 
The trifecta of Scoring, GameState, and GameRules fully controlling the GameState creates many
possibilities for what can come next.
2. The BaseCard as well as the InfluenceMask are a key subcomponent because they have room to evolve
with the game. This is because of two reasons: the simplicity of the BaseCard, and the extendability
of the InfluenceMask. We knew while working on this codebase that InfluenceMask would evolve as its
own behavior in future assignments, this led us to make the InfluenceMask separately from the 
Base Card.

<h3> Source organization: </h3>

sanguine/ <br>
    model/ <br>
        Card/ <br>
            Holds all the behaviors of a singular Card with an influence Mask. <br>
        Cell/ <br>
            Utilizes the Card package as well as pawnCounters to create the Card Classes which <br>
            transfer between each other. <br>
        Deck/ <br>
            Handles the mutable states of Deck and Hand while also working on loading the Deck <br>
            from the external file. The Deck and Hand classes communicate through the RuleKeeper <br>
            and a specific Record which reiterates both Classes into a Record to <br>
            iterate into a new state. <br>
        Game/ <br>
            This handles the GameState Record which is the storage of the state, as well as all <br>
            mutation and calculation around the state itself. This is the most crucial package with <br>
            explicit coordination with the game instructions and the functionality of the game. <br>
        SanguineModel: Model Interface for future Model types <br>
          \-> BasicSanguineModel: Basic Implementation connecting the Model to the three GameState <br>
                                    Modifiers. <br>
        Board: Board class which brings together the Cell class into a unified Grid. <br>
        PlayerId: ENUM to know which side player <br>
        Position: Simple Record for retaining positions <br>
    view/ <br>
        TextualView: Simple Interface for this and future implementations. <br>
        SanguineTextualView: CLI View for the Sanguine Game. <br>

<h1> Homework 6</h1>

<h3> Changes for Part 2 </h3>

1. Separated the SanguineModel from the ReadOnlySanguineModel.
2. Added the new necessary methods to ReadOnlySanguineModel for the view to operate correctly.
3. Implemented these into BasicSanguineModel
4. Added a toString method to CellClass to facilitate the view visualizing the Influence Mask.

<h3> Keybinds for SanguineGame </h3>

1. "P" pass
2. "Enter" confirm move
3. Clicking with cursor/mouse

<h3> New classes </h3>

1. ViewCommands: Publisher Subscriber for the relationship between the View and the Controller. 
Allows a feedback loop between the two where the User, through the view, chooses which command to
try. This is then sent to the controller where it is described and processed correctly according to
the game. As well, the controller modifies the state of the model. Last, the controller sends back
the new associated model to the view to update the GUI. This Allows the controller to be in control
of the model while the view is in control of both User Input and the GUI.
2. SanguineView & SanguineSwingView
The main Interface and class of the view. The Visuals of the SanguineSwingView is made up of
BoardPanel.java, HandPanel.java, and a HeaderLabel. These three components are able to fully
visualize the state of the game at any given state. Given a user input, SanguineSwingView is able
to process that input and correctly identify the move, cell, or card that the User associates that
area of the screen with. This information is then passed to the controller to modify the model.
The controller modifies the model and sends it back to be reprinted on the GUI through the view.
This continuous feedback loop means that the View is only capable of handling user input and 
displaying the information on the model.
3. HandPanel & BoardPanel
Both Panels (implementing JPanel) are very similar. They assess the different parts of the view
and are responsible for handling MouseClicks. This feedback is then given to the larger
SanguineSwingView. The HandPanel is responsible for: Displaying the Cards, Highlighting the current
chosen card, Displaying the information of each card with the correct InfluenceMask. The BoardPanel
is responsible for both the board itself, but also the row scores. Within these two subsections,
on the board, the BoardPanel is responsible for: Showing either empty, pawn, or card cells
correctly and Highlighting the current cell. On the Scoring, it is responsible for accurately
representing the score. As well as demonstrating the total score using the colored circles. These
classes work in tandem to take every mouse input and process that into the view where it then
helps the user play the game.
4. StubController
We first decided to make the required stubController, but as we continued to have fun with this
project, we couldn't wait to make a working version that works with the Model and the View. With
significant physical testing we made sure that the Controller is working up to par, as well as
satisfying the output stream of the necessary instructions on the homework.
5. (Extra Credit) Strategy Interface + Composite, ControlBoard, FillFirst, MaximizeRowScore, and Minimax Strategy
For step 4 of the assignment, we added a dedicated strategy interface that all future strategies
would implement. For the extra credit part of this implementation, the strategies we designed were
ControlBoard, Minimax, and Composite--with the composite strategy allowing for them to be combined
in various ways. These strategies can be found within the controller folder and are appropriately named
(MinimaxStrategy, etc). The tests for these classes can be found within the TestStrategies class,
located in the testController folder of the test folder.
6. BoardUtils
Board utils is a simple utils class that currently adds a method that produces a copy of the given board.
7. StrategyTieBreaker
StrategyTieBreaker is a class that contains the method, pickBest, which selects the best strategy of
the given list of moves. It does this by comparing each of the move within the list.
8. Move + MoveGenerator
Move is a record that holds information about a possible move that a player may make: the index
belonging to the card used in the move, the position of that move on the board, and the
GameState created by this move. The MoveGenerator, meanwhile, determines every legal move that
can be performed in a given GameState by simulating each move and determining if exceptions are
thrown.


How to run Jar:

To run the Jar included with our assignment, launch the Jar and include size of the board
the path to the decks for each player. You must then decide which kind of player that the
two players playing the game will be. If they are human, then simply type "human." If not,
type in the strategy that the machine player will utilize (example: fillfirst, maxrowscore).

Example: java -jar HW7-dev.jar 5 7 docs/example3.deck docs/example3.deck fillfirst human

Homework 7

ModelStatusListener: The ModelStatusListener is an interface that listens and responds
to the model status during the Sanguine game. Primarily utilized by the controller
and contains methods that respond to turns changing or the game ending.

SanguineController: Fully implemented the SanguineController class, which implements
the ModelStatusListener and ViewCommands to allow the player to interact with the 
Sanguine game, such as clicking on cards/cells or passing their turn.

Human and Machine Players: Implemented the Player interface, which represents
the two types of players that can play the Sanguine game. The Machine and Human
class implement this interface, and represent either a human player or a machine
player (which also takes in a strategy to use during the game).

Changes made:

Updated the Jar file to correspond with changes made in this assignment.

The SanguineGame method has been updated in correspondence to changes made in this
assignment--including taking in a board size, both player's decks, and what type of
players are playing the game; instead of just the singular deck in the last assignment.