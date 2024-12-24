package powercyphe.festive_frenzy.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
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
import powercyphe.festive_frenzy.item.PresentBlockItem;
import powercyphe.festive_frenzy.registry.ModSounds;

import java.util.List;

public class PresentBlock extends BlockWithEntity {
    public PresentBlock(Settings settings) {
        super(settings);
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
            ItemScatterer.spawn(world, pos, presentBlockEntity.getInventory());
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!world.isClient()) {
            DefaultedList<ItemStack> items = PresentBlockItem.getStoredItems(stack);
            if (items != null) {
                if (world.getBlockEntity(pos) instanceof PresentBlockEntity presentBlockEntity) {
                    presentBlockEntity.setInventory(items);
                }
            }
        }
        super.onPlaced(world, pos, state, placer, stack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && player.getStackInHand(hand).isEmpty()) {
            Vec3d pos = blockPos.toCenterPos();
            world.breakBlock(blockPos, true, player);
            world.playSound(null, blockPos, ModSounds.PRESENT_OPEN, SoundCategory.BLOCKS, 1f, 0.9f + (Random.create().nextInt(4) * 0.05f) );
            ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, pos.getX(), pos.getY(), pos.getZ(), 7, 0, 0, 0, 0.05);
            player.swingHand(hand, true);
        }
        return super.onUse(state, world, blockPos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PresentBlockEntity(pos, state);
    }
}
