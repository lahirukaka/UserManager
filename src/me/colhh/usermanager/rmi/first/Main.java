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

package me.colhh.usermanager.rmi.first;

import java.util.ArrayList;
import me.colhh.mysqlmanager.MysqlAdapter;
import me.colhh.usermanager.rmi.first.Adapter.DBAdapter;
import me.colhh.usermanager.rmi.first.Holder.User;
import me.colhh.usermanager.rmi.first.Holder.UserException;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class Main {

    public static void main(String[] args) {
        
        try
        {
            User user =  DBAdapter.queryUser("SELECT * FROM `users` WHERE `UID`=?", 3).get(0);
            user.setLogged(false);
            user.setLastLogin(System.currentTimeMillis());
            System.out.println(DBAdapter.updateUserInfo(user));

        }catch(UserException e)
        {
            System.out.println(e.getErrorCode());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage() + " - " + e.getClass());
        }
        System.out.println("OK");
    }
}
