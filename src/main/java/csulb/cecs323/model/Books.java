package csulb.cecs323.model;
import javax.persistence.*;

@Entity
public class Books {
    @Id
    @Column(nullable = false, length = 17)
    private String IBSN;
    @Column (length=80, nullable = false)
    private String title;
    @Column (nullable = false)
    private int yearPublished;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorithing_entities", referencedColumnName = "AUTHORING_ENTITY_NAME")
    public Books(){};
    public Books(String IBSN, String title, int yearPublished){
        this.IBSN = IBSN;
        this.title = title;
        this.yearPublished = yearPublished;
    }


}
