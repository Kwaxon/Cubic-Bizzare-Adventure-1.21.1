package kwax.cba.item;

import kwax.cba.CubicBizzareAdventure;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item Arrow_Shard = registerItem("arrow_shard", new ArrowShard(new Item.Settings().maxCount(4)));
    public static final Item Arrow_Shard_1 = registerItem("arrow_shard_1", new Item(new Item.Settings().maxCount(4)));
    public static final Item Arrow_Shard_2 = registerItem("arrow_shard_2", new Item(new Item.Settings().maxCount(4)));
    public static final Item Raw_Meteorite = registerItem("raw_meteorite", new Item(new Item.Settings()));

//
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CubicBizzareAdventure.MOD_ID, name), item);
    }

    public static void  registerModItems() {
        CubicBizzareAdventure.LOGGER.info("Registering ModItems");
    }
}
