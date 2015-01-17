package dk.muj.derius.acrobatics;

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

	@Override
	public void onEnable()
	{
		if ( ! this.preEnable()) return;
		
		MConfColl.get().init();
		
		AcrobaticsEngine.get().activate();
		AcrobaticsSkill.get().register();
		Fall.get().register();
		
		this.postEnable();
	}
}
