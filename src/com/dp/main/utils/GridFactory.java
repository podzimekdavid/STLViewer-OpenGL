package com.dp.main.utils;

import lwjglutils.OGLBuffers;

import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;

public class GridFactory {

    public static OGLBuffers createGrid(int a, int b, int bufferType) {

        float[] vb = new float[a * b * 2];
        int index = 0;
        for (int j = 0; j < b; j++) {
            float y = j / (float) (b - 1);
            for (int i = 0; i < a; i++) {
                float x = i / (float) (a - 1);
                vb[index++] = x;
                vb[index++] = y;
            }
        }

        int[] ib;

        index=0;
        if(bufferType==GL_TRIANGLES)
        {
            ib = new int[(a - 1) * (b - 1) * 2 * 3];

            for (int j = 0; j < b - 1; j++) {
                int offset = j * a;
                for (int i = 0; i < a - 1; i++) {
                    ib[index++] = offset + i;
                    ib[index++] = offset + i + 1;
                    ib[index++] = offset + i + a;
                    ib[index++] = offset + i + a;
                    ib[index++] = offset + i + 1;
                    ib[index++] = offset + i + a + 1;
                }
            }
        }else{
            ib = new int[((2 * a) * (b - 1)) + (2 * (b - 1 - 1))];

            for (int j = 0; j < b - 1; j++) {
                if (j > 0) {
                    ib[index++] = (j * b);
                }
                for (int i = 0; i < a; i++) {
                    ib[index++] =  ((j * b) + i);
                    ib[index++] = (((j + 1) * b) + i);
                }

                if (j < b - 2) {
                    ib[index++] = (((j + 1) * b) + (a - 1));
                }
            }
        }


        OGLBuffers.Attrib[] attributes = {
            new OGLBuffers.Attrib("inPosition", 2) // 2 floats per vertex
        };
        return new OGLBuffers(vb, attributes, ib);
    }

}
