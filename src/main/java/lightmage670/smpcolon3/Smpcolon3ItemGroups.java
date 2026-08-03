package lightmage670.smpcolon3;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class Smpcolon3ItemGroups {
    public static final ItemGroup COLON3_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(Smpcolon3.DIVINE_VIAL))
        .displayName(Text.translatable("itemGroup.smpcolon3.colon3_group"))
        .entries((content, entries) -> {
            entries.add(Smpcolon3.SYRINGE);
            entries.add(Smpcolon3.VIAL);
            entries.add(Smpcolon3.MORTAL_VIAL);
            entries.add(Smpcolon3.DIVINE_VIAL);
            entries.add(Smpcolon3.GOD_VIAL);
            entries.add(Smpcolon3.VAMP_VIAL);
            entries.add(Smpcolon3.SCULK_VIAL);
            entries.add(Smpcolon3.INK_VIAL);
            entries.add(Smpcolon3.DRIED_DIVINE_BLOOD);
            entries.add(Smpcolon3.DIVINE_INGOT);
            entries.add(Smpcolon3.DIVINE_NUGGET);
            entries.add(Smpcolon3.DIVINE_DUST);
            entries.add(Smpcolon3.DIVINE_BLOCK_ITEM);
            entries.add(Smpcolon3.CONTRACT);
            entries.add(Smpcolon3.SOUL_CONTRACT);
            entries.add(Smpcolon3.CARPET_ITEM);
            entries.add(Smpcolon3.SOGGY_CARPET_ITEM);
            entries.add(Smpcolon3.WALLPAPER_ITEM);
            entries.add(Smpcolon3.TRIM_WALLPAPER_ITEM);
            entries.add(Smpcolon3.TORN_WALLPAPER_ITEM);
            entries.add(Smpcolon3.BLANK_WALLPAPER_ITEM);
            entries.add(Smpcolon3.TRIM_BLANK_WALLPAPER_ITEM);
            entries.add(Smpcolon3.WAVY_WALLPAPER_ITEM);
            entries.add(Smpcolon3.CUBED_WALLPAPER_ITEM);
            entries.add(Smpcolon3.CEILING_TILE_ITEM);
            entries.add(Smpcolon3.LAMP_ITEM);
            entries.add(Smpcolon3.LAMP2_ITEM);
            entries.add(Smpcolon3.CLEAR_ITEM);
            entries.add(Smpcolon3.AMARITE_AMULET);
            entries.add(Smpcolon3.BONBON);
            
        })
        .build();

        public static void initialize() {
            Registry.register(Registries.ITEM_GROUP, new Identifier("tutorial", "test_group"), COLON3_GROUP);
        }
}