package csulb.cecs323.model;

import javax.persistence.Entity;

@Entity
public class Writing_Group extends Authoring_Entity
{
    //Variables outside of the abstract class
    private String head_writer;
    private int year_formed;

    //Constructors
    public Writing_Group() {};

    public Writing_Group(String name, String email, String authoring_entity_type, String head_writer, int year_formed)
    {
        this.name = name;
        this.email = email;
        this.authoring_entity_type = authoring_entity_type;
        this.head_writer = head_writer;
        this.year_formed = year_formed;
    }

    //Getters and Setters
    public String getHead_writer() {
        return head_writer;
    }

    public void setHead_writer(String head_writer) {
        this.head_writer = head_writer;
    }

    public int getYear_formed() {
        return year_formed;
    }

    public void setYear_formed(int year_formed) {
        this.year_formed = year_formed;
    }
}
