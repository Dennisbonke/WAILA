package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ColumnPositionMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {

    private String[] columnMapping = new String[0];

    public void captureHeader(CSVReader reader)
            throws IOException
    {}

    protected String getColumnName(int col)
    {
        return (null != this.columnMapping) && (col < this.columnMapping.length) ? this.columnMapping[col] : null;
    }

    public String[] getColumnMapping()
    {
        return this.columnMapping != null ? (String[])this.columnMapping.clone() : null;
    }

    public void setColumnMapping(String[] columnMapping)
    {
        this.columnMapping = (columnMapping != null ? (String[])columnMapping.clone() : null);
    }

}
