package net.bit.day0707;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DBTest {
  
  protected Connection CN = null; //DB 서버 연결정보
  protected Statement ST = null; // 명령어 생성
  protected ResultSet RS = null; // 조회결과를 기억
  protected String msg = "isud = crud 쿼리문기술";
  protected Scanner sc = new Scanner(System.in);
  
    
    
    public void connection() {
    
      try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
      CN =  DriverManager.getConnection(url, "system", "1234");
      System.out.println("오라클 드라이브 & 서버 연결성공");
      
      ST = CN.createStatement();
      
      int sel = 0;
      while(true) {
        System.out.println("\n1.입력  2.삭제  3.조회  0.종료");
        sel = Integer.parseInt(sc.nextLine());
        if(sel==0) {
          System.out.println("DB프로그램 종료");
          System.exit(0);
        }//if end
        switch(sel) {
          case 1 : add(sc); break;
          case 2 : delete(); break;
          case 3 : list(); break;
          default : System.out.println("올바른 메뉴를 선택해 주세요.");
          break;
        }//switch end
        
      }//while end
      } catch (Exception ex) { System.out.println("에러이유 " + ex);
      }//try C end
}//커넥션 end
      
      public void add(Scanner sc) throws SQLException {
      
//      //명령어 생성
//      ST = CN.createStatement();
      
      //키보드에서 데이터 입력
      
    System.out.print("\ncode입력>>>");
    int a = Integer.parseInt(sc.nextLine());
    System.out.print("이름입력>>>");
    String b = sc.nextLine();
    System.out.print("제목입력>>>");
    String c = sc.nextLine();
    
    //쿼리문 완성
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
//        ST = CN.createStatement();
        System.out.print("삭제할 코드입력>>>");
        int a = Integer.parseInt(sc.nextLine());
        
        msg = "delete from test where code = " + a ;
        System.out.println(msg);
        int OK = ST.executeUpdate(msg);
        if (OK>0) {
          System.out.println(a + "코드 데이터 삭제 성공");
        } else {
          System.out.println(a + "코드 데이터 삭제 실패");
        }
      }//delete end
      
      public void list() {
      
        try {
    System.out.println("프로그램 전체 데이터 읽어오는중... 잠시 기다려 주세요.");
    Thread.sleep(3000);
    msg = "select * from test "; // 문자열을 명령어 인식해서 실행하도록 Statement
    RS = ST.executeQuery(msg);
    
    System.out.println(" | 코드\t이름\t");
    while(RS.next()==true) {
      //필드 접근해서 데이터가져올때 getXXX()
      int ucode = RS.getInt("code");
      String uname = RS.getString("name");
      System.out.println(" | "+ucode + "\t" + uname);
    }
    
    } catch (Exception ex) { System.out.println("에러이유 " + ex);
    }//try C end
        
      }//list end

    

    public static void main(String[] args) {
      DBTest tt = new DBTest();
      tt.connection();
      
  }//M end
}//C end
