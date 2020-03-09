package kim.nzxy.auth.models.system.token;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
public class SysTokenServiceImpl implements SysTokenService {

    @Resource
    private SysTokenMapper sysTokenMapper;

    @Override
    public int insert (SysToken record) {
        return sysTokenMapper.insert(record);
    }
    @Override
    public SysToken findByUserId (Integer userId) {
        return sysTokenMapper.findByUserId(userId);
    }

    @Override
    public int updateByUserId (SysToken updated) {
        return sysTokenMapper.updateByUserId(updated, updated.getUserId());
    }

    @Override
    public void save (SysToken token) {
        if (token.getUserId() != null) {
            sysTokenMapper.updateByUserId(token, token.getUserId());
        } else {
            sysTokenMapper.insert(token);
        }
    }

    @Override
    public SysToken findByToken (String token) {
        return sysTokenMapper.findByToken(token);
    }

}
