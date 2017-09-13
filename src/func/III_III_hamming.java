package func;
import java.util.Arrays;
import com.sin.java.plot.Plot;
import static func.DVP.*;
import static func.FFT.*;
import static func.Matlab.*;
import static func.Complex.*;
/**
 * Created by daojia on 2017-9-13.
 */
public class III_III_hamming {
    public static void main(String[] args) {
        double[] x = linspace(20, 80, 61);
        double[] h = hamming(61);
        Plot.figrue(1);
        Plot.plot(h);

        double[] w1 = hamming(61);
//        for(int i = 0;i<61;i++){
//            System.out.println(w[i]);
//        }
        Complex[] w2 = FFT_RC(w1,1024);
        Complex[] w3 = new Complex[1024];
        for(int i=0;i<1024;i++){
            w3[i] = w2[i].divide(w2[0]);
        }
        double[] w4 = AmptitudeLog(abs(w3));
        double[] w = linspace(0,1023*2.0/1024,1024);
        Plot.figrue(2);
        Plot.plot(w, w4);
        Plot.axis(0,1,-100,0);
//        System.out.println(w3.length);
//        for(int i =0;i<10;i++){
//            System.out.println(w[i]);
//        }
//        System.out.println("------------------------");
//        for(int i =1020;i<1024;i++){
//            System.out.println(w[i]);
//        }
    }
}
