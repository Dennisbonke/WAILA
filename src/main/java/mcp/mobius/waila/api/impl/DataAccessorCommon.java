package mcp.mobius.waila.api.impl;

import cpw.mods.fml.common.registry.GameData;
import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.utils.NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
public class DataAccessorCommon implements IWailaCommonAccessor, IWailaDataAccessor, IWailaEntityAccessor {

    public World world;
    public EntityPlayer player;
    public MovingObjectPosition mop;
    public Vec3 renderingvec = null;
    public Block block;
    public int blockID;
    public String blockResource;
    public int metadata;
    public TileEntity tileEntity;
    public Entity entity;
    public NBTTagCompound remoteNbt = null;
    public long timeLastUpdate = System.currentTimeMillis();
    public double partialFrame;
    public ItemStack stack;
    public static DataAccessorCommon instance = new DataAccessorCommon();

    public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop)
    {
        set(_world, _player, _mop, null, 0.0D);
    }

    public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop, EntityLivingBase viewEntity, double partialTicks)
    {
        this.world = _world;
        this.player = _player;
        this.mop = _mop;
        if (this.mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            this.block = this.world.func_147439_a(_mop.field_72311_b, _mop.field_72312_c, _mop.field_72309_d);
            this.metadata = this.world.func_72805_g(_mop.field_72311_b, _mop.field_72312_c, _mop.field_72309_d);
            this.tileEntity = this.world.func_147438_o(_mop.field_72311_b, _mop.field_72312_c, _mop.field_72309_d);
            this.entity = null;
            this.blockID = Block.func_149682_b(this.block);
            this.blockResource = GameData.getBlockRegistry().func_148750_c(this.block);
            try
            {
                this.stack = new ItemStack(this.block, 1, this.metadata);
            }
            catch (Exception e) {}
        }
        else if (this.mop.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY)
        {
            this.block = null;
            this.metadata = -1;
            this.tileEntity = null;
            this.stack = null;
            this.entity = _mop.field_72308_g;
        }
        if (viewEntity != null)
        {
            double px = viewEntity.field_70142_S + (viewEntity.field_70165_t - viewEntity.field_70142_S) * partialTicks;
            double py = viewEntity.field_70137_T + (viewEntity.field_70163_u - viewEntity.field_70137_T) * partialTicks;
            double pz = viewEntity.field_70136_U + (viewEntity.field_70161_v - viewEntity.field_70136_U) * partialTicks;
            this.renderingvec = Vec3.func_72443_a(_mop.field_72311_b - px, _mop.field_72312_c - py, _mop.field_72309_d - pz);
            this.partialFrame = partialTicks;
        }
    }

    public World getWorld()
    {
        return this.world;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    public Block getBlock()
    {
        return this.block;
    }

    public int getBlockID()
    {
        return this.blockID;
    }

    public int getMetadata()
    {
        return this.metadata;
    }

    public TileEntity getTileEntity()
    {
        return this.tileEntity;
    }

    public Entity getEntity()
    {
        return this.entity;
    }

    public MovingObjectPosition getPosition()
    {
        return this.mop;
    }

    public Vec3 getRenderingPosition()
    {
        return this.renderingvec;
    }

    public NBTTagCompound getNBTData()
    {
        if ((this.tileEntity != null) && (isTagCorrectTileEntity(this.remoteNbt))) {
            return this.remoteNbt;
        }
        if ((this.entity != null) && (isTagCorrectEntity(this.remoteNbt))) {
            return this.remoteNbt;
        }
        if (this.tileEntity != null)
        {
            NBTTagCompound tag = new NBTTagCompound();
            try
            {
                this.tileEntity.func_145841_b(tag);
            }
            catch (Exception e) {}
            return tag;
        }
        if (this.entity != null)
        {
            NBTTagCompound tag = new NBTTagCompound();
            this.entity.func_70109_d(tag);
            return tag;
        }
        return null;
    }

    public void setNBTData(NBTTagCompound tag)
    {
        this.remoteNbt = tag;
    }

    private boolean isTagCorrectTileEntity(NBTTagCompound tag)
    {
        if ((tag == null) || (!tag.func_74764_b("WailaX")))
        {
            this.timeLastUpdate = (System.currentTimeMillis() - 250L);
            return false;
        }
        int x = tag.func_74762_e("WailaX");
        int y = tag.func_74762_e("WailaY");
        int z = tag.func_74762_e("WailaZ");
        if ((x == this.mop.field_72311_b) && (y == this.mop.field_72312_c) && (z == this.mop.field_72309_d)) {
            return true;
        }
        this.timeLastUpdate = (System.currentTimeMillis() - 250L);
        return false;
    }

    private boolean isTagCorrectEntity(NBTTagCompound tag)
    {
        if ((tag == null) || (!tag.func_74764_b("WailaEntityID")))
        {
            this.timeLastUpdate = (System.currentTimeMillis() - 250L);
            return false;
        }
        int id = tag.func_74762_e("WailaEntityID");
        if (id == this.entity.func_145782_y()) {
            return true;
        }
        this.timeLastUpdate = (System.currentTimeMillis() - 250L);
        return false;
    }

    public int getNBTInteger(NBTTagCompound tag, String keyname)
    {
        return NBTUtil.getNBTInteger(tag, keyname);
    }

    public double getPartialFrame()
    {
        return this.partialFrame;
    }

    public ForgeDirection getSide()
    {
        return ForgeDirection.getOrientation(getPosition().field_72310_e);
    }

    public ItemStack getStack()
    {
        return this.stack;
    }

    public boolean isTimeElapsed(long time)
    {
        return System.currentTimeMillis() - this.timeLastUpdate >= time;
    }

    public void resetTimer()
    {
        this.timeLastUpdate = System.currentTimeMillis();
    }

    public String getBlockQualifiedName()
    {
        return this.blockResource;
    }

}
