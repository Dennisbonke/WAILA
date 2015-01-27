package mcp.mobius.waila.gui.events;

import mcp.mobius.waila.gui.interfaces.IWidget;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class MouseEvent {

    public long timestamp;
    public Minecraft mc;
    public IWidget srcwidget;
    public IWidget trgwidget;
    public double x;
    public double y;
    public int z;

    public static enum EventType
    {
        NONE,  MOVE,  CLICK,  RELEASED,  DRAG,  WHEEL,  ENTER,  LEAVE;

        private EventType() {}
    }

    public static int buttonCount = ;
    public boolean[] buttonState = new boolean[buttonCount];
    public EventType type;
    public int button = -1;

    public MouseEvent(IWidget widget)
    {
        this.srcwidget = widget;
        this.timestamp = System.nanoTime();

        this.mc = Minecraft.func_71410_x();

        this.x = (Mouse.getEventX() * this.srcwidget.getSize().getX() / this.mc.field_71443_c);
        this.y = (this.srcwidget.getSize().getY() - Mouse.getEventY() * this.srcwidget.getSize().getY() / this.mc.field_71440_d - 1.0D);







        this.z = Mouse.getDWheel();
        for (int i = 0; i < buttonCount; i++) {
            this.buttonState[i] = Mouse.isButtonDown(i);
        }
        this.trgwidget = this.srcwidget.getWidgetAtCoordinates(this.x, this.y);
    }

    public String toString()
    {
        String retstring = String.format("MOUSE %s :  [%s] [ %.2f %.2f %d ] [", new Object[] { this.type, Long.valueOf(this.timestamp), Double.valueOf(this.x), Double.valueOf(this.y), Integer.valueOf(this.z) });
        if (buttonCount < 5) {
            for (int i = 0; i < buttonCount; i++) {
                retstring = retstring + String.format(" %s ", new Object[] { Boolean.valueOf(this.buttonState[i]) });
            }
        } else {
            for (int i = 0; i < 5; i++) {
                retstring = retstring + String.format(" %s ", new Object[] { Boolean.valueOf(this.buttonState[i]) });
            }
        }
        retstring = retstring + "]";
        if (this.button != -1) {
            retstring = retstring + String.format(" Button %s", new Object[] { Integer.valueOf(this.button) });
        }
        return retstring;
    }

    public EventType getEventType(MouseEvent me)
    {
        this.type = EventType.NONE;
        if (this.trgwidget != me.trgwidget)
        {
            this.type = EventType.ENTER;
            return this.type;
        }
        if (this.z != 0)
        {
            this.type = EventType.WHEEL;
            return this.type;
        }
        for (int i = 0; i < buttonCount; i++) {
            if (this.buttonState[i] != me.buttonState[i])
            {
                if (this.buttonState[i] == 1) {
                    this.type = EventType.CLICK;
                } else {
                    this.type = EventType.RELEASED;
                }
                this.button = i;
                return this.type;
            }
        }
        if ((this.x != me.x) || (this.y != me.y))
        {
            if (this.buttonState[0] == 1) {
                this.type = EventType.DRAG;
            } else {
                this.type = EventType.MOVE;
            }
            return this.type;
        }
        return this.type;
    }

}
