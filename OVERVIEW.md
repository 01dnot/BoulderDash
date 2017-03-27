# Oversikt

#Interfaces:

IBDObject -> IBDMovingObject -> IBDKillable

IBDObject:					The main interface that all game-objects must implement. 
										Its most important functionality is the step-method.


IBDMovingObject:	The main interface that all objects that move in the must implement.
										Its most important functionality is the prepareMove-method, it prepares the move
										of an object, and get executed at the next step. Inherit from IBDObject.


IBDKillable:			An interface that all object that can be killed must implement. 
										An object that can be killed can always move. Inherit from IBDMovingObject.



#Abstract-classes:


AbstractBDObject -> AbstractBDMovingObject -> AbstractBDFKillingObject -> AbstractBDFallingObject



AbstractBDObject:			Implements the common features of all game-object. 


AbstractBDMovingObject:		Implements AbstractBDObject and the common features of all movable game-objects.


AbstractBDKillingObject:	Implements AbstractBDMovingObject and the feature of killing.


AbstractBDFallingObject:	Implements AvstractBDKillingObject and the logic for falling. Used in such as diamond and rocks.

		
The abstract classes contains most of the logic for all objects. An object can then override or add new methods to become unique. 
The classes is put in a hierarchy so that an object must move to kill, and must be able kill if it can fall, and so on. This reduces the workload of making new object, 
being able to implement from the methods and field variables from the abstract classes.



#GameObjects/Tiles:

BDRock:			-Inherit from AbstractBDFallingObject. Can fall and kill player and bugs.

BDSand: 			Inherit from AbstractBDObject. Sand doesnt do anything. Disappears when the player walks over it.

BDWall:			Inherit from AbstractBDObject. Wall blocks the path. Does nothing.

BDEmpty:			Inherit from AbstractBDObject. BDEmpty is an empty tile. Does nothing

BDDiamond:		Inherit from AbstractBDFallingObject. Can be picked up by player, fall and kill. 

BDBug:				Inherit from AbstractBDKillingObject and implements the interface IBDKillable. Can kill the player if player and bug is in the same tile, get killed by rocks and move. 
							If a bug is killed, it explodes into diamonds.

BDPlayer:		Inherit from AbstractBDMovingObject and implements the interface of IBDKillable. Can be killed by bug or falling objects, move and pick up diamonds.


#Adding a new object
To add a new object you must create a class that implement one of the abstract BD classes or extends existing objects.
You can then add features by overriding/creating new methods and field variables.
Remember to add the character of the class in BDMap in the method makeObject().

#The step method

Each object have a step()-method that gets called by BDMap. This updates the objects and their positions. Works as the framerate in the game.

#The diamond falling feature
- If falling, a timeout start lasting 3 steps before fall starts.
- It checks that the falling object isnt on the lowest possible row.
- Then it start falling if the tile under itself is either BDEmpty, or a killable object.

#Animations
The animations images is kept inside the objects. The image of an object gets updated in every step, and that creates the animation. 

#Sounds
The sounds implemented in the objects as an optional, and gets played of through the prepareMove methods.

#Assets credits
Credits to Czirkos Zoltan for the sound. [source](https://bitbucket.org/czirkoszoltan/gdash) 

Credits to Peter Broadribb for the sprites from the original Boulder Dash game. [source](http://codeincomplete.com/posts/javascript-boulderdash/graphics.pdf)
