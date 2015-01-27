package mcp.mobius.waila.utils;

import mcp.mobius.waila.Waila;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class WailaExceptionHandler {

    private static ArrayList<String> errs = new ArrayList();

    public static List<String> handleErr(Throwable e, String className, List<String> currenttip)
    {
        if (!errs.contains(className))
        {
            errs.add(className);
            for (StackTraceElement elem : e.getStackTrace())
            {
                Waila.log.log(Level.WARN, String.format("%s.%s:%s", new Object[] { elem.getClassName(), elem.getMethodName(), Integer.valueOf(elem.getLineNumber()) }));
                if (elem.getClassName().contains("waila")) {
                    break;
                }
            }
            Waila.log.log(Level.WARN, String.format("Catched unhandled exception : [%s] %s", new Object[] { className, e }));
        }
        if (currenttip != null) {
            currenttip.add("<ERROR>");
        }
        return currenttip;
    }

}
