/**
 * 
 */
package Lab7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 
 */
public class GA_NQueenAlgo {

	public static final int POP_SIZE = 100;// Population size
	public static final double MUTATION_RATE = 0.03;
	public static final int MAX_ITERATIONS = 1000;
	List<Node> population = new ArrayList<Node>();
	Random rd = new Random();

	// initialize the individuals of the population
	public void initPopulation() {
		for (int i = 0; i < POP_SIZE; i++) {
			Node ni = new Node();
			ni.generateBoard();
			population.add(ni);
		}
	}

	public Node execute() {
		Node result = new Node();
		int k = 0;
		while (k++ < MAX_ITERATIONS) {
			List<Node> newPopulation = new ArrayList<Node>();
			for (int i = 0; i < POP_SIZE; i++) {
				Node x = getParentByRandomSelection();
                Node y = getParentByTournamentSelection();
                result = reproduce(x, y);
                if(rd.nextDouble()<MUTATION_RATE) {
                	mutate(result);
                	if(result.getH()==0)return result;
                }else {
                	newPopulation.add(result);
                }
			}
			population = newPopulation;
		}
		Collections.sort(population);
		return population.get(0);
	}

	// Mutate an individual by selecting a random Queen and
	// move it to a random row.
	public void mutate(Node node) {
		node.setRow(rd.nextInt(Node.N), rd.nextInt(Node.N));
	}

	// Crossover x and y to reproduce a child
	public Node reproduce(Node x, Node y) {
		Node child = new Node();
		child.generateBoard();
		int c = rd.nextInt(Node.N);
		for (int i = 0; i < c; i++) {
			child.setRow(i, x.getRow(i));
		}
		for (int i = 0; i < c; i++) {
			child.setRow(i, y.getRow(i));

		}
		return child;
	}

	// Select K individuals from the population at random and
	// select the best out of these to become a parent.
	public Node getParentByTournamentSelection() {
		int k = rd.nextInt(POP_SIZE);

		List<Node> tournamentParticipants = new ArrayList<Node>();
		for (int i = 0; i < k; i++) {
			tournamentParticipants.add(population.get(i));
		}
		Collections.sort(tournamentParticipants);
		return tournamentParticipants.get(0);
	}

	// Select a random parent from the population
	public Node getParentByRandomSelection() {
		// Enter your code here
		int randomIndex = rd.nextInt(POP_SIZE);
		return population.get(randomIndex);
	}

}
