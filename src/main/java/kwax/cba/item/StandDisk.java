package kwax.cba.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StandDisk extends Item {
    public StandDisk(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}