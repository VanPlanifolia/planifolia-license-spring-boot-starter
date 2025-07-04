package van.planifolia.license.core;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:
 * @Author: Planifolia.Van
 * @Date: 2025/7/4 10:32
 */
@Data
@Getter
@ConfigurationProperties(prefix = "license")
public class LicenseProperties {

    /**
     * 是否开启
     */
    private boolean enable = true;
    /**
     * 校验文件路径
     */
    private String licensePath;
    /**
     * 公钥路径
     */
    private String publicKeyPath;
    /**
     * 是否开启拦截器
     */
    private boolean enableInterceptor = true;
    /**
     * 是否开启自动刷新证书
     */
    private boolean enableAutoRefresh = true;
    /**
     * 是否开启开发模式
     */
    private boolean devModel = false;


    /**
     * 是否开发模式
     *
     * @return 是否为开发模式
     */
    public boolean isDevMode() {
        return devModel;
    }


}
