package com.cardinal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.*;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.extractor.*;
import org.apache.xmlbeans.XmlException;


 public class DocManager {

     public DocManager() {
     }

     public void createXWPFDocument(String filePath) throws IOException {
    	 XWPFDocument docx = new XWPFDocument();
    	 docx.write(new FileOutputStream(filePath));
    	 docx.close();
     }
     
     public void createHWPFDocument(String filePath) throws IOException {
    	 HWPFDocument doc = new HWPFDocument(
    			 new FileInputStream(DocManager.class.getResource("/").getPath()+"/template.doc"));
    	 doc.write(new FileOutputStream(filePath));
     }
     
     public String getXWPFText(String filePath) throws XmlException,OpenXML4JException,IOException{
    	//得到.docx文件提取器 
    	 XWPFWordExtractor docx = new XWPFWordExtractor(POIXMLDocument.openPackage(filePath)); 
    	 //提取.docx正文文本 
    	 return docx.getText(); 
     }
     
     public void appendXWPFDcoument(String filePath, String content) throws IOException{
    	 XWPFDocument docx = new XWPFDocument();
    	 docx.createParagraph().createRun().setText(content);
    	 docx.write(new FileOutputStream(filePath));
    	 docx.close();
     }
     
      public void formatDocument() throws IOException {
    	 /* XWPFDocument document = new XWPFDocument(
        		 new FileInputStream(DocManager.class.getResource("/").getPath()+"/template.docx"));
         //document_createTOC();
         // FileReader fr = new FileReader("final_words.txt");
         // On some conditions, read mistaken characters and have to set file encoding
         InputStreamReader isr = new InputStreamReader(
        		 new FileInputStream(DocManager.class.getResource("/").getPath()+"/final_words.txt"), "UTF-8");
         BufferedReader br = new BufferedReader(isr);
         String line = null;
         boolean next = true;
         while ((line = br.readLine()) != null) {
             XWPFParagraph paragraph = document.createParagraph();
             if(next) {
                 line = line.replace(',', ' ');
                 paragraph.setStyle("Heading3");
                 XWPFRun createRun = paragraph.createRun();
                 createRun.setFontFamily("Times New Roman");
                 createRun.setFontSize(10);
                 createRun.setBold(true);
                 createRun.setText(line);
                 next = false;
             } else {
                 XWPFRun createRun = paragraph.createRun();
                 createRun.setFontFamily(" ");
                 createRun.setFontSize(10);
                 createRun.setText(line);
                 if(line.length() ==0) {
                     next = true;
                 }
             }
         }
         
         FileOutputStream out = new FileOutputStream("words.docx");
         document.write(out);
         out.close();
         document.close();
         br.close();*/
     }

     public static void main(String[] args) {
			DocManager dm = new DocManager();
			 //dm.createXWPFDocument("test.docx");
     }
 }