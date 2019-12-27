import java.util.List;
import java.util.Stack;
import java.awt.Point;

public class MazeSolver{
    MazeGenerator myMaze;
    int[][] visited;
    Stack<Point> pathStack = new Stack<Point>();
    MazeView view;

    public MazeSolver(MazeGenerator maze){
        this.myMaze = maze;
        this.view = new MazeView(maze);
        this.visited = new int[myMaze.dimensions][myMaze.dimensions];
        solve();
    }
    public boolean notBlocked(Point pointA, Point pointB){
        return ((Utils.direction(pointA, pointB)==1 && this.myMaze.directions.get(pointA).UP)    ||
                (Utils.direction(pointA, pointB)==2 && this.myMaze.directions.get(pointA).RIGHT) ||
                (Utils.direction(pointA, pointB)==3 && this.myMaze.directions.get(pointA).DOWN)  ||
                (Utils.direction(pointA, pointB)==4 && this.myMaze.directions.get(pointA).LEFT)
                ? true : false);
    }
    public void solve(){
        //mark fist cell as visited
        this.visited[0][0] = 1;
        this.pathStack.push(new Point(0,0));
        this.view.UpdateBoard(0, 0, 0);
        Point currentCell;

        while(!this.pathStack.empty()){
            currentCell = this.pathStack.pop();
                List<Point> listOfpossible = Utils.hasNotVisited(this.visited, currentCell);
                while (listOfpossible.size() > 0){
                    for (Point p : listOfpossible){
                        if(notBlocked(currentCell, p)){
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
        }
    }
    public static void main(String[] args) { 
        System.out.println("TEST:\n");
        MazeSolver solver = new MazeSolver(Globals.myMaze);
        solver.view.printBoard();
    } 
}