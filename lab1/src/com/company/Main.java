package com.company;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    Paint rectangleColor = Color.color(0.12, 1, 0.05);
    Paint triangleColor = Color.color(0.12, 0.12, 0.69);
    double w, h, cx, cy, p = 10;
    Group root;
    Scene scene;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        initScreenBounds();
        init(primaryStage, true);

        appendPolygon(new Double[] {
                cx + p, cy + p,
                w / 4, cy + cy / 16,
                w / 5, h / 8,
                cx - cx / 16, h / 16
        }, rectangleColor, true);

        appendPolygon(new Double[] {
                cx - p, cy + p,
                cx + cx / 8, h / 7,
                w * 0.8, h / 4,
                w * 0.67, h * 0.67
        }, rectangleColor, true);

        appendPolygon(new Double[] {
                cx, cy - p,
                w * 0.6, h * 0.8,
                0.4 * w, 0.9 * h,
                0.3 * w, 0.65 * h
        }, rectangleColor, true);

        appendPolygon(new Double[] {
                cx / 2 + w / 10, cy / 2 + h / 16,
                cx / 2 + w * 0.4, cy / 2 + h / 8,
                cx / 2 + 0.2 * w, cy / 2 + 0.45 * h
        }, triangleColor, false);

        scene.setFill(Color.RED);
        primaryStage.show();
    }

    public void initScreenBounds() {
        Rectangle2D screenRes = Screen.getPrimary().getBounds();
        w = screenRes.getWidth();
        h = screenRes.getHeight();
        cx = w / 2;
        cy = h / 2;
    }

    public void init(Stage primaryStage, boolean maximized) {
        root = new Group();
        scene = new Scene (root, w, h);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(maximized);
    }

    public void appendPolygon(Double[] points, Paint paint, boolean fill) {
        Polygon poly = new Polygon();
        poly.getPoints().addAll(points);
        root.getChildren().add(poly);
        if (fill) poly.setFill(paint);
        else {
            poly.setFill(Color.TRANSPARENT);
            poly.setStrokeWidth(30);
            poly.setStroke(paint);
        }
    }
}
