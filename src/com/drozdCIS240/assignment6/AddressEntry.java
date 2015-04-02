package com.drozdCIS240.assignment6;

import java.io.File;

import com.drozdCIS240.assignment6.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class AddressEntry extends Activity implements View.OnClickListener {

	int CAMERA_PIC_REQUEST = 2;

	Button cmdSave;
	Button cmdClear;
	Button cmdCancel;
	Button cmdPhoto;

	ImageView imagePhoto;

	EditText editFName;
	EditText editLName;
	EditText editStreet;
	EditText editCity;
	EditText editState;
	EditText editZip;

	int result;
	AddressStrongTypeIntent stIntent;

	String imagePath = "";

	void showInfo(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_entry);
		editFName = (EditText) findViewById(R.id.editFName);
		editLName = (EditText) findViewById(R.id.editLName);
		editStreet = (EditText) findViewById(R.id.editStreet);
		editCity = (EditText) findViewById(R.id.editCity);
		editState = (EditText) findViewById(R.id.editState);
		editZip = (EditText) findViewById(R.id.editZip);
		imagePhoto = (ImageView) findViewById(R.id.imagePhoto); // could be
																// issue
		cmdSave = (Button) findViewById(R.id.cmdSave);
		cmdSave.setOnClickListener(this);
		cmdPhoto = (Button) findViewById(R.id.editPhoto);
		cmdPhoto.setOnClickListener(this);
		cmdClear = (Button) findViewById(R.id.cmdClear);
		cmdClear.setOnClickListener(this);
		cmdCancel = (Button) findViewById(R.id.cmdCancel);
		cmdCancel.setOnClickListener(this);
		stIntent = new AddressStrongTypeIntent(getIntent());
		editFName.setText(stIntent.fname);
		editLName.setText(stIntent.lname);
		editStreet.setText(stIntent.street);
		editCity.setText(stIntent.city);
		editState.setText(stIntent.state);
		editZip.setText(stIntent.zip);
		imagePath = stIntent.image;

		if (stIntent.action == AddressStrongTypeIntent.ActionType.DELETE)
			cmdSave.setText(R.string.delete);
		editFName
				.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		editLName
				.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		editStreet
				.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		editCity.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		editState
				.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		editZip.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		cmdClear.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
		loadImage();
	}

	void loadImage() {
		if (imagePath == null || imagePath.length() == 0)
			return;

		Bitmap imageBitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 3;
		imageBitmap = BitmapFactory.decodeFile(
				Environment.getExternalStorageDirectory() + "/DCIM/"
						+ imagePath, options);
		imagePhoto.setImageBitmap(imageBitmap);
	}

	@Override
	public void finish() {
		stIntent.clearIntent();
		stIntent.fname = editFName.getText().toString();
		stIntent.lname = editLName.getText().toString();
		stIntent.street = editStreet.getText().toString();
		stIntent.city = editCity.getText().toString();
		stIntent.state = editState.getText().toString();
		stIntent.zip = editZip.getText().toString();
		stIntent.image = imagePath;
		setResult(result, stIntent.getIntent());
		super.finish();
	}

	public void onClick(View v) {
		if (cmdSave.getId() == v.getId()) {
			result = RESULT_OK;
			finish();

		}
		if (cmdPhoto.getId() == v.getId()) {

			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			imagePath = "Address" + System.currentTimeMillis() + ".png";
			Uri uriSavedImage = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory() + "/DCIM", imagePath));
			cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					uriSavedImage);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		}
		if (cmdClear.getId() == v.getId()) {
			editFName.setText("");
			editLName.setText("");
			editStreet.setText("");
			editCity.setText("");
			editState.setText("");
			editZip.setText("");
		}
		if (cmdCancel.getId() == v.getId()) {
			result = RESULT_CANCELED;
			finish();

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAMERA_PIC_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				try { 
					//imageBitmap = (Bitmap) data.getExtras().get("data");
					loadImage();
				} catch (Exception e) {
					showInfo("Picture not taken");
					e.printStackTrace();
					imagePath = "";
				}
			}
		} else {
			showInfo("Picture not taken");
		}

	}
}
