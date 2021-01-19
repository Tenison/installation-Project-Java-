package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ShareModel {
	private StringProperty parameter;
	private StringProperty text;
	
	public ShareModel(){
		parameter = new SimpleStringProperty("-");
		text = new SimpleStringProperty("-");
	}

	/**
	 * @return the parameter
	 */
	public StringProperty getParameterProperty() {
		return parameter;
	}

	/**
	 * @return the text
	 */
	public StringProperty getTextProperty() {
		return text;
	}
	
	public String getParameter() {
		return parameter.get();
	}

	public String getText() {
		return text.get();
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter.set(parameter);
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text.set(text);
	}
	
	
}
