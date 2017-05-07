import java.util.*;

/**
 * Created by OS on 13.10.2016.
 */
class AVL <Key extends Comparable<Key>, Value> {
    public class Node {
        private int h;
        private int balance;
        Key key;
        Value value;
        private Node left, right, father;
        public Node (Key key, Value value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
            this.h = 1;
            this.balance = 0;
        }

    }

    private StringBuilder inString;
    private Node root;

    private int height(Node x, Node y){
        if(x == null && y == null) return 0;
        else if(x == null) return y.h;
        else if(y == null) return x.h;
        else return Math.max(x.h, y.h);
    }

    private int balance(Node x, Node y){
        if(x == null && y == null) return 0;
        else if(x == null) return - y.h;
        else if(y == null) return x.h;
        else return x.h - y.h;
    }
    private Node add (Node node,Key key, Value value){
        if (node == null){
            Node newnode = new Node(key,value);
            return newnode;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult > 0){node.right = add(node.right, key, value); node.h = height(node.left, node.right) + 1;}
        else if(compareResult < 0){node.left = add(node.left, key, value); node.h = height(node.left, node.right) + 1;}
        else{
            node.value = value;
        }
        node.balance = balance(node.left, node.right);
        if(node.balance == -2){
            node = leftRotation(node);
        }else if(node.balance == 2){
            node = rightRotation(node);
        }
        return node;
    }
    private Node leftRotation(Node node) {
        if(node.right.right == null && node.right.left != null){
            node.right = rightRotation(node.right);
            node = leftRotation(node);
        }else if(node.right.left == null || node.right.left.h <= node.right.right.h){
            Node newnode = node.right;
            newnode.father = node.father;
            node.right = newnode.left;
            if(node.right != null)
                node.right.father = node;
            node.h = height(node.left, node.right)+1;
            node.father = newnode;
            node.balance = balance(node.left, node.right);
            newnode.left = node;
            node = newnode;
            node.balance = balance(node.left, node.right);
            node.h = height(node.left, node.right)+1;
        }else{
            node.right = rightRotation(node.right);
            node = leftRotation(node);
        }
        return node;
    }
    private Node rightRotation(Node node){
        if(node.left.right != null && node.left.left == null){
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        }else if(node.left.right == null || node.left.right.h <= node.left.left.h){
            Node newnode = node.left;
            newnode.father = node.father;
            node.left = newnode.right;
            if(node.left != null)
                node.left.father = node;
            node.h = height(node.left, node.right)+1;
            node.father = newnode;
            node.balance = balance(node.left, node.right);
            newnode.right = node;
            node = newnode;
            node.balance = balance(node.left, node.right);
            node.h = height(node.left, node.right)+1;
        }else{
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        }
        return node;
    }

    public void add(Key key, Value value) {
        root = add(root, key, value);
    }


    void infiks(Node node){
        if (node != null) {
            infiks(node.left);
            System.out.print(node.value + " "); //буде пояснено пізніше
            infiks(node.right);
        }
    }
    void infiks(){
        infiks(root);
    }
    void prefiksna(Node node){
        if (node != null) {
            System.out.print(node.value + " "); //буде пояснено пізніше
            prefiksna(node.left);
            prefiksna(node.right);
        }
    }
    void prefiksna(){
        prefiksna(root);
    }
    void postfiskna(Node node){
        if (node != null) {
            postfiskna(node.left);
            postfiskna(node.right);
            System.out.print(node.value + " "); //буде пояснено пізніше
        }
    }
    void postfiskna(){
        postfiskna(root);
    }

    private void print(Node node) {
        if (node !=null){
            print(node.left);
            if (node.left==null && node.right== null){
                System.out.print(node.value+" ");
            }
            print(node.right);
        }
    }
    public void print() {
        print(root);
    }
    public void show(){
      printLevelWise(root);
    }

    public void printLevelWise(Node root) {
        List<List<Node>> levels = traverseLevels(root);
        int m = levels.size();
        for (List<Node> level : levels) {
            for (Node node : level) {

                try{

                    System.out.print(node.value + " ");

                } catch (NullPointerException e){}
            }
            System.out.println();
        }
    }


    private List<List<Node>> traverseLevels(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Node>> levels = new LinkedList<>();

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            List<Node> level = new ArrayList<>(nodes.size());
            levels.add(level);

            for (Node node : new ArrayList<>(nodes)) {
                level.add(node);
                if (node.left != null) {
                    nodes.add(node.left);
                }

                if (node.right != null) {
                    nodes.add(node.right);
                }
                nodes.poll();
            }
        }
        return levels;
    }

}

public class avl_tree {
    public static void main(String[] args){
        AVL<Integer, String> tree = new AVL<>();
        tree.add(50,"50");

        tree.add(30,"30");
        tree.add(35,"35");
        tree.add(25,"25");
        tree.add(10,"10");
        tree.add(15,"15");
        tree.add(30,"30");
        tree.add(37,"37");
        tree.add(55,"55");
        tree.add(53,"53");
        tree.add(60,"60");
        tree.add(62,"62");



        tree.infiks();
        System.out.println();
        tree.postfiskna();
        System.out.println();
        tree.prefiksna();

    }
}