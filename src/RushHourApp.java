import javafx.application.Application;
import javafx.scene.image.Image;
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

public class RushHourApp extends Application{


    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;


    private MainPiece ptr; // keeps track of the main object;

    private Group c = new Group();


    private Parent createContent()
    {
        Pane root = new Pane();

        // set size of it
        root.setPrefSize(WIDTH* TILE_SIZE   , HEIGHT * TILE_SIZE);
        root.getChildren().add(c);

        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                if (x == 0 && y == 2){
                    MainPiece main = new MainPiece(0, 2);

                    javafx.scene.image.Image img = new Image("images/car_red_h.png");
                    main.setFill(new ImagePattern(img));

                    c.getChildren().add(main);
                    ptr = main;
                }

            }
        }
        return root;
    }

    public void getImage(Vehicle v) {
        Direction direction = v.getDirection();
        Colour colour = v.getColour();
        VehicleType type = v.getVehicleType();
        if (type == VehicleType.EMPTY || direction == Direction.EMPTY) {
            return;
        }
        if (type == VehicleType.CAR) {
            if (direction == Direction.NORTH || v.getDirection() == Direction.SOUTH) {
                javafx.scene.image.Image img = new Image("images/car" + colour + "_v.png");
            } else {
                javafx.scene.image.Image img = new Image("images/car" + colour + "_h.png");
            }
        } else {
            if (direction == Direction.NORTH || v.getDirection() == Direction.SOUTH) {
                javafx.scene.image.Image img = new Image("images/truck" + colour + "_v.png");
            } else {
                javafx.scene.image.Image img = new Image("images/truck" + colour + "_h.png");
            }
        }
        // v.setFill(new ImagePattern(img)); <- need to be shape for this to work,
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if (event.getCode() == KeyCode.RIGHT) {
                    ptr.moveRight();
                } else if (event.getCode() == KeyCode.LEFT) {
                    ptr.moveLeft();
                }

            }
        });

        primaryStage.setTitle("Rush Hour"); // sets the title name
        primaryStage.setScene(scene); // places scene into primary Stage
        primaryStage.show(); // opens the java file
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}