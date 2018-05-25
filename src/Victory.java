import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import java.util.concurrent.TimeUnit;
import static javafx.stage.Screen.getPrimary;

/**
 * Victory Class
 * Appears when the user has reached the exit condition on the game
 * Shows the time and moves taken to reach the exit condition
 * and presents next, restart and menu options for the user
 * Next generates a new puzzle with the same difficulty as the one played
 * Restart puts the grid back to the original state
 * Menu returns the user back to the main menu.
 */
public class Victory extends Application {

    /**
     * Start function for Victory, gets run when the class is called
     * Sets all the UI features for the stage and button interaction for the buttons
     * Has buttons to go to a new puzzle, restart the current puzzle and go back to main menu.
     * @param stage Main stage that the class is displayed on
     */
    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15,20,10,10));
        RushHourApp statGetter = new RushHourApp();
        Pause timeGet = new Pause();
        VBox botLeftBox = new VBox();

        ImageView imv = new ImageView();
        Image pausePicture = new Image("images/victory.png");
        imv.setImage(pausePicture);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
        borderPane.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

        VBox box = new VBox();

        box.setPadding(new Insets(15,20,10,10));
        box.setSpacing(10);

        Button back = new Button();
        back.setText("Menu");
        back.setMinWidth(100);
        back.setOnAction(e -> {
            Stage gameStage = statGetter.getTestStage();
            timeGet.setPauseTime(0); // same for restart and new level
            statGetter.setStopControls(false);
            gameStage.close();
            stage.hide();
            Menu mainMenu = new Menu();
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Menu");
            mainMenu.start(secondaryStage);
        });

        Text moveNumber = new Text();
        int movesTaken = statGetter.getMovesTaken();
        moveNumber.setFont(new Font(25));
        moveNumber.setText(Integer.toString(movesTaken));

        Text timeNumber = new Text();
        long endTime = System.nanoTime();
        long minuteTime = TimeUnit.NANOSECONDS.toMinutes((endTime - statGetter.getStartTime() - timeGet.getPauseTime()));
        long secondTime = TimeUnit.NANOSECONDS.toSeconds((endTime - statGetter.getStartTime() - timeGet.getPauseTime()));
        timeNumber.setText(minuteTime + " M " + secondTime + " S");
        timeNumber.setFont(new Font(25));
        botLeftBox.getChildren().add(moveNumber);
        botLeftBox.getChildren().add(timeNumber);

        Button restart = new Button();
        restart.setText("Restart");
        restart.setMinWidth(100);

        Button nextPuzzle = new Button();
        nextPuzzle.setText("New Puzzle");
        nextPuzzle.setMinWidth(100);
        Label minuteLabel = new Label(Integer.toString(movesTaken));
        minuteLabel.setFont(new Font(25));
        minuteLabel.setTranslateY(85);
        minuteLabel.setTranslateX(-400);
        minuteLabel.setTextFill(Color.web("#000000"));
        Label secondLabel = new Label(minuteTime + " M " + secondTime + " S");
        secondLabel.setTextFill(Color.web("#000000"));
        secondLabel.setFont(new Font(25));
        secondLabel.setTranslateY(90);
        secondLabel.setTranslateX(-340);

        box.getChildren().add(minuteLabel);
        box.getChildren().add(secondLabel);
        box.getChildren().add(nextPuzzle);
        box.getChildren().add(restart);
        box.getChildren().add(back);
        box.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(box);

        Scene scene = new Scene(borderPane,600,300);
        Rectangle2D screenBounds = getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - (600)) / 2);
        stage.setY((screenBounds.getHeight() - (300)) / 2);
        stage.setResizable(false);
        stage.setTitle("Victory");
        stage.setScene(scene);
        stage.show();

    }
}
