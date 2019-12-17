package com.wendu.elm;

import eleme.openapi.sdk.oauth.OAuthClient;
import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.response.Token;

/**
 * 饿了么-工具类
 * @author easypanda
 * @since 2018-03-22
 */
public class ElemeUtil {


    /**
     * 获取配置类
     * @param isSandbox 是否沙箱
     * @return
     */
    public static Config getConfig(boolean isSandbox){
        Config config=null;
        if(isSandbox==true){
            config=new Config(true,ElemeConstants.SANDBOX_APP_KEY,ElemeConstants.SANDBOX_APP_SECRET);
        }else{
            // TODO 填充正式环境数据
            config = new Config(false, ElemeConstants.APP_KEY, ElemeConstants.APP_SECRET);
        }
        return config;
    }



    /**
     * 获取店铺授权URL地址
     * @desc 每次调用会产生一个新的授权码 需要记录code作为后续凭证
     */
    public static String getAuthUrl(Config config){
        OAuthClient client = new OAuthClient(config);
        String authUrl = client.getAuthUrl(ElemeConstants.REDIRECT_URL, "all", "1234");
        return authUrl;
    }



    /**
     * 获取Token
     * callbackUrl  回调地址
     * @param code 前面servlet返回的凭证
     * @return
     */
    public static Token getToken(OAuthClient client,  String code) {

        return client.getTokenByCode(code, ElemeConstants.REDIRECT_URL);
    }



}
