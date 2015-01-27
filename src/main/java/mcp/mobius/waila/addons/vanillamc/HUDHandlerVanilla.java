package mcp.mobius.waila.addons.vanillamc;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HUDHandlerVanilla implements IWailaDataProvider {

    static Block mobSpawner = Blocks.field_150474_ac;
    static Block crops = Blocks.field_150464_aj;
    static Block melonStem = Blocks.field_150394_bc;
    static Block pumpkinStem = Blocks.field_150393_bb;
    static Block carrot = Blocks.field_150459_bM;
    static Block potato = Blocks.field_150469_bN;
    static Block lever = Blocks.field_150442_at;
    static Block repeaterIdle = Blocks.field_150413_aR;
    static Block repeaterActv = Blocks.field_150416_aS;
    static Block comparatorIdl = Blocks.field_150441_bU;
    static Block comparatorAct = Blocks.field_150455_bV;
    static Block redstone = Blocks.field_150488_af;
    static Block jukebox = Blocks.field_150421_aI;
    static Block cocoa = Blocks.field_150375_by;
    static Block netherwart = Blocks.field_150388_bm;
    static Block silverfish = Blocks.field_150418_aU;
    static Block doubleplant = Blocks.field_150398_cm;
    static Block leave = Blocks.field_150362_t;
    static Block leave2 = Blocks.field_150361_u;

    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();
        if ((block == silverfish) && (config.getConfig("vanilla.silverfish")))
        {
            int metadata = accessor.getMetadata();
            switch (metadata)
            {
                case 0:
                    return new ItemStack(Blocks.field_150348_b);
                case 1:
                    return new ItemStack(Blocks.field_150347_e);
                case 2:
                    return new ItemStack(Blocks.field_150336_V);
            }
            return null;
        }
        if (block == redstone) {
            return new ItemStack(Items.field_151137_ax);
        }
        if ((block == doubleplant) && ((accessor.getMetadata() & 0x8) != 0))
        {
            int x = accessor.getPosition().field_72311_b;
            int y = accessor.getPosition().field_72312_c - 1;
            int z = accessor.getPosition().field_72309_d;
            int meta = accessor.getWorld().func_72805_g(x, y, z);

            return new ItemStack(doubleplant, 0, meta);
        }
        if ((block instanceof BlockRedstoneOre)) {
            return new ItemStack(Blocks.field_150450_ax);
        }
        if (block == crops) {
            return new ItemStack(Items.field_151015_O);
        }
        if (((block == leave) || (block == leave2)) && (accessor.getMetadata() > 3)) {
            return new ItemStack(block, 1, accessor.getMetadata() - 4);
        }
        return null;
    }

    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();
        if ((block == mobSpawner) && ((accessor.getTileEntity() instanceof TileEntityMobSpawner)) && (config.getConfig("vanilla.spawntype")))
        {
            String name = (String)currenttip.get(0);
            String mobname = ((TileEntityMobSpawner)accessor.getTileEntity()).func_145881_a().func_98276_e();
            currenttip.set(0, String.format("%s (%s)", new Object[] { name, mobname }));
        }
        if (block == redstone)
        {
            String name = ((String)currenttip.get(0)).replaceFirst(String.format(" %s", new Object[] { Integer.valueOf(accessor.getMetadata()) }), "");
            currenttip.set(0, name);
        }
        if (block == melonStem) {
            currenttip.set(0, SpecialChars.WHITE + "Melon stem");
        }
        if (block == pumpkinStem) {
            currenttip.set(0, SpecialChars.WHITE + "Pumpkin stem");
        }
        return currenttip;
    }

    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();

        boolean iscrop = crops.getClass().isInstance(block);
        if ((config.getConfig("general.showcrop")) && (
                (iscrop) || (block == melonStem) || (block == pumpkinStem) || (block == carrot) || (block == potato)))
        {
            float growthValue = accessor.getMetadata() / 7.0F * 100.0F;
            if (growthValue < 100.0D) {
                currenttip.add(String.format("%s : %.0f %%", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), Float.valueOf(growthValue) }));
            } else {
                currenttip.add(String.format("%s : %s", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), LangUtil.translateG("hud.msg.mature", new Object[0]) }));
            }
            return currenttip;
        }
        if ((block == cocoa) && (config.getConfig("general.showcrop")))
        {
            float growthValue = (accessor.getMetadata() >> 2) / 2.0F * 100.0F;
            if (growthValue < 100.0D) {
                currenttip.add(String.format("%s : %.0f %%", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), Float.valueOf(growthValue) }));
            } else {
                currenttip.add(String.format("%s : %s", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), LangUtil.translateG("hud.msg.mature", new Object[0]) }));
            }
            return currenttip;
        }
        if ((block == netherwart) && (config.getConfig("general.showcrop")))
        {
            float growthValue = accessor.getMetadata() / 3.0F * 100.0F;
            if (growthValue < 100.0D) {
                currenttip.add(String.format("%s : %.0f %%", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), Float.valueOf(growthValue) }));
            } else {
                currenttip.add(String.format("%s : %s", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), LangUtil.translateG("hud.msg.mature", new Object[0]) }));
            }
            return currenttip;
        }
        if ((config.getConfig("vanilla.leverstate")) &&
                (block == lever))
        {
            String redstoneOn = (accessor.getMetadata() & 0x8) == 0 ? LangUtil.translateG("hud.msg.off", new Object[0]) : LangUtil.translateG("hud.msg.on", new Object[0]);
            currenttip.add(String.format("%s : %s", new Object[] { LangUtil.translateG("hud.msg.state", new Object[0]), redstoneOn }));
            return currenttip;
        }
        if ((config.getConfig("vanilla.repeater")) && (
                (block == repeaterIdle) || (block == repeaterActv)))
        {
            int tick = (accessor.getMetadata() >> 2) + 1;
            if (tick == 1) {
                currenttip.add(String.format("%s : %s tick", new Object[] { LangUtil.translateG("hud.msg.delay", new Object[0]), Integer.valueOf(tick) }));
            } else {
                currenttip.add(String.format("%s : %s ticks", new Object[] { LangUtil.translateG("hud.msg.delay", new Object[0]), Integer.valueOf(tick) }));
            }
            return currenttip;
        }
        if ((config.getConfig("vanilla.comparator")) && (
                (block == comparatorIdl) || (block == comparatorAct)))
        {
            String mode = (accessor.getMetadata() >> 2 & 0x1) == 0 ? LangUtil.translateG("hud.msg.comparator", new Object[0]) : LangUtil.translateG("hud.msg.substractor", new Object[0]);

            currenttip.add("Mode : " + mode);

            return currenttip;
        }
        if ((config.getConfig("vanilla.redstone")) &&
                (block == redstone))
        {
            currenttip.add(String.format("%s : %s", new Object[] { LangUtil.translateG("hud.msg.power", new Object[0]), Integer.valueOf(accessor.getMetadata()) }));
            return currenttip;
        }
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
    {
        if (te != null) {
            te.func_145841_b(tag);
        }
        return tag;
    }

    public static void register()
    {
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.spawntype");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.leverstate");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.repeater");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.comparator");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.redstone");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.silverfish");
        ModuleRegistrar.instance().addConfigRemote("VanillaMC", "vanilla.jukebox");

        IWailaDataProvider provider = new HUDHandlerVanilla();

        ModuleRegistrar.instance().registerStackProvider(provider, silverfish.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, doubleplant.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, BlockRedstoneOre.class);
        ModuleRegistrar.instance().registerStackProvider(provider, crops.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, leave.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, leave2.getClass());


        ModuleRegistrar.instance().registerHeadProvider(provider, mobSpawner.getClass());
        ModuleRegistrar.instance().registerHeadProvider(provider, melonStem.getClass());
        ModuleRegistrar.instance().registerHeadProvider(provider, pumpkinStem.getClass());

        ModuleRegistrar.instance().registerBodyProvider(provider, crops.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, melonStem.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, pumpkinStem.getClass());


        ModuleRegistrar.instance().registerBodyProvider(provider, lever.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, repeaterIdle.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, repeaterActv.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, comparatorIdl.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, comparatorAct.getClass());
        ModuleRegistrar.instance().registerHeadProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, jukebox.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, cocoa.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, netherwart.getClass());

        ModuleRegistrar.instance().registerNBTProvider(provider, mobSpawner.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, crops.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, melonStem.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, pumpkinStem.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, carrot.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, potato.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, lever.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, repeaterIdle.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, repeaterActv.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, comparatorIdl.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, comparatorAct.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, jukebox.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, cocoa.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, netherwart.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, silverfish.getClass());
    }

}
