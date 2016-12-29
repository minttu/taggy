package me.pieso.taggy.services;

import me.pieso.taggy.exceptions.NonImageFileException;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImageUpload {
    private final byte[] data;
    private final byte[] hash;

    public ImageUpload(byte[] data) throws NoSuchAlgorithmException {
        this.data = data;
        this.hash = calculateHash();
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

    public void verifyContentAsImage() throws NonImageFileException {
        try {
            if (ImageIO.read(new ByteArrayInputStream(data)) == null) {
                throw new NonImageFileException();
            }
        } catch (IOException e) {
            throw new NonImageFileException();
        }
    }
}
