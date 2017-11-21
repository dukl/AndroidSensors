package com.jqd.sensorsinfo.view;

import java.util.List;

import com.jqd.sensorsinfo.file.SaveData;
import com.jqd.sensorsinfo.model.CollectModel;
import com.jqd.sensorsinfo.model.SensorsModel;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MultiData extends Activity  {
	
	private Button startButton;
	private Button stopButton;
	private Button saveButton;
	private EditText edit;
//	private CollectModel collectModel;
//	private SensorsModel sensorsModel = null;
	private float[] buf0 = new float[10000];
	private float[] buf1 = new float[10000];
	private float[] buf2 = new float[10000];
	private int[] SensorType = new int[10000];
	private int index=0;
	private boolean isStart;
	public static MultiData multiData;
	private SensorManager mSensorManager;
	private Sensor acceSensor;
	private Sensor orieSensor;
	private List<Sensor> sensors; //记录设备中所包含的传感器
	private SensorEventListener listener;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		multiData = this;
		setContentView(R.layout.get_multidata);
		
//		sensorsModel = new SensorsModel();
//		sensorsModel.sensorsInitial(this);
		startButton = (Button) findViewById(R.id.start);
		stopButton = (Button) findViewById(R.id.stop);
		saveButton = (Button) findViewById(R.id.save);
		edit =(EditText) findViewById(R.id.edit_input);
	
//		collectModel = new CollectModel();
//		int sensorName = sensorsModel.getSensors().get(1).getType();
//	    collectModel.initial(multiData, sensorName);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		Toast.makeText(MultiData.this, sensors.size()+"", Toast.LENGTH_LONG).show();
		acceSensor = mSensorManager.getDefaultSensor(sensors.get(0).getType());
        orieSensor = mSensorManager.getDefaultSensor(sensors.get(1).getType());
        listener = new SensorEventListener() {
        		 @Override
        		    public void onSensorChanged(SensorEvent sensorEvent)
        		    {
        		        if(sensorEvent.sensor == null) { return; }
        		        if(sensorEvent.sensor.getType() == sensors.get(0).getType())
        		        {
        	             updateView(sensorEvent,sensors.get(0).getType());
        		        }
        		        if(sensorEvent.sensor.getType() == sensors.get(1).getType())
        		        {
        		        	updateView(sensorEvent,sensors.get(1).getType());
        		        }
        		    }

        		    @Override
        		    public void onAccuracyChanged(Sensor sensor, int i) {

        		    }
        };
        mSensorManager.registerListener(listener, sensors.get(0),
				SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(listener, sensors.get(1),
				SensorManager.SENSOR_DELAY_NORMAL);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isStart = true;
				Toast.makeText(MultiData.this, "Start", Toast.LENGTH_SHORT).show();
			    //acceSensor
				// mSensorManager.registerListener(MultiData.this, sensors.get(0).getType()|sensors.get(1).getType(), SensorManager.SENSOR_DELAY_NORMAL);        
				index = 0;
		
			}
		});
		stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isStart = false;
				Toast.makeText(MultiData.this, "Stop", Toast.LENGTH_SHORT).show();
				// mSensorManager.unregisterListener(MultiData.this, sensors.get(0).getType()&sensors.get(1).getType());
			   // index = 0;
			}
			
		});
		saveButton.setOnClickListener(new OnClickListener() {
	       @Override
	       public void onClick(View v) {
	    	    isStart = false;
				String fileNameString = edit.getText()+"";
				SaveData.getInstance().save(buf0, buf1, buf2,SensorType, index,
						fileNameString, MultiData.this);
				Toast.makeText(MultiData.this, "Saved", Toast.LENGTH_LONG)
						.show();
				index = 0;
	        }
       });
       
	}
	
//	
	public void updateView(SensorEvent event,int type) {
		if(isStart){
		if (index >= 10000) {
			Toast.makeText(MultiData.this, "缓冲区已满！",
					Toast.LENGTH_SHORT).show();
			isStart = false;
		} else {
			buf0[index] = event.values[0];
			buf1[index] = event.values[1];
			buf2[index] = event.values[2];
			if(type ==  sensors.get(0).getType())
				SensorType[index] = 0;
			if(type ==  sensors.get(1).getType())
				SensorType[index] = 1;
			index++;
		}
	}
	}


//
//		@Override
//		public void onSensorChanged(int sensor, float[] values) {
//	        if(sensor== Sensor.TYPE_GRAVITY)
//	        {
//	       //  Toast.makeText(MultiData.this, "ok1", Toast.LENGTH_SHORT).show();
//             updateView(sensor,values);
//	        }
//	        if(sensor == Sensor.TYPE_ACCELEROMETER)
//	        {
//	      //  	Toast.makeText(MultiData.this, "ok2", Toast.LENGTH_SHORT).show();
//	        	updateView(sensor,values);
//	        }
//		}
//	@Override
//	public void onAccuracyChanged(int sensor, int accuracy) {
//	}

//		private void updateView(int sensor, float[] values) {
//			if(isStart){
//				if (index >= 10000) {
//					Toast.makeText(MultiData.this, "缓冲区已满！",
//							Toast.LENGTH_SHORT).show();
//					isStart = false;
//				} else {
//					buf0[index] = values[0];
//					buf1[index] = values[1];
//					buf2[index] = values[2];
//					if(sensor == sensors.get(0).getType())
//						SensorType[index] = 0;
//					if(sensor == sensors.get(1).getType())
//						SensorType[index] = 1;
//					index++;
//				}
//			}else{}
//        }


		




	
}
