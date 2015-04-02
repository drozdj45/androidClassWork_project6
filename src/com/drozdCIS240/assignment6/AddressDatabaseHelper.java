package com.drozdCIS240.assignment6;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.content.ContentValues;


public class AddressDatabaseHelper extends SQLiteOpenHelper {
	public static final String TABLE_ADDRESS = "address";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRSTNAME = "fname";
	public static final String COLUMN_LASTNAME = "lname";// 
	public static final String COLUMN_STREET = "street";//
	public static final String COLUMN_CITY = "city";
	public static final String COLUMN_STATE = "state";// 
	public static final String COLUMN_ZIP = "zip";// 
	public static final String COLUMN_IMAGE = "image";

	private static final String DATABASE_NAME = "address.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation SQL statement
	private static final String DATABASE_CREATE_SQL = "create table "
			+ TABLE_ADDRESS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + 
			COLUMN_FIRSTNAME + " text not null, " +
			COLUMN_LASTNAME  + " text not null, " + 
			COLUMN_STREET + " text not null, " + 
			COLUMN_CITY + " text not null, " + 
			COLUMN_STATE  + " text not null, " +
			COLUMN_ZIP  + " text not null, " +
			COLUMN_IMAGE 	+ " text not null);";

	public AddressDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
		onCreate(db);
	}
}

class AddressDataSource {
	private SQLiteDatabase database;
	private AddressDatabaseHelper dbHelper;
	private String[] allColumns = { AddressDatabaseHelper.COLUMN_ID,
			AddressDatabaseHelper.COLUMN_FIRSTNAME,
			AddressDatabaseHelper.COLUMN_LASTNAME,
			AddressDatabaseHelper.COLUMN_STREET,
			AddressDatabaseHelper.COLUMN_CITY,
			AddressDatabaseHelper.COLUMN_STATE,
			AddressDatabaseHelper.COLUMN_ZIP,
			AddressDatabaseHelper.COLUMN_IMAGE };

	AddressDataSource(Context context) {
		dbHelper = new AddressDatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long createAddress(AddressAttributeGroup address) {
		ContentValues values = new ContentValues();

		values.put(AddressDatabaseHelper.COLUMN_FIRSTNAME, address.fname);
		values.put(AddressDatabaseHelper.COLUMN_LASTNAME, address.lname);
		values.put(AddressDatabaseHelper.COLUMN_STREET, address.street);
		values.put(AddressDatabaseHelper.COLUMN_CITY, address.city);
		values.put(AddressDatabaseHelper.COLUMN_STATE, address.state);
		values.put(AddressDatabaseHelper.COLUMN_ZIP, address.zip);
		values.put(AddressDatabaseHelper.COLUMN_IMAGE, address.image);

		long insertId = database.insert(AddressDatabaseHelper.TABLE_ADDRESS,
				null, values);
		return insertId;
	}

	public void deleteAddress(AddressAttributeGroup address) {
		long id = address.id;
		database.delete(AddressDatabaseHelper.TABLE_ADDRESS,
				AddressDatabaseHelper.COLUMN_ID + " = " + id, null);
	}

	public AddressCollection getAllAddresses() throws Exception {
		AddressCollection addresses = new AddressCollection();

		Cursor cursor = database.query(AddressDatabaseHelper.TABLE_ADDRESS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			addresses.addAddress(cursorToAddressAttributeGroup(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return addresses;
	}

	private AddressAttributeGroup cursorToAddressAttributeGroup(Cursor cursor) {
		AddressAttributeGroup address = new AddressAttributeGroup(
				cursor.getInt(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ID)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_FIRSTNAME)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_LASTNAME)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_STREET)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_CITY)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_STATE)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ZIP)),
				cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_IMAGE)));
		return address;
	}

}
