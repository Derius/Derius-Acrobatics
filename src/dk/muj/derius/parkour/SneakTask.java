package dk.muj.derius.parkour;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.ModuloRepeatTask;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.engine.MsgEngine;
import dk.muj.derius.util.AbilityUtil;
import dk.muj.derius.util.LevelUtil;

public class SneakTask extends ModuloRepeatTask
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
		return DeriusAcrobatics.get();
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
				DeriusAcrobatics.sneakTime.remove(id);
				return;
			}
			
			String id = player.getUniqueId().toString();
			
			Short unit = DeriusAcrobatics.sneakTime.get(id);
			if (unit == null) unit = 0;
			unit++;
			
			DeriusAcrobatics.sneakTime.put(id, unit);
		
			Optional<JumpSetting> setting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), dplayer.getLvl(ParkourSkill.get()));
			if ( ! setting.isPresent()) return;
			unit = (short) (unit > setting.get().getMaxUnits() ? setting.get().getMaxUnits() : unit);
			String color = "<green>";
			double maxUnits = setting.get().getMaxVector()/setting.get().getVectorPerUnit();
			if (unit < Math.ceil(maxUnits/4.0*2.0)) color = "<yellow>";
			if (unit < Math.ceil(maxUnits/4.0)) color = "<red>";
			
			String msg = color + Txt.repeat("|", unit);
			msg += "<black>" + Txt.repeat("|", setting.get().getMaxUnits() - unit);
			MsgEngine.sendActionBar(dplayer,  msg);
		}
		
	}
	
	// -------------------------------------------- //
	// EVENT
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onJump(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		Entity entity = player; // Their isOnGround method is better.
		DPlayer dplayer = DeriusAPI.getDPlayer(player);
		if ( ! player.isSneaking() || ! entity.isOnGround()) return;
		
		String id = player.getUniqueId().toString();
		
		if ( ! MUtil.isSameBlock(event)) DeriusAcrobatics.sneakTime.remove(id);
		
		Short unit = DeriusAcrobatics.sneakTime.get(id);
		if (unit == null || unit < 2) return;
		
		if (event.getTo().getY() - event.getFrom().getY() <= 0.0001)
		{
			return;
		}

		AbilityUtil.activateAbility(dplayer, JumpAbility.get(), unit, false);
		MsgEngine.sendActionBar(dplayer,  "");
	}

}
