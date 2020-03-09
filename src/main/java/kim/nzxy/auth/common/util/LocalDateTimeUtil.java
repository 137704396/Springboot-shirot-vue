package kim.nzxy.auth.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author Xiaoyan
 * @date 2019/12/25 13:49
 */
public class LocalDateTimeUtil {
    /**
     * 获取指定日期的毫秒
     */
    public static Long getMilliByTime (LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 获取指定日期的秒
     */
    public static Long getSecondsByTime (LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault())
                .toInstant()
                .getEpochSecond();
    }

    /**
     * 秒转LocalDateTime
     */
    public static LocalDateTime secondsToLocalDateTime (long seconds) {
        return LocalDateTime.ofEpochSecond(seconds / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 由于Windows和linux下获取LocalDateTime时时区不一致问题，故设置此方法来替代LocalDateTime.now()方法
     * @return 当前时间
     */
    public static LocalDateTime now () {
        return secondsToLocalDateTime(System.currentTimeMillis());
    }


}
