package com.jqd.sensorsinfo.model;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.jqd.sensorsinfo.view.CollectActivity;
import com.jqd.sensorsinfo.view.MultiData;

/**
 * @author jiangqideng@163.com
 * @date 2016-6-25 ����9:03:39
 * @description �ɼ�����ģ��Ĵ���������
 */
public class CollectModel {
	private SensorManager sensorManager;
	private SensorEventListener listener;
	private Sensor sensor;
	private Activity activity;
	
	public void initial(Activity activity, int sensorName) {
		this.activity = activity;
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(sensorName);
		reListener();
		registerSensors();
	}
	
	public void reListener() {
		listener=new SensorEventListener() {			
			@Override
			public void onSensorChanged(SensorEvent event) {
				if(activity == MultiData.multiData){
					((MultiData) activity).updateView(event, 0);
				}else
				((CollectActivity) activity).updateView(event);
			}	
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
	}
	
	//������ע��
	public void unregisterSensors() {
		sensorManager.unregisterListener(listener);
	}
	
	//������ע��
	public void registerSensors() {
		sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

}
