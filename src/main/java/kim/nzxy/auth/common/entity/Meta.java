package kim.nzxy.auth.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta implements Serializable {
    private String title;
    private String icon;
}
