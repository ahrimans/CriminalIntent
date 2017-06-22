package com.example.ahrimans.poorwater;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private TextView tv_numInput, tv_numOutput;
    private ProgressBar bar = null;

    private ArrayList<ValveThread> InputThrds = null;
    private ArrayList<ValveThread> OutputThrds = null;
    private CountThread thread_count;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ValveThread.WATER_INPUT || msg.what == ValveThread.WATER_OUTPUT) {
                bar.setProgress(msg.arg1);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_numInput = (TextView) findViewById(R.id.tv_numInput);
        tv_numOutput = (TextView) findViewById(R.id.tv_numOutput);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        InputThrds = new ArrayList<ValveThread>();
        OutputThrds = new ArrayList<ValveThread>();

        thread_count = new CountThread();
        thread_count.start();
    }

    //控制进水和出水阀门的数量
    public void onCtrlValve(View v) {
        ValveThread t = null;
        switch (v.getId()) {
            case R.id.btn_incInput:
                t = new ValveThread((InputThrds.size() + 1), 200, ValveThread.WATER_INPUT, handler, thread_count.handler_count);
                //t = new ValveThread(1, 200, ValveThread.WATER_INPUT, handler, thread_count.handler_count);
                InputThrds.add(t);
                t.start();
                break;
            case R.id.btn_decInput:
                if (InputThrds.size() > 0) {
                    //关闭最近开启的那个阀门
                    t = InputThrds.remove(InputThrds.size() - 1);
                    t.close();
                }
                break;
            case R.id.btn_incOutput:
                t = new ValveThread((OutputThrds.size() + 1), 200, ValveThread.WATER_OUTPUT, handler, thread_count.handler_count);
                OutputThrds.add(t);
                t.start();
                break;
            case R.id.btn_decOutput:
                if (OutputThrds.size() > 0) {
                    t = OutputThrds.remove(OutputThrds.size() - 1);
                    t.close();
                }
                break;
        }
        tv_numInput.setText(InputThrds.size() + "");
        tv_numOutput.setText(OutputThrds.size() + "");
    }
}


class ValveThread extends Thread {
    public static final int FULL = 100;
    public static final int WATER_INPUT = 1;
    public static final int WATER_OUTPUT = 2;

    private static int water_count = 0;

    private int id = 0;
    //线程睡眠时间
    private long ms = 0;
    //WATER_INPUT OR WATER_OUTPUT
    private int ctrl_what = 0;
    //主线程的handler
    private Handler handler;
    private Handler handler_count;

    private boolean closed = false;

    /**
     * @param id 阀门ID
     * @param ms 1%进水间隔的毫秒数
     */
    public ValveThread(int id, long ms, int ctrl_what, Handler handler, Handler handler_count) {
        this.id = id;
        this.ms = ms;
        this.ctrl_what = ctrl_what;
        this.handler = handler;
        this.handler_count = handler_count;
    }

    public void close() {
        this.closed = true;
    }

    @Override
    public void run() {
        boolean hit = false; //是否发生水量变化
            /*考虑并发的互斥
             * （1）使用Lock的性能比使用synchronized关键字要提高4~5倍；
			 * （2）使用信号量实现同步的速度大约比synchronized要慢10~20%；
			 * （3）使用atomic包的AtomicInter速度是比Lock要快1一个数量级,CAS(compare and swap)并发
			 * Java 理论与实践: 流行的原子
			 * http://www.ibm.com/developerworks/cn/java/j-jtp11234/
			 * 在多核环境下，尽量减少互锁以提高多核并行的效率
			*/
        final Lock lock = new ReentrantLock();
        while (!closed) {
            lock.lock();
            if ((ctrl_what == WATER_INPUT) && (water_count < FULL)) {
                water_count++;
                hit = true;
            } else if ((ctrl_what == WATER_OUTPUT) && (water_count > 0)) {
                water_count--;
                hit = true;
            } else {
                hit = false;
            }
            lock.unlock();
            if (hit) {
                //从消息池获得一个可重用的message
                Message msg = Message.obtain();
                msg.what = ctrl_what;
                msg.arg1 = water_count;//也可以将水量作为消息传递出去。
                handler.sendMessage(msg);
            } else {
                Message msg_count = Message.obtain();
                msg_count.what = CountThread.SHOW_COUNT;
                //在给msg_count附加Bundle.putXX()携带更复杂的数据
                Bundle data = new Bundle();
                data.putString("count_msg", "full or empty");
                msg_count.setData(data);
                //通过sendToTarget发送消息，
                //也可以通过handle.sendMsg的方式发送
                //msg_count.setTarget(thread_count.handler_count);
                //msg_count.sendToTarget();
                handler_count.sendMessage(msg_count);
            }
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CountThread extends Thread {
    public static final int SHOW_COUNT = 1;
    public Handler handler_count;

    @Override
    public void run() {

        Looper.prepare();
        handler_count = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SHOW_COUNT:
                        //显示我收到的count
                        Log.i("MyTest", "CountThread 收到 SHOW_COUNT: " + msg.getData().getSerializable("count_msg"));
                        /*
                        //再将count处理后发送给主线程
                        //arg1 + 1;
                        Message msg2 = Message.obtain();
                        msg2.what = SHOW_COUNT;
                        msg2.arg1 = msg.arg1 + 1;

                        Log.i("MyTest", "CountThread将Count:" + msg.arg1 + " 发送给主线程");
                        handler_main.sendMessage(msg2);
                        */
                        break;
                }
                super.handleMessage(msg);
            }
        };
        Looper.loop();
        super.run();
    }
}
