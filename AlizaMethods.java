public class AlizaMethods {
    static int height = -1;

    // Helper function to find the height
    // of a given node in the binary tree
    static int findHeightUtil(DatabaseNode root, int id){
        // Base Case: tree is empty
        if (root == null) {
            return -1;
        }//end if

        // Store the maximum height of
        // the left and right subtree
        int leftHeight = findHeightUtil(root.left, id);

        int rightHeight = findHeightUtil(root.right, id);

        // Update height of the current node
        int ans = Math.max(leftHeight, rightHeight) + 1;

        // If current node is the required node
        if (root.getID() == id)
            height = ans;

        return ans;
    }//end findHeightUtil

    // Function to find the height of
    // a given node in a Binary Tree
    static int findHeight(DatabaseNode root, int id){
        // Stores height of the Tree
        findHeightUtil(root, id);

        // Return the height
        return height;
    }//end findHeight
}//end AlizaMethods
