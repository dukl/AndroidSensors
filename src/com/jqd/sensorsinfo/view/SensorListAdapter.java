package com.jqd.sensorsinfo.view;

import java.util.List;

import com.jqd.sensorsinfo.view.R;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author jiangqideng@163.com
 * @date 2016-6-25 ����7:42:28
 * @description Ϊlistview���õ�һ��Adapter
 */
public class SensorListAdapter extends BaseAdapter {
	private Context context;
	private List<Sensor> list;

	public SensorListAdapter(MainActivity context, List<Sensor> list) {
		super();
		this.context = context;
		this.list = list;
	}

	// getcount ��ȡ���ݵĸ���
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	// getView ��Ҫ����һ��View��������ʾ����Դ�е�����
	@Override
	public long getItemId(int position) {
		return 0;
	}

	// �������Sensor��listviewÿ�����ʾ��ʽ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Sensor sensor = list.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup group = (ViewGroup) inflater.inflate(R.layout.sensors_name,
				null);
		TextView textView1 = (TextView) group.findViewById(R.id.textView1);
		TextView textView2 = (TextView) group.findViewById(R.id.textView2);
		textView1.setText(String.valueOf(position + 1));
		textView2.setText(sensor.getName());
		return group;
	}
}