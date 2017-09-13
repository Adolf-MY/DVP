package func;

import java.util.Map;

/**
 * Created by daojia on 2017-9-6.
 *
 */
public class Complex {
    private double a, b;//复数的实部和虚部

    public double getImag() {
        return b;
    }

    public void setImag(double b) {
        this.b = b;
    }

    public double getReal() {
        return a;
    }

    public void setReal(double a) {
        this.a = a;
    }

    //private final double delta=1e-20;
    public Complex(double a, double b) {
        this.a = a;
        this.b = b;

    }

    public void Change(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        if (b > 0.0) {
            if (a == 0.0)
                return b + "*i";
            else
                return a + "+" + b + "*i";
        } else if (b < 0.0) {
            if (a == 0.0)
                return b + "*i";
            else
                return a + "" + b + "*i";//注意
        } else
            return a + "";//要加"",这样就可以返回一个String类型的值了
    }

    public Complex plus(Complex Z) {
        double aa = this.a + Z.a;
        double bb = this.b + Z.b;
        return new Complex(aa, bb);
    }

    public Complex minus(Complex Z) {
        double aa = this.a - Z.a;
        double bb = this.b - Z.b;
        return new Complex(aa, bb);
    }

    public Complex times(Complex Z) {
        double aa = this.a * Z.a - this.b * Z.b;
        double bb = this.b * Z.a + this.a * Z.b;
        return new Complex(aa, bb);
    }
    public Complex timesR(double Z) {
        double aa = this.a * Z;
        double bb = this.b * Z;
        return new Complex(aa, bb);
    }

    public Complex divide(Complex Z) {
        Z.Change(Z.a, -Z.b);
        Complex ZZ = this.times(Z);
        //System.out.println(ZZ.a+" "+ZZ.b);
        ZZ.a /= (Z.a * Z.a + Z.b * Z.b);
        ZZ.b /= (Z.a * Z.a + Z.b * Z.b);
        return ZZ;
    }
    public static Complex powerE(Complex Z){
        return new Complex(Math.pow(Math.E,Z.a)*Math.cos(Z.b),Math.pow(Math.E,Z.a)*Math.sin(Z.b));
    }
    public Complex power(int n){
        if(n==0){
            return new Complex(1,0);
        }else {
            Complex re = new Complex(this.a, this.b);
            for (int i = 1; i < n; i++) {
                re = this.times(re);
            }
            return re;
        }
    }

    public Complex conjugate(){
        return new Complex(this.a,-1*this.b);
    }
    public double abs(){
        return Math.sqrt(this.a*this.a+this.b*this.b);
    }
    public static double[] real(Complex[] x){
        double[] re = new double[x.length];
        for(int i=0;i<x.length;i++){
            re[i] = x[i].getReal();
        }
        return re;
    }
    public static double[] imag(Complex[] x){
        double[] re = new double[x.length];
        for(int i=0;i<x.length;i++){
            re[i] = x[i].getImag();
        }
        return re;
    }
    public static double[] abs(Complex[] x){
        double[] re = new double[x.length];
        for(int i=0;i<x.length;i++){
            re[i] = x[i].abs();
        }
        return re;
    }
    public static void main(String[] args){
        Complex r = new Complex(1,2);
        System.out.println(r.power(0).toString());
    }
}