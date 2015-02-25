package dk.muj.derius.parkour;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.EngineAbstract;
import com.massivecraft.massivecore.util.MUtil;

import dk.muj.derius.api.Ability;
import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.api.VerboseLevel;
import dk.muj.derius.events.player.PlayerDamageEvent;
import dk.muj.derius.util.AbilityUtil;

public class ParkourEngine extends EngineAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
   
	private static ParkourEngine i = new ParkourEngine();
	public static ParkourEngine get() { return i; }
	private ParkourEngine() { }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Plugin getPlugin()
	{
		return DeriusParkour.get();
	}

	// -------------------------------------------- //
	// EVENT
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void reduceFallDamage_and_GiveExp(PlayerDamageEvent event)
	{	
		if (event.getInnerEvent().getCause() != DamageCause.FALL) return;
		
		DPlayer player = DeriusAPI.getDPlayer( (Player) event.getInnerEvent().getEntity());
		
		Object obj = AbilityUtil.activateAbility(player, Fall.get(), event, VerboseLevel.HIGHEST);
		
		double damage;
		if ( obj == null )
		{
			damage = event.getInnerEvent().getDamage();
		}
		else
		{
			if ( ! (obj instanceof Double)) return;
			damage = (double) obj;
		}
		
		int exp = (int) damage * ParkourSkill.getExpPerBlockFallen();
		if (player.getPlayer().isSneaking()) exp *= ParkourSkill.getSneakMutiplier();
		
		player.addExp(ParkourSkill.get(), exp);
	}
	
	// -------------------------------------------- //
	// SCHEDULER
	// -------------------------------------------- //
	
	@Override
	public Long getPeriod()
	{
		// Every minute
		return 20 * 60L;
	}
	
	@Override
	public void run()
	{
		Ability ability = RunAbility.get();
		for (Player player : MUtil.getOnlinePlayers())
		{
			DPlayer dplayer = DeriusAPI.getDPlayer(player);
			AbilityUtil.activateAbility(dplayer, ability, null, VerboseLevel.ALWAYS);
		}
	}
}
