package func;

import com.sin.java.plot.Plot;
import com.sin.java.waveaccess.WaveFileWriter;

import static func.DVP.*;
import static func.FFT.*;
import static func.Matlab.*;
import static func.Complex.*;

/**
 * Created by daojia on 2017-9-13.
 */
public class III_IV_nengling {
    public static void main(String[] args) {
        double[] x = IntList2DoubleList(WaveTxtReader("D:\\pku6\\DigitalVoiceProcessing\\matlab\\code\\zqq1.txt"));

        //WaveFileWriter.saveSingleChannel("D:\\zqq.wav",x,9000);
    }
}
