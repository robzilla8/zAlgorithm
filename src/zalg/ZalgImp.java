package zalg;

import java.lang.String;//imports the string library for java, which is necessary in order to get the length of the string

public class ZalgImp {//a class which runs our implementation of the Z-algorithm.
	int zcount;//the Z-value for a specific string position.
	int leftValue;//the left value for a specific Z-box
	int rightValue;//the right value for a specific Z-box
	int[] zcounts;//the total number of Z-counts for a Z-box
	ZalgDriver driver = new ZalgDriver();
	
	public ZalgImp(String str) {//the constructor initializes all the values to zero, representing the beginning of any given string.
		zcounts = new int[str.length()];
		zcount=0;
		leftValue=0;
		rightValue=0;
		zcounts[0]=zcount;
		Zalg(str);
		int counter = 0;
		for (int z : zcounts) {
			System.out.printf("Counter = %d, Z = %d%n", counter, z);
			counter++;
		}
	}
	
	public void Zalg(String patternString) {//the actual class which controls our Z-values. 
		zcounts= new int[patternString.length()];
		if(patternString.charAt(0)==patternString.charAt(1)) {//this if statement takes into account the Z2 value, as well as accounts for if the string has multiple values that are similar.
			zcount+=1;
			zcount+=naiveSearch(patternString,1,2);
			zcounts[1]=zcount;
			leftValue=1;
			rightValue=zcount;
		}
		else {
			zcounts[1]=0;
		}
		for(int i=2;i<patternString.length();i++) {//iterates through the string, simply moving the current position we are looking for. 
			if(i>rightValue) {//case one of the Z-algorithm, the current position is outside of the Z-box. We check from the beginning till we find a new string.
				zcount=naiveSearch(patternString,1,i);
				zcounts[i]=zcount;
				if(zcount>0) {
					leftValue=i;
					rightValue=i+zcount;
				}
			}
			else if(i<=rightValue) {//case two of the Z-algorithm, broken up based upon if a previous Z-value is greater than or equal to another value.
				if(zcounts[i-leftValue]<(rightValue-i)) {//case 2.a of the Z-algorithm. If the new value is less than the length of the substring Beta. 
					zcounts[i]=zcounts[i-leftValue];
				}
				else if(zcounts[i-leftValue]>=(rightValue-i)) {
					zcount=naiveSearch(patternString,(rightValue-i),rightValue);
					zcounts[i]=zcount;
					leftValue=i;
					rightValue=(i+zcount+1);
				}
			}
		
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
