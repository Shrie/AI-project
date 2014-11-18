AI-project
==========
COMPLETE

R1: PTTT shall implement the basics of Polar Tic-Tac-Toe as described above with
one significant restriction. The first move may appear anywhere on the board. After
the first move is made, future legal moves shall be restricted legal to be positions
adjacent to an existing X or O on the board. (Note that this restriction might be
relaxed at the end of the semester.) A legal move checker shall be included in the
implementation and only legal moves shall permitted to be played.

ACTIVE: HUNTER & CHAD

R2: PTTT shall incorporate a “win checker” using resolution (with unification). Thus,
to satisfy this requirement, a set of first-order rules shall be developed to characterize
what constitutes a winning state. These rules shall be used with the resolution win
checker to examine the current board state and prove whether or not that board state
contains a win. The predicate to be proven shall be denoted win(player), where
player ∈ {X, O}.

ACTIVE: MASON

R3: The computer-based player in PTTT shall implement a heuristic function that
evaluates the quality of a board state. An example heuristic function that might
be useful as a starting point can be found in Exercise 5.9 in the course textbook.
PTTT may include multiple heuristic functions. Should multiple heuristic functions
be included, the choices shall be provided as configuration options to the user. All
heuristic functions shall be described fully in the design document. The performance
of each heuristic function shall be reported in the final project report.

ACTIVE:

R4: PTTT shall include a classifier (e.g., naïve Bayes, decision tree, nearest neighbor)
to evaluate any state and predict whether or not that state will lead to a win or loss.
The classifier shall be used as an alternative heuristic function and shall be compared to
the heuristic function(s) developed in response to R3. The design of the classifier shall
be described in detail in the design document, including any parameter settings. The
results of comparing the classifier’s performance to the heuristic function(s) developed
in response to R3 shall be described fully in the final project report.

ACTIVE:

R5: PTTT shall include a temporal difference neural network to evaluate any state
and predict the expected outcome of the game from that state. The TD neural network
shall be used as an alternative heuristic function and shall be compared to the heuristic
functions developed in response to R3 and R4. The design of the TD neural network
shall be described in detail in the design document, including any parameter settings.
The results of comparing the neural networks’ performance to the heuristic function(s)
developed in response to R3 and R4 shall be described fully in the final project report.

ACTIVE:

R6: The computer-based player in PTTT shall examine neighboring states for each ply,
applying the heuristic function to those states. For a given ply, PTTT may restrict the
neighborhood to a subset of available next moves. Should this option be exercised, the
design and rationale for this restriction shall be described fully in the design document,
and the effects of the restriction shall be described fully in the final project report. If
this is a configuration option, the ability to select the neighbor shall be provided to
the user.

ACTIVE:

R7: The computer-based player in PTTT shall implement minimax search with and
without alpha-beta pruning to play the game from either the X or the O perspective.
Selecting whether or not to use alpha-beta pruning shall be offered to the user as a
configuration option. The effects of using or not using alpha-beta pruning, including
the effects of searching with various search depths, shall be described fully in the final
project report.

TIMELINE
----------------------------------------------------------------------------------------------------------------------------
R1: November 17

R2: November 17

R3: November 18

alpha-beta: November 25

R4: November 25

R5: November 25

R6: November 27

R7: November 29

Coding done: November 29

Design document done: December 1

Technical document done: December 3
