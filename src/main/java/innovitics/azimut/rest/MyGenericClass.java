package innovitics.azimut.rest;

public class MyGenericClass<T> {
	
	     private final Class<T> type;

	     public MyGenericClass(Class<T> type) {
	          this.type = type;
	     }

	     public Class<T> getMyType() {
	         return this.type;
	     }
}
