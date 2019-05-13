package com.backinbusiness.cs_t.common;

public enum DataType {
    EMPTY ((byte)-1), AUTHENTIFICATION ((byte)0), FILE((byte)15), COMMAND((byte) 16);

    byte idByte;

    DataType(byte identifierByte) {
        this.idByte = identifierByte;
    }

    public static DataType getDataTypeById(byte b) {
        if (b == AUTHENTIFICATION.idByte) {
            return AUTHENTIFICATION;
        }
        if (b == FILE.idByte) {
            return FILE;
        }
        if (b == COMMAND.idByte) {
            return COMMAND;
        }
        return EMPTY;
    }
}
