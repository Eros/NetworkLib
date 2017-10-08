package com.connorlinfoot.networklib.Utils;

import com.connorlinfoot.networklib.NetworkLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*
 * NetworkLib 
 * Created by George at 8:11 PM on 08-Oct-17  
 * Copyright (C) 2017 RapidTheNerd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class AnimatedTitle {

    //general stuff for the components
    private HashMap<UUID, Integer> aProgress;
    private List<String> title;
    private List<String> subtitle;
    private int fadeIn, stay, fadeOut;
    private long delay;
    private long period;

    //static = ass | constructors = bae
    public AnimatedTitle(List<String> title, List<String> subtitle, int fadeIn, int stay, int fadeOut, long delay, long period) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
        this.delay = delay;
        this.period = period;
    }

    public void send(final Player p){
        aProgress = new HashMap<>();
        aProgress.put(p.getUniqueId(), 0);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!aProgress.containsKey(p.getUniqueId())){
                    cancel();
                    return;
                }
                int progress = aProgress.get(p.getUniqueId());
                if(title.size() > progress || subtitle.size() > progress){
                    new Title(title.get(progress), subtitle.get(progress), fadeIn, stay, fadeOut).send(p);
                }
            }
        }.runTaskTimer(NetworkLib.getLib(), delay, period);
    }
    public void sendToAllPlayers(){
        for(Player player : Bukkit.getOnlinePlayers()){
            send(player);
        }
    }
}
