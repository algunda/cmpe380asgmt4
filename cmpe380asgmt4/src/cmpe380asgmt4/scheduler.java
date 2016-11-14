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
		int[][] filledTable=dynamic();
		genResult(filledTable);
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
	private int[][] dynamic(){
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
					int pot;
					if (proj.start==1) 
						pot=proj.val; //if it starts in week 1, the value before will be 0
					else 
						pot=proj.val+val[proj.start-2][numproj-1]; 
					/*In the previous line we use the start value of the last project because
					 *that will be the maximum of the week. Otherwise a project with a higher number that 
					 *finished on that week won't be considered
					 */
					int prev;
					if (y==0)							//if it's the first project
						prev=val[x-1][numproj-1];		//check the last filled value of the last project
					else	
						prev=val[x][y-1];
					
					val[x][y]=max(pot,prev);
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
		
		return val;
	}
	
	private void genResult(int[][] val){
		ArrayList<project> best=new ArrayList<project>();
		int x=numweeks-1;
		int y=numproj-1;
		while (x!=0 && y!=0){
			if (val[x][y] == val[x][y-1])
				y--;
			else {
				best.add(projList.get(x));
				
			}
		}
		//generate file listing projects
		
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
			num=projectnumber;
			start=starttime;
			val=value;
			end=start+duration-1;
		}
		public String printMe(){
			return "Project #"+num+" starts at beginning of week "+start+ 
					"and lasting until week "+end;
		}
	}
	/**
	 * Testing function, that prints out list of projects to console
	 */
	public void printMe() {
		System.out.println("Printing all projects for file "+file);
		System.out.println("Contains "+numproj+ " projects over "+numweeks+" weeks");
		for (project p :projList){
			System.out.println("Proj #"+p.num+" lasts from start of week "+p.start+" until end of week "+p.end+" and is worth "+p.val);
		}
		
	}
}
