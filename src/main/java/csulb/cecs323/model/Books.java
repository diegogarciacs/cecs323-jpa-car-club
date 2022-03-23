package csulb.cecs323.model;
import javax.persistence.*;

@Entity
public class Books {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "name", nullable = false)
    private Publishers publisher;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "email", nullable = false)
    private Authoring_Entity authoring_entity;

    @Id
    @Column(length = 17, nullable = false)
    private String IBSN;
    @Column(length = 80, nullable = false)
    private String title;
    @Column(nullable = false)
    private int year_published;

    //Default Constructor
    public Books() {}

    //Overloaded Constructor
    public Books(String ISBN, String title, int year_published, Authoring_Entity authoring_entity, Publishers publisher)
    {
        this.IBSN = ISBN;
        this.title = title;
        this.year_published = year_published;
        this.authoring_entity = authoring_entity;
        this.publisher = publisher;
    }

    //Getters and Setters
    public String getIBSN() {
        return IBSN;
    }

    public void setIBSN(String IBSN) {
        this.IBSN = IBSN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }
}
