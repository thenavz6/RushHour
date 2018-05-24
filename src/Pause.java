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

import static javafx.stage.Screen.getPrimary;


public class Pause extends Application {
        // Try border panes
        @Override
        public void start(Stage primaryStage) {
            RushHourApp test = new RushHourApp();

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
            Button option = new Button();
            option.setMinWidth(100);
            option.setText("Options"); // Goes to options
            Button exit = new Button();
            exit.setMinWidth(100);
            exit.setText("Menu"); // Closes the stage and goes back to Menu

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            ImageView imv = new ImageView();
            Image pausePicture = new Image("images/road.png");
            imv.setImage(pausePicture);
            BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
            grid.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));
            exit.setOnAction(e -> {
                Stage gameStage = test.getTestStage();
                //test.setStopControls(false);
                gameStage.close();
                primaryStage.hide();
                Menu mainMenu = new Menu();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Menu");
                mainMenu.start(secondaryStage);
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
            hints.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Hints");
                    alert.setHeaderText(null);
                    alert.setContentText("Hacks");
                    alert.showAndWait();
                }
            });
            play.setOnAction( new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        //test.setStopControls(false);
                        primaryStage.hide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            option.setOnAction( new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Options option = new Options();
                        Stage secondaryStage = new Stage();
                        secondaryStage.setTitle("Options");
                        option.start(secondaryStage);
                        //test2.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            Text logo = new Text("Paused");
            VBox textPos = new VBox();
            textPos.getChildren().addAll(logo);
            textPos.setAlignment(Pos.CENTER);
            grid.add(textPos,0,0);
            grid.add(play, 0, 1);
            grid.add(hints, 0, 2);
            grid.add(restart,0,3);
            grid.add(instruct, 0, 4);
            grid.add(option,0,5);
            grid.add(exit, 0, 6);

            Scene scenes = new Scene(grid,250,300);
            primaryStage.setOnCloseRequest(e -> {
                //test.setStopControls(false);
                primaryStage.hide();
            });
            Rectangle2D screenBounds = getPrimary().getVisualBounds();
            primaryStage.setX((screenBounds.getWidth() - (250)) / 2);
            primaryStage.setY((screenBounds.getHeight() - (300)) / 2);
            primaryStage.setScene(scenes);
            //primaryStage.setTitle("Paused");
            //primaryStage.setScene(new Scene(root, 300, 250));
            primaryStage.show();
        }


    }


