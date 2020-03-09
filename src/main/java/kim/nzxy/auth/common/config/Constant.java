package kim.nzxy.auth.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 常量类
 * 加载yaml配置文件的方法
 * @author xy
 */
@Component
@ConfigurationProperties(prefix = "config")
@PropertySource(value = "classpath:/config.properties")
@Data
public class Constant {
    private String defaultPassword;
}
