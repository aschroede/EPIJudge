package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class TreePreorder {

  private static class NodeAndState {
    public BinaryTreeNode<Integer> node;
    public Boolean nodeProcessed;

    public NodeAndState(BinaryTreeNode<Integer> node, Boolean nodeProcessed) {
      this.node = node;
      this.nodeProcessed = nodeProcessed;
    }
  }

  @EpiTest(testDataFile = "tree_preorder.tsv")
  public static List<Integer> preorderTraversal(BinaryTreeNode<Integer> tree) {

    List<Integer> preorderTraversalResult = new ArrayList<>();

    if(tree == null)
      return preorderTraversalResult;

    preorderTraversalResult.add(tree.data);
    preorderTraversalResult.addAll(preorderTraversal(tree.left));
    preorderTraversalResult.addAll(preorderTraversal(tree.right));


    return preorderTraversalResult;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreePreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
