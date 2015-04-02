package com.drozdCIS240.assignment6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AddressStrongTypeIntent {
	public String fname;
	public String lname;
	public String street;
	public String state;
	public String city;
	public String zip;
	public String image;

	public enum ActionType {
		ADD, EDIT, DELETE
	}

	ActionType action;
	int addressIndex = 0;
	Intent intent;

	public AddressStrongTypeIntent(Intent intent) {
		Bundle bundle = intent.getExtras();
		try {
			fname = bundle.getString("fname");
			lname = bundle.getString("lname");
			street = bundle.getString("street");
			city = bundle.getString("city");
			state = bundle.getString("state");
			zip = bundle.getString("zip");
			image = bundle.getString("image");
			action = ActionType.values()[bundle.getInt("action", 0)];
			addressIndex = bundle.getInt("addressindex");
		} catch (Exception ex) {
		}
	}

	public AddressStrongTypeIntent() {
		fname = "";
		lname = "";
		street = "";
		state = "";
		city = "";
		zip = "";
	}

	public AddressStrongTypeIntent(AddressAttributeGroup addressAttributes,
			ActionType action, int addressIndex) {
		fname = addressAttributes.fname;
		lname = addressAttributes.lname;
		street = addressAttributes.street;
		city = addressAttributes.city;
		state = addressAttributes.state;
		zip = addressAttributes.zip;
		image = addressAttributes.image;
		this.action = action;
		this.addressIndex = addressIndex;
	}

	public void clearIntent() {
		intent = null;

	}

	void putExtras() {
		intent.putExtra("fname", fname);
		intent.putExtra("lname", lname);
		intent.putExtra("street", street);
		intent.putExtra("city", city);
		intent.putExtra("state", state);
		intent.putExtra("zip", zip);
		intent.putExtra("image", image);
		
		intent.putExtra("action", action.ordinal());
		intent.putExtra("addressindex", addressIndex);
	}

	public Intent getIntent() {
		if (intent == null) {
			intent = new Intent();
			putExtras();
		}
		return intent;
	}

	public Intent getIntent(Activity addressEntry, Class<AddressEntry> class1) {
		if (intent == null) {
			intent = new Intent(addressEntry, class1);
			putExtras();
		}
		return intent;
	}

}
