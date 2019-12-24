import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

// VIEW BOARD IS A 2D ARRAY ---  int[(dimensions * 2) + 1][(dimensions * 2) + 1]
//                          ... i.e.   5x5 board is an 11x11 view board
//     0  1  2  3  4  5  6  7  8  9  10     
// 0   *  *  *  *  *  *  *  *  *  *  * 
// 1   *     W     W     W     W     * 
// 2   *  W  *  W  *  *  *  W  *  W  *   
// 3   *     W     W     W     W     *      
// 4   *  W  *  W  *  *  *  W  *  W  *  
// 5   *     W     W     W     W     *    
// 6   *  W  *  W  *  *  *  W  *  W  *  
// 7   *     W     W     W     W     *     
// 8   *  W  *  W  *  *  *  W  *  W  * 
// 9   *     W     W     W     W     *     
// 10  *  W  *  W  *  W  *  W  *  W  *  

// BOUNDARY: FIRST ITERATION AND LAST ITERATION = "*  "

// EVEN PAIRS ARE JOINTS: "* "
// ODD PAIRS ARE CELLS : "    "
// EVEN X AND ODD Y ARE LEFT/RIGHT WALLS: "W "
// ODD X AND EVEN Y ARE UP/DOWN WALLS: " W  "

public class MazeView{
    MazeGenerator myMaze;
    String[][] viewboard;

    MazeView(MazeGenerator maze){
        this.myMaze = maze;
        this.viewboard = new String[(maze.dimensions * 2) + 1][(maze.dimensions * 2) + 1];
        GenerateBoard();
    }

    public void GenerateBoard(){
        for (int cellY = 0, y = 0; y < this.viewboard.length; y++){  // rows   
            if(y % 2 != 0 && y != 1){cellY += 1;}        
            for (int cellX = 0, x = 0 ; x < this.viewboard.length; x++){
                if(x % 2 != 0 && x != 1){cellX += 1;}    
                Directions dir = this.myMaze.directions.get(new Point(cellX,cellY));
                this.viewboard[y][x] = ( (y == 0 || y == (this.viewboard.length - 1) || (x == 0 || x == (this.viewboard.length - 1)) ? "*  ": 
                (y%2 != 0 && x%2 != 0)?"   ":(y%2 == 0 && x%2 == 0)? "*  ":
                (y%2 == 0 && dir.UP)?  "   ":
                (y%2 != 0 && dir.RIGHT)? "   ": "w  " ));
            }
        }
    }

    public void printBoard(){
        for (int y = 0; y < this.viewboard.length; y++){
            for (int x = 0; x < this.viewboard.length; x++){
                System.out.print(this.viewboard[y][x]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) 
    { 
        MazeGenerator myMaze = new MazeGenerator(5);

        MazeView view = new MazeView(myMaze);
        view.printBoard();
        System.out.println();
        //MazeSolver.printBoard();
    

    } 

    
    
}