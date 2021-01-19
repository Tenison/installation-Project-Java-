package application;

import java.io.IOException;
import application.models.ImportModel;
import application.models.New_procedureModel;
import application.models.New_workplanModel;
import application.views.CreatCompileController;
import application.views.ErrorController;
import application.views.ImportController;
import application.views.new_procedureController;
import application.views.new_workplanController;
import application.views.root_paneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Root_parent extends AnchorPane{
	@FXML BorderPane base_pane;
	@FXML AnchorPane center_pane;
	@FXML BorderPane border_pane;
	@FXML AnchorPane subroot_pane, left_pane; 
	@FXML SplitPane doc_pane;
	@FXML SplitPane subleft_pane;
	@FXML AnchorPane uppersubleft_pane;
	@FXML AnchorPane lowersubleft_pane;
	@FXML AnchorPane middle_pane;
	@FXML AnchorPane right_pane; 
	@FXML SplitPane subright_pane;
	@FXML AnchorPane uppersubright_pane;
	@FXML AnchorPane lowersubright_pane;
	
	//controllers...
	private root_paneController controller;
	private new_procedureController controllerProcedure;
	private ErrorController controllerError;
	private ImportController controllerImport;
	private CreatCompileController controllerCompile;
	private new_workplanController controllerWorkplan;
	
	
	public Root_parent(){//loads the parent fxml
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/root_parentPane.fxml"));
		
		fxmlLoader.setRoot(this); 
		//fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
            // Set the root_parent into the controller.
            controller = fxmlLoader.getController();
            controller.setRoot_parent(this); 
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
	}
	
	public void Show_about() throws IOException{

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/About.fxml"));
		
		
		try { 
			AnchorPane errorseens = fxmlLoader.load();
			Stage newsndStage = new Stage();
			newsndStage.setTitle(" About Technip Installation Guide Studio");
			newsndStage.initModality(Modality.WINDOW_MODAL);
			newsndStage.initOwner(Main.primaryStage);
			Scene scene = new Scene(errorseens);
			newsndStage.setScene(scene);
			
			// Set the root parent into the controller.
			
			newsndStage.showAndWait();
			
		} catch (IOException e) {
            e.printStackTrace();
		}
	}
	
	public void Show_compile() throws IOException{//loads new import fxml
		new Main();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/CreateCompile.fxml"));
		
		
		try { 
			AnchorPane seen = fxmlLoader.load();
			Stage newsndStage = new Stage();
			newsndStage.setTitle("Create Compilation");
			newsndStage.initModality(Modality.WINDOW_MODAL);
			newsndStage.initOwner(Main.primaryStage);
			Scene scene = new Scene(seen);
			newsndStage.setScene(scene);
			
			// Set the root parent into the controller.
            controllerCompile = fxmlLoader.getController();
			
			newsndStage.showAndWait();
		} catch (IOException e) {
            e.printStackTrace();
		}	
	}
	
	public boolean Show_import(ImportModel model, Stage stage) throws IOException{//loads new import fxml
		new Main();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Import_form.fxml"));
		
		
		try { 
			AnchorPane errorseen = fxmlLoader.load();
			Stage newsndStage = new Stage();
			newsndStage.setTitle("Import");
			newsndStage.initModality(Modality.WINDOW_MODAL);
			newsndStage.initOwner(Main.primaryStage);
			Scene scene = new Scene(errorseen);
			newsndStage.setScene(scene);
			
			// Set the root parent into the controller.
            controllerImport = fxmlLoader.getController();
            controllerImport.start();
            controllerImport.setImportInstance(model, stage);
			
			newsndStage.showAndWait();
			return controllerImport.isOkClicked();
		} catch (IOException e) {
            e.printStackTrace();
            return false;
		}	
	}
	
	public void Show_newError() throws IOException{//loads new save error fxml
		new Main();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Save_error.fxml"));
		
		
		try { 
			AnchorPane errorseen = fxmlLoader.load();
			Stage newsndStage = new Stage();
			newsndStage.setTitle("Error");
			newsndStage.initModality(Modality.WINDOW_MODAL);
			newsndStage.initOwner(Main.primaryStage);
			Scene scene = new Scene(errorseen);
			newsndStage.setScene(scene);
			
			// Set the root parent into the controller.
            controllerError = fxmlLoader.getController();
            controllerError.start(controller);
			
			newsndStage.showAndWait();
			
		} catch (IOException e) {
            e.printStackTrace();
		}
	}
	
	public boolean Show_newProcedure(New_procedureModel newProcedure) throws IOException{//loads new procedure fxml
		new Main();

		FXMLLoader secondfxmlLoader = new FXMLLoader(getClass().getResource("views/new_procedure_form.fxml"));
		
		//fxmlLoader.setRoot(this); 
		//fxmlLoader.setController(this);
		
		try { 
			AnchorPane secondseen = secondfxmlLoader.load();
			Stage newsndStage = new Stage();
			newsndStage.setTitle("Create New Procedure");
			secondseen.getStyleClass().add("mid_pane_layout");
			newsndStage.initModality(Modality.WINDOW_MODAL);
			newsndStage.initOwner(Main.primaryStage);
			Scene scene = new Scene(secondseen);
			newsndStage.setScene(scene);
			
			// Set the rootparent into the controller.
			controllerProcedure = secondfxmlLoader.getController();
			controllerProcedure.setProcedureStage(newsndStage);
			controllerProcedure.setProcedureInstance(newProcedure);
			
			newsndStage.showAndWait();
			
			return controllerProcedure.isOkClicked();
		} catch (IOException e) {
            e.printStackTrace();
            return false;
		}
	}
	
	public boolean show_newWorkplan(New_workplanModel workplanModel) throws IOException{//loads new wplan fxml
		new Main();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/new_workplan_form.fxml"));
		
		//fxmlLoader.setRoot(this); 
		//fxmlLoader.setController(this);
		
		try { 
			AnchorPane secondwp_form = fxmlLoader.load();
			Stage newsndStage = new Stage();
			newsndStage.setTitle("Create New Workplan");
			newsndStage.initModality(Modality.WINDOW_MODAL);
			newsndStage.initOwner(Main.primaryStage);
			Scene scene = new Scene(secondwp_form);
			newsndStage.setScene(scene);
			
			controllerWorkplan = fxmlLoader.getController();
			controllerWorkplan.setWorkplanStage(newsndStage);
			controllerWorkplan.setworkplanInstance(workplanModel);
			newsndStage.showAndWait();
			
			return controllerWorkplan.isOkClicked();
		} catch (IOException e) {
            e.printStackTrace();
            return false;
 		}
	}
}
