import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid extends Rectangle{

    public Grid(int x, int y){

        setWidth(RushHourApp.TILE_SIZE - 5);
        setHeight(RushHourApp.TILE_SIZE - 5);
        setArcHeight(50);
        setArcWidth(50);

        relocate(x * RushHourApp.TILE_SIZE, y * RushHourApp.TILE_SIZE);

        setFill(Color.valueOf("#c5c5c5"));


    }

}
