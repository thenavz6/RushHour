import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;


import java.awt.*;
import java.util.ArrayList;

public class RushHourApp extends Application{


    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;


    private MainPiece ptr; // keeps track of the main object;
    public static ArrayList<MainPiece> pieces = new ArrayList<>();

    private Group c = new Group();


    private Parent createContent()
    {
        Pane root = new Pane();

        // set size of it
        root.setPrefSize(WIDTH* TILE_SIZE   , HEIGHT * TILE_SIZE);
        root.getChildren().add(c);

        StateSearch state = new StateSearch();
        StateNode endState = state.generateEndState(6);
        StateNode startState;
        state.generateStateSpace(endState);
        startState = state.generateStartState(endState);

        while(startState == null){
            endState = state.generateEndState(6);
            state.generateStateSpace(endState);
            startState = state.generateStartState(endState);
        }

        state.printState(startState);

        for(Vehicle item: startState.getVehicles()){
            MainPiece car;
            if(item.getDirection() == Direction.WEST){
                car = new MainPiece(item.getFrontColumn(),item.getFrontRow(),item.getOrientation(),item.getVehicleType());
            }else if(item.getDirection() == Direction.EAST){
                car = new MainPiece(item.getBackColumn(),item.getBackRow(),item.getOrientation(),item.getVehicleType());
            }else if(item.getDirection() == Direction.NORTH){
                car = new MainPiece(item.getFrontColumn(),item.getFrontRow(),item.getOrientation(),item.getVehicleType());
            }else{
                car = new MainPiece(item.getBackColumn(),item.getBackRow(),item.getOrientation(),item.getVehicleType());
            }

            javafx.scene.image.Image img = new Image("images/"+item.getVehicleType().toString()+"_"+item.getColour().toString()+"_"+item.getOrientation()+".png");
            car.setFill(new ImagePattern(img));
            car.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            ptr = car;
                        }
                    });
            pieces.add(car);
            c.getChildren().add(car);
        }

        ptr = pieces.get(0);

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if(ptr.getDirection().equals("v")){
                    if (event.getCode() == KeyCode.UP) {
                        ptr.moveUp();
                        System.out.println("x = " + ptr.getxPos());
                        System.out.println("y = " + ptr.getyPos());
                    } else if (event.getCode() == KeyCode.DOWN) {
                        ptr.moveDown();
                        System.out.println("x = " + ptr.getxPos());
                        System.out.println("y = " + ptr.getyPos());
                    }
                }else {
                    if (event.getCode() == KeyCode.RIGHT) {
                        ptr.moveRight();
                        System.out.println("x = " + ptr.getxPos());
                        System.out.println("y = " + ptr.getyPos());
                    } else if (event.getCode() == KeyCode.LEFT) {
                        ptr.moveLeft();
                        System.out.println("x = " + ptr.getxPos());
                        System.out.println("y = " + ptr.getyPos());
                    }
                }

            }
        });


        primaryStage.setTitle("Rush Hour"); // sets the title name
        primaryStage.setScene(scene); // places scene into primary Stage
        primaryStage.show(); // opens the java file
    }

    public ArrayList<MainPiece> getPieces(){
        return pieces;
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}