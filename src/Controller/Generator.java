package Controller;

import Model.Cell;
import Model.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import java.util.Random;

//Controller
public class Generator {
    Maze maze;
    Stack<Cell> stack = new Stack<>();
    private Random randomGenerator = new Random();

    public Generator(Maze m){
        this.maze = m;
    }

    public void DFSIterativeBacktracker()  {


        maze.setCurrentLocation(maze.board[0][0]);

        this.stack.push(maze.getCurrLocation());

        while (!this.stack.empty()){
            //maze.currentLocation = this.stack.pop();
            maze.setCurrentLocation(this.stack.pop());
            Cell next = planVisit(maze.getCurrLocation());

            if (next != maze.getCurrLocation()){
                this.stack.push(maze.getCurrLocation());
                visit(maze.getCurrLocation(),next);
//                maze.setCurrentLocation(maze.currentLocation);
                this.stack.push(next);
            }


        }
    }

    private Cell planVisit(Cell current){
        List<Cell> possible = new ArrayList<Cell>();
        //North
        if(current.y != 0 && (!this.maze.board[current.y-1][current.x].hasVisited)){
            possible.add(this.maze.board[current.y-1][current.x]);
        }
        //West
        if(current.x != (this.maze.board.length-1) && (!this.maze.board[current.y][current.x+1].hasVisited)){
            possible.add(this.maze.board[current.y][current.x+1]);
        }
        //South
        if(current.y != (this.maze.board.length-1) && (!this.maze.board[current.y+1][current.x].hasVisited)){
            possible.add(this.maze.board[current.y+1][current.x]);
        }
        //East
        if(current.x != 0 && (!this.maze.board[current.y][current.x-1].hasVisited)){
            possible.add(this.maze.board[current.y][current.x-1]);
        }
        if (possible.size() > 0){
            int randomIndex = randomGenerator.nextInt(possible.size());
            return possible.get(randomIndex);
        }
        return current; // returns current if no possible visit
    }
    private void visit(Cell current, Cell next){
        //Moving North
        if(current.y-1 == next.y){
            current.NorthWall = false;
            next.SouthWall    = false;


        }
        //Moving West
        else if(current.x+1 == next.x){
            current.WestWall = false;
            next.EastWall = false;

        }
        //Moving South
        if(current.y+1 == next.y){
            current.SouthWall = false;
            next.NorthWall    = false;

        }
        //Moving East
        else if(current.x-1 == next.x){
            current.EastWall = false;
            next.WestWall = false;

        }

    }
}
