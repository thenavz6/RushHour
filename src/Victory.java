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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Victory extends Application {
    @Override
    public void start(Stage stage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15,20,10,10));
        //Group root = new Group();
        VBox botLeftBox = new VBox();

        //Text logo = new Text("Controls");
        //topBox.getChildren().add(logo);
        //topBox.setAlignment(Pos.CENTER);
        //borderPane.setTop(topBox);

        //Rectangle r = new Rectangle(25,25,480,400);
        //r.setFill(Color.BLUE);
        //root.getChildren().add(r);
        //borderPane.setCenter(root);

        //imageView.setImage(new Image(this.getClass().getResource("java.gif").toExternalForm()));
        ImageView imv = new ImageView();
        //Image titlepic = new Image(Options.class.getResourceAsStream("road.png"));
        //Image pausePicture = new Image("images/victory.png");
        Image pausePicture = new Image("images/victory.gif");
        imv.setImage(pausePicture);
        //imv.setImage(pausePicture);
        //ImagePattern pattern = new ImagePattern(titlepic);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true);
        borderPane.setBackground(new Background( new BackgroundImage(pausePicture, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

        VBox box = new VBox();
        box.setPadding(new Insets(15,20,10,10));
        box.setSpacing(10);
        Button back = new Button();
        back.setText("Menu");
        back.setMinWidth(100);
        back.setOnAction(e -> stage.hide());
        // Close game stage also

        Text moveNumber = new Text();
        moveNumber.setText("?");

        Text timeNumber = new Text();
        timeNumber.setText("?");

        botLeftBox.getChildren().add(moveNumber);
        botLeftBox.getChildren().add(timeNumber);
        //botLeftBox.set

        Button restart = new Button();
        restart.setText("Restart");
        restart.setMinWidth(100);

        Button nextPuzzle = new Button();
        nextPuzzle.setText("New Puzzle");
        nextPuzzle.setMinWidth(100);

        // close stage and restart it or
        // run the generator again for the stage

        box.getChildren().add(botLeftBox);
        box.getChildren().add(nextPuzzle);
        box.getChildren().add(restart);
        box.getChildren().add(back);
        box.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(box);

        Scene scene = new Scene(borderPane,600,300);
        stage.setTitle("Victory");
        stage.setScene(scene);
        stage.show();

    }
}
