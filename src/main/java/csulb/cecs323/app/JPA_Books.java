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
import org.apache.derby.impl.store.raw.log.Scan;

import javax.persistence.*;
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
      // Create an instance of JPA Books and store our new EntityManager as an instance variable.
      JPA_Books books_manager = new JPA_Books(manager);
      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();
      tx.begin();

      //BEGINNING OF CONSOLE OUTPUT
      Scanner sc = new Scanner(System.in);
       //Validates user input
      boolean circuit = true;
      while(circuit == true) {
         List<Writing_Group> writingGroupsList =
                 manager.createQuery("SELECT w FROM Writing_Group w ", Writing_Group.class).getResultList();
         List<Individual_Author> individualAuthors = manager.createQuery("SELECT ia FROM Individual_Author ia",
                 Individual_Author.class).getResultList();
         List<Ad_Hoc_Teams> adHocTeamsList =
                 manager.createQuery("SELECT aT FROM Ad_Hoc_Teams aT", Ad_Hoc_Teams.class).getResultList();
         List<Publishers> publishersList =
                 manager.createQuery("SELECT p FROM Publishers p",Publishers.class).getResultList();
         printMenu();
         int answer = getIntRange(1,5);
         String selection;
         if (answer == 1) {
            addNewObjectMenu();
            int object_answer = getIntRange(1,6);
            if (object_answer == 1){
               //add the writing team
               createWritingTeam(writingGroupsList);
               
            } else if (object_answer == 2) {
               //add individual author
               createIndividualAuthors(individualAuthors);
               if (adHocTeamsList.size() > 0){
                  System.out.println("Wanna add this author to an ad hoc team?");
               }
               
            } else if (object_answer == 3) {
               //add ad hoc team
               createAdHocTeam(adHocTeamsList);

            } else if (object_answer == 5) {
               // add publisher
               createPublisher(publishersList);

            } else if (object_answer == 6) {
               // add book
            }
         } else if (answer == 2) {
            listInformationMenu();
            int object_answer = getIntRange(1,3);
            listObjectInformation(object_answer);
         } else if (answer == 3) {
            System.out.println("Which book would you like to delete?");
            selection = sc.nextLine();
            //make sure book is in database
         } else if (answer == 4) {
            System.out.println("Which book would you like to update?");
            selection = sc.nextLine();
            //make sure book is in database
         } else if (answer == 5) {
            listPrimaryKeysMenu();
            int object_answer = getIntRange(1,6);
            listPrimaryKeys(object_answer);
         }


         circuit = keepGoing();
         books_manager.createEntity(writingGroupsList);
         books_manager.createEntity(adHocTeamsList);
         books_manager.createEntity(publishersList);
//         books_manager.createEntity(books);
         books_manager.createEntity(individualAuthors);
         tx.commit();
      } //end of while loop

      LOGGER.fine("End of Transaction");

   } // End of the main method

   private static void createPublisher(List<Publishers> publishersList) {
      System.out.println("Please input the name of the publisher.");
      String name = getString();
      System.out.println("Please input the phone number of the publisher.");
      String phone = getString();
      System.out.println("Please input the email of the individual author.");
      String email = getString();
      publishersList.add(new Publishers(name,phone,email));
   }

   private static void createAdHocTeam(List<Ad_Hoc_Teams> adHocTeams) {
      System.out.println("Please input the name of the ad hoc team.");
      String name = getString();
      System.out.println("Please input the email of the ad hoc team.");
      String email = getString();
      adHocTeams.add(new Ad_Hoc_Teams(name,email));
   }

   private static void createIndividualAuthors(List<Individual_Author> individualAuthors) {
      System.out.println("Please input the name the individual author.");
      String name = getString();
      System.out.println("Please input the email of the individual author.");
      String email = getString();
      individualAuthors.add(new Individual_Author(email,name));
   }

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
   public static void createWritingTeam(List<Writing_Group> writing_group){
      System.out.println("Please input the name of the writing group.");
      String name = getString();
      System.out.println("Please input the email of the writing group.");
      String email = getString();
      System.out.println("Please input the name of the head writer.");
      String headWriter = getString();
      System.out.println("Please input the year that the writing group was formed.");
      int yearFormed = getInt();
      writing_group.add(new Writing_Group(email,name,headWriter,yearFormed));
   }
   /**
    * Takes in a string from the user.
    * @return the inputted String.
    */
   public static String getString() {
      Scanner in = new Scanner( System.in );
      String input = in.nextLine();
      return input;
   }
   /**
    * Checks if the inputted value is an integer.
    * @return the valid input.
    */
   public static int getInt() {
      Scanner in = new Scanner( System.in );
      int input = 0;
      boolean valid = false;
      while( !valid ) {
         if( in.hasNextInt() ) {
            input = in.nextInt();
            valid = true;
         } else {
            in.next(); //clear invalid string
            System.out.println( "Invalid Input." );
         }
      }
      return input;
   }
   public static void printMenu() {
      System.out.println("What would you like to do?");
      System.out.println("1. Add a new object");
      System.out.println("2. List all information about an object");
      System.out.println("3. Delete a Book");
      System.out.println("4. Update a Book");
      System.out.println("5. List the primary keys of certain entities");
   }

   public static void addNewObjectMenu() {


      System.out.println("What new object would you like to commit?");
      System.out.println("Authoring Entities:");
      System.out.println("1. Writing Group");
      System.out.println("2. Individual Author");
      System.out.println("3. Ad Hoc Team");
      System.out.println("4. Ad Hoc Team Member (Individual Author)");
      System.out.println("Other objects: ");
      System.out.println("5. Publisher");
      System.out.println("6. Book");


      System.out.println("What object would you like to add?");
      System.out.println("1. Writing Group\n2. Individual Author\n3. Ad Hoc Team\n4. Ad Hoc Team Member (Individual " +
              "Author)\n5. Add a new publisher.\n6. Add a new book.");


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

//   public static String processaddNewObjectMenu(String answer) {
//      boolean flag = false;
//      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//              | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
//         flag = true;
//      } else {
//         while (flag == false) {
//            System.out.println();
//            System.out.println("Not a valid answer.");
//            System.out.println();
//            Scanner sc = new Scanner(System.in);
//            addNewObjectMenu();
//            answer = sc.nextLine();
//            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//                    | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
//               flag = true;
//            }
//            else {
//               flag = false;


   public static int getIntRange( int low, int high ) {
      Scanner in = new Scanner( System.in );
      int input = 0;
      boolean valid = false;
      while( !valid ) {
         if( in.hasNextInt() ) {
            input = in.nextInt();
            if( input <= high && input >= low ) {
               valid = true;
            } else {
               System.out.println( "Invalid Range." );
            }
         } else {
            in.next(); //clear invalid string
            System.out.println( "Invalid Input." );
         }
      }
      return input;
   }

//   public static String processMenuInput(String answer) {
//      boolean flag = false;
//      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//              | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
//         flag = true;
//      } else {
//         while (flag == false) {
//            System.out.println();
//            System.out.println("Not a valid answer.");
//            System.out.println();
//            Scanner sc = new Scanner(System.in);
//            printMenu();
//            answer = sc.nextLine();
//            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//                    | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5")) {
//               flag = true;
//            }
//            else {
//               flag = false;
//            }
//         }
//      }
//      return answer;
//   }

//   public static String processaddNewObjectMenu(String answer) {
//      boolean flag = false;
//      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//              | answer.equalsIgnoreCase("4")) {
//         flag = true;
//      } else {
//         while (flag == false) {
//            System.out.println();
//            System.out.println("Not a valid answer.");
//            System.out.println();
//            Scanner sc = new Scanner(System.in);
//            addNewObjectMenu();
//            answer = sc.nextLine();
//            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//                    | answer.equalsIgnoreCase("4")) {
//               flag = true;
//            }
//            else {
//               flag = false;
//            }
//         }
//      }
//      return answer;
//   }

//   public static String processListInformationMenu(String answer) {
//      boolean flag = false;
//      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")) {
//         flag = true;
//      } else {
//         while (flag == false) {
//            System.out.println();
//            System.out.println("Not a valid answer.");
//            System.out.println();
//            Scanner sc = new Scanner(System.in);
//            listInformationMenu();
//            answer = sc.nextLine();
//            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")) {
//               flag = true;
//            }
//            else {
//               flag = false;
//            }
//         }
//      }
//      return answer;
//   }

//   public static String processListPrimaryKeysMenu(String answer){
//      boolean flag = false;
//      if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//              | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
//         flag = true;
//      } else {
//         while (flag == false) {
//            System.out.println();
//            System.out.println("Not a valid answer.");
//            System.out.println();
//            Scanner sc = new Scanner(System.in);
//            listPrimaryKeysMenu();
//            answer = sc.nextLine();
//            if (answer.equalsIgnoreCase("1") | answer.equalsIgnoreCase("2") | answer.equalsIgnoreCase("3")
//                    | answer.equalsIgnoreCase("4") | answer.equalsIgnoreCase("5") | answer.equalsIgnoreCase("6")) {
//               flag = true;
//            }
//            else {
//               flag = false;
//            }
//         }
//      }
//      return answer;
//   }

//   public static List<String> gatherAuthoringInstanceData(int object_answer, List<Authoring_Entity> authoring_instance) {
//      Scanner sc = new Scanner(System.in);
//      List<Authoring_Entity> authoring_entity = new ArrayList<Authoring_Entity>();
//
//      System.out.println("Let us start with the authoring entity data itself: ");
//      System.out.println("Please enter a name: ");
//      String name = sc.nextLine();
//      System.out.println("Please enter an email: ");
//      String email = sc.nextLine();
//
//      String authoring_entity_type = "";
//
//      if(object_answer == 1){
//         System.out.println("You have chosen a new Writing Group.");
//         authoring_entity_type = "Writing Group";
//      } else if (object_answer == 2) {
//         System.out.println("You have chosen a new Individual Author.");
//         authoring_entity_type = "Individual Author";
//      } else if (object_answer == 3) {
//         System.out.println("You have chosen a new Ad Hoc Team.");
//         authoring_entity_type = "Ad Hoc Team";
//      } else if (object_answer == 4){
//         System.out.println("You have chosen a new Ad Hoc Team Member.");
//         authoring_entity_type = "Ad Hoc Team Member";
//      }
//      authoring_instance.add(new Authoring_Entity(name,email));
//
//
//      return authoring_instance;
//   }


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

//   public static void commitAuthoringInstance(String object_answer, List<String> new_authoring_instance) {
//      Scanner sc = new Scanner(System.in);
//      String authoring_entity_type;
//
//      if(object_answer.equalsIgnoreCase("1")){
//         authoring_entity_type = "Writing Group";
//         System.out.println("Please give me a head writer: ");
//         String head_writer = sc.nextLine();
//         System.out.println("Please give me a year (integer): ");
//         String year_formed = sc.nextLine();
//         new_authoring_instance.add(head_writer);
//         new_authoring_instance.add(year_formed);
//         //commit list
//      } else if (object_answer.equalsIgnoreCase("2")) {
//         authoring_entity_type = "Individual Author";
//         //commit list
//      } else if (object_answer.equalsIgnoreCase("3")) {
//         authoring_entity_type = "Ad Hoc Team";
//         System.out.println("Please give me the Ad Hoc Teams' email: ");
//         String ad_hoc_teams_email = sc.nextLine();
//         //commit list
//      } else if (object_answer.equalsIgnoreCase("4")) {
//         authoring_entity_type = "Ad Hoc Team Member";
//         System.out.println("Which Ad Hoc Team would you like to add an author to?: ");
//         String ad_hoc_team = sc.nextLine();
//         //check if ad hoc team exists
//         System.out.println("Which author would you like to add to the team?\n" +
//                 "Give the email: ");
//         String individual_author_email = sc.nextLine();
//         //check if individual author exists
//         //put individual author into the team
//      }
//   }//end method

//   public static void commitOtherObject(String object_answer){
//      List<String> information = new ArrayList<String>();
//      Scanner sc = new Scanner(System.in);
//      if(object_answer.equalsIgnoreCase("5")){
//         System.out.println("What is the publisher's name?: ");
//         String publisher_name = sc.nextLine();
//         System.out.println("What is the publisher's phone number?: ");
//         String publisher_phone = sc.nextLine();
//         System.out.println("What is the publisher's email?: ");
//         String publisher_email = sc.nextLine();
//         information.add(publisher_name);
//         information.add(publisher_phone);
//         information.add(publisher_email);
//         //commit list
//      } else if(object_answer.equalsIgnoreCase("6")){
//         System.out.println("What is the book publisher's name?: ");
//         String book_publisher = sc.nextLine();
//         //check if publisher is in database
//         System.out.println("What is the authoring entity email for the book?: ");
//         String book_entity_email = sc.nextLine();
//         //check if authoring entity is in database
//         System.out.println("What is the book's ISBN?: ");
//         String book_ISBN = sc.nextLine();
//         System.out.println("What is the book's title?: ");
//         String book_title = sc.nextLine();
//         information.add(book_publisher);
//         information.add(book_entity_email);
//         information.add(book_ISBN);
//         information.add(book_title);
//         //commit list
//      }
//   }//end method

   public static void listObjectInformation(int object_answer) {
      Scanner sc = new Scanner(System.in);
      if(object_answer == 1){
         System.out.println("Which publisher would you like to list? Give the name: ");
         String publisher = sc.nextLine();
         //make sure publisher is in the database
         //list out the information
      } else if (object_answer == 2 ) {
         System.out.println("Which Book would you like to list? Give the ISBN: ");
         String book = sc.nextLine();
         //make sure book is in database
         //list out the information
      } else if (object_answer == 3) {
         System.out.println("Which Writing Group would you like to list? Give the email: ");
         String writing_group = sc.nextLine();
         //make sure writing group is in the database
         //list out the information
      }
   }//end method

   public static void listPrimaryKeys(int object_answer) {

      if(object_answer == 1){
         //list primary keys of publishers
      } else if (object_answer == 2){
         //list primary keys of books
      } else if (object_answer== 3){
         //list primary keys of writing groups
      } else if (object_answer == 4){
         //list primary keys of individual author
      } else if (object_answer == 5){
         //list primary keys of ad hoc teams
      } else if (object_answer == 6){
         //list primary keys of ad hoc team members
      }
   }//end method

}// End of CarClub class


