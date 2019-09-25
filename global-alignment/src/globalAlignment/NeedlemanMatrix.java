package globalAlignment;

import java.util.ArrayList;

public class NeedlemanMatrix {
	private ArrayList<ArrayList<WunschCell>> matrix = new ArrayList<ArrayList<WunschCell>>();
	private String s;
	private String t;
	private int deltaMatch;
	private int deltaMismatch;
	private int deltaInsertionOrDeletion;
	private boolean printOneOptimalMatch = false;
	ArrayList<WunschCell> optimalPath = new ArrayList<WunschCell>();
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
		depthFirstWunsch(matrix.get(matrix.size() - 1).get(matrix.get(0).size() - 1));
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
			int max;
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
				//System.out.printf("(Mis)match score for cell %d,%d = %d, adding parent%n", row, i, matchScore);
			}
			
			if (insertionScore == max) {
				matrix.get(row).get(i).addInsertionParent(matrix.get(row - 1).get(i));
				//System.out.printf("Insertion score for cell %d,%d = %d, adding parent%n", row, i, insertionScore);
			}
			
			if (deletionScore == max) {
				matrix.get(row).get(i).addDeletionParent(matrix.get(row).get(i-1));
				//System.out.printf("Deletion score for cell %d,%d = %d, adding parent%n", row, i, deletionScore);
			}
		}
	}

	public void printMatrix() {
		for (int i = 0; i < s.length(); i++) {
			System.out.printf("  %c ", s.charAt(i));
		}
		System.out.println();
		for (int i = 0; i < matrix.size(); i++) {
			System.out.print(t.charAt(i) + " ");
			for (int j = 0; j < matrix.get(i).size(); j++) {
				String intVal =  new Integer(matrix.get(i).get(j).getScore()).toString();
				System.out.print(intVal);
				for (int p = 4 - intVal.length(); p > 0; p--) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	private void depthFirstWunsch(WunschCell w) {
		optimalPath.add(0, w);
		if(w == matrix.get(0).get(0)) {
			printOptimalPath(optimalPath);
			optimalPath.remove(0);
			return;
		}
		if (w.getMatch()) {
			w.setCellInfo("(Mis)match");
			depthFirstWunsch(w.getMatchParent());
		}
		if (w.getInsertion()) {
			w.setCellInfo("Insertion");
			depthFirstWunsch(w.getInsertionParent());
		}
		if (w.getDeletion()) {
			w.setCellInfo("Deletion");
			depthFirstWunsch(w.getDeletionParent());
		}
		optimalPath.remove(0);
	}
	
	private void printOptimalPath(ArrayList<WunschCell> path) {
		
		System.out.printf("%n%n-------Possible best alignment---------------%n");
		for (int i = 1; i < optimalPath.size(); i++) {
			System.out.printf("%s | ", path.get(i).getCellInfo());
		}
		int sCounter = 0;
		System.out.printf("%nString S:  ");
		for (int i = 1; i < path.size(); i++) {
			switch (path.get(i).getCellInfo()) {
			case "(Mis)match":
			case "Deletion":
				System.out.printf("%c ", s.charAt(sCounter+1));
				sCounter++;
				break;
			case "Insertion":
				System.out.printf("- ");
				break;
			default:
				System.err.printf("%nCell info = %s", path.get(i).getCellInfo());
				System.err.printf("%nSomething has gone terribly wrong, Abort, Abort");
				System.exit(1);
			}
		}
		System.out.println();
		int tCounter = 0;
		System.out.printf("String T:  ");
		for (int i = 1; i < path.size(); i++) {
			
			switch (path.get(i).getCellInfo()) {
			case "(Mis)match":
			case "Insertion":
				System.out.printf("%c ", t.charAt(tCounter+1));
				tCounter++;
				break;
			case "Deletion":
				System.out.printf("- ");
				break;
			default:
				System.err.printf("%nCell info = %s", path.get(i).getCellInfo());
				System.err.printf("%nSomething has gone terribly wrong, Abort, Abort");
				System.exit(1);
			}
		}
		int score = path.get(path.size() - 1).getScore();
		System.out.printf("%nScore: %d%n", score);
		if (printOneOptimalMatch) {
			System.out.printf("%n%nConfigured to only print one optimal alignment%nSet global boolean \"printOneOptimalMatch\" to false to see all optimal matches.%n");
			System.exit(0);
		}
	}
}