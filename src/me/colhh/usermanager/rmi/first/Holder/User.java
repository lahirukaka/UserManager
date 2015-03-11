
/*
 * Copyright (c) 2015 Lahiru Udana <lahirukaka@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Lahiru Udana <lahirukaka@gmail.com> - initial API and implementation and/or initial documentation
 */

package me.colhh.usermanager.rmi.first.Holder;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class User implements Serializable{

    private int uid;
    private String uname;
    private String hash = "";
    private long last_login = 0L;
    private boolean logged = false;
    private boolean iswritable = false;
   
    protected User(){}
    
    public User(int uid, String uname)
    {
        this.uid = uid;
        this.uname = uname;
    }
    
    /*-----------Getters and Setters---------*/
    
    protected void setUid(int uid) {
        this.uid = uid;
    }

    protected void setUname(String uname) {
        this.uname = uname;
    }

    public boolean Iswritable() {
        return iswritable;
    }

    public void setIsWritable(boolean iswritable) {
        this.iswritable = iswritable;
    }
    
    public int getUID()
    {
        return uid;
    }
    
    public String getUname()
    {
        return uname;
    }
    
    public String getPasswordHash()
    {
        return hash;
    }
    
    public long getLastLogin()
    {
        return last_login;
    }
    
    public boolean isLogged()
    {
        return logged;
    }
    
    public User setPassword(String hash)
    {
        this.hash = hash;
        return this;
    }
    
    public User setPasswordHash(String password) throws NoSuchAlgorithmException,
            UnsupportedEncodingException
    {
        hash = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte [] hashb = md.digest();
        for( byte cha : hashb)
        {
            this.hash += Integer.toHexString(0xFF & cha);
        }
        return this;
    }
    
    public User setLastLoginNow()
    {
        last_login = System.currentTimeMillis();
        return this;
    }
    
    public User setLastLogin(long time)
    {
        last_login = time;
        return this;
    }
    
    public User setLogged(boolean logged)
    {
        this.logged = logged;
        return this;
    }
    
    /*------------------------------------------------------------------------*/
    
    @Override
    public String toString()
    {
        return "\nUID -> " + uid + "\nUsername -> " + uname + "\nLast Login -> " 
                + last_login + "\nIs Logged in -> " + logged;
    }
}