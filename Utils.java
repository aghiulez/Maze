import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.awt.Point;

public class Utils {



    // FUNCTION
    // given a map  and two points (from to end), updates the map with directions possible from each point
    // CHANGE THIS TO RETURN DIRECTION AFTER PUTTING KEY INTO DICT


    // FUNCTION
    // given a visit board and a point returns a list of potential destination points
    // You can only visit a point you have not visited yet
    public static List<Point> hasNotVisited(int[][] visitboard, Point fromHere){
        List<Point> ret = new ArrayList<Point>();
        int x = (int)fromHere.getX();
        int y = (int)fromHere.getY();
        int dimensions = visitboard.length;

        // for(int i = 0; i<dimensions; i++)
        // {
        //     for (int j = 0; j < dimensions; j++){
        //         System.out.print(visitboard[i][j]);
        //     }
        //     System.out.println();
        // }
        
        //UP ( UP IS DOWN - DOWN IS UP)
        if(y < (dimensions - 1) && visitboard[y+1][x] != 1){
            ret.add(new Point(x,y+1));
        }
        //RIGHT
        if(x < (dimensions - 1) && visitboard[y][x+1] != 1){
            ret.add(new Point(x+1,y));
        }
        //DOWN
        if(y > 0 && visitboard[y-1][x] != 1){
            ret.add(new Point(x,y-1));
        }
        //LEFT
        if(x > 0 && visitboard[y][x-1] != 1){
            ret.add(new Point(x-1,y));
        }

        return ret;
    }


    //FUNCTION 
    // given 2 points (POINTA POINTB) returns the direction of how to get FROM POINTA TO POINTB
    // 1 -> UP (Y Increase : technically we are moving down) 
    // 2 -> RIGHT
    // 3 -> LEFT
    // 4 -> DOWN (Y Decrease: technically we are moving up)
    // 0 -> NOT REACHABLE
    public static int direction(Point pointA, Point pointB){
        if((int)pointA.getX() == (int)pointB.getX()  && (int)pointA.getY()+1 == (int)pointB.getY()){
            //UP
            return 1;
        }
        else if((int)pointA.getX()+1 == (int)pointB.getX()  && (int)pointA.getY() == (int)pointB.getY()){
            //RIGHT
            return 2;
        }
        else if((int)pointA.getX() == (int)pointB.getX() && (int)pointA.getY()-1 == (int)pointB.getY()){
            //DOWN
            return 3;
        }
        else if((int)pointA.getX()-1 == (int)pointB.getX() && (int)pointA.getY() == (int)pointB.getY()){
            //LEFT
            return 4;
        }

        return 0; 
    }


    public static void main(String[] args) 
    { 
        Utils myUtils = new Utils();
        System.out.println(myUtils.direction(new Point(0,0), new Point(0,0))); // nowhere
        System.out.println(myUtils.direction(new Point(0,1), new Point(0,2))); // up : 1
 
        System.out.println(myUtils.direction(new Point(0,1), new Point(1,1))); // right : 2
        System.out.println(myUtils.direction(new Point(0,1), new Point(0,0))); // down : 3
        System.out.println(myUtils.direction(new Point(1,1), new Point(0,1))); // left : 4
 
 
       
    } 



}