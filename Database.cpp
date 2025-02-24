#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <random>
#include <list>
#include <stack>
#include <vector>
using namespace std;//so you don't have to write std all the time
class DatabaseNode {
    private:
    //instance variables
    int idNum; //9 digit
    std::string firstName;
    std::string lastName;
    std::string address;
    std::string city;
    std::string state;
    int zip;
    std::string email;
    std::string phNum;

    public:
    int height; //node's height in tree, will be updated
    //pointing to left and right
    DatabaseNode *left, *right;
    //constructor
    DatabaseNode(int idNum, std::string firstName, std::string lastName, std::string address, 
    std::string city, std::string state, int zip, std::string email, std::string phNum){
        this->idNum = idNum;
        this->firstName = firstName;
        this->lastName = lastName;
        this->address = address;
        this->city = city;
        this->state = state;
        this->zip = zip;
        this->email = email;
        this->phNum = phNum;
        this->height = 0; //initial height is 0
        //pointing to left and right
        this-> left = this->right = NULL;
    }//end constructor

    //getters and setters
    int getIdNum(){
        return idNum;
    }
    void setIdNum(int idNum){
        this->idNum = idNum;
    }
    std::string getFirstName(){
        return firstName;
    }
    void setFirstName(std::string firstName){
        this->firstName = firstName;
    }
    std::string getLastName(){
        return lastName;
    }
    void setLastName(std::string lastName){
        this->lastName = lastName;
    }
    std::string getAddress(){
        return address;
    }
    void setAddress(std::string address){
        this->address = address;
    }
    std::string getCity(){
        return city;
    }
    void setCity(std::string city){
        this->city = city;
    }
    std::string getState(){
        return state;
    }
    void setState(std::string state){
        this->state = state;
    }
    int getZip(){
        return zip;
    }
    void setZip(int zip){
        this->zip = zip;
    }
    std::string getEmail(){
        return email;
    }
    void setEmail(std::string email){
        this->email = email;
    }
    std::string getPhNum(){
        return phNum;
    }
    void setPhNum(std::string phNum){
        this->phNum = phNum;
    }
    //to string (may change later)
    std::string toString(){
        return "ID#" + std::to_string(idNum) + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + 
        "\nAddress: " + address + "\nCity: " + city + "\nState: " + state + "\n Zip Code:" 
        + std::to_string(zip) + "\nEmail: " + email + "\nPhone #: " + phNum +"\n\n";
    }//end toString
};//end class DatabaseNode

class DatabaseMethods {
public:
    std::list<int> idNums;
    DatabaseNode* root = nullptr;

    //updates given node's height
    void updateHeight(DatabaseNode* n) {
        //node height = 1(because node is 1 above children) + height of largest subtree (left or right)
        if (n != nullptr) {
            n->height = 1 + std::max(height(n->left), height(n->right));
        }   
    }//end updateHeight

    //returns given node's height
    int height(DatabaseNode* n) {
        return n == nullptr ? -1 : n->height; //return -1 if null, else return node height (has to be at least 0-root)
    }//end height

    //returns balance factor of a given node
    int getBalance(DatabaseNode* n) {
        return (n == nullptr) ? 0 : height(n->right) - height(n->left); //return zero if null, else return height of right subtree - height left
    }//end get balance

    //rotates a node to the left: takes in node to rotate
    DatabaseNode* rotateLeft(DatabaseNode* y) { 
        DatabaseNode* x = y->right; //x is y's original right child
        DatabaseNode* z = x->left; //z is x's original left child (saving so isn't lost)
        x->left = y; //x's new left child is y
        y->right = z;//y's new right child is z (x's original left child)
        //update heights of y and x (even though z moves the height stays same)
        updateHeight(y);
        updateHeight(x);
        return x;
    }//end rotateLeft

    DatabaseNode* rotateRight(DatabaseNode* y) {
        DatabaseNode* x = y->left;
        DatabaseNode* z = x->right;
        //Performing the rotation
        x->right = y;
        y->left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
   }

   int max (int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    //rebalance method-rebalances the given node
    DatabaseNode* rebalance(DatabaseNode* z) {
        updateHeight(z);
        int balance = getBalance(z); //balance factor of z
        if (balance > 1) { //right heavy-shift z left
            //right right rotation: both node and right child are right-heavy
            if (height(z->right->right) > height(z->right->left)) { //right subtree is taller
                z= rotateLeft(z); //single left  rotation 
            } else { //right-left rotation
                z->right = rotateRight(z->right); //right rotation on right child
                z = rotateLeft(z); //left rotation on unbalanced node
            }
        } else if (balance < -1) { //left heavy - shift z right
            //left left rotation: both node and left child are left-heavy
            if (height(z->left->left) > height(z->left->right)) { //left subtree is taller
                z = rotateRight(z); //single right rotation
            } else { //left-right rotation
                z->left = rotateLeft(z->left); //left rotation on left child
                z = rotateRight(z); //right rotation on unbalanced node
            }

        }
        return z;
        
    }

    // In-order traversal: takes in root
    void printInOrder(DatabaseNode* node) {
        if (node == nullptr) {
            return; // Tree is empty
        }//end if statement
        printInOrder(node->left);
        std::cout << node->toString();
        printInOrder(node->right);
    }//end printInOrder

    // print PREORDER TRAVERSAL using Iteration: takes in root
    void printPreOrder(DatabaseNode* node) {
        if (root == nullptr){//BST empty
            return;
        }//end if

        // creating a stack to hold tree values
        stack<DatabaseNode*> preorder;
        // put the root in the stack
        preorder.push(root);

        //run until stack is empty
        while(!preorder.empty()){
            // remove current node from stack (so no repeats) and print it
            DatabaseNode* current = preorder.top();//return the top node
            preorder.pop();//delete the top node
            cout << current->toString() + " ";

            // do right subtree first (bc stacks are read opposite way they are added to)
            if (current->right != nullptr) {
                preorder.push(current->right); // adding right side values to stack
            } // end if statement

            // left subtree
            if (current->left != nullptr) {
                preorder.push(current->left);// adding left side values to stack
            } // end if statement
        }//end while loop
    }//end printPreOrder

    //Post order traversal: takes in root
    void printPostOrder(){
        if (root == nullptr) {
            cout << "Empty Database";
        } else {
            stack<DatabaseNode*> stack;
            DatabaseNode* current = root;
            bool check = true;

            while (true) { // infinite loop until break
                // go to extreme left
                while (current != nullptr && check) {
                    stack.push(current);
                    current = current->left;
                }
                if (stack.empty()) {
                    break;
                }
                // to avoid infinite loop
                if (current != stack.top()->right) {
                    current = stack.top()->right;
                    check = true;
                    continue;
                }
                // if not caught in any above special case
                current = stack.top();//return top of stack
                stack.pop();//delete node on top of stack
                cout << current->toString() + " ";
                check = false;
            } // end outer while loop
        } // end if/else statement
    }//end printPostOrder

    // Add a node to BST based on ID number
    DatabaseNode* addNode(DatabaseNode* node, DatabaseNode* newNode) {
        if (node == nullptr) { //base case: empty tree
            std::cout << "Record added successfully.\n";
            std::cout << "Your ID number is: " << newNode->getIdNum() << "\n";
            return newNode;
        } else if(node->getIdNum() > newNode->getIdNum()) { //current > new
            node->left = addNode(node->left, newNode); //add left recursively
        } else if(node->getIdNum() < newNode->getIdNum()) { //current < new
            node->right = addNode(node->right, newNode); //add right recursively
        } else { //duplicate
            std::cout << "Node is a duplicate and cannot be placed.\n";
        }

        return rebalance(node); //rebalance tree

        /*DatabaseNode* current = root;
        DatabaseNode* parent = nullptr;

        while (current != nullptr) {
            parent = current;
            if (newNode->getIdNum() < current->getIdNum()) {
                current = current->left;  // Move left
            } else if (newNode->getIdNum() > current->getIdNum()) {
                current = current->right; // Move right
            } else { // Duplicate node
                std::cout << "Node is a duplicate and cannot be placed.\n";
                return;
            }//end if/else
        }//end while loop

        if (newNode->getIdNum() < parent->getIdNum()) {
            parent->left = newNode;
        } else {
            parent->right = newNode;
        }//end if/else
        std::cout << "Record added successfully.\n";
        std::cout << "Your ID number is: " << newNode->getIdNum() << "\n";
        rebalance(newNode);*/
    }//end addNode

    // Main Method: Combines node creation and insertion
    void addNode() {
        DatabaseNode* newNode = createNode(); // Get user input to create a new node
        root = addNode(root, newNode); // Insert the new node into the tree
    }//end addNode
    
    void userMethods(){
        while(true) {
            int choice;
            std::cout << "Menu\n";
            std::cout << "1. Add\n";
            std::cout << "2. Delete\n";
            std::cout << "3. Modify\n";
            std::cout << "4. Lookup\n";
            std::cout << "5. List number of records\n";
            std::cout << "6. Print out records in order\n";
            std::cout << "7. Exit\n";
            std::cout << "Enter your choice: ";
            std::cin >> choice;

            switch (choice)
            {
            case 1: // ADD method
                addNode();
                break;
            
            case 2: // DELETE method
                deleteNode();
                break;
            case 3: // MODIFY method
                modifyNode();
                break;
            case 4: // LOOK UP method
                lookupNode();
                break;
            case 5: //list number of records
                //to wrok on
                std::cout <<"Number of Records: " << countRecords(root) << std::endl;
                break;
            case 6: //PRINT OUT records
                printPhoneBook();
                break;
            case 7:
                writeToFile();
                std::cout << "Exiting program...\n";
                exit(0);
            
            default:
                std::cout << "Invalid choice. Please try again.\n";    
                   
            }//end switch/case
        } //end while loop
    } //end userMethods

    int generateIdNum() {
        // Generate random ID number
        std::random_device rd;
        std::mt19937 gen(rd());  
        std::uniform_int_distribution<> dist(100000000, 999999999);
        int ID = dist(gen);
        //check if the id is in the list

        auto it = std::find(idNums.begin(), idNums.end(), ID);

        if (it == idNums.end()) {
            // ID is already in the list
            std::uniform_int_distribution<> dist(100000000, 999999999);
            ID = dist(gen);
        }//end if statement

        return ID;
    }//end generateIdNum
    
    void readFromFile(){ 
       std::fstream ContactsList;
       std::string line;
       std::string substring;
       int ID;
       std::string firstName;
       std::string lastName; 
       std::string address;
       std::string city;
       std::string state;
       int zip;
       std::string email;
       std::string phNum = "null";
       std::string data;
    
       ContactsList.open("Phonebook.txt");
       
       //read from txt file
       if(ContactsList.is_open()) {
            while (ContactsList.good()) {
                while(getline(ContactsList, line)) {
                    phNum = "null";
        
                    if (line.find("ID #") != std::string::npos) {
                        data = line.substr(4);
                        ID = std::stoi(data);
                        //std::cout << ID << std::endl;
                    }
                    if (line.find("First Name: ") != std::string::npos) {
                        data = line.substr(12);
                        firstName = data;
                        //std::cout << firstName << std::endl;
                    }
                    if (line.find("Last Name: ") != std::string::npos) {
                        data = line.substr(11);
                        lastName = data;
                        //std::cout << lastName << std::endl;
                    }
                    if(line.find("Address: ") != std::string::npos) {
                        data = line.substr(9);
                        address = data;
                        //std::cout << address << std::endl;
                    }
                    if (line.find("City: ") != std::string::npos) {
                        data = line.substr(6);
                        city = data;
                    // std::cout << city << std::endl;
                    }
                    if (line.find("State: ") != std::string::npos) {
                        data = line.substr(7);
                        state = data;
                        //std::cout << state << std::endl;
                    }
                    if (line.find("Zip Code: ") != std::string::npos) {
                        data = line.substr(10);
                        zip = std::stoi(data);
                        //std::cout << zip << std::endl;
                    }
                    if (line.find("Email: ") != std::string::npos) {
                        data = line.substr(7);
                        email = data;
                        //std::cout << email << std::endl;
                    }
                    if (line.find("Phone #: ") != std::string::npos) {
                        data = line.substr(9);
                        phNum = data;
                        //std::cout << phNum << std::endl;
                    }
                    if (phNum != "null") { //so that a node will only be created once all the values are saved
                        idNums.push_front(ID); //add IDs to list
                        DatabaseNode* contact = new DatabaseNode(ID, firstName, lastName, address, city, state, zip, email, phNum);
                        //std::cout << contact->toString();
                        root = addNode(root, contact);  
                    }//end if statements                
                }//end while loop
            }//end while loop
        } else {
            std::cout <<"unable to open file";
        }//end if/else
        ContactsList.close();
    }//end readFromFile
    
    //creates a node and returns it
    DatabaseNode* createNode() {
        int ID = generateIdNum(); //generate id num
        std::string firstName;
        std::string lastName;
        std::string address;
        std::string city;
        std::string state;
        int zip = 0;
        std::string email;
        std::string phNum;
        
        std::cin.ignore(); // clear the input buffer
        std::cout << "Enter first name: \n";
        getline(std::cin, firstName);
        std::cout << "Enter last name: \n";
        getline(std::cin, lastName);
        std::cout << "Enter address: \n";
        getline(std::cin, address);
        std::cout << "Enter city: \n";
        getline(std::cin, city);
        std::cout << "Enter state: \n";
        getline(std::cin, state);
        std::cout << "Enter zipcode: \n";
        std::string zipStr;
        getline(std::cin, zipStr);
        zip = std::stoi(zipStr);
        std::cout << "Enter email: \n";
        getline(std::cin, email);
        std::cout << "Enter phone number: \n";
        getline(std::cin, phNum);
    
        idNums.push_front(ID);
        DatabaseNode* contact = new DatabaseNode(ID, firstName, lastName, address, city, state, zip, email, phNum);
        return contact;
    }//end createNode

    //prints count of contacts using recursion, takes in root
    static int countRecords(DatabaseNode* node) {
        if (node == nullptr){
            return 0;
        }//base case
        // Traverse left subtree
        int left = countRecords(node->left);
        // Traverse right subtree
        int right = countRecords(node->right);
        int total = left + right;
        return total + 1;
    }//end countRecords

    //iterative inorder traversal, returns vector of nodes inorder
    vector<DatabaseNode*> inOrderVector(){//like inOrderArray method, but using vector
        vector<DatabaseNode*> treeVector;//initialize
        if (root == nullptr) { //tree is empty
            return treeVector;//returns empty vector
        }//end if statement
        //fill vector: traverse in-order
        stack<DatabaseNode*> stack;
        DatabaseNode* current = root;
        while (current != nullptr || !stack.empty()) {
            //add left children to stack
            while (current != nullptr) {
                stack.push(current);
                current = current->left;
            }//end inner while loop
            //stacks work differently in C++
            current = stack.top();//return top element
            stack.pop();//delete top element
            treeVector.push_back(current);//putting at end of vector
            //add right children to stack
            current = current->right;
        }//end outer while loop
        return treeVector;
    }//end inOrderVector

    //puts all contact nodes from the tree into the txt file
    void writeToFile(){
        // Create and open text file
        ofstream MyFile("Phonebook.txt");
        //put contact nodes from tree into vector (inorder)
        vector<DatabaseNode*> contactVector = inOrderVector();//vector to iterate through
        //write to file
        for (size_t i = 0; i < contactVector.size(); i++){//go through vector elements
            //write a contact into the file
            MyFile << "ID #" + to_string(contactVector[i]->getIdNum()) + "\n" + 
            "First Name: " + contactVector[i]->getFirstName() + "\n" + 
            "Last Name: " + contactVector[i]->getLastName() + "\n" + 
            "Address: " + contactVector[i]->getAddress() + "\n" + 
            "City: " + contactVector[i]->getCity() + "\n" + 
            "State: " + contactVector[i]->getState() + "\n" + 
            "Zip Code: " + to_string(contactVector[i]->getZip()) + "\n" + 
            "Email: " + contactVector[i]->getEmail() + "\n" + 
            "Phone #: " + contactVector[i]->getPhNum() + "\n\n";
        }//end for loop
        MyFile.close();//close file
    }//end writeToFile

    //searches for a node with a specific ID number: takes in id number and root
    DatabaseNode* search(int idNum, DatabaseNode* node){
        if (node == nullptr || node->getIdNum() == idNum) {
            return node;
        }
        if (idNum < node->getIdNum()) {
            return search(idNum, node->left);
        } else {
            return search(idNum, node->right);
        }
    }//end search

    //finds node with minimum id num
    DatabaseNode* findMin(DatabaseNode* node){
        while (node->left != nullptr) {
            node = node->left;
        }//end while loop
        return node;
    }//end finMin

    // print phonebook in traversal order user chooses
    void printPhoneBook() {
        //setting up user input
        int choice;
        while(true){
            try {
                cout << "Which order would you like to use?\n";
                cout << "1) Pre-order\n";
                cout << "2) Post-order\n";
                cout << "3) In-order\n";
                std::cin >> choice;

                switch(choice) {
                    case 1://preorder
                    printPreOrder(root);
                    break;

                    case 2://postorder
                    printPostOrder();
                    break;

                    case 3://inorder
                    printInOrder(root);
                    break;

                    default://number not in range (exception catching)
                    cout << "Please enter an integer 1-3.\n";
                    break;
                }//end switch/case
            } catch (const std::invalid_argument& e) {
                cout << "Please enter an integer 1-3.";
                std::string input;
                std::getline(std::cin, input);
                choice = std::stoi(input);
                continue;
            }//end try/catch
            break;
        }//end while loop
        cout << "\n";
    }//end printPhoneBook

    void lookupNode(){
        std::cout << "Enter ID number of the record you want to lookup: ";
        int idNum;
        std::cin >> idNum;
        DatabaseNode* node = search(idNum, root); //sets it to the node with that id number
        if (node == nullptr) {
            std::cout << "Record not found.\n";
            return;
        } else {
            std::cout << node->toString();
        }//end if/else
    }//end lookupNode

    //modify a ndoe
    void modifyNode(){
        //get ID for node to modify
        std::cout << "Enter ID number of the record you want to modify: ";
        int idNum;
        std::cin >> idNum;
        DatabaseNode* node = search(idNum, root);

        //search the tree for the node with that id number, and assign it to current
        if (node == nullptr) {
            std::cout << "Record with ID " << idNum << "not found.\n";
            return;
        }//end if statement

        //give choice of what to modify
        std::cout << "What data would you like to modify?\n";
        std:: cout << "Choose one: 1) First Name 2) Last Name 3) Address 4) City 5) State 6) Zip Code 7) Email 8) Phone Number\n";
        int userChoice;
        std::cin >> userChoice;
        std::cout << "Enter new value: ";
        std::string newValue;
        std::cin.ignore(); // clear the input buffer
        getline(std::cin, newValue); //obtain new value

        int temp;
        switch (userChoice)
        {
        case 1:
            node->setFirstName(newValue);
            break;
        case 2:
            node->setLastName(newValue);
            break;
        case 3:
            node->setAddress(newValue);
            break;
        case 4:
            node->setCity(newValue);
            break;
        case 5:
            node->setState(newValue);
            break;
        case 6: //have to convert to int
            temp = std::stoi(newValue);
            node->setZip(temp);
            break;
        case 7:
            node->setEmail(newValue);
            break;
        case 8:
            node->setPhNum(newValue);
            break;
        default:
            std::cout << "Invalid choice.\n";
            break;
        }
        std::cout << "Record modified successfully.\n";
    } //end modifyNode

    //delete method
    void deleteNode(){
        std::cout <<"Enter ID number of the record you want to delete: ";
        int idNum;
        std::cin >> idNum;

        //check if node exitsts before attempting to delete
        if (search(idNum, root) == nullptr) {
            std::cout << "Record with ID " << idNum << "not found.\n";
            return;
        }//end if statement

        root = deleteNode(root, idNum);
        std::cout << "Record deleted successfully.\n";
    }//end deleteNode

    //recursive delete helper method
    DatabaseNode* deleteNode(DatabaseNode* root, int idNum) {
        //base case: if tree is empty
        if (root == nullptr) {
            return nullptr;
        }//end if statement

        //if key to be searched is in a subtree
        if (root->getIdNum() > idNum) {
            root -> left = deleteNode(root -> left, idNum);
        } else if (root->getIdNum() < idNum) {
            root -> right = deleteNode(root -> right, idNum);
        } else {//if the root matches with the given idNUm
            //Case 1: no child or only right child
            if(root->left == nullptr && root->right == nullptr) {
                DatabaseNode* temp = root->right;
                delete root;
                return rebalance(temp);
            }//end if statement

            //Case 2: only left child
            if(root->right == nullptr) {
                DatabaseNode* temp = root->left;
                delete root;
                return rebalance(temp);
            }//end if statement

            //when both children are present
            DatabaseNode* successor = findMin(root->right);
            root->setIdNum(successor->getIdNum()); //replace the value
            root->right = deleteNode(root->right, successor->getIdNum()); //remove succesor
        }//end if/else
        return rebalance(root);
    }//end deleteNode
};//end class DatabaseMethods

//main method
int main() {
    DatabaseMethods db;//initializing instance of DatabaseMethods class
    db.readFromFile(); //add nodes from text file
    db.userMethods(); //user methods
    db.writeToFile();//once user exits program, write new tree to txt file
    return 0;
}//end main method
