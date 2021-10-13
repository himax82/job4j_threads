package linked;

public class Node<T> {

    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> setNext(final Node<T> next) {
        Node<T> node = new Node<>(next, value);
        return next;
    }

    public T getValue() {
        return value;
    }

    public Node<T> setValue(T value) {
        Node<T> node = new Node<>(next, value);
        return node;
    }
}