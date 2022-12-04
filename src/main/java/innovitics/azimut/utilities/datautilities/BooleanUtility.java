package innovitics.azimut.utilities.datautilities;

public final class BooleanUtility {

	public static boolean isTrue(Boolean flag)
	{
		return flag!=null&&flag;
	}
	
	public static boolean isFalse(Boolean flag)
	{
		return ((flag==null)||(flag!=null&&!flag));
	}
	
	public static boolean getValue(Boolean flag)
	{
		if(flag!=null&&flag)
			return true;
		else if((flag==null)||(flag!=null&&!flag))
			return false;
		else
			return false;
	}
}
