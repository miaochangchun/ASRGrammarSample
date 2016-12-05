package com.example.sinovoice.util;

/**
 * 灵云配置信息
 * Created by 10048 on 2016/12/3.
 */
public class ConfigUtil {
    /**
     * 灵云APP_KEY
     */
    public static final String APP_KEY = "c85d54f1";

    /**
     * 开发者密钥
     */
    public static final String DEVELOPER_KEY = "712ddd892cf9163e6383aa169e0454e3";

    /**
     * 灵云云服务的接口地址
     */
    public static final String CLOUD_URL = "test.api.hcicloud.com:8888";

    /**
     * 需要运行的灵云能力
     */
    // 云端自由说
    public static final String CAP_KEY_ASR_CLOUD_FREETALK = "asr.cloud.freetalk";

    // 云端语音识别+语义
    public static final String CAP_KEY_ASR_CLOUD_DIALOG = "asr.cloud.dialog";

    // 离线命令词
    public static final String CAP_KEY_ASR_LOCAL_GRAMMAR = "asr.local.grammar.v4";

    // 在线命令词
    public static final String CAP_KEY_ASR_CLOUD_GRAMMAR = "asr.cloud.grammar";
}
