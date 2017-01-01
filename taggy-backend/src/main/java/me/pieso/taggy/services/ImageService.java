package me.pieso.taggy.services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    public void saveFull(ImageUpload imageUpload) throws IOException {
        String hexHash = imageUpload.getHexHash();
        File file = new File(Paths.get(fileStorageLocation,
                "image-full",
                hexHash + "." + imageUpload.getType()
        ).toUri());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imageUpload.getData());
        fos.close();
    }

    public void saveThumbnail(ImageUpload imageUpload) throws IOException {
        String hexHash = imageUpload.getHexHash();
        File file = new File(Paths.get(fileStorageLocation,
                "image-thumbnail",
                hexHash + "." + imageUpload.getType()
        ).toUri());
        BufferedImage image = imageUpload.getThumbnail();
        save(image, imageUpload.getType(), file);
    }

    private void save(BufferedImage image, String type, File file) throws IOException {
        switch (type) {
            case "jpg":
                saveJPG(image, file);
                break;
            case "png":
                savePNG(image, file);
                break;
            case "gif":
                saveGIF(image, file);
                break;
        }
    }

    private void savePNG(BufferedImage image, File file) throws IOException {
        ImageIO.write(image, "png", file);
    }

    private void saveGIF(BufferedImage image, File file) throws IOException {
        ImageIO.write(image, "gif", file);
    }

    private void saveJPG(BufferedImage image, File file) throws IOException {
        BufferedImage convertedImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        convertedImg.getGraphics().drawImage(image, 0, 0, null);
        convertedImg.getGraphics().dispose();

        ImageIO.write(convertedImg, "jpg", file);
    }

}
