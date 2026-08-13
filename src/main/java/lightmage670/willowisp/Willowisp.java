package lightmage670.willowisp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lightmage670.willowisp.attachment.bloodAttachments.BloodData;
import lightmage670.willowisp.block.WillowispFacingBlock;
import lightmage670.willowisp.block.LeonCubeFacingBlock;
import lightmage670.willowisp.block.LightPillarBlock;
import lightmage670.willowisp.item.BloodComponent;
import lightmage670.willowisp.item.BloodFoodItem;
import lightmage670.willowisp.item.BloodItemSettings;
import lightmage670.willowisp.item.Syringe;



public class Willowisp implements ModInitializer {
	public static final String MOD_ID = "willowisp";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item SYRINGE = new Syringe(new FabricItemSettings().maxCount(1));
	public static final Item VIAL = new Item(new FabricItemSettings().maxCount(16));
	public static final FoodComponent MORTAL = new FoodComponent.Builder().alwaysEdible()
		.hunger(1)
		.saturationModifier(1)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0), 1.0F)
		.build();
	public static final BloodComponent MORTAL_BLOOD = new BloodComponent.Builder().alwaysEdible()
		.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 9), 1.0F)
		.build();
	public static final Item MORTAL_VIAL = new BloodFoodItem(new BloodItemSettings().recipeRemainder(VIAL).maxCount(16).food(MORTAL).blood(MORTAL_BLOOD));
	public static final FoodComponent VAMP = new FoodComponent.Builder().alwaysEdible()
		.hunger(0)
		.saturationModifier(0)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0), 1.0F)
		.build();
	public static final BloodComponent VAMP_BLOOD = new BloodComponent.Builder().alwaysEdible()
		.statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 1), 1.0F)
		.build();
	public static final Item VAMP_VIAL = new BloodFoodItem(new BloodItemSettings().recipeRemainder(VIAL).maxCount(16).food(VAMP).blood(VAMP_BLOOD));
	public static final FoodComponent DIVINE = new FoodComponent.Builder().alwaysEdible()
		.hunger(1)
		.saturationModifier(1)
		.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0), 1.0F)
		.build();
	public static final BloodComponent DIVINE_BLOOD = new BloodComponent.Builder().alwaysEdible()
		.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 9), 1.0F)
		.build();
	public static final Item DIVINE_VIAL = new BloodFoodItem(new BloodItemSettings().recipeRemainder(VIAL).maxCount(16).food(DIVINE).blood(DIVINE_BLOOD));
	public static final FoodComponent GOD = new FoodComponent.Builder().alwaysEdible()
		.hunger(1)
		.saturationModifier(1)
		.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 5), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0), 1.0F)
		.build();
	public static final BloodComponent GOD_BLOOD = new BloodComponent.Builder().alwaysEdible()
		.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 2), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 19), 1.0F)
		.build();
	public static final Item GOD_VIAL = new BloodFoodItem(new BloodItemSettings().recipeRemainder(VIAL).maxCount(16).food(GOD).blood(GOD_BLOOD));
	public static final FoodComponent SCULK = new FoodComponent.Builder().alwaysEdible()
		.hunger(1)
		.saturationModifier(0)
		.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 2), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 1), 1.0F)
		.build();
	public static final BloodComponent SCULK_BLOOD = new BloodComponent.Builder().alwaysEdible()
		.statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 400, 1), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0), 1.0F)
		.build();
	public static final Item SCULK_VIAL = new BloodFoodItem(new BloodItemSettings().recipeRemainder(VIAL).maxCount(16).food(SCULK).blood(SCULK_BLOOD));
	public static final FoodComponent INK = new FoodComponent.Builder().alwaysEdible()
		.hunger(0)
		.saturationModifier(0)
		.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 1), 1.0F)
		.build();
	public static final BloodComponent INK_BLOOD = new BloodComponent.Builder().alwaysEdible()
		.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0), 1.0F)
		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0), 1.0F)
		.build();
	public static final Item INK_VIAL = new BloodFoodItem(new BloodItemSettings().recipeRemainder(VIAL).maxCount(16).food(INK).blood(INK_BLOOD));
	public static final Block CARPET = new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
	public static final BlockItem CARPET_ITEM = new BlockItem(CARPET, new FabricItemSettings());
	public static final Block SOGGY_CARPET = new Block(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_BROWN).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
	public static final BlockItem SOGGY_CARPET_ITEM = new BlockItem(SOGGY_CARPET, new FabricItemSettings());
	public static final Block WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem WALLPAPER_ITEM = new BlockItem(WALLPAPER, new FabricItemSettings());
	public static final Block TRIM_WALLPAPER = new WillowispFacingBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem TRIM_WALLPAPER_ITEM = new BlockItem(TRIM_WALLPAPER, new FabricItemSettings());
	public static final Block TORN_WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem TORN_WALLPAPER_ITEM = new BlockItem(TORN_WALLPAPER, new FabricItemSettings());
	public static final Block BLANK_WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem BLANK_WALLPAPER_ITEM = new BlockItem(BLANK_WALLPAPER, new FabricItemSettings());
	public static final Block TRIM_BLANK_WALLPAPER = new WillowispFacingBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem TRIM_BLANK_WALLPAPER_ITEM = new BlockItem(TRIM_BLANK_WALLPAPER, new FabricItemSettings());
	public static final Block CEILING_TILE = new Block(AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.STONE));
	public static final BlockItem CEILING_TILE_ITEM = new BlockItem(CEILING_TILE, new FabricItemSettings());
	public static final Block LAMP = new RedstoneLampBlock(AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).strength(0.3F).sounds(BlockSoundGroup.GLASS).luminance(state -> state.get(Properties.LIT) ? 15 : 0));
	public static final BlockItem LAMP_ITEM = new BlockItem(LAMP, new FabricItemSettings());
	public static final Block LAMP2 = new LightPillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).strength(0.3F).sounds(BlockSoundGroup.GLASS).luminance(state -> state.get(Properties.LIT) ? 15 : 0));
	public static final BlockItem LAMP2_ITEM = new BlockItem(LAMP2, new FabricItemSettings());
	public static final Block CLEAR = new BarrierBlock(AbstractBlock.Settings.create().nonOpaque().allowsSpawning(Blocks::never));
	public static final BlockItem CLEAR_ITEM = new BlockItem(CLEAR, new FabricItemSettings());
	public static final Item DRIED_DIVINE_BLOOD = new Item(new FabricItemSettings());
	public static final Item DIVINE_INGOT = new Item(new FabricItemSettings());
	public static final Item DIVINE_NUGGET = new Item(new FabricItemSettings());
	public static final Item DIVINE_DUST = new Item(new FabricItemSettings());
	public static final Item CONTRACT = new Item(new FabricItemSettings());
	public static final Item SOUL_CONTRACT = new Item(new FabricItemSettings());
	public static final Block DIVINE_BLOCK = new Block(AbstractBlock.Settings.create().mapColor(MapColor.GOLD).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.NETHERITE));
	public static final BlockItem DIVINE_BLOCK_ITEM = new BlockItem(DIVINE_BLOCK, new FabricItemSettings());
	public static final Item AMARITE_AMULET = new Item(new FabricItemSettings());
	public static final FoodComponent BONBON_FOOD_COMPONENT = new FoodComponent.Builder().hunger(1).alwaysEdible().snack().build();
	public static final Item BONBON = new Item(new FabricItemSettings().food(BONBON_FOOD_COMPONENT));
	public static final Block WAVY_WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem WAVY_WALLPAPER_ITEM = new BlockItem(WAVY_WALLPAPER, new FabricItemSettings());
	public static final Block CUBED_WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem CUBED_WALLPAPER_ITEM = new BlockItem(CUBED_WALLPAPER, new FabricItemSettings());
	public static final Block LEON_CUBE = new LeonCubeFacingBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(0.8F).sounds(BlockSoundGroup.AMETHYST_BLOCK));
	public static final BlockItem LEON_CUBE_ITEM = new BlockItem(LEON_CUBE, new FabricItemSettings());
	

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello! :3");

		Registry.register(Registries.ITEM, new Identifier("willowisp", "syringe"), SYRINGE);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "vial"), VIAL);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "mortal_vial"), MORTAL_VIAL);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "vampire_vial"), VAMP_VIAL);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "divine_vial"), DIVINE_VIAL);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "god_vial"), GOD_VIAL);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "sculk_vial"), SCULK_VIAL);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "ink_vial"), INK_VIAL);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "carpet"), CARPET);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "carpet"), CARPET_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "wallpaper"), WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "wallpaper"), WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "soggy_carpet"), SOGGY_CARPET);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "soggy_carpet"), SOGGY_CARPET_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "trim_wallpaper"), TRIM_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "trim_wallpaper"), TRIM_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "torn_wallpaper"), TORN_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "torn_wallpaper"), TORN_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "blank_wallpaper"), BLANK_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "blank_wallpaper"), BLANK_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "trim_blank_wallpaper"), TRIM_BLANK_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "trim_blank_wallpaper"), TRIM_BLANK_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "ceiling_tile"), CEILING_TILE);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "ceiling_tile"), CEILING_TILE_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "lamp"), LAMP);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "lamp"), LAMP_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "lamp2"), LAMP2);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "lamp2"), LAMP2_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "clear"), CLEAR);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "clear"), CLEAR_ITEM);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "dried_divine_blood"), DRIED_DIVINE_BLOOD);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "divine_ingot"), DIVINE_INGOT);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "divine_nugget"), DIVINE_NUGGET);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "divine_dust"), DIVINE_DUST);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "contract"), CONTRACT);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "soul_contract"), SOUL_CONTRACT);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "divine_block"), DIVINE_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "divine_block"), DIVINE_BLOCK_ITEM);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "amarite_amulet"), AMARITE_AMULET);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "strawberry_bonbon"), BONBON);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "wavy_wallpaper"), WAVY_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "wavy_wallpaper"), WAVY_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "cubed_wallpaper"), CUBED_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "cubed_wallpaper"), CUBED_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("willowisp", "leon_cube"), LEON_CUBE);
		Registry.register(Registries.ITEM, new Identifier("willowisp", "leon_cube"), LEON_CUBE_ITEM);

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && LootTables.ANCIENT_CITY_CHEST.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
                .with(ItemEntry.builder(DRIED_DIVINE_BLOOD).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))
				.with(EmptyEntry.builder().weight(19));
 				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && LootTables.END_CITY_TREASURE_CHEST.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
                .with(ItemEntry.builder(DRIED_DIVINE_BLOOD).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))
				.with(EmptyEntry.builder().weight(14));
 				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && LootTables.ANCIENT_CITY_ICE_BOX_CHEST.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
                .with(ItemEntry.builder(DIVINE_VIAL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))))
				.with(EmptyEntry.builder().weight(9));
 				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && LootTables.JUNGLE_TEMPLE_CHEST.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
                .with(ItemEntry.builder(LEON_CUBE_ITEM).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))))
				.with(EmptyEntry.builder().weight(999));
 				tableBuilder.pool(poolBuilder);
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher,registryAccess,environment)->{
			dispatcher.register(
			CommandManager.literal("blood")
				.requires(source -> source.hasPermissionLevel(2))
				.then(
					CommandManager.literal("get")
						.executes(context -> getBlood(context.getSource(), (context.getSource().getPlayerOrThrow())))
						.then(
							CommandManager.argument("target", EntityArgumentType.entity())
								.executes(context -> getBlood(context.getSource(), EntityArgumentType.getPlayer(context, "target")))
						)
				)
				.then(
					CommandManager.literal("set")
						.then(
							CommandManager.argument("target", EntityArgumentType.entity())
								.then(
									CommandManager.argument("blood_type", StringArgumentType.word())
										.suggests(new BloodSuggestionProvider())
										.executes(
											context -> setBlood(
												context.getSource(),
												EntityArgumentType.getPlayer(context, "target"),
												StringArgumentType.getString(context, "blood_type")
											)
										)
									)
						)
				));});


		WillowispItemGroups.initialize();
		addStrippables();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	 public static void addStrippables() {
        AxeItem.STRIPPED_BLOCKS = new HashMap<>(AxeItem.STRIPPED_BLOCKS);
        AxeItem.STRIPPED_BLOCKS.put(WALLPAPER, TORN_WALLPAPER);
    }
	public static int getBlood(ServerCommandSource source, PlayerEntity target) throws CommandSyntaxException {
		source.sendFeedback(() -> Text.translatable("command.willowisp.getBloodFeedback",target.getName(),new BloodData(target, "").getBloodType(target).toString()), false);
		return 1;
	}
	public static int setBlood(ServerCommandSource source, PlayerEntity target, String bloodType){
		new BloodData(target, bloodType).setBloodType(target, bloodType);
		source.sendFeedback(() -> Text.translatable("command.willowisp.setBloodFeedback",target.getName(),bloodType.toString()), true);
		return 1;
	}
}