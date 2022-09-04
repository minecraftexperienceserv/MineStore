package me.chrommob.minestore.commandexecution;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;

public class Command {
    public static HashMap<String, ArrayList<String>> runLater;

    public static void online(String command) {
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MineStore"), () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }, 100L);
    }

    public static void offline(String username, String command) {
        Manager.add(username, command);
    }
}
