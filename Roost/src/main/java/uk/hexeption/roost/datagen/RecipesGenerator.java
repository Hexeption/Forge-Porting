package uk.hexeption.roost.datagen;

import java.util.function.Consumer;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import uk.hexeption.roost.setup.ModBlocks;
import uk.hexeption.roost.setup.ModItems;

/**
 * RecipesGenerator
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 04:21 am
 */
public class RecipesGenerator extends RecipeProvider {

    public RecipesGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModItems.CHICKEN_CATCHER.get())
            .patternLine("0")
            .patternLine("1")
            .patternLine("2")
            .key('0', Items.EGG)
            .key('1', Items.STICK)
            .key('2', Items.FEATHER)
            .addCriterion("egg", InventoryChangeTrigger.Instance.forItems(Items.EGG))
            .addCriterion("stick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
            .addCriterion("feather", InventoryChangeTrigger.Instance.forItems(Items.FEATHER))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ROOST.get())
            .patternLine("WWW")
            .patternLine("W W")
            .patternLine("HHH")
            .key('W', ItemTags.PLANKS)
            .key('H', Items.HAY_BLOCK)
            .addCriterion("planks", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ItemTags.PLANKS).build()))
            .addCriterion("hay", InventoryChangeTrigger.Instance.forItems(Items.HAY_BLOCK))
            .build(consumer);
    }
}
