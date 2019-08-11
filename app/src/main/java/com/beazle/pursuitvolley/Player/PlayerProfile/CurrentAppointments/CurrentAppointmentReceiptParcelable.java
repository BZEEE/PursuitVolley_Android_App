package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

import android.os.Parcel;
import android.os.Parcelable;

import com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents.UpcomingEventReceiptParcelable;

public class CurrentAppointmentReceiptParcelable implements Parcelable {

    private String currentAppointmentCoachName;
    private String currentAppointmentCoachUid;
    private double currentAppointmentPrice;
    private long currentAppointmentDate;
    private String currentAppointmentBeginTime;
    private String currentAppointmentEndTime;
    private String currentAppointmentLocation;

    public CurrentAppointmentReceiptParcelable() {

    }

    public CurrentAppointmentReceiptParcelable(
            String currentAppointmentCoachName,
            String currentAppointmentCoachUid,
            double cuurentAppointmentPrice,
            long currentAppointmentDate,
            String currentAppointmentBeginTime,
            String currentAppointmentEndTime,
            String currentAppointmentLocation) {
        this.currentAppointmentCoachName = currentAppointmentCoachName;
        this.currentAppointmentCoachUid = currentAppointmentCoachUid;
        this.currentAppointmentPrice = cuurentAppointmentPrice;
        this.currentAppointmentDate = currentAppointmentDate;
        this.currentAppointmentBeginTime = currentAppointmentBeginTime;
        this.currentAppointmentEndTime = currentAppointmentEndTime;
        this.currentAppointmentLocation = currentAppointmentLocation;
    }

    public String getCurrentAppointmentCoachName() {
        return currentAppointmentCoachName;
    }

    public String getCurrentAppointmentCoachUid() {
        return currentAppointmentCoachUid;
    }

    public double getCuurentAppointmentPrice() {
        return currentAppointmentPrice;
    }

    public long getCurrentAppointmentDate() {
        return currentAppointmentDate;
    }

    public String getCurrentAppointmentBeginTime() {
        return currentAppointmentBeginTime;
    }

    public String getCurrentAppointmentEndTime() {
        return currentAppointmentEndTime;
    }

    public String getCurrentAppointmentLocation() {
        return currentAppointmentLocation;
    }

    public void setCurrentAppointmentCoachName(String currentAppointmentCoachName) {
        this.currentAppointmentCoachName = currentAppointmentCoachName;
    }

    public void setCurrentAppointmentCoachUid(String currentAppointmentCoachUid) {
        this.currentAppointmentCoachUid = currentAppointmentCoachUid;
    }

    public void setCuurentAppointmentPrice(double cuurentAppointmentPrice) {
        this.currentAppointmentPrice = cuurentAppointmentPrice;
    }

    public void setCurrentAppointmentDate(long currentAppointmentDate) {
        this.currentAppointmentDate = currentAppointmentDate;
    }

    public void setCurrentAppointmentBeginTime(String currentAppointmentBeginTime) {
        this.currentAppointmentBeginTime = currentAppointmentBeginTime;
    }

    public void setCurrentAppointmentEndTime(String currentAppointmentEndTime) {
        this.currentAppointmentEndTime = currentAppointmentEndTime;
    }

    public void setCurrentAppointmentLocation(String currentAppointmentLocation) {
        this.currentAppointmentLocation = currentAppointmentLocation;
    }

    // Parcelling part
    public CurrentAppointmentReceiptParcelable(Parcel in){
        this.currentAppointmentCoachName = in.readString();
        this.currentAppointmentCoachUid = in.readString();
        this.currentAppointmentPrice = in.readDouble();
        this.currentAppointmentDate =  in.readLong();
        this.currentAppointmentBeginTime = in.readString();
        this.currentAppointmentEndTime = in.readString();
        this.currentAppointmentLocation = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CurrentAppointmentReceiptParcelable createFromParcel(Parcel in) {
            return new CurrentAppointmentReceiptParcelable(in);
        }

        public CurrentAppointmentReceiptParcelable[] newArray(int size) {
            return new CurrentAppointmentReceiptParcelable[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currentAppointmentCoachName);
        dest.writeString(this.currentAppointmentCoachUid);
        dest.writeDouble(this.currentAppointmentPrice);
        dest.writeLong(this.currentAppointmentDate);
        dest.writeString(this.currentAppointmentBeginTime);
        dest.writeString(this.currentAppointmentEndTime);
        dest.writeString(this.currentAppointmentLocation);
    }

    @Override
    public String toString() {
        return "UpcomingEventReceiptParcelable{" +
                "appointment title='" + this.currentAppointmentCoachName + '\'' +
                ", appointment price='" + Double.toString(this.currentAppointmentPrice) + '\'' +
                ", appointment date='" + Long.toString(this.currentAppointmentDate) + '\'' +
                ", appointment begin time='" + this.currentAppointmentBeginTime + '\'' +
                ", appointment end time='" + this.currentAppointmentEndTime + '\'' +
                ", appointment location='" + this.currentAppointmentLocation + '\'' +
                '}';
    }
}
