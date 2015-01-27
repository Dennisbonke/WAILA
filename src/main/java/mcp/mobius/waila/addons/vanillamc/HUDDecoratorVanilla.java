package mcp.mobius.waila.addons.vanillamc;

import mcp.mobius.waila.api.IWailaBlockDecorator;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.gui.helpers.UIHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HUDDecoratorVanilla implements IWailaBlockDecorator {

    public void decorateBlock(ItemStack itemStack, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Tessellator tessellator = Tessellator.field_78398_a;


        UIHelper.drawFloatingText("IN", accessor.getRenderingPosition(), 0.5F, 0.2F, -0.2F, 90.0F, 0.0F, 0.0F);
        UIHelper.drawFloatingText("OUT", accessor.getRenderingPosition(), -0.2F, 0.2F, 0.5F, 90.0F, 90.0F, 0.0F);
        UIHelper.drawFloatingText("OUT", accessor.getRenderingPosition(), 1.2F, 0.2F, 0.5F, 90.0F, -90.0F, 0.0F);
        UIHelper.drawFloatingText("OUT", accessor.getRenderingPosition(), 0.5F, 0.2F, 1.2F, 90.0F, -180.0F, 0.0F);

        double offset = 0.1D;
        double delta = 1.0D + 2.0D * offset;

        double x = accessor.getRenderingPosition().field_72450_a - offset;
        double y = accessor.getRenderingPosition().field_72448_b - offset;
        double z = accessor.getRenderingPosition().field_72449_c - offset;

        tessellator.func_78382_b();

        tessellator.func_78370_a(255, 255, 255, 150);

        tessellator.func_78377_a(x, y + 0.2D, z);
        tessellator.func_78377_a(x, y + 0.2D, z + delta / 2.0D - 0.1D);
        tessellator.func_78377_a(x + offset, y + 0.2D, z + delta / 2.0D - 0.1D);
        tessellator.func_78377_a(x + offset, y + 0.2D, z);

        tessellator.func_78377_a(x, y + 0.2D, z + delta / 2.0D + 0.1D);
        tessellator.func_78377_a(x, y + 0.2D, z + delta);
        tessellator.func_78377_a(x + offset, y + 0.2D, z + delta);
        tessellator.func_78377_a(x + offset, y + 0.2D, z + delta / 2.0D + 0.1D);

        tessellator.func_78377_a(x + delta - 0.1D, y + 0.2D, z + 0.1D);
        tessellator.func_78377_a(x + delta - 0.1D, y + 0.2D, z + delta / 2.0D - 0.1D);
        tessellator.func_78377_a(x + delta + offset - 0.1D, y + 0.2D, z + delta / 2.0D - 0.1D);
        tessellator.func_78377_a(x + delta + offset - 0.1D, y + 0.2D, z + 0.1D);

        tessellator.func_78377_a(x + delta - 0.1D, y + 0.2D, z + delta / 2.0D + 0.1D);
        tessellator.func_78377_a(x + delta - 0.1D, y + 0.2D, z + delta);
        tessellator.func_78377_a(x + delta + offset - 0.1D, y + 0.2D, z + delta);
        tessellator.func_78377_a(x + delta + offset - 0.1D, y + 0.2D, z + delta / 2.0D + 0.1D);


        tessellator.func_78377_a(x + 0.1D, y + 0.2D, z);
        tessellator.func_78377_a(x + 0.1D, y + 0.2D, z + offset);
        tessellator.func_78377_a(x + delta / 2.0D - 0.1D, y + 0.2D, z + offset);
        tessellator.func_78377_a(x + delta / 2.0D - 0.1D, y + 0.2D, z);

        tessellator.func_78377_a(x + delta / 2.0D + 0.1D, y + 0.2D, z);
        tessellator.func_78377_a(x + delta / 2.0D + 0.1D, y + 0.2D, z + offset);
        tessellator.func_78377_a(x + delta, y + 0.2D, z + offset);
        tessellator.func_78377_a(x + delta, y + 0.2D, z);

        tessellator.func_78377_a(x + 0.1D, y + 0.2D, z + delta - 0.1D);
        tessellator.func_78377_a(x + 0.1D, y + 0.2D, z + offset + delta - 0.1D);
        tessellator.func_78377_a(x + delta / 2.0D - 0.1D, y + 0.2D, z + offset + delta - 0.1D);
        tessellator.func_78377_a(x + delta / 2.0D - 0.1D, y + 0.2D, z + delta - 0.1D);

        tessellator.func_78377_a(x + delta / 2.0D + 0.1D, y + 0.2D, z + delta - 0.1D);
        tessellator.func_78377_a(x + delta / 2.0D + 0.1D, y + 0.2D, z + offset + delta - 0.1D);
        tessellator.func_78377_a(x + delta - 0.1D, y + 0.2D, z + offset + delta - 0.1D);
        tessellator.func_78377_a(x + delta - 0.1D, y + 0.2D, z + delta - 0.1D);

        tessellator.func_78381_a();
    }

}
