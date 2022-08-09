package innovitics.azimut.utilities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface CustomJsonRootName {
    String singular(); // element root name for a single object
    String plural(); // element root name for collections
}