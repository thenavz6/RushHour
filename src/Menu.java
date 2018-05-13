import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.paint.ImagePattern;

public class Menu extends Application {
	// Try border panes
    @Override
    public void start(Stage primaryStage) {
        //BorderPane border = new BorderPane();
        RushHourApp test = new RushHourApp();
        Options test2 = new Options();
    		
        primaryStage.setTitle("Rush Hour");

        Button play = new Button();
        play.setMinWidth(100);
        play.setText("Play");
        Button instruct = new Button();
        instruct.setMinWidth(100);
        instruct.setText("Instructions");
        Button option = new Button();
        //Image imageTest = new Image(getClass().getResourceAsStream("car_green_h.png"));
        //option.setGraphic(new ImageView(imageTest));
        option.setMinWidth(100);
        option.setText("Options");
        Button exit = new Button();
        exit.setMinWidth(100);
        exit.setText("Exit");
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("Easy");
        comboBox.getItems().add("Medium");
        comboBox.getItems().add("Hard");
        comboBox.getSelectionModel().selectFirst();
        comboBox.minWidth(100);
        comboBox.setButtonCell(new ListCell<String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item,empty);
                if (item != null) {
                    setText(item);
                    setAlignment(Pos.CENTER_RIGHT);
                    //Insets old = getPadding();
                    //setPadding(new Insets(old.getTop(),0,old.getBottom(),0));
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
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        //grid.setMargin(grid,new Insets(0,0,0,0));
        exit.setOnAction(e -> Platform.exit());
        instruct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //test tester = new test();
                Instructions instruct = new Instructions();

                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Instructions");
                instruct.start(secondaryStage);
                //Alert alert = new Alert(AlertType.INFORMATION);
                //alert.setTitle("Instructions");
                //alert.setHeaderText(null);
                //alert.setContentText("Howdy");
                //alert.showAndWait();
            }
        });
        play.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
					test.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
                
            }
        });
        option.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
					//test2.start(primaryStage);
					Pause paused = new Pause();
                    Stage secondaryStage = new Stage();
                    secondaryStage.setTitle("Pause");
                    paused.start(secondaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        });
        Text logo = new Text("Rush Hour");
        VBox textPos = new VBox();
        textPos.getChildren().addAll(logo);
        textPos.setAlignment(Pos.CENTER);
        //logo.setTextAlignment(centre);
        //grid.add(textPos,0,0);
        //grid.add(logo, 0, 0);
        grid.add(play, 0, 0);
        grid.add(comboBox, 0, 1);
        grid.add(instruct, 0, 2);
        grid.add(option,0,3);
        grid.add(exit, 0, 4);
        ImageView imv = new ImageView();
        Image titlepic = new Image(Options.class.getResourceAsStream("title.png"));

        imv.setImage(titlepic);
        ImagePattern pattern = new ImagePattern(titlepic);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,false,true);
        grid.setBackground(new Background( new BackgroundImage(titlepic, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));
        //grid.set;
        Scene scenes = new Scene(grid,370,370);
        //scenes.setFill(pattern);
        scenes.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.ESCAPE)) {
                Pause paused = new Pause();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Pause");
                paused.start(secondaryStage);
            }
        });
        primaryStage.setScene(scenes);
        //primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
