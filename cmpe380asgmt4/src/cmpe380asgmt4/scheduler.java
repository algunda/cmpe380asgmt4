package cmpe380asgmt4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class scheduler {
	private String file;
	private int numweeks, numproj;
	ArrayList<project> projList=new ArrayList<project>();
	public scheduler(String filename){
		file=filename;
		readFile();
		//TODO dynamic
		//TODO genResult
	}
	private void readFile(){
		String line;

		try {
			Scanner sc = new Scanner(new File(file));
			numweeks= sc.nextInt();
			numproj= sc.nextInt();
			
			while (sc.hasNextInt()){
				int projnum= sc.nextInt();
				int start= sc.nextInt();
				int dur= sc.nextInt();
				int val= sc.nextInt();
				projList.add(new project(projnum,start,dur,val));
			}

		} catch (FileNotFoundException ex) {
			System.out.println("File not found!");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("IO Error");
			ex.printStackTrace();
		}
	}
	private class project{
		public int num, start, dur, val;
		project(int projectnumber, int starttime, int duration, int value){
			num=projectnumber;
			start=starttime;
			dur=duration;
			val=value;
		}
	}
	/**
	 * Testing function, that prints out list of projects to console
	 */
	public void printMe() {
		System.out.println("Printing all projects for file "+file);
		System.out.println("Contains "+numproj+ " projects over "+numweeks+" weeks");
		for (project p :projList){
			System.out.println("Proj #"+p.num+" starts on week "+p.start+" lasts "+p.dur+" weeks and is worth "+p.val);
		}
		
	}
}
