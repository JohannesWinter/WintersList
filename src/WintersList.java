public class WintersList<T> {
    private class LogListElement<T> {
        private final T[] elements;
        private final int position;
        private final int elementCount;
        private final int from;
        private final int to;
        private LogListElement<T> next;

        public LogListElement(int position){
            this.position = position;
            elementCount = (int) Math.pow(2, position + 1) - 1;
            from = (int) Math.pow(2, position);
            to = from * 2;
            elements = (T[]) new Object[elementCount];
        }
    }
}
