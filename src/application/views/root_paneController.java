package application.views;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystems;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import application.Main;
import application.Root_parent;
import application.models.ChkSave;
import application.models.ContextmenuClass;
import application.models.DragIconClass;
import application.models.DragNode2;
import application.models.New_procedureModel;
import application.models.New_workplanModel;
import application.models.ShareModel;
import application.models.Fold_Class;
import application.models.ImportModel;
import application.models.TaskModel;
import application.models.WordClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class root_paneController {
	@FXML BorderPane base_pane;
	@FXML AnchorPane center_pane;
	@FXML BorderPane border_pane;
	@FXML AnchorPane subroot_pane, left_pane; 
	@FXML SplitPane doc_pane;
	@FXML SplitPane subleft_pane;
	@FXML AnchorPane uppersubleft_pane;
	@FXML AnchorPane lowersubleft_pane;
	@FXML AnchorPane technip_pane;
	@FXML AnchorPane right_pane; 
	@FXML SplitPane subright_pane;
	@FXML AnchorPane uppersubright_pane;
	@FXML AnchorPane lowersubright_pane;
	@FXML TreeView<String> selectionTreeView;
	@FXML TitledPane uppersubleft_titlepane;
	@FXML TableView<TaskModel> taskTable;
	@FXML TableColumn<TaskModel, String> task;
	@FXML ListView<String> recentSave;
    @FXML Label status;
    @FXML Label rev_numb;
    @FXML ProgressBar progressBar;
    @FXML ScrollPane middle_pane_scroll;
    @FXML MenuItem createCompile_btn;
    @FXML Button btn13;
    @FXML Button add_btn;//
    @FXML TextField parameter;
    @FXML TextArea text_one;
	@FXML TableView<ShareModel> Shared_table;
	@FXML TableColumn<ShareModel,String> param_wp;
	@FXML TableColumn<ShareModel, String> text_wp;
	
	private Root_parent Top; //Root_parent instance
	private TreeItem<String> root;	//Creating a tree view instance
	private boolean isProcedureCreated = false;//chk if a procedure has been created before creating a workplan
	public New_procedureModel newProcedure = null; //new procedure model instance
	private New_workplanModel workplan_model; //new workplan model
	private ImportModel import_project;
	//private compileModel compile_project;
	private WordClass word;
	Fold_Class fold;
	private String optionName;
	private List<String> import_results;
	private List<String> import_share_results;
	private DragNode2 pane;

	ChkSave chkSave = new ChkSave();
	ContextmenuClass menu;
	@SuppressWarnings("unused")
	private XWPFDocument document;
	private XWPFDocument documents;
	private XWPFDocument src1Document;
	private XWPFDocument src2Document;
	private BufferedWriter config_Save;
	
	final ProgressBar[] pbs = new ProgressBar[6];
	
	public void about() throws Exception{
		openURL("http://www.technipfmc.com/");
	}
	
    public void aboutApp() throws Exception{
		Top.Show_about();
	}
	
	public void clkAdd() throws Exception{
		ShareModel sharedWordparam = new ShareModel();
		sharedWordparam.setParameter(parameter.getText());
		sharedWordparam.setText(text_one.getText());
		System.out.println(text_one.getText());
		System.out.print(parameter.getText());
		text_one.setText(null);
		parameter.setText(null);
		Main.addWordParamData(sharedWordparam);
		DisplaySharedTable();
	}
	
	public boolean compile() throws Exception{
		save();
		
		try {
		    File folder = new File(Fold_Class.fullPath + "\\temp");
		    	folder.mkdir();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    return false;
		}
		System.out.println(Fold_Class.fullPath );

		word = new WordClass();
		int i = 0;
		
	    try { 
	    	document = new XWPFDocument();
	    } catch (Exception e) {e.printStackTrace();}
	    
	    
	    for(DragIconClass leaf : sortedlist()){
	    	if(leaf.getSorting() == 2){
	    		word.wpmerge(new FileInputStream(Fold_Class.fullPath + "\\" + leaf.getTitle() + "\\" + leaf.getLeafData().get(0).getTaskName() + ".docx"), 
	    		new FileInputStream(Fold_Class.fullPath + "\\" + leaf.getTitle() + "\\" + leaf.getLeafData().get(2).getTaskName() + ".docx"), 
	    		new FileInputStream(Fold_Class.fullPath + "\\" + leaf.getTitle() + "\\" + leaf.getLeafData().get(1).getTaskName() + ".docx"), 
	    		new FileOutputStream(Fold_Class.fullPath + "\\temp\\" + i + ".docx"));
	    		System.out.println(Fold_Class.fullPath + "\\" + leaf.getTitle() + "\\" + leaf.getLeafData().get(2).getTaskName() + ".docx");
	    	}else{
		  	  	try {
		  	  		FileOutputStream outstream = new FileOutputStream(Fold_Class.fullPath + "\\temp\\" + i + ".docx");
		  	  		documents = new XWPFDocument(OPCPackage.open(Fold_Class.fullPath + "\\" + leaf.getTitle() + ".docx"));
				    documents.write(outstream);
				    
				    outstream.close();
				    documents.close();
				    
		  	  	} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
	    	}
	    	i++;//Incremental value to create temporary word files...
	     }
	     //looping through temp file!!
	     {
	    	 OPCPackage src1Package = OPCPackage.open(new FileInputStream(Fold_Class.fullPath + "\\temp\\" + 0 + ".docx"));
	    	 src1Document = new XWPFDocument(src1Package);
	    	 CTBody src1Body = src1Document.getDocument().getBody();
	    	 int j;
	    	 
	    	 for(j = 1; j < i; j++){
			     OPCPackage src2Package = OPCPackage.open(Fold_Class.fullPath + "\\temp\\" + j + ".docx");
			     src2Document = new XWPFDocument(src2Package);
			     
			     if(!word.chkifwordempty(src2Document)){
			    	 CTBody src2Body = src2Document.getDocument().getBody();
			    	 src2Document.close();
			    	 word.appendBody(src1Body, src2Body);
			     }
			     src2Document.close();
			     Main.deleteFile(FileSystems.getDefault().getPath(Fold_Class.fullPath + "\\temp\\" + j + ".docx"));
	    	 }
	    	 
		     src1Document.write(new FileOutputStream(Fold_Class.fullPath + "\\" + this.newProcedure.getTitle() + ".docx"));
		     src1Document.close();
		     for(ShareModel ref : Main.wordParamData){
		    	 word.replaceTexts(Fold_Class.fullPath + "\\" + this.newProcedure.getTitle() + ".docx", ref.getParameter(), ref.getText());
		     }    
	     }
	     
	     Main.deleteDir(new File(Fold_Class.fullPath + "\\temp"));//..............................
	     Main.deleteFile(FileSystems.getDefault().getPath(Fold_Class.fullPath + "\\temp"));//..............................
	     createCompile_btn.setDisable(false);
	     btn13.setDisable(false);
	     status.setText("Project Created");
	     return true;
	}
	
	private void config(){
		FileWriter fileconfig;
		try {
		    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
		    //get current date time with Date()
		    Date date = new Date();
		    
			fileconfig = new FileWriter(System.getProperty("user.home") + "\\Documents\\Technip Installation Guide\\config.txt", true);
			PrintWriter config = new PrintWriter(fileconfig);
			config_Save = new BufferedWriter(config);
		    config.println(this.newProcedure.getTitle() + " - " + "Date : " + dateFormat.format(date) + "  Time : " + dateFormat1.format(date) );
		    config.close();
		    config_Save.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	  
	private String configTail( File file, int lines) {
	    java.io.RandomAccessFile fileHandler = null;
	    try {
	        fileHandler = 
	            new java.io.RandomAccessFile( file, "r" );
	        long fileLength = fileHandler.length() - 1;
	        StringBuilder sb = new StringBuilder();
	        int line = 0;

	        for(long filePointer = fileLength; filePointer != -1; filePointer--){
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	             if( readByte == 0xA ) {
	                if (filePointer < fileLength) {
	                    line = line + 1;
	                }
	            } else if( readByte == 0xD ) {
	                if (filePointer < fileLength-1) {
	                    line = line + 1;
	                    sb.append(',');
	                }
	            }
	            if (line >= lines) {
	                break;
	            }
	            sb.append( ( char ) readByte );
	        }

	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    }
	    finally {
	        if (fileHandler != null )
	            try {
	                fileHandler.close();
	            } catch (IOException e) {
	            }
	    }
	}
	
    public void createCompile_Pro() throws Exception{
			Top.Show_compile();
			
	}
    	
    public void createConclusion() throws IOException{ 	
    	if(isProcedureCreated){
	    	if(loopList(Main.procedureDataSet,word.CONCLUSION)){
	    		DragIconClass introDrag = new DragIconClass(word.CONCLUSION,3, new TreeItem<String>(word.CONCLUSION));
	        	introDrag.setLeafData(new TaskModel(word.CONCLUSION));
	        	Main.procedureDataSet.add(introDrag);
		        word.createword(word.CONCLUSION, Fold_Class.fullPath, null);
				root.getChildren().clear();
				//sortedlist();
				for (DragIconClass leaf : sortedlist())
					root.getChildren().add(leaf.getLeaf());//loop end
		        {
		        	DragNode2 pane1 = new DragNode2();
		        	pane1.createObjects(sortedlist());
		        	middle_pane_scroll.setContent(pane1.getPan());
		        }
		        status.setText("Conclusion Created");
		        chkSave.setChkSave(false);
	    	}
    	}else{
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Operation");
            alert.setContentText("Please create procedure first");       
            alert.showAndWait();
		}
    }		
    
    public void createIntro() throws IOException{
    	if(isProcedureCreated){
	    	if(loopList(Main.procedureDataSet,word.INTRO)){
	    		DragIconClass introDrag = new DragIconClass(word.INTRO,1, new TreeItem<String>(word.INTRO));
	        	introDrag.setLeafData(new TaskModel(word.INTRO));
	        	Main.procedureDataSet.add(introDrag);
		        word.createword(word.INTRO, Fold_Class.fullPath, null);
				root.getChildren().clear();
				sortedlist();
				for (DragIconClass leaf : sortedlist())
					root.getChildren().add(leaf.getLeaf());//loop end
		        {
		        	DragNode2 pane1 = new DragNode2();
		        	pane1.createObjects(sortedlist());
		        	middle_pane_scroll.setContent(pane1.getPan());
		        }
		        status.setText("Introduction Created");
		        chkSave.setChkSave(false);
	    	}
    	}else{
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Operation");
            alert.setContentText("Please create procedure first");       
            alert.showAndWait();
		}
    }
    
    public  void display_procedure_form() throws IOException {  	
		//if(selectionTreeView.getRoot() == null){
		if(Main.procedureDataSet == null){	
    		New_procedureModel newPorcedure = new New_procedureModel();
    		this.newProcedure = newPorcedure; // Passing newProcedure instance to make it global.. 
    		
			boolean okClicked = Top.Show_newProcedure(this.newProcedure);
			
			if (okClicked) {
				Main.procedureDataSet = FXCollections.observableArrayList();
				root = new TreeItem<>(this.newProcedure.getTitle());//treeview root
				Main.newProcedureTitleFull = this.newProcedure.getTitle();
				selectionTreeView.setRoot(root);//create root
				selectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				root.setExpanded(true);
				menu = new ContextmenuClass(selectionTreeView, root, middle_pane_scroll);
				selectionTreeView.setContextMenu(menu.getMenu());
				
		        isProcedureCreated = true;///chk if procedure is created!!!!
		        fold = new Fold_Class();
		        Fold_Class.fullPath = fold.createfolder(filepath(this.newProcedure.getDirectory_Path(),this.newProcedure.getTitle()),this.newProcedure.getDirectory_Path());
		        System.out.println(Fold_Class.fullPath);
		        
		        if(this.newProcedure.getChk_one()){//chking if the intro chk_box has been selected...
		        	DragIconClass introDrag = new DragIconClass(word.INTRO,1, new TreeItem<String>(word.INTRO));
		        	introDrag.setLeafData(new TaskModel(word.INTRO));
		        	Main.procedureDataSet.add(introDrag);
			        word.createword(word.INTRO, Fold_Class.fullPath,null);
			        {
			        	pane.createObjects(sortedlist());
			        }		        	
		        }
		        if(this.newProcedure.getChk_two()){
		        	DragIconClass wpDrag = new DragIconClass("Work Plan 1",2,new TreeItem<String>("Work Plan 1"));
		        	wpDrag.setLeafData(new TaskModel(word.INTRO+"_"));
		        	wpDrag.setLeafData(new TaskModel("Steps"));
		        	wpDrag.setLeafData(new TaskModel(word.CONCLUSION));
		        	Main.procedureDataSet.add(wpDrag);
		        	{
		        		pane.createObjects(sortedlist());
			        }
		        	Fold_Class wpfold = new Fold_Class();
					String PathTemp = Fold_Class.fullPath +"\\Work Plan 1";
					System.out.println(PathTemp);
					wpfold.createfolderWithFullpath(PathTemp);
					word.createword(word.INTRO+"_", PathTemp,"Work Plan 1");
					word.createword("Steps", PathTemp,"Work Plan 1");
					word.createword(word.CONCLUSION, PathTemp,null);
    			}
		        if(this.newProcedure.getChk_three()){
		        	word.createword(word.CONCLUSION, Fold_Class.fullPath, null);
		        	DragIconClass conclusionDrag = new DragIconClass(word.CONCLUSION,3,new TreeItem<String>(word.CONCLUSION));
		        	conclusionDrag.setLeafData(new TaskModel(word.CONCLUSION));
		        	Main.procedureDataSet.add(conclusionDrag);
			        {
			        	pane.createObjects(sortedlist());
			        }
		       }

		       //root.getChildren().addAll(Main.procedureDataSet);
			   for (DragIconClass leaf : sortedlist()) {
				   root.getChildren().add(leaf.getLeaf());
			   }
			}
			chkSave.setChkSave(false);
			okClicked = false;
			try {
				rev_numb.setText("Rev : " + this.newProcedure.getRevision_number());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.Top.Show_newError();
		}
	}
	 
    public void display_workplan_form() throws IOException{
		if(isProcedureCreated){
			New_workplanModel workplan_model = new New_workplanModel();
			this.workplan_model = workplan_model;
			boolean okClicked = Top.show_newWorkplan(this.workplan_model);
			if (okClicked) {
				this.workplan_model.setFullwp_name("Work Plan " + this.workplan_model.getWpnumber());
				
				if(loopList(sortedlist(),this.workplan_model.getFullwp_name())){
						DragIconClass wpDrag = new DragIconClass(this.workplan_model.getFullwp_name(),2,new TreeItem<String>(this.workplan_model.getFullwp_name()));
						Main.procedureDataSet.add(wpDrag);
			        	wpDrag.setLeafData(new TaskModel(word.INTRO+"_"));
			        	wpDrag.setLeafData(new TaskModel("Steps"));
			        	wpDrag.setLeafData(new TaskModel(word.CONCLUSION));
						root.getChildren().clear();
						for (DragIconClass leaf : sortedlist())
							root.getChildren().add(leaf.getLeaf());//loop end
						{
				        	DragNode2 pane1 = new DragNode2();
				        	pane1.createObjects(sortedlist());
				        	middle_pane_scroll.setContent(pane1.getPan());
				        }
						Fold_Class wpfold = new Fold_Class();
						wpfold.createfolderWithFullpath(Fold_Class.fullPath + "\\" +this.workplan_model.getFullwp_name());
						word.createword(word.INTRO+"_", Fold_Class.fullPath + "\\" +this.workplan_model.getFullwp_name(), this.workplan_model.getFullwp_name());
						word.createword("Steps", Fold_Class.fullPath + "\\" +this.workplan_model.getFullwp_name(), this.workplan_model.getFullwp_name());
						word.createword(word.CONCLUSION, Fold_Class.fullPath + "\\" +this.workplan_model.getFullwp_name(), null);
						chkSave.setChkSave(false);
						
				}
			}
		}else{
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Operation");
            alert.setContentText("Please create procedure first");       
            alert.showAndWait();
		}
	}
    	
    public void displayconfig() throws Exception{
		File fileconfig = new File(System.getProperty("user.home") +"\\Documents\\Technip Installation Guide\\config.txt");
		Main.recentlySave.addAll(configTail( fileconfig, 8).split(",")) ;
		recentSave.setItems(Main.recentlySave);
		
	}
	
    public void DisplaySharedTable(){
		Shared_table.setItems(Main.getWordParamData());
	}
	
	protected String DisplayTable(String Leafname){//displays a table of the tasks<task>
		for(DragIconClass ref : sortedlist()){
			if(Leafname.equalsIgnoreCase(ref.getTitle())){
				taskTable.setItems(ref.getLeafData());
			}
		}
		{//Please NOTE: this is not part of this code bits!!!just checks for if saved
			if(chkSave.isChkSave() == true){
				status.setText("Saved");
			}else{
				status.setText("Not Saved");
			}
		}
		return Leafname;
	}
	
	public String filepath(String path,String foldername){
    	if(path == null){
    		return "Documents\\Technip Installation Guide\\" + foldername;
    	}
    	return path + "\\" + foldername;
    }
	
	/**
	* @return the newProcedure
	 */
	public String getNewProcedureTitle() {
		return this.newProcedure.getTitle();
	}
		
	public void import_Pro() throws IOException{
		Stage stage = (Stage) center_pane.getScene().getWindow();
		ImportModel importmodel = new ImportModel();
		import_project = importmodel;
		boolean okClicked = Top.Show_import(this.import_project, stage);
		if(okClicked){
			Main.procedureDataSet = FXCollections.observableArrayList();
			Fold_Class.fullPath = this.import_project.getFullpath();
			File file = new File(Fold_Class.fullPath + "\\tig" + ".txt");
			File file1 = new File(Fold_Class.fullPath +"\\" + "wpTig.txt");
			FileReader import_r = null;
			New_procedureModel newPorcedure = new New_procedureModel();
			this.newProcedure = newPorcedure; // Passing newProcedure instance to make it global.. 
	        try {
		        	import_r = new FileReader(file);
		        	BufferedReader import_ = new BufferedReader(import_r);
		        	String line;
		        	String[] sepa = null;
		        	import_results = new ArrayList<String>();
		        	while ((line = import_.readLine()) != null){
		        		import_results.add(line);
		        	}
		            import_.close();
		            import_r.close();
		            {//creating imported procedure instance
		            sepa = import_results.get(0).split(",");
			            this.newProcedure.setTitle(sepa[0]);
			            this.newProcedure.setExternal_number(sepa[1]);
			            this.newProcedure.setInternal_number(sepa[2]);
			            this.newProcedure.setRevision_number(sepa[3]);
		            }
		            Main.procedureDataSet = null;
		            Main.procedureDataSet = FXCollections.observableArrayList();
					root = new TreeItem<>(this.newProcedure.getTitle());//treeview root
					Main.newProcedureTitleFull = this.newProcedure.getTitle();
					selectionTreeView.setRoot(root);//create root
					selectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
					root.setExpanded(true);
					menu = new ContextmenuClass(selectionTreeView, root, middle_pane_scroll);
					selectionTreeView.setContextMenu(menu.getMenu());
					
					chkSave.setChkSave(false);
					isProcedureCreated = true;
					System.out.println(import_results.size());
		    		for (int i = 1; i < import_results.size(); i++) {
		    			sepa = import_results.get(i).split(",");
		    			
		    			Integer inter = new Integer(sepa[1]);
		    			
						DragIconClass wpDrag = new DragIconClass(sepa[0],inter,new TreeItem<String>(sepa[0]));
		    			for (int j = 2; j < sepa.length; j++) {
		    				wpDrag.setLeafData(new TaskModel(sepa[j]));
		    			}
		    			Main.procedureDataSet.add(wpDrag);
		    		}
					for (DragIconClass leaf : sortedlist()) {
						root.getChildren().add(leaf.getLeaf());
					 }
					pane.createObjects(sortedlist());
					try {
						rev_numb.setText("Rev : " + this.newProcedure.getRevision_number());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					{///Importing Shared Docs
						Main.wordParamData.clear();
						FileReader import_r1 = new FileReader(file1);
						BufferedReader import_1 = new BufferedReader(import_r1);
						String line1;
						String[] sepa1 = null;
						import_share_results = new ArrayList<String>();
						while ((line1 = import_1.readLine()) != null){
							import_share_results.add(line1);
						}
					    import_1.close();
					    import_.close();
					    {//creating imported procedure instance
					    	
							System.out.println(import_share_results.size()+"dsd");
							for (int i = 0; i < import_share_results.size(); i++) {
								System.out.println("dsd");
								sepa1 = import_share_results.get(i).split(",,,");
								System.out.println(sepa1[0] + "dsd"+sepa1[1] );
								ShareModel sharedWordparam = new ShareModel();
								sharedWordparam.setParameter(sepa1[0]);
								sharedWordparam.setText(sepa1[1]);
								Main.addWordParamData(sharedWordparam);
								DisplaySharedTable();
							}
					    }
					    status.setText("Project Import");
					}
	         } catch (Exception e) {
	        	e.printStackTrace();
	        	import_r.close();
	         }
		}
	}
	
	@FXML
	public void initialize(){


		pane = new DragNode2();
		middle_pane_scroll.setContent(pane.getPan());
		middle_pane_scroll.setFitToWidth(true);
		word = new WordClass();//create some word documents
		technip_pane.setStyle("-fx-background-color: white;");
		//middle_pane.setStyle("-fx-background-color: #D3D3D333,linear-gradient(from 0.5px 0px to 10.5px 0px, repeat, #C1CDCD 5%, transparent 5%),linear-gradient(from 0px 0.5px to 0px 10.5px, repeat, #C1CDCD 5%, transparent 5%);");
		uppersubleft_titlepane.setCollapsible(false);///SETS THE TITLEPANE TO NON COLLAPSIBLE
		task.setCellValueFactory(cellData -> cellData.getValue().TaskNameProperty());
		
		param_wp.setCellValueFactory(cellData -> cellData.getValue().getParameterProperty());
		text_wp.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());
		
		selectionTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> optionName = DisplayTable(newValue.getValue()));				
		selectionTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
		       @Override
		       public void handle(MouseEvent mouseEvent)
		       {            
		           if(mouseEvent.getClickCount() == 2)
		           {
		               TreeItem<String> item = selectionTreeView.getSelectionModel().getSelectedItem();
		               //System.out.println("Selected Text : " + item.getValue());
		               show_proWord(item.getValue().toString());
		           }
		       }
		});
		taskTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		            //System.out.println(taskTable.getSelectionModel().getSelectedItem().getTaskName());  
		            //System.out.println(Fold_Class.fullPath + "\\" + optionName + "\\" + taskTable.getSelectionModel().getSelectedItem().getTaskName() + "docx");
		            String tempPath = Fold_Class.fullPath + "\\" + optionName;
		            if(optionName.startsWith("W"))
		            	word.openword(tempPath, taskTable.getSelectionModel().getSelectedItem().getTaskName());  
		            else
		            	word.openword(Fold_Class.fullPath, taskTable.getSelectionModel().getSelectedItem().getTaskName());
		        }
		    }
		});
		
		try {
			displayconfig();//displaying table view for recently saved
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	private boolean loopList(ObservableList<DragIconClass> list, String name){
		for(DragIconClass temp : list){
			if(name.equalsIgnoreCase(temp.getTitle())){
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setContentText(name +" already exist");		        
		        alert.showAndWait();
				return false;
			}
		}
		return true;
	}

	public void open_btn() throws IOException{
		  Stage stage = (Stage) center_pane.getScene().getWindow();
		try {    		
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Open");
			File file = chooser.showDialog(stage);
			Fold_Class.fullPath = file.getAbsolutePath().toString();
		} catch (NullPointerException e) {
	          e.printStackTrace();
	      }catch (Exception e) {
			e.printStackTrace();
		}
		{
			File file = new File(Fold_Class.fullPath + "\\tig" + ".txt");
			File file1 = new File(Fold_Class.fullPath +"\\" + "wpTig.txt");
			FileReader import_r = null;
			New_procedureModel newPorcedure = new New_procedureModel();
			this.newProcedure = newPorcedure; // Passing newProcedure instance to make it global.. 
	        try {
		        	import_r = new FileReader(file);
		        	BufferedReader import_ = new BufferedReader(import_r);
		        	String line;
		        	String[] sepa = null;
		        	import_results = new ArrayList<String>();
		        	while ((line = import_.readLine()) != null){
		        		import_results.add(line);
		        	}
		            import_.close();
		            import_r.close();
		            {//creating imported procedure instance
		            sepa = import_results.get(0).split(",");
			            this.newProcedure.setTitle(sepa[0]);
			            this.newProcedure.setExternal_number(sepa[1]);
			            this.newProcedure.setInternal_number(sepa[2]);
			            this.newProcedure.setRevision_number(sepa[3]);
		            }
		            Main.procedureDataSet = null;
		            Main.procedureDataSet = FXCollections.observableArrayList();
					root = new TreeItem<>(this.newProcedure.getTitle());//treeview root
					Main.newProcedureTitleFull = this.newProcedure.getTitle();
					selectionTreeView.setRoot(root);//create root
					selectionTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
					root.setExpanded(true);
					menu = new ContextmenuClass(selectionTreeView, root, middle_pane_scroll);
					selectionTreeView.setContextMenu(menu.getMenu());
					
					isProcedureCreated = true;
					System.out.println(import_results.size());
		    		for (int i = 1; i < import_results.size(); i++) {
		    			sepa = import_results.get(i).split(",");
		    			
		    			Integer inter = new Integer(sepa[1]);
		    			
						DragIconClass wpDrag = new DragIconClass(sepa[0],inter,new TreeItem<String>(sepa[0]));
		    			for (int j = 2; j < sepa.length; j++) {
		    				wpDrag.setLeafData(new TaskModel(sepa[j]));
		    			}
		    			Main.procedureDataSet.add(wpDrag);
		    		}
					for (DragIconClass leaf : sortedlist()) {
						root.getChildren().add(leaf.getLeaf());
					 }
					pane.createObjects(sortedlist());
					try {
						rev_numb.setText("Rev : " + this.newProcedure.getRevision_number());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					{///Importing Shared Docs
						Main.wordParamData.clear();
						FileReader import_r1 = new FileReader(file1);
						BufferedReader import_1 = new BufferedReader(import_r1);
						String line1;
						String[] sepa1 = null;
						import_share_results = new ArrayList<String>();
						while ((line1 = import_1.readLine()) != null){
							import_share_results.add(line1);
						}
					    import_1.close();
					    import_.close();
					    {//creating imported procedure instance
					    	
							System.out.println(import_share_results.size()+"dsd");
							for (int i = 0; i < import_share_results.size(); i++) {
								System.out.println("dsd");
								sepa1 = import_share_results.get(i).split(",,,");
								System.out.println(sepa1[0] + "dsd"+sepa1[1] );
								ShareModel sharedWordparam = new ShareModel();
								sharedWordparam.setParameter(sepa1[0]);
								sharedWordparam.setText(sepa1[1]);
								Main.addWordParamData(sharedWordparam);
								DisplaySharedTable();
							}
					    }
					}
	
	         } catch (FileNotFoundException e) {
	        	System.out.println("kldn");
	        	import_r.close();
	         }catch (Exception e) {
		        	e.printStackTrace();
		        	import_r.close();
		         }
		}
	}
	
	private void openURL(String url) throws Exception{

        java.awt.Desktop.getDesktop().browse(new URI(url));

   }
		
	public void quit() throws IOException{
    	if(chkSave.isChkSave()){
    		System.exit(0);
    	}else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Would You Like To Save Your Project?");
            alert.setContentText("Please choose an option.");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == yesButton)
            {
            	try {
					save();
					System.exit(0);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					System.exit(0);
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(0);
				}
            }
            else if(result.get() == noButton)
            {
            	try {
					System.exit(0);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					System.exit(0);
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(0);
				}
            }
            else if(result.get() == cancelButton)
            {
            	
            }
    	}
		 
	}
	
	public void save() throws IOException{
		progressBar.setVisible(true);
		progressBar.setProgress(0);

		File file = new File(Fold_Class.fullPath +"\\" + "tig.txt");
		File file1 = new File(Fold_Class.fullPath +"\\" + "wpTig.txt");
		if(!file.exists())
		{
			config();
		}
		PrintWriter save = null;
	  try {
	      save = new PrintWriter(file1);
	      BufferedWriter bsave = new BufferedWriter(save);
	      
	      for(ShareModel share : Main.wordParamData){
		      bsave.write(share.getParameter() + ",,," + share.getText());
		      bsave.newLine();
	      }     
	      bsave.close();
	      save.close();
	  }
	  catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (Exception e) {
		  e.printStackTrace();
	      save.close();
	  } finally {
          if ( save != null ) {
              save.close();
          }
      }
	  
	  progressBar.setProgress(0.5F);
	  
	  try {
	      save = new PrintWriter(file);
	      BufferedWriter bsave = new BufferedWriter(save);
	      bsave.write(newProcedure.getTitle() + "," + newProcedure.getExternal_number() + "," + newProcedure.getInternal_number() + "," + newProcedure.getRevision_number());
	      bsave.newLine();
	      
	      for(DragIconClass leaf : sortedlist()){
		      bsave.write(leaf.getTitle() + "," + leaf.getSorting() + ",");
		      for(TaskModel ref : leaf.getLeafData())
		    	  bsave.write(ref.getTaskName()+ ",");
		      bsave.newLine();
		      {
		          for (int i = 0; i < 6; i++) {
		        	  //final ProgressBar pb = pbs[i] = new ProgressBar();
		              progressBar.setProgress(i);
		          }
		      }
	      }     
	      bsave.close();
	      save.close();
	      chkSave.setChkSave(true);
			{//Please NOTE: this is not part of this code bits!!!just checks for if saved
				if(chkSave.isChkSave() == true){
					status.setText("Saved");
				}else{
					status.setText("Not Saved");
				}
			}
			progressBar.setProgress(1.0F);
			progressBar.setVisible(false);
	  }
	  catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (Exception e) {
		  e.printStackTrace();
	      save.close();
	  } finally {
          if ( save != null ) {
              save.close();
          }
      }
      
	}
	
	public void setRoot_parent(Root_parent Top) {
        this.Top = Top;
    }
	
	public void show_proWord(String fileName){
		word.openword(Fold_Class.fullPath, fileName);
		chkSave.setChkSave(false);
    }
		
	private ObservableList<DragIconClass> sortedlist(){//bubble sort algorithm
    	for(int i = 0;i < Main.procedureDataSet.size(); ++i){
    		for(int j = 0;j < (Main.procedureDataSet.size()-1); ++j){
    			if(Main.procedureDataSet.get(j).getSorting() > Main.procedureDataSet.get(j+1).getSorting()){
    				DragIconClass temp = Main.procedureDataSet.get(j);
    				Main.procedureDataSet.set(j ,Main.procedureDataSet.get(j+1));
    				Main.procedureDataSet.set(j+1 ,temp);
    			}
    		}
    		i++;
    	}
    	return Main.procedureDataSet;

    }
	
	public void viewCompile()throws Exception{
		word = new WordClass();//create some word documents
		word.openword(Fold_Class.fullPath, this.newProcedure.getTitle());
	}

}


