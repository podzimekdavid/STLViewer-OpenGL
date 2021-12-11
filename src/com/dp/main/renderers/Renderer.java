package com.dp.main.renderers;

import com.dp.main.stl.*;
import com.dp.main.LwjglWindow;
import com.dp.main.holders.DisplayMode;
import com.dp.main.holders.SceneState;
import com.dp.main.holders.TransformState;
import com.dp.main.managers.LocationManager;
import lwjglutils.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import transforms.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;


public class Renderer extends AbstractRenderer {

    private static final String DEPTH_TEXTURE_VAR = "depthTexture";

    private OGLTexture2D plateTexture;

    private double oldMx, oldMy;
    private boolean mousePressed;

    private int shaderProgramViewer, shaderProgramLight;
    private OGLBuffers buffers;
    private OGLRenderTarget renderTarget;

    private Camera camera, cameraLight, viewCamera;
    private Mat4 projection;

    private float constAtt, linearAtt, qouAtt;

    private DisplayMode displayMode;
    private SceneState scene;
    private TransformState transform;

    private OGLTexture.Viewer viewer;

    private Vec3D offset;

    private LocationManager locationManager;

    private boolean lightCamera = false;
    private STLFile file;

    private void initStl(boolean allowCancel) {
        file = STLFileLoader.load(false);

        if (file != null)
            buffers = STLBufferFactory.createBuffer(file.getTriangles());
    }

    @Override
    public void init() {

        initStl(false);

        OGLUtils.printOGLparameters();
        OGLUtils.printLWJLparameters();
        OGLUtils.printJAVAparameters();
        OGLUtils.shaderCheck();

        constAtt = 0.3f;
        linearAtt = 0.05f;
        qouAtt = 0.01f;


        displayMode = new DisplayMode(height, width);
        scene = new SceneState();
        transform = new TransformState();

        offset = new Vec3D(0, 0, 0);

        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        shaderProgramViewer = ShaderUtils.loadProgram("/start");
        shaderProgramLight = ShaderUtils.loadProgram("/light");

        locationManager = new LocationManager(shaderProgramViewer, shaderProgramLight);

        locationManager.initViewLocation();
        locationManager.initLightLocation();

        renderTarget = new OGLRenderTarget(1024, 1024);
        viewer = new OGLTexture2D.Viewer();


        viewCamera = new Camera()
                .withPosition(new Vec3D(-3, 3, 3))
                .withAzimuth(-1 / 4f * Math.PI)
                .withZenith(-1.3 / 5f * Math.PI);

        projection = displayMode.projectionMode();

        textRenderer = new OGLTextRenderer(width, height);

        cameraLight = new Camera()
                .withPosition(new Vec3D(6, 6, 6))
                .withAzimuth(5 / 4f * Math.PI)
                .withZenith(-1 / 5f * Math.PI);
        camera = viewCamera;

    }

    @Override
    public void display() {

        displayMode.polygonMode();
        projection = displayMode.projectionMode();


        glEnable(GL_DEPTH_TEST);

        renderFromLight();
        renderFromViewer();

        if (displayMode.isDebugMode()) {
            viewer.view(renderTarget.getColorTexture(), -1.0, -1.0, 0.7);
            viewer.view(renderTarget.getDepthTexture(), -1.0, -0.3, 0.7);
        }

    }

    private void renderFromLight() {
        glUseProgram(shaderProgramLight);
        renderTarget.bind();
        glClearColor(0.5f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUniformMatrix4fv(locationManager.getLocViewLight(), false, cameraLight.getViewMatrix().floatArray());
        glUniformMatrix4fv(locationManager.getLocProjectionLight(), false, projection.floatArray());

        glUniform3fv(locationManager.getOffsetLightLoc(), ToFloatArray.convert(offset));

        glUniformMatrix4fv(locationManager.getLocLightModel(), false, transform.getModel().floatArray());
        buffers.draw(displayMode.bufferType(), shaderProgramViewer);


    }

    private void renderFromViewer() {
        glUseProgram(shaderProgramViewer);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        glViewport(0, 0, width, height);

        glClearColor(0.5f, 0.5f, 0.5f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUniform1i(locationManager.getColorModeLoc(), displayMode.colorMode());

        glUniformMatrix4fv(locationManager.getLocView(), false, camera.getViewMatrix().floatArray());
        glUniformMatrix4fv(locationManager.getLocProjection(), false, projection.floatArray());
        glUniformMatrix4fv(locationManager.getLocLightVP(), false, cameraLight.getViewMatrix().mul(projection).floatArray());


        glUniform1f(locationManager.getLocConstAtt(), constAtt);
        glUniform1f(locationManager.getLocLinearAtt(), linearAtt);
        glUniform1f(locationManager.getLocQouAtt(), qouAtt);

        glUniform3fv(locationManager.getLocReflector(), ToFloatArray.convert(cameraLight.getViewVector()));

        glUniform3fv(locationManager.getLocLightPosition(), ToFloatArray.convert(cameraLight.getPosition()));
        glUniform3fv(locationManager.getLocEyePosition(), ToFloatArray.convert(camera.getEye()));

        glUniform1i(locationManager.getLocAmbient(), scene.isAmbient());
        glUniform1i(locationManager.getLocDiffuse(), scene.isDiffuse());
        glUniform1i(locationManager.getLocSpecular(), scene.isSpecular());


        renderTarget.getDepthTexture().bind(shaderProgramViewer, DEPTH_TEXTURE_VAR, 1);
        glUniformMatrix4fv(locationManager.getLocModel(), false, transform.getModel().floatArray());
        buffers.draw(displayMode.bufferType(), shaderProgramViewer);

    }

    @Override
    public GLFWCursorPosCallback getCursorCallback() {
        return cursorPosCallback;
    }

    @Override
    public GLFWMouseButtonCallback getMouseCallback() {
        return mouseButtonCallback;
    }

    @Override
    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    @Override
    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }


    private final GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
        @Override
        public void invoke(long window, double dx, double dy) {
            if (dy > 0)
                camera = camera.forward(0.05);
            else
                camera = camera.backward(0.05);
        }
    };


    private final GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double x, double y) {
            if (mousePressed) {
                camera = camera.addAzimuth(Math.PI * (oldMx - x) / LwjglWindow.WIDTH);
                camera = camera.addZenith(Math.PI * (oldMy - y) / LwjglWindow.HEIGHT);
                oldMx = x;
                oldMy = y;
            }
        }
    };

    private final GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if (button == GLFW_MOUSE_BUTTON_LEFT) {
                double[] xPos = new double[1];
                double[] yPos = new double[1];
                glfwGetCursorPos(window, xPos, yPos);
                oldMx = xPos[0];
                oldMy = yPos[0];
                mousePressed = (action == GLFW_PRESS);
            }

        }
    };

    private final GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                switch (key) {
                    case GLFW_KEY_A -> camera = camera.left(0.1);
                    case GLFW_KEY_D -> camera = camera.right(0.1);
                    case GLFW_KEY_W -> camera = camera.forward(0.1);
                    case GLFW_KEY_S -> camera = camera.backward(0.1);
                    case GLFW_KEY_R -> camera = camera.up(0.1);
                    case GLFW_KEY_F -> camera = camera.down(0.1);
                    case GLFW_KEY_1 -> displayMode.polygonModeSwitch();
                    case GLFW_KEY_2 -> displayMode.debugModeSwitch();
                    case GLFW_KEY_3 -> displayMode.projectionModeSwitch();
                    case GLFW_KEY_4 -> scene.switchScene();
                    case GLFW_KEY_5 -> displayMode.colorModeSwitch();
                    case GLFW_KEY_6 -> {
                        lightCamera = !lightCamera;
                        if (lightCamera) {
                            viewCamera = camera;
                            camera = cameraLight;
                        } else {
                            cameraLight = camera;
                            camera = viewCamera;
                        }
                    }
                    case GLFW_KEY_7 -> scene.switchLightType();
                    case GLFW_KEY_9 -> initStl(true);
                    case GLFW_KEY_0 -> scene.switchPlate();
                    case GLFW_KEY_UP -> transform.rotateUp();
                    case GLFW_KEY_DOWN -> transform.rotateDown();
                    case GLFW_KEY_LEFT -> transform.rotateLeft();
                    case GLFW_KEY_RIGHT -> transform.rotateRight();
                    case 333 -> transform.scaleDown();
                    case 334 -> transform.scaleUp();
                    case GLFW_KEY_B -> scene.switchAmbient();
                    case GLFW_KEY_N -> scene.switchDiffuse();
                    case GLFW_KEY_M -> scene.switchSpecular();
                }
            }
        }
    };


}
