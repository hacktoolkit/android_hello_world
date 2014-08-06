package com.hacktoolkit.helloworld.adapters;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hacktoolkit.android.adapters.HTKContactsAdapter;
import com.hacktoolkit.android.models.HTKContact;
import com.hacktoolkit.android.utils.ContactsUtils;
import com.hacktoolkit.helloworld.R;

public class ContactsAdapter extends HTKContactsAdapter {
	ViewHolder viewHolder;

	private static class ViewHolder {
		ImageView ivContactThumbnail;
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
			viewHolder.ivContactThumbnail = (ImageView) convertView.findViewById(R.id.ivContactThumbnail);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
			viewHolder.cbContactSelected = (CheckBox) convertView.findViewById(R.id.cbContactSelected);
			viewHolder.cbContactSelected.setTag(contact);
			viewHolder.cbContactSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					CheckBox checkBox = (CheckBox) buttonView;
					HTKContact contact = (HTKContact) checkBox.getTag();
					contact.setSelected(isChecked);
				}
			});

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.cbContactSelected.setTag(contact);
		}

		// Get the data item for this position
		HTKContact item = (HTKContact) this.getItem(position);

		// fill data
		int contactId = (Integer) item.getData("id");
		InputStream photoInputStream = ContactsUtils.openPhoto((Activity) context, contactId);
		Bitmap thumbnail = BitmapFactory.decodeStream(photoInputStream);
		viewHolder.ivContactThumbnail.setImageBitmap(thumbnail);
		viewHolder.tvName.setText((String) item.getData("name"));
		viewHolder.tvPhone.setText(String.format("%s: %s", (String) item.getData("phoneType"), (String) item.getData("phone"));
		boolean isSelected = (Boolean) contact.getMetaData("selected");
		System.out.println(contact.toJSON() + ", " + isSelected);
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
