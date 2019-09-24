package globalAlignment;

public class GlobAlignDriver {

	public static void main(String[] args) {
		NeedlemanMatrix n = new NeedlemanMatrix("GCATGCU", "GATTACA", 1, -1, -1);
		//NeedlemanMatrix n = new NeedlemanMatrix("GG", "GATGGGGATCGGTCATGAAAAAAAGGGGGTTGGGGGTTTCCCTGCATTGACGGTGCAAAGTGACCCCTTGGCTTAGGCTACA", 1, -1, -1);
	}

}
