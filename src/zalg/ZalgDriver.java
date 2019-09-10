package zalg;

public class ZalgDriver {

	public static void main(String[] args) {
		String s = "ABSBAJSLABSKDFBAABASBABSBAJSLABKABSBAK";
		
		for (int i = 0; i < s.length(); i++) {
			int count = naiveSearch(s, 0, i);
			System.out.printf("Index = %d || Count = %d%n", i, count);
		}
	}

	private static int naiveSearch(String s, int indexToStartOn, int currentIndex) {
		int count = 0;

		if (s.charAt(indexToStartOn) == s.charAt(currentIndex) && currentIndex != s.length() - 1) {
			// we have a match
			count = naiveSearch(s, indexToStartOn+1, currentIndex+1) + 1;
		} else if(s.charAt(indexToStartOn) == s.charAt(currentIndex) && currentIndex == s.length() - 1){
			// end of string & a match!
			count = 1;
		} else {
			// mismatch, no code needed
		}
		return count;
	}

}
