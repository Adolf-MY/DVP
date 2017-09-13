package func;

import java.util.Arrays;

/**
 * Created by daojia on 2017-9-6.
 */
public class FFT {

    // compute the FFT of x[], assuming its length is a power of 2
    public static Complex[] fft(Complex[] x) {
        int N = x.length;;
        // base case
        if (N == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }


    // compute the inverse FFT of x[], assuming its length is a power of 2
    public static Complex[] ifft(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(new Complex(1.0 / N, 0));
        }

        return y;

    }

    // compute the circular convolution of x and y
    public static Complex[] cconvolve(Complex[] x, Complex[] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length) { throw new RuntimeException("Dimensions don't agree"); }

        int N = x.length;

        // compute FFT of each sequence
        Complex[] a = fft(x);
        Complex[] b = fft(y);

        // point-wise multiply
        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].times(b[i]);
        }

        // compute inverse FFT
        return ifft(c);
    }


    // compute the linear convolution of x and y
    public static Complex[] convolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2*x.length];
        for (int i = 0;        i <   x.length; i++) a[i] = x[i];
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        for (int i = 0;        i <   y.length; i++) b[i] = y[i];
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }

    // display an array of Complex numbers to standard output
    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println();
    }
    public static Complex[] FFT_CC(Complex[] x,int n){
        if(n>x.length){
            Complex[] y = new Complex[n];
            for(int i=0;i<n;i++){
                if(i<x.length) {
                    y[i] = x[i];
                }
                else{
                    y[i]=new Complex(0,0);
                }
            }
            return fft(y);
        }else if(n==x.length){
            return fft(x);
        }else{
            Complex[] y = new Complex[n];
            for(int i=0;i<n;i++){
                    y[i] = x[i];
            }
            return fft(y);
        }
    }
    public static Complex[] FFT_RC(double[] x,int n){
        Complex[] y = new Complex[x.length];
        for(int i=0;i<x.length;i++){
            y[i]=new Complex(x[i],0);
        }
        return FFT_CC(y,n);
    }
    public static double[] FFT_Abs(Complex[] x){
        double[] re = new double[x.length];
        for(int i=0;i<x.length;i++){
            re[i]=x[i].abs();
        }
        return re;
    }
    public static void main(String[] args) {
        int N = 8;
        Complex[] x = new Complex[8];

        // original data
        for (int i = 0; i < N; i++) {
            x[i] = new Complex(i, 0);
        }
        show(x, "x");

        // FFT of original data
        Complex[] y = FFT_CC(x, 8);
        show(y, "y = fft(x)");
        double[] x1 = {0,1,2,3,4,5,6,7};
        Complex[] z = FFT_RC(x1,8);
        show(z,"RC");
    }

}
