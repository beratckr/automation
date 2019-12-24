package jdbctest;

import groovy.transform.Undefined;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.DBType;
import utils.DBUtility;
import utils.ExcelUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Insert {
    @BeforeClass
    public static void setUp(){
        //connect to database using utility
        DBUtility.establishConnection(DBType.ORACLE);
    }

   @Test(dataProvider = "TWNTableNames")
    public void testEmployeesCount(String tableName) throws Exception {
//        int employeesCount = DBUtility.getRowsCount("SELECT * FROM employees");
//        System.out.println("employeesCount: " + employeesCount);
//        int expectedEmpCount = 107;
//        assertEquals(expectedEmpCount, employeesCount);
        DBUtility.insertData(tableName);

}

    @DataProvider(name = "TWNTableNames")
    public Object[] getExcelSheet (){
        ExcelUtil excel = new ExcelUtil("./src/test/resources/test_data/TWN.xlsx","TABLES");
        List<String> listOfTable = excel.excelSheetTWNTableNameReader();
        String[] string = {};
        string = new String[listOfTable.size()];
        for(int i=0;i<listOfTable.size();i++){
            System.out.println(listOfTable.get(i));
            string[i] = listOfTable.get(i);
        }
        return string;

    }



}
