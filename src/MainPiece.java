import com.sun.tools.javac.Main;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class MainPiece extends Rectangle {

    private int frontxPos;
    private int frontyPos;
    private int backxPos;
    private int backyPos;
    private int midxPos;
    private int midyPos;
    private String direction;
    private VehicleType type;

    public MainPiece(int x, int y, String orientation, VehicleType type) {
        this.direction = orientation;
        this.type = type;
        if(x == 5){
            if(type == VehicleType.car){
                this.frontxPos = (x - 1)*100;
                this.frontyPos = y;
                updateCoords();
            }else{
                this.frontxPos = (x - 2)*100;
                this.frontyPos = y;
                updateCoords();

            }
        }
        if(y == 5){
            if(type == VehicleType.car){
                this.frontxPos = x;
                this.frontyPos = (y - 1)*100;
                updateCoords();
            }else{
                this.frontxPos = x;
                this.frontyPos = (y - 2)*100;
                updateCoords();

            }
        }
        this.frontxPos = x*100;
        this.frontyPos = y*100;
        updateCoords();

        if(type == VehicleType.car) {
            if (direction.equals("v")){
                setWidth(RushHourApp.TILE_SIZE);
                setHeight(RushHourApp.TILE_SIZE * 2);
            } else {
                setWidth(RushHourApp.TILE_SIZE * 2);
                setHeight(RushHourApp.TILE_SIZE);
            }
        }else{
            if (direction.equals("v")) {
                setWidth(RushHourApp.TILE_SIZE);
                setHeight(RushHourApp.TILE_SIZE * 3);
            } else {
                setWidth(RushHourApp.TILE_SIZE * 3);
                setHeight(RushHourApp.TILE_SIZE);
            }
        }

        // places it in the determined position
        // if not it will default to the top
        relocate(frontxPos, frontyPos);
    }
    public void moveRight(){
        if(type == VehicleType.car) {
            if (this.frontxPos == 400) {
                return;
            }
        }else if(this.frontxPos == 300) {
            return;
        }
        int newX = (this.frontxPos) + 100;
        for(MainPiece item: RushHourApp.pieces){
            if(this.type == VehicleType.car) {
                if (newX + 100 == item.frontxPos && this.frontyPos == item.frontyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (newX + 100 == item.backxPos && this.frontyPos == item.backyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (item.type == VehicleType.truck) {
                    if (newX + 100 == item.midxPos && this.frontyPos == item.midyPos) {
                        if (!this.equals(item))
                            return;
                    }
                }
            }else{
                if (newX + 200 == item.frontxPos && this.frontyPos == item.frontyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (newX + 200 == item.backxPos && this.frontyPos == item.backyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (item.type == VehicleType.truck) {
                    if (newX + 200 == item.midxPos && this.frontyPos == item.midyPos) {
                        if (!this.equals(item))
                            return;
                    }
                }

            }
        }

        this.frontxPos = newX;
        updateCoords();
        //System.out.println(newX);

        relocate(newX, this.frontyPos);

    }
    public void moveLeft(){
        if (this.frontxPos == 0) {
            return;
        }
        int newX = (this.frontxPos) - 100;
        for(MainPiece item: RushHourApp.pieces){
            if (newX == item.frontxPos && this.frontyPos == item.frontyPos) {
                if (!this.equals(item))
                    return;
            }
            if (newX == item.backxPos && this.frontyPos == item.backyPos) {
                if (!this.equals(item))
                    return;
            }
            if (item.type == VehicleType.truck) {
                if (newX == item.midxPos && this.frontyPos == item.midyPos) {
                    if (!this.equals(item))
                        return;
                }
            }
        }
        this.frontxPos = newX;
        updateCoords();
        //System.out.println(newX);

        relocate(newX, this.frontyPos);
    }

    public void moveUp(){
        if (this.frontyPos == 0) {
            return;
        }
        int newY = (this.frontyPos) - 100;
        for(MainPiece item: RushHourApp.pieces){
            if(this.frontxPos == item.frontxPos && newY == item.frontyPos){
                if(!this.equals(item))
                    return;
            }
            if(this.frontxPos == item.backxPos && newY == item.backyPos){
                if(!this.equals(item))
                    return;
            }
            if(item.type == VehicleType.truck){
                if(this.frontxPos == item.midxPos && newY == item.midyPos){
                    if(!this.equals(item))
                        return;
                }
            }
        }
        this.frontyPos = newY;
        updateCoords();
        //System.out.println(newX);

        relocate(this.frontxPos, newY);
    }

    public void moveDown(){
        if(type == VehicleType.car) {
            if (this.frontyPos == 400) {
                return;
            }
        }else if(this.frontyPos == 300) {
            return;
        }
        int newY = (this.frontyPos) + 100;
        for(MainPiece item: RushHourApp.pieces){
            if(this.type == VehicleType.car) {
                if (this.frontxPos == item.frontxPos && newY + 100 == item.frontyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (this.frontxPos == item.backxPos && newY + 100 == item.backyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (item.type == VehicleType.truck) {
                    if (this.frontxPos == item.midxPos && newY + 100 == item.midyPos) {
                        if (!this.equals(item))
                            return;
                    }
                }
            }else{
                if (this.frontxPos == item.frontxPos && newY + 200 == item.frontyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (this.frontxPos == item.backxPos && newY + 200 == item.backyPos) {
                    if (!this.equals(item))
                        return;
                }
                if (item.type == VehicleType.truck) {
                    if (this.frontxPos == item.midxPos && newY + 200 == item.midyPos) {
                        if (!this.equals(item))
                            return;
                    }
                }
            }
        }
        this.frontyPos = newY;
        updateCoords();
        //System.out.println(newX);

        relocate(this.frontxPos, newY);
    }

    public void updateCoords(){
        if(this.direction.equals("v")){
            this.backxPos = this.frontxPos;
            if(this.type == VehicleType.car) {
                this.backyPos = this.frontyPos + 100;
            }else{
                this.backyPos = this.frontyPos + 200;
                this.midyPos = this.frontyPos + 100;
                this.midxPos = this.frontxPos;
            }
        }else if(this.direction.equals("h")){
            this.backyPos = this.frontyPos;
            if(this.type == VehicleType.car) {
                this.backxPos = this.frontxPos + 100;
            }else{
                this.backxPos = this.frontxPos + 200;
                this.midxPos = this.frontxPos + 100;
                this.midyPos = this.frontyPos;
            }
        }
    }

    public boolean horizontalcollisionCheck(int newX){
        for(MainPiece item: RushHourApp.pieces){
            if(item.type == VehicleType.car) {
                if (((newX + 1 == item.frontxPos && this.frontyPos == item.frontyPos)  || (newX + 1 == item.backxPos && this.frontyPos == item.backyPos)) && !item.equals(this)){
                    return false;
                }
            }else{
                if (((newX + 2 == item.frontxPos && this.frontyPos == item.frontyPos)  || (newX + 2 == item.backxPos && this.frontyPos == item.backyPos) || (newX + 2 == item.midxPos && this.frontyPos== item.midyPos)) && !item.equals(this)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verticalcollisionCheck(int newY){
        for(MainPiece item: RushHourApp.pieces){
            if(item.type == VehicleType.car) {
                if (((this.frontxPos == item.frontxPos && newY + 1 == item.frontyPos)  || (this.frontxPos == item.backxPos && newY + 1 == item.backyPos)) && !item.equals(this)){
                    return false;
                }
            }else{
                if (((this.frontxPos == item.frontxPos && newY + 2 == item.frontyPos)  || (this.frontxPos == item.backxPos && newY + 2 == item.backyPos) || (this.frontxPos == item.midxPos && newY + 2 == item.midyPos)) && !item.equals(this)){
                    return false;
                }
            }
        }
        return true;
    }

    public String getDirection() {
        return direction;
    }

    public int getxPos() {
        return backxPos;
    }

    public int getyPos() {
        return backyPos;
    }
}
