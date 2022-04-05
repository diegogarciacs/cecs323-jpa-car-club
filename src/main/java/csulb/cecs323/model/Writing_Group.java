package csulb.cecs323.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(
        name = "ReturnWritingGroup",
        query = "SELECT * " +
                "FROM AUTHORING_ENTITIES " +
                "WHERE name = ? AND AUTHORING_ENTITY_TYPE = 'Writing Groups'",
        resultClass = Writing_Group.class
)
@DiscriminatorValue("Writing Groups")
public class Writing_Group extends Authoring_Entity
{
    //Variables outside the abstract class
    @Column(length = 80)
    private String head_writer;
    private int year_formed;

    //Constructors
    public Writing_Group() {};

    public Writing_Group(String name, String email, String head_writer, int year_formed)
    {
        super(name,email);
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

    @Override
    public String toString(){
        return ("Writing Group: "+getName() + "\nEmail: "+ getEmail() + "\nHead Writer: " +getHead_writer() + "\nYear" +
                " Formed: "+ getYear_formed()+"\n");
    }
}
