package van.planifolia.license.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import van.planifolia.license.util.*;

import java.security.PublicKey;

import java.util.LinkedHashMap;

/**
 * @Description:
 * @Author: Planifolia.Van
 * @Date: 2025/7/4 10:40
 */
@Slf4j
public class LicenseManager {
    /**
     * 是否校验通过-默认否
     */
    private volatile boolean valid = false;
    /**
     * 定义默认有效期
     */
    @Getter
    private volatile String expireDate;
    /**
     * 配置文件
     */
    private final LicenseProperties props;


    /**
     * 构造器
     *
     * @param props 配置信息
     */
    public LicenseManager(LicenseProperties props) {
        this.props = props;
    }

    /**
     * 是否生效
     *
     * @return 校验结果
     */
    public boolean isValid() {
        return props.isDevMode() || (valid && !isExpired());
    }

    /**
     * 加载证书并且去刷新一下过期时间
     */
    public synchronized void load() {
        log.info(Strings.toColor("系统正在加载授权信息...", ConsoleColors.YELLOW));
        try {
            String licenseJson = FileUtil.readFileAsString(props.getLicensePath());
            String publicKeyStr = FileUtil.readFileAsString(props.getPublicKeyPath());
            PublicKey publicKey = RsaUtil.decodePublicKey(publicKeyStr);
            LinkedHashMap<String, Object> map = JSON.parseObject(licenseJson, LinkedHashMap.class);
            String signature = (String) map.remove("signature");
            // 用相同方式生成签名原文
            String rawContent = JSON.toJSONString(map, SerializerFeature.MapSortField);
            if (RsaUtil.verify(rawContent, signature, publicKey)) {
                this.expireDate = (String) map.get("expireTime");
                this.valid = true;
                if (props.isDevMode()) {
                    log.error(Strings.toColor("授权信息加载成功！授权到期时间:{}，当前模式为DEV，不拦截任何请求。", ConsoleColors.YELLOW), expireDate);
                } else {
                    log.info(Strings.toColor("授权信息加载成功！授权到期时间:{},当前状态:{}", ConsoleColors.GREEN), expireDate, isValid() ? "正常" : "授权到期");
                }
            } else {
                this.valid = false;
                log.error(Strings.toColor("授权信息加载失败！", ConsoleColors.RED));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            this.valid = false;
        }
    }

    /**
     * 是否过期了
     *
     * @return 是否过期了
     */
    private boolean isExpired() {
        return System.currentTimeMillis() > DateTimeUtil.parseDateStr(expireDate, DateTimeUtil.YYYY_MM_DD_HH_MM_SS).getTime();
    }
}
