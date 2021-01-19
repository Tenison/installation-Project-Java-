package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class New_workplanModel {
	private StringProperty wpnumber;
	private StringProperty fullwp_name;

	public New_workplanModel(){
		wpnumber = new SimpleStringProperty();
		fullwp_name = new SimpleStringProperty();
	}

	public StringProperty Fullwp_nameProperty() {
		return fullwp_name;
	}
	
	public String getFullwp_name(){
		return fullwp_name.get();
	}

	public String getWpnumber() {
		return wpnumber.get();
	}
	
	public void setFullwp_name(String fullwp_name) {
		this.fullwp_name.set(fullwp_name);
	}

	public void setWpnumber(String wpnumber) {
		this.wpnumber.set(wpnumber);
	}
	public StringProperty WpnumberProperty() {
		return wpnumber;
	}
}
