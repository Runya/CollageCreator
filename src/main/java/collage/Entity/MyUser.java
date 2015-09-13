package collage.Entity;

/**
 * Created by byte on 9/13/15.
 */
public class MyUser {

    private String userLogin;
    private String userImageUrl;
    private Long userId;

    public MyUser(long userId) {
        this.userId = userId;
    }

    public MyUser(String userLogin) {
        this.userLogin = userLogin;
    }
}
