package com.Astar.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This is the class that triggers that 8 Puzzle problem and solves it using the A* algorithm1.
 */
public class EightPuzzle {
	
	//Goal State will be constant hence it will be loaded only one at class level
	public static int[][] goalState;
	public static PriorityQueue<EightPuzzleBeanH1> priorityQueueH1 = new PriorityQueue<EightPuzzleBeanH1>();
	public static PriorityQueue<EightPuzzleBeanH2> priorityQueueH2 = new PriorityQueue<EightPuzzleBeanH2>();
	public static ArrayList<EightPuzzleBeanH1> expandedNodesH1 = new ArrayList<EightPuzzleBeanH1>();
	public static ArrayList<EightPuzzleBeanH2> expandedNodesH2 = new ArrayList<EightPuzzleBeanH2>();

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int startState[][] = new int[3][3];
		goalState = new int[3][3];
		
		//Start state and goal state
		System.out.println("Enter the elements for the start state of the puzzle ==>");
		startState = EightPuzzleUtil.generate2dArrayFromUserInput(in);
		System.out.println("Enter the elements for the goal state of the puzzle ==>");
		goalState = EightPuzzleUtil.generate2dArrayFromUserInput(in);
		
		calculateBasedOnMisplacedTiles(startState);
		
		calculateBasedOnManhattanDistance(startState);
	}
	
	/**
	 * The method calculated the best possible moves in a 8 puzzle game based on the Misplaced Tiles
	 */
	private static void calculateBasedOnMisplacedTiles(int[][] startState) {
		System.out.println("Solving Puzzle using A* based on Misplaced Tiles...");
		long startTime = System.currentTimeMillis();
		//Level is 0 since we have only one node for now which is yet to be expanded
		EightPuzzleBeanH1 state = new EightPuzzleBeanH1(startState, 0);
		processPuzzlePlayH1(state);
		for (EightPuzzleBeanH1 gameStatesBean : expandedNodesH1) {
			EightPuzzleUtil.printMoveStats(gameStatesBean);
		}
		//This prevents the application from running for a very long duration for a difficult puzzle
		if(priorityQueueH1.size() >= EightPuzzleUtil.THRESHOLD) {
			System.out.println("Application is performing beyond set threshold value and will be exiting");
			System.out.println("To increase threshold value, modify the EightPuzzleUtil.THRESHOLD variable");
			System.out.println("NOTE: This will increase the time needed for solution calculation");
		} else {
			System.out.println("Total number of expanded nodes for Misplaced Tiles ==> " + 	 expandedNodesH1.size());
			System.out.println("Total number generated nodes for Misplaced Tiles ==> " + 	(expandedNodesH1.size() + priorityQueueH1.size()));
		}
		long endTime = System.currentTimeMillis();
		// Calculate Time Taken for Total Execution
		System.out.println("Time Taken for execution (milliseconds) for Misplaced Tiles ==> " + (endTime - startTime));
	}
	
	/**
	 * The method calculated the best possible moves in a 8 puzzle game based on the Manhattan Distance
	 * @param startState The current State of the puzzle
	 */
	private static void calculateBasedOnManhattanDistance(int[][] startState) {
		System.out.println("Solving Puzzle using A* based on Manhattan Distance...");
		long startTime = System.currentTimeMillis();
		//Level is 0 since we have only one node for now which is yet to be expanded
		EightPuzzleBeanH2 state = new EightPuzzleBeanH2(startState, 0);
		processPuzzlePlayH2(state);
		for (EightPuzzleBeanH2 gameStatesBean : expandedNodesH2) {
			EightPuzzleUtil.printMoveStats(gameStatesBean);
		}
		//This prevents the application from running for a very long duration for a difficult puzzle
		if(priorityQueueH2.size() >= EightPuzzleUtil.THRESHOLD) {
			System.out.println("Application is performing beyond set threshold value and will be exiting");
			System.out.println("To increase threshold value, modify the EightPuzzleUtil.THRESHOLD variable");
			System.out.println("NOTE: This will increase the time needed for solution calculation");
		} else {
			System.out.println("Total number of expanded nodes for Manhattan Distance ==> " + 	 expandedNodesH2.size());
			System.out.println("Total number generated nodes for Manhattan Distance ==> " + 	(expandedNodesH2.size() + priorityQueueH2.size()));
		}
		long endTime = System.currentTimeMillis();
		// Calculate Time Taken for Total Execution
		System.out.println("Time Taken for execution (milliseconds) for Manhattan Distance ==> " + (endTime - startTime));
	}
	
	/**
	 * Processes the current state of the Puzzle. Takes the current move as the argument and adds it to the PriorityQueue
	 */
	public static void processPuzzlePlayH1(EightPuzzleBeanH1 move) {
		priorityQueueH1.add(move);
		ArrayList<EightPuzzleBeanH1> childNodesList = new ArrayList<EightPuzzleBeanH1>();
		do {
			boolean isNodeVisited;
			//Poll function retrieves and removes the head of this queue
			EightPuzzleBeanH1 currentPuzzlePlay = priorityQueueH1.poll();
			//Once removed it is added to the expandedNodes queue to avoid duplicate processing
			expandedNodesH1.add(currentPuzzlePlay);
			//Keep checking if goal state has been reached by comparing every element position with the goal state
			if (Arrays.deepEquals(currentPuzzlePlay.stateOfPuzzle, goalState)) {
				break;
			}
			childNodesList = currentPuzzlePlay.generatePossibleStates(currentPuzzlePlay);
			//Check if expanded node is already visited
			for (EightPuzzleBeanH1 childNode : childNodesList) {
				isNodeVisited = false;
				for (EightPuzzleBeanH1 expandedNode : expandedNodesH1) {
					if (Arrays.deepEquals(childNode.stateOfPuzzle, expandedNode.stateOfPuzzle)) {
						isNodeVisited = true;
					}
				}
				if (isNodeVisited) {
					continue;
				}
				priorityQueueH1.add(childNode);
			}
		} while(!priorityQueueH1.isEmpty() && priorityQueueH1.size()<=EightPuzzleUtil.THRESHOLD);
	}
	
	/**
	 * Processes the current state of the Puzzle. Takes the current move as the argument and adds it to the PriorityQueue
	 */
	public static void processPuzzlePlayH2(EightPuzzleBeanH2 move) {
		priorityQueueH2.add(move);
		ArrayList<EightPuzzleBeanH2> childNodesList = new ArrayList<EightPuzzleBeanH2>();
		do {
			boolean isNodeVisited;
			//Poll function retrieves and removes the head of this queue
			EightPuzzleBeanH2 currentPuzzlePlay = priorityQueueH2.poll();
			//Once removed it is added to the expandedNodes queue to avoid duplicate processing
			expandedNodesH2.add(currentPuzzlePlay);
			//Keep checking if goal state has been reached by comparing every element position with the goal state
			if (Arrays.deepEquals(currentPuzzlePlay.stateOfPuzzle, goalState)) {
				break;
			}
			childNodesList = currentPuzzlePlay.generatePossibleStates(currentPuzzlePlay);
			//Check if expanded node is already visited
			for (EightPuzzleBeanH2 childNode : childNodesList) {
				isNodeVisited = false;
				for (EightPuzzleBeanH2 expandedNode : expandedNodesH2) {
					if (Arrays.deepEquals(childNode.stateOfPuzzle, expandedNode.stateOfPuzzle)) {
						isNodeVisited = true;
					}
				}
				if (isNodeVisited) {
					continue;
				}
				priorityQueueH2.add(childNode);
			}
		} while(!priorityQueueH2.isEmpty() && priorityQueueH2.size()<=EightPuzzleUtil.THRESHOLD);
	}
}