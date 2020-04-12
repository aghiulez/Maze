package View;
import Model.*;
import Controller.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import java.applet.Applet;
import java.util.concurrent.atomic.AtomicBoolean;

//View
public class MazeFX extends Application implements Runnable {

    int speed = 1;
    int size = 500;
    int dimensions = 10;
    Maze myMaze;
    Generator generator;
    Solver solver;
    GridPane maze;
    
    private AtomicBoolean running = new AtomicBoolean(false);
    Thread ActiveThread;



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
        GridPane mymaze = new GridPane();
        mymaze.getStyleClass().add("maze");
        ColumnConstraints column = new ColumnConstraints((int) Math.ceil((float)(size/dimensions)));
        RowConstraints row       = new RowConstraints((int) Math.ceil((float)(size/dimensions)));
        for (int i = 0; i < myMaze.board.length; i++){
            mymaze.getColumnConstraints().add(column);
            mymaze.getRowConstraints().add(row);
        }


        for(int i = 0; i < myMaze.board.length; i++){
            for(int j = 0; j < myMaze.board.length; j++){
                BorderPane cell = CellPane();
                mymaze.add(cell,i,j);
            }
        }
        return mymaze;
    }
    public void removeWall(Cell cell){
        BorderPane cellPane = getCellPane(cell);
        Circle pointer = new Circle((int) Math.ceil((float)(size/dimensions)/5));
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
        myMaze.CurrLocationProperty().addListener(new ChangeListener(){
                @Override public void changed(ObservableValue o,Object oldVal,
                                              Object newVal){

                    Cell from = (Cell) oldVal;
                    Cell to = (Cell) newVal;

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
        generator.DFSIterativeBacktracker();
    }
    public void SolveMaze(){
        solver.DFSIterativeBacktracker();
    };



    public void createThread(){
        ActiveThread = new Thread(this);
        ActiveThread.setDaemon(true);
        ActiveThread.start();
    }
    public VBox menuSpinners(){



        // Dimensions chooser
        final Spinner<Integer> dimensionsSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(5,100,5,5);
        dimensionsSpinner.setValueFactory(valueFactory);
        dimensionsSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);



        //speed chooser
        final Spinner<Integer> speedSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> speedValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50,500,50,50);
        speedSpinner.setValueFactory(speedValueFactory);
        speedSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);

        return new VBox(dimensionsSpinner,speedSpinner);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MazeFX");

        Button startbtn = new Button("start"); // add stop button too?
        Button backbtn = new Button("back"); // add stop button too?



        //View
        BorderPane view = new BorderPane();

        VBox menu = menuSpinners();
        view.setCenter(menu);
        view.setAlignment(menu, Pos.CENTER);
        menu.setAlignment(Pos.CENTER);



        // Start btn props
        view.setBottom(startbtn);
        startbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Node spinnernode = menu.getChildren().get(0);
                Node speednode = menu.getChildren().get(1);
                if (spinnernode instanceof Spinner && speednode instanceof  Spinner){
                    dimensions = (int) ((Spinner) spinnernode).getValue();
                    speed      = (int) ((Spinner) speednode).getValue();
                }
                System.out.println(speed);



                myMaze = new Maze(dimensions); /// --> add dimension choosing functionality
                generator = new Generator(myMaze); // --> add speed of generator
                solver    = new Solver(myMaze);
                maze = MazePane();
                view.setCenter(maze);
                view.setAlignment(maze,Pos.CENTER);
                maze.setAlignment(Pos.CENTER);


                view.setBottom(backbtn);

                createThread();


            }
        });
        // back btn props
        backbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ActiveThread.stop();
                //view.setCenter(dimensionsSpinner);
                view.setCenter(menu);
                view.setBottom(startbtn);
                speed = 1000;
            }
        });


        //Misc
        view.setAlignment(startbtn,Pos.CENTER);
        view.setAlignment(backbtn,Pos.CENTER);


        Scene scene = new Scene(view, size, size  + 25);
        scene.getStylesheets().add("View/MazeFX.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void run(){
//            running.set(true);
//            while(running.get()){
//                GenerateMaze();
//
//            }
        GenerateMaze();
        SolveMaze();
    };

    public static void main(String[] args) {
        launch(args);
    }
}