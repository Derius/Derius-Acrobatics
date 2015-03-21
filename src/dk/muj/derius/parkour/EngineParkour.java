package dk.muj.derius.parkour;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.EngineAbstract;
import com.massivecraft.massivecore.util.MUtil;

import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.api.VerboseLevel;
import dk.muj.derius.api.ability.Ability;
import dk.muj.derius.api.player.DPlayer;
import dk.muj.derius.api.util.AbilityUtil;
import dk.muj.derius.api.util.SkillUtil;

public class EngineParkour extends EngineAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
   
	private static EngineParkour i = new EngineParkour();
	public static EngineParkour get() { return i; }
	private EngineParkour() {}

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
	public void reduceFallDamage_and_GiveExp(EntityDamageEvent event)
	{	
		if (event.getCause() != DamageCause.FALL) return;
		if ( ! (event.getEntity() instanceof Player)) return;
		
		DPlayer dplayer = DeriusAPI.getDPlayer( (Player) event.getEntity());
		
		Object obj = AbilityUtil.activateAbility(dplayer, FallAbility.get(), event, VerboseLevel.HIGHEST);
		
		double damage;
		if (obj instanceof Number)
		{
			damage = ((Number) obj).doubleValue();
		}
		else
		{
			damage = event.getDamage();
		}
		
		double exp = (int) damage * ParkourSkill.getExpPerBlockFallen();
		if (dplayer.getPlayer().isSneaking()) exp *= ParkourSkill.getExpSneakMutiplier();
		
		if (SkillUtil.canPlayerLearnSkill(dplayer, ParkourSkill.get(), VerboseLevel.HIGHEST))
		{
			dplayer.addExp(ParkourSkill.get(), exp);
		}
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
