package com.dp.main.stl;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class STLFileFilter extends FileFilter {

    public static final String STL_FILE_EXTENSION = "stl";

    @Override
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
           return extension.equals(STL_FILE_EXTENSION);
        }

        return false;
    }

    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    @Override
    public String getDescription() {
        return "STL file";
    }
}
