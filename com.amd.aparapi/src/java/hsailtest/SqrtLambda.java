package hsailtest;

import com.amd.aparapi.AparapiException;
import com.amd.aparapi.Device;
import java.util.function.IntConsumer;


public class SqrtLambda{

    static void dump(String type, double[] in, double[] out) {
        System.out.print(type + " ->");
        for (int i = 0; i < in.length; i++) {
            if(i>0){
               System.out.print(",");
            }
            System.out.print("(" + in[i] + "," + out[i] + ")");
        }
        System.out.println();
    }

    public static void main(String[] args) throws AparapiException {
        final int len = Runtime.getRuntime().availableProcessors()*3;
        double in[] = new double[len];
        double out[] = new double[len];
        IntConsumer ic = gid -> {
            in[gid] = gid;
            out[gid] = Math.sqrt(in[gid]);
        };
        Device.hyb().forEach(len, ic);
        dump("hyb", in, out);
        Device.jtp().forEach(len, ic);
        dump("jtp", in, out);
        Device.seq().forEach(len, ic);
        dump("seq", in, out);
    }
}
