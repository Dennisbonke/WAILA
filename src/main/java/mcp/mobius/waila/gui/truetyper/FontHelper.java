package mcp.mobius.waila.gui.truetyper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class FontHelper {

    private static String formatEscape = "§";

    public static void drawString(String s, float x, float y, TrueTypeFont font, float scaleX, float scaleY, float... rgba)
    {
        drawString(s, x, y, font, scaleX, scaleY, 0.0F, rgba);
    }

    public static void drawString(String s, float x, float y, TrueTypeFont font, float scaleX, float scaleY, float rotationZ, float... rgba)
    {
        Minecraft mc = Minecraft.func_71410_x();
        ScaledResolution sr = new ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
        if (mc.field_71474_y.field_74319_N) {
            return;
        }
        int amt = 1;
        if (sr.func_78325_e() == 1) {
            amt = 2;
        }
        FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, matrixData);
        set2DMode(matrixData);
        GL11.glPushMatrix();
        y = mc.field_71440_d - y * sr.func_78325_e() - font.getLineHeight() / amt;
        float tx = x * sr.func_78325_e() + font.getWidth(s) / 2.0F;
        float tranx = tx + 2.0F;
        float trany = y + font.getLineHeight() / 2.0F;
        GL11.glTranslatef(tranx, trany, 0.0F);
        GL11.glRotatef(rotationZ, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-tranx, -trany, 0.0F);


        GL11.glEnable(3042);
        if (s.contains(formatEscape))
        {
            String[] pars = s.split(formatEscape);
            float totalOffset = 0.0F;
            for (int i = 0; i < pars.length; i++)
            {
                String par = pars[i];
                float[] c = rgba;
                if (i > 0)
                {
                    c = Formatter.getFormatted(par.charAt(0));
                    par = par.substring(1, par.length());
                }
                font.drawString(x * sr.func_78325_e() + totalOffset, y, par, scaleX / amt, scaleY / amt, c);
                totalOffset += font.getWidth(par);
            }
        }
        else
        {
            font.drawString(x * sr.func_78325_e(), y, s, scaleX / amt, scaleY / amt, rgba);
        }
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        set3DMode();
    }

    private static void set2DMode(FloatBuffer matrixData)
    {
        Minecraft mc = Minecraft.func_71410_x();
        ScaledResolution sr = new ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
        mc.field_71460_t.func_78478_c();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();


        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, mc.field_71443_c, 0.0D, mc.field_71440_d, -1.0D, 1.0D);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        Matrix4f matrix = new Matrix4f();
        matrix.load(matrixData);
        GL11.glTranslatef(matrix.m30 * sr.func_78325_e(), -matrix.m31 * sr.func_78325_e(), 0.0F);
    }

    private static void set3DMode()
    {
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        Minecraft mc = Minecraft.func_71410_x();
        mc.field_71460_t.func_78478_c();
    }

}
