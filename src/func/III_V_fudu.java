package func;

import com.sin.java.plot.Plot;

import static func.DVP.IntList2DoubleList;
import static func.DVP.WaveTxtReader;
import static func.Matlab.fra;

/**
 * Created by daojia on 2017-9-13.
 */
public class III_V_fudu {
    public static void main(String[] args) {
        double[] x = IntList2DoubleList(WaveTxtReader("D:\\pku6\\DigitalVoiceProcessing\\matlab\\code\\zqq1.txt"));
        double[][] s = fra(50, 50, x);
        double[][] s2 = new double[s.length][s[0].length];
        double[] e = new double[s.length];
        for (int i = 0; i < s.length; i++) {
            double ee = 0;
            for (int j = 0; j < s[0].length; j++) {
                ee = ee + Math.abs(s[i][j]);
            }
            e[i] = ee;
        }
        Plot.plot(e);
    }
}
