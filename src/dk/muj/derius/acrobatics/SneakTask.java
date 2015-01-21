package dk.muj.derius.acrobatics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.ModuloRepeatTask;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.acrobatics.entity.JumpSetting;
import dk.muj.derius.acrobatics.entity.MConf;
import dk.muj.derius.entity.MPlayer;
import dk.muj.derius.util.AbilityUtil;
import dk.muj.derius.util.ChatUtil;

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
	
	@SuppressWarnings("deprecation")
	@Override
	public void invoke(long arg0)
	{
		for (Player player : MUtil.getOnlinePlayers())
		{
			MPlayer mplayer = MPlayer.get(player);
			
			if ( ! player.isSneaking() || ! player.isOnGround() ||  ! JumpAbility.get().canPlayerActivateAbility(mplayer))
			{
				String id = player.getUniqueId().toString();
				DeriusAcrobatics.sneakTime.remove(id);
				return;
			}
			
			String id = player.getUniqueId().toString();
			
			Byte unit = DeriusAcrobatics.sneakTime.get(id);
			if (unit == null) unit = 0;
			unit++;
			
			DeriusAcrobatics.sneakTime.put(id, unit);
			
			player.setRemainingAir(unit);
		
			JumpSetting setting = AbilityUtil.getLevelSetting(MConf.get().jumpSteps, mplayer.getLvl(AcrobaticsSkill.get()));
			unit = (byte) (unit > setting.getMaxUnits() ? setting.getMaxUnits() : unit);
			String color = "<green>";
			double maxUnits = setting.getMaxVector()/setting.getVectorPerUnit();
			if (unit < maxUnits/4.0*2.0) color = "<yellow>";
			if (unit < maxUnits/4.0) color = "<red>";
			
			String msg = color + Txt.repeat("|", unit);
			msg += "<black>" + Txt.repeat("|", setting.getMaxUnits() - unit);
			ChatUtil.sendActionBar(player,  msg);
		}
		
	}
	
	// -------------------------------------------- //
	// EVENT
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onJump(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		MPlayer mplayer = MPlayer.get(player);
		if ( ! player.isSneaking() || ! player.isOnGround()) return;
		
		String id = player.getUniqueId().toString();
		
		Byte unit = DeriusAcrobatics.sneakTime.get(id);
		if (unit == null || unit < 2) return;
		
		if (event.getTo().getY() - event.getFrom().getY() <= 0.0001)
		{
			if ( ! MUtil.isSameBlock(event)) DeriusAcrobatics.sneakTime.remove(id);
			return;
		}
		
		
		
		JumpSetting setting = AbilityUtil.getLevelSetting(MConf.get().jumpSteps, mplayer.getLvl(AcrobaticsSkill.get()));

		AbilityUtil.activateAbility(mplayer, JumpAbility.get(), unit, false);
		ChatUtil.sendActionBar(player,  "");
	}

}
