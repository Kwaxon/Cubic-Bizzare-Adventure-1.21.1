package kwax.cba.item;

import kwax.cba.CubicBizzareAdventure;
import kwax.cba.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup Cubic_Bizzare_Adventure = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(CubicBizzareAdventure.MOD_ID, "cubic_bizzare_adventure"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.Arrow_Shard))
                    .displayName(Text.translatable("itemgroup.cba.cubic_bizzare_adventure"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.Arrow_Shard);
                        entries.add(ModItems.Arrow_Shard_1);
                        entries.add(ModItems.Arrow_Shard_2);
                        entries.add(ModItems.Raw_Meteorite);
                    }).build());

    public static final ItemGroup Cubic_Bizzare_Adventure_Blocks = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(CubicBizzareAdventure.MOD_ID, "cubic_bizzare_adventure_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.Meteor_Ore_Block))
                    .displayName(Text.translatable("itemgroup.cba.cubic_bizzare_adventure_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.Meteor_Ore_Block);

                    }).build());

    public static void  registerItemGroup() {
        CubicBizzareAdventure.LOGGER.info("Registring item groups for "+ CubicBizzareAdventure.MOD_ID);
    }
}
