package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;  // ← 修正
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectSample04 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null; // ← 修正
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Haruharu31");

            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String sql = "SELECT * FROM country WHERE Name = ?";    // ← 修正
            pstmt = con.prepareStatement(sql);  // ← 修正
            // 5, 6. Select文の実行と結果を格納／代入
            System.out.print("検索キーワードを入力してください >' or 'a' = 'a ");
            String input = keyIn();

         // PreparedStatementオブジェクトの?に値をセット  // ← 追記
            pstmt.setString(1, input);  // ← 追記

            rs = pstmt.executeQuery();  // ← 修正
            // 7. 結果を表示する
            while( rs.next() ){
                // Name列の値を取得
                String name = rs.getString("Name");
             // Population列の値を取得 ← 追記
                int population = rs.getInt("Population");
                // 取得した値を表示
                System.out.println(name);
            }
           } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 8. 接続を閉じる
            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
        }
    }
            if (pstmt != null) {    // ← 修正
                try {
                    pstmt.close();    // ← 修正
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
            }
        }
    }
 }

    private static String keyIn() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
 }
