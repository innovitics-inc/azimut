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
	
}
