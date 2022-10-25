package homework3;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A simple linked list class, implemented completely from scratch, using doubly
 * linked nodes and a cool "header" node to greatly simplify insertion and
 * deletions on the end of the list.
 */
public class SimpleLinkedList {

    /**
     * Doubly-linked node class, completely private to the List class, as clients
     * don't care about the implementation of the list. This is a regular class and
     * not a record because nodes are mutable.
     */
    private class Node {
        Object item;
        Node next;
        Node previous;

        Node(Object item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    /**
     * The list itself maintains only a reference to its "header" node. The header
     * is a node that does not store any data. Its 'next' field points to the first
     * item in the list and its 'previous' field points to the last item. This makes
     * all insertions and deletions uniform, even at the beginning and the end of
     * the list!
     */
    private Node header = new Node(null, null, null);

    /**
     * The number of items in the list, stored to make size() O(1).
     */
    private int size = 0;

    public SimpleLinkedList() {
        // The empty list is made by pointing the header to itself.
        header.next = header.previous = header;
    }

    public int size() {
        // The size stored as a field for easy lookup
        return size;
    }

    public void addFirst(Object item) {
        // The first node is the one right after the header.
        addBefore(item, header.next);
    }

    public void addLast(Object item) {
        // The last node is the one right before the header.
        addBefore(item, header);
    }

    public void add(int index, Object item) {
        // This one is trickier, as there is a special case. If the index is equal
        // to the size, we are just adding the new element at the end. Otherwise
        // we use the special nodeAt() utility to point to the existing node at the
        // desired position. Adding before this node does the trick.
        addBefore(item, (index == size ? header : nodeAt(index)));
    }

    public void remove(int index) {
        // The utility methods do all the work here.
        remove(nodeAt(index));
    }

    public Object get(int index) {
        // Many thanks to the nodeAt() utility!
        return nodeAt(index).item;
    }

    public void set(int index, Object item) {
        // nodeAt() is sure cool, innit?
        nodeAt(index).item = item;
    }

    public int indexOf(Object item) {
        // Ah the old "linear search" pattern: a for-loop with a return in
        // the middle. Note that to iterate the actual nodes, we start at
        // header.next, since header does not hold a list item. The loop
        // terminates when we get back to the header.
        var index = 0;
        for (var node = header.next; node != header; node = node.next) {
            if (node.item.equals(item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void forEach(Consumer<Object> consumer) {
        // Iterate from the first node (the one after the header).
        for (var node = header.next; node != header; node = node.next) {
            consumer.accept(node.item);
        }
    }

    /**
     * Get the node at a given index.
     */
    private Node nodeAt(int index) {
        // The only legal indexes are 0, 1, ... size-1.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " for size " + size);
        }
        // Only way to get by index in a simple linked list is to walk and
        // count. There are other kinds of lists that give quicker access by
        // position, but those structures are for another time.
        Node node = header;
        for (var i = 0; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    private void addBefore(Object item, Node node) {
        var newNode = new Node(item, node, node.previous);
        newNode.previous.next = newNode;
        newNode.next.previous = newNode;
        size++;
    }

    /**
     * Removes the referenced node by making its neighbors point around it.
     */
    private void remove(Node node) {
        // Just in case
        if (node == header) {
            throw new NoSuchElementException();
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
        size--;
    }

    public void take(int index) {
        if(index == 0)  {
            header.next = header;
            header.previous = header;
        } else if(index > 0 && index <= size) {
            nodeAt(index-1).next = header;
            header.previous = nodeAt(index-1);
        } else {
            throw new IllegalArgumentException();
        }
        size = index;
    }

    public void drop(int index) {
        if(index == size) {
            header.next = header;
            header.previous = header;
            size = 0;
        }else if(index > 0 && index <= size) {
            Node node = nodeAt(index);
            header.next = node;
            node.previous =header;
            size = size - index;
            System.out.println();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void reverse() {
        if(size > 1) {
            Node previousNode = header;
            Node currentNode = header.next;
            Node nextNode = currentNode.next;

            while(currentNode != header) {
                currentNode.previous = nextNode;
                currentNode.next = previousNode;
                previousNode = currentNode;
                currentNode = nextNode;
                nextNode = nextNode.next;
            }
            header.next = previousNode;
        }
    }

    public void append(SimpleLinkedList newList)  {
        if(header == newList.header) {
            throw new IllegalArgumentException();
        }
        Node newListHeadNode = newList.header.next;
        while (newListHeadNode != newList.header) {
            addLast(newListHeadNode.item);
            newListHeadNode = newListHeadNode.next;
        }
    }

    public int last() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        return (Integer)header.previous.item;
    }

    public void map(UnaryOperator<Object> function) {
        for (var node = header.next; node != header; node = node.next) {
            node.item = function.apply(node.item);
        }
    }

    public void filter(UnaryOperator<Object> function) {
        for (var node = header.next; node != header; node = node.next) {
            boolean result = (boolean) function.apply(node.item);
            if(!result) {
                remove(node);
            }
        }
    }

    public static SimpleLinkedList of(Object... nodeItems) {
        SimpleLinkedList list = new SimpleLinkedList();
        for (Object nodeItem : nodeItems) {
            list.addLast(nodeItem);
        }
        return list;
    }

    public boolean every(UnaryOperator<Object> function) {
        boolean result;
        for (var node = header.next; node != header; node = node.next) {
            result = (boolean) function.apply(node.item);
            if(!result)
                return false;
        }
        return true;
    }

    public boolean some(UnaryOperator<Object> function) {
        boolean result;
        for (var node = header.next; node != header; node = node.next) {
            result = (boolean) function.apply(node.item);
            if(result)
                return true;
        }
        return false;
    }
}
