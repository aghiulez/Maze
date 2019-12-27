import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.awt.Point;


            // *  *  *  *  *  *  *  *  *  *  *  *  *
            // *  -  -  -  *                       *
            // *  *  *  |  *  *  *  *  *     *  *  *
            // *  - - - -  *           *           *
            // *  |  *  *  *     *     *  *  *     *
            // *  | _ _          *           *     *
            // *  *  *  *  *  *  *  *  *     *     *
            // *           *                 *     *
            // *     *     *     *  *  *  *  *     *
            // *     *     *     *                 *
            // *     *     *     *     *  *  *     *
            // *     *                 *           *
            // *  *  *  *  *  *  *  *  *  *  *  *  *
//  ═╗

public class MazeSolver{
    MazeGenerator myMaze;
    int[][] visited;
    private Random myRandomGenerator;
    Stack<Point> pathStack = new Stack<Point>();

    MazeView view;

    //MazeView myView = new MazeView(myMaze);


    public MazeSolver(MazeGenerator maze){
        this.myMaze = maze;
        this.view = new MazeView(maze);
        this.visited = new int[myMaze.dimensions][myMaze.dimensions];
        this.myRandomGenerator = new Random();
        solve();
    
    }


    public boolean notBlocked(Point pointA, Point pointB){
        return ((Utils.direction(pointA, pointB)==1 && this.myMaze.directions.get(pointA).UP)    ||
                (Utils.direction(pointA, pointB)==2 && this.myMaze.directions.get(pointA).RIGHT) ||
                (Utils.direction(pointA, pointB)==3 && this.myMaze.directions.get(pointA).DOWN)  ||
                (Utils.direction(pointA, pointB)==4 && this.myMaze.directions.get(pointA).LEFT)
                ? true : false);
    }
    



    // !!! Backtracking IS NOT WORKING !!!!!
    /// Pops once but stops --- not enough is being pushed to stack before being popped
    public void solve(){
        //mark fist cell as visited
        this.visited[0][0] = 1;

        //push point to the stack
        this.pathStack.push(new Point(0,0));
        Point currentCell;

        //if stack is empty we have nothing to pop...
        while(!this.pathStack.empty()){
        //  Pop a cell from the stack and make it a current cell

            

            currentCell = this.pathStack.pop();
            System.out.print("Popped CurrentCell: ");
            System.out.println(currentCell);

            
            

        
            
        //  If the current cell has any (reachable) neighbours which have not been visited
            //if (Utils.hasNotVisited(this.visited, currentCell).size() > 0){
                List<Point> listOfpossible = Utils.hasNotVisited(this.visited, currentCell);
                //while (Utils.hasNotVisited(this.visited, currentCell).size() > 0){
                while (listOfpossible.size() > 0){
                    for (Point p : listOfpossible){
                        if(notBlocked(currentCell, p)){
                            System.out.print("  GOING TO -> ");
                            System.out.println(p);


                           

                            this.visited[(int)p.getY()][(int)p.getX()] = 1;
                            currentCell = p;
                            //if we are at the destination
                            if ((int)currentCell.getX() == this.myMaze.dimensions - 1 && (int)currentCell.getY() == this.myMaze.dimensions - 1 ){
                                this.view.UpdateBoard(0, this.myMaze.dimensions - 1, this.myMaze.dimensions - 1);
                                return;
                            }
                            int direction = Utils.direction(currentCell, p);
                            this.view.UpdateBoard(direction, (int)currentCell.getX(), (int)currentCell.getY());


                            this.pathStack.push(p);

                            listOfpossible = Utils.hasNotVisited(this.visited, currentCell);
                            break; 
                        }
                    }
                
            }
            System.out.println("**** BACKTRACK ******");
            

       
        }



    
    }
    public static void main(String[] args) 
    { 
       
        //System.out.println(myUtils.direction(new Point(0,0), new Point(0,0)));
        //System.out.println();
        System.out.println("TEST:\n");
        MazeGenerator myMaze = Globals.myMaze;

        MazeSolver solver = new MazeSolver(Globals.myMaze);
        solver.view.printBoard();




    
    } 




}