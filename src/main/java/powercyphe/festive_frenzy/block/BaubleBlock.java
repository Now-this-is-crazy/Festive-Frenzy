package powercyphe.festive_frenzy.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import powercyphe.festive_frenzy.registry.ModItems;
import powercyphe.festive_frenzy.registry.ModSounds;
import powercyphe.festive_frenzy.registry.ModTags;

public class BaubleBlock extends Block {
    public static BooleanProperty GLOWING = BooleanProperty.of("glowing");

    public BaubleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GLOWING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GLOWING);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        return canPlaceAt(state, world, blockPos) ? state : Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isIn(ModTags.BAUBLE_PLACEABLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(VoxelShapes.cuboid(0.3, 0.35, 0.3, 0.7, 0.8, 0.7), VoxelShapes.cuboid(0.4, 0.8, 0.4, 0.6, 1, 0.6));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isOf(Items.GLOW_INK_SAC) && !state.get(GLOWING)) {
                world.setBlockState(blockPos, state.with(GLOWING, true));
                world.playSound(null, blockPos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1f, 1f);
                player.swingHand(hand, true);
            }
        }
        return super.onUse(state, world, blockPos, player, hand, hit);
    }
}
