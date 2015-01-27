package mcp.mobius.waila.handlers.nei;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.api.API;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class NEIHandler {

    public static void register()
    {
        if (ConfigHandler.instance().getConfig("general", Constants.CFG_WAILA_NEWFILTERS, true))
        {
            API.addSearchProvider(new ModNameFilter());
            API.addSearchProvider(new OreDictFilter());
        }
        GuiContainerManager.addTooltipHandler(new TooltipHandlerWaila());




        NEIClientConfig.getSetting(Constants.BIND_NEI_SHOW).setIntValue(0);
        NEIClientConfig.getSetting(Constants.CFG_NEI_SHOW).setBooleanValue(false);




        GuiContainerManager.addInputHandler(new HandlerEnchants());
        API.addKeyBind(Constants.BIND_SCREEN_ENCH, 23);
    }

    public static boolean firstInventory = true;

    public static void openRecipeGUI(boolean recipe)
    {
        Minecraft mc = Minecraft.func_71410_x();
        if ((RayTracing.instance().getTarget() != null) && (RayTracing.instance().getTarget().field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK))
        {
            List<ItemStack> stacks = RayTracing.instance().getIdentifierItems();
            if (stacks.size() > 0)
            {
                mc.func_147108_a(new GuiInventory(mc.field_71439_g));
                if (firstInventory)
                {
                    try
                    {
                        Thread.sleep(1000L);
                    }
                    catch (Exception e) {}
                    firstInventory = false;
                }
                if (recipe) {
                    if (!GuiCraftingRecipe.openRecipeGui("item", new Object[] { ((ItemStack)stacks.get(0)).func_77946_l() }))
                    {
                        ItemStack target = ((ItemStack)stacks.get(0)).func_77946_l();
                        target.func_77964_b(0);
                        if (!GuiCraftingRecipe.openRecipeGui("item", new Object[]{target}))
                        {
                            mc.field_71439_g.func_145747_a(new ChatComponentTranslation("§f§o" + LangUtil.translateG("client.msg.norecipe", new Object[0]), new Object[0]));
                            mc.func_147108_a((GuiScreen)null);
                            mc.func_71381_h();
                        }
                    }
                }
                if (!recipe) {
                    if (!GuiUsageRecipe.openRecipeGui("item", new Object[] { ((ItemStack)stacks.get(0)).func_77946_l() }))
                    {
                        ItemStack target = ((ItemStack)stacks.get(0)).func_77946_l();
                        target.func_77964_b(0);
                        if (!GuiUsageRecipe.openRecipeGui("item", new Object[]{target}))
                        {
                            mc.field_71439_g.func_145747_a(new ChatComponentTranslation("§f§o" + LangUtil.translateG("client.msg.nousage", new Object[0]), new Object[0]));
                            mc.func_147108_a((GuiScreen)null);
                            mc.func_71381_h();
                        }
                    }
                }
            }
        }
    }

}
