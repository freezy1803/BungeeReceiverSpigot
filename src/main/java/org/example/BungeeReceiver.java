package org.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class BungeeReceiver extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        // Create a DataInputStream to read the data from the byte array
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            // Read the subchannel (should be "RunCommand")
            String subchannel = in.readUTF();
            if (subchannel.equals("RunCommand")) {
                // Read the command from the input stream
                String command = in.readUTF();
                // Run the command
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
