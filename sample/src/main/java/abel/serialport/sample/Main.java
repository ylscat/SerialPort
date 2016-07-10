package abel.serialport.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import abel.serialport.SerialPort;

/**
 * Created at 2016/6/3.
 *
 * @author YinLanShan
 */
public class Main extends Activity implements Runnable {
    private static final int RECEIVE = 0;
    private static final String DEVICE = "/dev/ttySAC3";
    private TextView mText;
    private FileDescriptor mFileDescriptor;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mText = new TextView(this);
        setContentView(mText);
        mFileDescriptor = SerialPort.open(DEVICE, 57600, 0);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        if(mFileDescriptor == null) {
            mText.setText("Fail to open " + DEVICE);
            return;
        }
        Thread t = new Thread(this);
        t.start();
        thread = t;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mFileDescriptor != null)
            SerialPort.close(mFileDescriptor);
        mFileDescriptor = null;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        FileInputStream inputStream = new FileInputStream(mFileDescriptor);
        FileOutputStream outputStream = new FileOutputStream(mFileDescriptor);
        byte[] buf = new byte[256];

        while (mFileDescriptor != null) {
            try {
                int len = inputStream.read(buf);
                Log.d("Rx", String.valueOf(len));
                Message msg = mHandler.obtainMessage(RECEIVE, format(buf, len));
                mHandler.sendMessage(msg);
                outputStream.write(String.valueOf(len).getBytes());
            }
            catch (IOException e) {
                Log.d("SerialPort", "error whilre read", e);
            }
        }
    }

    private String format(byte[] buf, int len) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++) {
            sb.append(String.format("%02X ", buf[i]));
        }

        return sb.toString();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVE:
                    mText.append(String.valueOf(msg.obj));
                    mText.append("\n");
                    break;
            }
        }
    };
}
