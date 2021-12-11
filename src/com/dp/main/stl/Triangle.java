package com.dp.main.stl;

import transforms.Vec3D;

import java.util.Arrays;

public class Triangle {
        private final Vec3D[] vertices;

        public Triangle(Vec3D v1, Vec3D v2, Vec3D v3) {
            vertices = new Vec3D[3];
            vertices[0] = v1;
            vertices[1] = v2;
            vertices[2] = v3;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Triangle[");
            for (Vec3D v : vertices) {
                sb.append(v.toString());
            }
            sb.append("]");
            return sb.toString();
        }


        public Vec3D[] getVertices() {
            return vertices;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Triangle other = (Triangle) obj;
            if (!Arrays.deepEquals(this.vertices, other.vertices)) {
                return false;
            }
            return true;
        }


        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Arrays.deepHashCode(this.vertices);
            return hash;
        }
    }

