package com.dp.main;

import com.dp.main.renderers.Renderer;
import com.dp.main.stl.STLFileLoader;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        new LwjglWindow(new Renderer());
    }
}
