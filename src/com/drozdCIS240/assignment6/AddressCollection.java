package com.drozdCIS240.assignment6;

import java.util.ArrayList;

public class AddressCollection {
	ArrayList<AddressAttributeGroup> addressList = new ArrayList<AddressAttributeGroup>();
	final int MAX_ADDRESS_COUNT = 20;

	public boolean isAddressLimitReached() {
		return (addressList.size() >= MAX_ADDRESS_COUNT);
	}

	public int addAddress(AddressAttributeGroup address) throws Exception {
		if (isAddressLimitReached())
			throw (new Exception("Max Address Reached."));
		addressList.add(address);
		return addressList.indexOf(address);
	}

	public void setAddress(int addressIndex, AddressAttributeGroup address) {
		addressList.set(addressIndex, address);
	}

	public void removeAddress(int addressIndex) {
		addressList.remove(addressIndex);
	}

	public AddressAttributeGroup getAddress(int addressIndex) {
		return addressList.get(addressIndex);
	}
}

class AddressAttributeGroup {
	public String fname;
	public String lname;
	public String street;
	public String city;
	public String state;
	public String zip;
	public String image;
	public long id;

	public AddressAttributeGroup(String fname, String lname, String street,
			String city, String state, String zip, String image) {
		this.fname = fname;
		this.lname = lname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.image = image;
	}
	public AddressAttributeGroup(int id,String fname, String lname, String street,
			String city, String state, String zip, String image) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.image = image;
	}
}
