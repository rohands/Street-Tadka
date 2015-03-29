package info.androidhive.listviewfeed.data;

public class FeedItem2 {
	private int id;
	private String name, location, profilePic, ratings;

	public FeedItem2() {
	}

	public FeedItem2(int id, String name, String location,String profilePic, String ratings) {
		super();
		this.id = id;
		this.name = name;
		this.location=location;
		//this.image = image;
		//this.status = status;
		this.profilePic = profilePic;
		this.ratings = ratings;
		//this.url = url;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public String getImge() {
		return image;
	}

	public void setImge(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
*/
	public String getProfilePic() {
		return profilePic;
	}
	public String getLocation() {
		return location;
	}
	public float getRatings() {
		return Float.parseFloat(ratings);
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
/*
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}*/
}
