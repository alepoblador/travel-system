
README.txt

Programming Homework 3
Alessandra Poblador
acp2164
------------------------------------------------------------------------

FILES:

Graph.java
TravelSystem.java
City.java
Edge.java
test.java

------------------------------------------------------------------------

GRAPH.JAVA

I wrote this class to implement a graph data structure using:
- one ArrayList to hold cities (startsArray)
- one HashMap mapping city names to City objects (startsHash)
- one ArrayList of edges

The class contains several constructors and methods used to add
cities or edges to the ArrayLists or HashMaps.

ALGORITHMS

	 • getRandomCity()
Finds random position in ArrayList of cities and returns a random city.
Runtime: O(N)

	 • addCitiesFromFile( File f )
Scans through text file 3 lines at a time, using data to create
cities which are added to the graph.
Runtime: O(N) where N is number of lines in file

	 • assignConnections()
This method iterates through a HashMap of cities, and assigns a random
number of connections to each. The connections are stored in a second
HashMap held within each City object.
Runtime: O(N) where N is the number of cities in the HashMap

	 • countInOut()
Iterates through ArrayList of edges and increments in and out counts
for each city.
Runtime: O(N) where N is the number of edges

	 • checkIncoming()
Iterates through list of cities and, if a city has no incoming edges,
assigns a random connection.
Runtime: O(N) where N is the number of cities

------------------------------------------------------------------------

TRAVELSYSTEM.JAVA

I wrote this class to implement an instance of the map travel system.
This holds all the methods that run when items on the test menu are
chosen. It holds a Graph object, as well as a City designated as current.

ALGORITHMS

	 • loadTextFile()
This algorithm takes a file and creates a new graph. Then it runs 4
methods from the Graph class to fill the Graph.
Runtime: O(N) where N is the number of edges

	• loadGraph() and saveGraph()
These serialize or deserialize Graph objects from a file, mygraph.bin.

	• searchState()
This method searches for a key in the graph's HashMap and returns
the related City value as well as some info about that City.
Runtime: O(N) where N is the number of cities

	• searchCity()
Searches for a key in the graph's HashMap and returns info about it.
Runtime: O(1)

	• setCurrent() and printCurrent()
Assign or print the TravelSystem's current City, with some info.
Runtime: O(1)

	• haversine()
Math formula that determines GPS distance between two cities based on
GPS coordinates.
Runtime: constant

	• findClosest()
Creates an ArrayList holding distances from the current city, sorts
the ArrayList, and then returns the first n cities.
Runtime: O(N) where N is the number of cities

	• sortArrayList()
Bubble sorts an ArrayList. Used in findClosest()
Runtime: O(N^2) where N is the number of cities

	• findAdjacentBelow()
Sorts the current city's HashMap of connections and prints those
with weights below Y.
Runtime: O(N) where N is the number of connections from current

	• findShortestPath()
I wrote this algorithm, it finds a path to the destination based on
the lowest weight edge from one city to the next. (It's a greedy
algorithm.) As it visits a city, it pushes that city onto a stack.
That city's connections are then sorted in order of weight,
and the closest city that has not been visited becomes the next city 
pushed onto the stack. If all of a city's connections have been visited, 
then TWO cities are pushed from the stack and the algorithm continues.
Runtime: O(N) where N is the number of cities.

------------------------------------------------------------------------

CITY.JAVA

Implements a city which acts as a vertex on a graph.
Each city has an ArrayList which holds all of the cities which are
directly connected by an outward edge to that city.

ALGORITHMS

	• sortConnections()
Bubble sorts the ArrayList that holds each city's connections.
Runtime: O(N^2) where N is the number of connections. (max 8)

	• resetVisited()
Goes through all the connections in a city object and resets
their visited boolean to false.
Runtime: O(N) where N is the number of connections.

------------------------------------------------------------------------








