// https://en.wikipedia.org/wiki/Maze_generation_algorithm

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.Point;
import java.util.Stack;


public class MazeGenerator{
    int dimensions;
    int[][] board;
    Stack<Point> stack = new Stack<Point>();
    Map<Point,Directions> directions = new HashMap<Point,Directions>();
    private Random randomGenerator = new Random();
    public MazeGenerator(int dimensions){
        this.dimensions = dimensions;
        this.board = new int[dimensions][dimensions];
        BacktrackingWithStackGenerator();
    }
    public void direction(Point pointA, Point pointB){
        if(!this.directions.containsKey(pointA)){this.directions.put(pointA,new Directions());}
        if(!this.directions.containsKey(pointB)){this.directions.put(pointB,new Directions());}
        if((int)pointA.getY() < (int)pointB.getY()){  // increase in y for pointA
            this.directions.get(pointA).UP = true;
            this.directions.get(pointB).DOWN = true;
        }
        else if((int)pointA.getX() < (int)pointB.getX()){  // increase in x for pointA
            this.directions.get(pointA).RIGHT = true;
            this.directions.get(pointB).LEFT = true;
        }
        else if((int)pointA.getY() > (int)pointB.getY()){  // decrease in y for pointA
            this.directions.get(pointA).DOWN = true;
            this.directions.get(pointB).UP = true;
        }
        else if((int)pointA.getX() > (int)pointB.getX()){  // decrease in x for pointA
            this.directions.get(pointA).LEFT = true;
            this.directions.get(pointB).RIGHT = true;
        }
    }
    public void BacktrackingWithStackGenerator(){
        // Choose the initial cell, mark it as visited and push it to the stack
        this.board[0][0] = 1;
        this.stack.push(new Point(0,0));
        // While the stack is not empty
        Point currentCell;
        while (!this.stack.empty()){
       //   Pop a cell from the stack and make it a current cell
            currentCell = this.stack.pop();
       //   If the current cell has any neighbours which have not been visited
            //List<Point> canGoTo = possibleVisits((int)currentCell.getX(),(int)currentCell.getY());
            List<Point> canGoTo = Utils.hasNotVisited(this.board, currentCell);
            //--System.out.println(canGoTo);
            if (canGoTo.size() > 0){
        //      Push the current cell to the stack
                this.stack.push(currentCell);
        //      Choose one of the unvisited neighbours
                int randomIndex = randomGenerator.nextInt(canGoTo.size());
                Point goingTo = canGoTo.get(randomIndex);
        //      Remove the wall between the current cell and the chosen cell
                direction(currentCell,goingTo);
        //      Mark the chosen cell as visited and push it to the stack
                this.board[(int)goingTo.getY()][(int)goingTo.getX()] = 1;
                this.stack.push(goingTo);  
            }
        }
    }
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
}




