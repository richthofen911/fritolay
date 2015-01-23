package io.ap1.fritolays.fritolaysapp;

import java.net.URI;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import io.ap1.fritolays.fritolaysapp.Util;

/**
 * 
 * @author Samson Kirk-Koffi
 * @author Ramanathan
 * @since 01/14/2014
 * 
 */
public final class User implements Parcelable {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -5025553011649744965L;

    public Date createdAt = null;
    public String description = null;
    public String address = null;
    public String phoneNumber = null;
    public int statusesCount;
    public long id;
    public String screenName = null;
    public String name = null;
    public URI website = null;
    public URI profileImageUrl = null;
    public URI profileBannerUrl = null;
    public int friendsCount;
    public int followersCount;
    
    public User() {
    } // end User

    public User(JSONObject obj) {
        try {
            // id
            id = obj.getInt("id");
            // name
            name = Util.unencode(Util.jsonGet("name", obj));
            // screen name
            String sn = Util.jsonGet("screen_name", obj);
            screenName = sn;
            // description
            description = Util.unencode(Util.jsonGet("description", obj));
            // address
            address = Util.unencode(Util.jsonGet("address", obj));

            // phone number
            phoneNumber = obj.optString("phone_number");
            // profile image
            String img = Util.jsonGet("profile_image_url", obj);
            profileImageUrl = img == null ? null : Util.URI(img);
            // banner image
            String bimg = Util.jsonGet("profile_banner_url", obj);
            profileBannerUrl = bimg == null ? null : Util.URI(img);
            // websiteurl
            String url = Util.jsonGet("website", obj);
            website = url == null ? null : Util.URI(url);

            // date
            String c = Util.jsonGet("created_at", obj);
            createdAt = c == null ? null : Util.parseDate(c); // null
                                                              // when
                                                              // fetching
                                                              // relationship-info
            statusesCount = obj.optInt("statuses_count");
        } catch (JSONException e) {
        } catch (NullPointerException e) {
            // throw new TwitterException(e + " from <" + obj + ">, <" + status
            // + ">\n\t" + e.getStackTrace()[0] + "\n\t"
            // + e.getStackTrace()[1]);
        } // end try-catch
    } // end User

    @Override
    public int describeContents() {
        return 0;
    } // end describeContents

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeValue(createdAt);
        dest.writeString(description);
        dest.writeLong(id);
        dest.writeString(phoneNumber);
        dest.writeValue(profileBannerUrl);
        dest.writeValue(profileImageUrl);
        dest.writeString(screenName);
        dest.writeInt(statusesCount);
        dest.writeValue(website);
        dest.writeInt(friendsCount);
    } // end writeToParcel

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str
        .append("name: ").append(name).append("|")
        .append("address: ").append(address).append("|")
        .append("createdAt: ").append(createdAt).append("|")
        .append("description: ").append(description).append("|")
        .append("id: ").append(id).append("|")
        .append("phone_number: ").append(phoneNumber).append("|")
        .append("profileBannerUrl: ").append(profileBannerUrl).append("|")
        .append("profileImageUrl: ").append(profileImageUrl).append("|")
        .append("screenName: ").append(screenName).append("|")
        .append("statusesCount: ").append(statusesCount).append("|")
        .append("website: ").append(website).append("|")
        .append("friendsCount: ").append(friendsCount)
        .append("followersCount: ").append(followersCount);
        return str.toString();
    } // end toString

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in){
            return new User(in);
        } // end createFromParcel

        public User[] newArray(int size){
            return new User[size];
        } // end newArray
    };

    private User(Parcel in) {
        name = in.readString();
        address = in.readString();
        createdAt = (Date) in.readValue(Date.class.getClassLoader());
        description = in.readString();
        id = in.readInt();
        phoneNumber = in.readString();
        profileBannerUrl = (URI) in.readValue(URI.class.getClassLoader());
        profileImageUrl = (URI) in.readValue(URI.class.getClassLoader());
        screenName = in.readString();
        statusesCount = in.readInt();
        website = (URI) in.readValue(URI.class.getClassLoader());
        friendsCount = in.readInt();
        followersCount = in.readInt();
    } // end User
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id2) {
        this.id = id2;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getWebsite() {
        return website;
    }

    public void setWebsite(URI website) {
        this.website = website;
    }

    public URI getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(URI profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public URI getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(URI profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

} // end User
