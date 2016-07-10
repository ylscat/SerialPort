package abel.serialport;

import java.io.FileDescriptor;

/**
 * Created at 2016/6/3.
 *
 * @author YinLanShan
 */
public class SerialPort {
    public native static FileDescriptor open(String path, int baudrate, int flags);

    public native static void close(FileDescriptor descriptor);

    static
    {
        System.loadLibrary("serial_port");
    }
}
