package com.connorlinfoot.networklib.Utils;

import org.bukkit.Bukkit;

/*
 * NetworkLib 
 * Created by George at 7:56 PM on 08-Oct-17  
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
 enum SI {

     MINECRAFT("net.minecraft.server." + getServerVersions()),
     CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersions());

     private final String path;
     SI(String path){
         this.path = path;
     }

     @Override
     public String toString(){
         return path;
     }
     public Class<?> getClass(String className) throws ClassNotFoundException {
         return Class.forName(toString() + "." + className);
     }
     public static String getServerVersions(){
         return Bukkit.getServer().getClass().getPackage().getName().substring(23);
     }
}
