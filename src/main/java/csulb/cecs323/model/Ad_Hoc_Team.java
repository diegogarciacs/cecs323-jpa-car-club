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
    private String ad_hoc_team_email;

    public Ad_Hoc_Team(){};

    public Ad_Hoc_Team(String name, String email){
        super(email,name);
        authors = new HashSet<>();
    }
    //Getters and Setters

    public String getAd_hoc_team_email() {
        return ad_hoc_team_email;
    }

    public void setAd_hoc_team_email(String ad_hoc_teams_email) {
        this.ad_hoc_team_email = ad_hoc_teams_email;
    }

    public void addIndividualAuthors(Individual_Author author){
        authors.add(author);
    }
}
