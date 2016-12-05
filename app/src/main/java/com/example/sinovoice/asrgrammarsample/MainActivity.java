package com.example.sinovoice.asrgrammarsample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinovoice.util.ConfigUtil;
import com.example.sinovoice.util.HciCloudAsrHelper;
import com.example.sinovoice.util.HciCloudSysHelper;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStart;
    private TextView tvState;
    private TextView tvError;
    private TextView tvResult;
    private HciCloudSysHelper mHciCloudSysHelper;
    private HciCloudAsrHelper mHciCloudAsrHelper;
    private TextView tvGrammar;

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case HciCloudAsrHelper.RECORDER_RESULT: //显示识别结果
                    Bundle resultBundle = msg.getData();
                    String result = resultBundle.getString("result");
                    tvResult.setText(result);
                    break;
                case HciCloudAsrHelper.RECORDER_ERROR:  //显示错误信息
                    Bundle errorBundle = msg.getData();
                    String error = errorBundle.getString("error");
                    System.out.print(error);
                    if (error.equals("0")) {
                        tvError.setVisibility(View.GONE);
                    } else {
                        tvError.setText(error);
                    }
                    break;
                case HciCloudAsrHelper.RECORDER_STATE:  //显示录音机的状态
                    Bundle stateBundle = msg.getData();
                    String state = stateBundle.getString("state");
                    tvState.setText(state);
                    break;
                case HciCloudAsrHelper.RECORDER_GRAMMAR:
                    Bundle grammarBundle = msg.getData();
                    String grammar = grammarBundle.getString("grammar");
                    tvGrammar.setText(grammar);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initSinovoice();
    }

    private void initSinovoice() {
        mHciCloudSysHelper = HciCloudSysHelper.getInstance();
        mHciCloudAsrHelper = HciCloudAsrHelper.getInstance();
        int errorCode = mHciCloudSysHelper.init(this);
        if (errorCode != HciErrorCode.HCI_ERR_NONE) {
            Toast.makeText(this, "系统初始化失败，错误码=" + errorCode, Toast.LENGTH_SHORT).show();
            return;
        }
        mHciCloudAsrHelper.setMyHander(new MyHandler());
        String grammarData = "#JSGF V1.0;\n" +
                "\n" +
                "grammar stock_1001;\n" +
                "\n" +
                "public <stock_1001> =     万东医疗 |\n" +
                "                          三峡水利 |\n" +
                "                          上海机场 |\n" +
                "                          上海梅林 |\n" +
                "                          上海汽车 |\n" +
                "                          上港集箱 |\n" +
                "                          东北高速 |\n" +
                "                          东方航空 |\n" +
                "                          东湖高新 |\n" +
                "                          东风汽车 |\n" +
                "                          东风科技 |\n" +
                "                          中国国贸 |\n" +
                "                          中国泛旅 |\n" +
                "                          中技贸易 |\n" +
                "                          中纺投资 |\n" +
                "                          中视股份 |\n" +
                "                          乐凯胶片 |\n" +
                "                          云天化 |\n" +
                "                          亚盛集团 |\n" +
                "                          人福科技 |\n" +
                "                          光彩建设;" ;
        String grammarConfigString = "capkey=" + ConfigUtil.CAP_KEY_ASR_LOCAL_GRAMMAR + ",isFile=no,grammarType=jsgf";
        boolean bool = false;
        try {
            bool = mHciCloudAsrHelper.initAsrRecorder(this, grammarConfigString, new String(grammarData.getBytes(), "utf-8"), ConfigUtil.CAP_KEY_ASR_LOCAL_GRAMMAR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (bool == false) {
            Toast.makeText(this, "录音机初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }else{      //隐藏错误码的状态栏
            Message message = new Message();
            message.arg1 = HciCloudAsrHelper.RECORDER_ERROR;
            Bundle bundle = new Bundle();
            bundle.putString("error", "0");
            message.setData(bundle);
            new MyHandler().sendMessage(message);
        }
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.btn_start);
        tvState = (TextView) findViewById(R.id.tv_state);
        tvError = (TextView) findViewById(R.id.tv_error);
        tvResult = (TextView) findViewById(R.id.tv_result);
        tvGrammar = (TextView) findViewById(R.id.tv_grammar);

        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mHciCloudAsrHelper.startAsrRecorder(ConfigUtil.CAP_KEY_ASR_LOCAL_GRAMMAR, "common");
                break;
            default:
                break;
        }
    }
}
