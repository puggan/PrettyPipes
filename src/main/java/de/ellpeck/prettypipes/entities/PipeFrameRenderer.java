package de.ellpeck.prettypipes.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

public class PipeFrameRenderer extends ItemFrameRenderer {
    public PipeFrameRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }

    @Override
    public void render(ItemFrameEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (entityIn.getDisplayedItem().isEmpty())
            return;
        matrixStackIn.push();
        Direction direction = entityIn.getHorizontalFacing();
        Vec3d vec3d = this.getRenderOffset(entityIn, partialTicks);
        matrixStackIn.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
        matrixStackIn.translate(direction.getXOffset() * 0.05, direction.getYOffset() * 0.05, direction.getZOffset() * 0.05);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.rotationPitch));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityIn.rotationYaw));

        FontRenderer font = this.getFontRendererFromRenderManager();
        int amount = ((PipeFrameEntity) entityIn).getAmount();
        String ammountStrg = String.valueOf(amount);
        float x = 0.5F - font.getStringWidth(ammountStrg) / 2F;
        Matrix4f matrix4f = matrixStackIn.getLast().getPositionMatrix();
        matrixStackIn.translate(0, -0.13F, 0);
        matrixStackIn.scale(-0.02F, -0.02F, 0.02F);
        font.renderString(ammountStrg, x, 0, 0xFFFFFF, true, matrix4f, bufferIn, false, 0, packedLightIn);

        matrixStackIn.pop();
    }
}