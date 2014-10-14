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

public class GroupMailSumarizer {

	
	connectionClass connectionClass = new connectionClass();
	private static final Logger logger = Logger.getLogger(GroupMailSumarizer.class);
	ResultSet groupMailCountResultSet;
	String filterQuaryGroupMailCount = "REPLACE into GroupMailCount (DateCurrent,weekNo,groupName,mailCount)"
			+ " select FROM_UNIXTIME(timestamp/1000,\"%Y-%m-%d\") as Date_Mail, WEEK(FROM_UNIXTIME(timestamp/1000,\"%Y-%m-%d\")) "
			+ "as weekNo, labels, sum(emailcount) from EmailMainTable group by labels,Date_Mail";



	public void filteringAndAddingEmailDataGroupEmailTable() {

		try {

			connectionClass.statement = connectionClass.databaseConnection.createStatement();
			groupMailCountResultSet = connectionClass.databaseConnection.getMetaData()
					.getTables(null, null, "GroupMailCount", null);
			// quary to filter data and insert into the table
			checkTableExcecute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage()); 
		}
		// /checking the table wheather it exists or not

	}

	public void checkTableExcecute() {
		try {
			if (groupMailCountResultSet.next()) {

				
			//excecuting the filterig quary
				connectionClass.statement.execute(filterQuaryGroupMailCount);

			} else {

				logger.error("GroupMaillTable table doesnt exist!! ");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage()); 
		}

	}



}
