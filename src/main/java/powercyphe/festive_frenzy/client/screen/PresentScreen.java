package powercyphe.festive_frenzy.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.menu.PresentMenu;
import powercyphe.festive_frenzy.common.payload.PresentClosePayload;

public class PresentScreen extends AbstractContainerScreen<PresentMenu> {
    private final Identifier texture;

    public PresentScreen(PresentMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.texture = getTexture(menu.getPresentType());
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.addRenderableWidget(new PresentCloseButton(
                this.leftPos + (this.imageWidth / 2) - (PresentCloseButton.WIDTH / 2),
                this.topPos - PresentCloseButton.HEIGHT));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        try {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.texture, k, l, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        } catch (Exception ignored) {
        }
    }

    public Identifier getTexture(String presentType) {
        return FestiveFrenzy.id("textures/gui/container/" + presentType + ".png");
    }

    public class PresentCloseButton extends AbstractButton {
        private static final int WIDTH = 50;
        private static final int HEIGHT = 19;

        private final int u;
        private final int v;

        public PresentCloseButton(int x, int y) {
            super(x, y, WIDTH, HEIGHT, CommonComponents.EMPTY);
            this.u = 0;
            this.v = 256 - this.height;
        }

        @Override
        public void onPress(InputWithModifiers inputWithModifiers) {
            Minecraft client = Minecraft.getInstance();
            if (client.player != null) {
                ClientPlayNetworking.send(new PresentClosePayload());
                client.player.closeContainer();
            }
        }

        @Override
        protected void renderContents(GuiGraphics guiGraphics, int x, int y, float f) {
            int color = this.isHoveredOrFocused() ? 0xDDDDDDFF : -1;
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, PresentScreen.this.texture, this.getX(), this.getY(), this.u, this.v,
                    this.width, this.height, 256, 256, color);
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }
    }
}
