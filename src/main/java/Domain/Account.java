package Domain;

public class Account extends Entity<String>{
    private String password;
    private UserType userType;
    private String id;
    public Account() {
        super("");
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Account(String username, String password, UserType userType) {
        super(username);
        this.id = username;
        this.password = password;
        this.userType=userType;
    }
    public String getId(){
        return super.getId();
    }
    public void setId(String id){
        super.setId(id);
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername(){
        return super.getId();
    }

    @Override
    public String toString() {
        return "Account{" +
                "password='" + password + '\'' +
                ", userType=" + userType +
                ", id='" + id + '\'' +
                '}';
    }
}
