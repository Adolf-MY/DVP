package func;

import java.util.Objects;
import java.util.Random;

/**
 * Created by daojia on 2017-9-14.
 */
public class AWGN {
    public static Object[][] wgn(int m,int n,double p,int imp,String pMode,String OpType){
        //д╛хо pMode='dbw' imp=1 OpType='real'
        int row = m;
        int col = n;
        double noisePower = 0;
        if(pMode.equals("linear")){
            noisePower = p;
        }else if(pMode.equals("dbw")){
            noisePower = Math.pow(10,p/10);
        }else if(pMode.equals("dbm")){
            noisePower = Math.pow(10,(p-30)/10);
        }
        System.out.println(noisePower);
        Object[][] r = new Object[row][col];
        Random ra = new Random(1);
        if(OpType.equals("real")){
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    r[i][j] = Math.sqrt(imp*noisePower)*(ra.nextGaussian());
                }
            }
        }else if(OpType.equals("complex")){
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    r[i][j] = new Complex(Math.sqrt(imp*noisePower/2)*ra.nextGaussian(),ra.nextGaussian());
                }
            }
        }
        return r;
    }

    public static wgnResult awgn(Object[][] sig,double reqSNR,double sigpower,String pMode,String measMode,String OpType){
        //sigpower=0;pMode='db';measMode='specify'
        int n = sig.length;
        int c = sig[0].length;
//        String OpType = "real";
//        if(sig[0][0].getClass()==func.Complex.class){
//            OpType = "complex";
//        }
        if(measMode.equals("measured")){
            sigpower = 0;
            for(int i=0;i<n;i++){
                for(int j=0;j<c;j++){
                    if(OpType.equals("real")) {
                        sigpower = sigpower + Math.abs(Math.pow((double)sig[i][j], 2));
                    }else{
                        sigpower = sigpower + ((Complex) sig[i][j]).power(2).abs();
                    }
                }
            }
            sigpower = sigpower/(n*c);
            if(pMode.equals("db")){
                sigpower = 10*Math.log10(sigpower);
            }
        }
        double noisePower = 0;
        if(pMode.equals("linear")){
            noisePower = sigpower/reqSNR;
        }else if(pMode.equals("db")){
            noisePower = sigpower - reqSNR;
            pMode = "dbw";
        }
        wgnResult re = new wgnResult();
        if(OpType.equals("real")){
            re.setOptype(0);
            double[][] rre = new double[n][c];
            Object[][] rre1 = wgn(n,c,noisePower,1,pMode,OpType);
            for(int i=0;i<n;i++){
                for(int j=0;j<c;j++){
                    rre[i][j]=(double)sig[i][j]+(double)rre1[i][j];
                }
            }
            re.setRealM(rre);
        }else{
            re.setOptype(1);
            Complex[][] rre = new Complex[n][c];
            Object[][] rre1 = wgn(n,c,noisePower,1,pMode,OpType);
            for(int i=0;i<n;i++){
                for(int j=0;j<c;j++){
                    rre[i][j]=((Complex)sig[i][j]).plus((Complex)rre1[i][j]);
                }
            }
            re.setComplexM(rre);
        }
        return re;
    }
    public static class wgnResult{
        public int optype;//0 real 1 complex
        public double[][] realM;
        public Complex[][] complexM;

        public int getOptype() {
            return optype;
        }

        public void setOptype(int optype) {
            this.optype = optype;
        }

        public double[][] getRealM() {
            return realM;
        }

        public void setRealM(double[][] realM) {
            this.realM = realM;
        }

        public Complex[][] getComplexM() {
            return complexM;
        }

        public void setComplexM(Complex[][] complexM) {
            this.complexM = complexM;
        }
    }
    public static Object[][] doubleToObject(double[] x){
        Object[][] re = new Object[1][x.length];
        for(int i=0;i<x.length;i++){
            re[0][i] = x[i];
        }
        return re;
    }



    public static void main(String[] args) {
        //Object[][] a = wgn(3,2,1,1,"dbw","complex");
        Object[][] a = doubleToObject(new double[]{1,2,3});
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                System.out.print(a[i][j].getClass()==double.class);
            }
            System.out.println(" ");
        }
    }
}
