package application.views;

import java.io.File;
import java.io.IOException;

import application.models.ImportModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ImportController {
	@FXML Button cancel_btn;
	@FXML TextField directorypath;
	
	private ImportModel importmodel;
	private boolean okClicked = false;
	private Stage stage;
	File file;
	
	public void cancel() throws IOException{
		// get a handle to the stage
	    Stage stage = (Stage) cancel_btn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
    @FXML
    private void handleOk() throws IOException{
    	Stage importStage = (Stage) cancel_btn.getScene().getWindow();
    	if(isInputValid()){
    		this.importmodel.setFullpath(file.getAbsolutePath().toString());
    		
		    okClicked = true;
		    importStage.close();    		
    	}		    
    }
	
	private boolean isInputValid() throws IOException{
        String errorMessage = "";
        if (directorypath.getText().isEmpty()) {
            errorMessage += "Invalid import!"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
      }
	
	/**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() throws IOException{
        
		return okClicked;
    }
	
    public void searchImport() throws IOException {
		try {    		
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Import");
			 file = chooser.showDialog(this.stage);
			System.out.println(file.getAbsolutePath().toString());
			directorypath.setText(file.getAbsolutePath().toString());
		} catch (NullPointerException e) {
	          e.printStackTrace();
	      }catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    public void setImportInstance(ImportModel model, Stage stage)throws IOException{
		this.importmodel = model;
		this.stage = stage;
	}
    
    public void start()throws IOException{
	}
}
