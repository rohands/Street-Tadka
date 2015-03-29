package com.akarsh.streetfoodfinder4;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

public class getAddress extends Activity {

///////////////
////Method for getting address from lat lng
/////////////

public String addressOf(double lat,double lng)
{
Geocoder geocoder = new Geocoder(getAddress.this,Locale.getDefault());
String Add = null;
try {
System.out.println("Trying to get address");
List<Address> addresses = geocoder.getFromLocation(lat,
lng, 1);

if (addresses != null) {
Address returnedAddress = addresses.get(0);
StringBuilder strReturnedAddress = new StringBuilder(
    "Address:\n");

for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
strReturnedAddress
        .append(returnedAddress.getAddressLine(i)).append(
                "\n");
}
Add=strReturnedAddress.toString();
System.out.println(Add);
} else {
System.out.println("No Address returned!");
}
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
System.out.println("Canont get Address!");
}
System.out.println("address acquired");
return Add;
}

}
