package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeathListener implements Listener {
    Main main;
    public PlayerDeathListener(Main m){
        main = m;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        Player pp = e.getEntity().getKiller();
        final CraftPlayer craftPlayer = (CraftPlayer)p;

        TDM tdm = new TDM(main);
        if((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
            if (((tdm.blueTeam.contains(p) && (tdm.redTeam.contains(pp))) || (tdm.redTeam.contains(p)) && (tdm.blueTeam.contains(pp)))) {
                int kills = main.kills.get(pp.getName());
                kills++;
                main.kills.put(p.getName(), kills);
            }
            if(tdm.redTeam.contains(p)){
                if(tdm.blueTeam.contains(pp)){
                    main.BlueTeamScore++;
                }
            }else if(tdm.blueTeam.contains(p)){
                if(tdm.redTeam.contains(pp)){
                    main.RedTeamScore++;
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(p.isDead()){
                    craftPlayer.getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                }
            }
        });
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        TDM tdm = new TDM(main);
        GetArena getArena = new GetArena();
        if(tdm.redTeam.contains(p)){
            e.setRespawnLocation(getArena.getRedSpawn(p));
        }else if(tdm.blueTeam.contains(p)){
            e.setRespawnLocation(getArena.getBlueSpawn(p));
        }
    }
}
