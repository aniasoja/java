package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.io.File;

public class Controller {
    /*
    ObservableList<String> optionsListview;  // string of files in directory
    ObservableList<String> optionsListRep  ; // string of parent directories
    FileSelector fs;						 // class the reads directories
    */


    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOpen;

    @FXML
    private ListView listViewFile;        // list to show files inside directory

    @FXML
    private ComboBox<String> comboxRep; // combo box of parent directories

    FileSelector fs = new FileSelector();


    @FXML
    public void initialize() {
        String path = System.getProperty("user.home");
        System.out.println(path);
        this.btnOpen.setOnAction((event -> {System.out.println("Open click");}));
    }


    /*
     * updates the combo view based on the selected path
     */

    private void majComboBox(String path) {

        ObservableList<String> itemsComboRep = FXCollections.observableArrayList(fs.getListRepParent(path));
        comboxRep.setItems(itemsComboRep);
        comboxRep.getSelectionModel().selectLast();

        comboxRep.setOnAction(event ->
        {
            majListView(comboxRep.getSelectionModel().getSelectedItem());
        });

    }

    /*
     * updates the data model (i.e., what is stored inside the list)
     * depending on the selected item in the combo
     * */

    private void majListView(String path) {
        ObservableList<String> itemsListView = FXCollections.observableArrayList(fs.getListFile(path));
        listViewFile.setItems(itemsListView);
        btnOpen.setDisable(true);
        listViewFile.setOnMouseClicked(event ->
                {
                    btnOpen.setDisable(false);
                    if (event.getClickCount() == 2) {
                        btnOpen.fire();
                    }
                }
        );


//        btnCancel.setOnAction(event -> {
//            System.exit(0);
//        });
//
//        btnOpen.setOnAction(event -> {
//            File element = new File(comboxRep.getValue() + listViewFile.getSelectionModel().getSelectedItem());
//            if (element.isFile()) {
//                //can do something with the file (open it?)
//                System.out.println("1");
//                System.exit(0);
//            } else if (element.isDirectory()) {
//                majComboBox(element.getAbsolutePath());
//            } else {
//                System.out.println("2");
//            }
//        });
    }
}






