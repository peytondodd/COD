package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.KillArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Leveling.PlayerLevels;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.GameTime;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActivePinger extends BukkitRunnable{
    Main main;
    private static ActivePinger pinger = null;
    public ActivePinger(Main m){
        main = m;
        pinger = ActivePinger.this;
    }

    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            MainRunnable runnable = new MainRunnable(main);
            if(BaseArena.type == BaseArena.ArenaType.TDM) {
                if (main.RedTeamScore > 30) {
                    TDM tdm = new TDM(main);
                    tdm.endTDM();
                    runnable.stopGameTime();
                } else if (main.BlueTeamScore > 30) {
                    TDM tdm = new TDM(main);
                    tdm.endTDM();
                    runnable.stopGameTime();
                }
            }

            for(Player pp : main.PlayingPlayers){
                PlayerLevels playerLevels = new PlayerLevels(main);
                int exp = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".EXP");
                int level = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Level");
                if (level == 1) {
                    if (exp >= playerLevels.levelTwo) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 2) {
                    if (exp >= playerLevels.levelThree) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 3) {
                    if (exp >= playerLevels.levelFour) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 4) {
                    if (exp >= playerLevels.levelFive) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 5) {
                    if (exp >= playerLevels.levelSix) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 6) {
                    if (exp >= playerLevels.levelSeven) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 7) {
                    if (exp >= playerLevels.levelEight) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 8) {
                    if (exp >= playerLevels.levelNine) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 9) {
                    if (exp >= playerLevels.levelTen) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                }
                TDM tdm = new TDM(main);
                KillArena killArena = new KillArena(main);
                if(BaseArena.type == BaseArena.ArenaType.TDM) {
                    if (TDM.redTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + tdm.getBetterTeam(), pp);
                    } else if (TDM.blueTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + tdm.getBetterTeam(), pp);
                    }
                }else if(BaseArena.type == BaseArena.ArenaType.KC){
                    if (KillArena.redTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + killArena.getBetterTeam(), pp);
                    } else if (KillArena.blueTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + killArena.getBetterTeam(), pp);
                    }
                }
            }
        }
    }
}
