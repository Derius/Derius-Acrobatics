package dk.muj.derius.acrobatics;

import dk.muj.derius.acrobatics.entity.MConf;
import dk.muj.derius.skill.Skill;

public class AcrobaticsSkill extends Skill
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static AcrobaticsSkill i = new AcrobaticsSkill();
	public static AcrobaticsSkill get() { return i; }
	
	
	public AcrobaticsSkill()
	{
		super.addEarnExpDesc("Mine ores");
		
		super.setDescription("Makes you better at mining");
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public int getId()
	{
		return MConf.get().getSkillId();
	}
	
	@Override
	public void setName(String name)
	{
		MConf.get().skillName = name;
	}
	
	@Override
	public String getName()
	{
		return MConf.get().skillName;
	}
	
}
