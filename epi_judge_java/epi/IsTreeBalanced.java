package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeBalanced {

  @EpiTest(testDataFile = "is_tree_balanced.tsv")

  // Example: Not a balanced tree. A.right.height = 1, A.left.height = 3, difference = 2 > 1
  //         A
  //        / \
  //       B   C
  //      / \
  //     D   E
  //    /
  //   F
  // Example: A balanced tree. A.right.height = 2, A.left.height = 3, difference = 1
  //         A
  //        / \
  //       B   C
  //      / \   \
  //     D   E   G
  //    /
  //   F
  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {

    //return isSubtreeBalanced(tree);
    return checkBalanced(tree).balanced;
  }

  private static class BalanceStatusWithHeight{
    public boolean balanced;
    public int height;

    public BalanceStatusWithHeight(boolean balanced, int height){
      this.balanced = balanced;
      this.height = height;
    }
  }

  // Time complexity: O(n)
  // Space complexity: O(h) where h is the height of the tree.
  private static BalanceStatusWithHeight checkBalanced(BinaryTreeNode<Integer> tree){
    if(tree == null){
      return new BalanceStatusWithHeight(/*balanced=*/true, /*height=*/-1);
    }

    BalanceStatusWithHeight leftResult = checkBalanced(tree.left);
    if(!leftResult.balanced){
      return leftResult;
    }

    BalanceStatusWithHeight rightResult = checkBalanced(tree.right);
    if(!rightResult.balanced){
      return  rightResult;
    }

    boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1;
    int height = Math.max(leftResult.height, rightResult.height) + 1;
    return new BalanceStatusWithHeight(isBalanced, height);
  }

  // Time complexity: O(n)
  // Space complexity: O(n) due to the extra height field in the nodes.
  private static boolean isSubtreeBalanced(BinaryTreeNode<Integer> tree) {

    // Empty tree
    if(tree == null)
      return true;

    // Leaf node
    if(tree.left == null && tree.right == null){
      tree.height=0;
      return true;
    }

    boolean IsLeftBalanced = isSubtreeBalanced(tree.left);
    boolean IsRightBalanced = isSubtreeBalanced(tree.right);

    if(tree.left == null){
      tree.height = tree.right.height + 1;
      if(tree.height > 1)
        return false;
    }
    else if(tree.right == null){
      tree.height = tree.left.height + 1;
      if(tree.height > 1)
        return false;
    }
    else{
      tree.height = Math.max(tree.left.height, tree.right.height) + 1;
      if(Math.abs(tree.left.height-tree.right.height) >1)
        return false;
    }

    return IsLeftBalanced && IsRightBalanced;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
