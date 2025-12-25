package powercyphe.festive_frenzy.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.common.item.component.PresentContentsComponent;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFSounds;

import java.util.List;

public class PresentBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PresentBlock> CODEC = simpleCodec((PresentBlock::new));
    public static final BooleanProperty CLOSED = BooleanProperty.create("closed");

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PresentBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CLOSED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLOSED, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public MapCodec<PresentBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.box(0.22, 0, 0.22, 0.78, 0.5625, 0.78);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        return super.getStateForPlacement(context).setValue(CLOSED, stack.getOrDefault(
                FFItems.Components.PRESENT_CONTENTS_COMPONENT, PresentContentsComponent.DEFAULT).closed())
                .setValue(WATERLOGGED, level.getFluidState(blockPos).is(Fluids.WATER));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide()) {
            PresentContentsComponent component = stack.getOrDefault(FFItems.Components.PRESENT_CONTENTS_COMPONENT,
                    PresentContentsComponent.DEFAULT);

            if (!component.stacks().isEmpty() && level.getBlockEntity(blockPos) instanceof PresentBlockEntity present) {
                List<ItemStack> stacks = component.stacks();

                for (int i = 0; i < Math.min(stacks.size(), PresentBlockEntity.SLOTS); i++) {
                    if (!stack.isEmpty()) {
                        present.setItem(i, stacks.get(i).copy());
                    }
                }
            }
        }
        super.setPlacedBy(level, blockPos, state, placer, stack);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean bl) {
        if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof PresentBlockEntity present) {
            Containers.dropContents(level, blockPos, present);
        }
        super.onRemove(state, level, blockPos, newState, bl);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (player.isCrouching()) {
            pickupPresent(level, state, blockPos, player);
            return InteractionResult.SUCCESS;
        } else if (level.getBlockEntity(blockPos) instanceof PresentBlockEntity presentBlock) {
            if (state.getValue(CLOSED)) {
                Containers.dropContents(level, blockPos, presentBlock);

                level.destroyBlock(blockPos, true, player);
                level.playSound(player, blockPos, FFSounds.PRESENT_UNBOX, SoundSource.BLOCKS, 1F, 1F);
                return InteractionResult.SUCCESS;
            } else {
                MenuProvider provider = state.getMenuProvider(level, blockPos);
                if (provider != null) {
                    player.openMenu(provider);

                    player.playSound(FFSounds.PRESENT_OPEN, 1F, 0.9F + RandomSource.create().nextFloat() * 0.2F);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useWithoutItem(state, level, blockPos, player, blockHitResult);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new PresentBlockEntity(blockPos, state);
    }

    public void pickupPresent(Level level, BlockState state, BlockPos blockPos, @Nullable Player player) {
        if (!level.isClientSide()) {
            ItemStack presentItem = this.asItem().getDefaultInstance();

            if (level.getBlockEntity(blockPos) instanceof PresentBlockEntity presentBlock) {
                presentItem.set(FFItems.Components.PRESENT_CONTENTS_COMPONENT, PresentContentsComponent.fromBlock(presentBlock, !presentBlock.isEmpty()));
                presentBlock.clearContent();
            }

            if (player != null) {
                player.getInventory().placeItemBackInInventory(presentItem);
            }
        }
        level.setBlockAndUpdate(blockPos, state.setValue(CLOSED, true));
        level.destroyBlock(blockPos, false, player);
        level.playSound(player, blockPos, FFSounds.PRESENT_PICKUP, SoundSource.BLOCKS, 1F, 0.9F + RandomSource.create().nextFloat() * 0.2F);
    }

    public static Block fromColor(DyeColor color) {
        return switch (color) {
            case LIGHT_GRAY -> FFBlocks.LIGHT_GRAY_PRESENT;
            case GRAY -> FFBlocks.GRAY_PRESENT;
            case BLACK -> FFBlocks.BLACK_PRESENT;
            case BROWN -> FFBlocks.BROWN_PRESENT;
            case RED -> FFBlocks.RED_PRESENT;
            case ORANGE -> FFBlocks.ORANGE_PRESENT;
            case YELLOW -> FFBlocks.YELLOW_PRESENT;
            case LIME -> FFBlocks.LIME_PRESENT;
            case GREEN -> FFBlocks.GREEN_PRESENT;
            case CYAN -> FFBlocks.CYAN_PRESENT;
            case LIGHT_BLUE -> FFBlocks.LIGHT_BLUE_PRESENT;
            case BLUE -> FFBlocks.BLUE_PRESENT;
            case PURPLE -> FFBlocks.PURPLE_PRESENT;
            case MAGENTA -> FFBlocks.MAGENTA_PRESENT;
            case PINK -> FFBlocks.PINK_PRESENT;
            case null, default -> FFBlocks.WHITE_PRESENT;
        };
    }
}
