# Part 1: Simple Supply Chain Model

## About
This repository contains the source code for the [Part 1 of the Supply Chain Simulation Tutorial](https://platform.amalgamasimulation.com/amalgama/SupplyChainTutorial/sc_tutorial_part_1.html).

The application simulates the functionality of a simple supply chain.
A set of experiments is run to find the optimal number of trucks to move cargo among warehouses and stores.

## How to build and run

1. Make sure that JDK-17+ and Maven 3.8.1+ are installed on your computer.
1. Clone the repository to your local machine.
1. Build the application: 

```
mvn clean package
```  

4. Start the console application: 

```
java -jar target/sc-tutorial-part-1-1.0.jar
```

This gets printed to the console:

```
Scenario                Trucks count    SL      Expenses        Expenses/SL
scenario                           1    0,27%   $ 26 040,00     $ 96 152,70
scenario                           2    1,02%   $ 52 008,66     $ 51 211,19
scenario                           3    2,23%   $ 77 859,98     $ 34 848,24
scenario                           4    5,35%   $ 103 458,21    $ 19 342,76
scenario                           5    91,08%  $ 119 586,46    $ 1 313,05
scenario                           6    99,93%  $ 127 026,46    $ 1 271,12
scenario                           7    100,00% $ 134 428,96    $ 1 344,29
scenario                           8    100,00% $ 141 806,46    $ 1 418,06
scenario                           9    100,00% $ 149 283,96    $ 1 492,84
scenario                          10    100,00% $ 156 686,46    $ 1 566,86
```
