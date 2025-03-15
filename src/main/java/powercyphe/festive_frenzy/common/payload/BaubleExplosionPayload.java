package powercyphe.festive_frenzy.common.payload;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.List;

public record BaubleExplosionPayload(Vec3d pos, float radius, List<BlockPos> affectedBlocks, Vec3d playerVelocity, ItemStack bauble) implements CustomPayload {
    public static final CustomPayload.Id<BaubleExplosionPayload> ID = new CustomPayload.Id<>(FestiveFrenzy.id("bauble_explosion_s2c"));
    public static final PacketCodec<RegistryByteBuf, BaubleExplosionPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.codec(Vec3d.CODEC), BaubleExplosionPayload::pos, PacketCodecs.FLOAT, BaubleExplosionPayload::radius,
            BlockPos.PACKET_CODEC.collect(PacketCodecs.toList()), BaubleExplosionPayload::affectedBlocks, PacketCodecs.codec(Vec3d.CODEC), BaubleExplosionPayload::playerVelocity, ItemStack.PACKET_CODEC, BaubleExplosionPayload::bauble,
            BaubleExplosionPayload::new
    );


    public BaubleExplosionPayload(Vec3d pos, float radius, List<BlockPos> affectedBlocks, @Nullable Vec3d playerVelocity, @Nullable ItemStack bauble) {
        this.pos = pos;
        this.radius = radius;
        this.affectedBlocks = Lists.newArrayList(affectedBlocks);
        this.playerVelocity = playerVelocity == null ? Vec3d.ZERO : playerVelocity;
        this.bauble = bauble;
    }

    public Vec3d getPlayerVelocity() {
        return this.playerVelocity;
    }

    public double getX() {
        return this.pos.getX();
    }

    public double getY() {
        return this.pos.getY();
    }

    public double getZ() {
        return this.pos.getZ();
    }

    public float getRadius() {
        return this.radius;
    }

    public List<BlockPos> getAffectedBlocks() {
        return this.affectedBlocks;
    }

    public ItemStack getBauble() {
        return this.bauble;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }


    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<BaubleExplosionPayload> {
        @Override
        public void receive(BaubleExplosionPayload payload, ClientPlayNetworking.Context context) {
            ClientPlayerEntity clientPlayerEntity = context.player();

            if (clientPlayerEntity != null) {
                BaubleExplosion explosion = new BaubleExplosion(clientPlayerEntity.getWorld(), (Entity) null, payload.getX(), payload.getY(), payload.getZ(), payload.getRadius(), payload.getAffectedBlocks(), payload.getBauble());
                explosion.affectWorld(true);
                clientPlayerEntity.setVelocity(clientPlayerEntity.getVelocity().add((double) payload.getPlayerVelocity().getX(), (double) payload.getPlayerVelocity().getY(), (double) payload.getPlayerVelocity().getZ()));
            }
        }
    }
}
