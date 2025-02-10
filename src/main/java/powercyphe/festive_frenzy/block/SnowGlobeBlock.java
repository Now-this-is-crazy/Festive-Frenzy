package powercyphe.festive_frenzy.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.registry.ModComponents;

public class SnowGlobeBlock extends Block {
    public static int MIN = 1;
    public static int MAX = 3;
    public static IntProperty TYPE = IntProperty.of("type", MIN, MAX);

    public SnowGlobeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(VoxelShapes.cuboid(0.2, 0, 0.2, 0.8, 0.65, 0.8));
    }

    /*

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ModComponents.SNOWGLOBE.get(world).addSnowGlobe(pos);
        super.randomTick(state, world, pos, random);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        ModComponents.SNOWGLOBE.get(world).addSnowGlobe(pos);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            ModComponents.SNOWGLOBE.get(world).removeSnowGlobe(pos);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            int type = state.get(TYPE) + 1 > MAX ? MIN : state.get(TYPE) + 1;
            world.setBlockState(blockPos, state.with(TYPE, type));

            player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 0.5f, 1.5f);
            player.swingHand(hand);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
     */
}
