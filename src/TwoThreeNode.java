/**
 * Created by OstapPK on 28.11.2016.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoThreeNode implements Comparable<TwoThreeNode> {

    private TwoThreeNode parent;
    private Integer valueKey;
    private List<TwoThreeNode> children;
    private int totalLeafsUnderneath;

    public TwoThreeNode() {
        children = new ArrayList<TwoThreeNode>();
        this.totalLeafsUnderneath = 0;
    }

    public TwoThreeNode(int valueKey) {
        this();
        this.valueKey = valueKey;
    }

    public boolean isLeaf() {
        return numChildren() == 0;
    }

    public boolean isDangling() {
        return this.isLeaf() && valueKey == null;
    }

    public int getKey() {
        if (!this.isLeaf()) {
            throw new NullPointerException(
                    "Trying to get value from internal node");
        }
        return this.valueKey;
    }

    public TwoThreeNode getChild(int index) {
        return children.get(index);
    }

    public void addChild(TwoThreeNode node) {
        if (numChildren() <= 3) {
            children.add(node);
            node.setParent(this);
            if (node.isLeaf()) {
                incrementLeafCount(1);
            } else {
                incrementLeafCount(node.getTotalLeafsUnderneath());
            }
            Collections.sort(children);
        } else {
            throw new IllegalArgumentException("Node has too many children!");
        }
    }

    public void removeChild(int index) {
        TwoThreeNode childToRemove = children.get(index);
        if (childToRemove.isLeaf() && !childToRemove.isDangling()) {
            decrementLeafCount(1);
        } else {
            decrementLeafCount(children.get(index).getTotalLeafsUnderneath());
        }
        children.remove(index);
    }

    public void removeChild(TwoThreeNode nodeToRemove) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) == nodeToRemove) {
                removeChild(i);
            }
        }
    }

    public void incrementLeafCount(int numLeaves) {
        this.totalLeafsUnderneath += numLeaves;
        if (this.parent != null) {
            getParent().incrementLeafCount(numLeaves);
        }
    }

    public void decrementLeafCount(int numLeaves) {
        this.totalLeafsUnderneath -= numLeaves;
        if (this.parent != null) {
            getParent().decrementLeafCount(numLeaves);
        }
    }

    public int getTotalLeafsUnderneath() {
        return this.totalLeafsUnderneath;
    }

    public void setParent(TwoThreeNode parent) {
        this.parent = parent;
    }

    public TwoThreeNode getParent() {
        return this.parent;
    }

    public int numChildren() {
        int childCounter = 0;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) != null) {
                childCounter++;
            }
        }
        return childCounter;
    }

    public Integer getLargestFirstSubtree() {
        if (!this.isLeaf()) {
            TwoThreeNode node = this.children.get(0);
            while (!node.isLeaf()) {
                node = node.largestSubElement();
            }
            return node.getKey();
        } else {
            return this.valueKey;
        }
    }

    public Integer getLargestSecondSubtree() {
        if (!this.isLeaf()) {
            TwoThreeNode node = this.children.get(1);
            while (!node.isLeaf()) {
                node = node.largestSubElement();
            }
            return node.getKey();
        } else {
            return this.valueKey;
        }
    }

    private TwoThreeNode largestSubElement() {
        return children.get(this.numChildren() - 1);
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "key value: " + this.valueKey;
        } else {
            return "[ Node L: "
                    + String.valueOf(getLargestFirstSubtree())
                    + ", R: "
                    + String.valueOf(getLargestSecondSubtree()
                    + " total leafs: "
                    + String.valueOf(totalLeafsUnderneath)) + " ]";
        }
    }

    //////////////////////////////////////////////////////////////////
    // call on the root node of a tree to print the entire tree

    public void printSubTree() {
        print("", true);
    }

    // ///////////////////////////////////////////////////////////////
    // Recursively print the 2-3 nodes under this one
    void print2(){
        System.out.println(children);
    }
    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "--- " : "|-- ") + this);
        if (!this.isLeaf()) {
            for (int i = 0; i < numChildren() - 1; i++) {
                children.get(i).print(prefix + (isTail ? "    " : "|   "),
                        false);
            }
            if (numChildren() > 0) {
                children.get(numChildren() - 1).print(
                        prefix + (isTail ? "    " : "|   "), true);
            }
        }
    }

    // //////////////////////////////////////////////////////////////
    // Satisfy Comparable interface

    @Override
    public int compareTo(TwoThreeNode arg0) {
        if (this.isLeaf() && arg0.isLeaf()) {
            return this.valueKey.compareTo(arg0.valueKey);
        } else {
            return this.getLargestFirstSubtree().compareTo(
                    arg0.getLargestFirstSubtree());
        }
    }
}