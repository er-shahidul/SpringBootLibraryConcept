package com.library.qrClasses;

import java.util.List;

/**
 * @developer Shahidul Hasan
 * class dataElement
 *
 */
public class dataElement<E> {
    private String _ID;
    private int _Length;
    private Boolean _SubElement;
    private List<E> _Data;
    private String _value = "";

    public String getID() {
        return _ID;
    }

    public void setID(String _ID) {
        this._ID = _ID;
    }

    public int getLength() {
        return _Length;
    }

    public void setLength(int _Length) {
        this._Length = _Length;
    }

    public Boolean getSubElement() {
        return _SubElement;
    }

    public void setSubElement(Boolean _SubElement) {
        this._SubElement = _SubElement;
    }

    public List<E> getData() {
        return _Data;
    }

    public void setData(List<E> _Data) {
        this._Data = _Data;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String _value) {
        this._value = _value;
    }
}
