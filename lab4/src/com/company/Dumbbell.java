package com.company;

import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.*;
import javax.vecmath.*;

public class Dumbbell {
    private final static int flags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

    public static Cylinder getBody(double r, double h) {
        return getCylinder(r, h, getBodyAppearence());
    }

    public static Cylinder getPlate(double r, double h) {
        return getCylinder(r, h, getPlateAppearence());
    }

    public static Cone getSpike(double r, double h) {
        return getCone(r, h, getBodyAppearence());
    }

    private static Cylinder getCylinder(double r, double h, Appearance a) {
        return new Cylinder((float)r, (float)h, flags, a);
    }

    private static Cone getCone(double r, double h, Appearance a) {
        return new Cone((float)r, (float)h, flags, a);
    }

    private static Appearance getBodyAppearence() {
        Appearance ap = new Appearance();

        Color3f emissive = new Color3f(0.4453f, 0.4453f, 0.4453f);
        Color3f ambient = new Color3f(0f, 0f, 0f);
        Color3f diffuse = new Color3f(1f, 1f, 1f);
        Color3f specular = new Color3f(1f, 1f, 1f);

        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 70f));
        return ap;
    }

    private static Appearance getPlateAppearence() {
        Appearance ap = new Appearance();

        Color3f emissive = new Color3f(0.03f, 0.03f, 0.03f);
        Color3f ambient = new Color3f(0.1f, 0.1f, 0.1f);
        Color3f diffuse = new Color3f(0.1f, 0.1f, 0.1f);
        Color3f specular = new Color3f(0.1f, 0.1f, 0.1f);

        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 70f));

        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }
}