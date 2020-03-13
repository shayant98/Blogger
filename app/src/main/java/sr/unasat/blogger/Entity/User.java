package sr.unasat.blogger.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String email;
    private String username;
    private String role;
    private String name;
    private String firstName;
    private String birthday;
    private String adress;
    private String district;
    private String phoneNumber;

    public User(int id, String email, String username, String role, String name, String firstName, String birthday, String adress, String district, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.name = name;
        this.firstName = firstName;
        this.birthday = birthday;
        this.adress = adress;
        this.district = district;
        this.phoneNumber = phoneNumber;
    }

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        username = in.readString();
        role = in.readString();
        name = in.readString();
        firstName = in.readString();
        birthday = in.readString();
        adress = in.readString();
        district = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", adress='" + adress + '\'' +
                ", district='" + district + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(role);
        dest.writeString(name);
        dest.writeString(firstName);
        dest.writeString(birthday);
        dest.writeString(adress);
        dest.writeString(district);
        dest.writeString(phoneNumber);
    }
}
