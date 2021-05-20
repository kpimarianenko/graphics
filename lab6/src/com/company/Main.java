package com.company;

import javax.vecmath.*;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class Main extends JFrame{
    private static Canvas3D canvas;
    private static SimpleUniverse universe;
    private static BranchGroup root;
    private static TransformGroup blackWidow;
    private static Scene spiderScene;

    private final static String __ASSETS__ = System.getProperty("user.dir") + "\\src\\assets\\";

    public Main() throws IOException {
        setUpWindow();

        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);

        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        BranchGroup root = new BranchGroup();
        setUpBackground(root, "background.png");
        setUpLight(root);
        blackWidow = getModelTransformGroup("black_widow.obj", "black_widow.jpg", "blkw_body", "leg", 8);
        root.addChild(blackWidow);
        root.compile();
        universe.addBranchGraph(root);
    }

    public static void main(String[] args) {
        try {
            Main window = new Main();
            Animation spiderMovement = new Animation(blackWidow);
            canvas.addKeyListener(spiderMovement);
            window.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setUpWindow() {
        setTitle("Lab6");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpLight(BranchGroup root) {
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100);
        DirectionalLight dirlight = new DirectionalLight(new Color3f(1, 1, 1), new Vector3f(-1, -1, -1));
        dirlight.setInfluencingBounds(bounds);
        root.addChild(dirlight);

        AmbientLight amblight = new AmbientLight(new Color3f(1, 1, 1));
        amblight.setInfluencingBounds(new BoundingSphere());
        root.addChild(amblight);
    }

    private void setUpBackground(BranchGroup root, String path) {
        TextureLoader t = new TextureLoader(__ASSETS__ + path, canvas);
        Background background = new Background(t.getImage());
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        root.addChild(background);
    }

    private void addAppearance(Shape3D shape, String path) {
        TextureLoader loader = new TextureLoader(__ASSETS__ + path, "RGP", new Container());
        Texture texture = loader.getTexture();
        Appearance appearance = new Appearance();
        appearance.setTexture(texture);
        shape.setAppearance(appearance);
    }

    public TransformGroup getModelTransformGroup(
            String modelPath,
            String texturePath,
            String bodyGroupName,
            String legGroupName,
            int legGroupsCount
    ) throws IOException {
        ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
        spiderScene = loader.load(__ASSETS__ + modelPath);

        Hashtable spider = spiderScene.getNamedObjects();

        TransformGroup sceneTg = new TransformGroup();
        TransformGroup bodyTg = new TransformGroup();
        initializeModelPart(bodyTg, sceneTg, spider, bodyGroupName, texturePath);

        final int movesDuration = 500;
        BoundingSphere bs = new BoundingSphere(new Point3d(0, 0, 0), Double.MAX_VALUE);

        for (int i = 0; i < legGroupsCount; i++) {
            TransformGroup legTg = new TransformGroup();
            initializeModelPart(legTg, sceneTg, spider, legGroupName + (i + 1), texturePath);

            int startTime = i < legGroupsCount / 2
                    ? i * movesDuration / legGroupsCount
                    : (legGroupsCount - i + legGroupsCount / 2 - 1) * movesDuration / legGroupsCount;

            Alpha alpha = new Alpha(Integer.MAX_VALUE, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration, 0, 0, 0, 0, 0);
            Transform3D rotate = new Transform3D();
            rotate.setRotation(new AxisAngle4d(0, 1, 0, 0));
            float rotationAngle = (float) Math.PI / 6 * (i < legGroupsCount / 2 ? 1 : -1);
            RotationInterpolator interpolator = new RotationInterpolator(alpha, legTg, rotate, 0, rotationAngle);
            interpolator.setSchedulingBounds(bs);
            legTg.addChild(interpolator);
        }

        Transform3D startTransformation = new Transform3D();

        Transform3D rotY = new Transform3D();
        rotY.rotY(Math.PI / 3);

        Transform3D rotX = new Transform3D();
        rotX.rotX(Math.PI / 6);

        Transform3D scale = new Transform3D();
        scale.setScale(0.45);

        startTransformation.mul(rotX);
        startTransformation.mul(rotY);
        startTransformation.mul(scale);

        TransformGroup rootTg = new TransformGroup(startTransformation);
        rootTg.addChild(sceneTg);
        rootTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        return rootTg;
    }

    private void initializeModelPart(TransformGroup part, TransformGroup sceneTg, Hashtable table, String groupName, String texturePath) {
        part.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Shape3D body = (Shape3D)table.get(groupName);
        addAppearance(body, texturePath);
        part.addChild(body.cloneTree());
        sceneTg.addChild(part);
    }
}