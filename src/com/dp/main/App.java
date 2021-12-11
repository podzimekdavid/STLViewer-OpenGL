package com.dp.main;

import com.dp.main.renderers.Renderer;
import com.dp.main.stl.STLParser;
import com.dp.main.stl.Triangle;

import java.io.IOException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws IOException {

        /*var data=STLParser.parseSTLFile(Path.of("E:\\3D print models\\drzak1.stl"));

        for (Triangle t :
                data) {
            System.out.println(t.toString());
        }
        System.out.println("-------------------------------------------------");
        System.out.println("Count" + data.size());*/

        new LwjglWindow(new Renderer());
    }
}
