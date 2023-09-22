package com.luxfacta.vernyy.api.shared;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import jakarta.xml.bind.DatatypeConverter;


public class ImageUtils {
	
	public static String scaleImage(String base64, int width, int height) throws IOException {
		return base64;
		/*
		
		if (base64 == null || base64.indexOf(",") < 0)
			return base64;

		BufferedImage image = null;
		byte[] imageByte = DatatypeConverter.parseBase64Binary(base64.split(",")[1]);
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream os = null;
		try {
			bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			
			BufferedImage scaledImage = scaleImage(image, width, height);
			
			os = new ByteArrayOutputStream();
			ImageIO.write(scaledImage, "PNG", os);
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(os.toByteArray());
			
		} finally {
			if (bis != null)
				bis.close();
			if (os != null)
				os.close();
			
		}
		*/
		
	}

	
	public static String _rotateIfLandscape(String base64) throws IOException {
		if (base64 == null || base64.indexOf(",") < 0)
			return base64;

		BufferedImage image = null;
		byte[] imageByte = DatatypeConverter.parseBase64Binary(base64.split(",")[1]);
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream os = null;
		try {
			bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			
			if (image.getHeight() < image.getWidth()) {
				BufferedImage rotatedImage = _rotate(image, 90);
				
				os = new ByteArrayOutputStream();
				ImageIO.write(rotatedImage, "PNG", os);
				return "data:image/png;base64," + Base64.getEncoder().encodeToString(os.toByteArray());
			}
			return base64;
			
		} finally {
			if (bis != null)
				bis.close();
			if (os != null)
				os.close();
			
		}
		
	}

	
	public static byte[] base64ToJPEG(String base64, int width, int height) throws IOException {
		if (base64 == null || base64.indexOf(",") < 0)
			return new byte[0];

		return DatatypeConverter.parseBase64Binary(base64.split(",")[1]);
		
		/*
		BufferedImage image = null;
		byte[] imageByte = DatatypeConverter.parseBase64Binary(base64.split(",")[1]);
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream os = null;
		try {
			bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			
			BufferedImage scaledImage = scaleImage(image, width, height);
			
			os = new ByteArrayOutputStream();
			ImageIO.write(scaledImage, "PNG", os);
			return os.toByteArray();
			
		} finally {
			if (bis != null)
				bis.close();
			if (os != null)
				os.close();
			
		}
		*/
		
	}

	private static BufferedImage _rotate(BufferedImage img, int degrees)
    {
		double angle = Math.toRadians(degrees);
		
		double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = img.getWidth(), h = img.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
 
        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
 
        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();
        try {
        	g2.translate((neww - w) / 2, (newh - h) / 2);
            g2.rotate(angle, w / 2, h / 2);
            g2.drawRenderedImage(img, null);
        } finally {
        	g2.dispose();
        }
        
        // Return rotated buffer image
        return newImage;
    }
	
	
	private static BufferedImage scaleImage(BufferedImage img, int width, int height) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        if (imgWidth == width || imgHeight == height)
        	return img;
        
        if (imgWidth*height < imgHeight*width) {
            width = imgWidth*height/imgHeight;
        } else {
            height = imgHeight*width/imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        try {
        	
        	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        	//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            //        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            //g.setBackground(Color.);
            //g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }
}
