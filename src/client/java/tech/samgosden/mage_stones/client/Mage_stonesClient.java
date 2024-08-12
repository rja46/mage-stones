package tech.samgosden.mage_stones.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.text.Text;

public class Mage_stonesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Get the client instance
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {

            // Get the client player entity
            ClientPlayerEntity player = client.player;

            // Check if the player is not null
            if (player != null) {
                // Create a text message for the in-development disclaimer
                Text disclaimer = Text.literal("This mod is currently in development. Use with caution.");

                // Send the disclaimer message to the player's chat
                player.sendMessage(disclaimer, false);
            }
        });
    }
}
