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

    /***
     *
     * Basic constructor for authoring entity.
     *
     * @param name
     * @param email
     */
    public Authoring_Entity(String name, String email){
        this.name = name;
        this.email = email;
    }

    //Getters and Setters

    /**
     * Function that returns private name of authoring entity.
     * @return name of authoring entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns name of email associated with authoring entity.
     * @return string email of authoring entity.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method that overrides to string of authoring entity.
     * @return string that displays author and email of authoring entity.
     */
    @Override
    public String toString(){
        return ("Author: "+getName() + "\nEmail: "+ getEmail()+"\n");
    }

}
