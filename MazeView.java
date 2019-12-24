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

// BOUNDARY: FIRST ITERATION AND LAST ITERATION = "* "

// EVEN PAIRS ARE JOINTS: "* "
// ODD PAIRS ARE CELLS : "    "
// EVEN X AND ODD Y ARE LEFT/RIGHT WALLS: "W "
// ODD X AND EVEN Y ARE UP/DOWN WALLS: " W  "

public class MazeView{
    MazeGenerator myMaze;

    MazeView(MazeGenerator maze){
        this.myMaze = maze;
    }

    public void printBoard(){
        int max = (myMaze.dimensions * 2) + 1;
        int y = 0;
        for (int i = 0 ; i < max ; i++){
            if (i % 2 == 0){
                int x = 0;
                for (int j = 0 ; j < ((myMaze.dimensions * 2) + 1); j++){  // if j odd -->  maybe  UP/DOWN wall
                    if(j%2 != 0 && j != 1){
                        x += 1;
                    }


                    //  we've got to check if the down wall for point(x,y) exists
                    Point searchPoint = new Point(x,y);
                    if (i != max - 1 && myMaze.directions.get(searchPoint).DOWN == true){
                        System.out.print(j == (myMaze.dimensions * 2)? "*": (j % 2 != 0 && i != 0 && i != myMaze.dimensions*2)? "   ": "*  ");

                    }
                    else
                    {
                        System.out.print(j == (myMaze.dimensions * 2)? "*": (j % 2 != 0 && i != 0 && i != myMaze.dimensions*2)? "*  ": "*  ");
                    }

                    
                }
                System.out.println();        
            }
            else{
                int x = 0;
                for (int j = 0 ; j < ((myMaze.dimensions*2) + 1); j++){      // odd j's are cells! ---> even j's are LEFT/RIGHT
                    if (j % 2 != 0 && j != 1){
                        x += 1;
                    }
                    Point searchPoint = new Point(x,y);
                    System.out.print(j%2 != 0? "     ": j == 0 || j == myMaze.dimensions*2 ? "*": 
                    myMaze.directions.get(searchPoint).RIGHT == true? " ":"*");

                }
                y += 1;                
                System.out.println();
            }
        }
    }

    
    
}