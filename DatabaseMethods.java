import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import org.w3c.dom.Node;

public class DatabaseMethods {
    DatabaseNode root; // top of tree
    ArrayList<Integer> listIDs = new ArrayList<>(); // stores all IDs
    Scanner scanner = new Scanner(System.in);

    public DatabaseMethods() {
        this.root = null;
    }// end constructor

    public DatabaseNode getRoot() {
        return root;
    }//end getRoot
    
    //updates given node's height
    void updateHeight(DatabaseNode n) {
        //node height = 1(because node is 1 above children) + height of largest subtree (left or right)
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }//end updateHeight

    //returns given node's height
    int height(DatabaseNode n) {
        return n == null ? -1 : n.height; //return -1 if null, else return node height (has to be at least 0-root)
    }//end height

    //returns balance factor of a given node
    int getBalance(DatabaseNode n) {
        return (n == null) ? 0 : height(n.right) - height(n.left); //return zero if null, else return height of right subtree - height left
    }//end get balance

    //rotates a node to the left: takes in node to rotate
    DatabaseNode rotateLeft(DatabaseNode y) { 
        DatabaseNode x = y.right; //x is y's original right child
        DatabaseNode z = x.left; //z is x's original left child (saving so isn't lost)
        x.left = y; //x's new left child is y
        y.right = z;//y's new right child is z (x's original left child)
        //update heights of y and x (even though z moves the height stays same)
        updateHeight(y);
        updateHeight(x);
        return x;
    }//end rotateLeft

    /*DatabaseNode rotateRight(DatabaseNode node) {
        DatabaseNode leftNode = node.left;
        DatabaseNode rightOfLeftNode = leftNode.right;
        //Performing the rotation
        leftNode.right = node;
        node.left = rightOfLeftNode;
        return leftNode;
   }*/

   //rotate given node to the right
   Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }//end rotateRight

   public int max (int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    //rebalances given node (will be passed in if node's balance
    //value is less than -1 or more than 1)
    DatabaseNode rebalance(DatabaseNode z) {
        updateHeight(z); //make sure you have correct value before rebalancing
        int balance = getBalance(z); //save the balance value of z
        if (balance > 1) {//right heavy - shift z left
            //Right-Right Rotation: 
            //The unbalanced node and its right child node are both right-heavy.
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);//single left rotation
            } else {//Right-Left Rotation
                //The unbalanced node is right heavy, and its right child node is left heavy.
                z.right = rotateRight(z.right);//right rotation on right child node
                z = rotateLeft(z);//left rotation on unbalanced node
            }//end inner if/else
        } else if (balance < -1) {//left heavy - shift z right
            //Left-Left Rotation:
            //The unbalanced node and its left child node are both left-heavy.
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);//single right rotation
            else {//Left-Right Rotation:
                z.left = rotateLeft(z.left);//rotate left child left
                z = rotateRight(z);//right rotation on unbalanced node
            }//end inner if/else
        }//end outer if/else
        return z;//return tree with z as root (now balanced)
    }//end rebalance  

    //iterative inorder traversal, returns array of nodes inorder
    public DatabaseNode[] inorderArray(DatabaseNode root) {
        //initialize array to store nodes (size of tree)
        DatabaseNode[] treeArray = new DatabaseNode[countRecords(root)];
        int index = 0;//start at beginning of array
        if (root == null) { //tree is empty
            return treeArray;//returns empty array
        }//end if statement
        
        //traverse inorder
        Stack<DatabaseNode> stack = new Stack<>(); //stack to hold nodes
        DatabaseNode current = root; //start at root

        while (current != null || !stack.isEmpty()) {
            //add left children to stack
            while (current != null) {
                stack.push(current);
                current = current.getLeftChild();
            }//end inner while loop

            current = stack.pop();
            treeArray[index] = current;
            index++;
            //add right children to stack
            current = current.getRightChild();
        }//end outer while loop
        return treeArray;
    }//end inorderArray

    //puts all contact nodes from the tree into the txt file
    public void writeToFile(){
        //put contact nodes from tree into array (inorder)
        DatabaseNode[] contactArray = inorderArray(root);
        try {
            FileWriter myWriter = new FileWriter("Phonebook.txt");
            //write all contacts into file
            for (int i = 0; i < contactArray.length; i++) {
                myWriter.write("ID #" + contactArray[i].getID() + "\n" + 
                "First Name: " + contactArray[i].getFirstName() + "\n" + 
                "Last Name: " + contactArray[i].getLastName() + "\n" + 
                "Address: " + contactArray[i].getaddress() + "\n" + 
                "City: " + contactArray[i].getCity() + "\n" + 
                "State: " + contactArray[i].getState() + "\n" + 
                "Zip Code: " + contactArray[i].getZip() + "\n" + 
                "Email: " + contactArray[i].getEmail() + "\n" + 
                "Phone #: " + contactArray[i].getPhNum() + "\n\n");
            }//end for loop
            myWriter.close();
            System.out.println("Succesfully written into the file.");
        } catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }//end try/catch
    }//end writeToFile method
    
    //reads contact nodes from the files and adds to tree
    public void addFromFile() {
        try {
            File phoneBookFile = new File("Phonebook.txt");
            Scanner reader = new Scanner(phoneBookFile);
            String beforeString = null;
            String line = null;

            //initialize variables to store contact info
            int idNum = 0;
            String firstName = null;
            String lastName = null;
            String address = null;
            String city = null;
            String state = null;
            int zip = 0;
            String email = null;
            String phNum = null;

            //read until end of file and get contact info
            while(reader.hasNextLine()) {
                //read ID number
                beforeString = "ID #";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    idNum = Integer.parseInt(data);
                }//end if statement
                
                //read first name
                beforeString = "First Name: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    firstName = data;
                }//end if statement

                //read last name
                beforeString = "Last Name: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    lastName = data;
                }//end if statement

                //read address
                beforeString = "Address: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    address = data;
                }//end if statement

                //read city
                beforeString = "City: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    city = data;
                }//end if statement

                //read state
                beforeString = "State: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    state = data;
                }//end if statement

                //read zip code
                beforeString = "Zip Code: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    zip = Integer.parseInt(data);
                }//end if statement

                //read email
                beforeString = "Email: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    email = data;
                }//end if statement

                //read phone number
                beforeString = "Phone #: ";
                line = reader.nextLine();
                if (line.contains(beforeString)) {
                    String data = line.substring(line.indexOf(beforeString) + beforeString.length());
                    phNum = data;
                }//end if statement

                //read empty line
                if (reader.hasNextLine()) {
                    line = reader.nextLine();
                }//end if statement
                System.out.println();

                //create node to add to tree
                DatabaseNode newNode = new DatabaseNode(idNum, firstName, lastName, address, city, state, zip, email, phNum);
                //add it to tree
                addNode(newNode);
            }//end while loop
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }//end try/catch
    } //end addFromFile method
    
    //create a node from user input
    public DatabaseNode createNode() {
        int idNum = generateID();
       
        System.out.println("Enter first name:");
        String firstName = scanner.next();
        System.out.println("Enter last name:");
        String lastName = scanner.next();
        System.out.println("Enter address:");
        scanner.nextLine();
        String address = scanner.nextLine();
        System.out.println("Enter city:");
        String city = scanner.nextLine();
        System.out.println("Enter state:");
        String state = scanner.nextLine();
        System.out.println("Enter zipcode:");
        int zip = scanner.nextInt();
        System.out.println("Enter email:");
        String email = scanner.next();
        System.out.println("Enter phone number:");
        String phNum = scanner.next();
        scanner.nextLine();

        return new DatabaseNode (idNum, firstName, lastName, address, city, state, zip, email, phNum);
    }// end createNode method
    
    //checks which node has greater sort value
    //returns greater node
    public DatabaseNode getGreaterValue(DatabaseNode newNode, DatabaseNode currentNode){
        //needs to check by char ASCII value, so convert to lowercase
        String newLast = newNode.getLastName().toLowerCase();
        String currentLast = currentNode.getLastName().toLowerCase();
        String newFirst = newNode.getFirstName().toLowerCase();
        String currentFirst = currentNode.getFirstName().toLowerCase();

        //check last names first
        //loop until reached last character of newnode last name
        //breaks when it find a difference in characters
        for (int i = 0; i < newLast.length(); i++){
            if (newLast.charAt(i) < currentLast.charAt(i)){
                return currentNode;
            } else if (newLast.charAt(i) > currentLast.charAt(i)) {
                return newNode;
            }//end if/else
        }//if you reach here, last names are same

        //if last names are same, check first names
        for (int i = 0; i < newFirst.length(); i++){
            if (newFirst.charAt(i) < currentFirst.charAt(i)){
                return currentNode;
            } else if (newFirst.charAt(i) > currentFirst.charAt(i)) {
                return newNode;
            }
        }//if you reach here, first names are same

        //if first names are same, sort by ID
        if (newNode.getID() < currentNode.getID()){
            return currentNode;
        } else {
            return newNode;
        }//end if/else
    }//end getGreaterValue

    //old addNode method that's sorted by idnumber
    public void addNode(DatabaseNode newNode) {
        // if tree is empty
        if (root == null) {
            root = newNode;
            System.out.println("Record added successfully.");
            System.out.println("Your ID number is: " + newNode.getID());
            return; // end method here if root == null
        }//end if
    
        // starting from the top
        DatabaseNode current = root;
        DatabaseNode parent = null;
    
        // while loop for placement if tree is not empty
        while (current != null) {
            parent = current;
            if (newNode.getID() < current.getID()) {
                current = current.getLeftChild(); // Move left
            } else if (newNode.getID() > current.getID()) {
                current = current.getRightChild(); // Move right
            } else {
                // Duplicate node found
                System.out.println("Node is a duplicate and cannot be placed.");
                return; // Exit the method if it's a duplicate
            }
        }
    
        // Insert the new node in the correct position
        if (newNode.getID() < parent.getID()) {
            parent.setLeftChild(newNode); // Set as left child
        } else {
            parent.setRightChild(newNode); // Set as right child
        }//end if/else
    
        // Success message
        System.out.println("Record added successfully.");
        System.out.println("Your ID number is: " + newNode.getID());
        rebalance(newNode);
    }//end addNode
    
    // Main Method: Combines node creation and insertion
    public void addNode() {
        DatabaseNode newNode = createNode(); // Get user input to create a new node
        addNode(newNode); // Insert the new node into the tree
    }//end addNode

    // DELETE method //
    //deletes by ID number
    public void deleteNode() {
        System.out.print("Enter ID number of record you want to delete: ");
        int idNum = scanner.nextInt();
        scanner.nextLine(); // Consume newline

          // Check if the node exists before attempting deletion
        if (search(idNum, root) == null) {
        System.out.println("Record with ID " + idNum + " not found.");
        return;
        }//end if

        root = delete(root, idNum);
        System.out.println("Record deleted successfully.");
    }//end deleteNode
 
    //Recursive delete helper method
    private DatabaseNode delete(DatabaseNode node, int idNum) {
       // Base case: if the tree is empty
        if (node == null) {
            return null;
        }

        // Traverse the tree to find the node to delete
        if (idNum < node.getID()) {
            node.left = delete(node.left, idNum);
        } else if (idNum > node.getID()) {
            node.right = delete(node.right, idNum);
        } else {
            // Found the node to delete
            // Case 1: No child (leaf node)
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: One child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Case 3: Two children
            DatabaseNode successor = findMin(node.right);
            node.setID(successor.getID()); // Replace the value
            node.right = delete(node.right, successor.getID()); // Remove successor
            //System.out.println("Record deleted successfully.");
        }
        return rebalance(node);
    }//end delete
    // end IDNUM DELETE method //

    // MODIFY method //
    public void modifyNode() {
        //get ID for node to modify
        System.out.print("Enter ID number of record you want to modify: ");
        int idNum = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        DatabaseNode node = search(idNum, root);

        //search the tree for the node with that id number, and assign it to current
        if (node == null) {//if no node exists with that id number
            System.out.println("Record not found.");
            return;
        }//end if statement

        //give choice of what to modify
        System.out.println("What data would you like to modify?");
        System.out.println("Choose one: 1) First name 2)Last name 3)Address 4)City 5)State 6)Zip code 7)email 8)Phone number");
        int userChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline    
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        scanner.nextLine(); // Consume newline

        switch (userChoice) {
            case 1: 
                node.setFirstName(newValue);
                break;
            case 2:
                node.setLastName(newValue);
                break;
            case 3:
                node.setaddress(newValue);
                break;
            case 4:
                node.setCity(newValue);
                break;
            case 5:
                node.setState(newValue);
                break;
            case 6:
                node.setZip(Integer.parseInt(newValue));
                break;
            case 7:
                node.setEmail(newValue);
                break;
            case 8:
                node.setPhNum(newValue);
                break;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.println("Record modified successfully.");      
    }  // end MODIFY method //

    // print phonebook in traversal order user chooses
    public void printPhoneBook() {
        while(true){
            try {
                System.out.println("Which order would you like to use?");
                System.out.println("1) Pre-order");
                System.out.println("2) Post-order");
                System.out.println("3) In-order");

                int choice = scanner.nextInt();
                switch(choice) {
                    case 1://preorder
                    printPreorder(root);
                    break;

                    case 2://postorder
                    printPostOrder();
                    break;

                    case 3://inorder
                    printInOrder(root);
                    break;

                    default://number not in range (exception catching)
                    System.out.println("Please enter an integer 1-3.");
                    scanner.nextLine();
                    continue;
                }//end switch/case
            } catch (Exception InputMismatchException) {
                System.out.println("Please enter an integer 1-3.");
                scanner.nextLine();
                continue;
            }//end try/catch
            break;
        }//end while loop
        System.out.println();
    }//end printPhoneBook

    // print PREORDER TRAVERSAL using Iteration//
    public void printPreorder(DatabaseNode root) {

        if (root == null) { // BST empty
            return;
        } // end if
        
        // creating a stack to hold tree values
        Stack<DatabaseNode> preorder = new Stack<>();
        // put the root in the stack
        preorder.push(root);

        while (!preorder.isEmpty()) {
            // remove current node from stack (so no repeats) and print it
            DatabaseNode current = preorder.pop();
            System.out.print(current.toString() + " ");
            
            // do right subtree first (bc stacks are read opposite way they are added to)
            if (current.getRightChild() != null) {
                preorder.push(current.getRightChild()); // adding right side values to stack
            } // end if statement

            // left subtree
            if (current.getLeftChild() != null) {
                preorder.push(current.getLeftChild());// adding left side values to stack
            } // end if statement
        } // end while loop
    }// end printPreorder method

// IN ORDER TRAVERSAL //
    public void printInOrder(DatabaseNode node) { // INORDER TRAVERSAL
        if (node == null)
            return;

        // left tree
        printInOrder(node.getLeftChild());

        //Print the current node's toString
        System.out.print(node.toString());

        // right tree
        printInOrder(node.getRightChild());
    }// end INORDER TRAVERSAL

// POST-ORDER TRAVERSAL //
    public void printPostOrder() {
        if (root == null) {
            System.out.println("Empty Database");
        } else {
            Stack<DatabaseNode> stack = new Stack<>();
            DatabaseNode current = root;
            boolean check = true;

            while (true) { // infinite loop until break
                // go to extreme left
                while (current != null && check) {
                    stack.push(current);
                    current = current.left;
                }
                if (stack.empty()) {
                    break;
                }
                // to avoid infinite loop
                if (current != stack.peek().right) {
                    current = stack.peek().right;
                    check = true;
                    continue;
                }
                // if not caught in any above special case
                current = stack.pop();
                System.out.print(current.toString() + " ");
                //System.out.print(current.getID() + " ");
                check = false;
            } // end outer while loop
        } // end if/else statement
    }// end PrintPostOrder method
    // end LOOKUP methods //

    // HELPER methods //
    private DatabaseNode findMin(DatabaseNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }//end findMin

    //searches for a node with a specific ID number: takes in id number and root
    private DatabaseNode search(int idNum, DatabaseNode node) {
        if (node == null || node.getID() == idNum) {
            return node;
        }
        if (idNum < node.getID()) {
            return search(idNum, node.left);
        } else {
            return search(idNum, node.right);
        }
    }//end search

    //generate random ID number
    public int generateID() {
        int idNum = 0;
        // generate random number
        idNum = (int) (Math.random() * 1000000000);
        // check that idNum is unique
        while (listIDs.contains(idNum)) {
            idNum = (int) (Math.random() * 1000000000);
        }
        return idNum;
    }// end generateID metho

    //prints count of contacts using recursion, takes in root
    public int countRecords(DatabaseNode node) {
        if (node == null) {
            return 0;//base case
        }
        // Traverse left subtree
        int left = countRecords(node.left);
        // Traverse right subtree
        int right = countRecords(node.right);
        int total = left + right;
        return total + 1;
    }//end countRecords method

    public void userMethods(Scanner scanner, DatabaseMethods database) {
        //user menu
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Modify");
            System.out.println("4. Print Phonebook");
            System.out.println("5. List number of records");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:  // ADD method
                    database.addNode();
                    break;
                case 2: // DELETE method
                    database.deleteNode();
                    break;
                case 3: // MODIFY method
                    database.modifyNode();
                    break;
                case 4: // print phonebook in order user chooses
                    database.printPhoneBook();
                    break;
                case 5:
                    System.out.println("Number of records: " 
                        + database.countRecords(database.getRoot()));
                    break;
                case 6:
                    database.writeToFile();//write to file
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            } // end Switch/Case
        } // end While loop 
    }//end userMethods
}//end class
