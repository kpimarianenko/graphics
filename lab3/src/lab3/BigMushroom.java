package lab3;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class BigMushroom {
    static Paint capColorPrimary = Color.rgb(185, 13, 11);
    static Paint stipeColor = Color.rgb(245, 247, 196);

    static public Polygon getLowerPartOfStipe() {
        Polygon stipe = new Polygon(
                110.0, 420.0,
                120.0, 435.0,
                140.0, 440.0,
                155.0, 435.0,
                155.0, 250.0,
                156.0, 430.0,
                165.0, 400.0,
                160.0, 240.0,
                120.0, 240.0
        );
        stipe.setFill(stipeColor);
        stipe.setStroke(Color.BLACK);
        stipe.setStrokeType(StrokeType.OUTSIDE);
        return stipe;
    }

    static public Polygon getUpperPartOfStipe() {
        Polygon stipe = new Polygon(
                160.0, 247.0,
                170.0, 245.0,
                173.0, 240.0,
                160.0, 210.0,
                157.0, 185.0,
                120.0, 185.0,
                110.0, 225.0,
                100.0, 237.0,
                100.0, 240.0,
                120.0, 247.0,
                130.0, 243.0,
                140.0, 255.0,
                150.0, 245.0,
                155.0, 250.0
        );
        stipe.setFill(stipeColor);
        stipe.setStroke(Color.BLACK);
        stipe.setStrokeType(StrokeType.OUTSIDE);
        return stipe;
    }

    static public Polygon getCap() {
        Polygon stipe = new Polygon(
                157.0, 188.0,
                197.0, 183.0,
                220.0, 175.0,
                240.0, 155.0,
                250.0, 140.0,
                250.0, 135.0,
                240.0, 125.0,
                220.0, 115.0,
                220.0, 115.0,
                195.0, 100.0,
                180.0, 92.0,
                150.0, 92.0,
                128.0, 102.0,
                90.0, 128.0,
                50.0, 150.0,
                45.0, 153.0,
                42.0, 163.0,
                42.0, 175.0,
                120.0, 185.0
        );
        stipe.setFill(capColorPrimary);
        stipe.setStroke(Color.BLACK);
        stipe.setStrokeType(StrokeType.OUTSIDE);
        return stipe;
    }

    static public Ellipse[] getCapStains() {
        Ellipse[] stains = new Ellipse[10];
        stains[0] = getStain(150.0, 110.0, 15, 5);
        stains[1] = getStain(130.0, 130.0, 14, 6);
        stains[2] = getStain(110.0, 150.0, 13, 5);
        stains[3] = getStain(170.0, 135.0, 12, 6);
        stains[4] = getStain(205.0, 120.0, 11, 6);
        stains[5] = getStain(150.0, 160.0, 12, 7);
        stains[6] = getStain(115.0, 170.0, 13, 6);
        stains[7] = getStain(190.0, 165.0, 14, 5);
        stains[8] = getStain(210.0, 145.0, 15, 6);
        stains[9] = getStain(70.0, 165.0, 14, 7);
        return stains;
    }

    static private Ellipse getStain(double x, double y, double w, double h) {
        Ellipse stain = new Ellipse(x, y, w, h);
        stain.setFill(stipeColor);
        stain.setStroke(Color.BLACK);
        stain.setStrokeType(StrokeType.OUTSIDE);
        return stain;
    }
}
