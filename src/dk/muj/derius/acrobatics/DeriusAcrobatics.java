package dk.muj.derius.acrobatics;

import java.util.HashMap;
import java.util.Map;

import com.massivecraft.massivecore.MassivePlugin;

import dk.muj.derius.acrobatics.entity.MConfColl;

public class DeriusAcrobatics extends MassivePlugin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
    
    private static DeriusAcrobatics i;
	public static DeriusAcrobatics get() { return i; }
	public DeriusAcrobatics() { i = this; }

	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	//				ID,		Seconds sneaked
	final static Map<String, Byte> sneakTime = new HashMap<String, Byte>();
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void onEnable()
	{
		if ( ! this.preEnable()) return;
		
		MConfColl.get().init();
		
		SneakTask.get().activate();
		
		AcrobaticsEngine.get().activate();
		AcrobaticsSkill.get().register();
		JumpAbility.get().register();
		Fall.get().register();
		
		this.postEnable();
	}
}
