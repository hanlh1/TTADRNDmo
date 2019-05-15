package com.ttaddemo.ad;

import java.util.ArrayList;
import java.util.List;

public class ADCodeErrorUtils {
    private static final ADCodeErrorUtils ourInstance = new ADCodeErrorUtils();

    public static ADCodeErrorUtils getInstance() {
        return ourInstance;
    }

    private List<String> configCodeErrorList;

    private List<String> netCodeErrorList;

    private String SHM_CONFIG_CODE_ERROR = "901";

    private String SHM_NET_CODE_ERRPR = "902";

    private String SHM_OTHER_CODE_ERROR = "900";


    private ADCodeErrorUtils() {
        configCodeErrorList = new ArrayList<>();
        configCodeErrorList.add("40002");//source_type=‘app’, 请求app不能为空
        configCodeErrorList.add("40003");//source_type=‘wap’, 请求wap不能为空
        configCodeErrorList.add("40004");//广告位不能为空
        configCodeErrorList.add("40005");//广告位尺寸不能为空
        configCodeErrorList.add("40006");//广告位ID不合法
        configCodeErrorList.add("40007");//广告数量错误
        configCodeErrorList.add("40008");//图片尺寸错误
        configCodeErrorList.add("40009");//媒体ID不合法
        configCodeErrorList.add("40010");//媒体类型不合法
        configCodeErrorList.add("40018");//媒体包名与录入不一致

        netCodeErrorList  =new ArrayList<>();
        netCodeErrorList.add("-2");//网络错误
        netCodeErrorList.add("40000");//http content type错误
        netCodeErrorList.add("40001");//http request pb错误
        netCodeErrorList.add("50001");//服务器错误
    }

    public String getSHMCodeError(String adCodeError){
        if(configCodeErrorList.contains(adCodeError)){
            return SHM_CONFIG_CODE_ERROR;
        }else if(netCodeErrorList.contains(adCodeError)){
            return SHM_NET_CODE_ERRPR;
        }
        return SHM_OTHER_CODE_ERROR;

    }

}
