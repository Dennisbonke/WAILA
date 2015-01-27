package mcp.mobius.waila.handlers.nei;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerInputHandler;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HandlerEnchants implements IContainerInputHandler {

    public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode)
    {
        return false;
    }

    public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {}

    public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyID)
    {
        GuiContainerManager.getManager();
        ItemStack stackover = GuiContainerManager.getStackMouseOver(gui);
        if (stackover == null) {
            return false;
        }
        if (keyID == NEIClientConfig.getKeyBinding(Constants.BIND_SCREEN_ENCH))
        {
            int itemEnchantability = stackover.func_77973_b().func_77619_b();
            if (itemEnchantability == 0) {
                return false;
            }
            Minecraft mc = Minecraft.func_71410_x();
            ScreenEnchants screen = new ScreenEnchants(mc.field_71462_r);
            screen.setStack(stackover);
            screen.setName(stackover.func_82833_r());
            screen.setEnchantability(String.valueOf(itemEnchantability));

            Enchantment[] enchants = null;
            if (stackover.func_77973_b() == Items.field_151122_aG) {
                enchants = Enchantment.field_92090_c;
            } else {
                enchants = Enchantment.field_77331_b;
            }
            for (Enchantment enchant : enchants)
            {
                boolean isCompatible = true;
                int level = 0;
                boolean isApplied = false;
                if ((enchant != null) && (
                        (enchant.canApplyAtEnchantingTable(stackover)) || (stackover.func_77973_b() == Items.field_151122_aG)))
                {
                    Map stackenchants;
                    if (stackover.func_77948_v())
                    {
                        stackenchants = EnchantmentHelper.func_82781_a(stackover);
                        for (Object id : stackenchants.keySet())
                        {
                            if (!enchant.func_77326_a(Enchantment.field_77331_b[((Integer)id).intValue()])) {
                                isCompatible = false;
                            }
                            if (((Integer)id).intValue() == enchant.field_77352_x)
                            {
                                isApplied = true;
                                level = ((Integer)stackenchants.get(id)).intValue();
                            }
                        }
                    }
                    for (int lvl = enchant.func_77319_d(); lvl <= enchant.func_77325_b(); lvl++)
                    {
                        int minEnchantEnchantability = enchant.func_77321_a(lvl);
                        int maxEnchantEnchantability = enchant.func_77317_b(lvl);

                        int minItemEnchantability = 1;
                        int meanItemEnchantability = 1 + itemEnchantability / 4;
                        int maxItemEnchantability = 1 + itemEnchantability / 2;

                        int minModifiedEnchantability = (int)(0.85D * minItemEnchantability + 0.5D);
                        int meanModifiedEnchantability = (int)(1.0D * meanItemEnchantability + 0.5D);
                        int maxModifiedEnchantability = (int)(1.15D * maxItemEnchantability + 0.5D);

                        int minLevel = (int)((minEnchantEnchantability - minModifiedEnchantability) / 1.15D);
                        int maxLevel = (int)((maxEnchantEnchantability - maxModifiedEnchantability) / 0.85D);

                        int meanMinLevel = (int)((minEnchantEnchantability - meanModifiedEnchantability) / 1.0D);
                        int meanMaxLevel = (int)((maxEnchantEnchantability - meanModifiedEnchantability) / 1.0D);

                        String colorcode = isCompatible ? "§f" : "§c";
                        if ((isApplied) && (lvl == level)) {
                            colorcode = "§e";
                        }
                        screen.addRow(new String[] { colorcode + enchant.func_77316_c(lvl), colorcode +
                                String.valueOf(minLevel), colorcode +
                                String.valueOf(maxLevel), colorcode +
                                String.valueOf(enchant.func_77324_c()), "§9§o" +
                                ModIdentification.nameFromObject(enchant) });
                    }
                }
            }
            mc.func_147108_a(screen);
        }
        return false;
    }

    public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button)
    {
        return false;
    }

    public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {}

    public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {}

    public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled)
    {
        return false;
    }

    public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {}

    public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button, long heldTime) {}

}
