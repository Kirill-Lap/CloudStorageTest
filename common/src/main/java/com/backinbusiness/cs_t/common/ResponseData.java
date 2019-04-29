package com.backinbusiness.cs_t.common;

public class ResponseData {
    private int intVal;

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "intVal=" + intVal +
                '}';
    }
}
