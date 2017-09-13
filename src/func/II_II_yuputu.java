package func;

import com.sin.java.waveaccess.WaveFileReader;
import static  func.DVP.*;

/**
 * Created by daojia on 2017-9-6.
 */
public class II_II_yuputu {
    public static void main(String[] args){
        WaveFileReader x = new WaveFileReader("D:\\pku6\\DigitalVoiceProcessing\\matlab\\code\\beijing.wav");
        long sr = x.getSampleRate();
        int s = x.getDataLen();
        int w = (int)Math.round(44.0*sr/1000);
        int ov = w/2;
        int h = w-ov;
        double[] win = hamming(w);
        System.out.println(win.length);

        for(int i=0;i<10;i++) {
            System.out.println(win[i]);
        }
        System.out.println("---------------------");
        for(int i=345;i<352;i++) {
            System.out.println(win[i]);
        }
    }
}
