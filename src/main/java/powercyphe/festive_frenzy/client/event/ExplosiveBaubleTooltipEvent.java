package powercyphe.festive_frenzy.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.client.gui.tooltip.ExplosiveBaubleTooltipData;

public class ExplosiveBaubleTooltipEvent implements TooltipComponentCallback {

    @Override
    public @Nullable ClientTooltipComponent getComponent(TooltipComponent data) {
        if (data instanceof ExplosiveBaubleTooltipData explosiveBaubleTooltipData) {
            return explosiveBaubleTooltipData;
        }
        return null;
    }
}
