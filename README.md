# OOP Course Exercise 2: Weighted Directed Graph Implementation With Shortest Path Algorithm On A Live Changing Graph Implementation

The following project represents a pseudo-AI for a vehicle routing problem.
The project is built from three parts:

1. Directed-weighted graph data structure, which uses Hash-map data structure to achieve O(1) complexity for searching and inputting specific nodes.

2. A game which is basically a vehicle routing problem. The user chooses which level they want to check (1 to 23), hits the ENTER button, and then gets the JSON file from the game server which contains all the relevant data about the graph like the number of nodes, number of edges, data of all the connections in the graph including each edge weight and distance, number of pokemon, number of allowed agents and the location, direction and weight of the pokemon at the beginning of the game. 
The main idea of the game is simple: in 1 minute all the received agents should be located and moved around the graph by the allowed directions of each edge and collect as many pokemon as possible, using the lowest number of moves as possible. 
All the game process is represented on the screen using JFrame extending GUI.

3. JUnit tests, to test (:drum:) the correctness of the build.

## Directed-weighted graph data structure

The graph implements several interfaces like `directed_weighted_graph`, `dw_graph_algorithms`, `edge_data`, `edge_location`, `geo_location`. 
The basic implementation of the graph is classic through using the Hash-map data structure to achieve O(1) complexity for searching and inputting specific nodes.
The Algorithm class implements very well-known algorithms like Dijkstra, DFS, and SCC algorithm mainly based on Tarjan's strongly connected components algorithm plus all the required interface algorithms like saving and loading the graph as a JSON file and a deep copying algorithm.
More info you can find in the project's wiki. 

## The pokemon catching game

A game that is basically a vehicle routing problem. The user chooses which level they want to check (1 to 23), hits the ENTER button, and then gets the JSON file from the game server which contains all the relevant data about the graph like the number of nodes, number of edges, data of all the connections in the graph including each edge weight and distance, number of pokemon, number of allowed agents and the location, direction and weight of the pokemon at the beginning of the game. 
The main idea of the game is simple: in 1 minute all the received agents should be located and moved around the graph by the allowed directions of each edge and collect as many pokemon as possible, using the lowest number of moves as possible. 
All the game process is represented on the screen using JFrame extending GUI.
More info you can find in the project's wiki. 

# Downloading and running

## Linux 

Please make sure that your JRE is 14 or higher. If not, simply install it by following the instructions in the following link: https://linuxhint.com/install_jdk_14_ubuntu/.

1. Open the terminal by pressing simultaneously ctrl+alt+t.
2. Copy the following to the terminal:

```
git clone https://github.com/porat-ah/assignment_2_oop.git 
```

3. Enter the project file using your terminal command line:

```
cd assignment_2_oop
```

4. Run the JAR file using your terminal command line:

```
java -jar Ex2.jar <YOUR ID NUMBER HERE WITHOUT THE BRACKETS> <LEVEL NUMBER YOU WANT TO RUN [1-23] WITHOUT THE BRACKETS>
```

5. Enjoy!

## Windows

Please make sure that your JRE is 14 or higher. If not, simply install it by following the instructions in the following link: https://java.tutorials24x7.com/blog/how-to-install-java-14-on-windows.

1. Open your command prompt (CMD).
2. Copy the following to the CMD:

```
git clone https://github.com/porat-ah/assignment_2_oop.git 
```

3. Enter the project file using your CMD command line:

```
cd assignment_2_oop
```

4. Run the JAR file using your CMD command line:

```
java -jar Ex2.jar <YOUR ID NUMBER HERE WITHOUT THE BRACKETS> <LEVEL NUMBER YOU WANT TO RUN [1-23] WITHOUT THE BRACKETS>
```

5. Enjoy!


