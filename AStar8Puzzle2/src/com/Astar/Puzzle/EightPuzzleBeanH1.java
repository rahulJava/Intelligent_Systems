package com.Astar.Puzzle;

import java.util.ArrayList;

/**
 * The class performance the movements of the 8 puzzle game while at the same time generating the child nodes for each move.
 * The best move for each level is calculated on the basis of the Misplaced Tiles concept where the number of misplaced tiles 
 * of the current puzzle state and the goal state are compared.
 */
public class EightPuzzleBeanH1 extends EightPuzzleBean implements Comparable<EightPuzzleBeanH1> {

	public EightPuzzleBeanH1(int[][] array, int level) {
		int lengthOfArray = array.length;
		this.stateOfPuzzle = new int[lengthOfArray][lengthOfArray];
		for (int i = 0; i < lengthOfArray; i++) {
			for (int j = 0; j < lengthOfArray; j++) {
				this.stateOfPuzzle[i][j] = array[i][j];
			}
		}
		this.level = level;
		this.aStarDistance = calculateMisplacedTilesDistance() + level;
	}

	/**
	 * Below function calculates the Missing Tiles distance(heuristic value) for each
	 * state or node. I.e the sum of the distances of the tiles from their goal
	 * positions
	 */
	private int calculateMisplacedTilesDistance() {
		int count = 0;
		int lengthOfArray = EightPuzzle.goalState.length;
		for(int i=0; i < lengthOfArray; i++) {
			for(int j = 0 ; j < lengthOfArray; j++) {
				if (this.stateOfPuzzle[i][j] == 0) {
					continue;
				}
				if(this.stateOfPuzzle[i][j] != EightPuzzle.goalState[i][j]) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Generates child nodes by searching for '0' which is the free space and moving it in all possible directions.
	 */
	public ArrayList<EightPuzzleBeanH1> generatePossibleStates(EightPuzzleBeanH1 parentNode) {
		ArrayList<EightPuzzleBeanH1> childNodes = new ArrayList<EightPuzzleBeanH1>();

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (parentNode.stateOfPuzzle[row][column] == 0) {
					//Up
					if (column - 1 >= 0) {
						int[][] a = new int[3][3];
						for (int row1 = 0; row1 < 3; row1++) {
							for (int column1 = 0; column1 < 3; column1++) {
								a[row1][column1] = parentNode.stateOfPuzzle[row1][column1];
							}
						}
						a = EightPuzzleUtil.movePiece(a, row, column, row, column - 1);
						addChildNodesToList(parentNode, childNodes, a);
					}
					//Down
					if (column + 1 < 3) {
						int[][] a = new int[3][3];
						for (int row2 = 0; row2 < 3; row2++) {
							for (int column2 = 0; column2 < 3; column2++) {
								a[row2][column2] = parentNode.stateOfPuzzle[row2][column2];
							}
						}
						a = EightPuzzleUtil.movePiece(a, row, column, row, column + 1);
						addChildNodesToList(parentNode, childNodes, a);
					}
					//Left
					if (row - 1 >= 0) {
						int[][] a = new int[3][3];
						for (int row3 = 0; row3 < 3; row3++) {
							for (int column3 = 0; column3 < 3; column3++) {
								a[row3][column3] = parentNode.stateOfPuzzle[row3][column3];
							}
						}
						a = EightPuzzleUtil.movePiece(a, row, column, row - 1, column);
						addChildNodesToList(parentNode, childNodes, a);
					}
					//Right
					if (row + 1 < 3) {
						int[][] a = new int[3][3];
						for (int row4 = 0; row4 < 3; row4++) {
							for (int column4 = 0; column4 < 3; column4++) {
								a[row4][column4] = parentNode.stateOfPuzzle[row4][column4];
							}
						}
						a = EightPuzzleUtil.movePiece(a, row, column, row + 1, column);
						addChildNodesToList(parentNode, childNodes, a);
					}
				}
			}
		}
		return childNodes;
	}
	
	/**
	 * For every move of the tiles, g(n) i.e. the current level of the puzzle is incremented by one for every expansion
	 */
	public void addChildNodesToList(EightPuzzleBeanH1 parentNode, ArrayList<EightPuzzleBeanH1> childNodes, int[][] a) {
		EightPuzzleBeanH1 childNode = new EightPuzzleBeanH1(a, parentNode.level + 1);
		childNodes.add(childNode);
	}
	
	/**
	 * The comparator determines the order in which the elements are accessed in the PriorotyQueue 
	 * which in this case is based on the Misplaced Tiles
	 */
	@Override
	public int compareTo(EightPuzzleBeanH1 EightPuzzleBean) {
		return this.aStarDistance > EightPuzzleBean.aStarDistance ? 1 : this.aStarDistance < EightPuzzleBean.aStarDistance ? -1 : 0;
	}
}