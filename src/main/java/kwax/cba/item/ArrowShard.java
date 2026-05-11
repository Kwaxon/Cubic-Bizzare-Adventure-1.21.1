package kwax.cba.item;

import kwax.cba.CubicBizzareAdventure;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class ArrowShard extends Item {

    private static final ParticleEmitterInfo arrow_shard = new ParticleEmitterInfo(Identifier.of("cba", "arrow_shard_use"));


    public ArrowShard(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        CubicBizzareAdventure.FROZEN_PLAYERS.add(user.getUuid());

        itemStack.decrementUnlessCreative(1,user);

        world.playSound(user,
                user.getX(), user.getY(), user.getZ(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.NEUTRAL,
                .05f,
                1f
        );


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                world.playSound(user,
                        user.getX(), user.getY(), user.getZ(),
                        SoundEvents.ITEM_TOTEM_USE,
                        SoundCategory.NEUTRAL,
                        .05f,
                        1.15f
                );
                CubicBizzareAdventure.FROZEN_PLAYERS.remove(user.getUuid());
            };
        }, 2250);


        if (world.isClient) {
            CubicBizzareAdventure.LOGGER.info("Started Effect Arrow_Shard");
            AAALevel.addParticle(world, false, arrow_shard.clone().position(
                    user.getX(), user.getY() + 1d, user.getZ()
            ).scale(.5f));
        }

        return super.use(world, user, hand);
    }
}
