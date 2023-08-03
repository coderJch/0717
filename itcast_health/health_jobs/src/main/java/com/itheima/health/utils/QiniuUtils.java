package com.itheima.health.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/24
 * @description ：
 * @version: 1.0
 */
public class QiniuUtils {

    public  static String accessKey = "mtVAnBxQKwr-kOQYo8lrbPAFrYqIm0WYkOlvuhii";
    public  static String secretKey = "O4j6NNa2L9Zv2OVCwaTyyHHmDDpf-OhWh6rSAfw7";
    public  static String bucket = "itcast-heslth-353-new";
    /**
     * 删除文件
     * @param fileName 服务端文件名
     */
    public static void deleteFileFromQiniu(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
    // 测试上传与删除
    public static void main(String args[]) throws Exception{
        // 测试删除
        deleteFileFromQiniu("Penguins.jpg_8434489ebecb43ca8185967ff2e819ea");
    }
}
