/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.colhh.usermanager.rmi.first.controller;

import java.rmi.Remote;
import java.util.ArrayList;
import me.colhh.usermanager.rmi.first.Holder.User;

/**
 *
 * @author user
 */
public interface UserControllerInterface extends Remote{
    
    public int addUser(User user) throws Exception;
    public int getNextUid() throws Exception;
    public User deleteUser(String uname) throws Exception;
    public ArrayList<User> queryUser(String sql, Object... params) throws Exception;
    public User updateUserInfo(User user) throws Exception;
}
