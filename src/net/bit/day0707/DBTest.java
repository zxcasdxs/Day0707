package net.bit.day0707;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBTest {
  
   Connection CN = null; //DB 서버 연결정보
   Statement ST = null; // 명령어 생성
   ResultSet RS = null; // 조회결과를 기억
   String msg = "isud = crud 쿼리문기술";
   String input = "";
   Scanner sc = new Scanner(System.in);
   int total = 0;
   int a;

    
    public void connect() {
    
      try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@localhost:1521:XE";
      CN =  DriverManager.getConnection(url, "system", "1234");
      System.out.println("07-09-금요일 오라클 드라이브 & 서버 연결성공");
      
      ST = CN.createStatement();
      
      msg = "select count(*) as hit from test";
      RS = ST.executeQuery(msg);
      if(RS.next()==true)  {
        total = RS.getInt("hit");
      }
      
      int sel = 0;
      while(true) {
        System.out.println("\n1.입력  2.삭제  3.수정  4.전체조회 5.개별조회  0.종료");
        sel = Integer.parseInt(sc.nextLine());
        if(sel==0) {
          System.out.println("DB프로그램 종료");
          System.exit(0);
        }//if end
        switch(sel) {
          case 1 : add(); break;
          case 2 : delete(); break;
          case 3 : update(); break;
          case 4 : AllList(); break;
          case 5 : view(); break;
          default : System.out.println("올바른 메뉴를 선택해 주세요.");
          break;
        }//switch end
        
      }//while end
      } catch (Exception ex) { System.out.println("에러이유 " + ex);
      }//try C end
}//커넥션 end
      
      public void add() throws SQLException {

    Checkcode();
    System.out.print("이름입력>>>");
    String b = sc.nextLine();
    System.out.print("제목입력>>>");
    String c = sc.nextLine();
    
    
    msg = "insert into test(code, name, title, wdate, cnt) "
        + "values( "+ a +", '"+ b +"', '"+ c +"' , sysdate, 0)";
    
    System.out.println(msg);
    
    int OK = ST.executeUpdate(msg);
    if (OK>0) {
      System.out.println(a + "코드 데이터 저장 성공");
    } else {
      System.out.println(a + "코드 데이터 저장 실패");
    }
      }//add end
      
      public void delete() throws SQLException {

        Checkcode();
        msg = "delete from test where code = " + a ;
        System.out.println(msg);
        int OK = ST.executeUpdate(msg);
        if (OK>0) {
          System.out.println(a + "코드 데이터 삭제 성공");
          AllList();
        } else {
          System.out.println(a + "코드 데이터 삭제 실패");
        }
      }//delete end
      
      
      public void Checkcode() {
        loop : 
      while(true) {
      System.out.print("\ncode입력>>>");
      input = sc.nextLine();
      if(input.equals("back")) {
        connect();
      } else { a = Integer.parseInt(input);
      }
      int length = (int)(Math.log10(a)+1);
      if(length != 4) {
        System.out.print("오류!\t네 자릿수의 code를 입력 하세요.");
        } else {break loop;}
      }
      }
      
      public void update() throws SQLException {
        System.out.println("수정할 코드를 입력하세요.");
        Checkcode();
        System.out.print("수정 될 이름입력>>>");
        String b = sc.nextLine();
        System.out.print("수정 될 제목입력>>>");
        String c = sc.nextLine();
        
        
        msg = "update test set "
            + "name = '"+b+"', title = '"+ c +"' where code = " +a;
        
        System.out.println(msg);
        
        int OK = ST.executeUpdate(msg);
        if (OK>0) {
          System.out.println(a + "코드 데이터 수정 성공");
        } else {
          System.out.println(a + "코드 데이터 수정 실패");
        }
      }
      
      public void AllList() {
        try {
    System.out.println("프로그램 전체 데이터 읽어오는중... 잠시 기다려 주세요.");
    Thread.sleep(500);
    msg = "select * from test "; // 문자열을 명령어 인식해서 실행하도록 Statement
    RS = ST.executeQuery(msg);
    
    System.out.println("\t\t\t 전체 레코드 갯수 : " + total);
        } catch (Exception ex) { System.out.println("에러이유 " + ex);
        }//try C end
        
        listin();
      }//list end
      
      public void view() {
        
        try {
          loop : 
            while(true) {
          System.out.println("검색할 코드를 입력하세요.");
          Checkcode();
          System.out.println("프로그램 데이터 읽어오는중... 잠시 기다려 주세요.");
          Thread.sleep(500);
          msg = "select * from test where code = "+ a; // 문자열을 명령어 인식해서 실행하도록 Statement
          
          int OK = ST.executeUpdate(msg);
          if (OK==0) {
            System.out.println(a + "코드 데이터 조회 실패");
          } else {
            System.out.println(a + "코드 데이터 조회 성공");
            break loop;
          }
}//while end
        } catch (Exception ex) { System.out.println("에러이유 " + ex);
        }//try C end
        
        listin();
      }
      
      public void listin() {
        try {
          System.out.println("코 드\t이 름\t제 목\t날 짜\t    조 회 수 ");
          RS = ST.executeQuery(msg);
          while(RS.next()==true) {

            int ucode = RS.getInt("code");
            String uname = RS.getString("name");
            String utitle = RS.getNString("title");
            Date udate = RS.getDate("wdate");
            int ucnt = RS.getInt("cnt");
            
            System.out.println(
                ucode + "\t" + uname + "\t" + utitle + "\t" + udate + "\t" + ucnt);
          }
          
          } catch (Exception ex) { System.out.println("에러이유 " + ex);
          }//try C end
      }
      
    public static void main(String[] args) {
      DBTest tt = new DBTest();
      tt.connect();
      
  }//M end
}//C end
