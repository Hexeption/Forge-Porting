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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import uk.hexeption.roost.setup.ModItems;

public class DataChicken {

    public static final String CHICKEN_ID_KEY = "Chicken";
    private static final Pattern REMOVE_CHICKENS_PREFIX = Pattern.compile("_?chick(en)?s?_?");
    protected String i18nName;
    protected Random rand = new Random();
    private final String name;

    public DataChicken(String name, String i18nName) {
        super();
        String underscoredName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        String cleanedName = REMOVE_CHICKENS_PREFIX.matcher(underscoredName).replaceAll("");
        this.name = cleanedName;
        this.i18nName = i18nName;
    }

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

    private static ItemStack createChildStack(DataChicken chickenA, DataChicken chickenB, World world) {
        if (chickenA.getClass() != chickenB.getClass()) {
            return chickenA.buildChickenStack();
        }
        ChickenEntity parentA = chickenA.buildEntity(world);
        ChickenEntity parentB = chickenB.buildEntity(world);
        if (parentA == null || parentB == null) {
            return ItemStack.EMPTY;
        }
        DataChicken childData = DataChicken.getDataFromEntity(parentA.func_241840_a((ServerWorld) world, parentB));
        if (childData == null) {
            return ItemStack.EMPTY;
        }
        return childData.buildChickenStack();
    }

    public void addInfoToTooltip(List<ITextComponent> tooltip) {
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

    public void spawnEntity(World world, BlockPos pos) {
    }

    public String getChickenType() {
        return null;
    }

    public String getTextureName() {
        return null;
    }

    public ItemStack createChildStack(DataChicken other, World world) {
        if (rand.nextBoolean()) {
            return createChildStack(this, other, world);
        }
        return createChildStack(other, this, world);
    }

    public ItemStack createDropStack() {
        return ItemStack.EMPTY;
    }

    public int getAddedTime(ItemStack stack) {
        return Math.max(0, stack.getCount());
    }

    public int getLayTime() {
        return 6000 + rand.nextInt(6000);
    }

    public String getName() {
        return name;
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(i18nName);
    }

    public boolean isEqual(DataChicken other) {
        return true;
    }

    public ITextComponent getDisplaySummary() {
        return getDisplayName();
    }

}
