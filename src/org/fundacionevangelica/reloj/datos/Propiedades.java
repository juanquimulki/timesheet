/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fundacionevangelica.reloj.datos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author SYSTEM
 */
public class Propiedades {
    private String bd;
    private String pass;
    private int tardanza;
    private int excesiva;
    private int tempranza;

    public Propiedades() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("config.properties"));
            
            this.bd   = p.getProperty("bd");
            this.pass = p.getProperty("pass");
            this.tardanza  = Integer.parseInt(p.getProperty("tardanza"));
            this.excesiva  = Integer.parseInt(p.getProperty("excesiva"));
            this.tempranza = Integer.parseInt(p.getProperty("tempranza"));
        } catch (FileNotFoundException ex) {
            System.out.println("archivo no encontrado");
            this.bd   = "C:/Program Files/C.In.Ti.A. Version Base/Cintia.mdb";
            this.pass = "ngtayeXmr?h";
            this.tardanza  = 5;
            this.excesiva  = 6;
            this.tempranza = 7;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }        
    }
    
    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTardanza() {
        return tardanza;
    }

    public void setTardanza(int tardanza) {
        this.tardanza = tardanza;
    }

    public int getExcesiva() {
        return excesiva;
    }

    public void setExcesiva(int excesiva) {
        this.excesiva = excesiva;
    }

    public int getTempranza() {
        return tempranza;
    }

    public void setTempranza(int tempranza) {
        this.tempranza = tempranza;
    }
}
