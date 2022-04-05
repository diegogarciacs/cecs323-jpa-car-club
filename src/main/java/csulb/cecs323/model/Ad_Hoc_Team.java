package csulb.cecs323.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedNativeQuery(
        name = "ReturnAdHocTeam",
        query = "SELECT * " +
                "FROM AUTHORING_ENTITIES " +
                "WHERE name = ? AND AUTHORING_ENTITY_TYPE = 'Ad Hoc Team'",
        resultClass = Ad_Hoc_Team.class
)
@DiscriminatorValue("Ad Hoc Team")
public class Ad_Hoc_Team extends Authoring_Entity
{
    @ManyToMany
    @JoinTable(
        name = "Ad_Hoc_Team_Member",
        joinColumns = @JoinColumn(name = "ad_hoc_teams_email"),
        inverseJoinColumns = @JoinColumn(name = "individual_authors_email")
    )
    Set<Individual_Author> authors;


    public Ad_Hoc_Team(){};

    public Ad_Hoc_Team(String name, String email){
        super(name,email);
        authors = new HashSet<>();

    }
    @Override
    public String toString(){
        return "Ad Hoc Team Name: " + getName() + "\nAd Hoc Team Email: "+ getEmail()+ "\n";
    }

    public void addIndividualAuthors(Individual_Author author){
        authors.add(author);
    }
}
