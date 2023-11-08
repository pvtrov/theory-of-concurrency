# The Dining Philosophers problem 

This is one of the classic problems in concurrency theory. The basic formulation of the problem is as follows:

* N philosophers are seated around a circular table.
* A fork is placed between each pair of adjacent philosophers (there are a total of N forks).

* Each philosopher continually follows the pattern of "thinking - eating, thinking - eating - ...". Each of these stages (thinking and eating) is finite.
* To eat, a philosopher must pick up both adjacent forks.

My solution includes six different approaches:
* **Naive Solution** (with potential deadlock)
* **Starvation-Prone Solution**
* **AsymmetricSolution**
* **StochasticSolution**
* **SolutionWithArbiter**
* **DiningRoomSolution**

For each of these solutions, I conducted experiments, measuring various average values. 
The full report on these experiments is available in the Polish language [here].


[here]: https://github.com/pvtrov/theory-of-concurrency/blob/main/TheDiningPhilosophersProblem/report01.pdf
