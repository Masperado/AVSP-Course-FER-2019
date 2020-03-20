import java.util.Iterator;

public class ArrayIterator implements Iterator<Integer> {

    private int[] array;

    private int currentIndex;

    public ArrayIterator(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        if (array==null){
            return false;
        }

        return currentIndex<array.length;
    }

    @Override
    public Integer next() {
        return array[currentIndex++];
    }


    public static void main(String[] args) {
        int array[] = new int[10];

//        for (int i=0;i<array.length;i++){
//            array[i]=i*10;
//        }

        Iterator<Integer> iterator = new ArrayIterator(new int[]{});

        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
