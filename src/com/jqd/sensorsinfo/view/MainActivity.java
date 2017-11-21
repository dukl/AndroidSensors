package com.jqd.sensorsinfo.view;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jqd.sensorsinfo.model.SensorsModel;
import com.jqd.sensorsinfo.thirdparty.RtChartsActivity;

/**
 * @author jiangqideng@163.com
 * @date 2016-6-25 ����7:28:58
 * @description �����棬��ʾһ�����������ֵ�list���͵�ǰѡ�еĴ���������ϸ��Ϣ
 */
public class MainActivity extends Activity {

	private ListView listView; // ��ʾ�豸�а����Ĵ������б�
	private TextView data1, data6; // �ֱ���ʾ��ǰ�������������Ϣ���Լ������������õ�����
	private Button button1; // �鿴ʵʱ����
	private Button button2; // �ɼ�����
	private SensorsModel sensorsModel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		data1 = (TextView) findViewById(R.id.data1);
		data6 = (TextView) findViewById(R.id.data6);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);

		sensorsModel = new SensorsModel();
		sensorsModel.sensorsInitial(this);
		SensorListAdapter adapter = new SensorListAdapter(this, sensorsModel.getSensors()); // Ϊlistview������ʾ���ݵ�adapter
		listView.setAdapter(adapter);

		// listview�ĵ���¼�,�����ı�һ��model�е��ź�ֵsensor_num, ����ǰ��ת�Ĵ�����
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sensorsModel.changeCurSensor(arg2);
			}
		});

		// Button1�ĵ�����鿴ʵʱ���ߣ���ת��RtChartsActivity
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RtChartsActivity.class);
				//�ѵ�ǰ����������Ϣͨ��intentֱ�Ӵ���ȥ
				intent.putExtra(
						"sensorName",
						sensorsModel.getSensors()
								.get(sensorsModel.getSensor_num()).getType());
				startActivity(intent);
			}
		});
		
		// Button1�ĵ�����ɼ����ݣ���ת��CollectActivity
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CollectActivity.class);
				intent.putExtra(
						"sensorName",
						sensorsModel.getSensors()
								.get(sensorsModel.getSensor_num()).getType());
				startActivity(intent);
			}
		});
	}

	//�뿪���activityʱע����������ʹ��
	protected void onPause() {
		sensorsModel.unregisterSensors();
		super.onPause();
	}
	
	// ���η����˳�
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٴε�������ء��˳�",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	//���µ�ǰ��������Ϣ�����ݵ���ʾ
	public void updateData(SensorEvent event) {
		data6.setText("  Name:\t" + event.sensor.getName() + "\n  Power:\t"
				+ String.valueOf(event.sensor.getPower()) + "\n  Resolution:\t"
				+ String.valueOf(event.sensor.getResolution())
				+ "\n  MinDelay:\t" + event.sensor.getMinDelay()
				+ "\n  MaximumRange:\t" + event.sensor.getMaximumRange());
		data1.setText("");
		for (int j = 0; j < event.values.length; j++) {
			data1.setText(data1.getText() + "  values[" + j + "]:\t"
					+ String.valueOf(event.values[j]) + "\n");

		}
	}
}
