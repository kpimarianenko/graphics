package com.company;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.Background;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

public class Main extends JFrame {
    private static Canvas3D canvas;
    private static TransformGroup t90;

    private final static String __ASSETS__ = System.getProperty("user.dir") + "\\src\\assets\\";

    public Main() throws IOException {
        setUpWindow();

        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);

        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        BranchGroup root = new BranchGroup();
        setUpBackground(root, "background.jpg");
        setUpLight(root);
        t90 = getModelTransformGroup("t90.obj", "t90.jpg", "t90");
        root.addChild(t90);
        root.compile();
        universe.addBranchGraph(root);
    }

    public static void main(String[] args) {
        try {
            Main window = new Main();
            Animation tankMovement = new Animation(t90);
            canvas.addKeyListener(tankMovement);
            window.setVisible(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setUpWindow() {
        setTitle("lab5");
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

    private TransformGroup getModelTransformGroup(String modelPath, String texturePath, String groupName) throws IOException {
        Shape3D shape = getModelShape(modelPath, groupName);
        addAppearance(shape, texturePath);
        Transform3D transforms = new Transform3D();
        Transform3D transformScale = new Transform3D();
        transformScale.setScale(new Vector3d(0.4, 0.4, 0.4));
        Transform3D transformRotX = new Transform3D();
        transformRotX.rotX(-Math.PI / 2.5);
        Transform3D transformRotZ = new Transform3D();
        transformRotZ.rotZ(-Math.PI / 4);
        transforms.mul(transformScale);
        transforms.mul(transformRotX);
        transforms.mul(transformRotZ);
        TransformGroup group = getModelGroup(shape);
        group.setTransform(transforms);
        return group;
    }

    private TransformGroup getModelGroup(Shape3D shape) {
        TransformGroup group = new TransformGroup();
        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(shape);
        return group;
    }

    private Shape3D getModelShape(String path, String groupName) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        Scene scene = file.load(new FileReader(__ASSETS__ + path));
        Map<String, Shape3D> map = scene.getNamedObjects();
        Shape3D shape = map.get(groupName);
        scene.getSceneGroup().removeChild(shape);
        return shape;
    }
}