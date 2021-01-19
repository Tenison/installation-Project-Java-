package application.views;

import java.io.File;
import java.io.IOException;

import application.Main;
import application.models.Fold_Class;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreatCompileController {
	@FXML Button cancel_btn;
	@FXML Label Name;
	@FXML Label Location;
	@FXML Label Rev;
	
	Stage stage ;
	File file;
	

	
	
	public void cancel() throws IOException{
		// get a handle to the stage
	    Stage stage = (Stage) cancel_btn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	@FXML
	public void initialize(){
		Name.setText(Main.newProcedureTitleFull);
		Location.setText(Fold_Class.fullPath);
	}


}
