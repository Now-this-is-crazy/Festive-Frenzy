package powercyphe.festive_frenzy.client.gui.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix4f;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class ExplosiveBaubleTooltipData implements TooltipComponent, ClientTooltipComponent {
    private static final String TEXTURE_PATH = "textures/gui/sprites/";
    private static final ResourceLocation DETAILS_TEXTURE = FestiveFrenzy.id(TEXTURE_PATH + "explosive_bauble_details.png");

    private static final int BASE_HEIGHT = 26;
    private static final int BASE_WIDTH = 20;

    private final int explosionPower;
    private final BaubleExplosion.ExplosionModification explosionModification;

    public ExplosiveBaubleTooltipData(ExplosiveBaubleComponent component) {
        this.explosionPower = component.explosionPower();
        this.explosionModification = component.explosionModification();
    }

    @Override
    public int getHeight(Font font) {
        return this.shouldShowTooltip() ? BASE_HEIGHT : 0;
    }

    @Override
    public int getWidth(Font font) {
        return this.shouldShowTooltip() ?
                BASE_WIDTH + Math.max(
                        font.width(getExplosionPowerText()),
                        font.width(getExplosionModificationText())
                ) : 0;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
        if (this.shouldShowTooltip()) {
            font.drawInBatch(this.getExplosionPowerText(), x + 20, y, -1, false,
                    matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
            font.drawInBatch(this.getExplosionModificationText(), x + 20, y + 16, -1, false,
                    matrix4f, bufferSource, Font.DisplayMode.NORMAL, -1, LightTexture.FULL_BRIGHT);
        }
    }

    @Override
    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics guiGraphics) {
        if (this.shouldShowTooltip()) {
            guiGraphics.blit(RenderType::guiTextured, DETAILS_TEXTURE, x, y - 1, 0, 0, 16, 24, 16, 24);

            if (this.explosionModification != BaubleExplosion.ExplosionModification.NONE) {
                guiGraphics.blit(RenderType::guiTextured, FestiveFrenzy.id(TEXTURE_PATH + "explosion_modification_" + this.explosionModification.getName() + ".png"),
                        x + 2, y + BASE_HEIGHT / 2 - 6, 0, 0, 8, 8, 8, 8);
            }
        }
    }

    private boolean shouldShowTooltip() {
        return this.explosionPower > 0;
    }

    private Component getExplosionPowerText() {
        return Component.translatable("tooltip.festive_frenzy.explosion_power")
                .append(Component.translatable("tooltip.festive_frenzy.explosion_power." + this.explosionPower));
    }

    private Component getExplosionModificationText() {
        return Component.translatable("tooltip.festive_frenzy.explosion_modification")
                .append(Component.translatable(this.explosionModification.getTranslationKey()));
    }
}
