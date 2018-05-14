import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class MainPiece extends Rectangle {

    private int xPos;
    private int yPos;

    public MainPiece(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        System.out.println("x cooridnate =" + x + " " + "Y coordinate = " + y);
        setWidth(RushHourApp.TILE_SIZE * 2);
        setHeight(RushHourApp.TILE_SIZE);

        // places it in the determined position
        // if not it will default to the top

        System.out.println("hello");
        relocate(x * RushHourApp.TILE_SIZE, y * RushHourApp.TILE_SIZE);
        setFill(Color.valueOf("#ff0000"));
    }
    public void moveRight(){
        int newX = (this.xPos) + 100;
        System.out.println("x cooridnate =" + this.xPos + " " + "Y coordinate = " + this.yPos);
        this.xPos = newX;
        //System.out.println(newX);
        relocate(newX, this.yPos*RushHourApp.TILE_SIZE);

    }
    public void moveLeft(){
        int newX = (this.xPos) - 100;
        System.out.println("x cooridnate =" + this.xPos + " " + "Y coordinate = " + this.yPos);
        this.xPos = newX;
        //System.out.println(newX);
        relocate(newX, this.yPos*RushHourApp.TILE_SIZE);
    }
}
