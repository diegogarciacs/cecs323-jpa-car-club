package csulb.cecs323.model;

import org.eclipse.persistence.annotations.PrimaryKey;
import javax.persistence.*;



/**
 * Individual, physical automobiles that someone can drive on land to transport one or more passengers
 * and a limited amount of cargo around.  Cars have four wheels and usually travel on paved roads.
 */
@Entity
public class Cars {
    /** The unique ID of the vehicle.  Limited to 17 characters. */
    @Id
    @Column(nullable = false, length = 17)
    private String VIN;

    /** The name of the corporation which manufactured the vehicle.  Limited to 40 characters. */
    @Column(nullable = false, length = 40)
    private String manufacturer;

    /** The popular name of the vehicle, like the Prius for Toyota.  Limited to 20 characters. */
    @Column(nullable = false, length = 20)
    private String model;

    /** The year that the vehicle was manufactured.  For now, do not worry about validating this #. */
    @Column(nullable = false)
    private int model_year;
    
    /** Many-to-one association defined in Cars.*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owners owners;

//    The primary key of auto_body_styles is an attribute called “name”. You will want
//    to role name that to “auto_body_style_name” in Cars.
//    ii. See here for how to use the join column annotation to give a role name to a
//    migrating foreign key.
    /** Trying to rename an attribute from auto_body_styles to auto_body_styles in cars. */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="auto_body_style_name", referencedColumnName = "name", nullable = false)
    private auto_body_styles auto_body_styles;


    public Cars(auto_body_styles auto_body_styles, String VIN, String manufacturer, String model, int year,
                Owners owner){
        this.auto_body_styles = auto_body_styles;
        this.VIN = VIN;
        this.manufacturer = manufacturer;
        this.model = model;
        this.model_year = year;
        setOwners(owner);
    }
    public Cars (){}


    public void setOwners(Owners owner){
        this.owners = owner;
    }

    @Override
    public String toString () {
        return "Cars - VIN: " + this.VIN + " Manufacturer: " + this.manufacturer +
                " Model: " + this.model + " year: " + this.model_year;
    }
}
