package powercyphe.festive_frenzy.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.item.PresentBlockItem;
import powercyphe.festive_frenzy.registry.ModNetworking;
import powercyphe.festive_frenzy.registry.ModSounds;
import powercyphe.festive_frenzy.util.PresentTypeAccessor;

import java.util.List;

public class PresentBlock extends BlockWithEntity {
    public static BooleanProperty CLOSED = BooleanProperty.of("closed");

    public PresentBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(CLOSED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLOSED);
        super.appendProperties(builder);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PresentBlock::new);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.2f, 0.0f, 0.2f, 0.8f, 0.55f, 0.8f);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof PresentBlockEntity presentBlockEntity) {
            if (!(state.getBlock() == newState.getBlock())) {
                ItemScatterer.spawn(world, pos, presentBlockEntity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        ItemStack stack = ctx.getStack();

        if (state != null && PresentBlockItem.isClosed(stack)) {
            state = state.with(CLOSED, true);
        }
        return state;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!world.isClient()) {
            DefaultedList<ItemStack> items = PresentBlockItem.getStoredItems(stack);
            if (items != null && items.size() <= 9) {
                if (world.getBlockEntity(pos) instanceof PresentBlockEntity presentBlockEntity) {
                    while (items.size() < 9) {
                        items.add(ItemStack.EMPTY);
                    }
                    presentBlockEntity.setInventory(items);
                }
            }
        }
        super.onPlaced(world, pos, state, placer, stack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) ((PresentTypeAccessor) player).festive_frenzy$setPresent(Registries.BLOCK.getId(this).getPath());
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof PresentBlockEntity presentBlockEntity) {
                if (state.get(CLOSED) && player.getStackInHand(hand).isEmpty()) {
                    // Present Unboxing
                    Vec3d pos = blockPos.toCenterPos();

                    ItemScatterer.spawn(world, blockPos, presentBlockEntity);
                    presentBlockEntity.clear();
                    world.breakBlock(blockPos, true, player);
                    world.playSound(null, blockPos, ModSounds.PRESENT_UNBOX, SoundCategory.BLOCKS, 1f, 0.9f + (Random.create().nextInt(4) * 0.05f));

                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, pos.getX(), pos.getY(), pos.getZ(), 7, 0, 0, 0, 0.05);
                    player.swingHand(hand, true);
                    return ActionResult.SUCCESS;

                } else if (!state.get(CLOSED)) {
                    if (!player.isSneaking()) {
                        // Opening Present Screen
                        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, blockPos);
                        if (screenHandlerFactory != null) {
                            PacketByteBuf packet = PacketByteBufs.create();
                            packet.writeString(Registries.BLOCK.getId(this).getPath());
                            ServerPlayNetworking.send((ServerPlayerEntity) player, ModNetworking.PRESENT_TYPE_PACKET, packet);

                            player.openHandledScreen(screenHandlerFactory);
                        }
                        world.playSound(null, blockPos, ModSounds.PRESENT_OPEN, SoundCategory.BLOCKS, 1f, 1f + (Random.create().nextInt(4) * 0.025f));
                        player.swingHand(hand, true);
                        return ActionResult.SUCCESS;

                    } else {
                        // Closing Present
                        closePresent(world, presentBlockEntity, state, blockPos, player);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.CONSUME;
    }

    public static void closePresent(World world, PresentBlockEntity presentBlockEntity, BlockState state, BlockPos blockPos, PlayerEntity player) {
        ItemStack stack = state.getBlock().asItem().getDefaultStack();

        if (!presentBlockEntity.isEmpty()) {
            PresentBlockItem.setClosed(stack, true);
            PresentBlockItem.setStoredItems(stack, presentBlockEntity.getInventory());
            presentBlockEntity.clear();
        }

        player.getInventory().offerOrDrop(stack);
        world.breakBlock(blockPos, false, player);

        world.playSound(null, blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1.4f + (Random.create().nextInt(4) * 0.025f));
        player.swingHand(Hand.MAIN_HAND, true);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> drops = super.getDroppedStacks(state, builder);
        if (state.get(PresentBlock.CLOSED)) {
            for (int i = 0; i < drops.size(); i++) {
                ItemStack stack = drops.get(i);
                if (stack.getItem() instanceof PresentBlockItem && builder.get(LootContextParameters.BLOCK_ENTITY) instanceof PresentBlockEntity presentBlock) {
                    PresentBlockItem.setStoredItems(stack, presentBlock.getInventory());
                }
            }
        }
        return drops;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PresentBlockEntity(pos, state);
    }
}
