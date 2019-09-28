package application;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.File;
import java.util.ArrayList;

public class FileSelector {

    private String directory = null ;
    private String file = null ;
    private ArrayList<String> listFile;
    private ArrayList<String> listRepParent;

    public FileSelector() {
        super();
    listRepParent = new ArrayList<>();
    listFile = new ArrayList<>();
    }

  
    // Returns and ArrayList of the parents of file Path
    public ArrayList<String> getListRepParent(String path){

        String actualPath = "";
        File dir = new File(path) ;
        if (!dir.exists() || !dir.isDirectory()) return null ;

        directory = dir.getAbsolutePath() ;
      //  System.out.println(directory) ;
        file = null ;

        String[] files = dir.list() ;
        if (files!=null) {
            for(int i=0; i<files.length; i++) {
                File f = new File(path, files[i]);
                if (f.isDirectory()) files[i] = files[i]+"\\\\" ;
        //        System.out.format("  file: %s\n",files[i]) ;
            }
        }

        //System.out.format("  parentdir: %s\n","\\\\") ;
        listRepParent.clear();
        String[] dirs = path.split("\\\\") ;
        for (String p : dirs) {
            actualPath += p + "\\\\";
           // if (p.equals("")) continue ;
            listRepParent.add(actualPath);
           // System.out.format("  parentdir: %s\n",p) ;
        }

        return listRepParent;
    }

 // Returns and ArrayList of the files present inside the directory Path
    public ArrayList<String> getListFile(String path){

        File dir = new File(path) ;

        if (!dir.exists() || !dir.isDirectory()) return null ;

        directory = dir.getAbsolutePath() ;
        file = null ;

        String[] files = dir.list() ;
        if (files!=null) {
            listFile.clear();
            for(int i=0; i<files.length; i++) {
                File f = new File(path, files[i]);

                if (f.isDirectory()) {
                    files[i] = files[i]+"\\\\" ;
                    listFile.add(f.getName()+ "\\\\");
                } else if(f.isFile()){
                    listFile.add(f.getName());
                }
               // System.out.format("  file: %s\n",files[i]) ;
            }
        }

        return listFile;
    }






}
