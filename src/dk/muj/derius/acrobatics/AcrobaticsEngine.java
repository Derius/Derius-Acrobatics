package dk.muj.derius.acrobatics;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.EngineAbstract;

import dk.muj.derius.acrobatics.entity.MConf;
import dk.muj.derius.entity.MPlayer;
import dk.muj.derius.events.PlayerDamageEvent;
import dk.muj.derius.util.AbilityUtil;

public class AcrobaticsEngine extends EngineAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
   
	private static AcrobaticsEngine i = new AcrobaticsEngine();
	public static AcrobaticsEngine get() { return i; }
	private AcrobaticsEngine() {}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Plugin getPlugin()
	{
		return DeriusAcrobatics.get();
	}

	// -------------------------------------------- //
	// EVENT
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onFall(PlayerDamageEvent event)
	{	
		if (event.getInnerEvent().getCause() != DamageCause.FALL) return;
		
		MPlayer player = MPlayer.get( (Player) event.getInnerEvent().getEntity());
		
		Optional<Object> opt = AbilityUtil.activateAbility(player, Fall.get(), event, false);
		
		double damage;
		if ( ! opt.isPresent())
		{
			damage = event.getInnerEvent().getDamage();
		}
		else
		{
			Object obj = opt.get();
			if ( ! (obj instanceof Double)) return;
			damage = (double) obj;
		}
		
		player.addExp(AcrobaticsSkill.get(), (long) damage*MConf.get().expPerBlock);
	}
	
}
