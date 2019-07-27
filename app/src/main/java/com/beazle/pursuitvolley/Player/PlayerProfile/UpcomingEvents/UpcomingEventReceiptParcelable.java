package com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents;

import android.os.Parcel;
import android.os.Parcelable;

public class UpcomingEventReceiptParcelable implements Parcelable {

    private String upcomingEventTitle;
    private double upcomingEventPrice;
    private String upcomingEventDate;
    private String upcomingEventBeginTime;
    private String upcomingEventEndTime;

    public UpcomingEventReceiptParcelable(String upcomingEventTitle, double upcomingEventPrice, String upcomingEventDate, String upcomingEventBeginTime, String upcomingEventEndTime) {
        this.upcomingEventTitle = upcomingEventTitle;
        this.upcomingEventPrice = upcomingEventPrice;
        this.upcomingEventDate = upcomingEventDate;
        this.upcomingEventBeginTime = upcomingEventBeginTime;
        this.upcomingEventEndTime = upcomingEventEndTime;
    }

    public String getUpcomingEventTitle() {
        return upcomingEventTitle;
    }

    public double getUpcomingEventPrice() {
        return upcomingEventPrice;
    }

    public String getUpcomingEventDate() {
        return upcomingEventDate;
    }

    public String getUpcomingEventBeginTime() {
        return upcomingEventBeginTime;
    }

    public String getUpcomingEventEndTime() {
        return upcomingEventEndTime;
    }

    public void setUpcomingEventTitle(String upcomingEventTitle) {
        this.upcomingEventTitle = upcomingEventTitle;
    }

    public void setUpcomingEventPrice(double upcomingEventPrice) {
        this.upcomingEventPrice = upcomingEventPrice;
    }

    public void setUpcomingEventDate(String upcomingEventDate) {
        this.upcomingEventDate = upcomingEventDate;
    }

    public void setUpcomingEventBeginTime(String upcomingEventBeginTime) {
        this.upcomingEventBeginTime = upcomingEventBeginTime;
    }

    public void setUpcomingEventEndTime(String upcomingEventEndTime) {
        this.upcomingEventEndTime = upcomingEventEndTime;
    }

    // Parcelling part
    public UpcomingEventReceiptParcelable(Parcel in){
        this.upcomingEventTitle = in.readString();
        this.upcomingEventPrice = in.readDouble();
        this.upcomingEventDate =  in.readString();
        this.upcomingEventBeginTime = in.readString();
        this.upcomingEventEndTime = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UpcomingEventReceiptParcelable createFromParcel(Parcel in) {
            return new UpcomingEventReceiptParcelable(in);
        }

        public UpcomingEventReceiptParcelable[] newArray(int size) {
            return new UpcomingEventReceiptParcelable[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.upcomingEventTitle);
        dest.writeDouble(this.upcomingEventPrice);
        dest.writeString(this.upcomingEventDate);
        dest.writeString(this.upcomingEventBeginTime);
        dest.writeString(this.upcomingEventEndTime);
    }

    @Override
    public String toString() {
        return "UpcomingEventReceiptParcelable{" +
                "event title='" + upcomingEventTitle + '\'' +
                ", event price='" + Double.toString(upcomingEventPrice) + '\'' +
                ", event date='" + upcomingEventDate + '\'' +
                ", event begin time='" + upcomingEventBeginTime + '\'' +
                ", event end time='" + upcomingEventEndTime + '\'' +
                '}';
    }
}
