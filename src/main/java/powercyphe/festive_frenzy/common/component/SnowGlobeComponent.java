package powercyphe.festive_frenzy.common.component;

public class SnowGlobeComponent /* implements CommonTickingComponent, AutoSyncedComponent */ {

    /*
    public World world;
    public DefaultedList<BlockPos> snowGlobeList;

    public int updateTick = 0;

    public SnowGlobeComponent(World world) {
        this.world = world;
        this.snowGlobeList = DefaultedList.of();
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.updateTick = nbtCompound.getInt("updateTick");

        this.snowGlobeList = DefaultedList.of();
        nbtCompound.getList("snowGlobes", 10).forEach((nbtElement -> {
            if (nbtElement instanceof NbtCompound compound) {
                int[] ints = compound.getIntArray("blockPos");
                this.snowGlobeList.add(new BlockPos(ints[0], ints[1], ints[2]));
            }
        }));
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("updateTick", this.updateTick);

        NbtList nbtList = new NbtList();
        for (BlockPos blockPos : this.snowGlobeList) {
            NbtCompound compound = new NbtCompound();
            int[] ints = new int[]{blockPos.getX(), blockPos.getY(), blockPos.getZ()};
            compound.putIntArray("blockPos", ints);
            nbtList.add(compound);
        }
        nbtCompound.put("snowGlobes", nbtList);
    }

    @Override
    public void serverTick() {
        this.updateTick++;
        if (this.updateTick >= 20) {
            this.updateTick = 0;

            DefaultedList<BlockPos> toRemove = DefaultedList.of();
            for (BlockPos blockPos : this.snowGlobeList) {
                if (this.world.isChunkLoaded(blockPos) && !(this.world.getBlockState(blockPos).isOf(ModBlocks.SNOW_GLOBE))) {
                    toRemove.add(blockPos);
                }
            }
            toRemove.forEach(this::removeSnowGlobe);
            ModComponents.SNOWGLOBE.sync(world);

        }
    }

    @Override
    public void tick() {}

    public void addSnowGlobe(BlockPos blockPos) {
        if (!this.snowGlobeList.contains(blockPos)) {
            this.snowGlobeList.add(blockPos);
            ModComponents.SNOWGLOBE.sync(world);
        }
    }

    public void removeSnowGlobe(BlockPos blockPos) {
        this.snowGlobeList.remove(blockPos);
        ModComponents.SNOWGLOBE.sync(world);
    }
     */
}
