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
/**
 * JPA Books Project
 * April 6th, 2022
 * Diego O. Garcia, Alexander Weber, Alan Robertson
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;
import java.util.logging.Level;
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
//      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      LOGGER.setLevel(Level.OFF);
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of JPA Books and store our new EntityManager as an instance variable.
      JPA_Books books_manager = new JPA_Books(manager);
//      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

       //Validates user input
      boolean circuit = true;
      while(circuit == true) {
         LOGGER.setLevel(Level.OFF);
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
               createIndividualAuthors(individualAuthorsList,adHocTeamsList);

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
            deleteBook(manager,booksList);
            //make sure book is in database

         } else if (mainMenuAnswer == 4) {
            //book update
            System.out.println("So you've chosen to update the book authoring. Very well.");
            updateBookAuthor(booksList,writingGroupsList,adHocTeamsList,individualAuthorsList,publishersList);

            //make sure book is in database
         } else if (mainMenuAnswer == 5) {
            primaryKeysMenu();
            int pkChoice = getIntRange(1,5);
            displayPrimaryKeys(pkChoice,manager);
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

//      LOGGER.fine("End of Transaction");

   } // End of the main method

   /**
    * Function that updates the book objects authoring entity.
    * @param booksList - List of books currently in database.
    * @param writingGroupsList - The writing groups currently in the database.
    * @param adHocTeamsList - The ad hoc teams currently in the database.
    * @param individualAuthorsList - The individual authors currently in the database.
    * @param publishersList - The publishers currently in the database.
    */
   public static void updateBookAuthor(List<Books> booksList, List<Writing_Group> writingGroupsList,
                                       List<Ad_Hoc_Team> adHocTeamsList, List<Individual_Author> individualAuthorsList, List<Publishers> publishersList) {
      if (booksList.size() == 0){
         System.out.println("Whoops. You have no books. Please create one.");
         createBook(booksList,writingGroupsList,adHocTeamsList,individualAuthorsList,publishersList);
      } else {
         System.out.println("Please input which book you'd like to update.");
         for (int i = 0; i < booksList.size(); i++){
            System.out.println(i+1+") "+booksList.get(i).getTitle() + " - Authoring Entity: " + booksList.get(i).getAuthoringEntity());
         }
         int bookChoice = getIntRange(1, booksList.size());
         System.out.println("Now, tell me what authoring entity you'd like to update this book to.\n1. Writing Group\n2. Individual Author\n3. Ad Hoc Team");
         int authoringChoice = getIntRange(1,3);
         //create authoring entity object
         Authoring_Entity tempAE = null;
         if (authoringChoice == 1){
            //Writing group
            while (writingGroupsList.size() == 0){
               System.out.println("There is no writing group. You're gonna have to create one.");
               createWritingTeam(writingGroupsList);
            }
            System.out.println("Please choose a writing group for this book. (1 - "+writingGroupsList.size()+
                    ")");
            for (int i = 0; i< writingGroupsList.size(); i++){
               System.out.println(i + 1 +") "+ writingGroupsList.get(i).getName());
            }
            int writingGroupChoice = getIntRange(1, writingGroupsList.size());
            tempAE = writingGroupsList.get(writingGroupChoice - 1);
            booksList.get(bookChoice - 1).setAuthoring_entity(tempAE);
         } else if (authoringChoice == 2) {
            while (individualAuthorsList.size() == 0){
               // Individual Author
               System.out.println("There is no individual author. You're gonna have to create one.");
               createIndividualAuthors(individualAuthorsList, adHocTeamsList);
            }
            System.out.println("Please choose an individual author for this book.");
            for (int i = 0; i< individualAuthorsList.size(); i++){
               System.out.println(i + 1 +") "+ individualAuthorsList.get(i).getName());
            }
            int individualAuthorChoice = getIntRange(1, individualAuthorsList.size());
            tempAE = individualAuthorsList.get(individualAuthorChoice - 1);
            booksList.get(bookChoice - 1).setAuthoring_entity(tempAE);
         } else if (authoringChoice == 3){
            //add hoc team
            while (adHocTeamsList.size() == 0){
               System.out.println("There is no ad hoc team. You're gonna have to create one.");
               createAdHocTeam(adHocTeamsList);
            }
            System.out.println("Please choose an ad hoc team for this book.");
            for (int i = 0; i< adHocTeamsList.size(); i++){
               System.out.println(i + 1 +") "+ adHocTeamsList.get(i).getName());
            }
            int adHocTeamChoice = getIntRange(1, adHocTeamsList.size());
            tempAE = adHocTeamsList.get(adHocTeamChoice - 1);
            booksList.get(bookChoice - 1).setAuthoring_entity(tempAE);
         }
      }
   }

   /**
    * Function that deletes a book from the database.
    * @param manager - Entity manager that is used to remove a book from the database.
    * @param booksList - Referenced to original queried list in main to also remove the book from so it's not
    *                  persisted at the end of main.
    */
   public static void deleteBook(EntityManager manager, List<Books> booksList) {
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
         booksList.remove(booksList2.get(0));
      }
   }

   /**
    * Function that creates a book to be persisted into the database.
    * @param books - Reference to the books list that will be persisted into the database.
    * @param writingGroupsList - Reference to the writing groups list that will be persisted into the database.
    * @param adHocTeamsList - Reference to the ad hoc teams list that will be persisted into the database.
    * @param individualAuthorsList - Reference to the individual authors list that will be persisted into the database.
    * @param publishersList - Reference to the publishers list that will be persisted into the database.
    */
   private static void createBook(List<Books> books, List<Writing_Group> writingGroupsList,
                                  List<Ad_Hoc_Team> adHocTeamsList, List<Individual_Author> individualAuthorsList,
                                  List<Publishers> publishersList) {
      System.out.println("Please input the IBSN of the book.");
      String IBSN = getString();
      for (int i = 0; i<books.size(); i++){
         if (Objects.equals(books.get(i).getIBSN(), IBSN)){
            System.out.println("There is a primary key violation for book. Try again.");
            createBook(books, writingGroupsList, adHocTeamsList, individualAuthorsList, publishersList);
         }
      }
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
            createIndividualAuthors(individualAuthorsList, adHocTeamsList);
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

   /**
    * Function that creates a publisher to be added to the database.
    * @param publishersList - Reference to the publisher list to be persisted into the database.
    */
   private static void createPublisher(List<Publishers> publishersList) {
      System.out.println("Please input the name of the publisher.");
      String name = getString();
      for (int i = 0; i<publishersList.size(); i++){
         if (Objects.equals(publishersList.get(i).getName(), name)){
            System.out.println("There is a primary key violation for publisher. Try again.");
            createPublisher(publishersList);
         }
      }
      System.out.println("Please input the phone number of the publisher.");
      String phone = getString();
      System.out.println("Please input the email of the publisher.");
      String email = getString();
      publishersList.add(new Publishers(name,phone,email));
   }

   /**
    * Function that creates an ad hoc team to be added to the database.
    * @param adHocTeams - Reference to the ad hoc team to be persisted into the database.
    */
   private static void createAdHocTeam(List<Ad_Hoc_Team> adHocTeams) {
      System.out.println("Please input the name of the ad hoc team.");
      String name = getString();
      System.out.println("Please input the email of the ad hoc team.");
      String email = getString();
      for (int i = 0; i<adHocTeams.size(); i++){
         if (Objects.equals(adHocTeams.get(i).getEmail(), email)){
            System.out.println("There is a primary key violation for ad hoc team email. Try again.");
            createAdHocTeam(adHocTeams);
         }
      }
      adHocTeams.add(new Ad_Hoc_Team(name,email));
   }

   /**
    * Function that creates an individual author to be added into the database.
    * @param individualAuthors - Reference to the individual author list to be persisted in main after object is added.
    * @param adHocTeamsList - Reference to the ad hoc teams list to prompt the user to add an author into the ad hoc
    *                       team if he/she so desires.
    */
   public static void createIndividualAuthors(List<Individual_Author> individualAuthors, List<Ad_Hoc_Team> adHocTeamsList) {
      System.out.println("Please input the name the individual author.");
      String name = getString();
      System.out.println("Please input the email of the individual author.");
      String email = getString();
      for (int i = 0; i<individualAuthors.size(); i++){
         if (Objects.equals(individualAuthors.get(i).getEmail(), email)){
            System.out.println("There is a primary key violation for individual authors email. Try again.");
            createIndividualAuthors(individualAuthors, adHocTeamsList);
         }
      }
      individualAuthors.add(new Individual_Author(name,email));
      if (adHocTeamsList.size() != 0){
         System.out.println("Would you like to add this author to an ad hoc team? (y/n)");
         boolean choice = getYesNo();
         if (choice){
            System.out.println("Very well. Please choose an ad hoc team.");
            for (int i = 0; i< adHocTeamsList.size(); i++){
               System.out.println(i + 1 +") "+ adHocTeamsList.get(i).getName());
            }
            int adHocTeamChoice = getIntRange(1, adHocTeamsList.size());
            adHocTeamsList.get(adHocTeamChoice-1).addIndividualAuthors(individualAuthors.get(individualAuthors.size()-1));
            System.out.println("The author has been added to "+ adHocTeamsList.get(adHocTeamChoice-1).getName());
         }

      }

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
    * This function creates a writing group to be added into the database.
    * @param writing_group reference to the writing group list initialized in main to be persisted.
    */
   public static void createWritingTeam(List<Writing_Group> writing_group){

      System.out.println("Please input the name of the writing group.");
      String name = getString();
      System.out.println("Please input the email of the writing group.");
      String email = getString();
      for (int i = 0; i<writing_group.size(); i++){
         if (Objects.equals(writing_group.get(i).getEmail(), email)){
            System.out.println("There is a primary key violation for the writing group email. Try again.");
            createWritingTeam(writing_group);
         }
      }
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

   /**
    * Prints the general menu of choices that is available to the user.
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
    * Displays list of objects to be added to the database.
    */
   public static void addNewObjectMenu() {
      System.out.println("What object would you like to add?");
      System.out.println("Authoring Entities:\n1. Writing Group\n2. Individual Author\n3. Ad Hoc Team\nOther " +
              "Objects:\n4. " +
              "Add a " +
              "new publisher.\n5. Add a " +
              "new book.");
   }

   /**
    * Menu that displays the objects that a user can list information about.
    */
   public static void listInformationMenu() {
      System.out.println("Which object would you like to list information about?");
      System.out.println("1. Publisher");
      System.out.println("2. Book");
      System.out.println("3. Writing Group");
   }

   /**
    * Method that displays the objects that can be chosen to view the primary keys.
    */
   public static void primaryKeysMenu() {
      System.out.println("Which primary keys would you like to print?");
      System.out.println("Authoring Entities:\n1. Writing Group(s).\n2. Individual Author(s).\n3. Ad Hoc Team(s)." +
              "\nOther Objects:\n4. Publisher(s).\n5. Book(s).");

   }

   /**
    * Checks if the inputted value is an integer and
    * within the specified range (ex: 1-10)
    * @param low lower bound of the range.
    * @param high upper bound of the range.
    * @return the valid input.
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

   /**
    * Function that takes in the choice of object to display the PK and related fields.
    * @param pkChoice - Choice of object that user would like to display PK related rows.
    * @param manager - Entity manager to query the database.
    */
   public static void displayPrimaryKeys(int pkChoice, EntityManager manager) {

      //query for all necessary groups
      List<Writing_Group> writingGroupsList =
              manager.createQuery("SELECT w FROM Writing_Group w ORDER BY w.name ASC", Writing_Group.class).getResultList();
      List<Individual_Author> individualAuthorsList = manager.createQuery("SELECT ia FROM Individual_Author ia ORDER " +
                      "BY ia.name ASC",
              Individual_Author.class).getResultList();
      List<Ad_Hoc_Team> adHocTeamsList =
              manager.createQuery("SELECT aT FROM Ad_Hoc_Team aT ORDER BY aT.name ASC", Ad_Hoc_Team.class).getResultList();
      List<Publishers> publishersList =
              manager.createQuery("SELECT p FROM Publishers p ORDER BY p.name ASC",Publishers.class).getResultList();
      List<Books> booksList = manager.createQuery("SELECT b FROM Books b ORDER BY b.title", Books.class).getResultList();


      if (pkChoice == 1) {
         //list primary keys of writing groups
         if (writingGroupsList.size() == 0){
            System.out.println("There are no writing group LOL. But the primary keys are associated with the email of" +
                    " the writing group.");
         } else {
            for (int i = 0; i< writingGroupsList.size(); i++){
               System.out.println(i+1+") "+writingGroupsList.get(i).getEmail() + "\nAuthoring Entity type: Writing " +
                       "Group.");
            }
         }

      } else if (pkChoice == 2) {
         //list primary keys of individual authors
         if (individualAuthorsList.size() == 0){
            System.out.println("There are no individual author. But the primary keys are associated with " +
                    "the email of the individual author.");
         } else {
            for (int i = 0; i< individualAuthorsList.size(); i++){
               System.out.println(i+1+") "+individualAuthorsList.get(i).getEmail() + "\nAuthoring Entity type: " +
                       "Individual Author.");
            }
         }
      } else if (pkChoice == 3) {
         //list primary keys of ad hoc teams
         if (adHocTeamsList.size() == 0){
            System.out.println("There are no ad hoc teams LOL. But the primary keys are associated with the email of" +
                    " the ad hoc teams.");
         } else {
            for (int i = 0; i< adHocTeamsList.size(); i++){
               System.out.println(i+1+") "+adHocTeamsList.get(i).getEmail() + "\nAuthoring Entity type: Ad Hoc Team.");
            }
         }

      } else if (pkChoice == 4) {
         //list primary keys of publishers
         if (publishersList.size() == 0){
            System.out.println("There are no publishers LOL. But the primary keys are associated with the name of" +
                    " the publishers.");
         } else {
            for (int i = 0; i< publishersList.size(); i++){
               System.out.println(i+1+") "+publishersList.get(i).getName());
            }
         }
      } else if (pkChoice == 5) {
         //list primary keys of books
         if (booksList.size() == 0){
            System.out.println("There are no books LOL. But the primary keys are associated with the IBSN of" +
                    " the books.");
         } else {
            for (int i = 0; i< booksList.size(); i++){
               System.out.println(i+1+") IBSN: "+booksList.get(i).getIBSN() + "\nTitle: "+ booksList.get(i).getTitle());
            }
         }
      }//end method
   }
}// End of JPABooks class


