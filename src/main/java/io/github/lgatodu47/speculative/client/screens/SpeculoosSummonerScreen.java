package io.github.lgatodu47.speculative.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.SpeculoosSummonerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SpeculoosSummonerScreen extends ContainerScreen<SpeculoosSummonerContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Speculative.MODID, "textures/gui/speculoos_summoner.png");

    public SpeculoosSummonerScreen(SpeculoosSummonerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        this.playerInventoryTitleY = 70;
    }

    @Override
    public void render(MatrixStack stack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(stack, x, y, 0, 0, this.xSize, this.ySize);
    }
}
