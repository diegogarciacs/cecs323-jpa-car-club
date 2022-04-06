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

import javax.persistence.*;
import java.util.Scanner;
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
    * Rather than make this a global variable, we will make it an instance variable within the JPABook
    * class, and create an instance of JPABook in the main.
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


      //BEGINNING OF CONSOLE OUTPUT
      Scanner sc = new Scanner(System.in);
       //Validates user input
      boolean circuit = true;
      while(circuit == true) {
         tx.begin();
         List<Writing_Group> writingGroupsList =
                 manager.createQuery("SELECT w FROM Writing_Group w ", Writing_Group.class).getResultList();
         List<Individual_Author> individualAuthorsList = manager.createQuery("SELECT ia FROM Individual_Author ia",
                 Individual_Author.class).getResultList();
         List<Ad_Hoc_Team> adHocTeamsList =
                 manager.createQuery("SELECT aT FROM Ad_Hoc_Team aT", Ad_Hoc_Team.class).getResultList();
         List<Publishers> publishersList =
                 manager.createQuery("SELECT p FROM Publishers p",Publishers.class).getResultList();
         List<Books> booksList = manager.createQuery("SELECT b FROM Books b", Books.class).getResultList();
         printMenu();
         int mainMenuAnswer = getIntRange(1,5);
         if (mainMenuAnswer == 1) {
            addNewObjectMenu();
            int createObjectAnswer = getIntRange(1,5);
            if (createObjectAnswer == 1){
               //add the writing team
               createWritingTeam(writingGroupsList);
               
            } else if (createObjectAnswer == 2) {
               //add individual author
               createIndividualAuthors(individualAuthorsList);
               if (adHocTeamsList.size() > 0){
                  System.out.println("Wanna add this author to an ad hoc team?(y/n)");
                  boolean add = getYesNo();
                  if (add){
                     System.out.println("Please choose the ad hoc team you'd like to add this author to. (1 - "+adHocTeamsList.size()+")");
                     for (int i = 0; i < adHocTeamsList.size();i++){
                        System.out.println(i+ 1 + ") " +adHocTeamsList.get(i).getName());
                     }
                     int teamChoice = getIntRange(1,adHocTeamsList.size());
                     adHocTeamsList.get(teamChoice-1).addIndividualAuthors(individualAuthorsList.get(individualAuthorsList.size() - 1));
                     System.out.println("The author has been added to "+ adHocTeamsList.get(teamChoice-1).getName());
                  }
               }

               
            } else if (createObjectAnswer == 3) {
               //add ad hoc team
               createAdHocTeam(adHocTeamsList);

            } else if (createObjectAnswer == 4) {
               // add publisher
               createPublisher(publishersList);

            } else if (createObjectAnswer == 5) {
               // add book
               createBook(booksList,writingGroupsList,adHocTeamsList,individualAuthorsList,publishersList);
            }
         } else if (mainMenuAnswer == 2) {
            listInformationMenu();

            int listAnswer = getIntRange(1,3);
            //publisher == 1
            if (listAnswer == 1) {
               if (publishersList.size() == 0) {
                  System.out.println("There are currently no publishers in the database. Would you like to create " +
                          "one?(y/n)");
                  boolean choice = getYesNo();
                  if (choice) {
                     createPublisher(publishersList);
                  } else {
                     System.out.println("There are currently no publishers to display.");
                  }
               } if (publishersList.size() != 0) {
                  System.out.println("Which publisher would you like to see more information about? (1 - " + publishersList.size() + ") ");
                  for (int i = 0; i < publishersList.size(); i++) {
                     System.out.println(i + 1 + ") "+publishersList.get(i).getName());
                  }
                  int publisherChoice = getIntRange(1, publishersList.size());
                  System.out.println(publishersList.get(publisherChoice - 1));
               }

               //book == 2
            } else if (listAnswer == 2) {
               if (booksList.size() == 0){
                  System.out.println("There are currently no books in the database. Would you like to create " +
                          "one?(y/n)");
                  boolean choice = getYesNo();
                  if (choice){
                     createBook(booksList,writingGroupsList,adHocTeamsList,individualAuthorsList,publishersList);
                  } else {
                     System.out.println("There are currently no books to display.");
                  }
               } if (booksList.size() != 0) {
                  System.out.println("Which book would you like to see more information about? (1 - " + booksList.size() + ") ");
                  for (int i = 0; i < booksList.size(); i++){
                     System.out.println(i + 1 + ") "+booksList.get(i).getTitle());
                  }
                  int bookChoice = getIntRange(1, booksList.size());
                  System.out.println(publishersList.get(bookChoice-1));
               }
               // writing group == 3
            } else if (listAnswer == 3) {
               if (writingGroupsList.size() == 0){
                  System.out.println("There are currently no writing groups in the database. Would you like to " +
                          "create " +
                          "one?(y/n)");
                  boolean choice = getYesNo();
                  if (choice){
                     createWritingTeam(writingGroupsList);
                  } else {
                     System.out.println("There are currently no writing groups to display.");
                  }
               } if (writingGroupsList.size() != 0) {
                  System.out.println("Which writing group would you like to see more information about? (1 - " + writingGroupsList.size() + ") ");
                  for (int i = 0; i < writingGroupsList.size(); i++){
                     System.out.println(i + 1 + ") "+writingGroupsList.get(i).getName());
                  }
                  int writingGroupChoice = getIntRange(1, writingGroupsList.size());
                  System.out.println(writingGroupsList.get(writingGroupChoice-1));
               }
            }

         } else if (mainMenuAnswer == 3) {
            //book deletion
            System.out.println("So you've chosen to delete a book. Ok.");
            deleteBook(manager);
            //make sure book is in database

         } else if (mainMenuAnswer == 4) {
            //
            //TODO book update
            System.out.println("So you've chosen to update the book authoring. Ok.");
            updateBookAuthor(booksList,writingGroupsList,adHocTeamsList,individualAuthorsList,publishersList);




            //make sure book is in database
         } else if (mainMenuAnswer == 5) {
            //TODO list primary keys
            listPrimaryKeysMenu();
            int object_answer = getIntRange(1,6);
            listPrimaryKeys(object_answer);
         }
         System.out.println("Would you like to keep going? (y/n)");
         circuit = getYesNo();
         books_manager.createEntity(writingGroupsList);
         books_manager.createEntity(adHocTeamsList);
         books_manager.createEntity(publishersList);
         books_manager.createEntity(booksList);
         books_manager.createEntity(individualAuthorsList);
         tx.commit();
      } //end of while loop

      LOGGER.fine("End of Transaction");

   } // End of the main method

   /***
    *
    * This function updates the author's book. First, the books list must have a book already in there.
    * If that requirement is met, then the book can be updated.
    *
    * @param booksList
    * @param writingGroupsList
    * @param adHocTeamsList
    * @param individualAuthorsList
    * @param publishersList
    */
   public static void updateBookAuthor(List<Books> booksList, List<Writing_Group> writingGroupsList,
                                       List<Ad_Hoc_Team> adHocTeamsList, List<Individual_Author> individualAuthorsList, List<Publishers> publishersList) {
      if (booksList.size() == 0){
         System.out.println("Whoops. You have no books. Please create one.");
         createBook(booksList,writingGroupsList,adHocTeamsList,individualAuthorsList,publishersList);
      } else {

      }
   }

   /***
    *
    * This function deletes a book if there is already one in the database
    * It takes the entity manager as an argument and prompts the user for the book values.
    * It will check if the book exists. If the book does not exist, an error pops up saying a book is not found.
    *
    *
    * @param manager
    */
   public static void deleteBook(EntityManager manager) {
      System.out.println("Please input the title of the book (case sensitive).");
      String inputTitle = getString();
      System.out.println("Please input the publisher of the book (case sensitive).");
      String inputPublisher = getString();
      List<Books> booksList2 =
              manager.createQuery("SELECT b FROM Books b WHERE (b.title LIKE :inputTitle) and (b.publisher.name LIKE " +
                              ":inputPublisher)",
                      Books.class).setParameter(
                      "inputTitle",
                      inputTitle).setParameter("inputPublisher",inputPublisher).getResultList();
      if (booksList2.size() == 0){
         System.out.println("Book not found! Please try again.");
      } else {
         System.out.println("We found it!\nPrepare for deallocation.");
         manager.remove(booksList2.get(0));
         booksList2.remove(0);
      }
//      int bookIndex = -1;
//      for (int i = 0; i<booksList.size(); i++){
//         if ((booksList.get(i).getTitle().equalsIgnoreCase(inputTitle))){
//            bookIndex = i;
//         }
//      }
//      if (bookIndex == -1){
//         System.out.println("Book not found. Wanna try it again? (y/n)");
//         boolean restart = getYesNo();
//         if (restart){
//            deleteBook(booksList, manager);
//         }
//      } else {
//         System.out.println("Book found!");
//         System.out.println(booksList.get(bookIndex));
//         System.out.println("Please confirm if this is the right book please. (y/n)");
//         boolean deleteConfirm = getYesNo();
//         if (deleteConfirm){
//            System.out.println("Great! Preparing for deallocation.");
//
//         }
//      }

   }

   /***
    *
    * This function creates a new book entity.
    * First, the user is prompted for information regarding a new book.
    * The user must choose a writing group for that book.
    * The user must also choose other values that go along with the book.
    * Then if the book can work, if is committed.
    *
    * @param books
    * @param writingGroupsList
    * @param adHocTeamsList
    * @param individualAuthorsList
    * @param publishersList
    */
   private static void createBook(List<Books> books, List<Writing_Group> writingGroupsList,
                                  List<Ad_Hoc_Team> adHocTeamsList, List<Individual_Author> individualAuthorsList,
                                  List<Publishers> publishersList) {
      System.out.println("Please input the IBSN of the book.");
      String IBSN = getString();
      System.out.println("Please input the title of the book.");
      String title = getString();
      System.out.println("Please give me the published year of the book.");
      int yearPublished = getIntRange(1440,2022);
      System.out.println("What authoring entity would you like to use for this book?");
      System.out.println("Authoring Entities:\n1. Writing Group\n2. Individual Author\n3. Ad Hoc Team");
      int authoringChoice = getIntRange(1,3);
      //create authoring entity object
      Authoring_Entity tempAE = null;
      if (authoringChoice == 1){
         //Writing group
         while (writingGroupsList.size() == 0){
            System.out.println("There is no writing group. You're gonna have to create one.");
            createWritingTeam(writingGroupsList);
         }
         System.out.println("Please choose a writing group that authored this book. (1 - "+writingGroupsList.size()+
                 ")");
         for (int i = 0; i< writingGroupsList.size(); i++){
            System.out.println(i + 1 +") "+ writingGroupsList.get(i).getName());
         }
         int writingGroupChoice = getIntRange(1, writingGroupsList.size());
         tempAE = writingGroupsList.get(writingGroupChoice - 1);
      } else if (authoringChoice == 2) {
         while (individualAuthorsList.size() == 0){
            // Individual Author
            System.out.println("There is no individual author. You're gonna have to create one.");
            createIndividualAuthors(individualAuthorsList);
         }
         System.out.println("Please choose an individual author that authored this book.");
         for (int i = 0; i< individualAuthorsList.size(); i++){
            System.out.println(i + 1 +") "+ individualAuthorsList.get(i).getName());
         }
         int individualAuthorChoice = getIntRange(1, individualAuthorsList.size());
         tempAE = individualAuthorsList.get(individualAuthorChoice - 1);
      } else if (authoringChoice == 3){
         //add hoc team
         while (adHocTeamsList.size() == 0){
            System.out.println("There is no ad hoc team. You're gonna have to create one.");
            createAdHocTeam(adHocTeamsList);
         }
         System.out.println("Please choose an ad hoc team that authored this book.");
         for (int i = 0; i< adHocTeamsList.size(); i++){
            System.out.println(i + 1 +") "+ adHocTeamsList.get(i).getName());
         }
         int adHocTeamChoice = getIntRange(1, adHocTeamsList.size());
         tempAE = adHocTeamsList.get(adHocTeamChoice - 1);
      }
      while (publishersList.size() == 0){
         System.out.println("There is no a publisher. You're gonna have to create one.");
         createPublisher(publishersList);
      }
      System.out.println("Please choose a publisher for this book. (1 - "+publishersList.size()+
              ") ");
      for (int i = 0; i < publishersList.size(); i++){
         System.out.println(i + 1 +") "+ publishersList.get(i).getName());
      }
      int publisherChoice = getIntRange(1, publishersList.size());
      Publishers pb = publishersList.get(publisherChoice - 1);
      books.add(new Books(IBSN,title,yearPublished,tempAE,pb));
   }


   /***
    *
    * This function creates a publisher entity.
    *
    * @param publishersList
    */
   private static void createPublisher(List<Publishers> publishersList) {
      System.out.println("Please input the name of the publisher.");
      String name = getString();
      System.out.println("Please input the phone number of the publisher.");
      String phone = getString();
      System.out.println("Please input the email of the publisher.");
      String email = getString();
      publishersList.add(new Publishers(name,phone,email));
   }

   /***
    *
    * This function creates an ad hoc team entity.
    *
    * @param adHocTeams
    */
   private static void createAdHocTeam(List<Ad_Hoc_Team> adHocTeams) {
      System.out.println("Please input the name of the ad hoc team.");
      String name = getString();
      System.out.println("Please input the email of the ad hoc team.");
      String email = getString();
      adHocTeams.add(new Ad_Hoc_Team(name,email));
   }

   /***
    *
    * This function creates an Individual Author entity.
    *
    * @param individualAuthors
    */
   private static void createIndividualAuthors(List<Individual_Author> individualAuthors) {
      System.out.println("Please input the name the individual author.");
      String name = getString();
      System.out.println("Please input the email of the individual author.");
      String email = getString();
      individualAuthors.add(new Individual_Author(name,email));
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
         // Use the JPABooks entityManager instance variable to get our EntityManager.
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
      writing_group.add(new Writing_Group(name,email,headWriter,yearFormed));
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

   /***
    *
    * This function prints a main menu.
    *
    */
   public static void printMenu() {
      System.out.println("What would you like to do?");
      System.out.println("1. Add a new object");
      System.out.println("2. List all information about an object");
      System.out.println("3. Delete a Book");
      System.out.println("4. Update a Book");
      System.out.println("5. List the primary keys of certain entities");
   }

   /**
    * Displays list of objects to be added to database.
    */
   public static void addNewObjectMenu() {
      System.out.println("What object would you like to add?");
      System.out.println("Authoring Entities:\n1. Writing Group\n2. Individual Author\n3. Ad Hoc Team\nOther " +
              "Objects:\n4. " +
              "Add a " +
              "new publisher.\n5. Add a " +
              "new book.");
   }

   /***
    *
    * This function lists out an information menu.
    *
    */
   public static void listInformationMenu() {
      System.out.println("Which object would you like to list information about?");
      System.out.println("1. Publisher");
      System.out.println("2. Book");
      System.out.println("3. Writing Group");
   }

   /***
    *
    * This function lists primary keys for a given entity.
    *
    */
   public static void listPrimaryKeysMenu() {
      System.out.println("Which primary keys would you like to print?");
      System.out.println("1. Publishers");
      System.out.println("2. Books");
      System.out.println("3. Writing Group");
      System.out.println("4. Individual Author");
      System.out.println("5. Ad Hoc Team");
      System.out.println("6. Ad Hoc Team Member");
   }


   /***
    *
    * This function returns the integer range of two given values.
    *
    * @param low
    * @param high
    * @return
    */
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


   /**
    * Takes in a yes/no from the user.
    * @return true if yes, false if no.
    */
   public static boolean getYesNo(){
      boolean valid = false;
      while( !valid ) {
         String s = getString();
         if( s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y") ) {
            return true;
         } else if( s.equalsIgnoreCase("no") || s.equalsIgnoreCase("n") ) {
            return false;
         } else {
            System.out.println( "Invalid Input." );
         }
      }
      return false;
   }


   /***
    *
    * This function lists the primary keys for a given entity.
    *
    * @param object_answer
    */
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


