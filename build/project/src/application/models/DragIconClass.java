package application.models;
/**
 * Class for creating drag icons.
 * also used to create array instances for data set forn drag icons..
 * shapes and their colours. 
 * */

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class DragIconClass extends Shape{
	@FXML AnchorPane middle_pane;
	Shape shape;
	StackPane Stackshape;
	private String title;
	private Integer sorting;
	private TreeItem<String> leaf;
	private ObservableList<TaskModel> leafData;
	
	
	public DragIconClass(String title, int sorting,TreeItem<String> leaf){
		this.title = title;
		this.sorting = sorting;
		this.leaf = leaf;
		leafData = FXCollections.observableArrayList();
		createDragIconClass();
	}
	
    /**
     * Returns the data as an observable list of Task Models. 
     * @return
     */
	public ObservableList<TaskModel> getLeafData() {
		return leafData;
	}

	/**
	 * @param leafData the leafData to set
	 */
	public void setLeafData(TaskModel leafData) {
		this.leafData.add(leafData);
	}

	@Override
    public String toString() {
        return this.title;
    }
	
    public Integer getSorting() {
		return sorting;
	}
    
	public TreeItem<String> getLeaf() {
		return leaf;
	}

	public Shape getShape() {
		return shape;
	}
	
	public StackPane getStackshape() {
		return Stackshape;
	}
	
	public String getTitle() {
		return title;
	}

	private void createDragIconClass(){
		Stackshape = new StackPane();
		Text text = new Text(this.title);

		this.shape = createRect();
		shape.setStroke(Color.OLDLACE);
		
		Stackshape.getChildren().addAll(shape, text);
		Stackshape.layoutXProperty().set(100);
		
		//MouseControlUtil.makeDraggable(Stackshape);		
	}
	
	private Rectangle createRect(){
		Random rand = new Random();
		Rectangle rect = new Rectangle();
		rect.xProperty().set(Math.random()*50+((rand.nextInt(5))));
		rect.yProperty().set(Math.random()*95+((rand.nextInt(100))));
		rect.widthProperty().setValue(150);
		rect.heightProperty().setValue(50);
		rect.arcWidthProperty().setValue(20);
		rect.arcHeightProperty().setValue(10);
		rect.fillProperty().setValue(Color.LAVENDER);
		return rect;
	}

	@SuppressWarnings("restriction")
	@Override
	public com.sun.javafx.geom.Shape impl_configShape() {
		// TODO Auto-generated method stub
		return null;
	}

}
