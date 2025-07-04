package van.planifolia.license.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import van.planifolia.license.core.LicenseManager;
import van.planifolia.license.core.LicenseProperties;
import van.planifolia.license.core.LicenseRefreshTask;
import van.planifolia.license.interceptor.LicenseInterceptor;
import van.planifolia.license.util.ConsoleColors;
import van.planifolia.license.util.Strings;

/**
 * @Description: 注册所有系统中需要的Bean
 * @Author: Planifolia.Van
 * @Date: 2025/7/4 11:24
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({LicenseProperties.class})
public class LicenseAutoConfiguration {
    /**
     * 注册授权管理器
     *
     * @param licenseProperties 配置信息
     * @return 注册的授权管理器
     */
    @Bean
    @ConditionalOnMissingBean
    public LicenseManager licenseManager(LicenseProperties licenseProperties) {
        LicenseManager manager = new LicenseManager(licenseProperties);
        // 启动时加载
        if (licenseProperties.isEnable()) {
            manager.load();
        }
        return manager;
    }

    /**
     * 注册拦截器
     *
     * @param licenseManager 授权管理器
     * @return 被注册的拦截器
     */
    @Bean
    @ConditionalOnProperty(prefix = "license", name = "enableInterceptor", havingValue = "true")
    public WebMvcConfigurer licenseInterceptorConfig(LicenseManager licenseManager) {
        log.info(Strings.toColor("授权拦截器已开启，正在启动...", ConsoleColors.YELLOW));
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LicenseInterceptor(licenseManager)).addPathPatterns("/**").excludePathPatterns("/flushLicense");
                log.info(Strings.toColor("授权拦截器启动成功！", ConsoleColors.GREEN));
            }
        };

    }

    /**
     * 注册一个定时任务用于固定时间刷新证书
     *
     * @param licenseManager    管理器
     * @param licenseProperties 配置文件
     * @return 注册定时任务
     */
    @Bean
    @ConditionalOnProperty(prefix = "license", name = "enableAutoRefresh", havingValue = "true")
    public LicenseRefreshTask licenseRefreshTask(LicenseManager licenseManager, LicenseProperties licenseProperties) {
        log.info(Strings.toColor("授权信息刷新定时任务启动成功！", ConsoleColors.GREEN));
        return new LicenseRefreshTask(licenseManager, licenseProperties);
    }

}
