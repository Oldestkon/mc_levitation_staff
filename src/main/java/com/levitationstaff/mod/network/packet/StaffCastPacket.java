package com.levitationstaff.mod.network.packet;

import com.levitationstaff.mod.client.StaffAnimationHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StaffCastPacket {
    private final boolean successful;

    public StaffCastPacket(boolean successful) {
        this.successful = successful;
    }

    public StaffCastPacket(FriendlyByteBuf buf) {
        this.successful = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.successful);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                StaffAnimationHandler.handleCastAnimation(this.successful);
            });
        });
        ctx.get().setPacketHandled(true);
    }
} 