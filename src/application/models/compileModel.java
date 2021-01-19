package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class compileModel {
	private StringProperty fullpath;

	public compileModel(){
		    this.fullpath = new SimpleStringProperty();
	}
	/**
	 * @return the fullpath
	 */
	public String getFullpath() {
		return fullpath.get();
	}

	/**
	 * @param fullpath the fullpath to set
	 */
	public void setFullpath(String fullpath) {
		this.fullpath.set(fullpath);
	}
}
