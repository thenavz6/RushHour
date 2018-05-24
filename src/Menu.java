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
import javafx.util.Callback;

import static javafx.stage.Screen.getPrimary;

public class Menu extends Application {

    @Override
    public void start(Stage primaryStage) {
        RushHourApp test = new RushHourApp();

        Button play = new Button();
        play.setMinSize(230,50);
        play.setFont(new Font(25));
        play.setText("Play");

        Button instruct = new Button();
        //instruct.setMinWidth(100);
        instruct.setMinSize(230,50);
        instruct.setFont(new Font(25));
        instruct.setText("Instructions");

        Button option = new Button();
        //option.setMinWidth(100);
        option.setMinSize(230,50);
        option.setFont(new Font(25));
        option.setText("Options");

        Button exit = new Button();
        //exit.setMinWidth(100);
        exit.setMinSize(230,50);
        exit.setFont(new Font(25));
        exit.setText("Exit");

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("Easy");
        comboBox.getItems().add("Medium");
        comboBox.getItems().add("Hard");
        comboBox.getSelectionModel().selectFirst();
        comboBox.setMinSize(230,50);
        comboBox.setButtonCell(new ListCell<String>() {
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
        comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new ListCell<String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item,empty);
                        if (item != null) {
                            setText(item);
                            setAlignment(Pos.CENTER);
                            //setPadding(new Insets(3,3,3,0));
                        }
                    }
                };
            }
        });
        MenuItem menuItem1 = new MenuItem("Easy");
        MenuItem menuItem2 = new MenuItem("Medium");
        MenuItem menuItem3 = new MenuItem("Hard");
        MenuButton menuButton = new MenuButton("Difficulty", null, menuItem1, menuItem2, menuItem3);
        menuButton.setAlignment(Pos.CENTER);
        menuButton.setMinWidth(100);
        menuItem1.setOnAction(e -> {
            menuButton.setText(menuItem1.getText());
        });
        menuItem2.setOnAction(e -> {
            menuButton.setText(menuItem2.getText());
        });
        menuItem3.setOnAction(e -> {
            menuButton.setText(menuItem3.getText());
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(10, 10, 10, 10));
        exit.setOnAction(e -> Platform.exit());
        instruct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Instructions instruct = new Instructions();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Instructions");
                instruct.start(secondaryStage);
            }
        });
        play.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    primaryStage.hide();
                    Stage secondaryStage = new Stage();
                    secondaryStage.setTitle("Rush Hour");
                    test.start(secondaryStage);
                    //test.start(primaryStage)
				} catch (Exception e) {
					e.printStackTrace();
				}
                
            }
        });
        option.setOnAction( new EventHandler<ActionEvent>() { // change this later
            @Override
            public void handle(ActionEvent event) {
                try {
                    Victory vict = new Victory();
                    Stage secondaryStage = new Stage();
                    secondaryStage.setTitle("Victory");
                    vict.start(secondaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
                
            }
        });

        grid.add(play, 0, 0);
        grid.add(comboBox, 0, 1);
        grid.add(instruct, 0, 2);
        grid.add(option,0,3);
        grid.add(exit, 0, 4);

        ImageView imv = new ImageView();
        Image titlePicture = new Image("images/menu2.png");
        imv.setImage(titlePicture);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,false,true);
        grid.setBackground(new Background( new BackgroundImage(titlePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

        Scene scenes = new Scene(grid,600,600);
        scenes.setOnKeyPressed(ke -> { // remove this later
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.ESCAPE)) {
                Pause paused = new Pause();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Pause");
                paused.start(secondaryStage);
            }
        });

        Rectangle2D screenBounds = getPrimary().getVisualBounds();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setX((screenBounds.getWidth() - (600)) / 2);
        primaryStage.setY((screenBounds.getHeight() - (600)) / 2);
        primaryStage.setScene(scenes);
        primaryStage.setTitle("Rush Hour");
        //primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
