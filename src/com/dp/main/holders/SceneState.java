package com.dp.main.holders;

public class SceneState {

    private int scene = 8;
    private boolean plate = true;

    private static final int MAX_SCENES = 10;

    public void switchScene() {
        if (scene < MAX_SCENES)
            scene++;
        else
            scene = 0;
    }

    public int getSceneState() {
        return scene;
    }

    private boolean reflector = false;
    private boolean ambient = true;
    private boolean diffuse = true;
    private boolean specular = true;

    public void switchLightType() {
        reflector = !reflector;
    }

    public int isAmbient() {
        return ambient ? 1 : 0;
    }

    public int isDiffuse() {
        return diffuse ? 1 : 0;
    }

    public int isSpecular() {
        return specular ? 1 : 0;
    }

    public void switchAmbient() {
        ambient = !ambient;
    }

    public void switchDiffuse() {
        diffuse = !diffuse;
    }

    public void switchSpecular() {
        specular = !specular;
    }

    public int isReflector() {
        return reflector ? 1 : 0;
    }

    public void switchPlate() {
        plate = !plate;
    }

    public boolean showPlate() {
        return plate;
    }
}
