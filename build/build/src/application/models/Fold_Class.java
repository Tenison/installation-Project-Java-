package application.models;

import java.io.File;


public class Fold_Class extends WordClass{
	public final String userHome = System.getProperty("user.home");
	public static String fullPath = null;//separate saving path

	
	public String createfolder(String path,String chkDirPathIfNull){
		String outputFolder = userHome + "\\" + path;
		if(chkDirPathIfNull == null){
			try {
			    File folder = new File(outputFolder);   
			    if(!folder.exists()){
			    	folder.mkdir();
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			}
			return outputFolder; //returns the folder path, so item can be created inside
		}else{
			try {
			    File folder = new File(path);   
			    if(!folder.exists()){
			    	folder.mkdir();
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			}
			return path; //returns the folder path, so item can be created inside
		}
		
	}
	
	public void createfolderWithFullpath(String path){	
		try {
		    File folder = new File(path);
		    
		    if(!folder.exists()){
		    	folder.mkdir();
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	//note : create template folder
	public void createTemplatefolder(){
		try {
		    File folder = new File(userHome + "\\Documents\\TechnipTemplate");
		    if(!folder.exists()){
		    	folder.mkdir();	
		    }
		    createtemplateword("PIT");
		    createtemplateword("stepTemplate");
		    createtemplateword("WPT");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}
}

