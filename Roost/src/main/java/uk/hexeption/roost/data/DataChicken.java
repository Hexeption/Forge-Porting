package uk.hexeption.roost.data;

import com.google.common.base.CaseFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import uk.hexeption.roost.setup.ModItems;

public class DataChicken {

	public static final String CHICKEN_ID_KEY = "Chicken";
	private static final Pattern REMOVE_CHICKENS_PREFIX = Pattern.compile("_?chick(en)?s?_?");

	public static List<DataChicken> getAllChickens() {
		List<DataChicken> chickens = new LinkedList<>();
		DataChickenVanilla.addAllChickens(chickens);
		return chickens;
	}

	public static DataChicken getDataFromTooltipNBT(CompoundNBT tag) {
		if (tag == null) {
			return null;
		}

		DataChicken data = null;

		if (data == null) {
			data = DataChickenVanilla.getDataFromTooltipNBT(tag);
		}

		return data;
	}

	public static DataChicken getDataFromEntity(Entity entity) {
		if (entity == null) {
			return null;
		}

		DataChicken data = null;

		if (data == null) {
			data = DataChickenVanilla.getDataFromEntity(entity);
		}

		return data;
	}

	public static DataChicken getDataFromStack(ItemStack stack) {
		if (!isChicken(stack)) {
			return null;
		}

		DataChicken data = null;

		if (data == null) {
			data = DataChickenVanilla.getDataFromStack(stack);
		}

		return data;
	}

	public static DataChicken getDataFromName(String name) {
		DataChicken data = null;

		if (data == null) {
			data = DataChickenVanilla.getDataFromName(name);
		}

		return data;
	}

	public static void getItemChickenSubItems(ItemGroup tab, List<ItemStack> subItems) {
		for (DataChicken chicken : getAllChickens()) {
			subItems.add(chicken.buildChickenStack());
		}
	}

	public static boolean isChicken(ItemStack stack) {
		return stack.getItem() == ModItems.CHICKEN_ITEM.get();
	}

	private String name;
	protected String i18nName;

	protected Random rand = new Random();

	public DataChicken(String name, String i18nName) {
		super();
		String underscoredName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
		String cleanedName = REMOVE_CHICKENS_PREFIX.matcher(underscoredName).replaceAll("");
		this.name = cleanedName;
		this.i18nName = i18nName;
	}

	public void addInfoToTooltip(List<String> tooltip) {
	}

	public boolean hasParents() {
		return false;
	}

	public List<ItemStack> buildParentChickenStack() {
		return null;
	}

	public ItemStack buildChickenStack() {
		return ItemStack.EMPTY;
	}

	public ItemStack buildCaughtFromStack() {
		return ItemStack.EMPTY;
	}

	public ChickenEntity buildEntity(World world) {
		return null;
	}

	public CompoundNBT buildTooltipNBT() {
		return null;
	}
}
