package van.planifolia.license.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.security.PrivateKey;
import java.util.Map;

public class LicenseGenerator {

    /**
     * 生成证书带签名的
     *
     * @param licenseData 证书原文件
     * @param privateKey  私钥源文件
     * @return 生成结果
     */
    public static String generateLicenseJson(Map<String, Object> licenseData, PrivateKey privateKey) throws Exception {
        // 1. 构造原始 JSON（不含 signature）
        String rawContent = JSON.toJSONString(licenseData, SerializerFeature.MapSortField);
        String signature = RsaUtil.sign(rawContent, privateKey);
        // 3. 加入签名
        licenseData.put("signature", signature);
        // 格式化输出 + 排序稳定
        return JSON.toJSONString(licenseData, SerializerFeature.PrettyFormat, SerializerFeature.MapSortField);
    }
}
