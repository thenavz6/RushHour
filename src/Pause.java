import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import static javafx.stage.Screen.getPrimary;

/**
 * Pause Class
 * When the user wants to pause the game, they press the ESCAPE button
 * This class provides a stage to give options for the user to do various functions
 * It has buttons to go restart the level, hints for the level, instructions for the game
 * and return back to the main menu. Creates a GUI for the user to use.
 */
public class Pause extends Application {
        private static long pauseTime;

    /**
     * Start function for Pause, gets run once the player presses ESCAPE during gameplay
     * Creates a UI for the user to use. Has buttons for the user to use i.e.
     * Resume, Hints, Restart, Instructions and Exit.
     * @param primaryStage A stage that displays the UI elements
     */
    @Override
        public void start(Stage primaryStage) {
            long imPaused = System.nanoTime();
            RushHourApp test = new RushHourApp();

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            primaryStage.setTitle("Rush Hour");
            Button play = new Button();
            play.setMinWidth(100);
            play.setText("Resume"); // -> Makes stage close and resume controls
            Button hints = new Button();
            hints.setText("Hints"); // -> Goes to a information box, showing the hint
            hints.setMinWidth(100);
            Button restart = new Button();
            restart.setText("Restart"); // Gets board information from beginning or complete new puzzle?
            restart.setMinWidth(100);
            Button instruct = new Button();
            instruct.setMinWidth(100);
            instruct.setText("Instructions"); // Goes to instructions
            Button exit = new Button();
            exit.setMinWidth(100);
            exit.setText("Menu"); // Closes the stage and goes back to Menu


            ImageView imv = new ImageView();
            Image pausePicture = new Image("images/road.png");
            imv.setImage(pausePicture);
            BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
            grid.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

            exit.setOnAction(e -> {
                Stage gameStage = test.getTestStage();
                test.setStopControls(false);
                gameStage.close();
                primaryStage.hide();
                Menu mainMenu = new Menu();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Menu");
                mainMenu.start(secondaryStage);
            });

            instruct.setOnAction(event -> {
                Instructions instruct1 = new Instructions();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Instructions");
                instruct1.start(secondaryStage);
            });

            hints.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Hints");
                alert.setHeaderText(null);
                alert.setContentText("Hacks");
                alert.showAndWait();
            });

            play.setOnAction(event -> {
                try {
                    test.setStopControls(false);
                    pauseTime = pauseTime + (System.nanoTime() - imPaused);
                    primaryStage.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Image image = new Image("images/paused.png");
            ImageView pausedImage = new ImageView();
            pausedImage.setImage(image);
            grid.add(pausedImage,0,0);
            grid.add(play, 0, 1);
            grid.add(hints, 0, 2);
            grid.add(restart,0,3);
            grid.add(instruct, 0, 4);
            grid.add(exit, 0, 5);

            Scene scenes = new Scene(grid,250,300);
            primaryStage.setOnCloseRequest(e -> {
                test.setStopControls(false);
                setPauseTime(0); // do the same for restart
                primaryStage.hide();
            });

            Rectangle2D screenBounds = getPrimary().getVisualBounds();
            primaryStage.setX((screenBounds.getWidth() - (250)) / 2);
            primaryStage.setY((screenBounds.getHeight() - (300)) / 2);
            primaryStage.setScene(scenes);
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    /**
     * Getter for how long the user has paused
     * @return pauseTime
     */
    public long getPauseTime() {
        return pauseTime;
    }

    /**
     * Sets pauseTime variable
     * @param time
     */
    public void setPauseTime(long time) {
            pauseTime = time;
    }

}


