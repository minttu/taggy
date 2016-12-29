package me.pieso.taggy.services;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageService {
    private final String fileStorageLocation;

    public ImageService(String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
        createDirectories();
    }

    private void createDirectory(String name) {
        File dir = new File(fileStorageLocation, name);
        dir.mkdirs();
    }

    private void createDirectories() {
        createDirectory("image-thumbnail");
        createDirectory("image-full");
    }

    public void save(ImageUpload imageUpload) throws IOException {
        String hexHash = DatatypeConverter.printHexBinary(imageUpload.getHash());
        File file = new File(Paths.get(fileStorageLocation,
                "image-full",
                hexHash + "." + imageUpload.getType()
        ).toUri());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imageUpload.getData());
        fos.close();
    }

}
