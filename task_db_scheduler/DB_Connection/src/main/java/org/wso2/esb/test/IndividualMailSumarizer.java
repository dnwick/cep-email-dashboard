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

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class IndividualMailSumarizer {

	connectionClass conClass = new connectionClass();
	private static final Logger logger = Logger.getLogger(IndividualMailSumarizer.class);
	ResultSet individualMailCountResultSet;
	
	String FilterQuaryIndividualMailCount = "REPLACE into IndividualMailCount (DateCurrent,weekNo,groupName,sender,mailcount)"
			+ "select FROM_UNIXTIME(timestamp/1000,\"%Y-%m-%d\") as Date_Mail,  WEEK(FROM_UNIXTIME(timestamp/1000,\"%Y-%m-%d\"))"
			+ " as weekNo, labels,sender, sum(emailcount) from EmailMainTable group by Date_Mail,labels, sender";

	

	public void filteringAndAddingEmailDataIndividualEmailTable() {

		try {

			// connecting to the db

			conClass.statement = conClass.databaseConnection.createStatement();

			individualMailCountResultSet = conClass.databaseConnection
					.getMetaData().getTables(null, null, "IndividualMailCount",
							null);
			
			//calling the executing method
			checkTableExecute();
			// quary to filter data and adding to the new table

		} catch (Exception e) {
			logger.error(e.getMessage()); 
			System.out.println("DB connection coud not established!!");
		}

	}

	public void checkTableExecute() {
		try {
			if (individualMailCountResultSet.next()) {
				
				//excueting the quarry to filter message
				conClass.statement.execute(FilterQuaryIndividualMailCount);
				
			} else {

			logger.error("IndividualMailTable table doesnt exist!! ");

			}
		} catch (SQLException e) {
			
			logger.error(e.getMessage()); 
		}

	}

	

}
