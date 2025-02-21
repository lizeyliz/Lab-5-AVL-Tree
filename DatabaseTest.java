import java.util.Scanner;
public class DatabaseTest {
    public static void main(String[] args) {
        //initialize the database
        DatabaseMethods database = new DatabaseMethods();
        //initialize the scanner
        Scanner scanner = new Scanner(System.in);

        //add nodes from file
        database.addFromFile();

        database.userMethods(scanner, database);
        
    } // end main
}  // end Test Class file