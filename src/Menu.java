import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import static javafx.stage.Screen.getPrimary;

/**
 * Menu Class containing the constructor
 * The class that the user first sees when the start the game up
 * Provides functionality and a GUI for a main menu for the user to use
 * Has buttons to start the game, look at the instructions and exit.
 * A combo box grabs a value for the difficulty of puzzle the user wants to play
 */
public class Menu extends Application {

    /**
     * Main Menu for the user to use to navigate and start a game to play
     * Creates the UI elements for the stage such as buttons and background images
     * Makes where each button is going to displayed in the stage and the size of the window
     * Has buttons for the user to use i.e. Play, Instructions and Exit.
     * @param primaryStage Stage to display the GUI of the main menu
     */
    @Override
    public void start(Stage primaryStage) {
        RushHourApp gameBoard = new RushHourApp();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Button play = new Button();
        play.setMinSize(230,50);
        play.setFont(new Font(25));
        play.setText("Play");

        Button instruct = new Button();
        instruct.setMinSize(230,50);
        instruct.setFont(new Font(25));
        instruct.setText("Instructions");

        Button exit = new Button();
        exit.setMinSize(230,50);
        exit.setFont(new Font(25));
        exit.setText("Exit");

        ComboBox diffBox = new ComboBox();
        diffBox.getItems().add("Easy");
        diffBox.getItems().add("Medium");
        diffBox.getItems().add("Hard");
        diffBox.getSelectionModel().selectFirst();
        diffBox.setMinSize(230,50);
        diffBox.setButtonCell(new ListCell<String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item,empty);
                if (item != null) {
                    setText(item);
                    setFont(new Font(25));
                    setAlignment(Pos.CENTER);
                }
            }
        });

        diffBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new ListCell<String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item,empty);
                        if (item != null) {
                            setText(item);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        });

        play.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    primaryStage.hide();
                    Stage secondaryStage = new Stage();
                    secondaryStage.setTitle("Rush Hour");
                    gameBoard.start(secondaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        instruct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Instructions instruct = new Instructions();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Instructions");
                instruct.start(secondaryStage);
            }
        });

        exit.setOnAction(e -> Platform.exit());

        grid.add(play, 0, 0);
        grid.add(diffBox, 0, 1);
        grid.add(instruct, 0, 2);
        grid.add(exit, 0, 3);

        ImageView imv = new ImageView();
        Image menuPicture = new Image("images/menu2.png");
        imv.setImage(menuPicture);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,false,true);
        grid.setBackground(new Background( new BackgroundImage(menuPicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

        Scene scenes = new Scene(grid,600,600);
        Rectangle2D screenBounds = getPrimary().getVisualBounds();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setX((screenBounds.getWidth() - (600)) / 2);
        primaryStage.setY((screenBounds.getHeight() - (600)) / 2);
        primaryStage.setScene(scenes);
        primaryStage.setTitle("Rush Hour");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
