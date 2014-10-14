/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.esb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class connectionClass {

		private static final Logger logger = Logger.getLogger(connectionClass.class);
		// attributes for the db connection
		Statement statement = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "testdb";
		String driver = "com.mysql.jdbc.Driver";
		//database user name
		String userName = "root";
		//database password
		String password = "*******";
		Connection databaseConnection;

		connectionClass()

		{

			try {

				databaseConnection = DriverManager.getConnection(url + dbName,
						userName, password);

				try {

					Class.forName(driver).newInstance();

				} catch (InstantiationException e) {
					logger.error(e.getMessage());
					// TODO Auto-generated catch block
				
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
					// TODO Auto-generated catch block
				
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage());
					// TODO Auto-generated catch block
				
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
		
				logger.error(e.getMessage());
				logger.error("DB connection coud not established!!");
			}

		}



	}
