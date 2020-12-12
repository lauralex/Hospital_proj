package com.bell_sic;

public class BaseApplication {
    public static void main(String[] args) {
        // System.out.println("Hello");
        // try (Connection conn = MariaDBConnectionManager.getInstance().getConnection()) {
        //     String statement = "select * from hw1_follow";
        //     try (PreparedStatement preparedStatement = conn.prepareStatement(statement)){
        //         var results = preparedStatement.executeQuery();
        //         var metadata = results.getMetaData();
        //         var colCount = metadata.getColumnCount();
        //
        //         for (int i = 1; i <= colCount; i++) {
        //             StringAlignedPrinter.print(30, metadata.getColumnName(i));
        //         }
        //         System.out.printf("%n");
        //         while (results.next()) {
        //             for (int i = 1; i <= colCount; i++) {
        //                 String colValue = results.getString(i);
        //                 StringAlignedPrinter.print(30, colValue);
        //             }
        //             System.out.printf("%n");
        //         }
        //     }
        // } catch (SQLException throwables) {
        //     throwables.printStackTrace();
        // }


        UILoop.execute();

    }
}
