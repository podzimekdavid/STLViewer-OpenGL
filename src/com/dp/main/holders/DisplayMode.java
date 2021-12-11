package com.dp.main.holders;

import transforms.Mat4;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;

import static org.lwjgl.opengl.GL11.*;

public class DisplayMode {

    private int polygonMode;
    private Mat4 perspectiveProjection;
    private Mat4 orthogonalProjection;


    public DisplayMode(int width, int height) {
        polygonMode = GL_FILL;

        perspectiveProjection = new Mat4PerspRH(Math.PI / 3, (float) height / width, 1.0, 20.0);
        orthogonalProjection = new Mat4OrthoRH(10, 10 * ((float) height / width), 1.0, 20.0);
    }

    public void polygonMode() {
        glPolygonMode(GL_FRONT_AND_BACK, polygonMode);
    }

    private int polygonModeSwitchValue = 0;
    private boolean debugModeSwitchValue = false;
    private boolean projectionModeSwitchValue = true;

    public void polygonModeSwitch() {

        if (polygonModeSwitchValue < 2)
            polygonModeSwitchValue++;
        else
            polygonModeSwitchValue = 0;

        switch (polygonModeSwitchValue) {
            case 0:
                polygonMode = GL_FILL;
                break;
            case 1:
                polygonMode = GL_POINT;
                break;
            case 2:
                polygonMode = GL_LINE;
                break;
        }

    }

    public void debugModeSwitch() {
        debugModeSwitchValue = !debugModeSwitchValue;
    }

    public boolean isDebugMode() {
        return debugModeSwitchValue;
    }

    public void projectionModeSwitch() {
        projectionModeSwitchValue = !projectionModeSwitchValue;
    }

    public Mat4 projectionMode() {
        return projectionModeSwitchValue ? perspectiveProjection : orthogonalProjection;
    }

    private int colorModeValue = 0;
    public static final int MAX_COLOR_MODE = 5;

    public void colorModeSwitch() {
        if (colorModeValue < MAX_COLOR_MODE)
            colorModeValue++;
        else
            colorModeValue = 0;
    }

    public int colorMode(){
        return colorModeValue;
    }

    private boolean bufferBasicTriangles=true;

    public void bufferModeSwitch(){
        bufferBasicTriangles=!bufferBasicTriangles;
    }

    public int bufferType(){
        return bufferBasicTriangles?GL_TRIANGLES:GL_TRIANGLE_STRIP;
    }

}
