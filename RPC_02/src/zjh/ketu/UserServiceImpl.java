package zjh.ketu;

public class UserServiceImpl implements IUserService {

    @Override
    public User getUserById(Integer id) {
        return new User(1, "ketu");
    }
}
