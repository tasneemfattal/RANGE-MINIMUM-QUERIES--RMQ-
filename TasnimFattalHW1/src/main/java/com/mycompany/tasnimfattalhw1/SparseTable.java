/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasnimfattalhw1;

/**
 *
 * @author tasni
 */
class SparseTable {
    private int[][] sparseTable;
    private int[] log;

    public SparseTable(int[] array) {
        int n = array.length;
        int maxLog = (int) (Math.log(n) / Math.log(2)) + 1;
        sparseTable = new int[n][maxLog];
        log = new int[n + 1];

        // Log değerlerini hesapla
        for (int i = 2; i <= n; i++) {
            log[i] = log[i / 2] + 1;
        }

        // Taban değerlerini hesapla
        for (int i = 0; i < n; i++) {
            sparseTable[i][0] = array[i];
        }

        // Sparse table hesapla
        for (int j = 1; j < maxLog; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                sparseTable[i][j] = Math.min(sparseTable[i][j - 1], sparseTable[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    // Belirli bir aralık için minimum değeri sorgula
    public int query(int left, int right) {
        int length = right - left + 1;
        int j = log[length];
        return Math.min(sparseTable[left][j], sparseTable[right - (1 << j) + 1][j]);
    }
}