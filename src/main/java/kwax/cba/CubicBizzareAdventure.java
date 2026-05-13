package kwax.cba;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import kwax.cba.component.ModDataComponentTypes;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kwax.cba.block.ModBlocks;
import kwax.cba.item.ModItemGroups;
import kwax.cba.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

public class CubicBizzareAdventure implements ModInitializer {
	public static final String MOD_ID = "cba";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Set<UUID> FROZEN_PLAYERS = new HashSet<>();

	private boolean isFirstJoin(ServerPlayerEntity player) {
		return true;
	};

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroup();
		ModDataComponentTypes.registerComponents();

		LOGGER.info("test 0.0.0.1v");
		LOGGER.info("Hello Fabric world!");

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.getPlayer();

			NbtCompound data = player.writeNbt(new NbtCompound());

			if (!data.contains("first_join")) {
				data.putString("Stand", "Standless");
				data.putBoolean("first_join", true);
				player.readNbt(data);
				player.sendMessage(Text.literal("You got "+ data.getString("Stand")));
			}
			else {
				player.sendMessage(Text.literal("Welcome "+ player.getName().getString()));
			}

		});


		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				if (CubicBizzareAdventure.FROZEN_PLAYERS.contains(player.getUuid())) {
					player.setVelocity(0, 0, 0);
					player.velocityModified = true;
				}
			}
		});
	}
}