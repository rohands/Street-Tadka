package com.akarsh.streetfoodfinder4;

import info.androidhive.listviewfeed.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class AndroidFacebookConnectActivity extends Activity {

	// Your Facebook APP ID
	private static String APP_ID = "1446551692261358"; // Replace with your App ID
	static String name;
	public static String email;
	static Uri pro;
	static String userId;
	static Bitmap bitmap;
	 InputStream is=null;
		String result=null;
		String line=null;

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	public static AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

	// Buttons
	Button btnFbLogin;
	Button btnFbGetProfile;
	//Button btnPostToWall;
	//Button btnShowAccessTokens;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		StrictMode.ThreadPolicy policy = 
    	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		btnFbLogin = (Button) findViewById(R.id.btn_fblogin);
		btnFbGetProfile = (Button) findViewById(R.id.btn_get_profile);
	//	btnPostToWall = (Button) findViewById(R.id.btn_fb_post_to_wall);
	//	btnShowAccessTokens = (Button) findViewById(R.id.btn_show_access_tokens);
		mAsyncRunner = new AsyncFacebookRunner(facebook);

		/**
		 * Login button Click event
		 * */
		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				loginToFacebook();
				
			}
		});

		/**
		 * Getting facebook Profile info
		 * */
		btnFbGetProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getProfileInformation();
			 	
			}
		});

		/**
		 * Posting to Facebook Wall
		 * */
	/*	btnPostToWall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				postToWall();
			}
		});*/

		/**
		 * Showing Access Tokens
		 * */
	/*	btnShowAccessTokens.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showAccessTokens();
			}
		});*/

	}

	/**
	 * Function to login into facebook
	 * */
	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			
			btnFbLogin.setVisibility(View.INVISIBLE);
			
			// Making get profile button visible
			btnFbGetProfile.setVisibility(View.VISIBLE);

			// Making post to wall visible
			//btnPostToWall.setVisibility(View.VISIBLE);

			// Making show access tokens button visible
		//	btnShowAccessTokens.setVisibility(View.VISIBLE);

			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream","user_photos"},
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();

							// Making Login button invisible
							btnFbLogin.setVisibility(View.INVISIBLE);

							// Making logout Button visible
							btnFbGetProfile.setVisibility(View.VISIBLE);

							// Making post to wall visible
							//btnPostToWall.setVisibility(View.VISIBLE);

							// Making show access tokens button visible
							//btnShowAccessTokens.setVisibility(View.VISIBLE);
							
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}


	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	
	public static Bitmap getFacebookProfilePicture(String userID) throws SocketException, SocketTimeoutException, MalformedURLException, IOException, Exception
	{
	   String imageURL;

	   Bitmap bitmap = null;
	   imageURL = "https://graph.facebook.com/"+userID+"/picture?type=large";
	   InputStream in = (InputStream) new URL(imageURL).getContent();
	   bitmap = BitmapFactory.decodeStream(in);

	   return bitmap;
	}

	
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject public_profile = new JSONObject(json);
					
					// getting name of the user
					name = public_profile.getString("name");
					
					// getting email of the user
					email = public_profile.getString("email");
					
					userId=public_profile.getString("id");
					
					try {
						  bitmap = getFacebookProfilePicture(userId);
					} catch (SocketTimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				//	pro=Uri.parse(profile.getString("link"));
					//pro=Uri.parse(profile.getString("picture"));
					
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							System.out.println("helllla");
							Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
							
							ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(1);
							nameValuePairs1.add(new BasicNameValuePair("u_name",name));
							nameValuePairs1.add(new BasicNameValuePair("u_img","https://graph.facebook.com/"+userId+"/picture?type=large"));
							nameValuePairs1.add(new BasicNameValuePair("u_email",email));
							try
							{
							HttpClient httpclient = new DefaultHttpClient();
						        HttpPost httppost = new HttpPost("http://streettadka.comze.com/ins-user.php");
						        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
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
							
							Intent intent=new Intent(AndroidFacebookConnectActivity.this,com.akarsh.streetfoodfinder4.Main.class);
				           	startActivity(intent);
						}

					});

					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	/**
	 * Function to post to facebook wall
	 * */
	public void postToWall() {
		// post on user's wall.
		facebook.dialog(this, "feed", new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}
		});

	}

	/**
	 * Function to show Access Tokens
	 * */
	public void showAccessTokens() {
		String access_token = facebook.getAccessToken();

		Toast.makeText(getApplicationContext(),
				"Access Token: " + access_token, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Function to Logout user from Facebook
	 * */
	public void hello()
	{
		logoutFromFacebook();
	}
	public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// make Login button visible
							btnFbLogin.setVisibility(View.VISIBLE);

							// making all remaining buttons invisible
							btnFbGetProfile.setVisibility(View.INVISIBLE);
							//btnPostToWall.setVisibility(View.INVISIBLE);
							//btnShowAccessTokens.setVisibility(View.INVISIBLE);
						}

					});

				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}
}

