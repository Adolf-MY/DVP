package func;

import com.sin.java.plot.Plot;

import static func.DVP.IntList2DoubleList;
import static func.DVP.WaveTxtReader;
import static func.Matlab.*;

/**
 * Created by daojia on 2017-9-13.
 */
public class III_VI_guoling {
    public static void main(String[] args) {
        double[] x1 = IntList2DoubleList(WaveTxtReader("D:\\pku6\\DigitalVoiceProcessing\\matlab\\code\\beijing.txt"));

        double[][] x = AWGN.awgn(AWGN.doubleToObject(x1),15,0,"db","measured","real").getRealM();
        double[][] s = fra(220,110,x[0]);
        double[] zcr = zcro(s);
        Plot.figrue(1);
        Plot.plot(x[0]);
        Plot.figrue(2);
        Plot.plot(zcr);
//        System.out.println(x.length);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(x[0][i]);
//        }
//        System.out.println("------------------------");
//        for (int i = 39750; i < 39760; i++) {
//            System.out.println(x[0][i]);
//        }
    }
}
