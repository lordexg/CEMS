package com.sage.cems.util;

import java.io.IOException;

public class EnumHelper  {
    /**
     * @param tableName Takes the Enum table name Value.
     * @return <code>String</code>, new PK value.
    */
     public static String getNewPK(TableName tableName) throws IOException {
        FileManager fileManager = new CEMSFileManager();
        ColumnName columnName = ColumnName.valueOf(tableName.name() + "_ID");

        String lastIDString = fileManager.getLastRow(tableName).get(columnName);

        // to handle empty Table -> lastIDString = null
        int newIDInt;
        try{
            newIDInt = Integer.parseInt(lastIDString) + 1;
        }catch(NumberFormatException e){
            newIDInt = 1;
        }
        return Integer.toString(newIDInt);
    }
}
