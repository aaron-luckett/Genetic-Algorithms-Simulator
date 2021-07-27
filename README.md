# Genetic-Algorithms-Simulator
A program that will simulate a genetic algorithm when applied to the travelling salesperson problem. It allows the user to pick between a range of selection and crossover methods. Also provides multiple ways of simulating to produce graphs.

The simulator is designed to apply genetic algorithms to the travelling salesperson problem. The focus of the GA is the selection and crossover methods that the GA uses. The simulator has three modes seen below. 

An individual simulation which run a selection crossover combination once and produces a line graph once finished. It also shows a visual solver displaying the best path found by the GA at that point in time. The graph and visual solver will appear in different windows. 

A simulation mode which runs a combination over a set number of iterations and also produces a line graph this time showing the average values at each generation. No visual solver appears for this.

The final mode is a full simulation, which will run all 15 combination over a set amount of iterations and produces a bar chart at the end to compare the best results found.

To use the simulator, you can run the Main class and from there the simulator will guide you through your options in the command terminal. You will be required to pick a simulation method, a selection and crossover method and whether to load a file containing city co-ordinates (There are 2 already supplied called paths.txt and DANTZ.txt).

It is also worth noting that this project was created and tested in IntelliJ with project SDK 11 and project language level of SDK default (11).

Unit tests are also included in the simulator.
