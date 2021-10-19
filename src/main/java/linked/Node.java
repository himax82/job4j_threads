package linked;

public final class Node<T> {

    private final Node<T> tNode;
    private final T val;

    public Node(Node<T> next, T value) {
        this.tNode = next;
        this.val = value;
    }

    public Node<T> getNext() {
        return tNode;
    }

    public Node<T> setNext(final Node<T> next) {
        Node<T> node = new Node<>(next, val);
        return next;
    }

    public T getValue() {
        return val;
    }

    public Node<T> setValue(T value) {
        Node<T> node = new Node<>(tNode, value);
        return node;
    }
}