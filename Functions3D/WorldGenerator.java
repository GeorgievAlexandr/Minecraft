package Functions3D;
//Генирирует мир
public abstract class WorldGenerator {
    public static Chunk[] chunks = new Chunk[4];
    public static void generate() {
        int chunksNumber = 0;
        for(long x = 0; x < 32; x += 16){
            for(long y = 0; y < 32; y += 16){
                chunks[chunksNumber] = new Chunk(new long[]{x, 0, y});
                chunksNumber++;
            }
        }
    }
}
