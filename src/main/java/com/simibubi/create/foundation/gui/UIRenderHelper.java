包com.simibubi.create.foundation.gui；

进口javax.annotation.noull；

进口org.lwjgl.opengl.GL20；
进口org.lwjgl.opengl.GL30；

进口com.mojang.blaze3d.pipeline.RenderTarget；
进口com.mojang.blaze3d.platform.GlConst；
进口com.mojang.blaze3d.platform.GlStateManager；
进口com.mojang.blaze3d.platform.window；
进口com.mojang.blaze3d.systems.RenderSystem；
进口com.mojang.blaze3d.vertex.BufferBuilder；
进口com.莫姜。blaze3d.vertex.DefaultVertexFormat；
进口com.mojang.blaze3d.vertex.PoseStack；
进口com.mojang.blaze3d.vertex.Tesselator；
进口com.mojang.blaze3d.vertex.vertexFormat；
进口com.mojang.math.Matrix4f；
进口com.mojang.math.Vector3f；
进口simibubi.create.foundation.实用程序.颜色；
进口com.simibubi.创造。基础。功能。夫妇；

进口net.minecraft.client.Minecraft；
进口net.minecraft.client.renderer.GameRenderer；
进口net.minecraftforge.client.gui.GuuiUtils；

公共班级UIRenderHelper{

	/**
*在需要模具的地方使用模具缓冲区的固定基地运营商。 强制主固定基地运营商拥有模具
*缓冲区将导致GL错误垃圾时，使用神话般的图形。
*/
公共静态的CustomRenderTarget帧缓冲区；

公共 静态的 无效的init() {
rendersystem。recordRenderCall(()->{
窗户主窗口=Minecraft。getInstance().GetWindow();
帧缓冲区=CustomRenderTarget。创造(主窗口);
		});
	}

	公共 静态的 无效的 updateWindowSize(窗户主窗口) {
		如果 (帧缓冲区！=无效的)
帧缓冲区。调整大小(主窗口。getWidth()，主窗口。getHeight()，Minecraft.ON_OSX);
	}

	公共 静态的 无效的 drawFramebuffer(漂浮阿尔法) {
帧缓冲区。renderWithAlpha(阿尔法);
	}

	/**
*将src的内容复制到dst后，从src切换到dst。
*/
	公共 静态的 无效的 swapAndBlitColor(renderTargetsrc，RenderTargetDST) {
GlStateManager。_glBindFramebuffer(GL30.GL_READ_framebuffer，src.frameBufferId);
GlStateManager。_glBindFramebuffer(GL30.GL_DRAW_framebuffer，dst.frameBufferId);
GlStateManager。_glBlitFrameBuffer(0,0，src.viewWidth，src.viewHeight，0,0，dst.viewWidth，dst.viewHeight，GL30.GL_COLOR_BUFFER_BIT，GL20.GL_LINEAR);

GlStateManager。_glBindFramebuffer(GlConst.GL_framebuffer，dst.frameBufferId);
	}

	公共 静态的 无效的 条纹(PoseStack女士，float角，intx，inty，int宽度，int长度) {
		条纹(Ms，角度，x，y，宽度，长度，主题。我(主题.键.STREAK));
	}
	//角度(以度为单位)；0°->向右渐淡
	//x和y指定起始边的中点
	//宽度是条纹的总宽度

	公共 静态的 无效的 条纹(PoseStack女士，float角，intx，inty，int宽度，int长度，int颜色) {
intA1=0xA0<<24;
intA2=0x80<<24;
intA3=0x10<<24;
		int a4 = 0x00 << 24;

		color &= 0x00FFFFFF;
		int c1 = a1 | color;
		int c2 = a2 | color;
		int c3 = a3 | color;
		int c4 = a4 | color;

		ms.pushPose();
		ms.translate(x, y, 0);
		ms.mulPose(Vector3f.ZP.rotationDegrees(angle - 90));

		streak(ms, breadth / 2, length, c1, c2, c3, c4);

		ms.popPose();
	}

	public static void streak(PoseStack ms, float angle, int x, int y, int breadth, int length, Color c) {
		Color color = c.copy().setImmutable();
		int c1 = color.scaleAlpha(0.625f).getRGB();
		int c2 = color.scaleAlpha(0.5f).getRGB();
		int c3 = color.scaleAlpha(0.0625f).getRGB();
		int c4 = color.scaleAlpha(0f).getRGB();

		ms.pushPose();
		ms.translate(x, y, 0);
		ms.mulPose(Vector3f.ZP.rotationDegrees(angle - 90));

		streak(ms, breadth / 2, length, c1, c2, c3, c4);

		ms.popPose();
	}

	private static void streak(PoseStack ms, int width, int height, int c1, int c2, int c3, int c4) {
		double split1 = .5;
		double split2 = .75;
		Matrix4f model = ms.last().pose();
		GuiUtils.drawGradientRect(model, 0, -width, 0, width, (int) (split1 * height), c1, c2);
		GuiUtils.drawGradientRect(model, 0, -width, (int) (split1 * height), width, (int) (split2 * height), c2, c3);
		GuiUtils.drawGradientRect(model, 0, -width, (int) (split2 * height), width, height, c3, c4);
	}

	/**
	 * @see #angledGradient(MatrixStack, float, int, int, int, int, int, Color, Color)
	 */
	public static void angledGradient(@Nonnull PoseStack ms, float angle, int x, int y, int breadth, int length, Couple<Color> c) {
		angledGradient(ms, angle, x, y, 0, breadth, length, c);
	}

	/**
	 * @see #angledGradient(MatrixStack, float, int, int, int, int, int, Color, Color)
	 */
	public static void angledGradient(@Nonnull PoseStack ms, float angle, int x, int y, int z, int breadth, int length, Couple<Color> c) {
		angledGradient(ms, angle, x, y, z, breadth, length, c.getFirst(), c.getSecond());
	}

	/**
	 * @see #angledGradient(MatrixStack, float, int, int, int, int, int, Color, Color)
	 */
	public static void angledGradient(@Nonnull PoseStack ms, float angle, int x, int y, int breadth, int length, Color color1, Color color2) {
		angledGradient(ms, angle, x, y, 0, breadth, length, color1, color2);
	}

	/**
	 * x and y specify the middle point of the starting edge
	 *
	 * @param angle   the angle of the gradient in degrees; 0° means from left to right
	 * @param color1  the color at the starting edge
	 * @param color2  the color at the ending edge
	 * @param breadth the total width of the gradient
	 */
	public static void angledGradient(@Nonnull PoseStack ms, float angle, int x, int y, int z, int breadth, int length, Color color1, Color color2) {
		ms.pushPose();
		ms.translate(x, y, z);
		ms.mulPose(Vector3f.ZP.rotationDegrees(angle - 90));

		Matrix4f model = ms.last().pose();
		int w = breadth / 2;
		GuiUtils.drawGradientRect(model, 0, -w, 0, w, length, color1.getRGB(), color2.getRGB());

		ms.popPose();
	}

	public static void breadcrumbArrow(PoseStack matrixStack, int x, int y, int z, int width, int height, int indent, Couple<Color> colors) {breadcrumbArrow(matrixStack, x, y, z, width, height, indent, colors.getFirst(), colors.getSecond());}

	// draws a wide chevron-style breadcrumb arrow pointing left
	public static void breadcrumbArrow(PoseStack matrixStack, int x, int y, int z, int width, int height, int indent, Color startColor, Color endColor) {
		matrixStack.pushPose();
		matrixStack.translate(x - indent, y, z);

		breadcrumbArrow(matrixStack, width, height, indent, startColor, endColor);

		matrixStack.popPose();
	}

	private static void breadcrumbArrow(PoseStack ms, int width, int height, int indent, Color c1, Color c2) {

		/*
		 * 0,0       x1,y1 ********************* x4,y4 ***** x7,y7
		 *       ****                                     ****
		 *   ****                                     ****
		 * x0,y0     x2,y2                       x5,y5
		 *   ****                                     ****
		 *       ****                                     ****
		 *           x3,y3 ********************* x6,y6 ***** x8,y8
		 *
		 */

		float x0 = 0, y0 = height / 2f;
		float x1 = indent, y1 = 0;
		float x2 = indent, y2 = height / 2f;
		float x3 = indent, y3 = height;
		float x4 = width, y4 = 0;
		float x5 = width, y5 = height / 2f;
		float x6 = width, y6 = height;
		float x7 = indent + width, y7 = 0;
		float x8 = indent + width, y8 = height;

		indent = Math.abs(indent);
		width = Math.abs(width);
		Color fc1 = Color.mixColors(c1, c2, 0);
		Color fc2 = Color.mixColors(c1, c2, (indent) / (width + 2f * indent));
		Color fc3 = Color.mixColors(c1, c2, (indent + width) / (width + 2f * indent));
		Color fc4 = Color.mixColors(c1, c2, 1);

		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableCull();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		Matrix4f model = ms.last().pose();
		bufferbuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

		bufferbuilder.vertex(model, x0, y0, 0).color(fc1.getRed(), fc1.getGreen(), fc1.getBlue(), fc1.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x1, y1, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x2, y2, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();

		bufferbuilder.vertex(model, x0, y0, 0).color(fc1.getRed(), fc1.getGreen(), fc1.getBlue(), fc1.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x2, y2, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x3, y3, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();

		bufferbuilder.vertex(model, x3, y3, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x1, y1, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x4, y4, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();

		bufferbuilder.vertex(model, x3, y3, 0).color(fc2.getRed(), fc2.getGreen(), fc2.getBlue(), fc2.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x4, y4, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x6, y6, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();

		bufferbuilder.vertex(model, x5, y5, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x4, y4, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x7, y7, 0).color(fc4.getRed(), fc4.getGreen(), fc4.getBlue(), fc4.getAlpha()).endVertex();

		bufferbuilder.vertex(model, x6, y6, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x5, y5, 0).color(fc3.getRed(), fc3.getGreen(), fc3.getBlue(), fc3.getAlpha()).endVertex();
		bufferbuilder.vertex(model, x8, y8, 0).color(fc4.getRed(), fc4.getGreen(), fc4.getBlue(), fc4.getAlpha()).endVertex();

		tessellator.end();
		RenderSystem.enableCull();
		RenderSystem.disableBlend();
		RenderSystem.enableTexture();
	}

	//just like AbstractGui#drawTexture, but with a color at every vertex
	public static void drawColoredTexture(PoseStack ms, Color c, int x, int y, int tex_left, int tex_top, int width, int height) {
		drawColoredTexture(ms, c, x, y, 0, (float) tex_left, (float) tex_top, width, height, 256, 256);
	}

	public static void drawColoredTexture(PoseStack ms, Color c, int x, int y, int z, float tex_left, float tex_top, int width, int height, int sheet_width, int sheet_height) {
		drawColoredTexture(ms, c, x, x + width, y, y + height, z, width, height, tex_left, tex_top, sheet_width, sheet_height);
	}

	public static void drawStretched(PoseStack ms, int left, int top, int w, int h, int z, AllGuiTextures tex) {
		tex.bind();
		drawTexturedQuad(ms.last()
			.pose(), Color.WHITE, left, left + w, top, top + h, z, tex.startX / 256f, (tex.startX + tex.width) / 256f,
			tex.startY / 256f, (tex.startY + tex.height) / 256f);
	}

	public static void drawCropped(PoseStack ms, int left, int top, int w, int h, int z, AllGuiTextures tex) {
		tex.bind();
		drawTexturedQuad(ms.last()
			.pose(), Color.WHITE, left, left + w, top, top + h, z, tex.startX / 256f, (tex.startX + w) / 256f,
			tex.startY / 256f, (tex.startY + h) / 256f);
	}

	private static void drawColoredTexture(PoseStack ms, Color c, int left, int right, int top, int bot, int z, int tex_width, int tex_height, float tex_left, float tex_top, int sheet_width, int sheet_height) {
		drawTexturedQuad(ms.last().pose(), c, left, right, top, bot, z, (tex_left + 0.0F) / (float) sheet_width, (tex_left + (float) tex_width) / (float) sheet_width, (tex_top + 0.0F) / (float) sheet_height, (tex_top + (float) tex_height) / (float) sheet_height);
	}

	private static void drawTexturedQuad(Matrix4f m, Color c, int left, int right, int top, int bot, int z, float u1, float u2, float v1, float v2) {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
		bufferbuilder.vertex(m, (float) left , (float) bot, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u1, v2).endVertex();
		bufferbuilder.vertex(m, (float) right, (float) bot, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u2, v2).endVertex();
		bufferbuilder.vertex(m, (float) right, (float) top, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u2, v1).endVertex();
		bufferbuilder.vertex(m, (float) left , (float) top, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u1, v1).endVertex();
		tesselator.end();
		RenderSystem.disableBlend();
	}

	public static void flipForGuiRender(PoseStack poseStack) {
		poseStack.mulPoseMatrix(Matrix4f.createScaleMatrix(1, -1, 1));
	}

	public static class CustomRenderTarget extends RenderTarget {

		public CustomRenderTarget(boolean useDepth) {
			super(useDepth);
		}

		public static CustomRenderTarget create(Window mainWindow) {
			CustomRenderTarget framebuffer = new CustomRenderTarget(true);
			framebuffer.resize(mainWindow.getWidth(), mainWindow.getHeight(), Minecraft.ON_OSX);
			framebuffer.setClearColor(0, 0, 0, 0);
			
			return framebuffer;
		}

		public void renderWithAlpha(float alpha) {
			Window window = Minecraft.getInstance().getWindow();

			float vx = (float) window.getGuiScaledWidth();
			float vy = (float) window.getGuiScaledHeight();
			float tx = (float) viewWidth / (float) width;
			float ty = (float) viewHeight / (float) height;

			RenderSystem.enableTexture();
			RenderSystem.enableDepthTest();
			RenderSystem.setShader(() -> Minecraft.getInstance().gameRenderer.blitShader);
			RenderSystem.getShader().setSampler("DiffuseSampler", colorTextureId);

			bindRead();

			Tesselator tessellator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuilder();
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

			bufferbuilder.vertex(0, vy, 0).color(1, 1, 1, alpha).uv(0, 0).endVertex();
			bufferbuilder.vertex(vx, vy, 0).color(1, 1, 1, alpha).uv(tx, 0).endVertex();
			bufferbuilder.vertex(vx, 0, 0).color(1, 1, 1, alpha).uv(tx, ty).endVertex();
			bufferbuilder.vertex(0, 0, 0).color(1, 1, 1, alpha).uv(0, ty).endVertex();

			tessellator.end();
			unbindRead();
		}

	}

}
