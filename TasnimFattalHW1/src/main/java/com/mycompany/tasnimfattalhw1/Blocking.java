/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasnimfattalhw1;

/**
 *
 * @author tasni
 */

class Blocking {
    private int[] array;
    private int[] blockMins;
    private int blockSize;

    public Blocking(int[] array, int blockSize) {
        this.array = array;
        this.blockSize = blockSize;
        int n = array.length;
        int numBlocks = (n + blockSize - 1) / blockSize; // Blok sayısını belirle
        blockMins = new int[numBlocks];

        // Her blok için minimum değeri hesapla
        for (int i = 0; i < numBlocks; i++) {
            int blockStart = i * blockSize;
            int blockEnd = Math.min(blockStart + blockSize, n);
            blockMins[i] = Integer.MAX_VALUE;
            for (int j = blockStart; j < blockEnd; j++) {
                blockMins[i] = Math.min(blockMins[i], array[j]);
            }
        }
    }

    // Belirli bir aralık için minimum değeri sorgula
    public int query(int left, int right) {
        int leftBlock = left / blockSize;
        int rightBlock = right / blockSize;
        int min = Integer.MAX_VALUE;

        if (leftBlock == rightBlock) {
            // Aralık aynı blok içindeyse
            for (int i = left; i <= right; i++) {
                min = Math.min(min, array[i]);
            }
        } else {
            // Sol bloktaki değerleri kontrol et
            for (int i = left; i < (leftBlock + 1) * blockSize; i++) {
                min = Math.min(min, array[i]);
            }
            // Orta bloklardaki değerleri kontrol et
            for (int i = leftBlock + 1; i < rightBlock; i++) {
                min = Math.min(min, blockMins[i]);
            }
            // Sağ bloktaki değerleri kontrol et
            for (int i = rightBlock * blockSize; i <= right; i++) {
                min = Math.min(min, array[i]);
            }
        }

        return min;
    }
}

/*class Blocking {
    private int[] array;
    private int[] blockMins;
    private int blockSize;

    public Blocking(int[] array) {
        this.array = array;
        int n = array.length;
        blockSize = (int) Math.sqrt(n);
        int numBlocks = (n + blockSize - 1) / blockSize;
        blockMins = new int[numBlocks];

        // Her blok için minimum değer hesapla
        for (int i = 0; i < numBlocks; i++) {
            int blockStart = i * blockSize;
            int blockEnd = Math.min(blockStart + blockSize, n);
            blockMins[i] = Integer.MAX_VALUE;
            for (int j = blockStart; j < blockEnd; j++) {
                blockMins[i] = Math.min(blockMins[i], array[j]);
            }
        }
    }

    // Belirli bir aralık için minimum değeri sorgula
    public int query(int left, int right) {
        int leftBlock = left / blockSize;
        int rightBlock = right / blockSize;
        int min = Integer.MAX_VALUE;

        if (leftBlock == rightBlock) {
            for (int i = left; i <= right; i++) {
                min = Math.min(min, array[i]);
            }
        } else {
            for (int i = left; i < (leftBlock + 1) * blockSize; i++) {
                min = Math.min(min, array[i]);
            }
            for (int i = leftBlock + 1; i < rightBlock; i++) {
                min = Math.min(min, blockMins[i]);
            }
            for (int i = rightBlock * blockSize; i <= right; i++) {
                min = Math.min(min, array[i]);
            }
        }

        return min;
    }
}*/
