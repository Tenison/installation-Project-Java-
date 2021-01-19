package application.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

public class WordClass{
	public final String userHome = System.getProperty("user.home");
	public final String INTRO = "Introduction";
	public final String CONCLUSION = "Conclusion";
	private XWPFDocument src1Document;
	private XWPFDocument src1aDocument;
	private XWPFDocument src2Document;
	private XWPFWordExtractor extr;
	
	public void createword(String Filename, String fullPath, String WP_name){
		try {
			String tempPath = fullPath + "\\" + Filename + ".docx";
			FileOutputStream outstream = new FileOutputStream(tempPath);
	
			XWPFDocument doc;
			if(Filename.equalsIgnoreCase(INTRO +"_"))
				doc = new XWPFDocument(OPCPackage.open(userHome + "\\Documents\\TechnipTemplate\\WPT.docx"));
			else if(Filename.equalsIgnoreCase(INTRO))
				//doc = new XWPFDocument();
				doc = new XWPFDocument(OPCPackage.open(userHome + "\\Documents\\TechnipTemplate\\PIT.docx"));
			else if(Filename.equalsIgnoreCase("Steps"))
				doc = new XWPFDocument(OPCPackage.open(userHome + "\\Documents\\TechnipTemplate\\stepTemplate.docx"));
			else{
				doc = new XWPFDocument();
			}
			doc.write(outstream);
			outstream.close();
			doc.close();
			
			String TempVar = WP_name + ": DESCRIPTION";
			if(Filename.equalsIgnoreCase(INTRO +"_"))
				replaceWPName(tempPath, TempVar, "WP-199400");
			if(Filename.equalsIgnoreCase("Steps"))
				replaceWPName(tempPath, WP_name, "WP-199401");

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	public void openword(String PathCon,String Filename){
		try {
			System.out.println(PathCon+ "\\" + Filename +".docx");
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " +PathCon+ "\\" + Filename +".docx");//super.fullpath
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		//Note: Create Template word document
		protected void createtemplateword(String Filename){
			try {
				String tempPath = userHome + "\\Documents\\TechnipTemplate" + "\\" + Filename + ".docx";
				
				File f = new File(tempPath);
				if(!f.exists()){
					FileOutputStream outstream = new FileOutputStream(tempPath);
					XWPFDocument doc = new XWPFDocument(OPCPackage.open("templates\\" + Filename + ".docx"));
					doc.write(outstream);
					outstream.close();
					doc.close();
				}		
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    public void wpmerge(FileInputStream src1,FileInputStream src1a, FileInputStream src2, FileOutputStream dest) throws Exception {
	        OPCPackage src1Package = OPCPackage.open(src1);
	        OPCPackage src1aPackage = OPCPackage.open(src1a);
	        OPCPackage src2Package = OPCPackage.open(src2);
	        
	        src1Document = new XWPFDocument(src1Package);        
	        CTBody src1Body = src1Document.getDocument().getBody();
	        
	        src1aDocument = new XWPFDocument(src1aPackage);        
	        CTBody src1aBody = src1aDocument.getDocument().getBody();
	        
	        src2Document = new XWPFDocument(src2Package);
	        CTBody src2Body = src2Document.getDocument().getBody(); 
	        
	        if(!chkifwordempty(src2Document))
	        appendBody(src1Body, src2Body);
	        if(!chkifwordempty(src1aDocument))
	        appendBody(src1Body, src1aBody);
	        
	        src1Document.write(dest);
	        
	        src1a.close();
	        src2.close();
	        src1.close();
	        dest.close();
	        
	        src1Document.close();
	        src1aDocument.close();
	        src2Document.close();
	    }

	    public void appendBody(CTBody src, CTBody append) throws Exception {
	        XmlOptions optionsOuter = new XmlOptions();
	        optionsOuter.setSaveOuter();
	        String appendString = append.xmlText(optionsOuter);
	        String srcString = src.xmlText();
	        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
	        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
	        String sufix = srcString.substring( srcString.lastIndexOf("<") );
	        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
	        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
	        src.set(makeBody);
	    }

	    public void merge() {
	        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }
	    
	    public boolean chkifwordempty(XWPFDocument doc){
	        extr = new XWPFWordExtractor(doc);
	        
	        if (extr.getText().trim().isEmpty()) {
	            return true;
	            
	        } else {
	        	return false;
	        }
	    }
	    
	    private void replaceWPName(String filePath, String WP_name, String container) throws IOException{
	    	 String fileName = filePath;
	         XWPFDocument document = null;
			try {
				InputStream fis = new FileInputStream(fileName);
				 document = new XWPFDocument(fis);
				 fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         List<XWPFParagraph> paragraphs = document.getParagraphs();
	          
	         for (int x=0; x<paragraphs.size();x++)
	         {
	             @SuppressWarnings("unused")
				XWPFParagraph paragraph = paragraphs.get(x);
	         }
	         List<XWPFTable> tables = document.getTables();
	         for (int x=0; x<tables.size();x++)
	         {
	             XWPFTable table = tables.get(x);
	             List<XWPFTableRow> tableRows = table.getRows();
	             for (int r=0; r<tableRows.size();r++)
	             {
	                 XWPFTableRow tableRow = tableRows.get(r);
	                 List<XWPFTableCell> tableCells = tableRow.getTableCells();
	                 for (int c=0; c<tableCells.size();c++)
	                 {
	                     XWPFTableCell tableCell = tableCells.get(c);
	                     String tableCellVal = tableCell.getText();
	                     
	                     for(int n=0; n<=(c+1); n++){
	                         if ((c+1)==1){
	                              
	                             if (tableCellVal!=null){
	                                 if (tableCellVal.length()>0){
	                                     
	                                     if (tableCell.getText().contains(container)){
	                                                                     
	                                         String txt = tableCell.getText();
	                                         txt = txt.replace(tableCellVal, WP_name);
	                                         tableCell.removeParagraph(0);
	                                         XWPFParagraph para = tableCell.addParagraph();
	                                         para.setAlignment(ParagraphAlignment.CENTER);
	                                         XWPFRun run = para.createRun();
	                                         run.setBold(true);
	                                         run.setFontFamily("Verdana");
	                                         run.setFontSize(9);
	                                         run.setText(txt);
	                                         
	                                        
	                                        
	                                    
	                                     System.out.println("yes");
	                                     
	                                     
	                                     
	                                 }
	                                     
	                                 }
	                             }
	                         }
	                        // System.out.println("tableCell.getText(" + (c) + "):" + tableCellVal);
	                     }
	                 }
	             }
	         }
	         OutputStream out = new FileOutputStream(filePath);
	         document.write(out);
	         out.close();
	    }
	    
	    public void replaceTexts(String filePath, String params, String results) throws IOException{
	    	String fileName = filePath;
	        InputStream fis = new FileInputStream(fileName);
	        XWPFDocument document = new XWPFDocument(fis);
	        List<XWPFParagraph> paragraphs = document.getParagraphs();
	         
	        for (int x=0; x<paragraphs.size();x++)
	        {
	            XWPFParagraph paragraph = paragraphs.get(x);
	            System.out.println(paragraph.getParagraphText());
	        }
	        List<XWPFTable> tables = document.getTables();
	        for (int x=0; x<tables.size();x++)
	        {
	            XWPFTable table = tables.get(x);
	            List<XWPFTableRow> tableRows = table.getRows();
	            for (int r=0; r<tableRows.size();r++)
	            {
	            //    System.out.println("Row "+ (r+1)+ ":");
	                XWPFTableRow tableRow = tableRows.get(r);
	                List<XWPFTableCell> tableCells = tableRow.getTableCells();
	                for (int c=0; c < tableCells.size();c++)
	                {
	                  // System.out.print("Column "+ (c+1)+ ": ");
	                    
	                    XWPFTableCell tableCell = tableCells.get(c);
	                    String tableCellVal = tableCell.getText();
	         
	                    for(int n=0;n<=(c+1); n++){
	                    if ((c+1)==n){
	                         
	                        if (tableCellVal!=null){
	                            if (tableCellVal.length()>0){
	                           
	                                if (tableCell.getText().contains(params)){
	                                  String txt = tableCell.getText();
	                                    //tableCell.removeParagraph(0);
	                                     XWPFParagraph para = tableCell.addParagraph();
	                                     tableCell.removeParagraph(0);
	                                     int nofrun = para.getRuns().size();
	                                     for(int h=0;nofrun > 0; h++){
	                                            para.removeRun(h);
	                                     }
	                                     
	                                    XWPFRun run = para.createRun();
	                                    txt = txt.replace(params, results);
	                                    run.setFontFamily("Verdana");
	                                    run.setFontSize(9);
	                                    run.setBold(true);
	                                    run.setText(txt);
	                                     //tableCell.setText(txt);
	                                     System.out.println("yes");
	                                
	                                
	                                
	                            }else{
	                                //tableCell.setText("NULL");
	                            }
	                        }
	                    }
	                  //  System.out.println("tableCell.getText(" + (c) + "):" + tableCellVal);
	              //  }
	            }
	           // System.out.println("\n");
	         }
	                
	        }}}
	       	OutputStream out = new FileOutputStream(filePath);
	       	document.write(out);
	       	out.close();
	        document.close();
	      
	    }
	    
	    /*public void replaceWPName2(String filePath) throws IOException{
	    	 String fileName = filePath;
	         XWPFDocument document = null;
			try {
				InputStream fis = new FileInputStream(fileName);
				 document = new XWPFDocument(fis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         List<XWPFParagraph> paragraphs = document.getParagraphs();
	          
	         for (int x=0; x<paragraphs.size();x++)
	         {
	             @SuppressWarnings("unused")
				XWPFParagraph paragraph = paragraphs.get(x);
	         }
	         List<XWPFTable> tables = document.getTables();
	         for (int x=0; x<tables.size();x++)
	         {
	             XWPFTable table = tables.get(x);
	             List<XWPFTableRow> tableRows = table.getRows();
	             for (int r=0; r<tableRows.size();r++)
	             {
	                 XWPFTableRow tableRow = tableRows.get(r);
	                 List<XWPFTableCell> tableCells = tableRow.getTableCells();
	                 for (int c=0; c<tableCells.size();c++)
	                 {
	                     XWPFTableCell tableCell = tableCells.get(c);
	                     String tableCellVal = tableCell.getText();
	                     
	                     for(int n=0; n<=(c+1); n++){
	                         if ((c+1)==1){
	                              
	                             if (tableCellVal!=null){
	                                 if (tableCellVal.length()>0){
	                                      //char c1 = tableCellVal.charAt(0);
	                                      //String s2 = "-TEST";
	                                      //char c2 = s2.charAt(0);
	                                      //String test = tableCell.getText().replace(tableCellVal,s2);
	                                      //tableCell.setText(test);//SHOULD BE REPLACE VALUES
	                                     if (tableCell.getText().contains("WORK PLAN")){///input container
	                                         
	                                     String txt = tableCell.getText();
	                                       //tableCell.removeParagraph(0);
	                                      
	                                        txt = txt.replace(tableCellVal, ""+2);////output container
	                                      //tableCell.addParagraph().createRun().setBold(true);
	                                        //XWPFParagraph para = tableCell.addParagraph();
	                                         //para.setAlignment(ParagraphAlignment.CENTER);
	                                         //XWPFRun run = para.createRun();
	                                         //run.setBold(true);
	                                         //run.setFontFamily("Verdana");
	                                         //run.setFontSize(9);
	                                         //run.setText(txt);
	                                         
	                                        
	                                         tableCell.setText(txt);
	                                    
	                                     System.out.println("yes");
	                                 }
	                                     
	                            }
	                        // System.out.println("tableCell.getText(" + (c) + "):" + tableCellVal);
	                     }
	                         }
	                        // System.out.println("tableCell.getText(" + (c) + "):" + tableCellVal);
	                     }
	                 }
	             }
	         }
	         OutputStream out = new FileOutputStream(filePath);
	         document.write(out);
	         out.close();
	    }*/
}
