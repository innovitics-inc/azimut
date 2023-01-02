package innovitics.azimut.utilities.threading;

import innovitics.azimut.businessmodels.user.BusinessUser;

public class ThreadAttributes {
    private static ThreadLocal<BusinessUser> threadAttrs = new ThreadLocal<BusinessUser>() {
        @Override
        protected BusinessUser initialValue() {
            return new BusinessUser();
        }
    };

    public static BusinessUser get() {
        return threadAttrs.get();
    }

    public static void set(BusinessUser businessUser) {
        threadAttrs.set(businessUser);
    }
}