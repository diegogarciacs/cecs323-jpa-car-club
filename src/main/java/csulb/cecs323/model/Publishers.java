package csulb.cecs323.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Publishers {
    @Id
    @Column(length=80, nullable = false)
    private String name;

    /** The cell phone number that we use to contact the publisher. */
    @Column(nullable = false, length = 80, unique = true)
    private String phone;

    @Column(nullable = false, length = 24, unique = true)
    private String email;

    //Default Constructor
    public Publishers() {};

    //Overloaded Constructor
    public Publishers(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
