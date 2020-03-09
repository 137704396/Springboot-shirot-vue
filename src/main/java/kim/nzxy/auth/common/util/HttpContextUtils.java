package kim.nzxy.auth.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author xy
 */
public class HttpContextUtils {
    private static HttpServletRequest getHttpServletRequest () {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static String getDomain () {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer       url     = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI()
                .length(), url.length())
                .toString();
    }

    public static String getOrigin () {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }
}
