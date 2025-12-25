package powercyphe.festive_frenzy.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import powercyphe.festive_frenzy.common.registry.FFBlocks;

import java.util.ArrayList;
import java.util.List;

public class StarDecorationBlockEntity extends BlockEntity {
    private final List<BlockPos> particlePoses;

    private boolean isLoading;
    private final List<BlockPos> loadQueue = new ArrayList<>();
    private final List<BlockPos> loadChecks = new ArrayList<>();

    private static final int TICKS_UNTIL_REFRESH = 300;
    private int ticks;

    public StarDecorationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FFBlocks.Entities.STAR_DECORATION_BLOCK_ENTITY, blockPos, blockState);
        this.particlePoses = new ArrayList<>();
        this.ticks = RandomSource.create().nextInt(TICKS_UNTIL_REFRESH);
    }

    public static void staticTick(Level level, BlockPos blockPos, BlockState state, StarDecorationBlockEntity block) {
        block.tick(level, state, blockPos);
    }

    public void tick(Level level, BlockState state, BlockPos blockPos) {
        if (level.isClientSide()) {
            BlockPos rootPos = blockPos.below();
            BlockState rootState = level.getBlockState(rootPos);

            if (this.isLoading) {

                // Check 10 Blocks per tick
                for (int index = 0; index < 10; index++) {
                    if (this.loadQueue.size() > index) {
                        BlockPos checkPos = this.loadQueue.removeFirst();
                        this.findAndAdd(checkPos, rootState, rootPos);
                    } else if (this.loadQueue.isEmpty()) {

                        // If Queue is empty, stop loading process & reset refresh ticks
                        this.isLoading = false;
                        this.ticks = RandomSource.create().nextInt(100);
                        break;
                    }
                }
            } else {
                this.ticks++;
                if (this.ticks > TICKS_UNTIL_REFRESH) {
                    this.prepareReload(rootState, rootPos);
                }
            }
        }
    }

    public List<BlockPos> getParticlePoses() {
        return this.particlePoses;
    }

    public void prepareReload(BlockState rootState, BlockPos rootPos) {
        this.isLoading = true;
        this.particlePoses.clear();
        this.loadQueue.clear();
        this.loadChecks.clear();

        this.findAndAdd(rootPos, rootState, rootPos);
    }

    public void findAndAdd(BlockPos checkPos, BlockState rootState, BlockPos rootPos) {
        LevelReader levelReader = this.getLevel();

        if (levelReader != null) {
            for (Direction direction : Direction.values()) {
                for (Direction otherDirection : Direction.values()) {
                    BlockPos neighborPos = checkPos.relative(direction).relative(otherDirection, direction == otherDirection ? 0 : 1);
                    BlockState neighborState = levelReader.getBlockState(neighborPos);

                    // Check if in range & if it hasn't been checked before
                    if (rootPos.closerThan(neighborPos, 10) && neighborState.is(rootState.getBlock()) && !this.loadChecks.contains(neighborPos)) {
                        this.loadChecks.add(neighborPos);

                        // If Exposed, add it to the Queue and get nearby partile poses
                        if (isBlockExposed(levelReader, neighborState, neighborPos)) {
                            this.addParticlePoses(neighborState, neighborPos);
                            this.loadQueue.add(neighborPos);
                        }
                    }
                }
            }
        }
    }

    public void addParticlePoses(BlockState state, BlockPos blockPos) {
        LevelReader levelReader = this.getLevel();

        for (Direction direction : Direction.values()) {
            BlockPos nextPos = blockPos.relative(direction);
            BlockState nextState = levelReader.getBlockState(nextPos);

            if (!this.particlePoses.contains(nextPos) && (nextState.canOcclude() || nextState.isAir())) {
                this.particlePoses.add(nextPos);
            }
        }
    }

    public static boolean isBlockExposed(LevelReader levelReader, BlockState state, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            BlockPos nextPos = blockPos.relative(direction);
            BlockState nextState = levelReader.getBlockState(nextPos);

            if (nextState.isAir() || !nextState.isCollisionShapeFullBlock(levelReader, nextPos)) {
                return true;
            }
        }
        return false;
    }
}
