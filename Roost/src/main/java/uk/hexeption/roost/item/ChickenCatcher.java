package uk.hexeption.roost.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import uk.hexeption.roost.Roost;

/**
 * ChickenCatcher
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 16/09/2020 - 06:18 pm
 */
public class ChickenCatcher extends Item {

    public ChickenCatcher(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

        if(target instanceof ChickenEntity){
            Roost.LOGGER.info("This is a chicken :)");
            return ActionResultType.SUCCESS;

        }
        return ActionResultType.FAIL;
    }
}
