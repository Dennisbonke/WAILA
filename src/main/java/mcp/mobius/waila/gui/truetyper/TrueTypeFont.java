package mcp.mobius.waila.gui.truetyper;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class TrueTypeFont {

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_CENTER = 2;
    private FloatObject[] charArray = new FloatObject[256];
    private Map customChars = new HashMap();
    protected boolean antiAlias;
    private float fontSize = 0.0F;
    private float fontHeight = 0.0F;
    private int fontTextureID;
    private int textureWidth = 1024;
    private int textureHeight = 1024;
    protected Font font;
    private FontMetrics fontMetrics;
    private int correctL = 9;
    private int correctR = 8;

    public TrueTypeFont(Font font, boolean antiAlias, char[] additionalChars)
    {
        this.font = font;
        this.fontSize = (font.getSize() + 3);
        this.antiAlias = antiAlias;

        createSet(additionalChars);
        System.out.println("TrueTypeFont loaded: " + font + " - AntiAlias = " + antiAlias);
        this.fontHeight -= 1.0F;
        if (this.fontHeight <= 0.0F) {
            this.fontHeight = 1.0F;
        }
    }

    public TrueTypeFont(Font font, boolean antiAlias)
    {
        this(font, antiAlias, null);
    }

    public void setCorrection(boolean on)
    {
        if (on)
        {
            this.correctL = 2;
            this.correctR = 1;
        }
        else
        {
            this.correctL = 0;
            this.correctR = 0;
        }
    }

    private BufferedImage getFontImage(char ch)
    {
        BufferedImage tempfontImage = new BufferedImage(1, 1, 2);

        Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
        if (this.antiAlias == true) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(this.font);
        this.fontMetrics = g.getFontMetrics();
        float charwidth = this.fontMetrics.charWidth(ch) + 8;
        if (charwidth <= 0.0F) {
            charwidth = 7.0F;
        }
        float charheight = this.fontMetrics.getHeight() + 3;
        if (charheight <= 0.0F) {
            charheight = this.fontSize;
        }
        BufferedImage fontImage = new BufferedImage((int)charwidth, (int)charheight, 2);

        Graphics2D gt = (Graphics2D)fontImage.getGraphics();
        if (this.antiAlias == true) {
            gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        gt.setFont(this.font);

        gt.setColor(Color.WHITE);
        int charx = 3;
        int chary = 1;
        gt.drawString(String.valueOf(ch), charx, chary + this.fontMetrics
                .getAscent());

        return fontImage;
    }

    private void createSet(char[] customCharsArray)
    {
        if ((customCharsArray != null) && (customCharsArray.length > 0)) {
            this.textureWidth *= 2;
        }
        try
        {
            BufferedImage imgTemp = new BufferedImage(this.textureWidth, this.textureHeight, 2);
            Graphics2D g = (Graphics2D)imgTemp.getGraphics();

            g.setColor(new Color(0, 0, 0, 1));
            g.fillRect(0, 0, this.textureWidth, this.textureHeight);

            float rowHeight = 0.0F;
            float positionX = 0.0F;
            float positionY = 0.0F;

            int customCharsLength = customCharsArray != null ? customCharsArray.length : 0;
            for (int i = 0; i < 256 + customCharsLength; i++)
            {
                char ch = i < 256 ? (char)i : customCharsArray[(i - 256)];

                BufferedImage fontImage = getFontImage(ch);

                FloatObject newIntObject = new FloatObject(null);

                newIntObject.width = fontImage.getWidth();
                newIntObject.height = fontImage.getHeight();
                if (positionX + newIntObject.width >= this.textureWidth)
                {
                    positionX = 0.0F;
                    positionY += rowHeight;
                    rowHeight = 0.0F;
                }
                newIntObject.storedX = positionX;
                newIntObject.storedY = positionY;
                if (newIntObject.height > this.fontHeight) {
                    this.fontHeight = newIntObject.height;
                }
                if (newIntObject.height > rowHeight) {
                    rowHeight = newIntObject.height;
                }
                g.drawImage(fontImage, (int)positionX, (int)positionY, null);

                positionX += newIntObject.width;
                if (i < 256) {
                    this.charArray[i] = newIntObject;
                } else {
                    this.customChars.put(new Character(ch), newIntObject);
                }
                fontImage = null;
            }
            this.fontTextureID = loadImage(imgTemp);
        }
        catch (Exception e)
        {
            System.err.println("Failed to create font.");
            e.printStackTrace();
        }
    }

    private void drawQuad(float drawX, float drawY, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2)
    {
        float DrawWidth = drawX2 - drawX;
        float DrawHeight = drawY2 - drawY;
        float TextureSrcX = srcX / this.textureWidth;
        float TextureSrcY = srcY / this.textureHeight;
        float SrcWidth = srcX2 - srcX;
        float SrcHeight = srcY2 - srcY;
        float RenderWidth = SrcWidth / this.textureWidth;
        float RenderHeight = SrcHeight / this.textureHeight;
        Tessellator t = Tessellator.field_78398_a;



        t.func_78374_a(drawX, drawY, 0.0D, TextureSrcX, TextureSrcY);



        t.func_78374_a(drawX, drawY + DrawHeight, 0.0D, TextureSrcX, TextureSrcY + RenderHeight);



        t.func_78374_a(drawX + DrawWidth, drawY + DrawHeight, 0.0D, TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);



        t.func_78374_a(drawX + DrawWidth, drawY, 0.0D, TextureSrcX + RenderWidth, TextureSrcY);
    }

    public float getWidth(String whatchars)
    {
        float totalwidth = 0.0F;
        FloatObject floatObject = null;
        int currentChar = 0;
        float lastWidth = -10.0F;
        for (int i = 0; i < whatchars.length(); i++)
        {
            currentChar = whatchars.charAt(i);
            if (currentChar < 256) {
                floatObject = this.charArray[currentChar];
            } else {
                floatObject = (FloatObject)this.customChars.get(new Character((char)currentChar));
            }
            if (floatObject != null)
            {
                totalwidth += floatObject.width / 2.0F;
                lastWidth = floatObject.width;
            }
        }
        return this.fontMetrics.stringWidth(whatchars);
    }

    public float getHeight()
    {
        return this.fontHeight;
    }

    public float getHeight(String HeightString)
    {
        return this.fontHeight;
    }

    public float getLineHeight()
    {
        return this.fontHeight;
    }

    public void drawString(float x, float y, String whatchars, float scaleX, float scaleY, float... rgba)
    {
        if (rgba.length == 0) {
            rgba = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
        }
        drawString(x, y, whatchars, 0, whatchars.length() - 1, scaleX, scaleY, 0, rgba);
    }

    public void drawString(float x, float y, String whatchars, float scaleX, float scaleY, int format, float... rgba)
    {
        if (rgba.length == 0) {
            rgba = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
        }
        drawString(x, y, whatchars, 0, whatchars.length() - 1, scaleX, scaleY, format, rgba);
    }

    public void drawString(float x, float y, String whatchars, int startIndex, int endIndex, float scaleX, float scaleY, int format, float... rgba)
    {
        if (rgba.length == 0) {
            rgba = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
        }
        GL11.glPushMatrix();
        GL11.glScalef(scaleX, scaleY, 1.0F);

        FloatObject floatObject = null;



        float totalwidth = 0.0F;
        int i = startIndex;
        float startY = 0.0F;
        int c;
        switch (format)
        {
            case 1:
                int d = -1;
                c = this.correctR;
        }
        int d;
        int c;
        while (i < endIndex)
        {
            if (whatchars.charAt(i) == '\n') {
                startY -= this.fontHeight;
            }
            i++; continue;
            for (int l = startIndex; l <= endIndex; l++)
            {
                int charCurrent = whatchars.charAt(l);
                if (charCurrent == 10) {
                    break;
                }
                if (charCurrent < 256) {
                    floatObject = this.charArray[charCurrent];
                } else {
                    floatObject = (FloatObject)this.customChars.get(new Character((char)charCurrent));
                }
                totalwidth += floatObject.width - this.correctL;
            }
            totalwidth /= -2.0F;



            d = 1;
            c = this.correctL;
        }
        GL11.glBindTexture(3553, this.fontTextureID);
        Tessellator t = Tessellator.field_78398_a;
        t.func_78382_b();
        if (rgba.length == 4) {
            t.func_78369_a(rgba[0], rgba[1], rgba[2], rgba[3]);
        }
        while ((i >= startIndex) && (i <= endIndex))
        {
            int charCurrent = whatchars.charAt(i);
            if (charCurrent < 256) {
                floatObject = this.charArray[charCurrent];
            } else {
                floatObject = (FloatObject)this.customChars.get(new Character((char)charCurrent));
            }
            if (floatObject != null)
            {
                if (d < 0) {
                    totalwidth += (floatObject.width - c) * d;
                }
                if (charCurrent == 10)
                {
                    startY -= this.fontHeight * d;
                    totalwidth = 0.0F;
                    if (format == 2)
                    {
                        for (int l = i + 1; l <= endIndex; l++)
                        {
                            charCurrent = whatchars.charAt(l);
                            if (charCurrent == 10) {
                                break;
                            }
                            if (charCurrent < 256) {
                                floatObject = this.charArray[charCurrent];
                            } else {
                                floatObject = (FloatObject)this.customChars.get(new Character((char)charCurrent));
                            }
                            totalwidth += floatObject.width - this.correctL;
                        }
                        totalwidth /= -2.0F;
                    }
                }
                else
                {
                    drawQuad(totalwidth + floatObject.width + x / scaleX, startY + y / scaleY, totalwidth + x / scaleX, startY + floatObject.height + y / scaleY, floatObject.storedX + floatObject.width, floatObject.storedY + floatObject.height, floatObject.storedX, floatObject.storedY);
                    if (d > 0) {
                        totalwidth += (floatObject.width - c) * d;
                    }
                }
                i += d;
            }
        }
        t.func_78381_a();


        GL11.glPopMatrix();
    }

    public static int loadImage(BufferedImage bufferedImage)
    {
        try
        {
            short width = (short)bufferedImage.getWidth();
            short height = (short)bufferedImage.getHeight();

            int bpp = (byte)bufferedImage.getColorModel().getPixelSize();

            DataBuffer db = bufferedImage.getData().getDataBuffer();
            ByteBuffer byteBuffer;
            if ((db instanceof DataBufferInt))
            {
                int[] intI = ((DataBufferInt)bufferedImage.getData().getDataBuffer()).getData();
                byte[] newI = new byte[intI.length * 4];
                for (int i = 0; i < intI.length; i++)
                {
                    byte[] b = intToByteArray(intI[i]);
                    int newIndex = i * 4;

                    newI[newIndex] = b[1];
                    newI[(newIndex + 1)] = b[2];
                    newI[(newIndex + 2)] = b[3];
                    newI[(newIndex + 3)] = b[0];
                }
                byteBuffer = ByteBuffer.allocateDirect(width * height * (bpp / 8)).order(ByteOrder.nativeOrder()).put(newI);
            }
            else
            {
                byteBuffer = ByteBuffer.allocateDirect(width * height * (bpp / 8)).order(ByteOrder.nativeOrder()).put(((DataBufferByte)bufferedImage.getData().getDataBuffer()).getData());
            }
            byteBuffer.flip();


            int internalFormat = 32856;
            int format = 6408;
            IntBuffer textureId = BufferUtils.createIntBuffer(1);
            GL11.glGenTextures(textureId);
            GL11.glBindTexture(3553, textureId.get(0));

            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);


            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 10241, 9728);







            GL11.glTexEnvf(8960, 8704, 8448.0F);

            GLU.gluBuild2DMipmaps(3553, internalFormat, width, height, format, 5121, byteBuffer);
            return textureId.get(0);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSupported(String fontname)
    {
        Font[] font = getFonts();
        for (int i = font.length - 1; i >= 0; i--) {
            if (font[i].getName().equalsIgnoreCase(fontname)) {
                return true;
            }
        }
        return false;
    }

    public static Font[] getFonts()
    {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    }

    public static byte[] intToByteArray(int value)
    {
        return new byte[] { (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value };
    }

    public void destroy()
    {
        IntBuffer scratch = BufferUtils.createIntBuffer(1);
        scratch.put(0, this.fontTextureID);
        GL11.glBindTexture(3553, 0);
        GL11.glDeleteTextures(scratch);
    }

    private class FloatObject
    {
        public float width;
        public float height;
        public float storedX;
        public float storedY;

        private FloatObject() {}
    }

}
