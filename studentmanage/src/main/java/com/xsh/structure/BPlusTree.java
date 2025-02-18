package com.xsh.structure;
import java.util.ArrayList;
import java.util.List;

public class BPlusTree<K extends Comparable<K>, V>implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    // B+树的阶数（每个节点最多存储的关键字数）
    private int order;
    // 根节点
    private Node root;

    public BPlusTree(int order) {
        if (order < 3) {
            throw new IllegalArgumentException("B+树的阶数至少为 3");
        }
        this.order = order;
        // 初始树只有一个叶子节点
        this.root = new LeafNode();
    }

    public int getOrder() {
        return order;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(K key, V value) {
        LeafNode leaf = findLeafNode(key);
        leaf.insert(key, value);
        if (leaf.isOverflow()) {
            Node newRoot = leaf.handleOverflow();
            if (newRoot != null) {
                this.root = newRoot;
            }
        }
    }

    public V search(K key) {
        LeafNode leaf = findLeafNode(key);
        int index = leaf.keys.indexOf(key);
        if (index != -1) {
            return leaf.values.get(index);
        }
        return null;
    }

    public void delete(K key) {
        LeafNode leaf = findLeafNode(key);
        leaf.delete(key);
        if (leaf.isUnderflow()) {
            Node newRoot = leaf.handleUnderflow();
            if (newRoot != null) {
                this.root = newRoot;
            }
        }
    }

    private LeafNode findLeafNode(K key) {
        Node currentNode = root;
        while (!currentNode.isLeaf) {
            InternalNode internalNode = (InternalNode) currentNode;
            currentNode = internalNode.getChild(key);
        }
        return (LeafNode) currentNode;
    }

    abstract class Node implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        protected ArrayList<K> keys;
        protected boolean isLeaf;
        protected InternalNode parent;

        public Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new ArrayList<>();
            this.parent = null;
        }

        public List<K> getKeys() {
            return keys;
        }

        public abstract boolean isOverflow();

        public abstract boolean isUnderflow();

        public abstract Node handleOverflow();

        public abstract Node handleUnderflow();
    }

    class InternalNode extends Node implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        protected ArrayList<Node> children;

        public InternalNode() {
            super(false);
            this.children = new ArrayList<>();
        }

        public List<Node> getChildren() {
            return children;
        }

        public Node getChild(K key) {
            int index = 0;
            while (index < keys.size() && key.compareTo(keys.get(index)) >= 0) {
                index++;
            }
            return children.get(index);
        }

        @Override
        public boolean isOverflow() {
            return children.size() > order;
        }

        @Override
        public boolean isUnderflow() {
            return children.size() < (order + 1) / 2;
        }

        @Override
        public Node handleOverflow() {
            int midIndex = keys.size() / 2;
            K midKey = keys.get(midIndex);

            InternalNode newInternalNode = new InternalNode();
            for (int i = midIndex + 1; i < keys.size(); i++) {
                newInternalNode.keys.add(keys.get(i));
            }
            for (int i = midIndex + 1; i < children.size(); i++) {
                newInternalNode.children.add(children.get(i));
            }

            keys.subList(midIndex, keys.size()).clear();
            children.subList(midIndex + 1, children.size()).clear();

            if (parent == null) {
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(midKey);
                newRoot.children.add(this);
                newRoot.children.add(newInternalNode);
                this.parent = newRoot;
                newInternalNode.parent = newRoot;
                return newRoot;
            } else {
                newInternalNode.parent = parent;
                parent.children.add(parent.keys.indexOf(midKey) + 1, newInternalNode);
                parent.keys.add(parent.keys.indexOf(midKey), midKey);
                return parent.isOverflow() ? parent.handleOverflow() : null;
            }
        }

        @Override
        public Node handleUnderflow() {
            // 处理节点下溢的逻辑
            return null;
        }
    }

    class LeafNode extends Node implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        protected ArrayList<V> values;
        protected LeafNode next;

        public LeafNode() {
            super(true);
            this.values = new ArrayList<>();
            this.next = null;
        }

        public List<V> getValues() {
            return values;
        }

        public LeafNode getNext() {
            return next;
        }

        public void insert(K key, V value) {
            int index = 0;
            while (index < keys.size() && key.compareTo(keys.get(index)) > 0) {
                index++;
            }
            keys.add(index, key);
            values.add(index, value);
        }

        public void delete(K key) {
            int index = keys.indexOf(key);
            if (index != -1) {
                keys.remove(index);
                values.remove(index);
            }
        }

        @Override
        public boolean isOverflow() {
            return values.size() > order - 1;
        }

        @Override
        public boolean isUnderflow() {
            return values.size() < (order + 1) / 2;
        }

        @Override
        public Node handleOverflow() {
            int midIndex = keys.size() / 2;

            LeafNode newLeafNode = new LeafNode();
            for (int i = midIndex; i < keys.size(); i++) {
                newLeafNode.keys.add(keys.get(i));
                newLeafNode.values.add(values.get(i));
            }

            keys.subList(midIndex, keys.size()).clear();
            values.subList(midIndex, values.size()).clear();

            newLeafNode.next = this.next;
            this.next = newLeafNode;

            if (parent == null) {
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(newLeafNode.keys.get(0));
                newRoot.children.add(this);
                newRoot.children.add(newLeafNode);
                this.parent = newRoot;
                newLeafNode.parent = newRoot;
                return newRoot;
            } else {
                newLeafNode.parent = parent;
                parent.children.add(parent.keys.indexOf(newLeafNode.keys.get(0)) + 1, newLeafNode);
                parent.keys.add(parent.keys.indexOf(newLeafNode.keys.get(0)), newLeafNode.keys.get(0));
                return parent.isOverflow() ? parent.handleOverflow() : null;
            }
        }

        @Override
        public Node handleUnderflow() {
            // 处理节点下溢的逻辑
            return null;
        }
    }
}