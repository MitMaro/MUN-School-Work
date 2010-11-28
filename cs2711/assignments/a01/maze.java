// COMP 2711 assignment 1, Fall 2009
// author: Tim Oram, oram, #########
/*
  COMP 2711 (Fall 2008), Assignment #1, Question #4b
          File Name: maze.java
       Student Name: Tim Oram
              MUN #: #########
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

class maze {

	// Some constants to help define the maze
	public static final int BLOCKED_CELL = 0;
	public static final int OPEN_CELL = 1;
	public static final int EXIT_CELL = 2;

	// the maze we run the algorithm on
	private int[][] working_maze;
	// holds the size of the maze
	private int maze_size;
	// set to true when an exit was found
	boolean exit_found;

	public static void main(String[] args) throws Exception{

		// lil' usage message
		if(args.length == 0){
			System.out.println("Usage: java maze file");
		}

		String line; // for reading the maze (ie. holds each line)
		int maze_size;
		int[] maze_row; // temp variable for holding a maze row
		int[][] maze; // holds the maze data
		int start_x; // the node on the maze to start
		int start_y;
		int row_counter = 0; // just a little counter for what row in the
		                     // maze we are in
		BufferedReader maze_file; // the file stream for reading the data
		String[] temp; // for use in spliting and parsing values

		// there are so many things that can go wrong with reading files
		try{
			// open the maze file
			maze_file = new BufferedReader(new FileReader(args[0]));
			// read and parse the maze size from the file
			maze_size = Integer.parseInt(maze_file.readLine().trim());
			// create the maze data structure
			maze = new int[maze_size][maze_size];
			// read, split and parse the start coordinates
			temp = maze_file.readLine().trim().split("\\s+");
			start_x = Integer.parseInt(temp[0]);
			start_y = Integer.parseInt(temp[1]);
			// for each remaining line in the maze file
			while( (line = maze_file.readLine()) != null ){
				// split the line into the maze nodes
				temp = line.trim().split("\\s+");
				// store each node into a array casting to an integer
				maze_row = new int[maze_size];
				for(int i = 0; i < maze_size; i++){
					maze_row[i] = Integer.parseInt(temp[i]);
				}
				// add the row to the maze
				maze[row_counter] = maze_row;
				row_counter++;
			}

			// we are done with the file so close it up
			maze_file.close();

			// create the maze class and start the search
			maze m = new maze(maze, maze_size);
			m.findExits(start_x, start_y);


			// print a message if there was no exit found
			if(!m.exit_found){
				System.out.println("Not reachable");
			}

			System.out.println(); // its nice to print a newline at the
			                      // end of any program, keeps the terminal
			                      // prompt clean
		}
		// print a nice message when the file given doesn't exist
		catch(FileNotFoundException e){
			System.out.println("The file provided does not exist.");
		}
		// There really shouldn't be any other problems unless someone gives an
		// invalid file
		catch(Exception e){
			System.out.println("An unexpected error occured while reading the" +
			                   "maze file.");
			System.out.println("[" + e.getClass() + "] " + "Message: " +
			                   e.getMessage());
		}
	}

	// making this a class speeds the algorithm up
	public maze(int[][] maze, int size){

		// the new maze size is 2 bigger because of the 0 padding
		this.maze_size = size + 2;
		this.working_maze = new int[this.maze_size][this.maze_size];

		// no exit was yet found so set this false
		this.exit_found = false;

		// pad the maze with BLOCKED_CELL, stops any invalid indexes
		Arrays.fill(working_maze[0] , BLOCKED_CELL);
		for(int i = 0; i < size ; i++){
			working_maze[i + 1][0] = BLOCKED_CELL;
			working_maze[i + 1][this.maze_size - 1] = BLOCKED_CELL;
			for(int j = 0; j < size; j++){
				working_maze[i+1][j+1] = maze[i][j];
			}
		}
		Arrays.fill(working_maze[this.maze_size - 1] , BLOCKED_CELL);
	}

	// the recursive algorithm
	public void findExits(int pos_x, int pos_y){

		// skip blocked cells
		if(this.working_maze[pos_x][pos_y] == BLOCKED_CELL)
			return;

		// if an exit was found we add it the the list of exits found
		if(this.working_maze[pos_x][pos_y] == EXIT_CELL){
			System.out.printf("(%d,%d) ", pos_x, pos_y);
			this.exit_found = true;
		}

		// set the node to blocked since we already visited it
		this.working_maze[pos_x][pos_y] = BLOCKED_CELL;


		// try move right
		this.findExits(pos_x + 1, pos_y);
		// try move left
		this.findExits(pos_x - 1, pos_y);
		// try move down
		this.findExits(pos_x, pos_y + 1);
		// try move up
		this.findExits(pos_x, pos_y - 1);
	}
}