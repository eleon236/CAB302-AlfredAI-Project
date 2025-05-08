import com.example.alfredAI.model.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    private final List<User> users = new ArrayList<>( List.of(
            new User(1, "Tester1","Password1"),
            new User(2, "Tester2","Password2"),
            new User(3, "Tester3","Password3"),
            new User(4, "Tester4","Password4"),
            new User(5, "Tester5","Password5")
    ));

    //Copy paste for incorrect username and password
    @Test
    public void testLoginUserWithAllCorrect(){
        String username = "Tester1";
        String password = "Password1";
        int userId = 0;
        for(User user : users){
            if((Objects.equals(user.getUsername(), username)) && (Objects.equals(user.getPassword(), password))){
                userId = user.getUserID();
            }
        }
        user = new User(userId,username,password);
        String message = user.Login(userId);
        assertEquals("You have successfully logged in", message);
    }

    @Test
    public void testLoginUserWithincorrectUsername(){
        String username = "Tester4";
        String password = "Password1";
        int userId = 0;
        for(User user : users){
            if((Objects.equals(user.getUsername(), username)) && (Objects.equals(user.getPassword(), password))){
                userId = user.getUserID();
            }
        }
        user = new User(userId,username,password);
        String message = user.Login(userId);
        assertEquals("This combination of username and password is invalid, please try again", message);
    }

    @Test
    public void testLoginUserWithIncorrectPassword() {
        String username = "Tester1";
        String password = "Password4";
        int userId = 0;
        for (User user : users) {
            if ((Objects.equals(user.getUsername(), username)) && (Objects.equals(user.getPassword(), password))) {
                userId = user.getUserID();
            }
        }
        user = new User(userId, username, password);
        String message = user.Login(userId);
        assertEquals("This combination of username and password is invalid, please try again", message);

    }
}
