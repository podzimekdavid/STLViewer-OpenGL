package com.dp.main.managers;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public class LocationManager {

    private int shaderProgramViewer;
    private int shaderProgramLight;

    public LocationManager(int shaderProgramViewer, int shaderProgramLight) {
        this.shaderProgramViewer = shaderProgramViewer;
        this.shaderProgramLight = shaderProgramLight;
    }

    private int timeLoc, timeLightLoc;
    private int locConstAtt, locLinearAtt, locQouAtt, locReflector;

    private int locView, locProjection, locSolid, locLightPosition, locEyePosition, locLightVP;
    private int locViewLight, locProjectionLight, locSolidLight;

    private int offsetLoc, offsetLightLoc;

    private int colorModeLoc;

    public int getLocAmbient() {
        return locAmbient;
    }

    public int getLocDiffuse() {
        return locDiffuse;
    }

    public int getLocSpecular() {
        return locSpecular;
    }

    private int locAmbient, locDiffuse, locSpecular;

    public int getLocModel() {
        return locModel;
    }

    public int getLocLightModel() {
        return locLightModel;
    }

    private int locModel, locLightModel;

    public int getLightTypeLoc() {
        return lightTypeLoc;
    }

    private int lightTypeLoc;

    public void initLightLocation() {
        offsetLightLoc = glGetUniformLocation(shaderProgramLight, "offset");
        timeLightLoc = glGetUniformLocation(shaderProgramLight, "time");
        locViewLight = glGetUniformLocation(shaderProgramLight, "view");
        locProjectionLight = glGetUniformLocation(shaderProgramLight, "projection");
        locSolidLight = glGetUniformLocation(shaderProgramLight, "solid");

        locLightModel = glGetUniformLocation(shaderProgramLight, "model");

    }

    public void initViewLocation() {
        locConstAtt = glGetUniformLocation(shaderProgramViewer, "constantAttenuation");
        locLinearAtt = glGetUniformLocation(shaderProgramViewer, "linearAttenuation");
        locQouAtt = glGetUniformLocation(shaderProgramViewer, "quadraticAttenuation");
        locReflector = glGetUniformLocation(shaderProgramViewer, "reflector");

        locEyePosition = glGetUniformLocation(shaderProgramViewer, "eyePosition");
        locView = glGetUniformLocation(shaderProgramViewer, "view");
        locProjection = glGetUniformLocation(shaderProgramViewer, "projection");
        locSolid = glGetUniformLocation(shaderProgramViewer, "solid");
        locLightPosition = glGetUniformLocation(shaderProgramViewer, "lightPosition");
        locLightVP = glGetUniformLocation(shaderProgramViewer, "lightVP");

        timeLoc = glGetUniformLocation(shaderProgramViewer, "time");
        offsetLoc = glGetUniformLocation(shaderProgramViewer, "offset");

        colorModeLoc = glGetUniformLocation(shaderProgramViewer, "colorMode");
        lightTypeLoc = glGetUniformLocation(shaderProgramViewer, "isReflector");

        locAmbient = glGetUniformLocation(shaderProgramViewer, "amb");
        locDiffuse = glGetUniformLocation(shaderProgramViewer, "dif");
        locSpecular = glGetUniformLocation(shaderProgramViewer, "spc");

        locModel = glGetUniformLocation(shaderProgramViewer, "model");

    }

    public int getTimeLoc() {
        return timeLoc;
    }

    public int getTimeLightLoc() {
        return timeLightLoc;
    }

    public int getLocConstAtt() {
        return locConstAtt;
    }

    public int getLocLinearAtt() {
        return locLinearAtt;
    }

    public int getLocQouAtt() {
        return locQouAtt;
    }

    public int getLocReflector() {
        return locReflector;
    }

    public int getLocView() {
        return locView;
    }

    public int getLocProjection() {
        return locProjection;
    }

    public int getLocSolid() {
        return locSolid;
    }

    public int getLocLightPosition() {
        return locLightPosition;
    }

    public int getLocEyePosition() {
        return locEyePosition;
    }

    public int getLocLightVP() {
        return locLightVP;
    }

    public int getLocViewLight() {
        return locViewLight;
    }

    public int getLocProjectionLight() {
        return locProjectionLight;
    }

    public int getLocSolidLight() {
        return locSolidLight;
    }

    public int getOffsetLoc() {
        return offsetLoc;
    }

    public int getOffsetLightLoc() {
        return offsetLightLoc;
    }

    public int getColorModeLoc() {
        return colorModeLoc;
    }
}
