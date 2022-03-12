package com.catphoto.awsimageupload.fileStore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

//store images (or anything)
@Service
public class FileStore {
    private final AmazonS3 s3;

    //dependency injection
    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    /*the method below saves file (image) to amazon s3 bucket
    * metadate can include content type, content length
    * inputSteam is what get save to amazon s3 bucket*/
    public void save(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream){
        //if metadata is present, we use the below method to add metadata
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map->{
            if(!map.isEmpty()) {
                map.forEach((key, value) -> metadata.addUserMetadata(key, value));
            }
        });

        try{
            s3.putObject(path, fileName, inputStream, metadata);
        }catch(AmazonServiceException e){
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

//download an image from s3
    public byte[] download(String path, String key) {
        try{
            S3Object object =  s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        }catch(AmazonServiceException | IOException e){
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }
}
