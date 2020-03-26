import javafx.beans.value.ObservableBooleanValue;

public class Cell {
    boolean hasVisited;
    boolean NorthWall, WestWall, SouthWall, EastWall;
    int x,y;
    Cell(int x, int y){
        this.x = x;
        this.y = y;
        this. hasVisited = false;
        this.NorthWall = true;
        this.WestWall  = true;
        this.SouthWall = true;
        this.EastWall  = true;
    }
}
