package com.parse.starter1;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemsModel implements Parcelable {

    private String imagefile1,imagefile2,imagefile3,imagefile4;
    private String title,availibilty,date, newPrice,oldPrice, objectId, desc,discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAvailibilty() {
        return availibilty;
    }

    public void setAvailibilty(String availibilty) {
        this.availibilty = availibilty;
    }

    public String getImagefile1() {
        return imagefile1;
    }

    public void setImagefile1(String imagefile1) {
        this.imagefile1 = imagefile1;
    }

    public String getImagefile2() {
        return imagefile2;
    }

    public void setImagefile2(String imagefile2) {
        this.imagefile2 = imagefile2;
    }

    public String getImagefile3() {
        return imagefile3;
    }

    public void setImagefile3(String imagefile3) {
        this.imagefile3 = imagefile3;
    }

    public String getImagefile4() {
        return imagefile4;
    }

    public void setImagefile4(String imagefile4) {
        this.imagefile4 = imagefile4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ItemsModel(Parcel in) {
        // TODO Auto-generated constructor stub
        String[] array = new String[7];
        in.readStringArray(array);
        title = array[0];
        date = array[1];
        newPrice = array[2];
        oldPrice = array[3];
        imagefile1 = array[4];
        imagefile2 = array[5];
        imagefile3 = array[6];
        imagefile4 = array[7];
        objectId = array[8];
        desc = array[9];
    }

    public ItemsModel() {

    }


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeStringArray(new String[]{this.title,this.date, this.newPrice,this.oldPrice,this.imagefile1 ,
                this.imagefile2 ,this.imagefile3,this.imagefile4, this.objectId,this.desc});
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<ItemsModel> CREATOR = new Creator<ItemsModel>() {
        public ItemsModel createFromParcel(Parcel in) {
            return new ItemsModel(in);
        }

        public ItemsModel[] newArray(int size) {
            return new ItemsModel[size];
        }
    };
}
