package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface MappingStrategy<T> {

    public abstract PropertyDescriptor findDescriptor(int paramInt)
            throws IntrospectionException;

    public abstract T createBean()
            throws InstantiationException, IllegalAccessException;

    public abstract void captureHeader(CSVReader paramCSVReader)
            throws IOException;

}
