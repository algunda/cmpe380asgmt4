package cmpe380asgmt4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class cmpe380asgmt4 {

	public static void main(String[] args) {
		scheduler partA=new scheduler("Qooqle_Projects_8_6_1477962811.78.txt");
		scheduler partB=new scheduler("Qooqle_Projects_10_30_1477962181.44.txt");
		scheduler partC=new scheduler("Qooqle_Projects_52_150_1477962917.86.txt"); 
		scheduler partD=new scheduler("Qooqle_Projects_500_1500_1477962966.94.txt"); 
		try{
			PrintWriter w = new PrintWriter(new File("Output.txt"));
			w.write("Answers to Lab 4 by Sam Codrington & Aidan Gunda");
			w.write("\nFile 1\n" + partA.genResult());
			w.write("\nFile 2\n" + partB.genResult());
			w.write("\nFile 3\n" + partC.genResult());
			w.write("\nFile 4\n" + partD.genResult());
			w.close();
		}
		catch  (FileNotFoundException ex) {
			System.out.println("File not found!");
			ex.printStackTrace();
		}
	}//end main
}//end classbody