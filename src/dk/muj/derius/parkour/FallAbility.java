package dk.muj.derius.parkour;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.api.ability.AbilityAbstract;
import dk.muj.derius.api.player.DPlayer;
import dk.muj.derius.api.skill.Skill;
import dk.muj.derius.api.util.AbilityUtil;

public class FallAbility extends AbilityAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static FallAbility i = new FallAbility();
	public static FallAbility get() { return i; }
	
	public FallAbility()
	{
		this.setDesc("Reduces fall damage");
		
		this.setName("Roll");
		
		this.setType(AbilityType.PASSIVE);
		
		this.setCooldownMillis(-1);
	}
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	@Override
	public String getId()
	{
		return "derius:parkour:fall";
	}

	@Override
	public Optional<String> getLvlDescriptionMsg(int lvl)
	{
		return Optional.of(Txt.parse("<i>Fall <h>%s <i>blocks without taking damage", lvl/ParkourSkill.getDamageLessPerLevel() + 3));
	}

	@Override
	public Object onActivate(DPlayer dplayer, Object other)
	{
		// First we cast
		EntityDamageEvent event = (EntityDamageEvent) other;
		// It must be because of fall damage.
		if (event.getCause() != DamageCause.FALL) return AbilityUtil.CANCEL;
		if ( !(event.getEntity() instanceof Player)) return AbilityUtil.CANCEL;
		
		int level = dplayer.getLvl(this.getSkill());
		final double originalDamage = event.getFinalDamage();
		double damageReduce = (double) level / ParkourSkill.getDamageLessPerLevel();
		
		if (dplayer.getPlayer().isSneaking())
		{
			damageReduce *= ParkourSkill.getDamageLessSneakMutiplier();
		}

		final double newDamage = Math.max(originalDamage-damageReduce, 0.0);
		
		// We use this method because standard bukkit method is buggy.
		MUtil.setDamage(event, newDamage);
		if (event.getDamage() <= 0) event.setCancelled(true);
		
		return event.getDamage();
	}

	@Override
	public void onDeactivate(DPlayer p, Object other)
	{
		// LELNOPE
		
	}

	@Override
	public Skill getSkill()
	{
		return ParkourSkill.get();
	}
	
}
