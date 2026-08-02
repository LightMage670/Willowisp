package lightmage670.smpcolon3;

import lightmage670.smpcolon3.attachment.bloodAttachments.BloodData;
import lightmage670.smpcolon3.block.Colon3FacingBlock;
import lightmage670.smpcolon3.block.LightPillarBlock;
import lightmage670.smpcolon3.item.Syringe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.command.argument.EntityArgumentType;
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



public class Smpcolon3 implements ModInitializer {
	public static final String MOD_ID = "smpcolon3";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item SYRINGE = new Syringe(new FabricItemSettings().maxCount(1));
	public static final Item VIAL = new Item(new FabricItemSettings().maxCount(16));
	public static final Item MORTAL_VIAL = new Item(new FabricItemSettings().recipeRemainder(VIAL).maxCount(16));
	public static final Item VAMP_VIAL = new Item(new FabricItemSettings().recipeRemainder(VIAL).maxCount(16));
	public static final Item DIVINE_VIAL = new Item(new FabricItemSettings().recipeRemainder(VIAL).maxCount(16));
	public static final Item GOD_VIAL = new Item(new FabricItemSettings().recipeRemainder(VIAL).maxCount(16));
	public static final Item SCULK_VIAL = new Item(new FabricItemSettings().recipeRemainder(VIAL).maxCount(16));
	public static final Item INK_VIAL = new Item(new FabricItemSettings().recipeRemainder(VIAL).maxCount(16));
	public static final Block CARPET = new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
	public static final BlockItem CARPET_ITEM = new BlockItem(CARPET, new FabricItemSettings());
	public static final Block SOGGY_CARPET = new Block(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_BROWN).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
	public static final BlockItem SOGGY_CARPET_ITEM = new BlockItem(SOGGY_CARPET, new FabricItemSettings());
	public static final Block WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem WALLPAPER_ITEM = new BlockItem(WALLPAPER, new FabricItemSettings());
	public static final Block TRIM_WALLPAPER = new Colon3FacingBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem TRIM_WALLPAPER_ITEM = new BlockItem(TRIM_WALLPAPER, new FabricItemSettings());
	public static final Block TORN_WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem TORN_WALLPAPER_ITEM = new BlockItem(TORN_WALLPAPER, new FabricItemSettings());
	public static final Block BLANK_WALLPAPER = new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
	public static final BlockItem BLANK_WALLPAPER_ITEM = new BlockItem(BLANK_WALLPAPER, new FabricItemSettings());
	public static final Block TRIM_BLANK_WALLPAPER = new Colon3FacingBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable());
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
	

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello! :3");

		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "syringe"), SYRINGE);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "vial"), VIAL);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "mortal_vial"), MORTAL_VIAL);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "vampire_vial"), VAMP_VIAL);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "divine_vial"), DIVINE_VIAL);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "god_vial"), GOD_VIAL);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "sculk_vial"), SCULK_VIAL);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "ink_vial"), INK_VIAL);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "carpet"), CARPET);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "carpet"), CARPET_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "wallpaper"), WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "wallpaper"), WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "soggy_carpet"), SOGGY_CARPET);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "soggy_carpet"), SOGGY_CARPET_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "trim_wallpaper"), TRIM_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "trim_wallpaper"), TRIM_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "torn_wallpaper"), TORN_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "torn_wallpaper"), TORN_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "blank_wallpaper"), BLANK_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "blank_wallpaper"), BLANK_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "trim_blank_wallpaper"), TRIM_BLANK_WALLPAPER);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "trim_blank_wallpaper"), TRIM_BLANK_WALLPAPER_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "ceiling_tile"), CEILING_TILE);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "ceiling_tile"), CEILING_TILE_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "lamp"), LAMP);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "lamp"), LAMP_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "lamp2"), LAMP2);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "lamp2"), LAMP2_ITEM);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "clear"), CLEAR);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "clear"), CLEAR_ITEM);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "dried_divine_blood"), DRIED_DIVINE_BLOOD);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "divine_ingot"), DIVINE_INGOT);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "divine_nugget"), DIVINE_NUGGET);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "divine_dust"), DIVINE_DUST);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "contract"), CONTRACT);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "soul_contract"), SOUL_CONTRACT);
		Registry.register(Registries.BLOCK, new Identifier("smpcolon3", "divine_block"), DIVINE_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "divine_block"), DIVINE_BLOCK_ITEM);
		Registry.register(Registries.ITEM, new Identifier("smpcolon3", "amarite_amulet"), AMARITE_AMULET);

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


		Smpcolon3ItemGroups.initialize();
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
		source.sendFeedback(() -> Text.translatable(target.getName() + "'s blood type: " + new BloodData(target, "").getBloodType(target).toString()), true);
		return 1;
	}
	public static int setBlood(ServerCommandSource source, PlayerEntity target, String bloodType){
		new BloodData(target, bloodType).setBloodType(target, bloodType);
		source.sendFeedback(() -> Text.translatable("Set " + target.getName() + "'s blood type to " + bloodType.toString()), true);
		return 1;
	}
}