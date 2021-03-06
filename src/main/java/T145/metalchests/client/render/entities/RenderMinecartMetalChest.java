package T145.metalchests.client.render.entities;

import T145.metalchests.client.render.blocks.RenderMetalChest;
import T145.metalchests.entities.base.EntityMinecartMetalChestBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMinecartMetalChest extends Render<EntityMinecartMetalChestBase> {

	private static final ResourceLocation MINECART_TEXTURES = new ResourceLocation("textures/entity/minecart.png");
	protected ModelBase modelMinecart = new ModelMinecart();

	public RenderMinecartMetalChest(RenderManager renderManagerIn) {
		super(renderManagerIn);
		shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntityMinecartMetalChestBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		bindEntityTexture(entity);
		long i = (long) entity.getEntityId() * 493286711L;
		i = i * i * 4392167121L + i * 98761L;
		float f = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f1 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f2 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GlStateManager.translate(f, f1, f2);
		double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		double d3 = 0.30000001192092896D;
		Vec3d vec3d = entity.getPos(d0, d1, d2);
		float f3 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

		if (vec3d != null) {
			Vec3d vec3d1 = entity.getPosOffset(d0, d1, d2, 0.30000001192092896D);
			Vec3d vec3d2 = entity.getPosOffset(d0, d1, d2, -0.30000001192092896D);

			if (vec3d1 == null) {
				vec3d1 = vec3d;
			}

			if (vec3d2 == null) {
				vec3d2 = vec3d;
			}

			x += vec3d.x - d0;
			y += (vec3d1.y + vec3d2.y) / 2.0D - d1;
			z += vec3d.z - d2;
			Vec3d vec3d3 = vec3d2.addVector(-vec3d1.x, -vec3d1.y, -vec3d1.z);

			if (vec3d3.lengthVector() != 0.0D) {
				vec3d3 = vec3d3.normalize();
				entityYaw = (float) (Math.atan2(vec3d3.z, vec3d3.x) * 180.0D / Math.PI);
				f3 = (float) (Math.atan(vec3d3.y) * 73.0D);
			}
		}

		GlStateManager.translate((float) x, (float) y + 0.375F, (float) z);
		GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-f3, 0.0F, 0.0F, 1.0F);
		float f5 = (float) entity.getRollingAmplitude() - partialTicks;
		float f6 = entity.getDamage() - partialTicks;

		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f5 > 0.0F) {
			GlStateManager.rotate(MathHelper.sin(f5) * f5 * f6 / 10.0F * (float) entity.getRollingDirection(), 1.0F, 0.0F, 0.0F);
		}

		int j = entity.getDisplayTileOffset();

		if (renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(getTeamColor(entity));
		}

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.75F, 0.75F, 0.75F);
		GlStateManager.translate(-0.5F, (float) (j - 8) / 16.0F, -0.5F);
		RenderMetalChest.INSTANCE.renderChest(entity.getChestInstance(), 0, 0, 0, partialTicks, -1, RenderMetalChest.INSTANCE.getRenderDistance(entity));
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		bindEntityTexture(entity);

		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		modelMinecart.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();

		if (renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMinecartMetalChestBase entity) {
		return MINECART_TEXTURES;
	}
}