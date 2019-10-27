/*
 * �� �� ��:  MSWordPoi4.java
 * ��    Ȩ:  Sunny Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * ��    ��:  <����>
 * �� �� ��:  L.Hao
 * �޸�ʱ��:  2014-8-8
 * ���ٵ���:  <���ٵ���>
 * �޸ĵ���:  <�޸ĵ���>
 * �޸�����:  <�޸�����>
 */
package com.cardinal;

/**
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  L.Hao
 * @version  [�汾��, 2014-8-8]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

public class MSWordPoi4
{
    
    /**
    * @param args
    */
    public static void main(String[] args)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("${sub}", "���ϴ�ѧ");
        map.put("${item2.school}", "���ϴ�ѧ");
        map.put("${item2.address}", "����");
//        map.put("${item1.name}", "����1");
//        map.put("${item1.numberStudent}", "���002");
//        map.put("${item1.sex}", "��2");
//        map.put("${item1.age}", "19");
        String srcPath = "D:/temp/���칤�տ�.doc";
        File f = new File(srcPath);
        if(!f.exists()){
        	try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
        }
        readwriteWord(srcPath, map);
    }
    
    /**
    * ʵ�ֶ�word��ȡ���޸Ĳ���
    * 
    * @param filePath
    *            wordģ��·��������
    * @param map
    *            ���������ݣ������ݿ��ȡ
    */
    public static void readwriteWord(String filePath, Map<String, String> map)
    {
        // ��ȡwordģ��
        // String fileDir = new
        // File(base.getFile(),"http://www.cnblogs.com/http://www.cnblogs.com/../doc/").getCanonicalPath();
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(new File(filePath));
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        HWPFDocument hdt = null;
        try
        {
            hdt = new HWPFDocument(in);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        Fields fields = hdt.getFields();
        Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN)
                .iterator();
        while (it.hasNext())
        {
            System.out.println(it.next().getType());
        }
        
        //��ȡword�ı�����
        Range range = hdt.getRange();
        TableIterator tableIt = new TableIterator(range); 
        //�����ĵ��еı��
        int ii = 0;
        while (tableIt.hasNext()) {  
            Table tb = (Table) tableIt.next();  
            ii++;
            System.out.println("��"+ii+"���������...................");
            //�����У�Ĭ�ϴ�0��ʼ
            for (int i = 0; i < tb.numRows(); i++) {  
                TableRow tr = tb.getRow(i);  
                //ֻ��ǰ8�У����ⲿ��
                if(i >=8) break;
                //�����У�Ĭ�ϴ�0��ʼ
                for (int j = 0; j < tr.numCells(); j++) {  
                    TableCell td = tr.getCell(j);//ȡ�õ�Ԫ��
                    //ȡ�õ�Ԫ�������
                    for(int k=0;k<td.numParagraphs();k++){  
                        Paragraph para =td.getParagraph(k);  
                        String s = para.text();  
                        System.out.println(s);
                    } //end for   
                }   //end for
            }   //end for
        } //end while
        //System.out.println(range.text());
        
        // �滻�ı�����
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            range.replaceText(entry.getKey(), entry.getValue());
        }
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        String fileName = "" + System.currentTimeMillis();
        fileName += ".doc";
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream("F:/" + fileName, true);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            hdt.write(ostream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // ����ֽ���
        try
        {
            out.write(ostream.toByteArray());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            ostream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
