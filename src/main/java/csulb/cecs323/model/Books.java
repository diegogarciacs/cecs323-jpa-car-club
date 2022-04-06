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
     * @param ISBN IBSN of book.
     * @param title title of book.
     * @param year_published int year of book published.
     * @param authoring_entity Authoring_entity authoring entity associated with the book
     * @param publisher Publisher publisher object associated with the book.
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

    /**
     * Function that returns the IBSN of a book.
     * @return String IBSN of a book.
     */
    public String getIBSN() {
        return IBSN;
    }

    /**
     * Function that returns the title of a book.
     * @return String title of a book abject.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function that returns the name of an authoring entity.
     * @return String representing the name of the authoring entity.
     */
    public String getAuthoringEntity(){
        return (authoring_entity.getName());
    }

    /**
     * Function that takes in an authoring entity object and sets the current book to the new one passed in.
     * @param ae Authoring_Entity to change the book object to.
     */
    public void setAuthoring_entity(Authoring_Entity ae){this.authoring_entity = ae;}

    /**
     * Override toString method that prints out relevant bookn information for user.
     * @return String representing characteristics of a book.
     */
    @Override
    public String toString(){
        return ("IBSN: "+IBSN+"\nTitle: "+title+"\nYear Published: "+year_published+"\n Authoring Entitiy: "+
                authoring_entity.getName()+"\nPublisher: "+publisher+"\n");
    }
}
