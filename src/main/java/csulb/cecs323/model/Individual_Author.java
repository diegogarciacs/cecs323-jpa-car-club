package csulb.cecs323.model;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Individual_Author extends Authoring_Entity
{

    @ManyToMany
    Set<Ad_Hoc_Teams> ad_hoc_teams;

    public Individual_Author() {};

    public Individual_Author(String name, String email)
    {
        this.name = name;
        this.email = email;
        this.authoring_entity_type = "Individual Author";
    }


}
