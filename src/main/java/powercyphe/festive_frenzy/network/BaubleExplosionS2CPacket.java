package powercyphe.festive_frenzy.network;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.registry.ModNetworking;

import java.util.ArrayList;
import java.util.List;

public class BaubleExplosionS2CPacket implements FabricPacket {
    private final double x;
    private final double y;
    private final double z;
    private final float radius;
    private final List<BlockPos> affectedBlocks;
    private final float playerVelocityX;
    private final float playerVelocityY;
    private final float playerVelocityZ;
    private final ItemStack stack;
    private final BaubleBlockItem.ExplosionModification explosionModification;


    public BaubleExplosionS2CPacket(double x, double y, double z, float radius, List<BlockPos> affectedBlocks, @Nullable Vec3d playerVelocity, @Nullable ItemStack stack, BaubleBlockItem.ExplosionModification explosionModification) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.affectedBlocks = Lists.newArrayList(affectedBlocks);
        if (playerVelocity != null) {
            this.playerVelocityX = (float)playerVelocity.x;
            this.playerVelocityY = (float)playerVelocity.y;
            this.playerVelocityZ = (float)playerVelocity.z;
        } else {
            this.playerVelocityX = 0.0F;
            this.playerVelocityY = 0.0F;
            this.playerVelocityZ = 0.0F;
        }
        this.stack = stack;
        this.explosionModification = explosionModification;
    }

    public BaubleExplosionS2CPacket(PacketByteBuf packetByteBuf) {
        this.x = packetByteBuf.readDouble();
        this.y = packetByteBuf.readDouble();
        this.z = packetByteBuf.readDouble();
        this.radius = packetByteBuf.readFloat();
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.y);
        int k = MathHelper.floor(this.z);
        this.affectedBlocks = packetByteBuf.readList((buf2) -> {
            int l = buf2.readByte() + i;
            int m = buf2.readByte() + j;
            int n = buf2.readByte() + k;
            return new BlockPos(l, m, n);
        });
        this.playerVelocityX = packetByteBuf.readFloat();
        this.playerVelocityY = packetByteBuf.readFloat();
        this.playerVelocityZ = packetByteBuf.readFloat();
        this.stack = packetByteBuf.readItemStack();
        this.explosionModification = BaubleBlockItem.ExplosionModification.fromName(packetByteBuf.readString());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.radius);
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.y);
        int k = MathHelper.floor(this.z);
        buf.writeCollection(this.affectedBlocks, (buf2, pos) -> {
            int l = pos.getX() - i;
            int m = pos.getY() - j;
            int n = pos.getZ() - k;
            buf2.writeByte(l);
            buf2.writeByte(m);
            buf2.writeByte(n);
        });
        buf.writeFloat(this.playerVelocityX);
        buf.writeFloat(this.playerVelocityY);
        buf.writeFloat(this.playerVelocityZ);
        if (this.stack != null) {
            buf.writeItemStack(this.stack);
        }
        buf.writeString(this.explosionModification.getName());
    }

    @Override
    public PacketType<?> getType() {
        return ModNetworking.BAUBLE_EXPLOSION_PACKET;
    }

    public float getPlayerVelocityX() {
        return this.playerVelocityX;
    }

    public float getPlayerVelocityY() {
        return this.playerVelocityY;
    }

    public float getPlayerVelocityZ() {
        return this.playerVelocityZ;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getRadius() {
        return this.radius;
    }

    public List<BlockPos> getAffectedBlocks() {
        return this.affectedBlocks;
    }

    public ItemStack getStack() {
        return this.stack;
    }
}
