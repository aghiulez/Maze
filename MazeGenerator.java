// https://en.wikipedia.org/wiki/Maze_generation_algorithm

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.Point;
import java.util.Stack;


public class MazeGenerator {
    int dimensions;
    private int[][] board;
    Stack<Point> stack = new Stack<Point>();
    Map<Point,Directions> directions = new HashMap<Point,Directions>();
    private Random randomGenerator;

    public MazeGenerator(int dimensions){
        this.dimensions = dimensions;
        this.board = new int[dimensions][dimensions];
        this.randomGenerator = new Random();
        recursiveBacktrackingWithStackGenerator();
 
    }


    public int direction(Point pointA, Point pointB){

        if(!this.directions.containsKey(pointA)){
            this.directions.put(pointA,new Directions());
        }
        if(!this.directions.containsKey(pointB)){
            this.directions.put(pointB,new Directions());
        }


        // 1 for up -- 2 for right -- 3 for down -- 4 for left
        if (pointB.getY() > pointA.getY()){
            this.directions.get(pointB).DOWN = true;
            this.directions.get(pointA).UP = true;
            return 1;
        }
        else if (pointB.getX() > pointA.getX()){
            this.directions.get(pointB).LEFT = true;
            this.directions.get(pointA).RIGHT = true;
            return 2;
        }
        else if (pointB.getY() < pointA.getY()){
            this.directions.get(pointB).UP = true;
            this.directions.get(pointA).DOWN = true;
            return 3;
        }
        else if (pointB.getX() < pointA.getX()){
            this.directions.get(pointB).RIGHT = true;
            this.directions.get(pointA).LEFT = true;
            return 4;
        }

        return 0;
    }

    public void recursiveBacktrackingWithStackGenerator(){
        // Choose the initial cell, mark it as visited and push it to the stack
        this.board[0][0] = 1;
        this.stack.push(new Point(0,0));
        // While the stack is not empty
        Point currentCell;
        while (!this.stack.empty()){
       //   Pop a cell from the stack and make it a current cell
            currentCell = this.stack.pop();
       //   If the current cell has any neighbours which have not been visited
            List<Point> canGoTo = possibleVisits((int)currentCell.getX(),(int)currentCell.getY());
            if (canGoTo.size() > 0){
        //      Push the current cell to the stack
                this.stack.push(currentCell);
        //      Choose one of the unvisited neighbours
                int randomIndex = randomGenerator.nextInt(canGoTo.size());
                Point goingTo = canGoTo.get(randomIndex);
        //      Remove the wall between the current cell and the chosen cell
                direction(currentCell,goingTo);


        //      Mark the chosen cell as visited and push it to the stack
                this.board[(int)goingTo.getX()][(int)goingTo.getY()] = 1;
                this.stack.push(goingTo);  
            }
        }
    }

    //function to return a list of unvisited neighbors
    public List<Point> possibleVisits(int x, int y){
        List<Point> myList = new ArrayList<Point>();
        // up     ->   y -= 1   (CAN'T IF Y == 0)
        if ( y != 0 && (this.board[x][y - 1] != 1)){
            myList.add(new Point(x, y-1));
        }
        // right  ->   x += 1   (CAN'T IF X == MAX)
        if ( x != (this.dimensions - 1) && (this.board[x + 1][y] != 1)){
            myList.add(new Point(x+1, y));  
        }
        // down   ->   y += 1   (CAN'T IF Y == MAX)
        if ( y != (this.dimensions - 1) && (this.board[x][y + 1] != 1)){
            myList.add(new Point(x, y+1));
        }
        // left   ->   x -= 1   (CAN'T IF X == 0)
        if ( x != 0 && (this.board[x - 1][y] != 1)){
            myList.add(new Point(x-1, y));
        }
        return myList;
    }

    public void printBoard(){
        int max = (this.dimensions * 2) + 1;
        int y = 0;
        for (int i = 0 ; i < max ; i++){
            if (i % 2 == 0){
                int x = 0;
                for (int j = 0 ; j < ((this.dimensions * 2) + 1); j++){  // if j odd -->  maybe  UP/DOWN wall
                    if(j%2 != 0 && j != 1){
                        x += 1;
                    }


                    //  we've got to check if the down wall for point(x,y) exists
                    Point searchPoint = new Point(x,y);
                    if (i != max - 1 && this.directions.get(searchPoint).DOWN == true){
                        System.out.print(j == (this.dimensions * 2)? "*": (j % 2 != 0 && i != 0 && i != this.dimensions*2)? "   ": "*  ");

                    }
                    else
                    {
                        System.out.print(j == (this.dimensions * 2)? "*": (j % 2 != 0 && i != 0 && i != this.dimensions*2)? "*  ": "*  ");
                    }

                    
                }
                System.out.println();        
            }
            else{
                int x = 0;
                for (int j = 0 ; j < ((this.dimensions*2) + 1); j++){      // odd j's are cells! ---> even j's are LEFT/RIGHT
                    if (j % 2 != 0 && j != 1){
                        x += 1;
                    }
                    Point searchPoint = new Point(x,y);
                    System.out.print(j%2 != 0? "     ": j == 0 || j == this.dimensions*2 ? "*": 
                    this.directions.get(searchPoint).RIGHT == true? " ":"*");

                }
                y += 1;                
                System.out.println();
            }
        }
    }

    public static void main(String[] args) 
    { 
        MazeGenerator myMaze = new MazeGenerator(5); 
        myMaze.printBoard();
    

    } 


}



