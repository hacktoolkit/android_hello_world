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
import com.hacktoolkit.helloworld.constants.AppConstants;
import com.hacktoolkit.helloworld.helpers.AppHelpers;

public class InviteFriendsSMSActivity extends Activity {
	ContactsAdapter adapter;
	ArrayList<HTKContact> contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends_sms);

		if (savedInstanceState != null) {
			contacts = savedInstanceState.getParcelableArrayList("contacts");
			adapter = new ContactsAdapter(this, R.layout.invite_contact_layout, contacts);
		} else {
			contacts = new ArrayList<HTKContact>();
			adapter = new ContactsAdapter(this, R.layout.invite_contact_layout, contacts);
			// get the data source asynchronously
			ContactsUtils.getContactsWithPhoneAsync(this, adapter);
		}

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
				adapter.setItemSelected(position, checkBox.isChecked());
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
		final ArrayList<HTKContact> invitationsToSend = adapter.getSelectedContacts();
		new AlertDialog.Builder(this)
		.setTitle(AppConstants.CONFIRM_SMS_INVITE_TITLE)
		.setMessage(String.format(AppConstants.CONFIRM_SMS_INVITE_MESSAGE, invitationsToSend.size()))
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (HTKContact contact : invitationsToSend) {
					String phone = (String) contact.getData("phone");
					String name = (String) contact.getData("name");
					HTKUtils.sendSMS(AppConstants.FAKE_PHONE, String.format(AppConstants.SMS_INVITE_MESSAGE, name, phone));
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save UI state changes to the outState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		outState.putParcelableArrayList("contacts", contacts);
	}
}
