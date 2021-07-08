package net.bit.day0707;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Board {
    
  
public static void main(String[] args) {
  //1) 오라클 db드라이버 로드하는 명령어 Class.forName(" ")
  //2) db서버접근 계정명, pwd 127.0.0.1:1521:XE system/1234
  //3) 명령어 생성은 2번째 서버 정보를 참조해서 명령어 생성 Statement ST=
  
  Scanner sc = new Scanner(System.in);
  
  Connection con = null;
  ResultSet RS = null;
  Statement ST = null;
  String url = "jdbc:oracle:thin:@localhost:1521:XE";
  String id = "system";
  String pwd = "1234";
  String driver = "oracle.jdbc.driver.OracleDriver"; 
  
  try {
    Class.forName(driver);
    con = DriverManager.getConnection(url, id, pwd);
    System.out.println("");
    
    String msg = "select * from test ";
    
    //4) 명령어 생성 후, 명령어 실행() 결과 값을 ResultSet에 기억
    ST = con.createStatement();
    RS = ST.executeQuery(msg);
    
    //5) 레코드를 첫번째 부터 차례차례 이동하면 출력 while(조건){코드,이름}
    System.out.print("코드입력");
    int a = Integer.parseInt(sc.nextLine());
    System.out.print("이름입력");
    String b = sc.nextLine();
    System.out.print("제목입력");
    String c = sc.nextLine();
    
    msg = "insert into test(code, name, title, wdate, cnt) values("+a+", '"+ b+"' ,'"+ c+"', sysdate, 0)";
    
    System.out.println(msg);
    int OK = ST.executeUpdate(msg);
    System.out.println(OK);
    if(OK>0) {
      System.out.println("저장성공");
    } else {
      System.out.println("저장실패");
    }
    System.out.println();
    
    System.out.print("이름입력");
    String d = sc.nextLine();
    
    msg = "delete from test where name = " +"'"+d+"'";
    System.out.println(msg);
    OK = ST.executeUpdate(msg);
    if(OK>0) {
      System.out.println("삭제성공");
    } else {
      System.out.println("삭제실패");
    }
    
    
  } catch (Exception ex) {
    System.out.println(ex);
  }
}
}
