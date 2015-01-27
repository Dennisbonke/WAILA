package mcp.mobius.waila.gui.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class UIHelper {

    public static void drawTexture(int posX, int posY, int sizeX, int sizeY)
    {
        drawTexture(posX, posY, sizeX, sizeY, 0, 0, 256, 256);
    }

    public static void drawTexture(int posX, int posY, int sizeX, int sizeY, int texU, int texV, int texSizeU, int texSizeV)
    {
        float zLevel = 0.0F;
        float f = 0.0039063F;

        Tessellator tess = Tessellator.field_78398_a;
        tess.func_78382_b();
        tess.func_78374_a(posX + 0, posY + sizeY, zLevel, texU * f, (texV + texSizeV) * f);
        tess.func_78374_a(posX + sizeX, posY + sizeY, zLevel, (texU + texSizeU) * f, (texV + texSizeV) * f);
        tess.func_78374_a(posX + sizeX, posY + 0, zLevel, (texU + texSizeU) * f, texV * f);
        tess.func_78374_a(posX + 0, posY + 0, zLevel, texU * f, texV * f);
        tess.func_78381_a();
    }

    public static void drawGradientRect(int minx, int miny, int maxx, int maxy, int zlevel, int color1, int color2)
    {
        float alpha1 = (color1 >> 24 & 0xFF) / 255.0F;
        float red1 = (color1 >> 16 & 0xFF) / 255.0F;
        float green1 = (color1 >> 8 & 0xFF) / 255.0F;
        float blue1 = (color1 & 0xFF) / 255.0F;
        float alpha2 = (color2 >> 24 & 0xFF) / 255.0F;
        float red2 = (color2 >> 16 & 0xFF) / 255.0F;
        float green2 = (color2 >> 8 & 0xFF) / 255.0F;
        float blue2 = (color2 & 0xFF) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78369_a(red1, green1, blue1, alpha1);
        tessellator.func_78377_a(maxx, miny, zlevel);
        tessellator.func_78377_a(minx, miny, zlevel);
        tessellator.func_78369_a(red2, green2, blue2, alpha2);
        tessellator.func_78377_a(minx, maxy, zlevel);
        tessellator.func_78377_a(maxx, maxy, zlevel);
        tessellator.func_78381_a();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    public static void drawBillboard(Vec3 pos, float offX, float offY, float offZ, double x1, double y1, double x2, double y2, int r, int g, int b, int a, double partialFrame)
    {
        drawBillboard((float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c, offX, offY, offZ, x1, y1, x2, y2, r, g, b, a, partialFrame);
    }

    public static void drawBillboard(float posX, float posY, float posZ, float offX, float offY, float offZ, double x1, double y1, double x2, double y2, int r, int g, int b, int a, double partialFrame)
    {
        EntityLivingBase player = Minecraft.func_71410_x().field_71451_h;
        float playerViewY = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * (float)partialFrame;
        float playerViewX = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * (float)partialFrame;

        drawBillboard(posX, posY, posZ, offX, offY, offZ, playerViewX, playerViewY * -1.0F, 0.0F, x1, y1, x2, y2, r, g, b, a);
    }

    public static void drawBillboard(float posX, float posY, float posZ, float offX, float offY, float offZ, float rotX, float rotY, float rotZ, double x1, double y1, double x2, double y2, int r, int g, int b, int a)
    {
        float f = 1.6F;
        float f1 = 0.01666667F * f;
        GL11.glPushMatrix();

        GL11.glTranslatef(posX + offX, posY + offY, posZ + offZ);

        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(rotZ, 0.0F, 0.0F, 1.0F);


        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);


        drawRectangle(x1, y1, 0.0D, x2, y2, 0.0D, r, g, b, a);

        GL11.glPopMatrix();
    }

    public static void drawBillboardText(String text, Vec3 pos, float offX, float offY, float offZ, double partialFrame)
    {
        drawBillboardText(text, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c, offX, offY, offZ, partialFrame);
    }

    public static void drawBillboardText(String text, float posX, float posY, float posZ, float offX, float offY, float offZ, double partialFrame)
    {
        EntityLivingBase player = Minecraft.func_71410_x().field_71451_h;
        float playerViewY = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * (float)partialFrame;
        float playerViewX = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * (float)partialFrame;

        drawFloatingText(text, posX, posY, posZ, offX, offY, offZ, playerViewX, playerViewY * -1.0F, 0.0F);
    }

    public static void drawFloatingText(String text, Vec3 pos, float offX, float offY, float offZ, float rotX, float rotY, float rotZ)
    {
        drawFloatingText(text, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c, offX, offY, offZ, rotX, rotY, rotZ);
    }

    public static void drawFloatingText(String text, float posX, float posY, float posZ, float offX, float offY, float offZ, float rotX, float rotY, float rotZ)
    {
        if (text.isEmpty()) {
            return;
        }
        FontRenderer fontrenderer = Minecraft.func_71410_x().field_71466_p;

        float f = 1.6F;
        float f1 = 0.01666667F * f;
        GL11.glPushMatrix();



        GL11.glTranslatef(posX + offX, posY + offY, posZ + offZ);


        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(rotZ, 0.0F, 0.0F, 1.0F);


        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        byte b0 = 0;

        GL11.glDisable(3553);
        int j = fontrenderer.func_78256_a(text) / 2;
        drawRectangle(-j - 1, 8 + b0, 0.0D, j + 1, -1 + b0, 0.0D, 0, 0, 0, 64);

        GL11.glEnable(3553);
        fontrenderer.func_78276_b(text, -fontrenderer.func_78256_a(text) / 2, b0, 553648127);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        fontrenderer.func_78276_b(text, -fontrenderer.func_78256_a(text) / 2, b0, -1);



        GL11.glDisable(3553);
        GL11.glPopMatrix();
    }

    public static void drawRectangle(double x1, double y1, double z1, double x2, double y2, double z2, int r, int g, int b, int a)
    {
        Tessellator tessellator = Tessellator.field_78398_a;

        tessellator.func_78382_b();
        tessellator.func_78370_a(r, g, b, a);

        tessellator.func_78377_a(x1, y2, z1);
        tessellator.func_78377_a(x1, y1, z2);
        tessellator.func_78377_a(x2, y1, z2);
        tessellator.func_78377_a(x2, y2, z1);

        tessellator.func_78381_a();
    }

    public static void drawRectangleEW(double x1, double y1, double z1, double x2, double y2, double z2, int r, int g, int b, int a)
    {
        Tessellator tessellator = Tessellator.field_78398_a;

        tessellator.func_78382_b();
        tessellator.func_78370_a(r, g, b, a);

        tessellator.func_78377_a(x1, y1, z1);
        tessellator.func_78377_a(x1, y1, z2);
        tessellator.func_78377_a(x2, y2, z2);
        tessellator.func_78377_a(x2, y2, z1);

        tessellator.func_78381_a();
    }

}
