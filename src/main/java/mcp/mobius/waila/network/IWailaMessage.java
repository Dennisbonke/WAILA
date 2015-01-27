package mcp.mobius.waila.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaMessage {

    public abstract void encodeInto(ChannelHandlerContext paramChannelHandlerContext, IWailaMessage paramIWailaMessage, ByteBuf paramByteBuf)
            throws Exception;

    public abstract void decodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, IWailaMessage paramIWailaMessage);

}
