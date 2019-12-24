package utils;

import org.codehaus.groovy.transform.SourceURIASTTransformation;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                    ConfigurationReader.getProperty("oracledb.user"),
                    ConfigurationReader.getProperty("oracledb.password"));
        }

        private static Connection connection;
        private static Statement statement;
        private static ResultSet resultSet;

        public static void establishConnection(DBType dbType)  {
            try {
                switch(dbType) {
                    case ORACLE:
                        connection = DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                                ConfigurationReader.getProperty("oracledb.user"),
                                ConfigurationReader.getProperty("oracledb.password"));
                        break;
                    default:
                        connection = null;

                }
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static int getRowsCount(String sql)  {

            try {
                statement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(sql);
                resultSet.last();
                return resultSet.getRow();
            }catch(Exception e) {
                throw new RuntimeException(e);
            }


        }

        public static List<Map<String,Object>> runSQLQuery(String sql){
            try {
                statement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(sql);

                List<Map<String,Object>> list = new ArrayList<>();
                ResultSetMetaData rsMdata = resultSet.getMetaData();

                int colCount = rsMdata.getColumnCount();

                while(resultSet.next()) {
                    Map<String,Object> rowMap = new HashMap<>();

                    for(int col = 1; col <= colCount; col++) {
                        rowMap.put(rsMdata.getColumnName(col), resultSet.getObject(col));
                    }


                    list.add(rowMap);
                }

                return list;
            }catch(Exception e) {
                throw new RuntimeException(e);
            }

        }

        public static void closeConnections() {
            try{
                if(resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
                if(statement != null && !statement.isClosed()) {
                    statement.close();
                }
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static void insertData(String TableName) throws SQLException {


                statement = connection.createStatement();
                String query = "INSERT INTO " + TableName + " (";
                String Path= "./src/test/resources/test_data/TWN.xlsx";
                String userSheet = TableName;
                ExcelUtil room = new ExcelUtil(Path, userSheet);
                for(String str:room.getColumnsNames()){
                    query += str + ",";
                }
                System.out.println(query);
                query =query.substring(0,query.length()-1);
                System.out.println(query);
                query +=") VALUES (";

                List<String> data = room.getData();
                int i=0;
                for(String str: room.getTypeofData()){
                    if(str.equals("STRING")){
                        data.set(i,"'" + data.get(i)+ "'");
                    }else if(str.equals("INT")){
                        data.set(i,""+(int)Double.parseDouble(data.get(i)));
                    }
                    i++;
                }
                for(String str : data){

                    query += str + ",";
                }
                System.out.println(query);
                query =query.substring(0,query.length()-1);
                query += ")";
                System.out.println(query);

                resultSet= statement.executeQuery(query);




        }

    }


