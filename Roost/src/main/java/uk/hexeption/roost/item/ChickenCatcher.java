package uk.hexeption.roost.item;

import java.util.Random;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import uk.hexeption.roost.data.DataChicken;

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

        DataChicken chickenData = DataChicken.getDataFromEntity(target);
        Vector3d pos = new Vector3d(target.getPosX(), target.getPosY(), target.getPosZ());
        World world = target.getEntityWorld();

        if (chickenData == null) {
            return ActionResultType.FAIL;
        } else if (target.isChild()) {
            if (world.isRemote) {
                spawnParticles(pos, world, ParticleTypes.SMOKE);
            }
            world.playSound(playerIn, pos.x, pos.y, pos.z, SoundEvents.ENTITY_CHICKEN_HURT, target.getSoundCategory(), 1.0F, 1.0F);
        } else {
            if (world.isRemote) {
                spawnParticles(pos, world, ParticleTypes.EXPLOSION);
            } else {
                ItemEntity item = target.entityDropItem(chickenData.buildChickenStack(), 1.0F);
                item.setMotion(0, 0.2D, 0);
                ((ServerWorld) target.getEntityWorld()).removeEntity(target);
            }
            world.playSound(playerIn, pos.x, pos.y, pos.z, SoundEvents.ENTITY_CHICKEN_EGG, target.getSoundCategory(), 1.0F, 1.0F);
            stack.damageItem(1, playerIn, playerEntity -> {
            });
        }

        return ActionResultType.SUCCESS;
    }

    private void spawnParticles(Vector3d pos, World world, IParticleData particleType) {
        Random rand = new Random();

        for (int k = 0; k < 20; ++k) {
            double xCoord = pos.x + (rand.nextDouble() * 0.6D) - 0.3D;
            double yCoord = pos.y + (rand.nextDouble() * 0.6D);
            double zCoord = pos.z + (rand.nextDouble() * 0.6D) - 0.3D;
            double xSpeed = rand.nextGaussian() * 0.02D;
            double ySpeed = rand.nextGaussian() * 0.2D;
            double zSpeed = rand.nextGaussian() * 0.02D;
            world.addParticle(particleType, true, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        }
    }
}
