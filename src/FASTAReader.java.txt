package zalg;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class FASTAReader {

	PrintStream fout; 
	BufferedReader fin;


	FASTAReader(String filename)
	{
		try{
			fout= new PrintStream ( new FileOutputStream(filename));
		}catch(IOException fo){
			System.out.println(fo); 
		}
	}

	FASTAReader(String filename, String in)
	{
		try{
			fin = new BufferedReader(new FileReader(filename));

		}catch(IOException fo){
			System.out.println(fo); 
		}
	}

	public void writer(String out)
	{

		fout.println(out);

	}
	public void writer(int out)
	{

		fout.println(out);

	}
	public void writer(char out)
	{

		fout.println(out);

	}
	public void writer(double out)
	{

		fout.println(out);

	}
	public void writer(float out)
	{

		fout.println(out);

	}
	public String reader(){
		StringBuffer sb = new StringBuffer();
		try{
			String s = fin.readLine();
			while (s != null) {
				// Look at first non whitespace character
				int counter = 0;
				while (s.charAt(counter) == ' ') {
					counter++;
				}
				if (s.charAt(counter) != '>') { // '>' is the description line which is not needed for generating data string
					sb.append(s);
				}
				s = fin.readLine();
			}
			return sb.toString();     
		}catch(IOException e){
			System.out.println("error reading from file " + e);
			return sb.toString();
			// return "error\t";
		}
	}
}
