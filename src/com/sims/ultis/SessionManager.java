/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.ultis;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SessionManager {
private static String currentEmployee;

    public static String getCurrentEmployee() {
        return currentEmployee;
    }

    public static void setCurrentEmployee(String currentEmployee, String encodedPassword) {
        SessionManager.currentEmployee = currentEmployee;
    }

   
}
