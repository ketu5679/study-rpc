package zjh.ketu;

import java.io.IOException;

/**
 * @author zjh 2020/06/13
 */
public interface IUserService {
    /**
     * 根据用户Id获取用户
     * @return User
     */
    User getUserById(Integer id) throws IOException;
    default String getNameByPrefix(String prefix) throws IOException {
        return prefix + "-00";
    };
}
