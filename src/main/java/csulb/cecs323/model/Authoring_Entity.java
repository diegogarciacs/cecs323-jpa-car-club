package csulb.cecs323.model;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Authoring_Entity_Type",discriminatorType = DiscriminatorType.STRING)
public abstract class Authoring_Entity {

    @Column(length = 80, nullable = false)
    private String name;
    @Id
    @Column(length = 30, nullable = false)
    private String email;

    public Authoring_Entity(){};
    public Authoring_Entity(String name, String email){
        this.name = name;
        this.email = email;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return ("Author: "+getName() + "\nEmail: "+ getEmail()+"\n");
    }

}
