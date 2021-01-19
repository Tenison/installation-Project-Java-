package application.views;

import java.io.IOException;

import application.models.New_workplanModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class new_workplanController {
	@FXML AnchorPane uppersubleft_pane;
	@FXML AnchorPane lowersubleft_pane;
	@FXML AnchorPane newprocedure_pane;
	@FXML TextField wpNumberField;
	@FXML Button can_btn;



	private Stage workplanStage;
	private New_workplanModel workplanModel;
	private boolean okClicked = false;
	
	
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() throws IOException{
        return okClicked;
    }
    
    public void setWorkplanStage(Stage workplanStage) throws IOException{
        this.workplanStage = workplanStage;   
        //this.procedureStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }
    
    //* Sets the workplan form to be edited in the dialog.
    public void setworkplanInstance(New_workplanModel workplanModel) throws IOException{//constructor to instantiate New_workplanModel
			this.workplanModel = workplanModel;
   }
    
	public void cancel() throws IOException{
		// get a handle to the stage
	    Stage stage = (Stage) can_btn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
    
    @FXML
    private void handleOk() throws IOException{
    	if(isInputValid()){
    		this.workplanModel.setWpnumber(wpNumberField.getText());
    		
		    okClicked = true;
		    workplanStage.close();    		
    	}		    
    }
    
    private boolean isInputValid() throws IOException{
        String errorMessage = "";
        if (wpNumberField.getText() == null || wpNumberField.getText().length() == 0) {
            errorMessage += "Not a valid Workplan number!"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(workplanStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
      }
}
