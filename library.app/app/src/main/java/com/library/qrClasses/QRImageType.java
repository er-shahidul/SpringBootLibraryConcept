package com.library.qrClasses;


import net.glxn.qrgen.image.ImageType;

/**
 * @developer Shahidul Hasan
 * class QRImageType
 *
 */
public enum QRImageType {
    JPG(ImageType.JPG), GIF(ImageType.GIF), PNG(ImageType.PNG);
    
    public ImageType imageType;
    QRImageType(ImageType type) {
        this.imageType = type;
    }
    public ImageType getimageType(){
        return imageType;
    }
}
