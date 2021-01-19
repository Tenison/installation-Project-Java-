package application.models;



import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
 
/** Example of dragging anchors around to manipulate a line. */
public class DragNode2{
	
	Pane pan = new Pane();
	Anchor end = null;
	Anchor start = null;
	Line line;
    DoubleProperty startX;
    DoubleProperty startY;
    DoubleProperty endX ;
    DoubleProperty endY ;
	/**
	 * @return the pan
	 */
	public Pane getPan() {
		return pan;
	}
	
	public void createObjects(ObservableList<DragIconClass> core){
		int size = core.size();
		System.out.print(size);
		pan.getChildren().clear();
		if(size == 1){
			start = new Anchor(core.get(0).getTitle(),Color.LAVENDER,startX, startY);
			pan.getChildren().add(start);
		}	
		else{
			pan.getChildren().clear();
			start = new Anchor(core.get(0).getTitle(),Color.LAVENDER,startX, startY);
			end = new Anchor(core.get(1).getTitle(), Color.LAVENDER, endX, endY);
			line = new BoundLine(new SimpleDoubleProperty(startX.doubleValue()+70 ), new SimpleDoubleProperty(startY.doubleValue() + 30), new SimpleDoubleProperty(endX.doubleValue() + 70), new SimpleDoubleProperty(endY.doubleValue() + 30));
			pan.getChildren().addAll(start,line,end);
		}
		
	    int i = 2;
	    
	    while(i < size){
	    	startX = new SimpleDoubleProperty(endX.doubleValue());
	    	startY = new SimpleDoubleProperty(endY.doubleValue());
	    	endY = new SimpleDoubleProperty(endY.doubleValue() + 100);
	    	
	    	//end = createDraggingRect(endX, endY, 150, 50);
	    	end = new Anchor(core.get(i).getTitle(), Color.LAVENDER, endX, endY);
	    	
	        //line = new BoundLine(startX, startY, endX, endY);
	    	line = new BoundLine(new SimpleDoubleProperty(startX.doubleValue()+70 ), new SimpleDoubleProperty(startY.doubleValue() + 30), new SimpleDoubleProperty(endX.doubleValue() + 70), new SimpleDoubleProperty(endY.doubleValue() + 30));
	        pan.getChildren().addAll(line,end);
	        ++i;	        
	    }
	}

	public DragNode2(){
		pan.getChildren().clear();
		startX = new SimpleDoubleProperty(280);//change
	    startY = new SimpleDoubleProperty(50);
	    endX   = new SimpleDoubleProperty(280);//change
	    endY   = new SimpleDoubleProperty(150);
	    //Rectangle start = createDraggingRect(startX, startY, 150, 50);
	    //Rectangle end = createDraggingRect(endX, endY, 150, 50);
		pan.setStyle("-fx-background-color: #D3D3D333,linear-gradient(from 0.5px 0px to 10.5px 0px, repeat, #C1CDCD 5%, transparent 5%),linear-gradient(from 0px 0.5px to 0px 10.5px, repeat, #C1CDCD 5%, transparent 5%);");
	    pan.setMinHeight(10000);

	}
  
 
  class BoundLine extends Line {
    BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
      startXProperty().bind(startX);
      startYProperty().bind(startY);
      endXProperty().bind(endX);
      endYProperty().bind(endY);
      setStrokeWidth(2);
      setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
      setStrokeLineCap(StrokeLineCap.BUTT);
      getStrokeDashArray().setAll(10.0, 5.0);
      setMouseTransparent(true);
    }
  }
 
  // a draggable anchor displayed around a point.
  class Anchor extends StackPane { 
    Anchor(String text_x, Color color, DoubleProperty x, DoubleProperty y) {
    	super.layoutXProperty().set(x.doubleValue());
    	super.layoutYProperty().set(y.doubleValue());
      Ellipse shape = new Ellipse(x.get(), y.get(), 70, 30);     
      shape.setFill(color.deriveColor(1, 1, 1, 0.5));
      shape.setStroke(color);
      shape.setStrokeWidth(2);
      shape.setStrokeType(StrokeType.OUTSIDE);
      Text text = new Text(text_x);
			
      super.getChildren().addAll(shape, text);

  
      //x.bind(shape.centerXProperty());
      //x.bind(super.layoutXProperty());
      //y.bind(shape.centerYProperty());
      //y.bind(super.layoutYProperty());
    }

 
    // records relative x and y co-ordinates.
    @SuppressWarnings("unused")
	private class Delta { double x, y; }
  }  
}