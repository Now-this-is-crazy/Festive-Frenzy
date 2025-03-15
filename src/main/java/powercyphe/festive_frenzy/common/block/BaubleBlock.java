package powercyphe.festive_frenzy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.block.property.ExplosionModificationProperty;
import powercyphe.festive_frenzy.common.entity.FallingBaubleBlockEntity;
import powercyphe.festive_frenzy.common.item.BaubleBlockItem;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.common.registry.ModTags;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.List;

public class BaubleBlock extends Block {
    public static BooleanProperty GLOWING = BooleanProperty.of("glowing");
    public static IntProperty EXPLOSION_STRENGTH = IntProperty.of("explosion_strength", 0, 8);
    public static ExplosionModificationProperty EXPLOSION_MODIFICATION = ExplosionModificationProperty.of("explosion_modification");

    public BaubleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GLOWING, false).with(EXPLOSION_STRENGTH, 0).with(EXPLOSION_MODIFICATION, BaubleExplosion.ExplosionModification.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GLOWING, EXPLOSION_STRENGTH, EXPLOSION_MODIFICATION);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        world.scheduleBlockTick(blockPos, this, 2);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, blockPos, neighborPos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        if (!canPlaceAt(state, world, blockPos)) {
            Vec3d pos = blockPos.toCenterPos();

            FallingBaubleBlockEntity bauble = new FallingBaubleBlockEntity(world, pos.getX(), pos.getY() - 0.5, pos.getZ(), state, state.get(EXPLOSION_STRENGTH), state.get(EXPLOSION_MODIFICATION));
            world.spawnEntity(bauble);
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        }
        super.scheduledTick(state, world, blockPos, random);
    }


    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isIn(ModTags.Blocks.BAUBLE_PLACEABLE);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        Vec3d pos = hit.getBlockPos().toCenterPos();

        world.setBlockState(hit.getBlockPos(), Blocks.AIR.getDefaultState());
        FallingBaubleBlockEntity bauble = new FallingBaubleBlockEntity(world, pos.getX(), pos.getY() - 0.5, pos.getZ(), state, state.get(EXPLOSION_STRENGTH), state.get(EXPLOSION_MODIFICATION));
        world.spawnEntity(bauble);

        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> stacks = super.getDroppedStacks(state, builder);
        int explosionStrength = state.get(EXPLOSION_STRENGTH);
        BaubleExplosion.ExplosionModification modification = state.get(EXPLOSION_MODIFICATION);

        if (explosionStrength > 0)
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (stack.isIn(ModTags.Items.BAUBLES_TAG)) {
                stack.set(ModItems.Components.EXPLOSION_STRENGTH, explosionStrength);
                stack.set(ModItems.Components.EXPLOSION_MODIFICATION, modification);
                stacks.set(0, stack);
            }
        }
        return stacks;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(VoxelShapes.cuboid(0.3, 0.35, 0.3, 0.7, 0.8, 0.7), VoxelShapes.cuboid(0.4, 0.8, 0.4, 0.6, 1, 0.6));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos, state.with(EXPLOSION_STRENGTH, itemStack.getOrDefault(ModItems.Components.EXPLOSION_STRENGTH, 0))
                .with(EXPLOSION_MODIFICATION, itemStack.getOrDefault(ModItems.Components.EXPLOSION_MODIFICATION, BaubleExplosion.ExplosionModification.NONE)));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            ItemStack stack = player.getMainHandStack();
            if (stack.isOf(Items.GLOW_INK_SAC) && !state.get(GLOWING)) {
                world.setBlockState(blockPos, state.with(GLOWING, true));
                world.playSound(null, blockPos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1f, 1f);
                player.swingHand(Hand.MAIN_HAND, true);
            }
        }
        return super.onUse(state, world, blockPos, player, hit);
    }
}
