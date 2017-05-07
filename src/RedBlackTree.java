import java.util.*;

/**
 * Created by Lemekh on 19.11.2016.
 */
public class RedBlackTree {

    public RedBlackTree( ) {
        header      = new RedBlackNode( null );
        header.left = header.right = nullNode;
    }

    private final int compare( Comparable item, RedBlackNode t ) {
        if( t == header )
            return 1;
        else
            return item.compareTo( t.element );
    }

    public void insert( Comparable item ) {
        current = parent = grand = header;
        nullNode.element = item;

        while( compare( item, current ) != 0 ) {
            great = grand; grand = parent; parent = current;
            current = compare( item, current ) < 0 ?
                    current.left : current.right;


            if( current.left.color == RED && current.right.color == RED )
                handleReorient( item );
        }


        current = new RedBlackNode( item, nullNode, nullNode );


        if( compare( item, parent ) < 0 )
            parent.left = current;
        else
            parent.right = current;
        handleReorient( item );
    }

    List<Comparable> arrayofelement = new ArrayList();
    public void find(){
        find(header.right);
    }
    public void find(RedBlackNode root) {

        if (root!=nullNode){
            find(root.left);
            find(root.right);
            if (root.left==nullNode && root.right == nullNode) arrayofelement.add(root.element);
        }
    }

    public void printTree( ) {
        printLevelWise( header.right );
    }
    public void printLevelWise(RedBlackNode root) {

        List<List<RedBlackNode>> levels = traverseLevels(root);
        for (List<RedBlackNode> level : levels) {
            for (RedBlackNode node : level) {
                try{
                    System.out.print(node.element +" " +"("+node.color+")");
                } catch (NullPointerException e){}
            }
            System.out.println();
        }

    }

    private List<List<RedBlackNode>> traverseLevels(RedBlackNode root) {

        List<List<RedBlackNode>> levels = new LinkedList<>();

        Queue<RedBlackNode> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            List<RedBlackNode> level = new ArrayList<>();
            levels.add(level);

            for (RedBlackNode node : new ArrayList<>(nodes)) {
                level.add(node);
                if (node.left != nullNode) {
                    nodes.add(node.left);
                }
                if (node.right != nullNode) {
                    nodes.add(node.right);
                }
                nodes.poll();
            }
        }
        return levels;
    }

    Map<Integer,Comparable> hashMap = new HashMap< Integer,Comparable>();
    private static int countred = 0;
    public Comparable findEl(Comparable el){
        countred=0;
        nullNode.element = el;
        current = header.right;
        for( ; ; ) {
            if (current.color==0) countred++;
            if( el.compareTo( current.element ) < 0 )
                current = current.left;
            else if( el.compareTo( current.element ) > 0 )
                current = current.right;
            else if( current != nullNode )
                return current.element;
            else
                return null;
        }

    }
    private void MinCountRed(){

        find();
        for (int i =0; i<arrayofelement.size(); i++){
            findEl(arrayofelement.get(i));
            hashMap.put(countred,arrayofelement.get(i));
        }


        int a=1;
        for (int i:hashMap.keySet()){
            if (i==0) continue;
            if (a>i) a=i;
        }

        showaway(hashMap.get(a));

    }
    public Comparable showaway(Comparable el){
        countred=0;
        nullNode.element = el;
        current = header.right;
        for( ; ; ) {
            System.out.print(current.element+" ");
            if (current.color==0) countred++;
            if( el.compareTo( current.element ) < 0 )
                current = current.left;
            else if( el.compareTo( current.element ) > 0 )
                current = current.right;
            else if( current != nullNode )
                return current.element;
            else
             return null;
        }

    }



    public boolean isEmpty( ) {
        return header.right == nullNode;
    }

    private void handleReorient( Comparable item ) {

        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if( parent.color == RED )
        {
            grand.color = RED;
            if( ( compare( item, grand ) < 0 ) !=
                    ( compare( item, parent ) < 0 ) )
                parent = rotate( item, grand );
            current = rotate( item, great );
            current.color = BLACK;
        }
        header.right.color = BLACK;
    }

    private RedBlackNode rotate( Comparable item, RedBlackNode parent ) {
        if( compare( item, parent ) < 0 )
            return parent.left = compare( item, parent.left ) < 0 ?
                    rotateWithLeftChild( parent.left )  :
                    rotateWithRightChild( parent.left ) ;
        else
            return parent.right = compare( item, parent.right ) < 0 ?
                    rotateWithLeftChild( parent.right ) :
                    rotateWithRightChild( parent.right );
    }

    private static RedBlackNode rotateWithLeftChild( RedBlackNode k2 ) {
        RedBlackNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    private static RedBlackNode rotateWithRightChild( RedBlackNode k1 ) {
        RedBlackNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    private static class RedBlackNode {

        RedBlackNode( Comparable theElement ) {
            this( theElement, null, null );
        }

        RedBlackNode( Comparable theElement, RedBlackNode lt, RedBlackNode rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
            color    = RedBlackTree.BLACK;
        }

        Comparable   element;
        RedBlackNode left;
        RedBlackNode right;
        int          color;
    }

    private RedBlackNode header;
    private static RedBlackNode nullNode;
    static
    {
        nullNode = new RedBlackNode( null );
        nullNode.left = nullNode.right = nullNode;
    }

    private static final int BLACK = 1;
    private static final int RED   = 0;

    private static RedBlackNode current;
    private static RedBlackNode parent;
    private static RedBlackNode grand;
    private static RedBlackNode great;

    public static void main( String [ ] args ) {
        RedBlackTree t = new RedBlackTree( );
        System.out.println("Black node = 1");
        System.out.println("Red node = 0");
        t.insert(50);
        t.insert(10);
        t.insert(45);
        t.insert(12);
        t.insert(65);
        t.insert(88);
        t.insert(1);
        t.insert(47);
        t.insert(34);
        t.printTree();
        System.out.println();
        t.MinCountRed();



    }
}

