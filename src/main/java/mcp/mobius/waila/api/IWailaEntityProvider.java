package mcp.mobius.waila.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaEntityProvider {

    public abstract Entity getWailaOverride(IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler);

    public abstract List<String> getWailaHead(Entity paramEntity, List<String> paramList, IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler);

    public abstract List<String> getWailaBody(Entity paramEntity, List<String> paramList, IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler);

    public abstract List<String> getWailaTail(Entity paramEntity, List<String> paramList, IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler);

    public abstract NBTTagCompound getNBTData(EntityPlayerMP paramEntityPlayerMP, Entity paramEntity, NBTTagCompound paramNBTTagCompound, World paramWorld);

}
