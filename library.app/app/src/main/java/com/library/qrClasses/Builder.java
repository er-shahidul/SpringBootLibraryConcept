package com.library.qrClasses;

import net.glxn.qrgen.QRCode;

import java.io.ByteArrayOutputStream;

/**
 * @developer Shahidul Hasan
 * class Builder
 *
 */
public class Builder {
    private String text;
    private QRImageType imageType = QRImageType.JPG;
    private int width = 125;
    private int height = 125;
    
    private byte[] QRBytes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QRImageType getImageType() {
        return imageType;
    }

    public void setImageType(QRImageType imageType) {
        this.imageType = imageType;
    }

    public int getHeightWidth() {
        return width;
    }

    public void setHeightWidth(int HeightWidth) {
        this.width = this.height = width;
    }

    public byte[] getQRBytes() 
    {
        return QRBytes;
    }    
    public boolean GenerateQR()
    {
        try{
            ByteArrayOutputStream output =  QRCode.from(this.text).withSize(256, 256).to(imageType.getimageType()).stream();
            QRBytes = output.toByteArray();
        }
        catch(Exception ex)
        {
            throw ex;
        }
        return true;
    }
    public boolean GenerateQR(String inText)
    {
        this.text = inText;
        try{
            return GenerateQR();
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public boolean GenerateQR(String inText, int HeightWidth)
    {
        this.height = this.width = HeightWidth;
        try{
            return GenerateQR(inText);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public boolean GenerateQR(String inText, int HeightWidth, QRImageType type)
    {
        this.imageType = type;
        try{
            return GenerateQR(inText, HeightWidth);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
