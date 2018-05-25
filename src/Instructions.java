import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafx.stage.Screen.getPrimary;

/**
 * Instructions Class
 * Provides a set of instructions for the user on how to play the game
 * Has a visual element and a return button that closes the stage
 */
public class Instructions extends Application {
    /**
     * Start function for when the user clicks on the instructions button
     * Sets out all the UI and button elements for the stage.
     * Creates a background image that displays how to play the game
     * and a button that returns the user that closes the stage.
     * @param stage Stage for the UI elements
     */
    @Override
    public void start(Stage stage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15,20,10,10));

        ImageView imv = new ImageView();
        Image pausePicture = new Image("images/controls2.png");
        imv.setImage(pausePicture);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
        borderPane.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

        VBox box = new VBox();
        Button back = new Button();
        back.setGraphic(new ImageView("images/barrow.png"));
        back.setBackground(null);
        back.setOnAction(e -> stage.hide());
        box.getChildren().add(back);
        borderPane.setBottom(box);

        Scene scene = new Scene(borderPane,630,500);
        Rectangle2D screenBounds = getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - (630)) / 2);
        stage.setY((screenBounds.getHeight() - (500)) / 2);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Instructions");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}
