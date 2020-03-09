package kim.nzxy.auth.common.util;

import org.springframework.util.DigestUtils;

/**
 * @author Xiaoyan
 * @date 20-1-7 下午2:11
 */
public class AuthUtils {
    /**
     * 盐
     */
    private static final String POWERED_BY_XIAOYAN = "powered by xiaoyan";
    /**
     * @param password 密码
     *
     * @return 加密后的值
     */
    public static String encodingPassword (String password) {
        return DigestUtils.md5DigestAsHex((password + POWERED_BY_XIAOYAN).getBytes());
    }

    /**
     * @param customer 客户端传来的密码
     * @param password 数据库查出来的密码
     *
     * @return 是否一致
     */
    public static boolean testPassword (String customer, String password) {
        return encodingPassword(customer).equals(password);
    }

    public static void main (String[] args) {
        System.out.println("encodingPassword(\"e10adc3949ba59abbe56e057f20f883e\") = " + encodingPassword(
                "e10adc3949ba59abbe56e057f20f883e"));
    }
}
