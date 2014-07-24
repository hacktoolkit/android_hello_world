package com.hacktoolkit.helloworld.activities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hacktoolkit.android.utils.ContactsUtils;
import com.hacktoolkit.helloworld.R;
import com.hacktoolkit.helloworld.helpers.AppHelpers;

public class InviteFriendsSMSActivity extends Activity {
	private class ContactsAdapter extends ArrayAdapter<JSONObject> {
		public ContactsAdapter(Context context, ArrayList<JSONObject> contacts) {
			super(context, R.layout.invite_contact_layout, contacts);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			// Get the data item for this position

			JSONObject item = this.getItem(position);
			// Check if an existing view is being reused, otherwise inflate the view
			if (convertView == null) {
				LayoutInflater vi = LayoutInflater.from(getContext());
				convertView = vi.inflate(R.layout.invite_contact_layout, parent, false);
			}
			// Lookup view for data population
		    TextView tv = (TextView) convertView.findViewById(R.id.tv);
		    TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);
		    // Populate the data into the template view using the data object
		    try {
		    		tv.setText(item.getString("name"));
		    		tv2.setText(item.getString("phone"));
		    } catch (JSONException jsone) {
		      	// oh well
		    }
		    return convertView;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends_sms);
		
		SmsManager sms = SmsManager.getDefault();
		
		// get the data source
		ArrayList<JSONObject> contacts = ContactsUtils.getContactsWithPhone(this);
		// Create the adapter to convert the array to views
		ContactsAdapter adapter = new ContactsAdapter(this,  contacts);
		// attach the adapter to a ListView
		ListView listView = (ListView) findViewById(R.id.lvContacts);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		AppHelpers.inflatePrimaryMenu(this, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		AppHelpers.handleMenuItemSelected(this,  item);
		return true;
	}
}
