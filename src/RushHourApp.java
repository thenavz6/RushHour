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
import java.util.*;

import static javafx.stage.Screen.*;

/**
 * RushHourApp class
 * Generate the board with a puzzle and show it for the user to play
 * Works by creating the UI elements of the board i.e. the background
 * while generating the puzzle based on the difficulty selected in the main menu.
 * Once a solvable puzzle is complete, set up the board pieces based on the
 * State node given by the puzzle algorithm and provide controls for each piece.
 * Once everyone is set up, show the stage to the user.
 */
public class RushHourApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    public static final int BUFFER = 80;
    private static long startTime;
    private static int movesTaken;
    private static boolean stopControls;
    private static Stage testStage;
    private MainPiece ptr; // keeps track of the main object;
    public static ArrayList<MainPiece> pieces = new ArrayList<>();
    public static StateNode currentState;
    public static Stack<StateNode> moveStack;

    private Group c = new Group();

    /**
     * Sets up the UI elements of the board
     * such as the vehicle pieces and the grid image.
     * Once that is done, generate the puzzle for the board based on
     * the difficulty setting the user has selected. Once a solveable board
     * is generated, generate the vehicle pieces based of the board State
     * and move them to the correct position on the grid and give controls
     * for each vehicle on the board.
     * @return root The content panel of the stage.
     */
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

        currentState = startState;
        state.solve(startState);
        moveStack = state.getMovementList();
        state.printMoves(state.getMovementList());

        System.out.println(Menu.difficulty);
        System.out.println("Start State: ");
        state.printState(startState);
        System.out.println("End State: ");
        state.printState(state.getEndState());


        //state.printState(startState);

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

    /**
     * Function that gets called when the game is started.
     * Generates the UI elements of the board by grabbing the board
     * generated by the algorithm and putting the pieces where they need to be.
     * Once the board is populated, make the UI elements such as the background appear
     * and make the controls for the user work.
     * @param primaryStage Stage for displaying the game board.
     */
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

    /**
     * Getter for the board pieces
     * @return pieces
     */
    public ArrayList<MainPiece> getPieces(){
        return pieces;
    }

    /**
     * Sets if the user can move the vehicle pieces
     * @param stopControls
     */
    public void setStopControls(boolean stopControls) {
        this.stopControls = stopControls;
    }

    /**
     * Getter for the main stage the grid is running on
     * @return testStage
     */
    public Stage getTestStage() {
        return testStage;
    }

    /**
     * Getter for the time the user started the puzzle
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Getter for amount of moves the user has taken
     * @return movesTaken
     */
    public int getMovesTaken() {
        return movesTaken;
    }
}