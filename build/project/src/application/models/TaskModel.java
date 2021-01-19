package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskModel{
	private StringProperty taskName;

	public TaskModel(String taskName){
		this.taskName = new SimpleStringProperty(taskName);
	}
	/**
	 * @param taskName to get
	 */
	public StringProperty TaskNameProperty() {
		return taskName;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName.get();
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName.set(taskName);
	}
	
}
