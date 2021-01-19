package application;
	
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/*
 * TIG Studio for technip
 * This application aids Technip engineers create procedures.
 * Authors : Osei-Owusu and Alexander Domakyaareh
 * */

import application.models.DragIconClass;
import application.models.Fold_Class;
import application.models.ShareModel;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class Main extends Application {
    public static final String SPLASH_IMAGE =  "images\\technipshp.png";
	public static Stage primaryStage;//creates a main stage for the fx-scene.
	public static ObservableList<DragIconClass> procedureDataSet;//Array to collect instances of introduction, varies work plans, conculsions
	public static ObservableList<String> recentlySave = FXCollections.observableArrayList();
	Fold_Class fold;//default save path
	public static String newProcedureTitleFull ;
	BorderPane root = new BorderPane();
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private static final int SPLASH_WIDTH = 625;
    private static final int SPLASH_HEIGHT = 227;
    public static ObservableList<ShareModel> wordParamData;


    
	@Override
    public void init() throws Exception {
        try {
			ImageView splash = new ImageView(new Image(new File(SPLASH_IMAGE).toURI().toString()));
			loadProgress = new ProgressBar();
			loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
			progressText = new Label("Loading files ....");
			splashLayout = new VBox();
			splashLayout.getChildren().addAll(splash, loadProgress, progressText);
			progressText.setAlignment(Pos.TOP_CENTER);
			splashLayout.setStyle(
					"-fx-background-color: LAVENDER; " 
			);
			splashLayout.setEffect(new DropShadow());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = new Stage();
		fold = new Fold_Class();
		fold.createfolder("Documents/Technip Installation Guide",null);
		fold.createTemplatefolder();
		wordParamData = FXCollections.observableArrayList();
		
		
		//for splash screen
        final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {

                for (int i = 0; i < 10; i++) {
                    Thread.sleep(300);
                    updateProgress(i + 1, 8);
                    updateMessage("Loading files...");
                }
                updateMessage("All Files Loaded");

                ObservableList<String> foundFriends = null;
				return foundFriends;
            }
        };
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("views/icon.png")));
        showSplash(
        		primaryStage,
                friendTask,
                () -> showMainStage()
        );
        new Thread(friendTask).start();
        //ends here
		
	}
	
	private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit? Not saving may cause file losses\n\t\t\t\t\t\t  Remember to save before exist"
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(Main.primaryStage);

        // normally, you would just use the default alert positioning,
        // but for this simple sample the main stage is small,
        // so explicitly position the alert so that the main window can still be seen.
        ///closeConfirmation.setX(Main.primaryStage.getX());
        ///closeConfirmation.setY(Main.primaryStage.getY() + Main.primaryStage.getHeight());

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
    };
	
	private void showMainStage(){
		try {			
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene = new Scene(root, screenBounds.getWidth()-15, screenBounds.getHeight()-35);
			scene.getStylesheets().add(getClass().getResource("views/application.css").toExternalForm());
			Main.primaryStage.setScene(scene);
			Main.primaryStage.setTitle("Technip Installation Guide");
			Main.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("views/icon.png")));
			Main.primaryStage.show();
			//Main.primaryStage.setFullScreen(true);
			Main.primaryStage.setOnCloseRequest(confirmCloseEventHandler);

	        Button closeButton = new Button("Close Application");
	        closeButton.setOnAction(event ->
	        Main.primaryStage.fireEvent(
	                        new WindowEvent(
	                        		Main.primaryStage,
	                                WindowEvent.WINDOW_CLOSE_REQUEST
	                        )
	                )
	        );
		} catch(Exception e) {
			e.printStackTrace();
		}
		root.setCenter(new Root_parent());
	}
	
    private void showSplash(final Stage initStage,Task<?> task,InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(2.0), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	public static boolean deleteDir(File dir) {
		  if (dir.isDirectory()) {
		         String[] children = dir.list();
		         for (int i = 0; i < children.length; i++) {
		            boolean success = deleteDir (new File(dir, children[i]));
		            
		            if (!success) {
		            	System.out.println("The directory is not deleted.");
		               return false;
		            }
		         }
		  }
		  System.out.println("The directory is deleted.");
		  dir.delete();	 
		  return  false;
	}
	
	public static boolean deleteFile(Path path){
		try {
			Files.deleteIfExists(path);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}
	
	public interface InitCompletionHandler {
        void complete();
    }

	/**
	 * @return the wordParamData
	 */
	public static ObservableList<ShareModel> getWordParamData() {
		return wordParamData;
	}



	/**
	 * @param wordParamData the wordParamData to add
	 */
	public static void addWordParamData(ShareModel wordParamData) {
		Main.wordParamData.add(wordParamData);
	}
	
	
}
