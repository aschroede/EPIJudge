package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class TreePostorder {

  private static class NodeAndState {
    public BinaryTreeNode<Integer> node;
    public Boolean subtreesTraversed;

    public NodeAndState(BinaryTreeNode<Integer> node,
                        Boolean subtreesTraversed) {
      this.node = node;
      this.subtreesTraversed = subtreesTraversed;
    }
  }

  @EpiTest(testDataFile = "tree_postorder.tsv")
  public static List<Integer> postorderTraversal(BinaryTreeNode<Integer> tree) {
    List<Integer> postorderTraversalResult = new ArrayList<>();

    if(tree == null)
      return postorderTraversalResult;

    postorderTraversalResult.addAll(postorderTraversal(tree.left));
    postorderTraversalResult.addAll(postorderTraversal(tree.right));
    postorderTraversalResult.add(tree.data);


    return postorderTraversalResult;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreePostorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
