package com.catphoto.awsimageupload.bucket;

public enum BucketName {
    //below is the s3 bucket where the image will be stored
    PROFILE_IMAGE("pet-image-upload-123");

    //we can only use the getter method in enum BucketName to get the value below
    private final String bucketName;

    //the constructor below creates an enum with the given bucket name
    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    //below is the getter to get the bucketName finalized in constructor
    public String getBucketName(){
        return bucketName;
    }
}
