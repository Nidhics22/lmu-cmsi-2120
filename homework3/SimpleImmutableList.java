package homework3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A simple immutable linked list, implement completely from scratch as a
 * singly-linked structure. There aren't too many operations, as additional will
 * be left for homework.
 *
 * The implementation uses a sum type because I have major null-phobia. Not
 * going to pay the billion-dollar mistake, not with this one.
 */
public sealed interface SimpleImmutableList permits EmptyList, ListNode {
    int size();

    Object head();

    SimpleImmutableList tail();

    static SimpleImmutableList of(Object... items) {
        SimpleImmutableList list = new EmptyList();
        for (var i = items.length - 1; i >= 0; i--) {
            list = new ListNode(items[i], list);
        }
        return list;
    }

    static SimpleImmutableList from(Object head, SimpleImmutableList tail) {
        return new ListNode(head, tail);
    }

    Object at(int index);

    void forEach(Consumer<Object> consumer);

    SimpleImmutableList take(int index);

    SimpleImmutableList drop(int index);

    SimpleImmutableList reversed();

    SimpleImmutableList append(SimpleImmutableList twoFive);

    int last();

    SimpleImmutableList map(UnaryOperator<Object> function);

    SimpleImmutableList filter(UnaryOperator<Object> function);

    boolean every(UnaryOperator<Object> function);

    boolean some(UnaryOperator<Object> function);
}

final record EmptyList() implements SimpleImmutableList {
    public int size() {
        return 0;
    }

    public Object head() {
        throw new NoSuchElementException();
    }

    public SimpleImmutableList tail() {
        throw new NoSuchElementException();
    }

    public Object at(int index) {
        throw new NoSuchElementException();
    }

    public void forEach(Consumer<Object> consumer) {
        // Intentionally empty: nothing to iterate
    }

    public SimpleImmutableList take(int index) {
        if(index == 0)
            return new EmptyList();
        else
            throw new IllegalArgumentException();
    }

    public SimpleImmutableList drop(int index) {
        if(index == 0)
            return new EmptyList();
        else
            throw new IllegalArgumentException();
    }

    public SimpleImmutableList reversed() {
        return new EmptyList();
    }

    public SimpleImmutableList append(SimpleImmutableList list) {
        return list;
    }

    public int last() {
        throw new NoSuchElementException();
    }

    public SimpleImmutableList map(UnaryOperator<Object> function) {
        return new EmptyList();
    }

    public SimpleImmutableList filter(UnaryOperator<Object> function) {
        return new EmptyList();
    }

    public boolean every(UnaryOperator<Object> function) {
        return true;
    }

    public boolean some(UnaryOperator<Object> function) {
        return false;
    }

}

final record ListNode(Object head, SimpleImmutableList tail) implements SimpleImmutableList {
    public int size() {
        return 1 + tail.size();
    }

    public Object at(int index) {
        return index == 0 ? head : tail.at(index - 1);
    }

    public void forEach(Consumer<Object> consumer) {
        consumer.accept(head);
        tail.forEach(consumer);
    }

    public SimpleImmutableList take(int index) {
        if(index > size() || index < 0) {
            throw new IllegalArgumentException();
        } else if(index == 0) {
            return new EmptyList();
        }

        Object[] valueArray = new Object[index];
        int i = 0;
        while(index > 0) {
            valueArray[i] = at(i);
            index--;
            i++;
        }
        return SimpleImmutableList.of(valueArray);
    }

    public SimpleImmutableList drop(int index) {
        if(index > size() || index < 0) {
            throw new IllegalArgumentException();
        } else if(index == size()) {
            return new EmptyList();
        }

        Object[] valueArray = new Object[size()-index];
        int i = index;
        int j = 0;
        while(i < size()) {
            valueArray[j] = at(i);
            i++;
            j++;
        }
        return SimpleImmutableList.of(valueArray);
    }

    public SimpleImmutableList reversed() {
        Object[] valueArray = new Object[size()];
        int i = 0;
        int j = size()-1;
        while(i < size()) {
            valueArray[i] = at(j);
            i++;
            j--;
        }
        return SimpleImmutableList.of(valueArray);
    }

    public SimpleImmutableList append(SimpleImmutableList list) {
        if(list.size() == 0) {
            return new ListNode(head, tail());
        }

        int currentSize = size();
        int appendListSize = list.size();
        int newListSize = currentSize + appendListSize;

        int i = 0;
        Object[] newValueArray = new Object[newListSize];
        while(i < currentSize) {
            newValueArray[i] = at(i);
            i++;
        }

        int j = 0;
        while(j < appendListSize) {
            newValueArray[i] = at(j);
            j++;
            i++;
        }
        return SimpleImmutableList.of(newValueArray);
    }

    public int last() {
        return (int) at(size()-1);
    }

    public SimpleImmutableList map(UnaryOperator<Object> function) {
        int currentSize = size();
        Object[] valueArray = new Object[currentSize];
        int i= 0;
        while (i < currentSize) {
            valueArray[i] = function.apply(at(i));
            i++;
        }
        return SimpleImmutableList.of(valueArray);
    }

    public SimpleImmutableList filter(UnaryOperator<Object> function) {
        int currentSize = size();
        List<Object> valueList = new ArrayList<>();
        int i= 0;
        while (i < currentSize) {
            boolean result = (boolean) function.apply(at(i));
            if(result) {
                valueList.add(at(i));
            }
            i++;
        }
        return SimpleImmutableList.of(valueList.toArray());
    }

    public boolean every(UnaryOperator<Object> function) {
        boolean result;
        int i = 0;
        int currentSize = size();

        while (i < currentSize) {
            result = (boolean) function.apply(at(i));
            if(!result)
                return false;
            i++;
        }
        return true;
    }

    public boolean some(UnaryOperator<Object> function) {
        boolean result;
        int i = 0;
        int currentSize = size();

        while (i < currentSize) {
            result = (boolean) function.apply(at(i));
            if(result)
                return true;
            i++;
        }
        return false;
    }
}