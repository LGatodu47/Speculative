package io.github.lgatodu47.speculative.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.container.BossSummonerMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class BossSummonerScreen extends AbstractContainerScreen<BossSummonerMenu> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Speculative.MODID, "textures/gui/speculo_boss_summoner_gui_temp.png");

    public BossSummonerScreen(BossSummonerMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, new TextComponent(""));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
