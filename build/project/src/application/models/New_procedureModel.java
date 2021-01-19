package application.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class New_procedureModel {

	

	private StringProperty title;
    private StringProperty external_number;
    private StringProperty internal_number;
    private StringProperty revision_number;
    private BooleanProperty chk_one;
    private BooleanProperty chk_two;
    private BooleanProperty chk_three;
    private StringProperty directoryPath;
    
    
    public String getDirectory_Path() {
		return directoryPath.get();
	}

	public void setDirectory_Path(String directoryPath) {
		this.directoryPath.set(directoryPath);
	}
	
	public StringProperty directory_PathProperty(){
		return directoryPath;
	}

	public New_procedureModel() {
	    this.title = new SimpleStringProperty();
	    this.external_number = new SimpleStringProperty();
	    this.internal_number = new SimpleStringProperty();
	    this.revision_number = new SimpleStringProperty();
	    this.chk_one = new SimpleBooleanProperty();
	    this.chk_two = new SimpleBooleanProperty();
	    this.chk_three = new SimpleBooleanProperty();
	    this.directoryPath = new SimpleStringProperty();
    }
    
    //Default constructor.
     
	//public New_procedureModel() {
    //}

    
    //Constructor with some initial data.
     
    /*public New_procedureModel(String title, String external_number, String internal_number, int revision_number) {
        this.title = new SimpleStringProperty(title);
        this.external_number = new SimpleStringProperty(external_number);
        this.internal_number = new SimpleStringProperty(internal_number);
        this.revision_number = new SimpleIntegerProperty(revision_number);
    }*/
    
    //get methods
	public String getTitle() {
		return title.get();
	}
	public String getExternal_number() {
		return external_number.get();
	}
	public String getInternal_number() {
		return internal_number.get();
	}
	public String getRevision_number() {
		return revision_number.get();
	}
	
	///property methods
	public StringProperty TitleProperty() {
		return title;
	}
	public StringProperty External_numberProperty() {
		return external_number;
	}
	public StringProperty Internal_numberProperty() {
		return internal_number;
	}
	public StringProperty Revision_numberProperty() {
		return revision_number;
	}
	
	//setmethods;
	public void setTitle(String title) {
		//this.title = (title);
		this.title.set(title);
	}
	public void setExternal_number(String external_number) {
		//this.external_number = (external_number);
		this.external_number.set(external_number);
		
	}
	public void setInternal_number(String internal_number) {
		//this.internal_number = (internal_number);
		this.internal_number.set(internal_number);
	}
	public void setRevision_number(String revision_number) {
		//this.revision_number = (revision_number);
		this.revision_number.set(revision_number);
	}
    //boolean property

	public Boolean getChk_one() {
		return chk_one.get();
	}

	public Boolean getChk_two() {
		return chk_two.get();
	}

	public Boolean getChk_three() {
		return chk_three.get();
	}

	public void setChk_one(Boolean chk_one) {
		this.chk_one.set(chk_one);
	}

	public void setChk_two(Boolean chk_two) {
		this.chk_two.set(chk_two);
	}

	public void setChk_three(Boolean chk_three) {
		this.chk_three.set(chk_three);
	}
	
}
