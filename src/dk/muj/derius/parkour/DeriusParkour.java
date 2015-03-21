package dk.muj.derius.parkour;

import java.util.HashMap;
import java.util.Map;

import com.massivecraft.massivecore.MassivePlugin;

public final class DeriusParkour extends MassivePlugin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static DeriusParkour i;
	public static DeriusParkour get() { return i; }
	public DeriusParkour() { i = this; }

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	//				ID,		Seconds sneaked
	final static Map<String, Short> sneakTime = new HashMap<>();
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void onEnable()
	{
		if ( ! this.preEnable()) return;
		
		EngineParkour.get().activate();
		ParkourSkill.get().register();
		JumpAbility.get().register();
		RunAbility.get().register();
		FallAbility.get().register();
		
		SneakTask.get().activate();
		
		this.postEnable();
	}
	
}
