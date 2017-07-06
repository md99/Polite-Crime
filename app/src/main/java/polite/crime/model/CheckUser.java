package polite.crime.model;

/**
 * Created by admin on 7/5/2017.
 */

public class CheckUser {
    public CheckUser() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String mobile;
    private String password;
}
