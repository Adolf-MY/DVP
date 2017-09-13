package func;

import java.io.File;
import java.io.FileInputStream;
import java.io.*;
import java.util.*;

/**
 * Created by daojia on 2017-9-5.
 * linspace 形成平均序列
 * zeros 形成零序列
 * zerosM 形成零矩阵
 * onesX 形成one*a序列
 * OnesXM 形成one或者eye零矩阵
 * calc_window 窗函数计算公式
 * sym_window 计算窗函数
 * hamming 汉明窗
 * hanning 汉宁窗
 * WaveTxtReader 读取txt格式语音文件
 * DoubleList2IntList double序列变int序列
 * IntList2DoubleList int序列变double序列
 * AmptitudeLog 振幅计算
 * buffer 形成buffer矩阵
 * datawrap buffer求和公式
 * filter 差分公式
 *
 */


public class DVP {
    public static double[] linspace(double a,double b,int c){
        double[] re = new double[c];
        re[0]=a;
        for(int i=1;i<c-1;i++){
            re[i]=(a+(b-a)/(c-1)*i);
        }
        re[c-1]=b;
        return re;
    }
    public static double[] zeros(int c){
        double[] re = new double[c];
        return re;
    }
    public static double[][] zerosM(int r,int c){
        double[][] re = new double[r][c];
        return re;
    }
    public static double[] OnesX(int c,int a){
        double[] re = new double[c];
        for(int i =0;i<c;i++){
            re[i]=a;
        }
        return re;
    }
    public static double[][] OnesXM(int r,int c,Integer type,int a){
        //type = 0 one;type =1 eye
        double[][] re = new double[r][c];
        if(type.equals(0)){
            for(int i =0;i<r;i++){
                for(int j=0;j<c;j++){
                    re[i][j]=a;
                }
            }
        }else{
            for(int i=0;i<Math.min(r,c);i++){
                re[i][i]=a;
            }

        }
        return re;
    }
    public static double[] calc_window(int m,int n,String w){
        double[] x = linspace(0,(m-1)*1.0/(n-1),m);
        double[] re = new double[m];
        if(w.equals("hann")){
            for(int i=0;i<m;i++){
                re[i] = 0.5-0.5*Math.cos(2*Math.PI*x[i]);
            }
        }else if(w.equals("hamming")){
            for(int i=0;i<m;i++){
                re[i] = 0.54-0.46*Math.cos(2*Math.PI*x[i]);
            }
        }else if(w.equals("blackman")){
            for(int i=0;i<m;i++){
                re[i] = 0.42-0.5*Math.cos(2*Math.PI*x[i])+0.8*Math.cos(4*Math.PI*x[i]);
            }
        }else if(w.equals("flattopwin")){
            for(int i=0;i<m;i++){
                re[i] = 0.21557895-0.41663158*Math.cos(2*Math.PI*x[i])+0.277263158*Math.cos(4*Math.PI*x[i])
                        -0.083578947*Math.cos(6*Math.PI*x[i])+0.006947368*Math.cos(8*Math.PI*x[i]);
            }
        }
        return re;
    }
    public static double[] sym_window(Integer n,String w){
        double[] r = new double[n];
        if(n%2==0){
            int half = n/2;
            double[] re = calc_window(half,n,w);
            for(int i=0;i<half;i++){
                r[i] = re[i];
                r[n-1-i]=re[i];
            }
        }else{
            int half = (n+1)/2;
            double[] re = calc_window(half,n,w);
            for(int i=0;i<half;i++){
                r[i]=re[i];
                r[n-1-i]=re[i];
            }
        }
        return r;
    }
    public static double[] hamming(int n){
        return sym_window(n,"hamming");
    }
    public static double[] hanning(int n){
        double[] r = new double[n];
        int half;
        if(n%2==0){
            half = n/2;
        }else{
            half = (n+1)/2;
        }
        double[] x = linspace(1.0/(n+1),half*1.0/(n+1),half);
        for(int i=0;i<half;i++){
            double a= 0.5-0.5*Math.cos(2*Math.PI*x[i]);
            r[i]=a;
            r[n-1-i]=a;
        }
        return r;
    }
    public static int[] WaveTxtReader(String pathname){
        File filename = new File(pathname);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            List<Integer> lineList = new ArrayList<>();
            String line = "";
           // int w =0;
            while((line=br.readLine())!=null){
                //System.out.println(w + " " + line.trim());
                lineList.add(Integer.parseInt(line.trim()));
               // w = w + 1;
            }
            int[] re = new int[lineList.size()];
            for(int i=0;i<lineList.size();i++){
                re[i]=lineList.get(i);
            }
            return re;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static int[] DoubleList2IntList(double[] a){
        int[] b = new int[a.length];
        for(int i =0;i<a.length;i++){
            b[i]=(int)a[i];
        }
        return b;
    }
    public static double[] IntList2DoubleList(int[] a){
        double[] b = new double[a.length];
        for(int i =0;i<a.length;i++){
            b[i]=(int)a[i];
        }
        return b;
    }
    public static double[] AmptitudeLog(double[] x){
        double[] r = new double[x.length];
        for(int i=0;i<x.length;i++){
            r[i] = 20*Math.log10(x[i]);
        }
        return r;
    }
    public static double[][] buffer(double[] x,int n){
        double[][] re = new double[n][(int)Math.ceil(x.length*1.0/n)];
        int c = 0;
        for(int i=0;i<(int)Math.ceil(x.length*1.0/n);i++){
            for(int j=0;j<n;j++){
                if(c<x.length){
                    re[j][i]=x[c];
                    c=c+1;
                }else{
                    re[j][i]=0;
                }
            }
        }
        return re;
    }
    public static double[] datawrap(double[] x,int n){
        double[] re = new double[n];
        double[][] r = buffer(x, n);
        for(int i =0;i<n;i++){
            re[i] = 0;
            for(int j=0;j<(int)Math.ceil(x.length*1.0/n);j++){
                re[i]=re[i]+r[i][j];
            }
        }
        return re;
    }
    public static double[] filter(double[] b,double[] a,double[] x){
        double[] re = new double[x.length];
        int n = Math.max(b.length,a.length);
        double[] b1 = new double[n];
        double[] a1 = new double[n];
        for(int i=0;i<n;i++){
            if(i<b.length){
                b1[i]=b[i];
            }else{
                b1[i]=0;
            }
            if(i<a.length){
                a1[i]=a[i];
            }else{
                a1[i]=0;
            }
        }
        for(int i=0;i<x.length;i++){
            re[i]=0;
            for(int j=0;j<Math.min(i+1,n);j++){
                re[i]=re[i]+b1[j]*x[i-j];
                if(j>0){
                    re[i]=re[i]-a1[j]*re[i-j];
                }
            }
        }
        return re;
    }
    public static void main(String[] args){
//        double[][] a = buffer(new double[]{1.0,2.0,3.0},);
//        for(int i=0;i<a.length;i++){
//            for(int j=0;j<a[0].length;j++){
//                System.out.print(a[i][j]+"  ");
//            }
//            System.out.println("");
//        }
        double[] a = datawrap(new double[]{1.0, 2.0, 3.0}, 5);
        for(int i=0;i<a.length;i++) {
            System.out.print(a[i] + "  ");
        }
    }
}
