package com.akarsh.streetfoodfinder4;

import info.androidhive.listviewfeed.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
//import com.example.uploadimagedemo.UploadImageDemo;
//import com.example.uploadimagedemo.UploadImageDemo;
//import com.example.review.R;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class AddVendor extends FragmentActivity implements OnItemClickListener,OnItemSelectedListener {
	 Intent i;
	 
	  //private Spinner spinner1;
	  public boolean check1=false,check2=false;
	  String mCameraFileName;
	  int serverResponseCode = 0;
	  String food;
      private RatingBar ratingBar;
	  InputStream is=null;
		String result=null;
		ProgressDialog dialog = null;
		 EditText vendor,comm;
		 AutoCompleteTextView textView=null;
		String line=null;
		String uid="2";
	 // private static final int CAMERA_REQUEST = 1888;
	  final static int cameraData = 0;
	  private static int RESULT_LOAD_IMAGE = 1; 
	  private ImageView img;
	    private Button b3;
	    String finalpath=null;
	    private Button b1,submit;
	    int n=1;
	    private String newpicFile;
	    GoogleMap map ;
		//String newpicFile="build"+(n++)+".jpg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpost);
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
    	
    	
    	map = ((SupportMapFragment) getSupportFragmentManager()
	              .findFragmentById(R.id.map)).getMap();
    	
    	
		img=(ImageView) findViewById(R.id.imageView1);
		textView=(AutoCompleteTextView)findViewById(R.id.textView6);
		b3=(Button) findViewById(R.id.button2);
		b1=(Button) findViewById(R.id.button1);
		submit=(Button)findViewById(R.id.button3);
		//spinner1 = (Spinner) findViewById(R.id.spinner1);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 check2=true;
						check1=false;
						startActivityForResult(i, RESULT_LOAD_IMAGE);
						
				
				
			}
			
			
			
		});
		//final List<String> list=new ArrayList<String>();
		try
		{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://streettadka.comze.com/onlyfood.php");
	        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        Log.e("pass 1", "connection success ");
	}
	    catch(Exception e)
	{
	    	Log.e("Fail 1", e.toString());
	    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
			Toast.LENGTH_LONG).show();
	}     
	    
	    try
	    {
	     	BufferedReader reader = new BufferedReader
				(new InputStreamReader(is,"iso-8859-1"),8);
	        	StringBuilder sb = new StringBuilder();
	        	while ((line = reader.readLine()) != null)
		{
	   		    sb.append(line + "\n");
	       	}
	        	is.close();
	        	result = sb.toString();
	        Log.e("pass 2", "connection success ");
	}
	    catch(Exception e)
		{
		Log.e("Fail 2", e.toString());
	}     
	   
		try
		{
			int i;
			JSONArray array=new JSONArray(result);
	    //	JSONObject json_data = new JSONObject(result);
			int t=array.length();
			String[] foods=new String[t];
			for(i=0;i<t;i++)
		
			{JSONObject json_data =array.getJSONObject(i);
			foods[i]=(json_data.getString("f_name"));
			};
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,foods);
			textView.setThreshold(1);
			
			textView.setAdapter(adapter);
			textView.setOnItemSelectedListener(this);
			textView.setOnItemClickListener(this);
			
			//list.add(json_data.getString("f_name"));};
	    	//name=(json_data.getString("c_name"));
	    	//System.out.println(name);
	    	//res.setText(name);}
		//Toast.makeText(getBaseContext(), "Name : "+name,
			//Toast.LENGTH_SHORT).show();
		}
	    catch(Exception e)
		{
	    	Log.e("Fail 3", e.toString());
		}
		/*list.add("Item 1");
	    list.add("Item 2");
	    list.add("Item 3");
	    list.add("Item 4");
	    list.add("Item 5");*/

		/*spinner1= (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adp1=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1,list);
	adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner1.setAdapter(adp1);
	spinner1.setOnItemSelectedListener(new OnItemSelectedListener()
	{

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
	   // TODO Auto-generated method stub
	//Toast.makeText(getBaseContext(),list.get(position), Toast.LENGTH_SHORT).show();
	food=list.get(position).toString();
	    }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub

	}

	});
		*/
		
			b1.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					//Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					
				//	startActivityForResult(intent,CAMERA_REQUEST);
					//cameraTake();
					
					
					 
					
			   	         
	    	         
					cameraTake();
					    
					    
	    	        
				}
				
			});
			
			
			submit.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v) {
					
					
					
					dialog = ProgressDialog.show(AddVendor.this, "", "Uploading file...", true);
                    new Thread(new Runnable() {
                           public void run() {
                           	                 
                        int responsef= uploadFile(finalpath);
                        System.out.println("RES : " + responsef);                         
                                         
                   
					vendor=(EditText)findViewById(R.id.editText1);
					String ven_name=vendor.getText().toString();
					comm=(EditText)findViewById(R.id.editText2);
					String comments=comm.getText().toString();
					String img_path="http://streettadka.comze.com/upload_test/uploads/"+newpicFile;
					
					ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
					String rating=String.valueOf(ratingBar.getRating());
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        		nameValuePairs.add(new BasicNameValuePair("img",img_path));
	        		nameValuePairs.add(new BasicNameValuePair("food",food));

	        		nameValuePairs.add(new BasicNameValuePair("vendor",ven_name));
	        		nameValuePairs.add(new BasicNameValuePair("ratings",rating));
	        		nameValuePairs.add(new BasicNameValuePair("comm",comments));
	        		String email=AndroidFacebookConnectActivity.email;
	        		nameValuePairs.add(new BasicNameValuePair("email",email));
	        	    	
	        	    	try
	        	    	{
	        			HttpClient httpclient = new DefaultHttpClient();
	        		        HttpPost httppost = new HttpPost("http://streettadka.comze.com/ins-post.php");
	        		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        		        HttpResponse response = httpclient.execute(httppost); 
	        		        HttpEntity entity = response.getEntity();
	        		        is = entity.getContent();
	        		        Log.e("pass 1", "connection success ");
	        		}
	        	        catch(Exception e)
	        		{
	        	        	Log.e("Fail 1", e.toString());
	        		    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
	        				Toast.LENGTH_LONG).show();
	        		}     
	        	        
	        	        try
	        	        {
	        	         	BufferedReader reader = new BufferedReader
	        					(new InputStreamReader(is,"iso-8859-1"),8);
	        	            	StringBuilder sb = new StringBuilder();
	        	            	while ((line = reader.readLine()) != null)
	        			{
	        	       		    sb.append(line + "\n");
	        	           	}
	        	            	is.close();
	        	            	result = sb.toString();
	        		        Log.e("pass 2", "connection success ");
	        		}
	        	        catch(Exception e)
	        	    	{
	        			Log.e("Fail 2", e.toString());
	        		}     
                           }
                    }).start();  
	        	   	/*try
	        	    	{
	        	   		/*int i;
	        	   		JSONArray array=new JSONArray(result);
	        	        //	JSONObject json_data = new JSONObject(result);
	        	   		int t=array.length();
	        	   		for(i=0;i<t;i++)
	        	    	
	        	   		{JSONObject json_data =array.getJSONObject(i);
	        	        	name=(json_data.getString("c_name"));
	        	        	System.out.println(name);
	        	        	res.setText(name);}
	        			//Toast.makeText(getBaseContext(), "Name : "+name,
	        				//Toast.LENGTH_SHORT).show();
	        	    	}
	        	        catch(Exception e)
	        	    	{
	        	        	Log.e("Fail 3", e.toString());
	        	    	}*/
				}});

				
			//spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
			
			
			//addListenerOnButton();
		//	addListenerOnSpinnerItemSelection();
			
			
			Double lat=12.934268;
			Double lng=77.534326;
			Main.addressOf(lat, lng);
	    	
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(new LatLng(lat,lng)).zoom(14).build();

	map.animateCamera(CameraUpdateFactory
			.newCameraPosition(cameraPosition));
	map.setOnCameraChangeListener(new OnCameraChangeListener() {
	    @Override
	    public void onCameraChange(CameraPosition cameraPosition) {
	      
	    	System.out.println("lat="+cameraPosition.target.latitude);
	    	System.out.println("lng="+cameraPosition.target.longitude);
	
	    	Double myLat=cameraPosition.target.latitude;
	    	Double myLng=cameraPosition.target.longitude;
	    	Main.addressOf(myLat, myLng);
	    	
	    	
			Toast.makeText(AddVendor.this, Main.h,
					Toast.LENGTH_SHORT).show();
	    }
	});

	}
	
	 public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
	            long arg3) {
	        // TODO Auto-generated method stub
	        //Log.d("AutocompleteContacts", "onItemSelected() position " + position);
	    }
	 
	    public void onNothingSelected(AdapterView<?> arg0) {
	        // TODO Auto-generated method stub
	         
	        //InputMethodManager imm = (InputMethodManager) getSystemService(
	            //    INPUT_METHOD_SERVICE);
	          //  imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	 
	    }
	 
	  
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	        // TODO Auto-generated method stub
	         
	        // Show Alert       
	       // Toast.makeText(getBaseContext(), "Position:"+arg2+" Month:"+arg0.getItemAtPosition(arg2),
	         //       Toast.LENGTH_LONG).show();
	         food=arg0.getItemAtPosition(arg2).toString();
	        Log.d("AutocompleteContacts", "Position:"+arg2+" Month:"+arg0.getItemAtPosition(arg2));
	         
	    }
	     
	    protected void onResume() {
	        super.onResume();
	    }
	  
	    protected void onDestroy() {
	        super.onDestroy();
	    }
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		      super.onActivityResult(requestCode, resultCode, data);
		       if(check2)
		       {
		    	   
				if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
		    	         Uri selectedImage = data.getData();
		    	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
		    	 
		    	         Cursor cursor = getContentResolver().query(selectedImage,
		    	                 filePathColumn, null, null, null);
		    	         cursor.moveToFirst();
		    	 
		    	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		    	         String picturePath = cursor.getString(columnIndex);
		    	         System.out.println(picturePath);
		    	         cursor.close();
		    	         img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		       }
				}
		       else  if(check1)
		      {          
		    	 
				if (requestCode == cameraData && resultCode == Activity.RESULT_OK&& null != data)
		    	  {  
		              Bitmap photo = (Bitmap) data.getExtras().get("data"); 
		              img.setImageBitmap(photo);	          
		          }
		    	  Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/MyImages/"+newpicFile);
				    img.setImageBitmap(bitmap);
		      }
		    }
		       
		       //Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/MyImages/"+newpicFile);
			  //  img.setImageBitmap(bitmap);
		       
		  
		  /*public void addListenerOnButton() {
		 
			
			
		  }*/
		 /* public void addListenerOnSpinnerItemSelection() {
				spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
				
			  }*/
	 public int uploadFile(String sourceFileUri) {
	        String upLoadServerUri = "http://streettadka.comze.com/upload_test/upload_media_test.php";
	        String fileName = sourceFileUri;

	        HttpURLConnection conn = null;
	        DataOutputStream dos = null;  
	        String lineEnd = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        int bytesRead, bytesAvailable, bufferSize;
	        byte[] buffer;
	        int maxBufferSize = 1 * 1024 * 1024; 
	        File sourceFile = new File(sourceFileUri); 
	        if (!sourceFile.isFile()) {
	         Log.e("uploadFile", "Source File Does not exist");
	         return 0;
	        }
	            try { // open a URL connection to the Servlet
	             FileInputStream fileInputStream = new FileInputStream(sourceFile);
	             URL url = new URL(upLoadServerUri);
	             conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
	             conn.setDoInput(true); // Allow Inputs
	             conn.setDoOutput(true); // Allow Outputs
	             conn.setUseCaches(false); // Don't use a Cached Copy
	             conn.setRequestMethod("POST");
	             conn.setRequestProperty("Connection", "Keep-Alive");
	             conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	             conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	             conn.setRequestProperty("uploaded_file", fileName); 
	             dos = new DataOutputStream(conn.getOutputStream());
	   
	             dos.writeBytes(twoHyphens + boundary + lineEnd); 
	             dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
	             dos.writeBytes(lineEnd);
	   
	             bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
	   
	             bufferSize = Math.min(bytesAvailable, maxBufferSize);
	             buffer = new byte[bufferSize];
	   
	             // read file and write it into form...
	             bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	               
	             while (bytesRead > 0) {
	               dos.write(buffer, 0, bufferSize);
	               bytesAvailable = fileInputStream.available();
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);               
	              }
	   
	             // send multipart form data necesssary after file data...
	             dos.writeBytes(lineEnd);
	             dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	   
	             // Responses from the server (code and message)
	             serverResponseCode = conn.getResponseCode();
	             String serverResponseMessage = conn.getResponseMessage();
	              
	             Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
	             if(serverResponseCode == 200){
	                 runOnUiThread(new Runnable() {
	                      public void run() {
	                         // tv.setText("File Upload Completed.");
	                          Toast.makeText(AddVendor.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
	                    }
	                 });                
	             }    
	             
	             //close the streams //
	             fileInputStream.close();
	             dos.flush();
	             dos.close();
	              
	        } catch (MalformedURLException ex) {  
	            dialog.dismiss();  
	            ex.printStackTrace();
	            Toast.makeText(this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	        } catch (Exception e) {
	            dialog.dismiss();  
	            e.printStackTrace();
	            Toast.makeText(this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
	            Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
	        }
	        dialog.dismiss();       
	        return serverResponseCode;  
	       } 

	 
	 public String cameraTake(){
		  i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		  
		  File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
		  imagesFolder.mkdirs();
		  String email=AndroidFacebookConnectActivity.email;
			ArrayList<NameValuePair> nameValuePairs= new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("u_email",email));
			String nn = null;
		  try
			{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://streettadka.comze.com/n.php");
		       httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost); 
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        Log.e("pass 1", "connection success ");
		}
		    catch(Exception e)
		{
		    	Log.e("Fail 1", e.toString());
		    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
				Toast.LENGTH_LONG).show();
		}     
		    
		    try
		    {
		     	BufferedReader reader = new BufferedReader
					(new InputStreamReader(is,"iso-8859-1"),8);
		        	StringBuilder sb = new StringBuilder();
		        	while ((line = reader.readLine()) != null)
			{
		   		    sb.append(line + "\n");
		       	}
		        	is.close();
		        	result = sb.toString();
		        Log.e("pass 2", "connection success ");
		}
		    catch(Exception e)
			{
			Log.e("Fail 2", e.toString());
		}     
		   
			try
			{
				int i;
				JSONArray array=new JSONArray(result);
		    //	JSONObject json_data = new JSONObject(result);
				int t=array.length();
				
				for(i=0;i<t;i++)
			
				{JSONObject json_data =array.getJSONObject(i);
				nn=(json_data.getString("n"));
				
				
				
				System.out.println(nn);
				
				};
				
				
			}
		    catch(Exception e)
			{
		    	Log.e("Fail 3", e.toString());
			}
		  
		  
		 
		  newpicFile=email+"-"+nn+".jpg";
		 
		  File image = new File(imagesFolder, newpicFile);
		  finalpath=image.toString();
		  Uri uriSavedImage = Uri.fromFile(image);
		// System.out.println(uriSavedImage);
		 System.out.println(finalpath);
		  i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
		 // String newpicFile="build"+(n++)+".jpg";
			//String outPath="/storage/sdf/"+newpicFile;
		//	i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			
		//	File outFile=new File(outPath);
			
			//mCameraFileName=outFile.toString();
			//Uri outuri=Uri.fromFile(outFile);
		//	i.putExtra(MediaStore.EXTRA_OUTPUT,outuri);
		  check2=false;
			 check1=true;
		  startActivityForResult(i, cameraData);
		  
		  return newpicFile;

		}

}
