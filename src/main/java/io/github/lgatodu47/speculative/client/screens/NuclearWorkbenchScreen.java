package io.github.lgatodu47.speculative.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lgatodu47.speculative.common.container.NuclearWorkbenchContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NuclearWorkbenchScreen extends ContainerScreen<NuclearWorkbenchContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");

    public NuclearWorkbenchScreen(NuclearWorkbenchContainer container, PlayerInventory playerInv, ITextComponent title) {
        super(container, playerInv, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 29;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        super.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        this.blit(matrixStack, this.leftPos, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
    }
}
