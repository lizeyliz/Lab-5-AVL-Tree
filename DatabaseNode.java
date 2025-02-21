//object class
public class DatabaseNode {
    
    //instance variables
    private int idNum; //9 digit
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zip;
    private String email;
    private String phNum;

    //pointing to left and right
    DatabaseNode left;
    DatabaseNode right;
    
    //constructor
    public DatabaseNode(int idNum, String firstName, String lastName, String address, String city, String state, int zip, String email, String phNum){
        this.idNum = idNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address; //address
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.phNum = phNum;
        
        //pointing to left and right
        this.left = null;
        this.right = null;
    }//end constructor

    //getters and setters for private data
    public int getID() {
        return idNum;
    }//end getID

    public void setID(int newID) {
        this.idNum = newID;
    }//end setID

    public String getFirstName() {
        return firstName;
    }//end getFirstName

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }//end setFirstName

    public String getLastName() {
        return lastName;
    }//end getLastName

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }//end setLastName

    public String getaddress() {
        return address;
    }//end getaddress

    public void setaddress(String newaddress) {
        this.address = newaddress;
    }//end setaddress

    public String getCity() {
        return city;
    }//end getCity

    public void setCity(String newCity) {
        this.city = newCity;
    }//end setCity

    public String getState() {
        return state;
    }//end getState

    public void setState(String newState) {
        this.state = newState;
    }//end setState

    public int getZip(){
        return zip;
    }//end getZip

    public void setZip(int newZip) {
        this.zip = newZip;
    }//end setZip

    public String getEmail(){
        return email;
    }//end getEmail

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }//end setEmail

    public String getPhNum() {
        return phNum;
    }//end getPhNUm

    public void setPhNum(String newPhNum) {
        this.phNum = newPhNum;
    }//end setPhNum

    public DatabaseNode getLeftChild() {
        return left;
    }

    public void setLeftChild(DatabaseNode left) {
        this.left = left;
    }

    public DatabaseNode getRightChild() {
        return right;
    }

    public void setRightChild(DatabaseNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "\nID #" + idNum + "\n Name: " + firstName + " " + lastName
                + "\n Address: " + address + " " + city + ", " + state + " " + zip
                + "\n Email: " + email + "\t Phone Number: " + phNum + "\n";
    }//end toString
}//end class DatabaseNode
