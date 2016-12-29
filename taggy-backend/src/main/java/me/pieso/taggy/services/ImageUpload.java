package me.pieso.taggy.services;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import me.pieso.taggy.exceptions.NonImageFileException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImageUpload {
    private final byte[] data;
    private final byte[] hash;
    private final String type;

    public ImageUpload(byte[] data) throws NoSuchAlgorithmException, NonImageFileException {
        this.data = data;
        this.hash = calculateHash();
        this.type = this.getImageType();
    }

    private byte[] calculateHash() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getHash() {
        return hash;
    }

    public String getType() {
        return type;
    }

    public String getImageType() throws NonImageFileException {
        FileType fileType;

        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
            if (bufferedImage == null) {
                throw new NonImageFileException();
            }

            fileType = FileTypeDetector.detectFileType(new BufferedInputStream(new ByteArrayInputStream(data)));
        } catch (IOException e) {
            throw new NonImageFileException();
        }

        switch (fileType) {
            case Jpeg:
                return "jpg";
            case Png:
                return "png";
            case Gif:
                return "gif";
            case Unknown:
            default:
                throw new NonImageFileException();
        }
    }
}
