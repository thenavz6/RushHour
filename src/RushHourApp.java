import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.stage.Stage;

import java.awt.*;

public class RushHourApp extends Application{

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;

    private Group tileGroup = new Group();

    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup);
        root.setStyle("-fx-background-color: darkGray;");

        for(int y = 0; y < HEIGHT; y++){
            for(int x = 0; x < WIDTH; x++){

                Grid grid = new Grid(x,y);

                tileGroup.getChildren().add(grid);

            }
        }

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("RushHour");


        // handle movement

        // TODO: something that allows you to select vehicle. this depends on how we're actually displaying them.

        // right now this does nothing
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP: break;
                    case DOWN: break;
                    case LEFT: break;
                    case RIGHT: break;
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
