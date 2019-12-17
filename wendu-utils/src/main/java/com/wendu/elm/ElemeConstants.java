package com.wendu.elm;

/**
 * 饿了吗常量类
 * 2019.10.13
 * @author baihaojie
 *
 */
public class ElemeConstants {

    /**标志是否为沙箱模式*/
    public final static boolean IS_SANDBOX = true;

    //沙箱环境参数
    /**对应Key 表示沙箱环境的应用Key*/
    public final static String SANDBOX_APP_KEY="Dt9zS183j5";
    /**对应Secret 表示沙箱环境的应用Secret*/
    public final static String SANDBOX_APP_SECRET="28887f3fc0f4b7287da5a68eac1263f6d380b100";
    /**对应沙箱环境店铺ID*/
    public final static long SANDBOX_STORE_ID=300708541;
    /**对应沙箱环境店铺URL*/
    public final static String SANDBOX_STORE_URL="\r\n" +
            "https://www.ele.me/shop/E12040631826254074863";
    //对应沙箱环境店铺密码
    /**对应沙箱环境店铺密码*/
    public final static String SANDBOX_STORE_PASS="fTB2LQb689a8";
    /**对应沙箱环境回调地址URL*/
    public final static String SANDBOX_REDIRECT_URL="http://www.wenbao.com/wendu_web/elm?method=auth_back";

    /**正式环境参数 暂时么有*/
    public final static boolean NOT_SANDBOX = false;
    public final static String APP_KEY="KpvrclSbQ8";
    public final static String APP_SECRET="0aca50e56e14b5ddb5fde10ccc2d1b4da175725a";
    public final static String REDIRECT_URL="http://www.wenbao.com/wendu_web/elm?method=auth_back";
}
