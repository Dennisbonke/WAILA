package mcp.mobius.waila.api;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaCommonAccessor {

    public abstract World getWorld();

    public abstract EntityPlayer getPlayer();

    public abstract Block getBlock();

    public abstract int getBlockID();

    public abstract String getBlockQualifiedName();

    public abstract int getMetadata();

    public abstract TileEntity getTileEntity();

    public abstract Entity getEntity();

    public abstract MovingObjectPosition getPosition();

    public abstract Vec3 getRenderingPosition();

    public abstract NBTTagCompound getNBTData();

    public abstract int getNBTInteger(NBTTagCompound paramNBTTagCompound, String paramString);

    public abstract double getPartialFrame();

    public abstract ForgeDirection getSide();

    public abstract ItemStack getStack();

}
