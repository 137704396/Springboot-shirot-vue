package kim.nzxy.auth.models.system.token;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
public interface SysTokenService {

    int insert (SysToken record);
    SysToken findByUserId (Integer userId);
    int updateByUserId (SysToken updated);
    void save (SysToken token);

    SysToken findByToken (String token);

}
