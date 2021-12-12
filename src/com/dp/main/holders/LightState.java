package com.dp.main.holders;

public class LightState {

    private boolean ambient = true;
    private boolean diffuse = true;
    private boolean specular = true;

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

}
