package dk.muj.derius.parkour;

import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.massivecraft.massivecore.Progressbar;
import com.massivecraft.massivecore.util.MUtil;

import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.api.VerboseLevel;
import dk.muj.derius.api.player.DPlayer;
import dk.muj.derius.api.util.AbilityUtil;
import dk.muj.derius.api.util.LevelUtil;
import dk.muj.derius.engine.EngineMsg;
import dk.muj.derius.lib.scheduler.RepeatingTask;

public class SneakTask extends RepeatingTask
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
		Map<Integer, JumpSetting> steps = ParkourSkill.getJumpSteps();
		int waitUnits = ParkourSkill.getWaitUnits();
		for (Player player : MUtil.getOnlinePlayers())
		{
			this.handlePlayer(player, steps, waitUnits);
		}
		
		return;
	}
	
	public void handlePlayer(Player player, Map<Integer, JumpSetting> steps, int waitUnits)
	{
		DPlayer dplayer = DeriusAPI.getDPlayer(player);
		if (this.isJumpReady(player, dplayer))
		{	
			this.clearPlayer(player);
			return;
		}
					
		String id = player.getUniqueId().toString();
		
		Optional<JumpSetting> optSetting = LevelUtil.getLevelSetting(steps, dplayer.getLvl(ParkourSkill.get()));
		if ( ! optSetting.isPresent()) return;
		final JumpSetting setting = optSetting.get();
		final short unit = this.getUnit(id, setting.getMaxUnits());

		DeriusParkour.sneakTime.put(id, (short) (unit+waitUnits));

		if (unit <= 0) return;
		
		Bukkit.getScheduler().runTaskAsynchronously(DeriusParkour.get(), () ->
		{
			String bar = Progressbar.HEALTHBAR_CLASSIC.withQuota((double)unit/setting.getMaxUnits()).withWidth(setting.getMaxUnits()).render();
			EngineMsg.sendActionBar(player, bar);
		});

		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Const.JUMP_EFFECT_TICKS, setting.getPotionLevel(unit), false, false), true);
	}
	
	public boolean isJumpReady(Player player, DPlayer dplayer)
	{
		return ( ! player.isSneaking() || ! ((Entity) player).isOnGround() ||  ! AbilityUtil.canPlayerActivateAbility(dplayer, JumpAbility.get(), VerboseLevel.ALWAYS));
	}
	
	public void clearPlayer(Player player)
	{
		String id = player.getUniqueId().toString();
		if (DeriusParkour.sneakTime.containsKey(id))
		{
			EngineMsg.sendActionBar(player, "");
			player.removePotionEffect(PotionEffectType.JUMP);
			DeriusParkour.sneakTime.remove(id);
		}
		
		return;
	}
	
	public short getUnit(String id, int max)
	{
		Short unit = DeriusParkour.sneakTime.get(id);
		if (unit == null) unit = 0;
		unit = (short) (unit-ParkourSkill.getWaitUnits());
		unit++;
		return (short) Math.min(unit, max);
	}
	
	// -------------------------------------------- //
	// WALK EVENT
	// -------------------------------------------- //

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void stopSneakIfWalking(PlayerMoveEvent event)
	{
		if (MUtil.isSameBlock(event)) return;
		this.clearPlayer(event.getPlayer());
	}

}
