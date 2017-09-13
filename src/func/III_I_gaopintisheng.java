package func;
import com.sin.java.waveaccess.WaveFileWriter;

import java.util.Arrays;
import com.sin.java.plot.Plot;
import static func.DVP.*;
import static func.FFT.*;
import static func.Matlab.*;
import static func.Complex.*;
/**
 * Created by daojia on 2017-9-7.
 */
public class III_I_gaopintisheng {
    public static void main(String[] args) {
        int[] f = WaveTxtReader("D:\\pku6\\DigitalVoiceProcessing\\matlab\\code\\voice2.txt");
        double[] ee = IntList2DoubleList(Arrays.copyOfRange(f, 199, 455));
        Complex[] r =  FFT_RC(ee, 1024);
        double[] r1 = FFT_Abs(r);
        double[] pinlv = linspace(0, 255 * 8000.0 / 512, 256);
        double[] yuanlai = AmptitudeLog(r1);
        double[] signal = Arrays.copyOfRange(yuanlai, 0, 256);
        FreqzResult signal1 = Freqz.freqz(new double[]{1,-0.98},new double[]{1},256,4000);
        Complex[] h1 = signal1.getH();
        double[] f1 = signal1.getW();
        double[] pha = angle(h1);
        double[] H1 = FFT_Abs(h1);
        Complex[] r2 = Arrays.copyOfRange(r,0,256);
        Complex[] u = new Complex[256];
        for(int i=0;i<256;i++){
            u[i] = r2[i].times(h1[i].conjugate());
        }
        double[] u2 = FFT_Abs(u);
        double[] u3 = AmptitudeLog(u2);
        double[] un = filter(new double[]{1,-0.98},new double[]{1},ee);
        //Plot.plot(f1,H1);
        Plot.plot(pinlv,signal);
        Plot.plot(pinlv,u3);

//        System.out.println(un.length);
//        for(int i =0;i<10;i++){
//            System.out.println(un[i]);
//        }
//        System.out.println("------------------------");
//        for(int i =250;i<256;i++){
//            System.out.println(un[i]);
//        }
        //WaveFileWriter.saveSingleChannel("D:\\pku6\\voice2.wav",ee,8000);
    }
}
