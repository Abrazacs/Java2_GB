import java.util.Arrays;

public class ThreadTest {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        System.out.println(Arrays.equals(oneThreadTest(),twoThreadTest()));
    }

    private static float[] oneThreadTest() {
        float [] array = new float [size];
        Arrays.fill(array,1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i]= (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis()-a);
        return array;
    }

    private static float[] twoThreadTest() {
        float [] array = new float [size];
        Arrays.fill(array,1);
        long a = System.currentTimeMillis();
        float[]a1 = new float[h];
        float[]a2 = new float[h];
        System.arraycopy(array, 0, a1, 0, h);
        System.arraycopy(array, h, a2, 0, h);
        Thread t1 = new Thread(()-> {
            for (int i = 0; i < a1.length; i++) {
            a1[i]=(float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread t2 = new Thread(()-> {
            int i = h;
            for (int j =0; j < a2.length; j++) {
                a2[j]=(float)(array[j] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                i++;
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            t1.interrupt();
            t2.interrupt();
        }
        System.arraycopy(a1,0,array,0,h);
        System.arraycopy(a2,0,array,h,h);
        System.out.println(System.currentTimeMillis()-a);
        return array;
    }

}
