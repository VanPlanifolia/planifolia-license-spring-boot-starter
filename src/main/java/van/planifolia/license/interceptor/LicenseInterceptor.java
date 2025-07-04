package van.planifolia.license.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import van.planifolia.license.core.LicenseManager;
import van.planifolia.license.util.ConsoleColors;
import van.planifolia.license.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 校验拦截器
 * @Author: Planifolia.Van
 * @Date: 2025/7/4 10:57
 */
@Slf4j
public class LicenseInterceptor implements HandlerInterceptor {

    private final LicenseManager licenseManager;

    public LicenseInterceptor(LicenseManager manager) {
        this.licenseManager = manager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!licenseManager.isValid()) {
            log.error(Strings.toColor("授权已到期！到期时间:{}", ConsoleColors.RED), licenseManager.getExpireDate());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":403,\"message\":\"授权无效或已过期,到期时间:" + licenseManager.getExpireDate() + "\"}");
            return false;
        }
        return true;
    }
}
