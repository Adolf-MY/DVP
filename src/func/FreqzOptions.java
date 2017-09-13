package func;

/**
 * Created by daojia on 2017-9-7.
 */
public class FreqzOptions {
    public double[] w;
    public double Fs;
    public int nfft = 512;
    public boolean fvflag;
    public String range;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double getFs() {
        return Fs;
    }

    public void setFs(double fs) {
        Fs = fs;
    }

    public int getNfft() {
        return nfft;
    }

    public void setNfft(int nfft) {
        this.nfft = nfft;
    }

    public boolean isFvflag() {
        return fvflag;
    }

    public void setFvflag(boolean fvflag) {
        this.fvflag = fvflag;
    }
}
