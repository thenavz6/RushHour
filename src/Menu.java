import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
        option.setMinWidth(100);
        option.setText("Options");
        Button exit = new Button();
        exit.setMinWidth(100);
        exit.setText("Exit");
        MenuItem menuItem1 = new MenuItem("Easy");
        
        MenuItem menuItem2 = new MenuItem("Medium");
        MenuItem menuItem3 = new MenuItem("Hard");
        MenuButton menuButton = new MenuButton("Difficulty", null, menuItem1, menuItem2, menuItem3);
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
        exit.setOnAction(e -> Platform.exit());
        instruct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Instructions");
                alert.setHeaderText(null);
                alert.setContentText("Howdy");
                alert.showAndWait();
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
					test2.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        });
        Text logo = new Text("Rush Hour");
        grid.add(logo, 0, 0);
        grid.add(play, 0, 1);
        grid.add(menuButton, 1, 1);
        grid.add(instruct, 0, 2);
        grid.add(option,0,3);
        grid.add(exit, 0, 4);
        
        Scene scenes = new Scene(grid,300,275);
        primaryStage.setScene(scenes);
        //primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
