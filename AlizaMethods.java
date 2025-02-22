public class AlizaMethods {
    DatabaseMethods methods = new DatabaseMethods();

    //rotates a node to the left: takes in node to rotate
    DatabaseNode rotateLeft(DatabaseNode y) { 
        DatabaseNode x = y.right; //x is y's original right child
        DatabaseNode z = x.left; //z is x's original left child (saving so isn't lost)
        x.left = y; //x's new left child is y
        y.right = z;//y's new right child is z (x's original left child)
        //update heights of y and x (even though z moves the height stays same)
        methods.updateHeight(y);
        methods.updateHeight(x);
        return x;
    }//end rotateLeft
}//end AlizaMethods
