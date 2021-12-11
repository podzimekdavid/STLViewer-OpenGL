package com.dp.main.stl;

import javax.swing.*;
import java.awt.*;

public class STLFileLoader {

    public static STLFile load(boolean allowCancel){

        final JFileChooser fc = new JFileChooser();

        fc.addChoosableFileFilter(new STLFileFilter());
        fc.setAcceptAllFileFilterUsed(false);

        do {

        }while(fc.showOpenDialog(new Button("Open STL"))!=JFileChooser.APPROVE_OPTION || allowCancel);

        try{
            var path = fc.getSelectedFile().toPath();
            var file = STLParser.parseSTLFile(path);

            return  new STLFile(file, path);

        }catch (Exception e){
            System.out.println("Error load STL file!");
        }

        return null;

    }

}
