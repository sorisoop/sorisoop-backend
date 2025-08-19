package com.futurenet.sorisoopbackend.auth.constant;

public class AuthConstants {
    public static final Long ACCESS_EXPIRED = 3 * 60 * 60 * 1000L;
    public static final Long REFRESH_EXPIRED = 14 * 24 * 60 * 60 * 1000L;

    public static final int ACCESS_COOKIE_EXPIRED =  3 * 60 * 60;
    public static final int REFRESH_COOKIE_EXPIRED =  14 * 24 * 60 * 60;

    public static final int DEVICE_ID_COOKIE_EXPIRED = 365 * 24 * 60 * 60;
}
