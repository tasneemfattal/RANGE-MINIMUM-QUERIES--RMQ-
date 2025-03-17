/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasnimfattalhw1;

/**
 *
 * @author tasni
 */
class PrecomputeAll {
    private int[][] precomputed;

    public PrecomputeAll(int[] array) {
        int n = array.length;
        precomputed = new int[n][n];

        // Tüm aralıklar için minimum değerleri hesapla
        for (int i = 0; i < n; i++) {
            precomputed[i][i] = array[i];
            for (int j = i + 1; j < n; j++) {
                precomputed[i][j] = Math.min(precomputed[i][j - 1], array[j]);
            }
        }
    }

    // Belirli bir aralık için minimum değeri sorgula
    public int query(int left, int right) {
        return precomputed[left][right];
    }
}

