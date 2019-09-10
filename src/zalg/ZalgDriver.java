package zalg;

public class ZalgDriver {

	public static void main(String[] args) {
		int count = naiveSearch("ABABABABC", 0, 2);
		System.out.printf("Count = %d", count);
	}
	
	private static int naiveSearch(String s, int indexToStartOn, int currentIndex) {
		int count = 0;
		
		if (s.charAt(indexToStartOn) == s.charAt(currentIndex) && currentIndex != s.length() - 1) {
			// we have a match
			count = naiveSearch(s, indexToStartOn+1, currentIndex+1) + 1;
		} else {
			// mismatch or end of string
		}
		return count;
	}

}
