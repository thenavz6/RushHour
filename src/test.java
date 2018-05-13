import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class test extends Application {
    @Override
    public void start(Stage stage) {
        //Menu men = new Menu();
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        BorderPane borderPane = new BorderPane();

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        ImageView imv = new ImageView();
        //imv.setFitHeight(250);
        //imv.setFitWidth(250);
        //imv.setPreserveRatio(true);
        Image imageCar = new Image(Options.class.getResourceAsStream("car_aqua_h.png"));
        imv.setImage(imageCar);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,false);
        borderPane.setBackground(new Background(new BackgroundImage(imageCar,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));
        //Scene scene2 = new Scene(borderPane,300,300);
        HBox pictureRegion = new HBox();

        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 3, 10);
        root.getChildren().add(gridpane	);

        //Rectangle r = new Rectangle(25,25,250,250);
        //r.setFill(Color.BLUE);
        //root.getChildren().add(r);
        //root.getChildren().add(imv);
        Button goBack = new Button();
        goBack.setText("Return");
        //btn.setOnAction(e -> men.start(stage));
        goBack.setOnAction(e -> stage.hide());
        Button tutorial = new Button();
        tutorial.setText("Tutorial");
        //StackPane layout = new StackPane();
        //root.getChildren().add(btn);
        //gridpane.add(borderPane,1,1);
        gridpane.add(goBack,1,37);
        gridpane.add(tutorial,19,37);
        stage.setTitle("Options/Testing");
        stage.setScene(scene);
        stage.show();

    }
}
