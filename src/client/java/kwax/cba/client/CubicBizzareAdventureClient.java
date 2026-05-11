package kwax.cba.client;

import kwax.cba.CubicBizzareAdventure;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CubicBizzareAdventureClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (CubicBizzareAdventure.FROZEN_PLAYERS.contains(client.player.getUuid())) {
					client.player.setVelocity(0, 0, 0);
					client.player.velocityModified = true;
					// Cancel all movement input
					client.options.forwardKey.setPressed(false);
					client.options.backKey.setPressed(false);
					client.options.leftKey.setPressed(false);
					client.options.rightKey.setPressed(false);
					client.options.jumpKey.setPressed(false);
				}
			}
		});
	}
}