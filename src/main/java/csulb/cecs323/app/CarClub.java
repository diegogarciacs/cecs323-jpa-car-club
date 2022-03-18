/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class CarClub {
   /**
    * You will likely need the entityManager in a great many functions throughout your application.
    * Rather than make this a global variable, we will make it an instance variable within the CarClub
    * class, and create an instance of CarClub in the main.
    */
   private EntityManager entityManager;

   /**
    * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
    * We use it because it is easy to control how much or how little logging gets done without having to
    * go through the application and comment out/uncomment code and run the risk of introducing a bug.
    * Here also, we want to make sure that the one Logger instance is readily available throughout the
    * application, without resorting to creating a global variable.
    */
   private static final Logger LOGGER = Logger.getLogger(CarClub.class.getName());

   /**
    * The constructor for the CarClub class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    * @param manager    The EntityManager that we will use.
    */
   public CarClub(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("CarClub");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of CarClub and store our new EntityManager as an instance variable.
      CarClub carclub = new CarClub(manager);


      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();
      // List of owners that I want to persist.  I could just as easily done this with the seed-data.sql
      List <Owners> owners = new ArrayList<Owners>();
      // Load up my List with the Entities that I want to persist.  Note, this does not put them
      // into the database.
      owners.add(new Owners("Reese", "Mike", "714-892-5544"));
      owners.add(new Owners("Lack", "Carl", "714-321-3729"));
      owners.add(new Owners("Gutierrez", "Luis", "562-982-2899"));
      owners.add(new Owners("Diego", "Garcia", "562-778-2432"));
      owners.add(new Owners("Brandon", "Lane", "714-239-5272"));
      owners.add(new Owners("Harold", "Germo", "915-982-2899"));
      // Create the list of owners in the database.
      carclub.createEntity (owners);
      auto_body_styles v1 = new auto_body_styles();
      auto_body_styles v2 = new auto_body_styles();
      auto_body_styles v3 = new auto_body_styles();
      auto_body_styles v4 = new auto_body_styles();
      auto_body_styles v5 = new auto_body_styles();
      auto_body_styles v6 = new auto_body_styles();
      auto_body_styles v7 = new auto_body_styles();
      auto_body_styles v8 = new auto_body_styles();
      auto_body_styles v9 = new auto_body_styles();

      v1.setName("convertible");
      v1.setDescription("Sleek sporty alternatives: top down or up.");
      v2.setName("coupe");
      v2.setDescription("Fixed roof, two doors, and a sloping rear.");
      v3.setName("hatchback");
      v3.setDescription("FA car with a door across the full width at the back end that upens upward to provide easy " +
              "access for loading.");
      v4.setName("minivan");
      v4.setDescription("A small van, typically one fitted with seats in the back for passengers.");
      v5.setName("pickup truck");
      v5.setDescription("A small truck with an enclosed cab and open back.");
      v6.setName("sedan");
      v6.setDescription("An automobile having a closed body and a closed trunk separated from the part in which the " +
              "driver and passengers sit.");
      v7.setName("sport-utility vehicle");
      v7.setDescription("A high-performance four-wheel-drive vehicle.");
      v8.setName("sports car");
      v8.setDescription("A low-built car designed for performance at high speeds.");
      v9.setName("station wagon");
      v9.setDescription("A car with longer body than usual, incorporating a large carrying area behind the seats and " +
              "having an extra door at the rear for easy loading.");




      // String VIN, String manufacturer, String model, int year, Owners owner
      List <Cars> cars = new ArrayList<Cars>();
      cars.add(new Cars(v2,"3VWDX7AJ5BM006256","Volkswagen","Jetta",1998, owners.get(0)));
      cars.add(new Cars(v4,"2S3TD52V3Y6103456","Suzuki","Vitara",2000, owners.get(0)));
      cars.add(new Cars(v2,"JH4DB1660PS005158","Acura","Integra",1993, owners.get(1)));
      cars.add(new Cars(v3,"2P4GH2535SR296546","Plymouth","Voyager",1995, owners.get(1)));
      cars.add(new Cars(v7,"1HGCG1657WA051534","Honda","Accord",1998, owners.get(2)));
      cars.add(new Cars(v6,"1FUJAPCK25DU88948","Frieghtliner","Conventional",2005, owners.get(2)));
      cars.add(new Cars(v4,"JN1DA31A52T300757","Nissan","Maxima",2002, owners.get(3)));
      cars.add(new Cars(v8,"JTHFE2C24A2504933","Lexus","IS 350C",2010, owners.get(3)));
      cars.add(new Cars(v1,"JYAVP18E07A005321","Yamaha","XVZ13",2007, owners.get(4)));
      cars.add(new Cars(v2,"JH4KA7680RC011845","Acura","Legend",1994, owners.get(4)));
      cars.add(new Cars(v6,"JH4DA9360MS000737","Acura","Integra",1991, owners.get(5)));
      cars.add(new Cars(v6,"1MEFM53S4XA661641","Mercury","Sable",1999, owners.get(5)));
      carclub.createEntity(cars);
      // Commit the changes so that the new data persists and is visible to other users.
      tx.commit();


      LOGGER.fine("End of Transaction");

   } // End of the main method

   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CarClub entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   /**
    * Think of this as a simple map from a String to an instance of auto_body_styles that has the
    * same name, as the string that you pass in.  To create a new Cars instance, you need to pass
    * in an instance of auto_body_styles to satisfy the foreign key constraint, not just a string
    * representing the name of the style.
    * @param name       The name of the autobody style that you are looking for.
    * @return           The auto_body_styles instance corresponding to that style name.
    */
   public auto_body_styles getStyle (String name) {
      // Run the native query that we defined in the auto_body_styles entity to find the right style.
      List<auto_body_styles> styles = this.entityManager.createNamedQuery("ReturnAutoBodyStyle",
              auto_body_styles.class).setParameter(1, name).getResultList();
      if (styles.size() == 0) {
         // Invalid style name passed in.
         return null;
      } else {
         // Return the style object that they asked for.
         return styles.get(0);
      }
   }// End of the getStyle method
} // End of CarClub class
