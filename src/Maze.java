
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;



//Model
public class Maze {
    Cell[][] board;

    //Cell currentLocation; //update view

    private ObjectProperty<Cell> currLocation = new SimpleObjectProperty<Cell>();
    public final Cell getCurrLocation() {return currLocation.get();}
    public final void setCurrentLocation(Cell next){
        next.hasVisited = true;
        currLocation.set(next);}
    public ObjectProperty<Cell> CurrLocationProperty() {return currLocation;}


    Maze(int d){

        this.board = new Cell[d][d];

        for (int i =0; i<d; i++){
            for(int j=0; j<d; j++){
                this.board[i][j] = new Cell(j,i);
            }
        }

    }


}
