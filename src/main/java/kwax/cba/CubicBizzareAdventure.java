package kwax.cba;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import kwax.cba.bloodlines.Bloodlines;
import kwax.cba.component.ModDataComponentTypes;
import kwax.cba.misc.ModCommands;
import kwax.cba.misc.ModStates;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kwax.cba.block.ModBlocks;
import kwax.cba.item.ModItemGroups;
import kwax.cba.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

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
		ModCommands.registerCommands();

		LOGGER.info("test 0.0.0.1v");
		LOGGER.info("Hello Fabric world!");

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.getPlayer();
			ModStates state = ModStates.getServerState(server);

			CubicBizzareAdventure.LOGGER.info(player.getUuid().toString());

			String[] table = Bloodlines.getBloodlines();

			if (!state.joinedPlayers.contains(player.getUuid())) {
				state.joinedPlayers.add(player.getUuid());

				String family = table[player.getRandom().nextInt(table.length)];
				state.bloodlines.put(player.getUuid(), family);

				state.markDirty();

				player.sendMessage(Text.literal("You were born as "+ family));
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