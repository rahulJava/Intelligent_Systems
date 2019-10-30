package com.Astar.Puzzle;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This class contains common utilities that are used by the app
 */

public class EightPuzzleUtil {

	/**
	 * This constant determines how long each heuristic technique will run based
	 * on the size of the Priority Queue
	 */
	public static final int THRESHOLD = 20000;

	/**
	 * Method prints the following variables per expanded node
	 */
	public static <T extends EightPuzzleBean> void printMoveStats(
			T EightPuzzleBean) {
		System.out.println(Arrays.deepToString(EightPuzzleBean.stateOfPuzzle)
				.replace("[", "").replace("], ", "\n").replace("[[", "")
				.replace("]]", "").replace(",", "\t"));
		System.out.println("\ng(n) ==> " + EightPuzzleBean.level);
		System.out.println("f(n) ==> " + EightPuzzleBean.aStarDistance + "\n");
	}

	//Common method that takes input from user. Contains sanity check for input.

	public static int[][] generate2dArrayFromUserInput(Scanner input) {
		int[][] puzzleInput = new int[3][3];
		for (int i = 0; i < puzzleInput.length; i++) {
			for (int j = 0; j < puzzleInput[i].length; j++) {
				puzzleInput[i][j] = input.nextInt();
				if (puzzleInput[i][j] < 0 || puzzleInput[i][j] / 9 > 0) {
					System.out.println("Invalid Entry. Try Again...");
					System.exit(0);
				}
			}
		}
		return puzzleInput;
	}

	// Moves the 0 element by swapping

	public static int[][] movePiece(int[][] currentPuzzlePlay, int row1,
			int column1, int row2, int column2) {
		int tmp = currentPuzzlePlay[row1][column1];
		currentPuzzlePlay[row1][column1] = currentPuzzlePlay[row2][column2];
		currentPuzzlePlay[row2][column2] = tmp;
		return currentPuzzlePlay;
	}
}
