package com.garbagemule.NoRegen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.plugin.java.JavaPlugin;

public class NoRegen extends JavaPlugin
{
    public NoRegen() {}
    
    public void onEnable()
    {
        File worldFile;
        List<World> worldList = new LinkedList<World>();
        
        // FILE CHECK/CREATION
        try
        {
            new File("plugins" + File.separator + "NoRegen").mkdir();
            worldFile = new File("plugins" + File.separator + "NoRegen" + File.separator + "worlds.txt");
            
            if (!worldFile.exists())
            {
                // Create the file.
                worldFile.createNewFile();
                
                // Open the file.
                FileWriter     fw = new FileWriter(worldFile);
                BufferedWriter bw = new BufferedWriter(fw);
                
                // Write the default world to the file.
                bw.write(getServer().getWorlds().get(0).getName());
                
                // Close the file.
                bw.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("[NoRegen] Fatal error: worlds.txt could not be created or written to!");
            return;
        }
        
        // FILE READ
        try
        {
            // Open the file.
            FileReader     fr = new FileReader(worldFile);
            BufferedReader br = new BufferedReader(fr);
            
            /* For every String in the file, if the String is the name
             * of a World object, add it to the worldList. Otherwise,
             * print an error. */
            World w;
            String s = br.readLine();
            while (s != null)
            {
                w = getServer().getWorld(s);
                
                if (w == null)
                {
                    System.out.println("[NoRegen] World not found: " + s);
                    s = br.readLine();
                    continue;
                }
                
                worldList.add(getServer().getWorld(s));
                s = br.readLine();
            }
        }
        catch (Exception e)
        {
            System.out.println("[NoRegen] Fatal error: worlds.txt could not be found or read from!");
            return;
        }
        
        // SETTINGS
        CraftWorld cw;
        net.minecraft.server.World nmsWorld;
        for (World w : worldList)
        {
            cw = (CraftWorld) w;
            
            // If the world isn't null, get the n.m.s.World
            if (w != null)
            {
                nmsWorld = cw.getHandle();
            }
            else
            {
                System.out.println(w + " is not a real world.");
                continue;
            }
            
            /* allowMonsters = false means no monsters will spawn, and
             * spawnMonsters > 0 means health will not be regenerated. */
            nmsWorld.allowMonsters = false;
            nmsWorld.spawnMonsters = 1;
        }
        
        System.out.println("[NoRegen] Enabled.");
    }
    
    public void onDisable()
    {
        System.out.println("[NoRegen] Disabled.");
    }
}