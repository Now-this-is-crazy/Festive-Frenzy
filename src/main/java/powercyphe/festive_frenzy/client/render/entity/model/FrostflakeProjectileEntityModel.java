package powercyphe.festive_frenzy.client.render.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class FrostflakeProjectileEntityModel extends EntityModel<EntityRenderState> {
	public FrostflakeProjectileEntityModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
	}

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild(
                "main",
                CubeListBuilder.create(),
                PartPose.ZERO
        );
        CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(0, 2).addBox(0F, 0F, -6.5F, 0F, 15F, 13F, CubeDeformation.NONE, 1F, 1F);
        partDefinition.addOrReplaceChild("cross_1", cubeListBuilder, PartPose.rotation(0F, (float) Math.toRadians(90), 0F));
        partDefinition.addOrReplaceChild("cross_2", cubeListBuilder, PartPose.ZERO);

        return LayerDefinition.create(meshDefinition.transformed(partPose -> partPose.scaled(0.4F)), 13, 15);
	}
}