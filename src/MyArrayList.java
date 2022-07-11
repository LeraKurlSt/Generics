import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class MyArrayList<T> {
    private Object[] data;
    private int capacity;
    private int lastElem = 0;

// Task 2
    public MyArrayList(int amount) {
        this.capacity = amount;
        data = new Object[amount];
    }

    public MyArrayList() {
        int size = 10;
        data = new Object[size];
        capacity = size;
    }

    public int getSize() {
        if ((lastElem == 0) && (data[lastElem] == null)){
            return 0;
        }else {
            return lastElem + 1;
        }
    }

    public int getCapacity(){
        return capacity;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Object i : data){
            if (i != null) sb.append(i).append(" ");
        }
        return sb.toString();
    }

    private void ensureCapacity(int value){
        capacity = data.length;
        if (capacity < value){
            int capacity = (int)(value * 1.5) + 1;
            Object[] newData = new Object[capacity];
            System.arraycopy(data, 0, newData, 0, capacity);
            data = newData;
            capacity = data.length;
        }
    }

// Task 3
    public boolean insert(T item, int index){
        boolean success = false;
        ensureCapacity(lastElem + 2);
        if (index == 0){
            if (data[0] == null){
                data[0] = item;
            }else {
                System.arraycopy(data, 0, data, 1, lastElem + 1);
                data[0] = item;
                lastElem++;
            }
            success = true;
        }else if (index <= lastElem){
            System.arraycopy(data, index, data, index+1, lastElem + 1 - index);
            data[index] = item;
            lastElem++;
            success = true;
        }else if (index == lastElem + 1){
            data[index] = item;
            lastElem++;
            success = true;
        }
        return success;
    }

    public void pushBack(T item){
        if ((lastElem == 0) && (data[0] == null)){
            insert (item, 0);
        }else {
            insert(item, lastElem+1);
        }
    }

    public boolean removeAt(int index) {
        boolean success = false;
        if ((index == 0) && (data[0] != null)) {
            System.arraycopy(data, 1, data, 0, lastElem);
            data[lastElem] = null;
            lastElem--;
            success = true;
        } else if (index == lastElem) {
            data[lastElem] = null;
            lastElem--;
            success = true;
        } else if ((index > 0) && (index <= lastElem)) {
            System.arraycopy(data, index + 1, data, index, lastElem - index);
            data[lastElem] = null;
            lastElem--;
            success = true;
        }
        return success;
    }

        public boolean popFront() {
            return removeAt(0);
        }


    public void pushFront(T item){
        insert(item, 0);
    }

    public boolean popBack(){
        return  removeAt(lastElem);
    }

// Task 4
    public int indexOf(Object item) {
        for (int i = 0; i <= lastElem; i++) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }
// Task 3
    public boolean remove(T item){
        boolean success = false;
        int index = indexOf(item);
        if (index != -1){
            removeAt(index);
            success = true;
        }
        return success;
    }

    public void removeAll(T item) {
        Object[] arr = new Object[data.length];
        data = Arrays.stream(data).filter(o -> ((o != null) && !o.equals(item))).toArray();
        System.arraycopy(data, 0, arr, 0, data.length);
        lastElem = data.length - 1;
        data = arr;
    }

    public void clear() {
        data = new Object[capacity];
        lastElem = 0;
    }

// Task 4
    public boolean isEmpty() {
        return ((lastElem == 0) && (data[0] == null));
    }

    public void trimToSize() {
        data = new Object[lastElem + 1];
        capacity = data.length;
    }

    public int lastIndexOf(Object item) {
        for (int i = lastElem; 0 <= i; i--) {
            if ((data[i] != null) && (data[i].equals(item))) {
                return i;
            }
        }
        return -1;
    }

// Task 5

    public void reverse() {
        Object revItem;
        for (int i = 0; i < (lastElem / 2); i++) {
            revItem = data[i];
            data[i] = data[lastElem - i];
            data[lastElem - i] = revItem;
        }
    }

    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i <= lastElem; i++) {
            int randomIndexToSwap = rand.nextInt(lastElem);
            Object o = data[randomIndexToSwap];
            data[randomIndexToSwap] = data[i];
            data[i] = o;
        }
    }

    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyArrayList<?> that)) {
            return false;
        }
        return capacity == that.capacity &&
                lastElem == that.lastElem &&
                Arrays.equals(data, that.data);
    }

    public T getElementAt(int index) throws IOException, ClassNotFoundException {
        if ((0 <= index) && (index <= lastElem)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream;
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data[index]);
            objectOutputStream.close();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } else {
            return null;
        }
    }

    protected MyArrayList clone() throws CloneNotSupportedException {
        MyArrayList cloneList = (MyArrayList) super.clone();
        MyArrayList<Object> clone = new MyArrayList<>(this.capacity);
        for (int i = 0; i <= lastElem; i++) {
            clone.pushBack(data[i]);
        }
        return clone;
    }
}
