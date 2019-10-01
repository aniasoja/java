package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;

import java.io.File;

public class Controller {


    //ObservableList<String> optionsListview;  // string of files in directory
    //ObservableList<String> optionsListRep  ; // string of parent directories
    //FileSelector fs;						 // class the reads directories

	
	@FXML
    private Button btnCancel;
	
	@FXML
    private Button btnOpen;
    
	@FXML
	private  ListView listViewFile;		// list to show files inside directory
    
	@FXML
	private ComboBox<String> comboxRep; // combo box of parent directories
	
	FileSelector fs = new FileSelector();

    //public Controller(){
    //    super();
    //     fs = new FileSelector();
    //     initialize();
    // }

	@FXML
    public void initialize(){
    	
    	String path = System.getProperty("user.home");
    	System.out.println(path);
    	this.btnOpen.setOnAction(event -> {
    		System.out.println("Open click");
    	});
    	majComboBox(path);
    	majListView(path);
    	btnCancel.setOnAction(event -> {
    		System.exit(0);
    	});
    	btnOpen.setOnAction(event ->{
    		File element = new File(comboxRep.getValue()+listViewFile.getSelectionModel().getSelectedItem());
    		if(element.isFile()) {
    			//element.open(element);
    			System.exit(0);
    		}
    		else if(element.isDirectory()) {
    			majComboBox(element.getAbsolutePath());
    		}
    		else {
    			System.out.println("Stranger things are happening");
    		}
    	});
    };

    	
    /* 
     * updates the combo view based on the selected path
    */ 
    
    private void majComboBox(String path){
    	
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
    		if(event.getClickCount()==2) {
    			btnOpen.fire();
    		}
    	}
    	);
/*
    	
    	
    */
}}






