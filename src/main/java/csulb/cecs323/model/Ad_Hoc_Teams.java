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
        resultClass = Ad_Hoc_Teams.class
)
@DiscriminatorValue("Ad Hoc Team")
public class Ad_Hoc_Teams extends Authoring_Entity
{
    private String ad_hoc_teams_email;

    @ManyToMany
    @JoinTable(
        name = "Ad_Hoc_Team_Member",
        joinColumns = @JoinColumn(name = "email"),
        inverseJoinColumns = @JoinColumn(name = "email")
    )
    Set<Individual_Author> authors;

    public Ad_Hoc_Teams(){};

    public Ad_Hoc_Teams(String name, String email){
        super(email,name);
        authors = new HashSet<>();
    }
    //Getters and Setters

    public String getAd_hoc_teams_email() {
        return ad_hoc_teams_email;
    }

    public void setAd_hoc_teams_email(String ad_hoc_teams_email) {
        this.ad_hoc_teams_email = ad_hoc_teams_email;
    }

    public void addIndividualAuthors(Individual_Author author){
        authors.add(author);
    }
}
