# STLViewer

**STLViewer** is a simple application for loading, parsing, and displaying 3D models from STL files using OpenGL.

This project started as a fun experiment to better understand the STL file format and basic 3D rendering with OpenGL. While working on it, I learned how STL files are structured—especially the differences between binary and ASCII formats—and how to visualize them. Later, I reused the experience gained from this project in one of my university coursework assignments in 2021.

STL (Stereolithography) files come in two formats: [ASCII and Binary](https://en.wikipedia.org/wiki/STL_(file_format)). This viewer fully supports Binary STL files and partially supports ASCII STL (due to its limited usage in practice).

Sample STL files can be found in the [STL FILES directory](https://github.com/podzimekdavid/STLViewer-OpenGL/tree/master/STL%20files).

---

## 🕹️ Controls

| Key              | Action              |
|------------------|---------------------|
| `1`              | Toggle Fill / Lines |
| `2`              | Debug mode          |
| `3`              | Toggle Projection   |
| `5`              | Change Colors       |
| `6`              | Light View Toggle   |
| `9`              | Load Model          |
| `W, A, S, D, R, F` | Move Camera        |
| `+ / -`          | Scale Model         |
| Arrow Keys       | Rotate Model        |
| `B, N, M`        | Adjust Light Components |

---

## 🖼️ Screenshots

![Screenshot 1](./1.png)  
![Screenshot 2](./2.png)

---
