package plscuddleme.yuhanlee.cremebrulee;

/**
 * Created by yuhanlee on 2018-03-16.
 */

public class Member {

    public String uuid;
    public String email;
    public String firstName;
    public String lastName;
    public String fullName;

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

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Member{" +
                "uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
