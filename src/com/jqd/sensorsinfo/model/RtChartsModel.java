package com.jqd.sensorsinfo.model;

import java.util.Date;

import com.jqd.sensorsinfo.thirdparty.RtChartsActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

/**
 * @author jiangqideng@163.com
 * @date 2016-6-25 ����8:27:36
 * @description ��ȡ���������ݣ��򵥴���Ȼ��ÿ��Ҫ���µ�ʱ��һ���߳�ȥ����ͼ��
 */
public class RtChartsModel {

	private Handler handler;
	private SensorManager sensorManager;
	private Sensor sensor;
	private int ADD = 10;// ÿ�θ���ͼ���������ݸ���,�����첽�߳��еĹ�����.���뱣֤û�½�һ���߳�ʱ֮ǰ�Ǹ��Ѿ����
	private int add_j = 0;
	private int[] addY = new int[ADD];
	private long[] addX = new long[ADD];
	private int valueNumber = 0;
	private RtChartsActivity activity;

	public void initial(final RtChartsActivity activity, int sensorName) {
		this.activity = activity;
		sensorManager = (SensorManager) activity
				.getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(sensorName);
		activity.updateTitle("�������ͺ�: " + sensor.getName() + "\n" + "value[" + valueNumber + "]:");
		handler = new Handler() {
			public void handleMessage(Message msg) {
				// ˢ��ͼ��
				activity.updateChart();
				super.handleMessage(msg);
			}
		};
	}

	// ������ע��
	public void registerSensors() {
		sensorManager.registerListener(new SensorEventListener() {
			@Override
			public void onSensorChanged(final SensorEvent event) {
				// �½�һ���̣߳�������ִ�к�UI��ص�һЩ����
				new Thread(new Runnable() {
					@Override
					public void run() {
						addX[add_j] = new Date().getTime();
						addY[add_j] = (int) (event.values[valueNumber] * 5 - 50);
						add_j++;
						if (add_j >= ADD) {
							add_j = 0;
							Message message = new Message();
							// message.what = 200;
							handler.sendMessage(message);
						}
					}
				}).start();
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		}, sensor, SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void changeValueNumber() {
		valueNumber++;
		if (valueNumber >= 3) {
			valueNumber = 0;
		}
		activity.updateTitle("�������ͺ�: " + sensor.getName() + "\n" + "value[" + valueNumber + "]:");
	}
	
	public int getADD() {
		return ADD;
	}

	public int getAdd_j() {
		return add_j;
	}

	public int[] getAddY() {
		return addY;
	}

	public long[] getAddX() {
		return addX;
	}
}
