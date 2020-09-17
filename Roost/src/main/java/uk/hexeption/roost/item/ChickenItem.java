package uk.hexeption.roost.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import uk.hexeption.roost.data.DataChicken;

/**
 * ChickenItem
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 16/09/2020 - 07:26 pm
 */
public class ChickenItem extends Item {

    private static final String I18N_NAME = "entity.minecraft.chicken";

    public ChickenItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DataChicken data = DataChicken.getDataFromStack(stack);
        if (data != null) {
            data.addInfoToTooltip(tooltip);
        } else {
            CompoundNBT tag = stack.getTag();
            if (tag != null) {
                String chicken = tag.getString(DataChicken.CHICKEN_ID_KEY);
                if (chicken.length() > 0) {
                    tooltip.add(new TranslationTextComponent("Broken chicken, id = \"" + chicken + "\""));
                }
            }
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        DataChicken data = DataChicken.getDataFromStack(stack);
        if (data == null) {
            return new TranslationTextComponent(I18N_NAME);
        }
        return data.getDisplayName();
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            spawnChicken(context.getPlayer().getHeldItem(context.getHand()), context.getPlayer(), context.getWorld(), context.getPos().offset(context.getFace()));

        }
        return super.onItemUse(context);
    }

    private void spawnChicken(ItemStack stack, PlayerEntity playerIn, World worldIn, BlockPos pos) {
        DataChicken chickenData = DataChicken.getDataFromStack(stack);
        if (chickenData == null) {
            return;
        }
        chickenData.spawnEntity(worldIn, pos);
        stack.shrink(1);
    }
}
