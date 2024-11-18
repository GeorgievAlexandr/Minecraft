package Functions3D;
import java.util.Random;
public abstract class PerlinNoise {
    private static final Random random = new Random();
    private static final Random random2 = new Random();
    private static int seed = 10;
    private static float smooth = 16;
    private static int bound;

    public static int getNoise(long x, long y){
        //Возвращает значение шума Перлина на данных координатах
        int result;
        smooth = 128;
        bound = 64;
        result = get(x, y);
        smooth = 32;
        bound = 16;
        result += get(x, y);
        return result;
    }

    public static int get(long x, long y) {
        //Возвращает сглаженный шум
        int[][] smoothPoints = new int[4][2];
        smoothPoints[0][0] = (int) Math.floor(x / smooth);
        smoothPoints[0][1] = (int) Math.floor(y / smooth);
        smoothPoints[1][0] = smoothPoints[0][0] + 1;
        smoothPoints[1][1] = smoothPoints[0][1];
        smoothPoints[2][0] = smoothPoints[0][0];
        smoothPoints[2][1] = smoothPoints[1][1] + 1;
        smoothPoints[3][0] = smoothPoints[1][0];
        smoothPoints[3][1] = smoothPoints[2][1];


        int[] randomValues = new int[4];

        for (int i = 0; i < 4; i++) {
            random.setSeed(calculateSeed(smoothPoints[i][0], smoothPoints[i][1]));
            randomValues[i] = random.nextInt(bound);
        }

        float xValue = x / smooth - smoothPoints[0][0];
        float yValue = y / smooth - smoothPoints[0][1];

        int noise = (int) interpolate(interpolate(randomValues[0], randomValues[2], yValue), interpolate(randomValues[1], randomValues[3], yValue), xValue);
        return noise;
    }

    private static long calculateSeed(long x, long y) {
        //Рассчитывает сид для рандома
        random2.setSeed(x * seed);
        long result = random2.nextInt(10000);
        random2.setSeed(y * seed);
        result *= random2.nextLong(10000);
        random2.setSeed(x * seed + y);
        result /= random2.nextLong(1000);
        return result;
    }

    private static float interpolate(float from, float to, float value){
        //Интерполяция
        return (float) (from + (to - from) * (1 - Math.cos(value * 3.14159265358973f)) / 2);
    }
}
