package com.library.qrClasses;

/**
 * @developer Shahidul Hasan
 * class CRC_16
 *
 */
public class CRC_16 {
    public void CRC_16()
    {}
    public char Calculate(String data_p)
    {
        int length = data_p.length();
        int count = 0;
        String CRC = "";
        char x;
        char crc = (char) 0xFFFF;
        while (0 < length--) {
           x = (char)(crc >> 8 ^ (char)data_p.charAt(count)) ;
           count++;
           x = (char)(x ^ x>>4);
           crc = ( char)((crc << 8) ^ (( char)(x << 12)) ^ (( char)(x <<5)) ^ (( char)x));
            
        }
        return crc;
    }

}
