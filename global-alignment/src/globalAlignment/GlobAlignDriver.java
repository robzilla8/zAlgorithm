package globalAlignment;

import java.util.ArrayList;

public class GlobAlignDriver {

	public static void main(String[] args) {
		String file = "";
		int x = 0;
		int y = 0;
		int z = 0;
		
		if (args.length != 4) {
			System.err.printf("%n%nInsuffecient Command Line Arguments! Four needed!");
			System.exit(1);
		} else {
			file = args[0];
			x = Integer.parseInt(args[1]);
			System.out.printf("x = %d%n", x);
			y = Integer.parseInt(args[2]);
			System.out.printf("y = %d%n", y);
			z = Integer.parseInt(args[3]);
			System.out.printf("z = %d%n", z);
		}
		ArrayList<String> compareStrings = new FASTAReader(file, "in").reader();
		System.out.printf("Strings[0] = %s%nStrings[1] = %s%n%n", compareStrings.get(0), compareStrings.get(1));
		new NeedlemanMatrix(compareStrings.get(0), compareStrings.get(1), x, y, z);
		// new NeedlemanMatrix("GAC", "GTC", 1, -1, -1);
	}

}
