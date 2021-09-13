package struct;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private final T data;

    private Node<T> parent;

    private final List<Node<T>> children;

    private final static int initCapacity = 50;

    public Node(T data)
    {
        this.data = data;
        this.children = new ArrayList<>(initCapacity);
    }

    public Node(T data, Node<T> parent)
    {
        this.data = data;
        this.children = new ArrayList<>(initCapacity);
        this.parent = parent;
    }

    public void addChild(Node<T> child)
    {
        if (child == null)
            throw new IllegalArgumentException(
                    "child must not be null."
            );
        child.setParent(this);
        this.children.add(child);
    }

    public Node<T> addChild(T data)
    {
        Node<T> newChild = new Node<>(data);
        this.addChild(newChild);
        return this;
    }

    public Node<T> addChildren(List<Node<T>> children)
    {
        if (children == null)
            throw new IllegalArgumentException(
                    "children must not be null."
            );
        children.forEach(this::addChild);
        return this;
    }

    public List<Node<T>> getChildren()
    {
        return this.children;
    }

    public boolean isRoot()
    {
        return (this.getParent()== null);
    }

    public boolean isLeaf()
    {
        return (this.getChildren().size() == 0);
    }

    private void setParent(Node<T> parent)
    {
        this.parent = parent;
    }

    public Node<T> getParent()
    {
        return this.parent;
    }

    public T getData()
    {
        return this.data;
    }

    public int getHeight()
    {
        if (this.getChildren().size() == 0) return 0;
        int h = 0;

        for (Node<T> n : this.getChildren()) {
            h = Math.max(h, n.getHeight());
        }
        return h + 1;
    }

    public int getDepth()
    {
        if (this.getParent() == null) return 0;
        int d = 0;

        d = Math.max(d, this.getParent().getDepth());
        return d + 1;
    }

    public List<Node<T>> leafNodes()
    {
        List<Node<T>> leafNodes = new ArrayList<Node<T>>();
        if (this.isLeaf()) {
            leafNodes.add(this);
        } else {
            for (Node<T> child : this.getChildren()) {
                leafNodes.addAll(child.leafNodes());
            }
        }
        return leafNodes;
    }

    public List<Node<T>> nthDepthNodes(int n)
    {
        List<Node<T>> nodes = new ArrayList<Node<T>>();
        if (this.getDepth() == n) {
            nodes.add(this);
        } else {
            for (Node<T> child : this.getChildren()) {
                nodes.addAll(child.nthDepthNodes(n));
            }
        }
        return nodes;
    }

    public static <T> Node<T> firstGenParent(final Node<T> node)
    {
        if (node == null || node.isRoot())
            throw new IllegalArgumentException(
                    "node must not be null, and starting node must not be root."
            );

        if (node.getParent().isRoot())
            return node;

        return firstGenParent(node.getParent());
    }

    public static <T> void print(Node<T> node)
    {
        System.out.println(node);

        if (node.isLeaf())
            return;

        for (Node<T> child : node.getChildren())
            print(child);
    }

    public static <T> void print(Node<T> node, int depth)
    {
        if (node.getDepth() == depth)
            System.out.println(node);

        if (node.isLeaf())
            return;

        for (Node<T> child : node.getChildren())
        {
            print(child, depth);
        }
    }

    @Override
    public String toString()
    {
        return String.format("Node<%s>[%sdata=%s, depth=%d]",
                this.getData().getClass().getSimpleName(),
                this.parent != null ? "parent=" + this.parent.data + ", " : "", this.data, this.getDepth());
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Node))
            return false;
        if (o == this)
            return true;

        @SuppressWarnings("unchecked")
        Node<T> node = (Node<T>) o;
        return node.getParent().equals(this.getParent()) &&
                node.getChildren().equals(this.getChildren()) &&
                node.getData().equals(this.getData());
    }
}
