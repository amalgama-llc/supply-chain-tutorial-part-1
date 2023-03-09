# Step 1: Simple Supply Chain Model

## About
This repository contains the source code for the [Step 1 in the Supply Chain Simulation Tutorial](https://platform.amalgamasimulation.com/amalgama/SupplyChainTutorial/platform_tutorial_step_1.html).

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
java -jar target/tutorial-step-1-1.0.jar
```

This gets printed to the console:

> Trucks count:	1	SL:	14,29%	Expenses:	\$ 70,00	Expenses/SL:	$ 4,90  
Trucks count:	2	SL:	28,57%	Expenses:	\$ 136,67	Expenses/SL:	$ 4,78  
Trucks count:	3	SL:	42,86%	Expenses:	\$ 200,77	Expenses/SL:	$ 4,68  
Trucks count:	4	SL:	57,14%	Expenses:	\$ 250,89	Expenses/SL:	$ 4,39  
Trucks count:	5	SL:	71,43%	Expenses:	\$ 298,25	Expenses/SL:	$ 4,18  
Trucks count:	6	SL:	75,00%	Expenses:	\$ 343,10	Expenses/SL:	$ 4,57  
Trucks count:	7	SL:	77,78%	Expenses:	\$ 383,47	Expenses/SL:	$ 4,93  
Trucks count:	8	SL:	80,00%	Expenses:	\$ 417,65	Expenses/SL:	$ 5,22  
Trucks count:	9	SL:	80,00%	Expenses:	\$ 448,86	Expenses/SL:	$ 5,61  
Trucks count:	10	SL:	80,00%	Expenses:	\$ 472,11	Expenses/SL:	$ 5,90  

