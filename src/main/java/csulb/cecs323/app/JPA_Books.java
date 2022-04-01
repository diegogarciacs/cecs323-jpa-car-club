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
import java.util.Scanner;
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
public class JPA_Books {
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
   private static final Logger LOGGER = Logger.getLogger(JPA_Books.class.getName());

   /**
    * The constructor for the JPA Book class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    *
    * @param manager The EntityManager that we will use.
    */
   public JPA_Books(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of CarClub and store our new EntityManager as an instance variable.
      JPA_Books books_manager = new JPA_Books(manager);

      //BEGINNING OF CONSOLE OUTPUT
      Scanner sc = new Scanner(System.in);
       //Validates user input
      boolean circuit = true;
      while(circuit == true) {
         printMenu();
         String answer = sc.nextLine();
         answer = processMenuInput(answer);
         String selection;
         String object_answer;
         if (answer.equalsIgnoreCase("1")) {
            addNewObjectMenu();
            object_answer = sc.nextLine();
            object_answer = processaddNewObjectMenu(object_answer);
            List<String> new_authoring_instance = new ArrayList<String>();
            new_authoring_instance = gatherAuthoringInstanceData(object_answer, new_authoring_instance);
         } else if (answer.equalsIgnoreCase("2")) {
            listInformationMenu();
            object_answer = sc.nextLine();
            object_answer = processListInformationMenu(object_answer);
         } else if (answer.equalsIgnoreCase("3")) {
            System.out.println("Which book would you like to delete?");
            selection = sc.nextLine();
         } else if (answer.equalsIgnoreCase("4")) {
            System.out.println("Which book would you like to update?");
            selection = sc.nextLine();
         } else if (answer.equalsIgnoreCase("5")) {
            listPrimaryKeysMenu();
            object_answer = sc.nextLine();
            object_answer = processListPrimaryKeysMenu(object_answer);
         }

         circuit = keepGoing();
      } //end of while loop
      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();


      tx.begin();
//      // List of owners that I want to persist.  I could just as easily done this with the seed-data.sql
//      List <Owners> owners = new ArrayList<Owners>();
//      // Load up my List with the Entities that I want to persist.  Note, this does not put them
//      // into the database.
//      owners.add(new Owners("Reese", "Mike", "714-892-5544"));
//      owners.add(new Owners("Leck", "Carl", "714-321-3729"));
//      owners.add(new Owners("Guitierez", "Luis", "562-982-2899"));
//      // Create the list of owners in the database.
//      carclub.createEntity (owners);

      // Commit the changes so that the new data persists and is visible to other users.
      tx.commit();
      LOGGER.fine("End of Transaction");

   } // End of the main method

   /**
    * Create and persist a list of objects to the database.
    *
    * @param entities The list of entities to persist.  These can be any object that has been
    *                 properly annotated in JPA and marked as "persistable."  I specifically
    *                 used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List<E> entities) {
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
    *
    * //@param The name of the autobody style that you are looking for.
    * @return The auto_body_styles instance corresponding to that style name.
    */
//   public auto_body_styles getStyle (String name) {
//      // Run the native query that we defined in the auto_body_styles entity to find the right style.
//      List<auto_body_styles> styles = this.entityManager.createNamedQuery("ReturnAutoBodyStyle",
//              auto_body_styles.class).setParameter(1, name).getResultList();
//      if (styles.size() == 0) {
//         // Invalid style name passed in.
//         return null;
//      } else {
//         // Return the style object that they asked for.
//         return styles.get(0);
//      }
//   }// End of the getStyle method
   public static void printMenu() {
      System.out.println("What would you like to do?");
      System.out.println("1. Add a new object");
      System.out.println("2. List all information about an object");
      System.out.println("3. Delete a Book");
      System.out.println("4. Update a Book");
      System.out.println("5. List the primary keys of certain entities");
   }

   public static void addNewObjectMenu() {
      System.out.println("What new authoring instance would you like to commit?");
      System.out.println("1. Writing Group\n2. Individual Author\n3. Ad Hoc Team\n4. Ad Hoc Team Member (Individual Author)");
   }

   public static void listInformationMenu() {
      System.out.println("Which object would you like to list information about?");
      System.out.println("1. Publisher");
      System.out.println("2. Book");
      System.out.println("3. Writing Group");
   }

   public static void listPrimaryKeysMenu() {
      System.out.println("Which primary keys would you like to print?");
      System.out.println("1. Publishers");
      System.out.println("2. Books");
      System.out.println("3. Writing Group");
      System.out.println("4. Individual Author");
      System.out.println("5. Ad Hoc Team");
      System.out.println("6. Ad Hoc Team Member");
   }

   public static String processMenuInput(String answer) {
      boolean flag = false;
      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
              | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5")) {
         flag = true;
      } else {
         while (flag == false) {
            System.out.println();
            System.out.println("Not a valid answer.");
            System.out.println();
            Scanner sc = new Scanner(System.in);
            printMenu();
            answer = sc.nextLine();
            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
                    | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5")) {
               flag = true;
            }
            else {
               flag = false;
            }
         }
      }
      return answer;
   }

   public static String processaddNewObjectMenu(String answer) {
      boolean flag = false;
      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
              | answer.equalsIgnoreCase("4")) {
         flag = true;
      } else {
         while (flag == false) {
            System.out.println();
            System.out.println("Not a valid answer.");
            System.out.println();
            Scanner sc = new Scanner(System.in);
            addNewObjectMenu();
            answer = sc.nextLine();
            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
                    | answer.equalsIgnoreCase("4")) {
               flag = true;
            }
            else {
               flag = false;
            }
         }
      }
      return answer;
   }

   public static String processListInformationMenu(String answer) {
      boolean flag = false;
      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")) {
         flag = true;
      } else {
         while (flag == false) {
            System.out.println();
            System.out.println("Not a valid answer.");
            System.out.println();
            Scanner sc = new Scanner(System.in);
            listInformationMenu();
            answer = sc.nextLine();
            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")) {
               flag = true;
            }
            else {
               flag = false;
            }
         }
      }
      return answer;
   }

   public static String processListPrimaryKeysMenu(String answer){
      boolean flag = false;
      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
              | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
         flag = true;
      } else {
         while (flag == false) {
            System.out.println();
            System.out.println("Not a valid answer.");
            System.out.println();
            Scanner sc = new Scanner(System.in);
            listPrimaryKeysMenu();
            answer = sc.nextLine();
            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
                    | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
               flag = true;
            }
            else {
               flag = false;
            }
         }
      }
      return answer;
   }

   public static List<String> gatherAuthoringInstanceData(String object_answer, List<String> authoring_instance) {
      Scanner sc = new Scanner(System.in);
      List<String> authoring_entity = new ArrayList<String>();

      System.out.println("Let us start with the authoring entity data itself: ");
      System.out.println("Please enter a name: ");
      String name = sc.nextLine();
      System.out.println("Please enter an email: ");
      String email = sc.nextLine();

      String authoring_entity_type = "";

      if(object_answer.equalsIgnoreCase("1")){
         System.out.println("You have chosen a new Writing Group.");
         authoring_entity_type = "Writing Group";
         
      } else if (object_answer.equalsIgnoreCase("2")) {
         System.out.println("You have chosen a new Individual Author.");
         authoring_entity_type = "Individual Author";
      } else if (object_answer.equalsIgnoreCase("3")) {
         System.out.println("You have chosen a new Ad Hoc Team.");
         authoring_entity_type = "Ad Hoc Team";
      } else {
         System.out.println("You have chosen a new Ad Hoc Team Member.");
         authoring_entity_type = "Ad Hoc Team Member";
      }
      authoring_entity.add(name);
      authoring_entity.add(email);
      authoring_entity.add(authoring_entity_type);




      return authoring_instance;
   }


   public static boolean keepGoing() {
      Scanner sc = new Scanner(System.in);

      System.out.println("Would you like to keep going? (y/n)");
      String answer = sc.nextLine();
      boolean flag = false;
      boolean keep_going = true;
      while(flag == false) {


         if (answer.equalsIgnoreCase("y")) {
            flag = true;
            keep_going = true;
         } else if (answer.equalsIgnoreCase("n")) {
            flag = true;
            keep_going = false;
         } else {
            System.out.println("Bad answer. Please enter either 'y' or 'n'");
            answer = sc.nextLine();
         }
      }
      return keep_going;
   }
}// End of CarClub class


