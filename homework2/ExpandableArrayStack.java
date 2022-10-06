package homework2;

import java.util.NoSuchElementException;

public class ExpandableArrayStack implements Stack {

    private static final int initialCapacity = 16;
    private int currentCapacity;
    private Object[] stack;
    private int last;

    public ExpandableArrayStack() {
        currentCapacity = 16;
        last = 0;
        stack = new Object[initialCapacity];
    }

    private void increaseCapacity() {
        currentCapacity = currentCapacity * 2;
        Object[] largeStack = new Object[currentCapacity];
        System.arraycopy(stack, 0, largeStack, 0, stack.length);
        stack = largeStack;
    }

    private void decreaseCapacity() {
        currentCapacity = currentCapacity / 2;
        Object[] smallStack = new Object[currentCapacity];
        System.arraycopy(stack, 0, smallStack, 0, smallStack.length);
        stack = smallStack;
    }

    @Override
    public void push(Object value) {
        if(last >= currentCapacity) {
            increaseCapacity();
        }
        stack[last] = value;
        last++;
    }

    @Override
    public Object pop() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        last--;
        Object poppedElement = stack[last];
        stack[last] = null;
        if(last > initialCapacity && last < currentCapacity/4) {
            decreaseCapacity();
        }
        return poppedElement;
    }

    @Override
    public Object peek() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return stack[last-1];
    }

    @Override
    public int size() {
        return last;
    }

    public int capacity() {
        return currentCapacity;
    }
}
