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
    @DataProvider(name = "usernameKeywordsV2", parallel = true)
    public static Object[][] getUsernameSearchDataV2() {
        return new Object[][]{
                {"Admin",true},
                {"MacBook Pro",false}
        };
    }
    @DataProvider(name = "userRoleData")
    public static Object[][] getUserRoleSearchData() {
        return new Object[][]{
                {"Admin",true},
                {"ESS",true},
                {"ABC",false}
        };
    }
    @DataProvider(name = "userRoleDataV2", parallel = true)
    public static Object[][] getUserRoleSearchDataV2() {
        return new Object[][]{
                {"Admin",true},
                {"ESS",true},
                {"ABC",false}
        };
    }
    @DataProvider(name = "employeeNameValidKeywords")
    public static Object[][] getEmployeeNameSearchData() {
        return new Object[][]{
                {"Employee"},
                {"John"}
        };
    }
    @DataProvider(name = "employeeNameValidKeywordsV2", parallel = true)
    public static Object[][] getEmployeeNameSearchDataV2() {
        return new Object[][]{
                {"Employee"},
                {"aa"},
                {"mandaa"}
        };
    }
}
