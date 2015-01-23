package io.ap1.fritolays.fritolaysapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SurveyAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mListArrays;
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mData;
	
	public  Map<Integer,Boolean> mCBFlag = null;
	private List items = new ArrayList();

    private int selectedPos = -1;   // init value for not-selected

	public SurveyAdapter(Context c,List items){
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
	
    public void setSelectedPosition(int pos){
    selectedPos = pos;
         // inform the view of this change
         notifyDataSetChanged();
    }
    public int getSelectedPosition(){
         return selectedPos;
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
		final ViewHolder holder;
		SurveyModel s = (SurveyModel) items.get(position);
		if(convertView == null){
			//holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.survey_item_layout, null);
			holder = new ViewHolder();
			//add
			holder.img = (ImageView) convertView.findViewById(R.id.img);
//			holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cb_item);
			holder.mTextView = (TextView) convertView.findViewById(R.id.tv_item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		System.out.println("Get view got called " + position + " " + holder);
        // change the row color based on selected state
        if(selectedPos == position){
        	holder.mTextView.setTextColor(Color.CYAN);
//        	holder.mCheckBox.setChecked(true);
        }
        else{
        	holder.mTextView.setTextColor(Color.WHITE);
//        	holder.mCheckBox.setChecked(false);
        }
		
		holder.mTextView.setText(s.getName());
        if (s.getImage() != null) {
            holder.img.setImageBitmap(s.getImage());
        } else {
                // MY DEFAULT IMAGE
           // holder.img.setImageResource(R.drawable.welcome);
			
        }
		
		
		//holder.mCheckBox.setChecked(mCBFlag.get(position));
	
		return convertView;
	}
	public final class ViewHolder{
//		public  CheckBox mCheckBox;
		public  TextView mTextView;
		public ImageView img;
	}
}
