package com.dp.main.stl;

import transforms.Vec3D;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class STLParser {

    public static List<Triangle> parseSTLFile(Path filepath) throws IOException {
        byte[] allBytes = Files.readAllBytes(filepath);

        boolean isAsciiStl = false;

        String buf = readBlock(allBytes, 0, 512);
        StringBuffer sb = new StringBuffer();
        int inl = readLine(buf, sb, 0);
        String line = sb.toString();
        StringTokenizer st = new StringTokenizer(line);
        String token = st.nextToken();

        if (token.equals("solid")) {
            if (inl > -1) {

                sb = new StringBuffer();

                inl = readLine(buf, sb, inl + 1);
                line = sb.toString();
                st = new StringTokenizer(line);
                token = st.nextToken();

                if (token.equals("endsolid"))
                    isAsciiStl = true;
                else if (token.equals("facet")) {
                    isAsciiStl = true;
                } else if (isBinaryFile(allBytes))
                    isAsciiStl = false;
            } else {
                if (isBinaryFile(allBytes))
                    isAsciiStl = false;
            }
        } else {
            if (isBinaryFile(allBytes))
                isAsciiStl = false;
        }


        List<Triangle> mesh;
        if (isAsciiStl) {
            Charset charset = Charset.forName("UTF-8");
            mesh = readASCII(charset.decode(ByteBuffer.wrap(allBytes)).toString().toLowerCase());
        } else {
            mesh = readBinary(allBytes);
        }
        return mesh;
    }


    private static String readBlock(byte[] allBytes, int offset, int length) {
        if (allBytes.length - offset < length) length = allBytes.length - offset;
        Charset charset = Charset.forName("UTF-8");
        CharBuffer decode = charset.decode(ByteBuffer.wrap(allBytes, offset, length));
        return decode.toString().toLowerCase();
    }

    private static int readLine(String buf, StringBuffer sb, int offset) {
        int il = buf.indexOf('\n', offset);
        if (il > -1)
            sb.append(buf.substring(offset, il - 1));
        else
            sb.append(buf.substring(offset));
        return il;
    }

   private static boolean isBinaryFile(byte[] allBytes) throws IllegalArgumentException {
        if (allBytes.length < 84)
            throw new IllegalArgumentException("invalid binary file, length<84");
        int numTriangles = byteaToTnt(Arrays.copyOfRange(allBytes, 80, 84));
        if (allBytes.length >= 84 + numTriangles * 50)
            return true; // Binary file
        else {
            String msg = "invalid binary file, num triangles does not match length specs";
            throw new IllegalArgumentException(msg);
        }
    }


   private static int byteaToTnt(byte[] bytes) {
        assert (bytes.length == 4);
        int r = 0;
        r = bytes[0] & 0xff;
        r |= (bytes[1] & 0xff) << 8;
        r |= (bytes[2] & 0xff) << 16;
        r |= (bytes[3] & 0xff) << 24;
        return r;
    }


    private static List<Triangle> readASCII(String content) {
        System.out.println("ASCII STL format");

        ArrayList<Triangle> triangles = new ArrayList<>();

        int position = 0;
        scan:
        {
            while (position < content.length() & position >= 0) {
                position = content.indexOf("facet", position);
                if (position < 0) {
                    break scan;
                }
                try {
                    Vec3D[] vertices = new Vec3D[3];
                    for (int v = 0; v < vertices.length; v++) {
                        position = content.indexOf("vertex", position) + "vertex".length();
                        while (Character.isWhitespace(content.charAt(position))) {
                            position++;
                        }
                        int nextSpace;
                        double[] vals = new double[3];
                        for (int d = 0; d < vals.length; d++) {
                            nextSpace = position + 1;
                            while (!Character.isWhitespace(content.charAt(nextSpace))) {
                                nextSpace++;
                            }
                            String value = content.substring(position, nextSpace);
                            vals[d] = Double.parseDouble(value);
                            position = nextSpace;
                            while (Character.isWhitespace(content.charAt(position))) {
                                position++;
                            }
                        }
                        vertices[v] = new Vec3D(vals[0], vals[1], vals[2]);
                    }
                    position = content.indexOf("endfacet", position) + "endfacet".length();
                    triangles.add(new Triangle(vertices[0], vertices[1], vertices[2]));
                } catch (Exception ex) {
                    int back = position - 128;
                    if (back < 0) {
                        back = 0;
                    }
                    int forward = position + 128;
                    if (position > content.length()) {
                        forward = content.length();
                    }
                    throw new IllegalArgumentException("Malformed STL syntax near \"" + content.substring(back, forward) + "\"", ex);
                }
            }
        }

        return triangles;
    }


    private static List<Triangle> readBinary(byte[] allBytes) {
        System.out.println("Binary STL format");
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(allBytes));
        ArrayList<Triangle> triangles = new ArrayList<>();
        try {

            byte[] header = new byte[80];
            in.read(header);
            int numberTriangles = Integer.reverseBytes(in.readInt());
            triangles.ensureCapacity(numberTriangles);

            try {
                while (in.available() > 0) {
                    float[] nvec = new float[3];
                    for (int i = 0; i < nvec.length; i++) {
                        nvec[i] = Float.intBitsToFloat(Integer.reverseBytes(in.readInt()));
                    }

                    Vec3D[] vertices = new Vec3D[3];
                    for (int v = 0; v < vertices.length; v++) {
                        float[] vals = new float[3];
                        for (int d = 0; d < vals.length; d++) {
                            vals[d] = Float.intBitsToFloat(Integer.reverseBytes(in.readInt()));
                        }
                        vertices[v] = new Vec3D(vals[0], vals[1], vals[2]);
                    }
                    short attribute = Short.reverseBytes(in.readShort());

                    triangles.add(new Triangle(vertices[0], vertices[1], vertices[2]));
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException("Malformed STL binary at triangle number " + (triangles.size() + 1), ex);
            }
        } catch (IOException ex) {

            System.out.println(ex.toString());

        }
        return triangles;
    }

}
