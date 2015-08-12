package qinyuan.hrmis.domain.user;

import junit.framework.TestCase;

public class UserDaoTest extends TestCase {
    public void testGetUser() throws Exception {
        User user = UserDao.getUser("覃源", "test");
        System.out.println(user);
        if (user != null) {
            System.out.println(user.getName());
        }
    }
}
