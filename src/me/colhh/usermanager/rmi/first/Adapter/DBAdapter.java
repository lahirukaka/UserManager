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

package me.colhh.usermanager.rmi.first.Adapter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import me.colhh.mysqlmanager.MysqlAdapter;
import me.colhh.usermanager.rmi.first.Holder.User;
import me.colhh.usermanager.rmi.first.Holder.UserException;
import me.colhh.usermanager.rmi.first.Holder.UserException.UserExceptionCodes;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class DBAdapter {

    /**
     * Returns next available UID for the user
     * @return next available UID
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException 
     */
    public static int getNextUid() throws SQLException, ClassNotFoundException,
            IOException
    {
        ResultSet res = MysqlAdapter.querySql("SELECT `UID` FROM `users` ORDER "
                + "BY `uid` DESC LIMIT 1;");
        if(res == null) return 1;
        return (res.getInt(1) + 1);
    }
    
    /**
     * Add a not existing user to the Database
     * @param user User object
     * @return Number of updated rows
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    public static int addUser(User user) throws SQLException,
            ClassNotFoundException, IOException, NoSuchAlgorithmException, UserException
    {
        if(!MysqlAdapter.exists("SELECT `uname` FROM `users` WHERE `uname`=?", user.getUname()))
        {
            return MysqlAdapter.executeSql("INSERT INTO `users` VALUES(?,?,?,0,0);", 
                    user.getUID(),user.getUname(),user.getPasswordHash());
        }
        throw new UserException(UserExceptionCodes.USER_EXISTS);
    }
    
    /**
     * Delete a user from DB if user exists
     * @param uname username to be deleted
     * @return Deleted User object
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws UserException 
     */
    public static User deleteUser(String uname) throws SQLException,
            ClassNotFoundException, IOException, UserException
    {
        if(MysqlAdapter.exists("SELECT `uname` FROM `users` WHERE `uname`=?", uname))
        {
            User user = queryUser("SELECT * FROM `users` WHERE `uname`=?", uname).get(0);
            MysqlAdapter.executeSql("DELETE FROM `users` WHERE `uname`=?", uname);
            return user;
        }
        throw new UserException(UserExceptionCodes.USER_NOT_EXISTING);
    }
    
    /**
     * Queries user information from DB
     * @param sql SQL to be executed
     * @param params Values for the prepared statement if any
     * @return  ArrayList of User objects
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException 
     * @throws me.colhh.usermanager.rmi.first.Holder.UserException 
     */
    public static ArrayList<User> queryUser(String sql, Object... params) 
            throws UserException, SQLException, ClassNotFoundException, IOException
    {
        ArrayList<User> list = new ArrayList<>();
        ResultSet res = MysqlAdapter.querySql(sql, params);

        if(res.first())
        {
            User user;
            do
            {
                user = new User(res.getInt(Contracts.FIELD_NAME_UID), 
                        res.getString(Contracts.FIELD_NAME_USERNAME))
                        .setLastLogin(res.getLong(Contracts.FIELD_NAME_LAST_LOGIN))
                        .setLogged(res.getBoolean(Contracts.FIELD_NAME_IS_LOGGED))
                        .setPassword(res.getString(Contracts.FIELD_NAME_PASSWORD));
                list.add(user);
                
            }while(res.next());
        }
        if(list.size() > 0) return list;
        throw new UserException(UserExceptionCodes.USER_NOT_EXISTING);
    }
    
    public static User updateUserInfo(User user) 
            throws SQLException, ClassNotFoundException, IOException, UserException
    {
        if(MysqlAdapter.exists("SELECT `UID` FROM `users` WHERE `UID`=?;", user.getUID()))
        {
            String sql = "UPDATE `users` SET " + Contracts.FIELD_NAME_PASSWORD + 
                    "=?," + Contracts.FIELD_NAME_LAST_LOGIN + "=?," +
                    Contracts.FIELD_NAME_IS_LOGGED + "=? WHERE " + 
                    Contracts.FIELD_NAME_UID + "=?";
            MysqlAdapter.executeSql(sql, user.getPasswordHash(), user.getLastLogin(), 
                    user.isLogged(), user.getUID());
            return (queryUser("SELECT * FROM `users` WHERE " +
                    Contracts.FIELD_NAME_UID + "=?", user.getUID()).get(0));
        }else throw new UserException(UserExceptionCodes.USER_NOT_EXISTING);
    }
}
