package mcp.mobius.waila.api.impl;

import mcp.mobius.waila.api.IWailaFMPAccessor;
import mcp.mobius.waila.utils.NBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class DataAccessorFMP implements IWailaFMPAccessor {

    String id;
    World world;
    EntityPlayer player;
    MovingObjectPosition mop;
    Vec3 renderingvec = null;
    TileEntity entity;
    NBTTagCompound partialNBT = null;
    NBTTagCompound remoteNBT = null;
    long timeLastUpdate = System.currentTimeMillis();
    double partialFrame;
    public static DataAccessorFMP instance = new DataAccessorFMP();

    public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop, NBTTagCompound _partialNBT, String id)
    {
        set(_world, _player, _mop, _partialNBT, id, null, 0.0D);
    }

    public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop, NBTTagCompound _partialNBT, String id, Vec3 renderVec, double partialTicks)
    {
        this.world = _world;
        this.player = _player;
        this.mop = _mop;
        this.entity = this.world.func_147438_o(_mop.field_72311_b, _mop.field_72312_c, _mop.field_72309_d);
        this.partialNBT = _partialNBT;
        this.id = id;
        this.renderingvec = renderVec;
        this.partialFrame = partialTicks;
    }

    public World getWorld()
    {
        return this.world;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    public TileEntity getTileEntity()
    {
        return this.entity;
    }

    public MovingObjectPosition getPosition()
    {
        return this.mop;
    }

    public NBTTagCompound getNBTData()
    {
        return this.partialNBT;
    }

    public NBTTagCompound getFullNBTData()
    {
        if (this.entity == null) {
            return null;
        }
        if (isTagCorrect(this.remoteNBT)) {
            return this.remoteNBT;
        }
        NBTTagCompound tag = new NBTTagCompound();
        this.entity.func_145841_b(tag);
        return tag;
    }

    public int getNBTInteger(NBTTagCompound tag, String keyname)
    {
        return NBTUtil.getNBTInteger(tag, keyname);
    }

    public double getPartialFrame()
    {
        return this.partialFrame;
    }

    public Vec3 getRenderingPosition()
    {
        return this.renderingvec;
    }

    public String getID()
    {
        return this.id;
    }

    private boolean isTagCorrect(NBTTagCompound tag)
    {
        if (tag == null)
        {
            this.timeLastUpdate = (System.currentTimeMillis() - 250L);
            return false;
        }
        int x = tag.func_74762_e("x");
        int y = tag.func_74762_e("y");
        int z = tag.func_74762_e("z");
        if ((x == this.mop.field_72311_b) && (y == this.mop.field_72312_c) && (z == this.mop.field_72309_d)) {
            return true;
        }
        this.timeLastUpdate = (System.currentTimeMillis() - 250L);
        return false;
    }

}
