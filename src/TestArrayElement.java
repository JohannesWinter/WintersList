public class TestArrayElement <T> {
        private final int logPos; //position of LogArrayElement, 0-indicated
        private final int arrCount; //amount in all arrays
        private final int startArrIndex; //absolute index of first array, 1-indicated
        private final int endArrIndex; //absolute index of last array, 1-indicated
        private final int startElmIndex; //absolute index of first element, 1-indicated
        private final int endElmIndex; //absolute index of last element, 1-indicated
        private ArrayWrapper<T>[] arrs;

        public TestArrayElement(int logPos){
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
            int absArrayFirstElementIndex = (int) Math.pow(2, absArrayIndex - 1);
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
        private void generateArr(int localArrayIndex, int absArrayIndex){
            if (arrs[localArrayIndex] == null)
            {
                arrs[localArrayIndex] = new ArrayWrapper<T>((int) Math.pow(2, absArrayIndex - 1));
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