package uk.hexeption.roost.data;

import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import uk.hexeption.roost.config.Config.Common;
import uk.hexeption.roost.setup.ModItems;

public class DataChickenVanilla extends DataChicken {

	private static final String VANILLA_TYPE = "minecraft:chicken";

	public static DataChicken getDataFromStack(ItemStack stack) {
		CompoundNBT tagCompound = stack.getTag();
		if (tagCompound == null || !tagCompound.getString(CHICKEN_ID_KEY).equals(VANILLA_TYPE)) {
			return null;
		}
		return new DataChickenVanilla();
	}

	public static DataChicken getDataFromTooltipNBT(CompoundNBT tagCompound) {
		if (tagCompound == null || !tagCompound.getString(CHICKEN_ID_KEY).equals(VANILLA_TYPE)) {
			return null;
		}
		return new DataChickenVanilla();
	}

	public static DataChicken getDataFromEntity(Entity entity) {
		if (entity instanceof ChickenEntity) {
			return new DataChickenVanilla();
		}
		return null;
	}

	public static DataChicken getDataFromName(String name) {
		if (name.equals("minecraft:vanilla")) {
			return new DataChickenVanilla();
		}
		return null;
	}

	public static void addAllChickens(List<DataChicken> chickens) {
		chickens.add(new DataChickenVanilla());
	}

	public DataChickenVanilla() {
		super("vanilla", "entity.minecraft.chicken");
	}

	public boolean isEqual(DataChicken other) {
		return (other instanceof DataChickenVanilla);
	}

	public ItemStack createDropStack() {
		Item item = rand.nextInt(3) > 0 && !Common.disableEgglaying.get() ? Items.EGG : Items.FEATHER;
		return new ItemStack(item, 1);
	}

	@Override
	public ChickenEntity buildEntity(World world) {
		return new ChickenEntity(EntityType.CHICKEN, world);
	}

	public void spawnEntity(World world, BlockPos pos) {
		ChickenEntity chicken = new ChickenEntity(EntityType.CHICKEN, world);
		chicken.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		chicken.onInitialSpawn(((ServerWorld) world), world.getDifficultyForLocation(pos), SpawnReason.SPAWN_EGG, null, null);
		chicken.setGrowingAge(getLayTime());
		world.addEntity(chicken);
	}

	@Override
	public ItemStack buildChickenStack() {
		ItemStack stack = new ItemStack(ModItems.CHICKEN_ITEM.get());
		CompoundNBT tagCompound = new CompoundNBT();
		tagCompound.putString(CHICKEN_ID_KEY, VANILLA_TYPE);
		stack.setTag(tagCompound);
		return stack;
	}

	@Override
	public CompoundNBT buildTooltipNBT() {
		CompoundNBT tagCompound = new CompoundNBT();
		tagCompound.putString(CHICKEN_ID_KEY, VANILLA_TYPE);
		return tagCompound;
	}

	@Override
	public boolean hasParents() {
		return true;
	}

	@Override
	public List<ItemStack> buildParentChickenStack() {
		return Arrays.asList(buildChickenStack(), buildChickenStack());
	}

	@Override
	public ItemStack buildCaughtFromStack() {
		ItemStack stack = new ItemStack(Items.CHICKEN_SPAWN_EGG);
		SpawnEggItem.getIdFromItem(stack.getItem());
		return stack;
	}

	@Override
	public String toString() {
		return "DataChickenVanilla [name=" + getName() + "]";
	}

	public String getChickenType() {
		return "minecraft:vanilla";
	}
}
