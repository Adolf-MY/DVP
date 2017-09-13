package func;

import com.sin.java.plot.Plot;
import com.sin.java.waveaccess.WaveFileWriter;

import static func.DVP.*;
import static func.FFT.*;
import static func.Matlab.*;
import static func.Complex.*;

/**
 * Created by daojia on 2017-9-13.
 */
public class III_IV_nengling {
    public static void main(String[] args) {
        double[] x = IntList2DoubleList(WaveTxtReader("D:\\pku6\\DigitalVoiceProcessing\\matlab\\code\\zqq1.txt"));
        double[][] s = fra(50,50,x);
        double[][] s2 = new double[s.length][s[0].length];
        double[] e = new double[s.length];
        for(int i=0;i<s.length;i++){
            double ee = 0;
            for(int j=0;j<s[0].length;j++){
                ee = ee+s[i][j]*s[i][j];
            }
            e[i]=ee;
        }
        Plot.plot(e);


        System.out.println(s.length+" "+s[0].length);
        for(int i=0;i<10;i++){
            System.out.println(s[2][i]);
        }
        System.out.println("-----------");
        for(int i=0;i<10;i++){
            System.out.println(s[3][i]);
        }

       // WaveFileWriter.saveSingleChannel("D:\\zqq.wav",x,8000);
    }
}
