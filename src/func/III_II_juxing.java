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
public class III_II_juxing {
    public static void main(String[] args) {
        double[] x = linspace(0, 100, 10001);
        double[] h = zeros(10001);
        for (int i = 2001; i < 8003; i++) {
            h[i]=1;
        }
        Plot.figrue(1);
        Plot.plot(h);
        Plot.axis(-500,10500,-0.5,1.5);

        double[] w1 = OnesX(61,1);
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
