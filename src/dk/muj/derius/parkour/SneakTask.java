package dk.muj.derius.parkour;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.engine.MsgEngine;
import dk.muj.derius.lib.Task;
import dk.muj.derius.util.AbilityUtil;
import dk.muj.derius.util.LevelUtil;

public class SneakTask extends Task
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static SneakTask i = new SneakTask();
	public static SneakTask get() { return i; }
	private SneakTask() { super(100); }
	
	// -------------------------------------------- //
	// OVERRIDE: ENGINE
	// -------------------------------------------- //
	
	@Override
	public Plugin getPlugin()
	{
		return DeriusParkour.get();
	}
	
	// -------------------------------------------- //
	// OVERRIDE: TASK
	// -------------------------------------------- //
	
	@Override
	public void invoke(long useless_i_believe)
	{
		for (Player player : MUtil.getOnlinePlayers())
		{
			DPlayer dplayer = DeriusAPI.getDPlayer(player);
			Entity entity = player; // Their isOnGround method is better.
			
			
			if ( ! player.isSneaking() || ! entity.isOnGround() ||  ! AbilityUtil.canPlayerActivateAbility(dplayer, JumpAbility.get(), false))
			{
				
				String id = player.getUniqueId().toString();
				if (DeriusParkour.sneakTime.containsKey(id))
				{
					MsgEngine.sendActionBar(dplayer, "");
					player.removePotionEffect(PotionEffectType.JUMP);
					
				}
				DeriusParkour.sneakTime.remove(id);
				return;
			}
			
			String id = player.getUniqueId().toString();
			
			Short unit = DeriusParkour.sneakTime.get(id);
			if (unit == null) unit = 0;
			unit++;
			
			DeriusParkour.sneakTime.put(id, unit);
		
			Optional<JumpSetting> optSetting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), dplayer.getLvl(ParkourSkill.get()));
			if ( ! optSetting.isPresent()) return;
			JumpSetting setting = optSetting.get();
			unit = (short) (unit > setting.getMaxUnits() ? setting.getMaxUnits() : unit);
			String color = "<green>";
			int maxUnits = setting.getMaxUnits();
			if (unit < Math.ceil(maxUnits/4.0*2.0)) color = "<yellow>";
			if (unit < Math.ceil(maxUnits/4.0)) color = "<red>";
			
			String msg = color + Txt.repeat("|", unit);
			msg += "<black>" + Txt.repeat("|", setting.getMaxUnits() - unit);
			MsgEngine.sendActionBar(dplayer,  msg);
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3, setting.getPotionLevel(unit), false, false), true);
		}
		
	}

}
