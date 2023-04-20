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


> Trucks count:   1       SL:     0,20%   Expenses:       $ 26 040,00     Expenses/SL:    $ 128 203,60  
Trucks count:   2       SL:     0,81%   Expenses:       $ 52 008,66     Expenses/SL:    $ 64 013,99  
Trucks count:   3       SL:     1,56%   Expenses:       $ 77 853,73     Expenses/SL:    $ 49 995,64  
Trucks count:   4       SL:     2,84%   Expenses:       $ 103 528,58    Expenses/SL:    $ 36 407,55  
Trucks count:   5       SL:     9,82%   Expenses:       $ 128 979,14    Expenses/SL:    $ 13 138,08  
Trucks count:   6       SL:     89,32%  Expenses:       $ 138 176,46    Expenses/SL:    $ 1 547,03  
Trucks count:   7       SL:     98,44%  Expenses:       $ 145 610,21    Expenses/SL:    $ 1 479,10  
Trucks count:   8       SL:     99,86%  Expenses:       $ 153 043,96    Expenses/SL:    $ 1 532,51  
Trucks count:   9       SL:     99,93%  Expenses:       $ 160 477,71    Expenses/SL:    $ 1 605,86  
Trucks count:   10      SL:     100,00% Expenses:       $ 167 923,96    Expenses/SL:    $ 1 679,24  
Trucks count:   11      SL:     100,00% Expenses:       $ 175 345,21    Expenses/SL:    $ 1 753,45  
Trucks count:   12      SL:     100,00% Expenses:       $ 182 785,21    Expenses/SL:    $ 1 827,85  
Trucks count:   13      SL:     100,00% Expenses:       $ 190 218,96    Expenses/SL:    $ 1 902,19  
Trucks count:   14      SL:     100,00% Expenses:       $ 197 658,96    Expenses/SL:    $ 1 976,59  
Trucks count:   15      SL:     100,00% Expenses:       $ 205 098,96    Expenses/SL:    $ 2 050,99  
