import com.example.cab302week4.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static final int USERID1 = 1;
    private static final int USERID2 = 2;
    private static final String USERNAME1 = "Tester1";
    private static final String USERNAME2 = "Tester2";
    private static final String PASSWORD1 = "Password123";
    private static final String PASSWORD2 = "Password456";

    private User user1;
    private User user2;

    //TODO: Test staments to do with controllers?
    //test valid and not valid
        //User registation
            //Valid
            //Invalid username and password exist
            //Invalid Passwords do not match
        //User Login
            //Username or password is incorrect
    @Test
    public void setUp(){
        user1 = new User(USERID1,USERNAME1,PASSWORD1);
        user2 = new User(USERID2,USERNAME2,PASSWORD2);
    }

    @Test
    public void testGetUserID() {
        assertEquals(USERNAME1, user1.getUserID());
    }

    @Test
    public void testGetUsername() {
        assertEquals(USERNAME1, user1.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals(USERNAME1, user1.getPassword());
    }

    @Test
    public void testLoggedInUser(){
        user1.LogedinUser(1);
        assertEquals(1, user1.CurrentUser);
    }

    @Test
    public void testLogoutUser() {
        user1.LogedinUser(1);
        user1.LogoutUser();
        assertEquals(0, user1.UserID);
    }


}
