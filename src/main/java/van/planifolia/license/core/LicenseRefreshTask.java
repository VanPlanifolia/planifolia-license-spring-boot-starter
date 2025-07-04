package van.planifolia.license.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description:
 * @Author: Planifolia.Van
 * @Date: 2025/7/4 14:11
 */
@Slf4j
public class LicenseRefreshTask {
    /**
     * 授权管理
     */
    private final LicenseManager licenseManager;
    /**
     * 授权配置文件
     */
    private final LicenseProperties licenseProperties;

    /**
     * 构造器
     *
     * @param licenseManager    管理器
     * @param licenseProperties 配置文件
     */
    public LicenseRefreshTask(LicenseManager licenseManager, LicenseProperties licenseProperties) {
        this.licenseManager = licenseManager;
        this.licenseProperties = licenseProperties;
    }

    /**
     * 定时任务刷新
     */
    @Scheduled(cron = "0 0 2 * * ? ")
    public void refreshLicense() {
        if (licenseProperties.isEnableAutoRefresh()) {
            log.info("定时任务：证书授权刷新！");
            licenseManager.load();
        }

    }
}
