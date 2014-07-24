package com.hacktoolkit.helloworld.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hacktoolkit.helloworld.R;
import com.hacktoolkit.helloworld.helpers.AppHelpers;

public class InviteFriendsSMSActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends_sms);
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
