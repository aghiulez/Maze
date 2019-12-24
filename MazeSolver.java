import java.util.Random;
import java.util.Stack;
import java.awt.Point;

//https://en.wikipedia.org/wiki/Maze_solving_algorith


//👣
//* 

            // *  *  *  *  *  *  *  *  *  *  *  *  *
            // *  -  -  -  *                       *
            // *  *  *  |  *  *  *  *  *     *  *  *
            // *  - - - -  *           *           *
            // *  |  *  *  *     *     *  *  *     *
            // *  | _            *           *     *
            // *  *  *  *  *  *  *  *  *     *     *
            // *           *                 *     *
            // *     *     *     *  *  *  *  *     *
            // *     *     *     *                 *
            // *     *     *     *     *  *  *     *
            // *     *                 *           *
            // *  *  *  *  *  *  *  *  *  *  *  *  *


public class MazeSolver{
    MazeGenerator myMaze;
    int[][] visited;
    private Random myRandomGenerator;
    Stack<Point> pathStack = new Stack<Point>();

    public MazeSolver(MazeGenerator maze){
        this.myMaze = maze;
        this.visited = new int[myMaze.dimensions][myMaze.dimensions];
        this.myRandomGenerator = new Random();
        findPath();
    }

    public Point generateDirection(Point from, Directions possibleDirections){
                                                                   
        // if UP is a possible direction       AND       visited[Y:row][X:column] is not 1 (visited)
        //Point retPoint;
        if(possibleDirections.UP ){
            if(this.visited[(int)from.getY() + 1][(int)from.getX()] != 1){
                return new Point((int)from.getX(),(int)from.getY() + 1 );
            }

        }
        else if(possibleDirections.RIGHT ){
            if(this.visited[(int)from.getY()][(int)from.getX()+1] != 1){
                return new Point((int)from.getX() + 1,(int)from.getY());
            }

        }
        else if(possibleDirections.DOWN){
            if(this.visited[(int)from.getY()-1][(int)from.getX()] != 1){
                return new Point((int)from.getX(),(int)from.getY()- 1);
            }
            
        }
        else if(possibleDirections.LEFT){
            if(this.visited[(int)from.getY()][(int)from.getX()-1] != 1){
                return new Point((int)from.getX()-1,(int)from.getY());
            }
            
        }

        return from; //returns from if no possible direction
    }
    
    public void findPath(){
        this.visited[0][0] = 1;
        Point currentCell = new Point(0,0);

        this.pathStack.push(currentCell);

        //System.out.println(this.myMaze.directions.get(currentCell).UP);
        System.out.println(generateDirection(currentCell, this.myMaze.directions.get(currentCell)));
        // while((currentCell.getX() != (this.myMaze.dimensions - 1) && currentCell.getY() != (this.myMaze.dimensions - 1))){


        // }

    
    }




    public static void main(String[] args) 
    { 
        MazeGenerator myMaze = new MazeGenerator(5);
        myMaze.printBoard();
        MazeSolver myMazeSolver = new MazeSolver(myMaze); 
        //MazeSolver.printBoard();
    

    } 
}