import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

//View
public class MazeFX extends Application implements Runnable{

    int speed = 100;
    int size = 500;
    int dimensions = 100;


    Maze myMaze = new Maze(dimensions);
    Generator generator = new Generator(myMaze);



    GridPane maze;


    public BorderPane CellPane(){
        int thickness = (int) Math.ceil((float)(size/dimensions)/10); //


        BorderPane cell = new BorderPane();
        cell.getStyleClass().add("cell");
        Pane topwall = new Pane();
        topwall.setPrefHeight(thickness);
        Pane rightwall = new Pane();
        rightwall.setPrefWidth(thickness);
        Pane bottomwall = new Pane();
        bottomwall.setPrefHeight(thickness);
        Pane leftwall = new Pane();
        leftwall.setPrefWidth(thickness);
        topwall.getStyleClass().add("wall");
        rightwall.getStyleClass().add("wall");
        bottomwall.getStyleClass().add("wall");
        leftwall.getStyleClass().add("wall");
        cell.setTop(topwall);
        cell.setRight(rightwall);
        cell.setBottom(bottomwall);
        cell.setLeft(leftwall);

        return cell;
    }
    public GridPane MazePane(){
        maze = new GridPane();
        maze.getStyleClass().add("maze");
        ColumnConstraints column = new ColumnConstraints(size/dimensions);
        RowConstraints row       = new RowConstraints(size/dimensions);
//        System.out.println("cell size: " + size/dimensions);
//        int thickness = (int) Math.ceil((float)(size/dimensions)/10);
//        System.out.println("thickness: " + thickness);
        for (int i = 0; i < myMaze.board.length; i++){
            maze.getColumnConstraints().add(column);
            maze.getRowConstraints().add(row);
        }


        for(int i = 0; i < myMaze.board.length; i++){
            for(int j = 0; j < myMaze.board.length; j++){
                BorderPane cell = CellPane();
                maze.add(cell,i,j);
            }
        }
        return maze;
    }


    public void removeWall(Cell cell){
        BorderPane cellPane = getCellPane(cell);
        Circle pointer = new Circle((size/dimensions)/10);
        pointer.setFill(Color.RED);
        cellPane.setCenter(pointer);
        if (cellPane != null){
            if (!cell.NorthWall){
                //      System.out.print("Top ");
                cellPane.setTop(null);
            }
            if (!cell.WestWall){
                //    System.out.print("Right ");
                cellPane.setRight(null);
            }
            if (!cell.SouthWall){
                //  System.out.print("Bot ");
                cellPane.setBottom(null);
            }
            if (!cell.EastWall){
                //System.out.print("Left ");
                cellPane.setLeft(null);
            }

        }
    }
    public BorderPane getCellPane(Cell curr){
        for(Node n: maze.getChildren()){
            Integer r = GridPane.getRowIndex(n);
            Integer c = GridPane.getColumnIndex(n);
            int row = r == null ? 0 : r;
            int column = c == null? 0 : c;

            if (row == curr.y && column == curr.x && (n instanceof BorderPane)) {

                BorderPane cellpane = (BorderPane) n;
                //((BorderPane) n).setBottom(null);
//                return cellpane;
                return (BorderPane) n;
            }
        }
        return null;
    }

    public void GenerateMaze()  {
        generator.DFSIterativeBacktracker();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MazeFX");
        myMaze.CurrLocationProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o,Object oldVal,
                                          Object newVal){

//                String coords = "("+ myMaze.getCurrLocation().x+ ","+ myMaze.getCurrLocation().y + ")";
                Cell from = (Cell) oldVal;
                Cell to = (Cell) newVal;

                //removeWall(myMaze.board[myMaze.getCurrLocation().y][myMaze.getCurrLocation().x]);
                Platform.runLater( () -> {
                    if(from != null){

                        removeWall(from);
                        getCellPane(from).setCenter(null);
                    }
                    removeWall(to);
                });
                synchronized (this) {
                    try { wait(speed); }
                    catch (InterruptedException e) { }
                }

            }
        });


        GridPane maze = MazePane();
        maze.setAlignment(Pos.CENTER);
        Scene scene = new Scene(maze,size,size);
        scene.getStylesheets().add("MazeFX.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();



        Thread runner = new Thread(this);
        runner.setDaemon(true);
        runner.start();


    }
    public void run(){
        GenerateMaze();
    };
}