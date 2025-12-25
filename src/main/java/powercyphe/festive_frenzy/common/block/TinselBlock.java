package powercyphe.festive_frenzy.common.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import powercyphe.festive_frenzy.common.registry.FFBlocks;

public class TinselBlock extends MultiWallDecorationBlock {

    public TinselBlock(Properties properties) {
        super(properties);
    }

    public static Block fromColor(DyeColor color) {
        return switch (color) {
            case LIGHT_GRAY -> FFBlocks.LIGHT_GRAY_TINSEL;
            case GRAY -> FFBlocks.GRAY_TINSEL;
            case BLACK -> FFBlocks.BLACK_TINSEL;
            case BROWN -> FFBlocks.BROWN_TINSEL;
            case RED -> FFBlocks.RED_TINSEL;
            case ORANGE -> FFBlocks.ORANGE_TINSEL;
            case YELLOW -> FFBlocks.YELLOW_TINSEL;
            case LIME -> FFBlocks.LIME_TINSEL;
            case GREEN -> FFBlocks.GREEN_TINSEL;
            case CYAN -> FFBlocks.CYAN_TINSEL;
            case LIGHT_BLUE -> FFBlocks.LIGHT_BLUE_TINSEL;
            case BLUE -> FFBlocks.BLUE_TINSEL;
            case PURPLE -> FFBlocks.PURPLE_TINSEL;
            case MAGENTA -> FFBlocks.MAGENTA_TINSEL;
            case PINK -> FFBlocks.PINK_TINSEL;
            case null, default -> FFBlocks.WHITE_TINSEL;
        };
    }
}
