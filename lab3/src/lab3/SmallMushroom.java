package lab3;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class SmallMushroom {
    static Paint capColor = Color.rgb(185, 13, 11);
    static Paint stipeColor = Color.rgb(245, 247, 196);

    static public Polygon getStipe() {
        Polygon stipe = new Polygon(
                240.0, 450.0,
                250.0, 445.0,
                260.0, 435.0,
                260.0, 420.0,
                250.0, 400.0,
                245.0, 355.0,
                218.0, 355.0,
                217.0, 400.0,
                210.0, 420.0,
                220.0, 440.0
        );
        stipe.setFill(stipeColor);
        stipe.setStroke(Color.BLACK);
        stipe.setStrokeType(StrokeType.OUTSIDE);
        return stipe;
    }

    static public Polygon getCap() {
        Polygon cap = new Polygon(
                245.0, 355.0,
                255.0, 345.0,
                265.0, 330.0,
                265.0, 315.0,
                255.0, 300.0,
                240.0, 290.0,
                220.0, 288.0,
                210.0, 295.0,
                200.0, 305.0,
                195.0, 315.0,
                195.0, 335.0,
                201.0, 352.0,
                218.0, 360.0
        );
        cap.setFill(capColor);
        cap.setStroke(Color.BLACK);
        cap.setStrokeType(StrokeType.OUTSIDE);
        return cap;
    }

    static public Circle[] getCapStains() {
        Circle[] stains = new Circle[7];
        stains[0] = getStain(232.0, 340.0, 5);
        stains[1] = getStain(247.0, 322.0, 2);
        stains[2] = getStain(227.0, 320.0, 5);
        stains[3] = getStain(210.0, 330.0, 4);
        stains[4] = getStain(245.0, 300.0, 3);
        stains[5] = getStain(225.0, 300.0, 4);
        stains[6] = getStain(250.0, 332.0, 3);
        return stains;
    }

    static private Circle getStain(double x, double y, double r) {
        Circle stain = new Circle(x, y, r);
        stain.setFill(stipeColor);
        stain.setStroke(Color.BLACK);
        stain.setStrokeType(StrokeType.OUTSIDE);
        return stain;
    }
}
