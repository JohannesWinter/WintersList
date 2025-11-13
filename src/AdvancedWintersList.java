public class AdvancedWintersList<T> {
    private int size;
    private LogListElement<T> headLLE;

    public AdvancedWintersList(int size) {
        headLLE = new LogListElement<>(0);
        if (size < 0)
            throw new IllegalArgumentException();
        this.size = size;
    }
    public void set(int index, T value) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        else
            headLLE.setElementList(index, value);
    }
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        else
            return headLLE.getElementList(index);
    }
    public void setSize(int size){
        if (size < 0)
        {
            throw new IllegalArgumentException();
        }
        else if (size >= this.size)
        {
            this.size = size;
        }
        else
        {
            this.size = size;
            headLLE.decreaseSize(size);
        }
    }


    private class LogListElement<T> {
        private final int sqPos;
        private final LogArrayElement<T> logArrayElement;
        private LogListElement<T> next;

        public LogListElement(int sqPos){
            this.sqPos = sqPos;
            logArrayElement = new LogArrayElement<>(sqPos);
        }

        public void setElementList(int listIndex, T value)
        {
            if (listIndex + 1 >= logArrayElement.startElmIndex && listIndex + 1 <= logArrayElement.endElmIndex)
            {
                logArrayElement.setElementArray(listIndex, value);
            }
            else if (listIndex + 1 < logArrayElement.startElmIndex)
            {
                throw new IllegalStateException();
            }
            else
            {
                generateNext();
                next.setElementList(listIndex, value);
            }
        }
        public T getElementList(int listIndex)
        {
            if (listIndex + 1 >= logArrayElement.startElmIndex && listIndex + 1 <= logArrayElement.endElmIndex)
            {
                return logArrayElement.getElementArray(listIndex);
            }
            else if (listIndex + 1 < logArrayElement.startElmIndex)
            {
                throw new IllegalStateException();
            }
            else if (next != null)
            {
                return next.getElementList(listIndex);
            }
            else
            {
                return null;
            }
        }
        private void generateNext(){
            if (next == null)
            {
                next = new LogListElement<>(sqPos + 1);
            }
        }
        public int getSqPos() {
            return sqPos;
        }
        public LogListElement<T> getNext() {
            return next;
        }
        public void decreaseSize(int newSize){
            if (newSize <= logArrayElement.endElmIndex - 1)
            {
                next = null;
                logArrayElement.decreaseSize(newSize);
            }
            else if(next != null)
            {
                next.decreaseSize(newSize);
            }
        }

        private class LogArrayElement<T>{
            private final int logPos; //position of LogArrayElement, 0-indicated
            private final int arrCount; //amount in all arrays
            private final int startArrIndex; //absolute index of first array, 1-indicated
            private final int endArrIndex; //absolute index of last array, 1-indicated
            private final int startElmIndex; //absolute index of first element, 1-indicated
            private final int endElmIndex; //absolute index of last element, 1-indicated
            private ArrayWrapper<T>[] arrs;

            public LogArrayElement(int logPos){
                this.logPos = logPos;
                arrCount = (int) Math.pow(2, logPos);
                startArrIndex = arrCount;
                endArrIndex = 2 * startArrIndex - 1;
                startElmIndex = (int) Math.pow(2, startArrIndex - 1);
                endElmIndex = (int) Math.pow(2, endArrIndex) - 1;
                arrs = new ArrayWrapper[arrCount]; //each ArrayWrapper is ONE array
            }

            public void setElementArray(int elementIndex, T value){
                elementIndex++; //1-indicated
                if (elementIndex < startElmIndex || elementIndex > endElmIndex){
                    throw new IllegalStateException();
                }
                int absArrayIndex = (int) Math.floor(Math.log(elementIndex) / Math.log(2)) + 1;
                int localArrayIndex = absArrayIndex - startArrIndex; //+1: startArrIndex is inclusive, -1: index has to be 0-indicated -> +-0
                int absArrayFirstElementIndex = (int) Math.pow(2, absArrayIndex -1);
                int localArrayElementIndex = elementIndex - absArrayFirstElementIndex; //**
                generateArr(localArrayIndex, absArrayIndex);
                arrs[localArrayIndex].setElement(localArrayElementIndex, value);
            }
            public T getElementArray(int elementIndex){
                elementIndex++; //1-indicated
                if (elementIndex < startElmIndex || elementIndex > endElmIndex){
                    throw new IllegalStateException();
                }
                int absArrayIndex = (int) Math.floor(Math.log(elementIndex) / Math.log(2)) + 1;
                int localArrayIndex = absArrayIndex - startArrIndex; //+1: startArrIndex is inclusive, -1: index has to be 0-indicated -> +-0
                int absArrayFirstElementIndex = (int) Math.pow(2, absArrayIndex - 1);
                int localArrayElementIndex = elementIndex - absArrayFirstElementIndex; //**
                if (arrs[localArrayIndex] == null){
                    return null;
                }
                return arrs[localArrayIndex].getElement(localArrayElementIndex);
            }
            public void decreaseSize(int newSize){
                int lastNeededAbsArrayIndex = (int) Math.floor(Math.log(newSize + 1) / Math.log(2)) + 1;
                int lastNeededLocalArrayIndex = lastNeededAbsArrayIndex - startArrIndex;
                for (int i = lastNeededLocalArrayIndex + 1; i < arrs.length; i++)
                {
                    arrs[i] = null;
                }
            }
            public int getStartArrIndex() {
                return startArrIndex;
            }
            public int getEndArrIndex() {
                return endArrIndex;
            }

            private void generateArr(int localArrayIndex, int absArrayIndex){
                if (arrs[localArrayIndex] == null)
                {
                    arrs[localArrayIndex] = new ArrayWrapper<>((int) Math.pow(2, absArrayIndex - 1));
                }
            }
            private class ArrayWrapper<T>{
                private T[] elements;
                private int size;
                public ArrayWrapper(int size){
                    elements = (T[]) new Object[size];
                }
                public int getSize() {
                    return size;
                }
                public void setElement(int localIndex, T value) {
                    elements[localIndex] = value;
                }
                public T getElement(int localIndex) {
                    return elements[localIndex];
                }
            }
        }
    }
}
