package com.hacktoolkit.helloworld.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.hacktoolkit.android.models.HTKContact;
import com.hacktoolkit.android.utils.ContactsUtils;
import com.hacktoolkit.android.utils.HTKUtils;
import com.hacktoolkit.helloworld.R;
import com.hacktoolkit.helloworld.adapters.ContactsAdapter;
import com.hacktoolkit.helloworld.helpers.AppHelpers;

public class InviteFriendsSMSActivity extends Activity {
	ArrayList<HTKContact> theContacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends_sms);
		
		SmsManager sms = SmsManager.getDefault();
		
		// get the data source
		ArrayList<HTKContact> contacts = ContactsUtils.getContactsWithPhone(this);
		theContacts = contacts;
		// Create the adapter to convert the array to views
		ContactsAdapter adapter = new ContactsAdapter(this, contacts);
		// attach the adapter to a ListView
		ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
		lvContacts.setAdapter(adapter);
		Button btnInvite = (Button) findViewById(R.id.btnInvite);

		lvContacts.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(	AdapterView<?> aView,
					View view, int position, long rowId) {
				CheckBox checkBox = (CheckBox) view.findViewById(R.id.cbContactSelected);
				checkBox.toggle();
				HTKContact clickedItem = theContacts.get(position);
				clickedItem.setMetaData("selected", checkBox.isChecked());
//				Toast.makeText(
//						getApplicationContext(),
//						"Clicked ListItem Number " + position + ", " + clickedItem,
//						Toast.LENGTH_SHORT
//						).show();
			}
		});
		
		btnInvite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirmSendInvites();
			}
		});
	}

	private void confirmSendInvites() {
		ArrayList<HTKContact> invitationsToSend = new ArrayList<HTKContact>();
		for (HTKContact contact : theContacts) {
			if ((Boolean) contact.getMetaData("selected")) {
				invitationsToSend.add(contact);
			}
		}
		final ArrayList<HTKContact> invitations = invitationsToSend;
		new AlertDialog.Builder(this)
		.setTitle("Send SMS Invitations?")
		.setMessage("Are you sure you want to send " + invitationsToSend.size() + " invitations using SMS?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (HTKContact contact : invitations) {
					String phone = (String) contact.getData("phone");
					HTKUtils.sendSMS(phone, contact.getData("name") + ": Check out Hacktoolkit at http://hacktoolkit.com");
				}
			}
		})
		.setNegativeButton("No", null)
		.show();
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
