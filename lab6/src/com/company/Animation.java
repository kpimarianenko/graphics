package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

class KeyCodes {
    final static int ROTATE_UP = 38;
    final static int ROTATE_DOWN = 40;
    final static int ROTATE_LEFT = 37;
    final static int ROTATE_RIGHT = 39;

    final static int MOVE_UP = 87;
    final static int MOVE_DOWN = 83;
    final static int MOVE_LEFT = 65;
    final static int MOVE_RIGHT = 68;
}

public class Animation implements ActionListener, KeyListener {
    private TransformGroup group;
    private Transform3D transform3D = new Transform3D();

    private float x = 0;
    private float y = 0;
    private float dx = 0.02f;
    private float dy = 0.02f;
    private float da = 0.05f;

    private boolean mu = false;
    private boolean md = false;
    private boolean ml = false;
    private boolean mr = false;
    private boolean ru = false;
    private boolean rd = false;
    private boolean rl = false;
    private boolean rr = false;

    Animation(TransformGroup group) {
        this.group = group;
        this.group.getTransform(this.transform3D);
        Timer timer = new Timer(20, this);
        timer.start();
    }

    private void Move() {
        if (ml) x -= dx;
        if (mr) x += dx;
        if (mu) y += dy;
        if (md) y -= dy;
        transform3D.setTranslation(new Vector3f(x, y, 0));

        Transform3D rotation = new Transform3D();
        if (ru) rotation.rotX(-da);
        if (rd) rotation.rotX(da);
        if (rl) rotation.rotY(da);
        if (rr) rotation.rotY(-da);
        transform3D.mul(rotation);
        group.setTransform(transform3D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case KeyCodes.MOVE_UP -> mu = true;
            case KeyCodes.MOVE_DOWN -> md = true;
            case KeyCodes.MOVE_LEFT -> ml = true;
            case KeyCodes.MOVE_RIGHT -> mr = true;
            case KeyCodes.ROTATE_UP -> ru = true;
            case KeyCodes.ROTATE_DOWN -> rd = true;
            case KeyCodes.ROTATE_LEFT -> rl = true;
            case KeyCodes.ROTATE_RIGHT -> rr = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case KeyCodes.MOVE_UP -> mu = false;
            case KeyCodes.MOVE_DOWN -> md = false;
            case KeyCodes.MOVE_LEFT -> ml = false;
            case KeyCodes.MOVE_RIGHT -> mr = false;
            case KeyCodes.ROTATE_UP -> ru = false;
            case KeyCodes.ROTATE_DOWN -> rd = false;
            case KeyCodes.ROTATE_LEFT -> rl = false;
            case KeyCodes.ROTATE_RIGHT -> rr = false;
        }
    }
}