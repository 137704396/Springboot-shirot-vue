package kim.nzxy.auth.models.system.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kim.nzxy.auth.common.auth.TokenGenerator;
import kim.nzxy.auth.common.config.Constant;
import kim.nzxy.auth.models.system.token.SysToken;
import kim.nzxy.auth.models.system.token.SysTokenService;
import kim.nzxy.auth.common.util.AuthUtils;
import kim.nzxy.auth.common.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiaoyan
 * @date 2019/12/24 16:22
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * 12小时后过期
     */
    private final static long            EXPIRE = 3600 * 12 * 1000;
    private final        UserMapper      userMapper;
    private final        SysTokenService tokenService;
    private final        Constant        constant;
    @Override
    public User insert (User record) {
        record.setPassword("123");
        userMapper.insert(record);
        return record;
    }
    @Override
    public User findById (Integer id) {
        return userMapper.findByIdAndDelFalse(id);
    }
    @Override
    public User findByUsername (String username) {
        return userMapper.findByUsername(username);
    }
    @Override
    public Map<String, Object> createToken (Integer id) {
        Map<String, Object> result = new HashMap<>(2);
        //生成一个token
        String token = TokenGenerator.generateValue();
        //过期时间
        LocalDateTime expireTime = LocalDateTimeUtil.secondsToLocalDateTime(System.currentTimeMillis() + EXPIRE);
        //判断是否生成过token
        SysToken tokenEntity = tokenService.findByUserId(id);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
            tokenEntity.setUserId(id);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(LocalDateTimeUtil.now());
            tokenEntity.setExpireTime(expireTime);
            //保存token
            tokenService.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(LocalDateTimeUtil.now());
            tokenEntity.setExpireTime(expireTime);
            //更新token
            tokenService.updateByUserId(tokenEntity);
        }
        result.put("token", token);
        result.put("expire", EXPIRE);
        return result;
    }
    @Override
    public void logout (Integer id) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //修改token
        SysToken tokenEntity = new SysToken();
        tokenEntity.setUserId(id);
        tokenEntity.setToken(token);
        tokenService.save(tokenEntity);
    }
    @Override
    public PageInfo<User> search (User user, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(userMapper.findByAll(user));
    }
    @Override
    public User updateById (User updated) {
        userMapper.updateByPrimaryKeySelective(updated);
        return updated;
    }
    @Override
    public void updatePasswordById (String password, Integer id) {
        userMapper.updatePasswordById(AuthUtils.encodingPassword(password), id);
    }
    @Override
    public int deleteById (Integer id) {
        return userMapper.updateDelById(true, id);
    }
    @Override
    public int resetPassword (Integer id) {
        return userMapper.updatePasswordById(AuthUtils.encodingPassword(constant.getDefaultPassword()), id);
    }
    @Override
    public int updateByPrimaryKeySelective (User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public User insertSelective (User user) {
        user.setPassword(AuthUtils.encodingPassword(constant.getDefaultPassword()));
        userMapper.insertSelective(user);
        return user;
    }
    @Override
    public void enableUser (Integer id) {
        userMapper.updateEnableById(true, id);
    }
    @Override
    public void disableUser (Integer id) {
        userMapper.updateEnableById(false, id);
    }

}





