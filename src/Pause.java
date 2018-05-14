import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Pause extends Application {
        // Try border panes
        @Override
        public void start(Stage primaryStage) {
            //BorderPane border = new BorderPane();
            RushHourApp test = new RushHourApp();
            Options test2 = new Options();

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
            //Image imageTest = new Image(getClass().getResourceAsStream("car_green_h.png"));
            //option.setGraphic(new ImageView(imageTest));
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
            //Image titlepic = new Image(Options.class.getResourceAsStream("road.png"));
            Image pausePicture = new Image("images/road.png");
            imv.setImage(pausePicture);
            //ImagePattern pattern = new ImagePattern(titlepic);
            BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
            grid.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));
            exit.setOnAction(e -> primaryStage.hide());
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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
            Text logo = new Text("Paused");
            VBox textPos = new VBox();
            textPos.getChildren().addAll(logo);
            textPos.setAlignment(Pos.CENTER);
            //logo.setTextAlignment(centre);
            grid.add(textPos,0,0);
            //grid.add(logo, 0, 0);
            grid.add(play, 0, 1);
            grid.add(hints, 0, 2);
            grid.add(restart,0,3);
            grid.add(instruct, 0, 4);
            grid.add(option,0,5);
            grid.add(exit, 0, 6);

            Scene scenes = new Scene(grid,250,300);
            //scenes.setFill(pattern);
            primaryStage.setScene(scenes);
            //primaryStage.setScene(new Scene(root, 300, 250));
            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }

    }


