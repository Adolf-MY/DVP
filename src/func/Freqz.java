package func;
import java.util.*;

import static func.Matlab.*;
import static func.DVP.*;

/**
 * Created by daojia on 2017-9-7.
 */
public class Freqz {
    public static FreqzResult freqz(String... args){
        return null;
    }
    public static FreqzResult freqz(double[] b,double[] a,int n,double Fs){
        FreqzOptions Op = Freqz_Options(n,Fs,new double[1],"onesided",false);
        FreqzResult re = new FreqzResult();
        if(a.length==1){
            double[] b1 = new double[b.length];
            for(int i=0;i<b.length;i++){
                b1[i]=b[i]/a[0];
            }
            //System.out.println(b1[0]+" "+b1[1]);
            re = firfreqz(b1,Op);
        }else{
            re = iirfreqz(b,a,Op);

        }
        return re;
    }

    public static FreqzResult firfreqz(double[] b,FreqzOptions Op){
        FreqzResult re = new FreqzResult();
        //System.out.println(b[0]+" "+b[1]);
        int n = b.length;
        double[] w = Op.w;
        double Fs = Op.Fs;
        int nfft = Op.nfft;
        boolean fvflag = Op.fvflag;
        double[] digw = new double[w.length];
        if(fvflag){
            if(Fs>0){
                for(int i=0;i<w.length;i++){
                    digw[i]=2.0*Math.PI*w[i]/Fs;
                }
            }else{
                digw = w;
            }
            Complex[] s = new Complex[w.length];
            Complex[] h = new Complex[w.length];
            for(int i =0;i<w.length;i++){
                s[i] = Complex.powerE(new Complex(0,digw[i]));
                h[i] = polyvalC(b,s[i]).divide(new Complex(0, digw[i] * (n - 1)));
            }
            re.setH(h);
        }else{
            //System.out.println("1111111111");
            int s =0;
            if(Op.range=="twosided"){
                s = 1;
            }else{
                s = 2;
            }
            //System.out.println(s+" "+nfft+" "+n);
            //System.out.println(s*nfft<n);
            if(s*nfft<n){
                double[] b1 = datawrap(b, s * nfft);
                //System.out.println(b.length);
                Complex[] h = Arrays.copyOfRange(FFT.FFT_RC(b1, s * nfft), 0, nfft);
                re.setW(Freqz_Freqvec(nfft, Fs, s));
                re.setH(h);
            }else{
                //System.out.println(b[0]+" "+b[1]);
                Complex[] h = Arrays.copyOfRange(FFT.FFT_RC(b, s * nfft), 0, nfft);
                re.setW(Freqz_Freqvec(nfft, Fs, s));
                re.setH(h);
            }
        }
        //System.out.println(re.getH().length);
        re.setOp(Op);
        return re;
    }

    public static FreqzResult iirfreqz(double[] b,double[] a,FreqzOptions Op){
        FreqzResult re = new FreqzResult();
        int nb = b.length;
        int na = a.length;
        int n = Math.max(nb,na);
        double[] bb = new double[n];
        double[] aa = new double[n];
        for(int i=0;i<n;i++){
            if(i<nb){
                bb[i]=b[i];
            }else{
                bb[i]=0;
            }
            if(i<na){
                aa[i]=a[i];
            }else{
                aa[i]=0;
            }
        }

        double[] w = Op.w;
        double Fs = Op.Fs;
        int nfft = Op.nfft;
        boolean fvflag = Op.fvflag;
        double[] digw = new double[w.length];
        if(fvflag){
            if(Fs>0){
                for(int i=0;i<w.length;i++){
                    digw[i]=2.0*Math.PI*w[i]/Fs;
                }
            }else{
                digw = w;
            }
            Complex[] s = new Complex[w.length];
            Complex[] h = new Complex[w.length];
            for(int i =0;i<w.length;i++){
                s[i] = Complex.powerE(new Complex(0,digw[i]));
                h[i] = polyvalC(bb,s[i]).divide(polyvalC(aa,s[i]));
            }
            re.setH(h);
        }else{
            int s =0;
            if(Op.range=="twosided"){
                s = 1;
            }else{
                s = 2;
            }
            if(s*nfft<n){
                Complex[] b1 = FFT.FFT_RC(datawrap(b, s * nfft),s * nfft);
                Complex[] a1 = FFT.FFT_RC(datawrap(a, s * nfft),s * nfft);
                Complex[] h = new Complex[nfft];
                for(int i=0;i<nfft;i++){
                    h[i] = b1[i].divide(a1[i]);
                }
                re.setW(Freqz_Freqvec(nfft, Fs, s));
                re.setH(h);
            }else{
                Complex[] b1 = FFT.FFT_RC(b,s * nfft);
                Complex[] a1 = FFT.FFT_RC(a,s * nfft);
                Complex[] h = new Complex[nfft];
                for(int i=0;i<nfft;i++){
                    h[i] = b1[i].divide(a1[i]);
                }
                re.setW(Freqz_Freqvec(nfft,Fs,s));
                re.setH(h);
            }
        }
        re.setOp(Op);
        return re;
    }

    public static  double[] Freqz_Freqvec(int nfft,double Fs,int s){
        Double F = Fs;
        Integer S = s;
        if(F.equals(0.0)){
            F=2*Math.PI;
        }
        if(S.equals(0)){
            S = 2;
        }
        //System.out.println(F + " " + S);
        if(S.equals(1)){
            double deltaF = F*1.0/nfft;
            double[] w = linspace(0,F-deltaF,nfft);
            if(nfft%2==1){
                w[(nfft+1)/2-1]=F/2-F/(2*nfft);
                w[(nfft+1)/2]=F/2+F/(2*nfft);
            }else{
                w[nfft/2] = F/2;
            }
            w[nfft-1]=F-F/nfft;
            return w;

        }else if(S.equals(2)){
            double deltaF = F/2.0/nfft;
            double[] w = linspace(0,F/2-deltaF,nfft);
            w[nfft-1]=F/2-F/2.0/nfft;
            return w;
        }else if(S.equals(3)) {
            double deltaF = F / nfft;
            double wmin = 0;
            double wmax = 0;
            if (nfft % 2 == 1) {
                wmin = -1.0 * (F - deltaF) / 2;
                wmax = (F - deltaF) / 2;
            } else {
                wmin = -1.0 * F / 2;
                wmax = F / 2 - deltaF;
            }
            double[] w = linspace(wmin, wmax, nfft);
            if (nfft % 2 == 1) {
                w[(nfft + 1) / 2 - 1] = 0;
            } else {
                w[nfft / 2] = 0;
            }
            return w;
        }
        return null;
    }
    public static FreqzOptions Freqz_Options(int nfft,double Fs,double[] w,String range,Boolean fvflag){
        //   options.nfft         - number of freq. points to be used in the computation
        //   options.fvflag       - Flag indicating whether nfft was specified or a vector was given
        //   options.w            - frequency vector (empty if nfft is specified)
        //   options.Fs           - Sampling frequency (empty if no Fs specified)
        //   options.range        - 'half' = [0, Nyquist); 'whole' = [0, 2*Nyquist)


        FreqzOptions re = new FreqzOptions();
        //Ä¬ÈÏfft=512;range=onesided;fvflag=0
        re.setNfft(nfft);
        re.setFs(Fs);
        re.setW(w);
        re.setRange(range);
        re.setFvflag(fvflag);
        return re;
    }
    public static void main(String[] args) {
//        double[][] a = buffer(new double[]{1.0,2.0,3.0},);
//        for(int i=0;i<a.length;i++){
//            for(int j=0;j<a[0].length;j++){
//                System.out.print(a[i][j]+"  ");
//            }
//            System.out.println("");
//        }
//        double[] a = Freqz_Freqvec(10,1,3);
//        for(int i=0;i<a.length;i++) {
//            System.out.print(a[i] + "  ");
//        }

        }

}
