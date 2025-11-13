
public class Main {
    public static void main(String[] args) {
        AdvancedWintersList<Integer> awl = new AdvancedWintersList<>(1001);
        for (int i = 0; i <= 1000; i++)
        {
            awl.set(i, Integer.valueOf(1000 + i));
        }
        awl.setSize(500);
        for (int i = 0; i < 500; i++)
        {
            System.out.println(awl.get(i));
        }
    }


}

