package com.psychology.dictioinary.adapter;

		import android.content.Context;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.ViewGroup;
		import android.widget.BaseAdapter;
		import android.widget.TextView;

		import com.psychology.dictioinary.R;
		import com.psychology.dictioinary.model.KamusModel;

		import java.util.ArrayList;

public class ListKataAdapter extends BaseAdapter{

	Context context;
	ArrayList<KamusModel> listKamus;
	int count;

	public ListKataAdapter(Context context, ArrayList<KamusModel> listKamus) {
		// TODO Auto-generated constructor stub
		this.listKamus = listKamus;
		this.context = context;
		this.count = listKamus.size();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v = arg1;
		ViewHolder holder = null;

		if (v == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_list_kata, null);
			holder.txtKata = (TextView)v.findViewById(R.id.txtItemKata);

			v.setTag(holder);
		} else {
			holder = (ViewHolder)v.getTag();
		}

		holder.txtKata.setText(listKamus.get(arg0).getKata());

		return v;
	}

	static class ViewHolder{
		TextView txtKata;
	}
}
