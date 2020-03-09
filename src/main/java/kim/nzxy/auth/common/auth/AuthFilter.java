package kim.nzxy.auth.common.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kim.nzxy.auth.common.entity.Result;
import kim.nzxy.auth.common.enums.ResultCodeEnum;
import kim.nzxy.auth.common.util.HttpContextUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * auth过滤器
 *
 * @author xy
 */
public class AuthFilter extends AuthenticatingFilter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(
            DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    /**
     * 生成自定义token
     */
    @Override
    protected AuthenticationToken createToken (ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new AuthToken(token);
    }

    /**
     * 步骤1.所有请求全部拒绝访问
     */
    @Override
    protected boolean isAccessAllowed (ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod()
                .equals(RequestMethod.OPTIONS.name());
    }

    /**
     * 步骤2，拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取 token，再调用executeLogin方法
     */
    @Override
    protected boolean onAccessDenied (ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            httpResponse.setCharacterEncoding("UTF-8");
            Result failure = Result.failure(ResultCodeEnum.PERMISSION_NO_ACCESS);
            httpResponse.getWriter()
                    .print(OBJECT_MAPPER.writeValueAsString(failure));
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 登陆失败时候调用
     */
    @Override
    protected boolean onLoginFailure (AuthenticationToken token,
                                      AuthenticationException e,
                                      ServletRequest request,
                                      ServletResponse response) {
        try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            httpResponse.setCharacterEncoding("UTF-8");
            //处理登录失败的异常
            Result failure = Result.failure(ResultCodeEnum.USER_NOT_LOGGED_IN);
            httpResponse.getWriter()
                    .print(OBJECT_MAPPER.writeValueAsString(failure));
        } catch (IOException ignored) {
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken (HttpServletRequest httpRequest) {

        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }
}
