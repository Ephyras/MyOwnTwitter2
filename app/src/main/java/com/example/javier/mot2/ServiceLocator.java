package com.example.javier.mot2;

import android.content.Context;

/**
 * Created by Javier on 2/26/2018.
 */

class ServiceLocator {

    public static UserService getUserService(Context applicationContext, String type) {
        switch (type) {
            case "dao":
                return AppDatabase.getAppDB(applicationContext).userDao();
            case "gae":
                throw new UnsupportedOperationException("Operation is not supported");
            default:
                throw new UnsupportedOperationException("Operation is not supported");
        }
    }
}
