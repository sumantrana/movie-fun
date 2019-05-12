package org.superbiz.moviefun.blobstore;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class S3Store implements BlobStore {

    private final AmazonS3Client s3Client;
    private final String photoStorageBucket;
    private final Tika tika = new Tika();

    public S3Store( AmazonS3Client s3Client, String photoStorageBucket){
        this.photoStorageBucket = photoStorageBucket;
        this.s3Client = s3Client;
    }

    @Override
    public void put(Blob blob) throws IOException {
        s3Client.putObject( this.photoStorageBucket, blob.name, blob.inputStream, new ObjectMetadata());
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {

        if ( ! s3Client.doesObjectExist(this.photoStorageBucket, name) ) {
            return Optional.empty();
        }

        try(S3Object s3Object = s3Client.getObject(this.photoStorageBucket, name)){

            S3ObjectInputStream s3ObjectContent = s3Object.getObjectContent();

            byte[] imageBytes = IOUtils.toByteArray(s3ObjectContent);

            return Optional.of( new Blob( name, new ByteArrayInputStream(imageBytes), tika.detect(imageBytes)));
        }
    }

    @Override
    public void deleteAll() {
        List<S3ObjectSummary> summaries = s3Client
                .listObjects(photoStorageBucket)
                .getObjectSummaries();

        for (S3ObjectSummary summary : summaries) {
            s3Client.deleteObject(photoStorageBucket, summary.getKey());
        }
    }
}
