package func;

/**
 * Created by daojia on 2017-9-7.
 */
public class FreqzResult {
    public Complex[] H;
    public FreqzOptions Op;
    public double[] w;

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public Complex[] getH() {
        return H;
    }

    public void setH(Complex[] h) {
        H = h;
    }

    public FreqzOptions getOp() {
        return Op;
    }

    public void setOp(FreqzOptions op) {
        Op = op;
    }
}
