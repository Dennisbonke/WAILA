package au.com.bytecode.opencsv.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HeaderColumnNameTranslateMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {

    private Map<String, String> columnMapping = new HashMap();

    protected String getColumnName(int col)
    {
        return col < this.header.length ? (String)this.columnMapping.get(this.header[col].toUpperCase()) : null;
    }

    public Map<String, String> getColumnMapping()
    {
        return this.columnMapping;
    }

    public void setColumnMapping(Map<String, String> columnMapping)
    {
        for (String key : columnMapping.keySet()) {
            this.columnMapping.put(key.toUpperCase(), columnMapping.get(key));
        }
    }

}
