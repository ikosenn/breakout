Breakout
========

This details the changes that I have made to my breakout game.
I don't have any extra dependencies.

Levels
======

I have three levels. To move the paddle use the arrow keys.


Level One
~~~~~~~~~

Level 1 just has red bricks with a single ball. Red bricks get destroyed with
a single bounce


Level Two
~~~~~~~~~
Level two has red and blue bricks and a faster ball. The red bricks get destroyed
with a single bounce while the blue bricks require two bounces

Level Three
~~~~~~~~~~~~

Level three has red and blue bricks with two balls.


Cheat codes
===========

+ To access level 2 press the key ``z`` while in the PlayingState
+ To access level 3 press the key ``x`` while in the PlayingState


Extra Features
==============

High Score
~~~~~~~~~~
The high score for a particular game is persisted using file storage. High scores 
are only updated at the GameOverState


Bricks with many bounces
~~~~~~~~~~~~~~~~~~~~~~~~
The game has two brick types. Red bricks and Blue bricks. The red bricks can be
destroyed with one bounce while the blue bricks require two bounces.


Powerups
~~~~~~~~
The game has a powerup that destroys all the blocks in a column.
You get powerups when your score is a power of 10 i.e when your score is 10, 20, 30.
The powerup is a red ball that will be on top of the paddle.

To activate the powerup press the key ``p``.

