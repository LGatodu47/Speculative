package io.github.lgatodu47.speculative.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.CentrifugeContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CentrifugeScreen extends ContainerScreen<CentrifugeContainer> {
    private static final ResourceLocation CENTRIFUGE_GUI = new ResourceLocation(Speculative.MODID, "textures/gui/centrifuge_gui.png");

    public CentrifugeScreen(CentrifugeContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = 74;
        this.titleY = 7;
        this.playerInventoryTitleX = 97;
        this.playerInventoryTitleY = 70;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(CENTRIFUGE_GUI);
        this.blit(stack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.blit(stack, this.guiLeft + 55, this.guiTop + 31, 176, 0, this.container.getFuseProgressionScaled(), 17);
    }
}
