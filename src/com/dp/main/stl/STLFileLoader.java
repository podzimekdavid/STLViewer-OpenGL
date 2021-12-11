package com.dp.main.stl;

import javax.swing.*;
import java.awt.*;

public class STLFileLoader {

    public static void load(){

        final JFileChooser fc = new JFileChooser();

        fc.addChoosableFileFilter(new STLFileFilter());
        fc.setAcceptAllFileFilterUsed(false);

        do {

        }while(fc.showOpenDialog(new Button("Open STL"))!=JFileChooser.APPROVE_OPTION);

        try{
            var file = STLParser.parseSTLFile(fc.getSelectedFile().toPath());
        }catch (Exception e){
            System.out.println("Error load STL file!");
        }


    }

}
