package powercyphe.festive_frenzy.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public interface VelocityBasedRotationImpl {

    Vec3 getRandomRotationMultipliers();

    void setLastRotation(Vec3 lastRotation);
    Vec3 getLastRotation();

    void setRotation(Vec3 rotation);
    Vec3 getRotation();

    default void updateRotation(Vec3 velocity) {
        this.setLastRotation(this.getRotation());
        for (Direction.Axis axis : Direction.Axis.values()) {
            this.setRotation(this.getRotation().with(axis, this.getLastRotation().get(axis) + (((
                    Math.abs(velocity.get(axis)) +
                            Math.abs(velocity.get(
                                    switch (axis) {
                                        case X -> Direction.Axis.Y;
                                        case Y -> Direction.Axis.Z;
                                        case Z -> Direction.Axis.X;
                                    })))
                    / 2F) * this.getRandomRotationMultipliers().get(axis))));
        }
    }
}
