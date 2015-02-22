package dk.muj.derius.parkour;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.EngineAbstract;

import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.events.PlayerDamageEvent;
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
		
		Object obj = AbilityUtil.activateAbility(player, Fall.get(), event, false);
		
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
	
}
