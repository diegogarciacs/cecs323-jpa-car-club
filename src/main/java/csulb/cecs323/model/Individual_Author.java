package csulb.cecs323.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import java.util.Set;

@Entity
@NamedNativeQuery(
        name = "ReturnIndividualAuthor",
        query = "SELECT * " +
                "FROM AUTHORING_ENTITIES " +
                "WHERE name = ? AND AUTHORING_ENTITY_TYPE = 'Individual Author'",
        resultClass = Individual_Author.class
)
@DiscriminatorValue("Individual Author")
public class Individual_Author extends Authoring_Entity
{

    @ManyToMany(mappedBy = "authors")
    Set<Ad_Hoc_Teams> ad_hoc_teams;

    public Individual_Author() {};

    public Individual_Author(String name, String email)
    {
        super(name,email);

    }


}
