package info.androidhive.listviewfeed.data;

public class FeedItem3 {
	private int id;
	private String name, comments, userPic;

	public FeedItem3() {
	}

	public FeedItem3(int id, String name, String comments,String userPic) {
		super();
		this.id = id;
		this.name = name;
		this.comments=comments;
		//this.image = image;
		//this.status = status;
		this.userPic = userPic;
		//this.ratings = ratings;
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
	public String getuserPic() {
		return userPic;
	}
	public String getComments() {
		return comments;
	}
	//public float getRatings() {
	//	return Float.parseFloat(ratings);
	//}
	public void setuserPic(String userPic) {
		this.userPic = userPic;
	}
	public void setComments(String comments) {
		this.comments =comments;
	}
	/*public void setRatings(String ratings) {
		this.ratings = ratings;
	}*/
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
