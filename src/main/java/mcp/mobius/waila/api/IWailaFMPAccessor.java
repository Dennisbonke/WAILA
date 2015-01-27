package mcp.mobius.waila.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaFMPAccessor {

    public abstract World getWorld();

    public abstract EntityPlayer getPlayer();

    public abstract TileEntity getTileEntity();

    public abstract MovingObjectPosition getPosition();

    public abstract NBTTagCompound getNBTData();

    public abstract NBTTagCompound getFullNBTData();

    public abstract int getNBTInteger(NBTTagCompound paramNBTTagCompound, String paramString);

    public abstract double getPartialFrame();

    public abstract Vec3 getRenderingPosition();

    public abstract String getID();

}
