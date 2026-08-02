package data;

import org.testng.annotations.DataProvider;

public class AdminPageData {

    // Khai báo tên của DataProvider bằng thuộc tính name
    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return new Object[][]{
                // Dòng 1: {Tham_so_1, Tham_so_2, ...}
                {"Admin", "admin123", true},  // Tài khoản đúng
                {"adminTest", "admin123", false}, // Sai email
                {"Admin", "WrongPass", false}    // Sai pass
        };
    }

    @DataProvider(name = "usernameKeywords")
    public static Object[][] getUsernameSearchData() {
        return new Object[][]{
                {"Admin",true},
                {"MacBook Pro",false}
        };
    }

    @DataProvider(name = "userRoleValidData")
    public static Object[][] getUserRoleSearchData() {
        return new Object[][]{
                {"Admin",true},
                {"ESS",true},
        };
    }
    @DataProvider(name = "employeeNameKeywords")
    public static Object[][] getEmployeeNameSearchData() {
        return new Object[][]{
                {"A"},
                {"test"}
        };
    }
}
