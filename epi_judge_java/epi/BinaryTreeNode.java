
package epi;

public class BinaryTreeNode<T> extends TreeLike<T, BinaryTreeNode<T>> {
  public T data;
  public T height;
  public BinaryTreeNode<T> left, right;

  public BinaryTreeNode() {}

  public BinaryTreeNode(T data) { this.data = data; }

  public BinaryTreeNode(T data, BinaryTreeNode<T> left,
                        BinaryTreeNode<T> right, T height) {
    this.data = data;
    this.left = left;
    this.right = right;
    this.height = height;
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public BinaryTreeNode<T> getLeft() {
    return left;
  }

  @Override
  public BinaryTreeNode<T> getRight() {
    return right;
  }
}
