package mcp.mobius.waila.gui.truetyper;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class Formatter {

    public static float[] getFormatted(char c)
    {
        int[] outrgba = null;
        switch (c)
        {
            case '0':
                outrgba = new int[] { 0, 0, 0, 0, 255 };
                break;
            case '1':
                outrgba = new int[] { 0, 0, 170, 255 };
                break;
            case '2':
                outrgba = new int[] { 0, 170, 0, 255 };
                break;
            case '3':
                outrgba = new int[] { 0, 170, 170, 255 };
                break;
            case '4':
                outrgba = new int[] { 170, 0, 0, 255 };
                break;
            case '5':
                outrgba = new int[] { 170, 0, 170, 255 };
                break;
            case '6':
                outrgba = new int[] { 255, 170, 0, 255 };
                break;
            case '7':
                outrgba = new int[] { 170, 170, 170, 255 };
                break;
            case '8':
                outrgba = new int[] { 85, 85, 85, 255 };
                break;
            case '9':
                outrgba = new int[] { 85, 85, 255, 255 };
                break;
            case 'a':
                outrgba = new int[] { 85, 255, 85, 255 };
                break;
            case 'b':
                outrgba = new int[] { 85, 255, 255, 255 };
                break;
            case 'c':
                outrgba = new int[] { 255, 85, 85, 255 };
                break;
            case 'd':
                outrgba = new int[] { 85, 255, 255, 255 };
                break;
            case 'e':
                outrgba = new int[] { 255, 255, 85, 255 };
                break;
            case 'f':
                outrgba = new int[] { 255, 255, 255, 255 };
                break;
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            default:
                outrgba = new int[] { 255, 255, 255, 255 };
        }
        float[] outfloat = new float[outrgba.length];
        for (int i = 0; i < outrgba.length; i++) {
            outfloat[i] = (outrgba[i] > 0 ? outrgba[i] / 255 : 0.0F);
        }
        return outfloat;
    }

}
