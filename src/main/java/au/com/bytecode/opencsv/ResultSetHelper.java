package au.com.bytecode.opencsv;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface ResultSetHelper {

    public abstract String[] getColumnNames(ResultSet paramResultSet)
            throws SQLException;

    public abstract String[] getColumnValues(ResultSet paramResultSet)
            throws SQLException, IOException;

}
