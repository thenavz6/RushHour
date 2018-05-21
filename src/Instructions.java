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

public class Instructions extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15,20,10,10));
        Group root = new Group();
        VBox topBox = new VBox();

        Text logo = new Text("Controls");
        topBox.getChildren().add(logo);
        topBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topBox);

        Rectangle r = new Rectangle(25,25,480,400);
        r.setFill(Color.BLUE);
        root.getChildren().add(r);
        borderPane.setCenter(root);

        VBox box = new VBox();
        Button back = new Button();
        back.setText("Return");
        back.setMinWidth(100);
        back.setOnAction(e -> stage.hide());
        box.getChildren().add(back);
        borderPane.setBottom(box);

        Scene scene = new Scene(borderPane,500,500);
        stage.setTitle("test");
        stage.setScene(scene);
        stage.show();

    }
}
