package com.hacktoolkit.helloworld.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hacktoolkit.android.adapters.HTKContactsAdapter;
import com.hacktoolkit.android.models.HTKContact;
import com.hacktoolkit.helloworld.R;

public class ContactsAdapter extends HTKContactsAdapter {
	ViewHolder viewHolder;

	private static class ViewHolder {
		ImageView ivAvatar;
		TextView tvName;
		TextView tvPhone;
		CheckBox cbContactSelected;
	}

	public ContactsAdapter(Activity context, int layoutId, ArrayList<HTKContact> contacts) {
		super(context, R.layout.invite_contact_layout, contacts);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Check if an existing view is being reused, otherwise inflate the view
		HTKContact contact = contacts.get(position);
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.invite_contact_layout, parent, false);
			// configure view holder
			viewHolder = new ViewHolder();
			// Lookup views for data population
			viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
			viewHolder.cbContactSelected = (CheckBox) convertView.findViewById(R.id.cbContactSelected);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.cbContactSelected.setTag(contact);

		// Get the data item for this position
		HTKContact item = (HTKContact) this.getItem(position);

		// fill data
		Bitmap avatar = item.getAvatar(context);

		viewHolder.ivAvatar.setImageBitmap(avatar);
        ScaleAnimation scaleAvatarAnimation = new ScaleAnimation(
        		0.0F, 1.0F, 0.0F, 1.0F,
        		viewHolder.ivAvatar.getWidth() / 2,
        		viewHolder.ivAvatar.getHeight() / 2
        		);
        scaleAvatarAnimation.setDuration(100L);
        scaleAvatarAnimation.setFillAfter(true);
        viewHolder.ivAvatar.startAnimation(scaleAvatarAnimation);
		viewHolder.tvName.setText((String) item.getData("name"));
		viewHolder.tvPhone.setText(String.format("%s: %s", (String) item.getData("phoneType"), (String) item.getData("phone")));
		boolean isSelected = (Boolean) contact.getMetaData("selected");
		viewHolder.cbContactSelected.setChecked(isSelected);

		return convertView;
	}

	public void loadContacts(ArrayList<HTKContact> contacts) {
		super.loadContacts(contacts);
		hideLoadingPanel();
	}

	public void hideLoadingPanel() {
		RelativeLayout loadingPanel = (RelativeLayout) context.findViewById(R.id.loadingPanel);
		loadingPanel.setVisibility(View.GONE);
	}
}
