package com.teammetallurgy.atum.init;

import com.teammetallurgy.atum.api.recipe.RecipeHandlers;
import com.teammetallurgy.atum.api.recipe.quern.IQuernRecipe;
import com.teammetallurgy.atum.api.recipe.quern.QuernRecipe;
import com.teammetallurgy.atum.blocks.glass.BlockAtumStainedGlass;
import com.teammetallurgy.atum.blocks.glass.BlockAtumStainedGlassPane;
import com.teammetallurgy.atum.blocks.stone.limestone.BlockLimestoneBricks;
import com.teammetallurgy.atum.utils.AtumRegistry;
import com.teammetallurgy.atum.utils.BlacklistOreIngredient;
import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import org.apache.commons.lang3.StringUtils;

import static net.minecraft.block.BlockFlower.EnumFlowerType;
import static net.minecraft.potion.PotionUtils.addPotionToItemStack;
import static net.minecraftforge.common.brewing.BrewingRecipeRegistry.addRecipe;

@Mod.EventBusSubscriber
public class AtumRecipes {

    public static void registerRecipeHandlers() {
        RecipeHandlers.quernRecipes = AtumRegistry.makeRegistry("quern_recipes", IQuernRecipe.class);
    }

    private static void register() {
        addSmeltingRecipes();
        addBrewingRecipes();
    }

    private static void addSmeltingRecipes() {
        GameRegistry.addSmelting(AtumBlocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
        GameRegistry.addSmelting(AtumBlocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
        GameRegistry.addSmelting(AtumBlocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
        GameRegistry.addSmelting(AtumBlocks.LAPIS_ORE, new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
        GameRegistry.addSmelting(AtumBlocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1.0F);
        GameRegistry.addSmelting(AtumBlocks.EMERALD_ORE, new ItemStack(Items.EMERALD), 1.0F);
        GameRegistry.addSmelting(AtumBlocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1.0F);
        GameRegistry.addSmelting(AtumBlocks.PALM_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
        GameRegistry.addSmelting(AtumBlocks.DEADWOOD_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
        GameRegistry.addSmelting(AtumBlocks.LIMESTONE_CRACKED, new ItemStack(AtumBlocks.LIMESTONE), 0.1F);
        GameRegistry.addSmelting(BlockLimestoneBricks.getBrick(BlockLimestoneBricks.BrickType.LARGE), new ItemStack(BlockLimestoneBricks.getBrick(BlockLimestoneBricks.BrickType.CRACKED)), 0.1F);
        GameRegistry.addSmelting(AtumBlocks.LIMESTONE, new ItemStack(BlockLimestoneBricks.getBrick(BlockLimestoneBricks.BrickType.CARVED)), 0.1F);
        GameRegistry.addSmelting(AtumBlocks.SAND, new ItemStack(AtumBlocks.CRYSTAL_GLASS), 0.1F);
        GameRegistry.addSmelting(AtumBlocks.SANDY_CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35F);
        GameRegistry.addSmelting(AtumItems.JEWELED_FISH, new ItemStack(Items.GOLD_NUGGET, 3), 0.3F);
        GameRegistry.addSmelting(AtumItems.GOLD_COIN, new ItemStack(Items.GOLD_NUGGET), 0.1F);
        GameRegistry.addSmelting(AtumItems.EMMER_DOUGH, new ItemStack(AtumItems.EMMER_BREAD), 0.1F);
    }

    private static void addBrewingRecipes() {
        addBrewingRecipeWithSubPotions(new ItemStack(AtumItems.MANDIBLES), PotionTypes.WEAKNESS);
        addRecipe(addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(AtumItems.ECTOPLASM), new ItemStack(Items.EXPERIENCE_BOTTLE));
    }

    private static void addBrewingRecipeWithSubPotions(ItemStack ingredient, PotionType potionType) {
        addRecipe(addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), ingredient, addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.MUNDANE));
        addRecipe(addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), ingredient, addPotionToItemStack(new ItemStack(Items.POTIONITEM), potionType));
        addRecipe(addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.AWKWARD), ingredient, addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potionType));
        addRecipe(addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.WATER), ingredient, addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.MUNDANE));
        addRecipe(addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.WATER), ingredient, addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.MUNDANE));
        addRecipe(addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.AWKWARD), ingredient, addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potionType));
    }

    private static void addQuernRecipes(RegistryEvent.Register<IQuernRecipe> event) {
        AtumRegistry.registerRecipe("emmer_wheat", new QuernRecipe("cropEmmer", new ItemStack(AtumItems.EMMER_FLOUR), 4), event);

        AtumRegistry.registerRecipe("dandelion", new QuernRecipe(new ItemStack(Blocks.YELLOW_FLOWER, 1, EnumFlowerType.DANDELION.getMeta()), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()), 2), event);
        AtumRegistry.registerRecipe("popey", new QuernRecipe(new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.POPPY.getMeta()), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()), 2), event);
    }

    @SubscribeEvent
    public static void registerTreeTapRecipes(RegistryEvent.Register<IQuernRecipe> event) {
        addQuernRecipes(event);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        final ResourceLocation crystal = new ResourceLocation(Constants.MOD_ID, "crystal_glass");
        final ResourceLocation framed = new ResourceLocation(Constants.MOD_ID, "framed_glass");

        for (EnumDyeColor color : EnumDyeColor.values()) {
            String colorName = StringUtils.capitalize(color.getTranslationKey().replace("silver", "lightGray"));
            AtumRegistry.registerRecipe("crystal_" + colorName, new ShapedOreRecipe(crystal, new ItemStack(BlockAtumStainedGlass.getGlass(AtumBlocks.CRYSTAL_GLASS, color), 8), "GGG", "GDG", "GGG", 'G', AtumBlocks.CRYSTAL_GLASS, 'D', "dye" + colorName), event);
            AtumRegistry.registerRecipe("framed_" + colorName, new ShapedOreRecipe(framed, new ItemStack(BlockAtumStainedGlass.getGlass(AtumBlocks.FRAMED_GLASS, color), 8), "GGG", "GDG", "GGG", 'G', AtumBlocks.FRAMED_GLASS, 'D', "dye" + colorName), event);
            AtumRegistry.registerRecipe("crystal_to_framed_" + colorName, new ShapedOreRecipe(framed, BlockAtumStainedGlass.getGlass(AtumBlocks.FRAMED_GLASS, color), " S ", "SGS", " S ", 'S', "stickWood", 'G', BlockAtumStainedGlass.getGlass(AtumBlocks.CRYSTAL_GLASS, color)), event);
            AtumRegistry.registerRecipe("thin_crystal_" + colorName, new ShapedOreRecipe(crystal, new ItemStack(BlockAtumStainedGlassPane.getGlass(AtumBlocks.CRYSTAL_GLASS, color), 16), "GGG", "GGG", 'G', BlockAtumStainedGlass.getGlass(AtumBlocks.CRYSTAL_GLASS, color)), event);
            AtumRegistry.registerRecipe("thin_framed_" + colorName, new ShapedOreRecipe(framed, new ItemStack(BlockAtumStainedGlassPane.getGlass(AtumBlocks.FRAMED_GLASS, color), 16), "GGG", "GGG", 'G', BlockAtumStainedGlass.getGlass(AtumBlocks.FRAMED_GLASS, color)), event);
        }
        AtumRecipes.register();
        fixOreDictEntries(registry);
    }

    private static void fixOreDictEntries(IForgeRegistry<IRecipe> registry) {
        IForgeRegistryModifiable<IRecipe> recipes = (IForgeRegistryModifiable<IRecipe>) registry;
        final ResourceLocation stick = new ResourceLocation("stick");
        final ResourceLocation torch = new ResourceLocation("torch");
        final ResourceLocation ladder = new ResourceLocation("ladder");
        final ResourceLocation chest = new ResourceLocation("chest");
        final ResourceLocation trapdoor = new ResourceLocation("trapdoor");

        Ingredient plankWood = new BlacklistOreIngredient("plankWood", stack -> stack.getItem() == Item.getItemFromBlock(Blocks.PLANKS));

        //Stick
        recipes.remove(stick);
        registry.register(new ShapedOreRecipe(stick, new ItemStack(Items.STICK, 4), "P", "P", 'P', plankWood).setRegistryName(new ResourceLocation(Constants.MOD_ID, "stick"))); //Modded planks
        registry.register(new ShapedOreRecipe(stick, new ItemStack(Items.STICK, 4), "P", "P", 'P', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE)).setRegistryName(stick));

        //Torch
        recipes.remove(torch);
        registry.register(new ShapedOreRecipe(torch, new ItemStack(Blocks.TORCH, 4), "C", "S", 'C', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE), 'S', Items.STICK).setRegistryName(torch));

        //Ladder
        recipes.remove(ladder);
        registry.register(new ShapedOreRecipe(ladder, new ItemStack(Blocks.LADDER, 3), "S S", "SSS", "S S", 'S', "stickWood").setRegistryName(ladder));

        //Chest
        recipes.remove(chest);
        registry.register(new ShapedOreRecipe(chest, new ItemStack(Blocks.CHEST), "PPP", "P P", "PPP", 'P', plankWood).setRegistryName(new ResourceLocation(Constants.MOD_ID, "chest"))); //Modded chests
        registry.register(new ShapedOreRecipe(chest, new ItemStack(Blocks.CHEST), "PPP", "P P", "PPP", 'P', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE)).setRegistryName(chest));

        //Trapdoor
        recipes.remove(trapdoor);
        registry.register(new ShapedOreRecipe(trapdoor, new ItemStack(Blocks.TRAPDOOR, 2), "PPP", "PPP", 'P', "plankWood").setRegistryName(trapdoor));

        ////Cracked Limestone
        final ResourceLocation sword = new ResourceLocation("stone_sword");
        final ResourceLocation shovel = new ResourceLocation("stone_shovel");
        final ResourceLocation pickaxe = new ResourceLocation("stone_pickaxe");
        final ResourceLocation hoe = new ResourceLocation("stone_hoe");
        final ResourceLocation axe = new ResourceLocation("stone_axe");
        final ResourceLocation furnace = new ResourceLocation("furnace");

        Ingredient cobblestone = new BlacklistOreIngredient("cobblestone", stack -> stack.getItem() == Item.getItemFromBlock(AtumBlocks.LIMESTONE_CRACKED));

        //Sword
        recipes.remove(sword);
        registry.register(new ShapedOreRecipe(sword, Items.STONE_SWORD, " C ", " C ", " S ", 'C', cobblestone, 'S', "stickWood").setRegistryName(sword));

        //Shovel
        recipes.remove(shovel);
        registry.register(new ShapedOreRecipe(shovel, Items.STONE_SHOVEL, " C ", " S ", " S ", 'C', cobblestone, 'S', "stickWood").setRegistryName(shovel));

        //Pickaxe
        recipes.remove(pickaxe);
        registry.register(new ShapedOreRecipe(pickaxe, Items.STONE_PICKAXE, "CCC", " S ", " S ", 'C', cobblestone, 'S', "stickWood").setRegistryName(pickaxe));

        //Hoe
        recipes.remove(hoe);
        registry.register(new ShapedOreRecipe(hoe, Items.STONE_HOE, "CC ", " S ", " S ", 'C', cobblestone, 'S', "stickWood").setRegistryName(hoe));

        //Axe
        recipes.remove(axe);
        registry.register(new ShapedOreRecipe(axe, Items.STONE_AXE, "CC ", "CS ", " S ", 'C', cobblestone, 'S', "stickWood").setRegistryName(axe));

        //Furnace
        recipes.remove(furnace);
        registry.register(new ShapedOreRecipe(furnace, Blocks.FURNACE, "CCC", "C C", "CCC", 'C', cobblestone).setRegistryName(furnace));
    }
}