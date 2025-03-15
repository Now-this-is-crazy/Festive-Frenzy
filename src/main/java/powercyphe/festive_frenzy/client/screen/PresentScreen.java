package powercyphe.festive_frenzy.client.screen;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.payload.PresentClosePayload;
import powercyphe.festive_frenzy.common.screen.PresentScreenHandler;

import java.util.List;

public class PresentScreen extends HandledScreen<PresentScreenHandler> {
    private static final Text CLOSE_PRESENT = Text.translatable("container.festive_frenzy.present.close");
    private static final String PATH = "textures/gui/container/present/";
    private Identifier texture;
    private final List<PressableWidget> buttons = Lists.newArrayList();

    public PresentScreen(PresentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.texture = FestiveFrenzy.id(PATH + handler.present + ".png");
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addButton(new CloseButtonWidget(this.width / 2 - 20, this.y - 17, 0, 179));
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2 - 2;
        context.drawTexture(this.texture, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight + 2);
    }

    private <T extends PressableWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add(button);
    }

    class CloseButtonWidget extends PressableWidget {
        private final int u;
        private final int v;

        public CloseButtonWidget(int i, int j, int k, int l) {
            super(i, j, 35, 13, CLOSE_PRESENT);
            this.u = k;
            this.v = l;
        }

        @Override
        public void onPress() {
            ClientPlayerEntity clientPlayer = PresentScreen.this.client.player;

            if (clientPlayer != null) {
                ClientPlayNetworking.send(new PresentClosePayload(clientPlayer.getId()));
                clientPlayer.closeHandledScreen();
            }
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            int j = this.u;
            if (this.isSelected()) {
                j += this.width;
            }
            context.drawTexture(PresentScreen.this.texture, this.getX() + 2, this.getY() + 2, j, this.v, 35, 13);
        }

        public void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
