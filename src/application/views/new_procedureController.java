package application.views;

import java.io.File;
import java.io.IOException;
import application.models.New_procedureModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class new_procedureController {
	@FXML AnchorPane uppersubleft_pane;
	@FXML AnchorPane lowersubleft_pane;
	@FXML AnchorPane newprocedure_pane;
	@FXML Button cancel_btn;
	@FXML TextField titleField; 
	@FXML TextField externalNumberField;
	@FXML TextField internalNumberField;
	@FXML TextField revisionField;
	@FXML Button ok_btn;
	@FXML CheckBox chk_intro;
    @FXML CheckBox chk_wp;
    @FXML CheckBox chk_conculsion;
    @FXML Button browse_btn;
    @FXML TextField directorypath;
    

	private Stage procedureStage;
	private boolean okClicked = false;
	private New_procedureModel procedure_model;

	public void cancel() throws IOException{
		// get a handle to the stage
	    Stage stage = (Stage) cancel_btn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	
    public void directoryPath_chk(){
    	if(procedure_model.getDirectory_Path().isEmpty()){
    		procedure_model.setDirectory_Path(null);
    	}    	
    }
    
    @FXML
    private void handleOk() throws IOException{
			if(isInputValid()){
				procedure_model.setTitle(titleField.getText());
				procedure_model.setExternal_number(externalNumberField.getText().isEmpty() ? "-" : externalNumberField.getText());
				procedure_model.setInternal_number(internalNumberField.getText().isEmpty() ? "-" : internalNumberField.getText());
				procedure_model.setRevision_number(revisionField.getText().isEmpty() ? "-" : revisionField.getText());
				procedure_model.setChk_one(chk_intro.isSelected()?true:false);
				procedure_model.setChk_two(chk_wp.isSelected()?true:false);
				procedure_model.setChk_three(chk_conculsion.isSelected()?true:false);
				procedure_model.setDirectory_Path(directorypath.getText());
				directoryPath_chk();
				
			    okClicked = true;
			    procedureStage.close();
			}
    }
 
	/**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() throws IOException{
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Not a valid Title!\n"; 
        }
        //if (revisionField.getText() == null || revisionField.getText().length() == 0) {
        //    errorMessage += "Not a valid Revision Number!\n"; 
        //}

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(procedureStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
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
    
     public void saveas() throws IOException {
		try {    	
			
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Save");
			File file = chooser.showDialog(procedureStage);
			directorypath.setText(file.getAbsolutePath().toString());
			
			
			
		} catch (NullPointerException e) {
	          e.printStackTrace();
	      }catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    /**
	 * @param okClicked the okClicked to set
	 */
	public void setOkClicked(boolean okClicked) throws IOException{
		this.okClicked = okClicked;
	}
    
    //* Sets the procedure form to be edited in the dialog.
    public void setProcedureInstance(New_procedureModel procedure_model) throws IOException{//constructor to instantiate New_procedureModel
			this.procedure_model = procedure_model;
    }

    public void setProcedureStage(Stage procedureStage) throws IOException{
        this.procedureStage = procedureStage;   
        //this.procedureStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }
}
