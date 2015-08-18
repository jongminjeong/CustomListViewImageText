package androidinterview.com.customlistviewimagetext;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter  {

	private ArrayList<fromServerData> objects;
	private Context context;

	public CustomListAdapter(Context context,ArrayList<fromServerData> objects) {
		super();
		// TODO Auto-generated constructor stub
		this.objects = objects;
		this.context = context;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return getObjects();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View v = convertView;
		if(v ==null)
		{
			LayoutInflater vi = (LayoutInflater)context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.mylist, null);
			holder = new ViewHolder();
			holder.rowImage = (ImageView)v.findViewById(R.id.map);
			holder.rowTxt1 = (TextView)v.findViewById(R.id.textView1);
			holder.rowTxt2 = (TextView)v.findViewById(R.id.textView2);
			v.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)v.getTag();       }
		if(objects.get(position).getImage()==null)
		{
			holder.rowImage.setBackgroundResource(R.drawable.pic1);       }
		else
		{
			holder.rowImage.setImageBitmap(objects.get(position).getImage());
		}
		holder.rowImage.setScaleType(ImageView.ScaleType.FIT_XY);
		holder.rowTxt1.setText(objects.get(position).getText1());
		holder.rowTxt2.setText(objects.get(position).getText2());
		return v;
	}

	public ArrayList<fromServerData> getObjects() {
		return objects;
	}

	public Context getContext() {
		return context;
	}

	public void setObjects(ArrayList<fromServerData> objects) {
		this.objects = objects;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	static class ViewHolder
	{
		ImageView rowImage;
		TextView rowTxt1;
		TextView rowTxt2;
	}

}
