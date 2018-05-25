import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.stage.StageStyle;
import java.lang.reflect.Array;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Random;

import java.util.ArrayList;

import static javafx.stage.Screen.*;

public class RushHourApp extends Application{


    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    private static final int BUFFER = 80;
    private static long startTime;
    private static int movesTaken;
    private static boolean stopControls;
    private static Stage testStage;
    private MainPiece ptr; // keeps track of the main object;
    public static ArrayList<MainPiece> pieces = new ArrayList<>();

    private Group c = new Group();


    private Parent createContent()
    {
        Pane root = new Pane();
        pieces.clear();
        movesTaken = 0;
        // set size of it
        root.setPrefSize(WIDTH* TILE_SIZE + 2*BUFFER, HEIGHT * TILE_SIZE + 2*BUFFER);
        root.getChildren().add(c);
        ImageView imv = new ImageView();
        Image gridPicture = new Image("images/grid3.png");
        imv.setImage(gridPicture);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
        root.setBackground(new Background( new BackgroundImage(gridPicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

        StateSearch state = new StateSearch();
        Random generator = new Random();
        int numberOfCars;
        if(Menu.difficulty.equals("Easy")) {
            numberOfCars = generator.nextInt(2) + 8;
        }else if(Menu.difficulty.equals("Medium")){
            numberOfCars = generator.nextInt(2) + 12;
        }else{
            numberOfCars = generator.nextInt(2) + 14;
        }
        StateNode startState = state.generateStartState(numberOfCars,Menu.difficulty);

        while(!state.solve(startState)){
            startState = state.generateStartState(numberOfCars,Menu.difficulty);
        }
        state.solve(startState);
        state.printMoves(state.getMovementList());

        System.out.println(Menu.difficulty);
        System.out.println("Start State: ");
        state.printState(startState);
        System.out.println("End State: ");
        state.printState(state.getEndState());


        //state.printState(startState);
        // generation section
        // create an queue to store the easy
        //PriorityQueue<StateNode> easy = new PriorityQueue<StateNode>();
        //Levels t1 = new Levels(easy);
        //t1.start();
        //StateNode a = easy.poll();
        //for(Vehicle item: a.getVehicles()){
        for(Vehicle item: startState.getVehicles()){
            MainPiece car;
            if(item.getDirection() == Direction.west){
                car = new MainPiece(item.getFrontColumn(),item.getFrontRow(),item.getOrientation(),item.getVehicleType());
            }else if(item.getDirection() == Direction.east){
                car = new MainPiece(item.getBackColumn(),item.getBackRow(),item.getOrientation(),item.getVehicleType());
            }else if(item.getDirection() == Direction.north){
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
                            // removes previous effect when new car is choosen
                            ptr.setEffect(null);

                            DropShadow borderGlow = new DropShadow();
                            borderGlow.setColor(Color.YELLOW);
                            borderGlow.setOffsetX(0f);
                            borderGlow.setOffsetY(0f);
                            borderGlow.setHeight(20);
                            borderGlow.setWidth(20);
                            // change new pointer
                            ptr = car;
                            // make selected car glow
                            car.setEffect(borderGlow);
                        }
                    });
            pieces.add(car);
            c.getChildren().add(car);
        }

        ptr = pieces.get(0);

        return root;
    }

    @Override
    public void start(Stage primaryStage)
    {
        Scene scene = new Scene(createContent());
        stopControls = false;
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Pause paused = new Pause();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Pause");
                paused.start(secondaryStage);
                stopControls = true;

            } else if (ptr.getDirection().equals("v") & stopControls == false) {
                if (event.getCode() == KeyCode.UP) {
                    ptr.moveUp();
                    System.out.println("x = " + ptr.getxPos());
                    System.out.println("y = " + ptr.getyPos());
                } else if (event.getCode() == KeyCode.DOWN) {
                    ptr.moveDown();
                    System.out.println("x = " + ptr.getxPos());
                    System.out.println("y = " + ptr.getyPos());
                }
                movesTaken++;
            } else if (ptr.getDirection().equals("h") & stopControls == false) {
                if (event.getCode() == KeyCode.RIGHT) {
                    ptr.moveRight();
                    System.out.println("x = " + ptr.getxPos());
                    System.out.println("y = " + ptr.getyPos());
                } else if (event.getCode() == KeyCode.LEFT) {
                    ptr.moveLeft();
                    System.out.println("x = " + ptr.getxPos());
                    System.out.println("y = " + ptr.getyPos());
                }
                movesTaken++;
            }

        });
        testStage = primaryStage;
        startTime = System.nanoTime();
        Rectangle2D screenBounds = getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - (WIDTH * TILE_SIZE + 2*BUFFER)) / 2);
        primaryStage.setY((screenBounds.getHeight() - (HEIGHT * TILE_SIZE + 2*BUFFER)) / 2);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Rush Hour"); // sets the title name
        primaryStage.setScene(scene); // places scene into primary Stage
        primaryStage.show(); // opens the java file

    }

    public ArrayList<MainPiece> getPieces(){
        return pieces;
    }

    public void setStopControls(boolean stopControls) {
        this.stopControls = stopControls;
    }



    public Stage getTestStage() {
        return testStage;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getMovesTaken() {
        return movesTaken;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}