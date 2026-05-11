package kwax.cba.item;

import kwax.cba.CubicBizzareAdventure;
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
                    }).build());

    public static void  registerItemGroup() {
        CubicBizzareAdventure.LOGGER.info("Registring item groups for "+ CubicBizzareAdventure.MOD_ID);
    }
}
