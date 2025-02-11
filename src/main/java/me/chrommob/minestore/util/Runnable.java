package me.chrommob.minestore.util;

import me.chrommob.minestore.MineStore;
import me.chrommob.minestore.data.Config;
import me.chrommob.minestore.gui.BuyListener;
import me.chrommob.minestore.gui.Currency;
import me.chrommob.minestore.mysql.MySQLData;
import me.chrommob.minestore.mysql.connection.ConnectionPool;
import me.chrommob.minestore.placeholders.listener.DonationGoalListener;
import me.chrommob.minestore.placeholders.listener.LastDonatorListener;
import me.chrommob.minestore.placeholders.listener.TopDonoListener;
import me.chrommob.minestore.weblistener.Listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//Runnable to execute plugin functions
public class Runnable {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void runListener(String load) {
        executor.scheduleAtFixedRate(() -> {
            if (load.equalsIgnoreCase("web") && Config.isEmpty()) {
                Listener.run();
            }
            if (Config.isGuiEnabled()) {
                BuyListener.run();
                Currency.run();
            }
            if (Config.isPlaceholderPresent()) {
                TopDonoListener.run();
                DonationGoalListener.run();
                LastDonatorListener.run();
            }
            if (Config.isVaultPresent() && MySQLData.isEnabled()) {
                MineStore.instance.getUserManager().updateAll();
                ConnectionPool.updateTable();
            }
        }, 0, 30, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(() -> {
            if (load.equalsIgnoreCase("web") && !Config.isEmpty()) {
                Listener.run();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
