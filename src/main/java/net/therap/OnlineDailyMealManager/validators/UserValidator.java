package net.therap.OnlineDailyMealManager.validators;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.dao.CommonUtilsForDao;
import net.therap.OnlineDailyMealManager.exceptions.BlankInputException;
import net.therap.OnlineDailyMealManager.exceptions.InvalidUserException;
import net.therap.OnlineDailyMealManager.model.User;
import net.therap.OnlineDailyMealManager.utils.Utils;

import javax.servlet.http.HttpSession;

import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.BLANK_INPUT;

/**
 * @author anwar
 * @since 2/4/18
 */
public class UserValidator {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();
    private Utils utils = new Utils();

    public String isUserValid(User user, HttpSession session) {
        try {
            fetchUser(user);
        } catch (BlankInputException e) {
            synchronized (session) {
                session.setAttribute("authError", BLANK_INPUT_ERROR.getMessage());
            }
            return BLANK_INPUT_ERROR.getMessage();
        } catch (InvalidUserException e) {
            synchronized (session) {
                session.setAttribute("authError", USER_NOT_AVAILABLE_ERROR.getMessage());
            }
            return USER_NOT_AVAILABLE_ERROR.getMessage();
        } catch (Exception e) {
            synchronized (session) {
                session.setAttribute("authError", e.getMessage());
            }
            return e.getMessage();
        }
        return OK.getMessage();
    }

    private User fetchUser(User user) throws Exception {
        if ((BLANK_INPUT.getValue().equals(user.getName()) || BLANK_INPUT.getValue().equals(user.getPassword()))) {
            throw new BlankInputException(BLANK_INPUT_ERROR.getMessage());
        }
        User foundUser = (User) commonUtilsForDao.findOneByColumnNames(user,
                new Pair<String, Object>("name", user.getName()));
        if (foundUser != null && utils.isPasswordMatched(user.getPassword(), foundUser.getPassword())) {
            return foundUser;
        } else {
            throw new InvalidUserException(USER_NOT_AVAILABLE_ERROR.getMessage());
        }
    }
}
