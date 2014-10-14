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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.synapse.task.Task;

public class TableDataDelete implements Task {
	
	// attributes for creating the database connection
			String truncateTable = "DELETE FROM   EmailMainTable WHERE  FROM_UNIXTIME(timestamp/1000,\"%Y-%m-%d\") < DATE_SUB(NOW(), INTERVAL 8 DAY)";
			Statement statement = null;
			String url = "jdbc:mysql://localhost:3306/";
			String dbName = "testdb";
			String driver = "com.mysql.jdbc.Driver";
			String userName = "root";
			//password for the database
			String password = "*******";
			final Logger logger = Logger.getLogger(TableDataDelete.class);

			boolean checkTableExists = false;
			
			
	public void execute() {

		try {

			// /creating the connection
			Connection DatabaseConnection = DriverManager.getConnection(url
					+ dbName, userName, password);

			ResultSet resultSet = DatabaseConnection.getMetaData().getTables(
					null, null, "EmailMainTable", null);

			// checking wheather the table exist or not
			if (checkTableExists = true) {
				statement = DatabaseConnection.createStatement();

				// database quary for truncate table

				statement.execute(truncateTable);

			} else {
				logger.error("Table doesnot exist !!");
				

			}
			// closing the database connection when itz done
			DatabaseConnection.close();
		} catch (Exception e) {

			logger.error(e.getMessage());
			
		}

	}

}
