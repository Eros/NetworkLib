package com.connorlinfoot.networklib.Utils;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

/*
 * NetworkLib 
 * Created by George at 8:01 PM on 08-Oct-17  
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
public class Title {

    public JSONObject title, subtitle;
    public int fadeIn, stay, fadeOut;

    public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut){
        this.title = convert(title);
        this.subtitle = convert(subtitle);
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public Title(JSONObject title, JSONObject subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(Player player){
        try {
            Preconditions.checkNotNull(player);
            //general stuff for the title packet
            Object handle = player.getClass().getMethod("getHandle").invoke(player),
                    connection = handle.getClass().getField("playerConnection").get(handle);
            Class<?> playPacket = SI.MINECRAFT.getClass("PacketPlayOutTitle"),
                    genericPacket = SI.MINECRAFT.getClass("Packet"),
                    chatComponent = SI.MINECRAFT.getClass("IChatBaseComponent"),
                    serializer = SI.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer"),
                    action = SI.MINECRAFT.getClass("PacketPlayOutTitle$EnumTitleAction");

            //timings and stoof
            Object timesPacket = playPacket.getConstructor(int.class, int.class, int.class).newInstance(fadeIn, stay, fadeOut);
            connection.getClass().getMethod("sendPacket", genericPacket).invoke(connection, timesPacket);

            //now to send the title and such
            if(title != null && !title.isEmpty()){
                Object titleComponent = serializer.getMethod("a", String.class).invoke(null, title.toString()),
                        titlePacket = playPacket.getConstructor(action, chatComponent).newInstance(action.getField("TITLE").get(null), titleComponent);
                connection.getClass().getMethod("sendPacket", genericPacket).invoke(connection, titlePacket);
            }

            if(subtitle != null && !subtitle.isEmpty()){
                Object subtitleComponent = serializer.getMethod("a", String.class).invoke(null, subtitle.toString()),
                        subtitlePacket = playPacket.getConstructor(action, chatComponent).newInstance(action.getField("SUBTITLE").get(null), subtitleComponent);
                connection.getClass().getMethod("sendPacket", genericPacket).invoke(connection, subtitlePacket);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendToAllPlayers(){
        for(Player player : Bukkit.getOnlinePlayers()){
            send(player);
        }
    }

    //allows us to convert json objects into strings if we need it
    private static JSONObject convert(String text){
        JSONObject object = new JSONObject();
        object.put("text", text);
        return object;
    }
}
