package com.jqd.sensorsinfo.model;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.jqd.sensorsinfo.view.MainActivity;
import com.jqd.sensorsinfo.view.MultiData;

/**
 * @author jiangqideng@163.com
 * @date 2016-6-25 ����7:38:17
 * @description ���ƴ�������ע�ᡢע������ȡ��Ӧ�¼�
 */
public class SensorsModel {
	private SensorManager sensorManager;
	private List<Sensor> sensors; //��¼�豸���������Ĵ�����
	private SensorEventListener listener;
	private int sensor_num; //��ǰ���������б��е�λ��
	private Activity activity;

	//��ʼ
	public void sensorsInitial(final Activity aActivity) {
		this.activity = aActivity;
		sensorManager = (SensorManager) aActivity
				.getSystemService(Context.SENSOR_SERVICE);
		sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		sensor_num = 0;
		listener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				if(aActivity == MultiData.multiData ){
					
				}else
				((MainActivity) aActivity).updateData(event);
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		registerSensors();
	}

	//�ı䵱ǰ������
	public void changeCurSensor(int newIdx) {
		unregisterSensors();
		sensor_num = newIdx;
		listener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				((MainActivity) activity).updateData(event);
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		registerSensors();
	}

	//������ע��
	public void registerSensors() {
		sensorManager.registerListener(listener, sensors.get(sensor_num),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	//������ע��
	public void unregisterSensors() {
		sensorManager.unregisterListener(listener);
	}

	//����Sensor���б�
	public List<Sensor> getSensors() {
		return sensors;
	}

	//���ص�ǰ��������λ��
	public int getSensor_num() {
		return sensor_num;
	}
}
