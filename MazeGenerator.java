// https://en.wikipedia.org/wiki/Maze_generation_algorithm



import java.util.HashMap;
import java.util.Map;



public class MazeGenerator {

    int dimensions;
    int[][] board;


    public MazeGenerator(int dimensions){
        this.dimensions = dimensions;
        this.board = new int[dimensions][dimensions];
 
    }



    public void recusiveBacktrackingGenerator(){
    // Given a current cell as a parameter,                         we start at [0,0]
    // Mark the current cell as visited
    // While the current cell has any unvisited neighbour cells
    //     Chose one of the unvisited neighbours
    //     Remove the wall between the current cell and the chosen cell
    //     Invoke the routine recursively for a chosen cell
    // go back recursively
    }

    public void printBoard(){
        int max = (this.dimensions * 2) + 1;
        for (int i = 0 ; i < max ; i++){
            if (i % 2 == 0){
                for (int j = 0 ; j < ((this.dimensions * 2) + 1); j++){  // if j odd -->  maybe  UP/DOWN wall
                    System.out.print(j == (this.dimensions * 2)? "*": (j % 2 != 0 && i != 0 && i != this.dimensions*2)? "w  ": "*  ");
                }
                System.out.println();        
            }
            else{
                for (int j = 0 ; j < (this.dimensions + 1); j++){      // if j even --> maybe LEFT/RIGHT wall
                    System.out.print(j == 0 || j == this.dimensions ? "*     ": "w     "   );
                }
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
//        0     1     2     3     4     5     6     7     8     9
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 0   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 1   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 2   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 3   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 4   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 5   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 6   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 7   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 8   *     *     *     *     *     *     *     *     *     *     *  
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
// 9   *     *     *     *     *     *     *     *     *     *     *   
//     *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 