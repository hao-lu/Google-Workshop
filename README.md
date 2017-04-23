# Anagrams
(./screenshots/anagrams.png)

Anagram: a word, phrase, or name formed by rearranging the letters of another, such as cinema, formed from iceman.

This is a game allows the player to think of other words that can be compromised of letters from the selected word with an additional letter appended. 

	Given word: times
	Answers: misted, theism, kismet, merits, mister, miters, remits, timers, smites, stymie

# Scarne's Dice
(/screenshots/scarne-dice.gif)
## Game rules
Roll 1: Player loses all points for that turn and loses their turn

Roll 2-6: Player can choose to roll or hold

	- Roll: Their turn score will be accumulated as long as the player does not roll a 1
	- Hold: The player ends their turn and their turn score is added to their total score

The first player to 100 is the winner

# Touring Musician
This is an example of the Travelling Salesman Problem. The problem is to find the most efficient path for the band's tour between cities and returning back to the start city.
The solution for this problem will use a circular LinkedList. Each node will contain a point (x,y) coordinate on the map, a reference to the next node in the LinkedList and the a reference to the previous node in the LinkedList. Circular LinkedList differ from Single LinkedList because they have a previous node reference. Circular LinkedList differ from Double LinkedList because HEAD points to the TAIL and the TAIL points to the HEAD creating a cycle.

## 3 Heuristics
(/screenshots/touring-musician.png)
### Insert Beginning
This heuristics will simply insert every new city the HEAD or the beginning of the the list.

### Insert Nearest
This heuristics will calculate the distance between the new node and all the nodes and find the minimum distance and insert to the right of that node. 

### Insert Smallest
This heuristics will calculate the smallest increase in the total distance trip and add that node to that position in the list. 


