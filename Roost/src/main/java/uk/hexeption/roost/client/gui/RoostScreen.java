package uk.hexeption.roost.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.gui.RoostContainer;

/**
 * RoostScreen
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 04:44 pm
 */
public class RoostScreen extends ContainerScreen<RoostContainer> {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 133;

    private ResourceLocation GUI = new ResourceLocation(Roost.MODID, "textures/gui/roost_gui.png");

    private RoostContainer roostContainer;

    public RoostScreen(RoostContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.roostContainer = screenContainer;
        this.xSize = 176;
        this.ySize = 133;
    }

    @Override
    public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.func_230446_a_(p_230430_1_);
        super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        field_230712_o_.func_243248_b(p_230451_1_, this.field_230704_d_, 8, 6, 4210752);
        this.field_230712_o_.func_243248_b(p_230451_1_, this.playerInventory.getDisplayName(), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(GUI);
        int relX = (this.field_230708_k_ - WIDTH) / 2;
        int relY = (this.field_230709_l_ - HEIGHT) / 2;
        this.func_238474_b_(p_230450_1_, relX, relY, 0, 0, this.xSize, this.ySize);
        this.func_238474_b_(p_230450_1_, relX + 48, relY + 20, 176, 0, getProgressWidth(), 16);
    }


    private int getProgressWidth() {
        double progress = this.roostContainer.getProgress();
        return progress == 0.0D ? 0 : 1 + (int) (progress * 25);
    }


}
