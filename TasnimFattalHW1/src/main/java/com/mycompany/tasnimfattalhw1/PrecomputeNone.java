/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasnimfattalhw1;

/**
 *
 * @author tasni
 */
class PrecomputeNone {
    private int[] array;

    public PrecomputeNone(int[] array) {
        this.array = array;
    }

    // Belirli bir aralık için minimum değeri sorgula
    public int query(int left, int right) {
        int min = Integer.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            min = Math.min(min, array[i]);
        }
        return min;
    }
}
