package tech.samgosden.magestones.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.text.Text;

public class MageStonesClient implements ClientModInitializer {
    /**
     * This method is called when the client mod is initialized.
     * It registers a listener for the JOIN event of the ClientPlayConnectionEvents.
     * When the event is triggered, it retrieves the client player entity and sends a disclaimer message to the player's chat.
     */
    @Override
    public void onInitializeClient() {
        // Register a listener for the JOIN event of the ClientPlayConnectionEvents
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {

            // Get the client player entity from the client instance
            ClientPlayerEntity player = client.player;

            // Check if the player is not null to avoid NullPointerException
            if (player != null) {
                // Create a text message for the in-development disclaimer
                Text disclaimer = Text.literal("Mage Stones is currently in development. Use with caution.");

                // Send the disclaimer message to the player's chat
                // The second parameter is set to false in order to prevent the message from being logged to the console
                player.sendMessage(disclaimer, false);
            }
        });

        MageStonesCutout.registerCutouts();
    }
}