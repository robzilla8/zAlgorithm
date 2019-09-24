package globalAlignment;

import java.util.ArrayList;

public class NeedlemanMatrix {
	private ArrayList<ArrayList<WunschCell>> matrix = new ArrayList<ArrayList<WunschCell>>();
	private String s;
	private String t;
	private int deltaMatch;
	private int deltaMismatch;
	private int deltaInsertionOrDeletion;
	NeedlemanMatrix(String s, String t, int deltaMatch, int deltaMismatch, int deltaInsertionOrDeletion) {
		// Initialize Matrix with indeces(i,j) where i represents an index in string S and J an index in String T
		// Using <= because there should be a value representing the empty string at index (0,0)
		this.s = "-"+s;
		this.t = "-"+t;
		this.deltaMatch = deltaMatch;
		this.deltaMismatch = deltaMismatch;
		this.deltaInsertionOrDeletion = deltaInsertionOrDeletion;
		for (int j = 0; j <= t.length(); j++) {
			ArrayList<WunschCell> wunschRow = new ArrayList<WunschCell>();
			for (int i = 0; i <= s.length(); i++) {
				WunschCell w = new WunschCell();
				wunschRow.add(w);
			}
			matrix.add(wunschRow);
		}
		//printMatrix();
		buildMatrix();
		printMatrix();
	}

	public void buildMatrix() {
		buildFirstRowAndColumn();
		for (int i = 1; i < matrix.size(); i++) {
			dynamicFillMatrix(i);
		}
	}

	private void buildFirstRowAndColumn() {
		// First value is always 0
		matrix.get(0).get(0).updateScore(0);

		// update the scores in the first row
		// optimal scores for the first row and column are always insertions or deletions as that
		// is the only way to reach these cells
		for (int i = 1; i < matrix.get(0).size(); i++) {
			matrix.get(0).get(i).updateScore(i * deltaInsertionOrDeletion);
			// adds parent wunsch cell to get this optimal score
			matrix.get(0).get(i).addDeletionParent(matrix.get(0).get(i-1));
		}

		// update the scores in the first column
		for (int i = 1; i < matrix.size(); i++) {
			matrix.get(i).get(0).updateScore(i * deltaInsertionOrDeletion);
			// adds parent wunsch cell to get the optimal score
			matrix.get(i).get(0).addInsertionParent(matrix.get(i - 1).get(0));
		}

	}

	private void dynamicFillMatrix(int row) {
		for (int i = 1; i < matrix.get(row).size(); i++) {
			int delta = 0;
			int max = 0;
			if (t.charAt(row) == s.charAt(i)) {
				//System.out.printf("t at %d matches s at %d %c=%c delta = %d%n", row, i, t.charAt(row), s.charAt(i), deltaMatch);
				delta = deltaMatch;
			} else {
				delta = deltaMismatch;
			}
			// Candidate score for this cell given a match or a mismatch
			int matchScore = matrix.get(row - 1).get(i - 1).getScore() + delta;
			
			max = matchScore;
			// Candidate score for this cell given an insertion
			int insertionScore = matrix.get(row - 1).get(i).getScore() + deltaInsertionOrDeletion;
			if (max < insertionScore) {
				max = insertionScore;
			}
			
			// Candidate score for this cell given a deletion
			int deletionScore = matrix.get(row).get(i - 1).getScore() + deltaInsertionOrDeletion;
			if (max < deletionScore) {
				max = deletionScore;
			}
			
			matrix.get(row).get(i).updateScore(max);
			if (matchScore == max) {
				matrix.get(row).get(i).addMatchParent(matrix.get(row - 1).get(i - 1));
			}
			
			if (insertionScore == max) {
				matrix.get(row).get(i).addInsertionParent(matrix.get(row - 1).get(i));
			}
			
			if (deletionScore == max) {
				matrix.get(row).get(i).addDeletionParent(matrix.get(row).get(i-1));
			}
		}
	}

	public void printMatrix() {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				System.out.printf("%d ", matrix.get(i).get(j).getScore());
			}
			System.out.println();
		}
	}
	
	private void depthFirstWunsch(WunschCell w) {
		ArrayList<WunschCell> optimalPath = new ArrayList<WunschCell>();
	}
	

}
