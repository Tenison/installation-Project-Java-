package application.views;

import java.io.File;
import java.io.IOException;


import application.Main;
import application.models.Fold_Class;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ErrorController{
	@FXML Text procedure_labelTitle;
	@FXML Button cancel_btn;
	@FXML Button dont_save_btn;
	@FXML Button save_btn;
	
	private root_paneController controller;
	
	public void start(root_paneController controller){
		this.controller = controller;
	}
	
	public void cancel() throws IOException{
		// get a handle to the stage
	    Stage stage = (Stage) cancel_btn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	public void d_save() throws IOException{
		Main.procedureDataSet = null;
		Main.wordParamData.clear();
		Main.deleteDir(new File(Fold_Class.fullPath));
		controller.selectionTreeView.setRoot(null);
		controller.display_procedure_form();
		cancel();

	}
	
	public void save() throws IOException{
		controller.save();
	    Main.procedureDataSet = null;
	    Main.wordParamData.clear();
	    controller.newProcedure = null;
	    controller.selectionTreeView.setRoot(null);
	    controller.display_procedure_form();
	    cancel();
	}

	@FXML
	public void initialize(){
		procedure_labelTitle.setText("\"" + Main.newProcedureTitleFull + "\"");
	}
}
