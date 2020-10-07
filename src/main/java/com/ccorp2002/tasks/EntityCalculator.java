package com.ccorp2002.tasks;

import com.ccorp2002.Simplestone;
import com.ccorp2002.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.material.Diode;
import org.bukkit.material.Ladder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EntityCalculator extends BukkitRunnable {

	private Simplestone plugin;
	private List<Entity> following = new ArrayList<Entity>();
	
	public EntityCalculator(Simplestone plugin){
		this.plugin = plugin;
	}
	
	/**
	 * Returns all entities from all worlds
	 * @return list of all entities
	 */
	public Collection<? extends Entity> getAllEntities(){
		List<Entity> ent = new ArrayList<Entity>();
		for(World w : Bukkit.getWorlds()){
			ent.addAll(w.getEntities());
		}
		return ent;
	}
	
	public void run() {
		if(plugin.getTpsCounter().getAverageTPS() < plugin.getConfig().getDouble("Settings.Other.MinimumTPS")) return;
		
		following.addAll(getAllEntities());
		Iterator<Entity> iterator = following.iterator();
		while(iterator.hasNext()){
			Entity i = iterator.next();

			if(i.isDead() || !i.isValid()){
				iterator.remove();
				continue;
			}

			Block myBlock = i.getLocation().getBlock();

			if(myBlock.getType() != Material.AIR){
				if(myBlock.getType() == Material.LEGACY_DIODE_BLOCK_ON || myBlock.getType() == Material.REPEATER){
					if(!plugin.getConfig().getBoolean("Settings.Repeater.Enabled")) continue;
					Diode b = (Diode) myBlock.getState().getData();
					
					if(myBlock.getRelative(b.getFacing()).getType() != Material.REPEATER || myBlock.getRelative(b.getFacing()).getType() !=  Material.LEGACY_DIODE_BLOCK_ON){
						Material m = myBlock.getRelative(b.getFacing()).getType();
						Material m_TWO = myBlock.getRelative(b.getFacing()).getType();
						Material m_THREE = myBlock.getRelative(b.getFacing()).getType();
						Material m_FLAK = myBlock.getRelative(b.getFacing()).getType();;
						Material m_MORTAR = myBlock.getRelative(b.getFacing()).getType();
						if(m == Material.OAK_STAIRS || m == Material.SPRUCE_STAIRS || m == Material.BIRCH_STAIRS || m == Material.JUNGLE_STAIRS || m == Material.LEGACY_WOOD_STAIRS || m == Material.LEGACY_SPRUCE_WOOD_STAIRS || m == Material.LEGACY_BIRCH_WOOD_STAIRS || m == Material.LEGACY_JUNGLE_WOOD_STAIRS || m == Material.DARK_OAK_STAIRS || m == Material.ACACIA_STAIRS || m == Material.WARPED_STAIRS || m == Material.CRIMSON_STAIRS){
							if(!plugin.getConfig().getBoolean("Settings.StairCannon.Enabled")) continue;
							if(i instanceof Item){
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(100 * plugin.getConfig().getDouble("Settings.StairCannon.Force")).add(new Vector(0, 1, 0)));
							}else{
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(400 * plugin.getConfig().getDouble("Settings.StairCannon.Force")).add(new Vector(0, 1, 0)));
							}
                        }
                    if(m_TWO == Material.NETHER_BRICK_STAIRS || m_TWO == Material.LEGACY_SMOOTH_STAIRS || m_TWO == Material.STONE_BRICK_STAIRS || m_TWO == Material.COBBLESTONE_STAIRS){
							if(!plugin.getConfig().getBoolean("Settings.StairCannon2.Enabled")) continue;
							if(i instanceof Item){
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(100 * plugin.getConfig().getDouble("Settings.StairCannon2.Force")).add(new Vector(0, 1, 0)));
							}else{
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(400 * plugin.getConfig().getDouble("Settings.StairCannon2.Force")).add(new Vector(0, 1, 0)));
							}
                        }
                    if(m_THREE == Material.QUARTZ_STAIRS || m_THREE == Material.BLACKSTONE_STAIRS){
							if(!plugin.getConfig().getBoolean("Settings.StairCannon3.Enabled")) continue;
							if(i instanceof Item){
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(100 * plugin.getConfig().getDouble("Settings.StairCannon3.Force")).add(new Vector(0, 1, 0)));
							}else{
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(400 * plugin.getConfig().getDouble("Settings.StairCannon3.Force")).add(new Vector(0, 1, 0)));
							}
            }
                    if(m_FLAK == Material.PURPUR_STAIRS){
							if(!plugin.getConfig().getBoolean("Settings.StairCannonFlak.Enabled")) continue;
							if(i instanceof Item){
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(400 * plugin.getConfig().getDouble("Settings.StairCannonFlak.Force")).add(new Vector(0, 4, 0)));
							}else{
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(100 * plugin.getConfig().getDouble("Settings.StairCannonFlak.Force")).add(new Vector(0, 4, 0)));
							}
            }
                    if(m_MORTAR == Material.BRICK_STAIRS || m_MORTAR == Material.END_STONE_BRICK_STAIRS){
							if(!plugin.getConfig().getBoolean("Settings.StairCannonMortar.Enabled")) continue;
							if(i instanceof Item){
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(175 * plugin.getConfig().getDouble("Settings.StairCannonMortar.Force")).add(new Vector(0, 3, 0)));
							}else{
								i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(200 * plugin.getConfig().getDouble("Settings.StairCannonMortar.Force")).add(new Vector(0, 2, 0)));
							}
            }
					}else{
						if(myBlock.getRelative(BlockFace.DOWN).getType() == Material.IRON_BLOCK && plugin.getConfig().getBoolean("Settings.Repeater.FastEnabled")){
							i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(30 * plugin.getConfig().getDouble("Settings.Repeater.Fast")).add(Utils.centerExcludeFace(i.getLocation(), b.getFacing()).multiply(0.7)));
						}else{
							i.setVelocity(Utils.faceToForce(b.getFacing()).multiply(30 * plugin.getConfig().getDouble("Settings.Repeater.Normal")).add(Utils.centerExcludeFace(i.getLocation(), b.getFacing()).multiply(0.7)));
						}
					}
					
				}	
			}
		}
		following.clear();
	}

}
