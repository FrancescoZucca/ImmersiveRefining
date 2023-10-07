package mod.francescozucca.immersiverefining.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.francescozucca.immersiverefining.block.gui.TankScreenHandler;
import mod.francescozucca.immersiverefining.util.References;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TankHandledScreen extends HandledScreen<TankScreenHandler> {

    long amount;
    public static final Identifier TEXTURE = new Identifier(References.MOD_ID, "textures/gui/container/tank.png");
    public TankHandledScreen(TankScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        amount = handler.getAmount();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int h = 55 - MathHelper.ceil(((float) amount / (8 * FluidConstants.BUCKET)/81) * 55);

        context.drawTexture(TEXTURE, x+40, y+17, 176, 0, 69, h);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title))/2;
        backgroundHeight = 170;
        backgroundWidth = 176;
    }
}
