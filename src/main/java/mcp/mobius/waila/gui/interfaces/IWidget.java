package mcp.mobius.waila.gui.interfaces;

import mcp.mobius.waila.gui.events.MouseEvent;
import org.lwjgl.util.Point;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWidget {

    public abstract IWidget getParent();

    public abstract void setParent(IWidget paramIWidget);

    public abstract IWidget setGeometry(WidgetGeometry paramWidgetGeometry);

    public abstract WidgetGeometry getGeometry();

    public abstract IWidget setPos(double paramDouble1, double paramDouble2);

    public abstract IWidget setPos(double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2);

    public abstract IWidget setSize(double paramDouble1, double paramDouble2);

    public abstract IWidget setSize(double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2);

    public abstract IWidget adjustSize();

    public abstract Point getPos();

    public abstract Point getSize();

    public abstract int getRight();

    public abstract int getLeft();

    public abstract int getTop();

    public abstract int getBottom();

    public abstract void draw();

    public abstract void draw(Point paramPoint);

    public abstract void show();

    public abstract void hide();

    public abstract void setAlpha(float paramFloat);

    public abstract float getAlpha();

    public abstract boolean shouldRender();

    public abstract IWidget addWidget(String paramString, IWidget paramIWidget);

    public abstract IWidget addWidget(String paramString, IWidget paramIWidget, RenderPriority paramRenderPriority);

    public abstract IWidget getWidget(String paramString);

    public abstract IWidget delWidget(String paramString);

    public abstract IWidget getWidgetAtCoordinates(double paramDouble1, double paramDouble2);

    public abstract boolean isWidgetAtCoordinates(double paramDouble1, double paramDouble2);

    public abstract void handleMouseInput();

    public abstract void onMouseClick(MouseEvent paramMouseEvent);

    public abstract void onMouseDrag(MouseEvent paramMouseEvent);

    public abstract void onMouseMove(MouseEvent paramMouseEvent);

    public abstract void onMouseRelease(MouseEvent paramMouseEvent);

    public abstract void onMouseWheel(MouseEvent paramMouseEvent);

    public abstract void onMouseEnter(MouseEvent paramMouseEvent);

    public abstract void onMouseLeave(MouseEvent paramMouseEvent);

    public abstract void emit(Signal paramSignal, Object... paramVarArgs);

    public abstract void onWidgetEvent(IWidget paramIWidget, Signal paramSignal, Object... paramVarArgs);

}
