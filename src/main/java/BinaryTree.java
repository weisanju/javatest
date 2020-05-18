public class BinaryTree {
    public static void main(String[] args) {

        final int[] values = { 1, 3, 4, 5, 2, 8, 6, 7, 9, 0 };
        // TODO:
        Node treeNode = null;
        for (int value : values) {
             treeNode = createBinaryTree(treeNode, value);
        }
        inOrderTransval(treeNode);
    }

    public static Node createBinaryTree(Node node, int value) {
        // TODO:
        if(node == null){
            return new Node(value,null,null);
        }
        if(node.getValue() <= value){
            node.setRight(createBinaryTree(node.getRight(),value));
        }else{
            node.setLeft(createBinaryTree(node.getLeft(),value));
        }
        return  node;
    }

    public static void inOrderTransval(Node node) {
        // TODO:

        if(node.getLeft()!=null){
            inOrderTransval(node.getLeft());
        }
        System.out.println(node.getValue());

        if(node.getRight()!=null){
            inOrderTransval(node.getRight());
        }
    }


}
class Node {

    // 节点值
    private int value;

    // 左节点
    private Node left;

    // 右节点
    private Node right;


    // TODO:

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
