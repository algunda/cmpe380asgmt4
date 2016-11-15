package cmpe380asgmt4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class scheduler {

	private String file;
	private int numweeks, numproj;
	private int[][] filledTable;
	ArrayList<project> projList=new ArrayList<project>();

	public scheduler(String filename){
		file=filename;
		readFile();
		dynamic();
	}

	private void readFile(){
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
			sc.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found!");
			ex.printStackTrace();
		} 
	}

	private void dynamic(){
		int[][] val=new int [numweeks] [numproj];
		for (int x=0; x<numweeks; x++) {
			for(int y=0; y<numproj; y++) {
				project proj=projList.get(y);
				if (proj.end == (x + 1)){ //0th index of array represents 1st week etc.
					/*
					 * The next section of code will determine val[x][y] as follows.
					 * The value at val[x][y] will be the maximum of either the previous calculated value  or
					 * the value of the current project plus the value from before the start of 
					 * the current project.
					 */

					int pot;							//setting potential
					if (proj.start==1) 
						pot=proj.val; //if it starts in week 1, the value before will be 0
					else 
						pot=proj.val+val[proj.start-2][numproj-1]; 
					/*In the previous line we use the accumulated value at the last project because
					 *that will be the maximum of the week. Otherwise a project with a higher number than the current one
					 *that finished on that week won't be considered
					 */

					int prev;							//setting previous
					if (y==0)							//if it's the first project
						prev=val[x-1][numproj-1];		//check the last filled value of the last project
					else	
						prev=val[x][y-1];

					val[x][y]=max(pot,prev);			//take maximum of the two
				}

				else {
					if ((y==0) && (x==0)) 				//if it's the first cell
						val[x][y]=0;
					else if (y==0) 							//if it's in the first row
						val[x][y]=val[x-1][numproj-1];	//use the last filled value of the last project
					else 								
						val[x][y]=val[x][y-1]; 			//otherwise use the value immediately above
				}
			}
		}

		filledTable=val;
	}

	public String genResult(){
		//Find optimal projects
		ArrayList<project> best = new ArrayList<project>();
		int x = numweeks-1;		//start at the end of the array
		int y = numproj-1;
		int total=filledTable[x][y];
		while (filledTable[x][y] != 0){
			if (y==0){
				x--;
				y = numproj - 1;	
			}
			else if (filledTable[x][y] != filledTable[x][y-1]){
				best.add(projList.get(y));
				if (projList.get(y).start==1) //if you find the starting project
					break;
				x = projList.get(y).start - 2; //goes to index of the week before this project's start
				y = numproj - 1;
			}
			else
				y--;

		}
		//generate message listing projects
		String msg = "";
		for (project p : best)
			msg = p.printMe() + "\n" + msg;
		msg="The correct project plan will yield " + total + 
				" and will do the following projects:\n" + msg;
		return msg;
	}

	/**
	 * returns max of ints a & b
	 * @return
	 */
	private int max(int a, int b) {
		if (a>=b) return a;
		else return b;
	}

	private class project{
		public int num, start, val, end;
		project(int projectnumber, int starttime, int duration, int value){
			num = projectnumber;
			start = starttime;
			val = value;
			end = start + duration - 1;
		}

		public String printMe(){
			return "Project #"+num+" starts at beginning of week "+start+ 
					" and lasts until week "+end;
		}
	}
	/**
	 * Testing function, that prints out list of projects to console
	 */
	public void printMe() {
		System.out.println("Printing all projects for file "+file);
		System.out.println("Contains "+numproj+ " projects over "+numweeks+" weeks");
		for (project p :projList)
			System.out.println("Proj #"+p.num+" lasts from start of week "+p.start+
					" until end of week "+p.end+" and is worth "+p.val);

	}
}
