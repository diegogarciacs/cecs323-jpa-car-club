package csulb.cecs323.model;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "books_ck_01", columnNames = {"title", "publisher_name"}),
        @UniqueConstraint(name = "books_ck_02", columnNames = {"title", "authoring_entity_name"})})
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

    /***
     *
     * Basic constructor for a book entity.
     *
     * @param ISBN
     * @param title
     * @param year_published
     * @param authoring_entity
     * @param publisher
     */
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

    public String getAuthoringEntity(){
        return (authoring_entity.getName());
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

    public void setAuthoring_entity(Authoring_Entity ae){this.authoring_entity = ae;}

    @Override
    public String toString(){
        return ("IBSN: "+IBSN+"\nTitle: "+title+"\nYear Published: "+year_published+"\n Authoring Entitiy: "+
                authoring_entity.getName()+"\nPublisher: "+publisher+"\n");
    }
}
