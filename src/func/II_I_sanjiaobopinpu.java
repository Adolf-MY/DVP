package func;

import com.sin.java.plot.Plot;
import static func.FFT.*;
import java.util.ArrayList;
import java.util.Map;

import static func.DVP.*;
/**
 * Created by daojia on 2017-9-5.
 */
public class II_I_sanjiaobopinpu {
    public static void main(String[] args){
        double[] n = linspace(0, 25, 125);
        double[] g = zeros(n.length);
        for(int i = 0;i<=40;i++){
            if(n[i]<=5){
                g[i]=0.5*(1-Math.cos(n[i]*Math.PI/5));
            }else{
                g[i]=Math.cos((n[i]-5)*Math.PI/8);
            }
        }
        Plot.figrue(1);
        Plot.plot(n, g);
        Plot.axis(0, 25, -0.4, 1.2);
        Complex[] r = FFT_RC(g,1024);
        double[] r1 = new double[1024];
        for(int i=0;i<64;i++){
            r1[i] = 20*Math.log10(r[i].abs());
        }
        double[] pinlv = linspace(0,63.0*8000/512,64);
        for(int i=0;i<64;i++){
            System.out.println(pinlv[i]);
        }
        Plot.figrue(2);
        Plot.plot(pinlv,r1);
        Plot.axis(0,620,0,30);
    }

}
