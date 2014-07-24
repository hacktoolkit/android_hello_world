package com.hacktoolkit.helloworld.helpers;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hacktoolkit.android.utils.HTKUtils;
import com.hacktoolkit.helloworld.R;
import com.hacktoolkit.helloworld.constants.AppConstants;

public class AppHelpers {
	public static void inflatePrimaryMenu(Activity currentActivity, Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		currentActivity.getMenuInflater().inflate(R.menu.activity_hello_world, menu);
	}
	
	public static void handleMenuItemSelected(Activity currentActivity, MenuItem menuItem) {
		  switch (menuItem.getItemId()) {
//		  case android.R.id.home:
//			  break;
		  case R.id.menu_settings:
			  HTKUtils.switchActivity(currentActivity, "com.hacktoolkit.helloworld", "com.hacktoolkit.helloworld.activities.SettingsActivity");
			  //Toast.makeText(this, "Menu Settings", Toast.LENGTH_SHORT).show();
			  break;
		  case R.id.menu_splash:
			  HTKUtils.switchActivity(currentActivity, "com.hacktoolkit.helloworld", "com.hacktoolkit.helloworld.activities.SplashScreenActivity");
			  break;
		  case R.id.menu_about:
			  HTKUtils.launchUrlInBrowser(currentActivity, AppConstants.HACKTOOLKIT_URL);
			  break;
		  case R.id.menu_app_store:
			  HTKUtils.openGooglePlay(currentActivity);
			  break;
		  case R.id.menu_share:
			  HTKUtils.shareHtml(currentActivity, AppConstants.SHARE_MESSAGE, AppConstants.SHARE_LABEL);
			  break;
		  case R.id.menu_invite_sms:
			  Toast.makeText(currentActivity, "Invite Friends via SMS", Toast.LENGTH_SHORT).show();
			  HTKUtils.switchActivity(currentActivity, "com.hacktoolkit.helloworld", "com.hacktoolkit.helloworld.activities.InviteFriendsSMSActivity");
			  break;
		  default:
			  break;
		  }
	}
}
