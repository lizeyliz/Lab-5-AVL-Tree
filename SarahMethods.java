public class SarahMethods {
    
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
                return rebalance(node.right);
            } else if (node.right == null) {
                return rebalance(node.left);
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
}
