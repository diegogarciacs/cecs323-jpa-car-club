package csulb.cecs323.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
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

    //Getters and Setters

    public String getAd_hoc_teams_email() {
        return ad_hoc_teams_email;
    }

    public void setAd_hoc_teams_email(String ad_hoc_teams_email) {
        this.ad_hoc_teams_email = ad_hoc_teams_email;
    }
}
