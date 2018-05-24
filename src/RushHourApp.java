import com.sun.tools.javac.Main;
import javafx.application.Application;
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
import javafx.scene.layout.GridPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;


import java.awt.*;
import java.util.ArrayList;

public class RushHourApp extends Application{


    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    private boolean stopControls = false;

    private MainPiece ptr; // keeps track of the main object;
    public static ArrayList<MainPiece> pieces = new ArrayList<>();

    private Group c = new Group();


    private Parent createContent()
    {
        Pane root = new Pane();

        // set size of it
        root.setPrefSize(WIDTH* TILE_SIZE   , HEIGHT * TILE_SIZE);
        root.getChildren().add(c);
        ImageView imv = new ImageView();
        //Image titlepic = new Image(Options.class.getResourceAsStream("road.png"));
        Image pausePicture = new Image("images/grid.png");
        imv.setImage(pausePicture);
        //ImagePattern pattern = new ImagePattern(titlepic);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
        root.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

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

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());
        //boolean stopControls = false;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if (ptr.getDirection().equals("v") & stopControls == false) {
                    if (event.getCode() == KeyCode.UP) {
                        ptr.moveUp();
                        System.out.println("x = " + ptr.getxPos());
                        System.out.println("y = " + ptr.getyPos());
                    } else if (event.getCode() == KeyCode.DOWN) {
                        ptr.moveDown();
                        System.out.println("x = " + ptr.getxPos());
                        System.out.println("y = " + ptr.getyPos());
                    }
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
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    Pause paused = new Pause();
                    Stage secondaryStage = new Stage();
                    secondaryStage.setTitle("Pause");
                    paused.start(secondaryStage);
                    //stopControls = true;
                }

            }
        });
        // Need to make it change that if pause = 1 then you can't handle the key events
        // Add pause with KeyCode.ESCAPE

        primaryStage.setTitle("Rush Hour"); // sets the title name
        primaryStage.setScene(scene); // places scene into primary Stage
        primaryStage.show(); // opens the java file

    }

    public ArrayList<MainPiece> getPieces(){
        return pieces;
    }

    //public void setStopControls(boolean stopControls) {
    //    this.stopControls = stopControls;
    //}


    public static void main(String[] args)
    {
        launch(args);
    }
}