package plscuddleme.yuhanlee.cremebrulee;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yuhanlee on 2018-03-16.
 */

public class Member {

    public String uuid;
    public String email;
    public String firstName;
    public String lastName;
    public String fullName;
    List<String> interestImageIds;


    public Member () {

    }
    public Member (String uuid, String email, String firstName, String lastName) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName () {
        return firstName + " " +lastName;
    }
    public String getUuid() {
        return uuid;
    }
    public String getFirstName() {
        return firstName;
    }

}
