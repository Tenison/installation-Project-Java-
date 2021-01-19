package application.models;

import java.io.File;
import java.nio.file.FileSystems;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ContextmenuClass {
	protected ContextMenu menu;
	private MenuItem del;
	
	public ContextmenuClass(TreeView<String> selectionTreeView, TreeItem<String> treeRoot, ScrollPane middle_pane_scroll){
		this.menu = new ContextMenu();
		
		this.del = new MenuItem("Delete");
		this.menu.getItems().add(this.del);
		del.disableProperty().bind(selectionTreeView.getSelectionModel().selectedItemProperty().isNull()
                .or(selectionTreeView.getSelectionModel().selectedItemProperty().isEqualTo(treeRoot)));
		
		this.del.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
			public void handle(ActionEvent e) {
		    	
		        //System.out.println(Main.procedureDataSet.remove(selectionTreeView.getSelectionModel().getSelectedIndex() - 1));
		        DragIconClass tempName = Main.procedureDataSet.remove(selectionTreeView.getSelectionModel().getSelectedIndex() - 1);
		        //System.out.println(tempName.getTitle());
		        TreeItem<String> selected = selectionTreeView.getSelectionModel().getSelectedItem();
	            selected.getParent().getChildren().remove(selected);
	            if(tempName.getSorting() == 2){
	            	Main.deleteFile(FileSystems.getDefault().getPath(Fold_Class.fullPath + "\\" + tempName.getTitle(),"Steps.docx"));
	            	Main.deleteFile(FileSystems.getDefault().getPath(Fold_Class.fullPath + "\\" + tempName.getTitle(),"Introduction_.docx"));
	            	Main.deleteFile(FileSystems.getDefault().getPath(Fold_Class.fullPath + "\\" + tempName.getTitle(),"Conclusion.docx"));
	            	Main.deleteDir(new File(Fold_Class.fullPath + "\\" + tempName.getTitle()));
	            }
	            else
	            	Main.deleteFile(FileSystems.getDefault().getPath(Fold_Class.fullPath, tempName.getTitle() + ".docx"));
	        	DragNode2 pane1 = new DragNode2();
	        	pane1.createObjects(Main.procedureDataSet);
	        	middle_pane_scroll.setContent(pane1.getPan());
	            
		    }
		});
	}
	
	/**
	 * @return the menu
	 */
	public ContextMenu getMenu() {
		return menu;
	}
}
