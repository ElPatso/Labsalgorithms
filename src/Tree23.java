import java.util.*;

class TwoThreeTre <K extends Comparable<K>, V> {

    private Node root;
    private int nodeCount = 0;

    public class Node
    {

        protected KeyValuePair keyvalues1;
        protected KeyValuePair keyvalues2;
        protected Node parent, left, middle, right;
        public Node(KeyValuePair keyValues1)
        {
            this.keyvalues1 = keyValues1;
        }

        public Node(KeyValuePair keyValues1, KeyValuePair keyValues2)
        {
            this.keyvalues1 = keyValues1;
            this.keyvalues2 = keyValues2;
        }

    }
    public class FourNode extends Node
    {
        protected KeyValuePair keyvalues3;
        protected Node middle2;

        public FourNode (KeyValuePair keyValues1, KeyValuePair keyValues2, KeyValuePair keyValues3)
        {
            super(keyValues1, keyValues2);
            this.keyvalues3 = keyValues3;
        }
    }
    List<K> keys = new ArrayList<>();
    LinkedHashMap<K, V> book = new LinkedHashMap<>();
    public void add(K key, V value){
        book.put(key,value);
        keys.add(key);
        put(key,value);

    }
    public  void delete(K key){
        root = null;
        book.remove(key);
        keys.remove(key);
        for (K i:keys){
            V value = book.get(i);

            put(i,value);
        }

    }


    public void put(K key, V value)
    {


        if (value == null)
            throw new IllegalArgumentException("Null values not supported!");

             if (root == null)
        {
            root = new Node(new KeyValuePair(key, value));
            nodeCount = 1;
            return;
        }


        Node foundNode = getNode(key, root, false);


        if (foundNode.keyvalues2 == null)
        {
            if (key.compareTo(foundNode.keyvalues1.key) < 0)
            {

                foundNode.keyvalues2 = foundNode.keyvalues1;
                foundNode.keyvalues1 = new KeyValuePair(key, value);
            }
            else if (key.equals(foundNode.keyvalues1.key))
                foundNode.keyvalues1.value = value;
            else if (key.compareTo(foundNode.keyvalues1.key) > 0)
                foundNode.keyvalues2 = new KeyValuePair(key, value);

        }
        else
        {

            if (foundNode.keyvalues1.key.equals(key))
            {
                foundNode.keyvalues1.value = value;
                return;
            }
            if (foundNode.keyvalues2.key.equals(key))
            {
                foundNode.keyvalues2.value = value;
                return;
            }

            FourNode tempFourNode = null;


            if (key.compareTo(foundNode.keyvalues1.key) <= -1)
                tempFourNode = new FourNode(new KeyValuePair(key, value), foundNode.keyvalues1, foundNode.keyvalues2);


            else if (key.compareTo(foundNode.keyvalues1.key) >= 1 && key.compareTo(foundNode.keyvalues2.key) <= -1)
                tempFourNode = new FourNode(foundNode.keyvalues1, new KeyValuePair(key, value), foundNode.keyvalues2);


            else if (key.compareTo(foundNode.keyvalues2.key) >= 1)
                tempFourNode = new FourNode(foundNode.keyvalues1, foundNode.keyvalues2, new KeyValuePair(key, value));


            put4NodeInTree(foundNode, tempFourNode);
        }
    }
    public Iterable<KeyValuePair> keys()
    {

        LinkedList<KeyValuePair> results = new LinkedList<KeyValuePair>();

        traverseTree(root, results);

        return results;
    }

      public Iterable<KeyValuePair> keys(K lo, K hi)
    {
        List<KeyValuePair> treeKeyValues = (LinkedList<KeyValuePair>)keys();
        List<KeyValuePair> result = new LinkedList<KeyValuePair>();

        int indexOfLow = indexOfKeyInList(lo, treeKeyValues);
        int indexOfHigh = indexOfKeyInList(hi, treeKeyValues)+1;

        if (indexOfLow == -1 || indexOfHigh == -1)
            throw new IllegalArgumentException("Key lo and/or hi not found in the tree.");

        result.addAll(treeKeyValues.subList(indexOfLow, indexOfHigh));

        return result;
    }


    private Node getNode(K key, Node startNode, boolean returnNullOnMissing)
    {

        if (returnNullOnMissing)
        {
            if(startNode == null)
                return null;
        }
        else
        {

            if (startNode.left == null)
                return startNode;
        }

          if (key.equals(startNode.keyvalues1.key))
            return startNode;

            if (key.compareTo(startNode.keyvalues1.key) < 0)
            return getNode(key, startNode.left, returnNullOnMissing);

        if (startNode.keyvalues2 == null)
        {

            return getNode(key, startNode.middle, returnNullOnMissing);
        }
        else
        {

            if (key.equals(startNode.keyvalues2.key))
                return startNode;

                if (key.compareTo(startNode.keyvalues1.key) > 0 && key.compareTo(startNode.keyvalues2.key) < 0)
                return getNode(key, startNode.middle, returnNullOnMissing);
            else
                return getNode(key, startNode.right, returnNullOnMissing);
        }
    }


    private void put4NodeInTree(Node currentNode, FourNode tmpFourNode)
    {
        Node splitResult = Convert4to2(tmpFourNode);
        nodeCount++;
        if (currentNode == root)
        {
            root = splitResult;
            nodeCount++;
        }
        else
        {
            Node parent = currentNode.parent;
            FourNode mergeResult = MergeNodes (parent, splitResult);
            if (mergeResult != null)
                put4NodeInTree(parent, mergeResult);
        }
    }

    private Node Convert4to2(FourNode inNode)
    {
        Node newRoot = new Node (inNode.keyvalues2);

        Node newLeft = new Node (inNode.keyvalues1);
        Node newRight = new Node (inNode.keyvalues3);

        newRoot.left = newLeft;
        newRoot.middle = newRight;

        newLeft.parent = newRoot;
        newRight.parent = newRoot;

        newLeft.left = inNode.left;

        if (newLeft.left != null)
            newLeft.left.parent = newLeft;

        newLeft.middle = inNode.middle;

        if (newLeft.middle != null)
            newLeft.middle.parent = newLeft;

        newRight.left = inNode.middle2;
        if (newRight.left != null)
            newRight.left.parent = newRight;

        newRight.middle = inNode.right;

        if(newRight.middle != null)
            newRight.middle.parent = newRight;

        return newRoot;
    }


    private void traverseTree (Node curNode, List<KeyValuePair> treeItems)
    {

        if (curNode.left == null)
        {

            treeItems.add(curNode.keyvalues1);
            if (curNode.keyvalues2 != null)
                treeItems.add(curNode.keyvalues2);
        }
        else if (curNode.keyvalues2 == null)
        {
            traverseTree(curNode.left, treeItems);
            treeItems.add(curNode.keyvalues1);
            traverseTree(curNode.middle, treeItems);
        }
        else
        {
            traverseTree(curNode.left, treeItems);
            treeItems.add(curNode.keyvalues1);
            traverseTree(curNode.middle, treeItems);
            treeItems.add(curNode.keyvalues2);
            traverseTree(curNode.right, treeItems);
        }
    }

      private FourNode MergeNodes(Node treeNode, Node separateNode)
    {
               FourNode newFourNode = null;


        if (treeNode.keyvalues2 == null)
        {
            if(separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) <= -1)
            {
                treeNode.keyvalues2 = treeNode.keyvalues1;
                treeNode.keyvalues1 = separateNode.keyvalues1;

                treeNode.right = treeNode.middle;
                treeNode.middle = separateNode.middle;
                treeNode.left = separateNode.left;
            }
            else if (separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) >= 1)
            {

                treeNode.keyvalues2 = separateNode.keyvalues1;


                treeNode.right = separateNode.middle;
                treeNode.middle = separateNode.left;
            }


            separateNode.middle.parent = treeNode;
            separateNode.left.parent = treeNode;
        }
        else
        {

            if (separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) <= -1)
            {
                newFourNode = new FourNode(separateNode.keyvalues1, treeNode.keyvalues1, treeNode.keyvalues2);

                newFourNode.left = separateNode.left;
                newFourNode.middle = separateNode.middle;
                newFourNode.middle2 = treeNode.middle;
                newFourNode.right = treeNode.right;
            }
            else if (separateNode.keyvalues1.key.compareTo(treeNode.keyvalues1.key) > 0 && separateNode.keyvalues1.key.compareTo(treeNode.keyvalues2.key) < 0)
            {
                newFourNode = new FourNode(treeNode.keyvalues1, separateNode.keyvalues1, treeNode.keyvalues2);

                newFourNode.left = treeNode.left;
                newFourNode.middle = separateNode.left;
                newFourNode.middle2 = separateNode.middle;
                newFourNode.right = treeNode.right;
            }
            else
            {
                newFourNode = new FourNode(treeNode.keyvalues1, treeNode.keyvalues2, separateNode.keyvalues1);

                newFourNode.left = treeNode.left;
                newFourNode.middle = treeNode.middle;
                newFourNode.middle2 = separateNode.left;
                newFourNode.right = separateNode.middle;
            }

            newFourNode.left.parent = newFourNode;
            newFourNode.middle.parent = newFourNode;
            newFourNode.middle2.parent = newFourNode;
            newFourNode.right.parent = newFourNode;
        }


        return newFourNode;
    }


    private int indexOfKeyInList(K key, List<KeyValuePair> listToSearch)
    {
        for (int i = 0; i < listToSearch.size(); i++)
        {
            KeyValuePair element = listToSearch.get(i);

            if (element !=null && element.equals(new KeyValuePair (key, null)))
                return i;
        }
        return -1;
    }

    public class KeyValuePair {

        public K key;
        public V value;

        public KeyValuePair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public boolean equals(Object o)
        {
            KeyValuePair obj = (KeyValuePair) o;
            return this.key.equals(obj.key);
        }


    }
     public void find(int key){
            find(root,key);
     }

     private void find(Node node, Integer key) {
         String s = "Not found";
         if (node !=null){
             try{
                 if(node.keyvalues1.key == key) System.out.println(node.keyvalues1.value);
                 if(node.keyvalues2.key == key) System.out.println(node.keyvalues2.value);

             }
             catch (NullPointerException e){

             }
             find(node.left,key);
             find(node.middle,key);
             find(node.right,key);
         }

     }


     void show(){
        printLevelWise(root);


     }
    public void printLevelWise(Node root) {


        List<List<Node>> levels = traverseLevels(root);
        int m=levels.size();
        for (List<Node> level : levels) {
            for (Node node : level) {
                try{

                  for (int j=0; j<m+1 ;j++){
                      System.out.print("\t");
                      m--;
                  }

                System.out.print(node.keyvalues1.value + " ");
                System.out.print(node.keyvalues2.value + " ");
                } catch (NullPointerException e){}
            }
            System.out.println();
        }


    }


     private List<List<Node>> traverseLevels(Node root) {

         List<List<Node>> levels = new LinkedList<>();

         Queue<Node> nodes = new LinkedList<>();
         nodes.add(root);

         while (!nodes.isEmpty()) {
             List<Node> level = new ArrayList<>();
             levels.add(level);

             for (Node node : new ArrayList<>(nodes)) {
                 level.add(node);
                 if (node.left != null) {
                     nodes.add(node.left);
                 }
                 if (node.middle!= null) {
                     nodes.add(node.middle);
                 }if (node.right != null) {
                     nodes.add(node.right);
                 }
                 nodes.poll();
             }
         }
         return levels;
     }
 }

public class Tree23 {
public  static void main(String[] args){
   TwoThreeTre<Integer, Integer> t = new TwoThreeTre<>();
   t.add(13,13);
   t.add(19,19);
   t.add(20,20);
   t.add(34,34);
   t.add(29,29);
   t.add(100,100);
   t.add(130,130);
   t.add(8,8);
   t.add(15,15);
   t.add(4,4);
   t.add(6, 6);
   t.add(9,9);
   t.show();

   System.out.println();
   t.delete(8);
   t.delete(100);
   t.delete(13);
   t.delete(34);
   t.show();
   t.find(0);

}
}