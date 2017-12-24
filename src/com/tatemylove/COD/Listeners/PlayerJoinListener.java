package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PlayerJoinListener implements Listener {

    Main main;
    public PlayerJoinListener (Main m){
        main = m;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Main.kills.put(p.getName(), 0);
        Main.deaths.put(p.getName(), 0);
        Main.killStreak.put(p.getName(), 0);
        if(ThisPlugin.getPlugin().getConfig().getBoolean("auto-join")){
            main.WaitingPlayers.add(e.getPlayer());
            e.getPlayer().sendMessage(main.prefix);
        }
        File file = new File("plugins/COD/arenas.yml");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.readLine() == null){
                e.getPlayer().sendMessage(main.prefix + "§2COD is disabled because you don't have any Arenas!! "+"\n" + "         §4Create an arena and then type §c/cod enable");
            }

        }catch(Exception ei){
            ei.printStackTrace();
        }

        if(main.getConfig().getBoolean("MySQL.Enabled")){

        }else{
            StatsFile.getData().set(p.getUniqueId().toString() + ".Wins", 0);
            StatsFile.getData().set(p.getUniqueId().toString() + ".Kills", 0);
            StatsFile.getData().set(p.getUniqueId().toString() + ".Deaths", 0);
            StatsFile.getData().set(p.getUniqueId().toString() + ".Level", 1);
            StatsFile.getData().set(p.getUniqueId().toString() + ".EXP", 0);
            StatsFile.saveData();
            StatsFile.reloadData();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        MainCommand cmd = new MainCommand(main);

        GetLobby lobby = new GetLobby(main);
        if(main.PlayingPlayers.contains(p)){
            main.PlayingPlayers.remove(p);
            p.teleport(lobby.getLobby(p));
            p.getInventory().clear();
            if(cmd.savedInventory.containsKey(p)) {
                p.getInventory().setContents(cmd.savedInventory.get(p));
            }
            if(cmd.armorSaved.containsKey(p)){
                p.getInventory().setArmorContents(cmd.armorSaved.get(p));
            }
        }else if(main.WaitingPlayers.contains(p)){
            main.WaitingPlayers.remove(p);
            p.getInventory().clear();
            if(cmd.savedInventory.containsKey(p)) {
                p.getInventory().setContents(cmd.savedInventory.get(p));
            }
            if(cmd.armorSaved.containsKey(p)){
                p.getInventory().setArmorContents(cmd.armorSaved.get(p));
            }
            p.teleport(lobby.getLobby(p));
            p.getInventory().clear();
        }
    }

}
