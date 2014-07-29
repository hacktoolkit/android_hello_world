package com.hacktoolkit.helloworld.adapters;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacktoolkit.android.models.HTKContact;
import com.hacktoolkit.android.utils.ContactsUtils;
import com.hacktoolkit.helloworld.R;

public class ContactsAdapter extends ArrayAdapter<HTKContact> {
	private final Activity context;
	private final ArrayList<HTKContact> contacts;
	ViewHolder viewHolder;
	
	private static class ViewHolder {
		ImageView ivContactThumbnail;
		TextView tvName;
		TextView tvPhone;
		CheckBox cbContactSelected;
	}

	public ContactsAdapter(Activity context, ArrayList<HTKContact> contacts) {
		super(context, R.layout.invite_contact_layout, contacts);
		this.context = context;
		this.contacts = contacts;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.invite_contact_layout, parent, false);
			// configure view holder
			viewHolder = new ViewHolder();
			// Lookup views for data population
			viewHolder.ivContactThumbnail = (ImageView) convertView.findViewById(R.id.ivContactThumbnail);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
			viewHolder.cbContactSelected = (CheckBox) convertView.findViewById(R.id.cbContactSelected);
			convertView.setTag(viewHolder);
			viewHolder.cbContactSelected.setTag(contacts.get(position));
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.cbContactSelected.setTag(contacts.get(position));
		}

		// Get the data item for this position
		HTKContact item = (HTKContact) this.getItem(position);

		// fill data
		int contactId = (Integer) item.getData("id");
		InputStream photoInputStream = ContactsUtils.openPhoto((Activity) context, contactId);
		Bitmap thumbnail = BitmapFactory.decodeStream(photoInputStream);
		viewHolder.ivContactThumbnail.setImageBitmap(thumbnail);
		viewHolder.tvName.setText((String) item.getData("name"));
		viewHolder.tvPhone.setText((String) item.getData("phoneType") + ": " + (String) item.getData("phone"));
		viewHolder.cbContactSelected.setChecked((Boolean) ((HTKContact) contacts.get(position)).getMetaData("selected"));

		return convertView;
	}
}
