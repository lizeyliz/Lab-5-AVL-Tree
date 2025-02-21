public class ErnestMethods {
    public int max (int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }
    
    public int getBalance(DatabaseNode node) {
        if(node == null) {
            return 0;
        } else {
            int balance = findHeight(node.left) - findHeight(node.right);
            //If the balance is negative, the right subtree is heavier
            //If the balance is positive, the left subtree is heavier
            return balance;
        }
    }    
}
