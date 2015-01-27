package mcp.mobius.waila.commands;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class CommandDumpHandlers extends CommandBase {

    public String func_71517_b()
    {
        return "dumphandlers";
    }

    public String func_71518_a(ICommandSender p_71518_1_)
    {
        return "/dumphandlers";
    }

    public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_)
    {
        System.out.printf("\n\n== HEAD BLOCK PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().headBlockProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaDataProvider provider : (ArrayList)ModuleRegistrar.instance().headBlockProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== BODY BLOCK PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().bodyBlockProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaDataProvider provider : (ArrayList)ModuleRegistrar.instance().bodyBlockProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== TAIL BLOCK PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().tailBlockProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaDataProvider provider : (ArrayList)ModuleRegistrar.instance().tailBlockProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== STACK BLOCK PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().stackBlockProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaDataProvider provider : (ArrayList)ModuleRegistrar.instance().stackBlockProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== HEAD ENTITY PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().headEntityProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaEntityProvider provider : (ArrayList)ModuleRegistrar.instance().headEntityProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== BODY ENTITY PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().bodyEntityProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaEntityProvider provider : (ArrayList)ModuleRegistrar.instance().bodyEntityProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== TAIL ENTITY PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().tailEntityProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaEntityProvider provider : (ArrayList)ModuleRegistrar.instance().tailEntityProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n== STACK ENTITY PROVIDERS ==\n", new Object[0]);
        for (Class clazz : ModuleRegistrar.instance().overrideEntityProviders.keySet())
        {
            System.out.printf("+ %s +\n", new Object[] { clazz.getName() });
            for (IWailaEntityProvider provider : (ArrayList)ModuleRegistrar.instance().overrideEntityProviders.get(clazz)) {
                System.out.printf("  - %s\n", new Object[] { provider.getClass().getName() });
            }
            System.out.printf("\n", new Object[0]);
        }
    }

    public int func_82362_a()
    {
        return 3;
    }

    public boolean func_71519_b(ICommandSender sender)
    {
        return super.func_71519_b(sender);
    }

}
