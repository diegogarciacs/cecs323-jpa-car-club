package csulb.cecs323.model;

import javax.persistence.*;

@Entity
public abstract class Authoring_Entity {

    @Column(length = 80, nullable = false)
    private String name;
    @Id
    @Column(length = 30, nullable = false)
    private String email;
    @Column(length = 31)
    private String authoring_entity_type;

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

    public String getAuthoring_entity_type() {
        return authoring_entity_type;
    }

    public void setAuthoring_entity_type(String authoring_entity_type) {
        this.authoring_entity_type = authoring_entity_type;
    }
}
