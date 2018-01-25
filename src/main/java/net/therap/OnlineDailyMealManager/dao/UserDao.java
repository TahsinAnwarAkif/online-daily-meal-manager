package net.therap.OnlineDailyMealManager.dao;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.model.User;

/**
 * @author anwar
 * @since 1/25/18
 */
public class UserDao {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();

    public User findOneByName(String name) {
        return (User) commonUtilsForDao.findOneByColumnNames(new User(), new Pair<String, Object>("name", name));
    }
}
