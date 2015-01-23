package io.ap1.fritolays.fritolaysapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BarcodeAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mListArrays;
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mData;
	
	public  Map<Integer,Boolean> mCBFlag = null;
	private List items = new ArrayList();

	
	public BarcodeAdapter(Context c,List items){
		this.mContext = c;
		mInflater = LayoutInflater.from(mContext);
		mCBFlag = new HashMap<Integer, Boolean>();
		this.items=items;
		init();
	}
	
	void init(){
		for (int i = 0; i < items.size(); i++) {
			mCBFlag.put(i, false);
		}
	}

	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		BarcodeModel s = (BarcodeModel) items.get(position);
		if(convertView == null){
			//holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.coupon_item_layout, null);
			holder = new ViewHolder();
			//add
			holder.img = (ImageView) convertView.findViewById(R.id.barimg);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		if (s.getImage() != null) {
            holder.img.setImageBitmap(s.getImage());
        } else {
                // MY DEFAULT IMAGE
           // holder.img.setImageResource(R.drawable.welcome);
			
        }
		
			
		return convertView;
	}
	public final class ViewHolder{
		public  TextView mTextView;
		public ImageView img;
	}
}
