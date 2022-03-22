package csulb.cecs323.model;

import javax.persistence.*;

@Entity
public class Authoring_Entity {
    @Id
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 17)
    private String email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IBSN", nullable = false)
    private Books books;



    public Authoring_Entity(){};
    public Authoring_Entity(String name, String email){
        this.name = name;
        this.email = email;
    }

}
