package com.dp.main.holders;

import transforms.*;

public class TransformState {

    private Mat4 model;

    public TransformState() {
        model = new Mat4Identity();
        scale = 1;
    }

    private float scale;

    public void scaleUp() {
        scale += 0.01;
    }

    public void scaleDown() {
        scale -= 0.01;
    }


    public Mat4 getModel() {
        return model.mul(new Mat4Scale(scale));
    }

    public Mat4 getCameraTransformation(Vec3D position) {

        return new Mat4Scale(0.2).mul(new Mat4Transl(position));
    }


}
