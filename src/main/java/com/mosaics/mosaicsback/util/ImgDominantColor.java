package com.mosaics.mosaicsback.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class ImgDominantColor {

    private ImgDominantColor() {throw new IllegalStateException("Utility class");}

    public static String getHexColor(byte[] img) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));

        Map<Integer, Integer> colorMap = new HashMap<>();
        int height = image.getHeight();
        int width = image.getWidth();

        IntStream.range(0, width)
                .flatMap(i -> IntStream.range(0, height)
                        .map(j -> image.getRGB(i, j)))
                .filter(rgb -> !isGray(getRGBArr(rgb)))
                .forEach(rgb -> colorMap.put(rgb, colorMap.getOrDefault(rgb, 0) + 1));


        return getMostCommonColor(colorMap);
    }

    private static String getMostCommonColor(Map<Integer, Integer> map) {
        Map.Entry<Integer, Integer> entry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(NoSuchElementException::new);

        int[] rgb = getRGBArr(entry.getKey());

        return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
    }


    private static int[] getRGBArr(int pixel) {
        return new int[]{
                (pixel >> 16) & 0xff,
                (pixel >> 8) & 0xff,
                (pixel) & 0xff
        };
    }

    private static boolean isGray(int[] rgbArr) {
        int rgDiff = Math.abs(rgbArr[0] - rgbArr[1]);
        int rbDiff = Math.abs(rgbArr[0] - rgbArr[2]);

        int tolerance = 10;
        return rgDiff <= tolerance && rbDiff <= tolerance;
    }
}
