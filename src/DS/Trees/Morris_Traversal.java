package DS.Trees;

public class Morris_Traversal {
    // O(n) time
    // O(1) space

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) {
            val = v;
        }
    }

    static void morrisInorderTraversal(TreeNode curr) {
        while (curr != null) {
            if (curr.left == null) {
                System.out.printf(curr.val + " ");
                curr = curr.right;
            }
            else {
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    pred.right = curr;
                    curr = curr.left;
                }
                else {
                    pred.right = null;
                    System.out.printf(curr.val + " ");
                    curr = curr.right;
                }
            }
        }
    }

    static void morrisPreorderTraversal(TreeNode curr) {
        while (curr != null) {
            if (curr.left == null) {
                System.out.printf(curr.val + " ");
                curr = curr.right;
            }
            else {
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    pred.right = curr;
                    System.out.printf(curr.val + " ");
                    curr = curr.left;
                }
                else {
                    pred.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.left.left = new TreeNode(-2);
        root.left.left.right = new TreeNode(2);
        root.left.left.right.left = new TreeNode(-1);
        root.left.right = new TreeNode(6);
        root.left.right.right = new TreeNode(8);
        root.right = new TreeNode(30);
        root.right.right = new TreeNode(40);
        morrisInorderTraversal(root);
        System.out.println("");
        morrisPreorderTraversal(root);
    }
}
