package com.company;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends Applet implements ActionListener {
    private final TransformGroup tg = new TransformGroup();
    private final Transform3D t3d = new Transform3D();
    private double angle = 0;
    private final double velocity = 0.02;

    public static void main(String[] args) {
        MainFrame mf = new MainFrame(new Main(), 700, 700);
        mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mf.run();
    }

    private Main() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);
        add("Center", c);
        SimpleUniverse universe = new SimpleUniverse(c);

        Timer timer = new Timer(20, this);
        timer.start();

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(createBranchGroup());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        t3d.rotY(angle);
        tg.setTransform(t3d);
        angle += velocity;
    }

    private BranchGroup createBranchGroup() {
        BranchGroup bg = new BranchGroup();

        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        bg.addChild(tg);
        createDumbbells(3, 1.2, 0.3);

        Color3f lightColor = new Color3f(new Color(64, 64, 64));
        BoundingSphere ib = new BoundingSphere(new Point3d(0, 0, 0), 100);

        Vector3f lightDirection1 = new Vector3f(-1, 1, -1);
        DirectionalLight light1 = new DirectionalLight(lightColor, lightDirection1);
        light1.setInfluencingBounds(ib);
        bg.addChild(light1);

        Vector3f lightDirection2 = new Vector3f(-1, 0, 0);
        DirectionalLight light2 = new DirectionalLight(lightColor, lightDirection2);
        light2.setInfluencingBounds(ib);
        bg.addChild(light2);

        AmbientLight al = new AmbientLight(new Color3f(new Color(100, 255, 255)));
        al.setInfluencingBounds(ib);
        bg.addChild(al);

        return bg;
    }

    private void createDumbbells(int quantity, double size, double distance) {
        double period = 2 * Math.PI / quantity;

        for (int i = 0; i < quantity; i++) {
            float x = (float)(distance * Math.cos(i * period));
            float z = (float)(distance * Math.sin(i * period));
            createDumbbell(size, new Vector3f(x, 0, z));
        }
    }

    private void createDumbbell(double size, Vector3f initialVector) {
        float x = initialVector.getX();
        float y = initialVector.getY();
        float z = initialVector.getZ();

        // body
        double bodyRadius = size * 0.025;
        double bodyHeight = size * 0.6;

        appendPrimitive(Dumbbell.getBody(bodyRadius, bodyHeight), initialVector);

        // plates
        double plateHeight = size * 0.04;

        double bigPlateRadius = size * 0.2;
        double middlePlateRadius = size * 0.15;
        double smallPlateRadius = size * 0.1;

        float bigPlateDistance = (float)(bodyHeight / 2 - 3.5 * plateHeight);
        float middlePlateDistance = bigPlateDistance + (float)plateHeight;
        float smallPlateDistance = middlePlateDistance + (float)plateHeight;

        appendPrimitive(Dumbbell.getPlate(bigPlateRadius, plateHeight), new Vector3f(x, y + bigPlateDistance, z));
        appendPrimitive(Dumbbell.getPlate(bigPlateRadius, plateHeight), new Vector3f(x, y - bigPlateDistance, z));

        appendPrimitive(Dumbbell.getPlate(middlePlateRadius, plateHeight), new Vector3f(x, y + middlePlateDistance, z));
        appendPrimitive(Dumbbell.getPlate(middlePlateRadius, plateHeight), new Vector3f(x, y - middlePlateDistance, z));

        appendPrimitive(Dumbbell.getPlate(smallPlateRadius, plateHeight), new Vector3f(x, y + smallPlateDistance, z));
        appendPrimitive(Dumbbell.getPlate(smallPlateRadius, plateHeight), new Vector3f(x, y - smallPlateDistance, z));

        // spikes
        double spikeHeight = size * 0.04;

        float spikeDistance = (float)((bodyHeight + spikeHeight) / 2);

        appendPrimitive(Dumbbell.getSpike(bodyRadius, spikeHeight), new Vector3f(x, y + spikeDistance, z));
        appendPrimitive(Dumbbell.getSpike(bodyRadius, -spikeHeight), new Vector3f(x, y - spikeDistance, z));

    }

    private void appendPrimitive(Primitive primitive, Vector3f vector) {
        TransformGroup bodyGroup = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.setTranslation(vector);
        bodyGroup.setTransform(transform);
        bodyGroup.addChild(primitive);
        tg.addChild(bodyGroup);
    }
}