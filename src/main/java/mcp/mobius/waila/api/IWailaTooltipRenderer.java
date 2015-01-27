package mcp.mobius.waila.api;

import java.awt.*;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaTooltipRenderer {

    public abstract Dimension getSize(String[] paramArrayOfString, IWailaCommonAccessor paramIWailaCommonAccessor);

    public abstract void draw(String[] paramArrayOfString, IWailaCommonAccessor paramIWailaCommonAccessor);

}
