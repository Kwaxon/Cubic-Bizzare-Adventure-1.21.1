package kwax.cba;

import kwax.cba.block.ModBlocks;
import kwax.cba.item.ModItemGroups;
import kwax.cba.item.ModItems;
import kwax.cba.structure.ModStructures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CubicBizzareAdventure implements ModInitializer {
	public static final String MOD_ID = "cba";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Set<UUID> FROZEN_PLAYERS = new HashSet<>();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroup();
		ModStructures.registerModStructures();

		LOGGER.info("test 0.0.0.1v");
		LOGGER.info("Hello Fabric world!");

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