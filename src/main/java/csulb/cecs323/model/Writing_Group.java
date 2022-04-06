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

    /***
     *
     * Basic constructor for a writing group entity.
     *
     * @param name string name of writing group.
     * @param email string email of writing group.
     * @param head_writer string name of head writer.
     * @param year_formed int year that writing group was formed.
     */
    public Writing_Group(String name, String email, String head_writer, int year_formed)
    {
        super(name,email);
        this.head_writer = head_writer;
        this.year_formed = year_formed;
    }

    //Getters and Setters

    /**
     * Returns the head writer in string format.
     * @return String head writer name.
     */
    public String getHead_writer() {
        return head_writer;
    }


    /**
     * Returns year formed of writing group.
     * @return int of year formed of writing group.
     */
    public int getYear_formed() {
        return year_formed;
    }


    /**
     * To string method overriden to print writing groups.
     * @return string of writing group characteristics.
     */
    @Override
    public String toString(){
        return ("Writing Group: "+getName() + "\nEmail: "+ getEmail() + "\nHead Writer: " +getHead_writer() + "\nYear" +
                " Formed: "+ getYear_formed()+"\n");
    }
}
