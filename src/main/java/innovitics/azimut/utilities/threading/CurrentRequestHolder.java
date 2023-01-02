package innovitics.azimut.utilities.threading;

import java.lang.ref.WeakReference;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.utilities.logging.MyLogger;

public class CurrentRequestHolder {

		  private static final ThreadLocal<BusinessUser> CONTEXT = new ThreadLocal<>();
		  private static final ThreadLocal<String> systemTrx= new ThreadLocal<>();
		  public static void set(BusinessUser businessUser) {
		    CONTEXT.set(businessUser);
		  }

		  public static BusinessUser get() {
		    return CONTEXT.get();
		  }

		  public static void clear() {
			MyLogger.info("Clearing the threadlocal variable "+ get().getSystemTrx());
		    CONTEXT.remove();
		  }

		public static String getSystemtrx() {
			return systemTrx.get();
		}
		
		public static void setSystemtrx(String systemTrxString) {
			systemTrx.set(systemTrxString);
		}
		  
		public static void clearSystemTrx() {
			systemTrx.remove();
		  }

}
