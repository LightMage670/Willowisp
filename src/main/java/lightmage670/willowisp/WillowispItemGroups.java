package lightmage670.willowisp;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class WillowispItemGroups {
    public static final ItemGroup WILLOWISP_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(Willowisp.DIVINE_VIAL))
        .displayName(Text.translatable("itemGroup.willowisp.willowisp_group"))
        .entries((content, entries) -> {
            entries.add(Willowisp.SYRINGE);
            entries.add(Willowisp.VIAL);
            entries.add(Willowisp.MORTAL_VIAL);
            entries.add(Willowisp.DIVINE_VIAL);
            entries.add(Willowisp.GOD_VIAL);
            entries.add(Willowisp.VAMP_VIAL);
            entries.add(Willowisp.SCULK_VIAL);
            entries.add(Willowisp.INK_VIAL);
            entries.add(Willowisp.DRIED_DIVINE_BLOOD);
            entries.add(Willowisp.DIVINE_INGOT);
            entries.add(Willowisp.DIVINE_NUGGET);
            entries.add(Willowisp.DIVINE_DUST);
            entries.add(Willowisp.DIVINE_BLOCK_ITEM);
            entries.add(Willowisp.CONTRACT);
            entries.add(Willowisp.SOUL_CONTRACT);
            entries.add(Willowisp.CARPET_ITEM);
            entries.add(Willowisp.SOGGY_CARPET_ITEM);
            entries.add(Willowisp.WALLPAPER_ITEM);
            entries.add(Willowisp.TRIM_WALLPAPER_ITEM);
            entries.add(Willowisp.TORN_WALLPAPER_ITEM);
            entries.add(Willowisp.BLANK_WALLPAPER_ITEM);
            entries.add(Willowisp.TRIM_BLANK_WALLPAPER_ITEM);
            entries.add(Willowisp.WAVY_WALLPAPER_ITEM);
            entries.add(Willowisp.CUBED_WALLPAPER_ITEM);
            entries.add(Willowisp.CEILING_TILE_ITEM);
            entries.add(Willowisp.LAMP_ITEM);
            entries.add(Willowisp.LAMP2_ITEM);
            entries.add(Willowisp.CLEAR_ITEM);
            entries.add(Willowisp.AMARITE_AMULET);
            entries.add(Willowisp.BONBON);
            
        })
        .build();

        public static void initialize() {
            Registry.register(Registries.ITEM_GROUP, new Identifier("tutorial", "test_group"), WILLOWISP_GROUP);
        }
}