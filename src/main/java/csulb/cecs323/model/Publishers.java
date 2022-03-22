package csulb.cecs323.model;

import javax.persistence.*;

/**
 *
 */
@Entity
public class Publishers {
    @Id
    @Column(length=80, nullable = false)
    private String name;

    /** The cell phone number that we use to contact the publisher. */
    @Column(nullable = false, length = 80, unique = true)
    private String phone;

    @Column(nullable = false, length = 24, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IBSN", nullable = false)
    private Books books;

    public Publishers() {};

    public Publishers(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }



}
