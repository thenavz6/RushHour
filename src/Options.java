import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Options extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Menu men = new Menu();
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        ImageView imv = new ImageView();
        Image imageCar = new Image(Options.class.getResourceAsStream("car_aqua_h.png"));
        imv.setImage(imageCar);

        HBox pictureRegion = new HBox();
        
        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 3, 10);
        root.getChildren().add(gridpane	);
        
        //Rectangle r = new Rectangle(25,25,250,250);
        //r.setFill(Color.BLUE);
        //root.getChildren().add(r);
        //root.getChildren().add(imv);
        Button btn = new Button();
        btn.setText("Return");
        btn.setOnAction(e -> men.start(stage));
        //StackPane layout = new StackPane();
        root.getChildren().add(btn);
       
        stage.setTitle("Options/Testing");
        stage.setScene(scene);
        stage.show();
		
	}

}
