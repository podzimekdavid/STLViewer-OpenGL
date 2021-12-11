package com.dp.main.holders;

import transforms.*;

public class TransformState {

    private Mat4 model;

    public TransformState() {
        model = new Mat4Identity();
        scale = 1;
        rotateZ = 0;
    }

    private float scale;
    private float rotateZ;
    private float rotateX;

    public void scaleUp() {
        scale += 0.01;
    }

    public void scaleDown() {
        scale -= 0.01;
    }

    public void rotateLeft() {
        rotateZ += 0.01;
    }

    public void rotateRight() {
        rotateZ -= 0.01;
    }

    public void rotateDown() {
        rotateX += 0.01;
    }

    public void rotateUp() {
        rotateX -= 0.01;
    }


    public Mat4 getModel() {
        return model.mul(new Mat4Scale(scale)).mul(new Mat4RotZ(rotateZ).mul(new Mat4RotX(rotateX)));
    }

    public Mat4 getCameraTransformation(Vec3D position) {

        return new Mat4Scale(0.2).mul(new Mat4Transl(position));
    }


}
