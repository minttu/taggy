package me.pieso.taggy.services;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import me.pieso.taggy.exceptions.NonImageFileException;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
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
        this.type = resolveImageType();
    }

    private byte[] calculateHash() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }


    private String resolveImageType() throws NonImageFileException {
        FileType fileType;

        try {
            Image image = getImage();
            if (image == null) {
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

    public byte[] getData() {
        return data;
    }

    public byte[] getHash() {
        return hash;
    }

    public String getType() {
        return type;
    }

    public String getHexHash() {
        return DatatypeConverter.printHexBinary(getHash());
    }

    public BufferedImage getImage() throws IOException {
        return ImageIO.read(new ByteArrayInputStream(data));
    }

    public BufferedImage getThumbnail() throws IOException {
        return Scalr.resize(getImage(), Scalr.Method.AUTOMATIC, 200, 200);
    }
}
