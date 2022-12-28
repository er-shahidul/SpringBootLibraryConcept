package com.library.qrClasses;


import java.util.ArrayList;
import java.util.List;

/**
 * @developer Shahidul Hasan
 * class QRObjects
 *
 */
public class QRObjects {
    public QRObjects()
    {
        QRObject = new ArrayList<dataElement>();
        for(int i = 0; i< 100; i++)
        {
            dataElement item ;
            if(!subElement[i])
                item = new dataElement<dataElement>();
            else
                item = new dataElement<String>();
            if(i<10)   
                item.setID("0" + Integer.toString(i));
            else                
                item.setID(Integer.toString(i));
            
            item.setLength(fieldlength[i]);
            QRObject.add(item);
        }
    }
    private int[] fieldlength = new int[]
           {2,2,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,4,3,0,2,0,0,2,0,
            0,0,0,4,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0};
    private boolean[] subElement = new boolean[]
           {false,false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false, true, true, true, true,
             true, true, true, true, true, true, true, true, true, true,
             true, true, true, true, true, true, true, true, true, true,
             true, true,false,false,false,false,false,false,false,false,
            false,false, true,false, true,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,
            };
    
    private List<dataElement> QRObject;

    public List<dataElement> getQRObject() {
        return QRObject;
    }

    public void setQRObject(List<dataElement> QRObject) {
        this.QRObject = QRObject;
    }
    
   
    public String Buield()
    {
        String QRData = "";
        for(int i = 0; i< 100; i++)
        {
            dataElement item = QRObject.get(i);
            if(!"".equals(item.getValue()))
            {
                if(fieldlength[i]==0)
                    QRData += padLeft(item.getID(), 2) + padLeft(Integer.toString(item.getValue().length()), 2) + item.getValue();
                else
                    QRData += padLeft(item.getID(), 2) + padLeft(Integer.toString(item.getLength()), 2) + item.getValue();
            }
        }
        return QRData;
    }
    private String padLeft(String inStr, int lngt)
    {
        while(inStr.length()<lngt)
            inStr="0"+inStr;
        
        return inStr;
    }
    
    public void Parse(String QRData)
    {
        String qrData = QRData;
        while(qrData.length()>0)
        {
            int ID = Integer.parseInt(qrData.substring(0,2));
            qrData = qrData.substring(2);
            int Ln = Integer.parseInt(qrData.substring(0,2));
            qrData = qrData.substring(2);
            String Data = qrData.substring(0,Ln);
            qrData = qrData.substring(Ln);
            
            if(fieldlength[ID]==0)
                QRObject.get(ID).setLength(Ln);
            
            QRObject.get(ID).setValue(Data);
            if(subElement[ID])            
            {
                QRObject.get(ID).setValue(Data);
                while(Data.length()>0)
                {
                    String subData = Data;
                    int subID = Integer.parseInt(subData.substring(0,2));
                    subData = subData.substring(2);
                    int subLn = Integer.parseInt(subData.substring(0,2));
                    subData = subData.substring(2);
                    String subsubData = subData.substring(0,Ln);
                    subData = subData.substring(Ln);
                    dataElement<String> item = new dataElement<String>();
                    item.setID("0" + subID);
                    item.setLength(subLn);
                    item.setValue(subsubData);
                    QRObject.get(ID).getData().add(item);
                }
            }
        }
    }
    
    private void add(String ID, String data)
    {
        dataElement<String> element = new dataElement<String>();
        element.setID(ID);
        element.setLength(data.length());
        element.setSubElement(false);
        element.setValue(data);
        QRObject.add(element);
    }
    private void add(String ID, String data, List<dataElement> elements)
    {
        dataElement<dataElement> element = new dataElement<dataElement>();
        element.setID(ID);
        element.setLength(data.length());
        element.setSubElement(true);       
        element.setData(elements);
        QRObject.add(element);
    }
    public void Add(String ID, String data, boolean subElement, List<dataElement> elements)
    {
        if(subElement)
        {
            add(ID, data, elements);
        }
        else
        {
            add(ID, data);
        }     
        
    }
}
