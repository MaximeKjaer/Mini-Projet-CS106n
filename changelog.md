#Changelog#

2.11
- final version in my opinon
- change Number_iteration depending on d
- epsilon 1e-5 for all d<10, else 1e-4

27.10
- changed lot's of things. Was very optimistic yesterday I suppose :3
- it's all about fine tuning right now, parameters :
 - Epsilon
 - Number_iteration
 - how points are given
 - deviation when in create U*V

26.10
- approximately 100% correct result with a 150x20 matrix filled at 30%
 - Changed the way to consider an input : if U*V has a negative = don't take into account. => drastically improved results
 - Still have problem in if the 2 entries (the biggest ones) are different at the first / second decimal => which means nothing in fact :)
- changed the order of stuff to look like private / public  attributs, private / public  methods

## To change##
- Maybe too greedy (20 iterations)
- Need to implement Netflix stuff
- Revisit how points are granted (only the best result per line receive 1 point per iteration right now)

25.10
- Java 7 compatible
- Created automatized test to output to compare recommended output vs computed expected output
- Changed the way how P is computed (to fit the instructions)
- Still need to find a way to better it :)
 - absurd : don't consider if any input is out of initial possible range
 - don't consider if RMSE is too big

24.10
- UpdateU/Velem ok, OptimizeU/V ok. Recommend works quite fine. Still need to work on how U/V are generated, how I count the stuff.
- Need to implement Netflix data

22.10
- reworked createMatrix to finally fit myself and the instructions

20.10
- added rmse. reworked createMatrix to fit the forum recommendations, modified order of conditions in  multiplyMAtrix to fit the style of the rest of the code.

17.10
- added Recommendation.java. <strong>You need to update your sciper</strong>. slightly retouched the code
- started Part2.java => added multiplyMatrix. Updated Recommendation.java

16.10
- added createMatrix. Slightly updated matrixToString. Added comments + added some space. There should be a space when there's an operator.

15.10
- added Part1.java with matrixToString and isMAtrix. Missing createMatrix
