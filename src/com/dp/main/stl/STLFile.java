package com.dp.main.stl;

import java.nio.file.Path;
import java.util.List;

public class STLFile {

    private List<Triangle> triangles;
    private Path path;

    public STLFile(List<Triangle> triangles, Path path) {
        this.triangles = triangles;
        this.path = path;
    }
    public List<Triangle> getTriangles() {
        return triangles;
    }

    public Path getPath() {
        return path;
    }
}
