import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
*This class is a Red Black Tree
**/

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    // You can add Red-Black Tree specific methods and logic here.
    
    // Constructor
    public RedBlackTree() {
        super(); 
    }


    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;
        public RBTNode(T data) { super(data); }
        public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
        public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
        public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
    }

    protected void enforceRBTreePropertiesAfterInsert(Node<T> redNode) {

    }


    /**
     * Inserts a new data value into the tree. This tree will not hold null references, nor
     * duplicate data values.
     *
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");
        RBTNode<T> newNode = new RBTNode<>(data);
        boolean valueInserted = super.insertHelper(newNode); // Call the BinarySearchTree's
        // insertHelper method.
        enforceRBTreePropertiesAfterInsert(newNode); // Enforce Red-Black Tree properties.
        root = (RBTNode) root;// Ensure the root is black.
        ((RBTNode<T>) root).blackHeight = 1;
        return valueInserted;
    }

    /**
     * This method tests if node is black when added to empty tree
     */
    @Test
    public void testInsertBlackNodeIntoEmptyTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10); // Black root

        RBTNode<Integer> rootNode = (RBTNode<Integer>) tree.root;

        Assertions.assertEquals(1, rootNode.blackHeight);
    }

    /**
     * Test case to verify insertion of a red node into a red parent node.
     */
    @Test
    public void testInsertRedNodeIntoRedParentNode() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10); // Black root
        tree.insert(7); //red
        tree.insert(11); //red
        tree.insert(1); //red

        RBTNode<Integer> rootNode = (RBTNode<Integer>) tree.root;
        RBTNode<Integer> node7 = (RBTNode<Integer>) tree.root.down[0];
        RBTNode<Integer> node1 = (RBTNode<Integer>) node7.down[0];

        Assertions.assertTrue(rootNode.blackHeight != 0 && node7.blackHeight != 1 && node1.blackHeight != 1, "Red node child violation with Red Parent");
    }

    /**
     * Test case to verify insertion when the parent node is red and the aunt/uncle
     * node is black.
     */
    @Test
    public void testInsertWithBlackUncle() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10); // Black root
        tree.insert(7); //red
        tree.insert(11); //Black Uncle
        tree.insert(1); //red

        RBTNode<Integer> rootNode = (RBTNode<Integer>) tree.root;
        Assertions.assertEquals(1, rootNode.blackHeight);

        RBTNode<Integer> leftChildNode = rootNode.getDownLeft();
        Assertions.assertEquals(0, leftChildNode.blackHeight);

        RBTNode<Integer> rightChildNode = rootNode.getDownRight();
        Assertions.assertEquals(0, rightChildNode.blackHeight);

        RBTNode<Integer> rightsRightChildNode = rootNode.getDownRight();
        Assertions.assertEquals(1, rightsRightChildNode.blackHeight);

    }

}
