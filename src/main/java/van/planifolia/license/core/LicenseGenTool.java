package van.planifolia.license.core;

import lombok.extern.slf4j.Slf4j;
import van.planifolia.license.util.*;

import java.security.KeyPair;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 证书生成工具
 * @Author: Planifolia.Van
 * @Date: 2025/7/4 15:26
 */
@Slf4j
public class LicenseGenTool {

    /**
     * 生成一组授权证书文件,调用该方法会自动生成一套证书与授权文件信息
     *
     * @param applicationName 应用名称
     * @param expTime         授权到期时间
     * @param customerName    客户信息
     * @param licPath         输出的授权文件路径
     * @param publicKeyPath   输出的公钥文件路径
     */
    public static void genLicense(String applicationName, Date expTime, String customerName, String licPath, String publicKeyPath) throws Exception {
        // 1. 生成公私钥
        KeyPair keyPair = RsaUtil.generateKeyPair();
        log.info("私钥：\n{}", RsaUtil.encodeKey(keyPair.getPrivate()));
        log.info("公钥：\n{}", RsaUtil.encodeKey(keyPair.getPublic()));

        // 2. 构造授权内容
        String endTimeStr = DateTimeUtil.getTimeStr(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, expTime);
        Map<String, Object> licenseData = new LinkedHashMap<>();
        licenseData.put("customer", customerName);
        licenseData.put("expireTime", endTimeStr);
        licenseData.put("product", applicationName);
        log.info("授权文件已生成！授权到期时间 : {}", endTimeStr);
        // 3. 生成证书内容
        String licenseJson = LicenseGenerator.generateLicenseJson(licenseData, keyPair.getPrivate());

        // 4. 保存到文件
        FileUtil.writeStringToFile(Strings.isBlank(licPath) ? "license.lic" : licPath + "/license.lic", licenseJson);
        FileUtil.writeStringToFile(Strings.isBlank(publicKeyPath) ? "public.key" : publicKeyPath + "/public.key", RsaUtil.encodeKey(keyPair.getPublic()));
    }



}

