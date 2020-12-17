package freeBoard.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import freeBoard.action.Action;
import freeBoard.action.BoardDeleteProAction;
import freeBoard.action.BoardDetailAction;
import freeBoard.action.BoardListAction;
import freeBoard.action.BoardReplyFormAction;
import freeBoard.action.BoardReplyProAction;
import freeBoard.action.BoardUpdateFormAction;
import freeBoard.action.BoardUpdateProAction;
import freeBoard.action.BoardWriteProAction;
import freeBoard.action.ReplePlayProAction;
import freeBoard.action.RepleformProAction;
import freeBoard.action.boardReDeleteProAction;
//import action.MemberJoinAction;
import freeBoard.vo.ActionForward;


@WebServlet("*.abc")
public class BoardFrontController extends HttpServlet {
   
   
   protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      String RequestURI=request.getRequestURI();
      String contextPath=request.getContextPath();
      String command=RequestURI.substring(contextPath.length());
      ActionForward forward=null;
      Action action=null;
      HttpSession session = request.getSession();
	  String id = (String)session.getAttribute("id");
		
      if(command.equals("/boardList.abc")){
            action = new BoardListAction();
            try{
               forward=action.execute(request, response);
            }catch(Exception e){
               e.printStackTrace();
            }
      }else if(command.equals("/boardWriteForm.abc")){
          forward=new ActionForward();
         forward.setPath("/board/post.jsp");
      }
      		else if(command.equals("/boardWritePro.abc")){
           action  = new BoardWriteProAction();
           try {
              forward=action.execute(request, response );
           } catch (Exception e) {
              e.printStackTrace();
           }
        }
      		else if(command.equals("/boardDetail.abc")){
            action = new BoardDetailAction();
            try{
               forward=action.execute(request, response);
            }catch(Exception e){
               e.printStackTrace();
            }
         }
      		else if(command.equals("/boardReplyForm.abc")){
             action = new BoardReplyFormAction();
             try{
                forward=action.execute(request, response);
             }catch(Exception e){
                e.printStackTrace();
             }
          }
      		else if(command.equals("/boardReplyPro.abc")){
              action = new BoardReplyProAction();
              try{
                 forward=action.execute(request, response);
              }catch(Exception e){
                 e.printStackTrace();
              }
           }
      		else if(command.equals("/boardDeleteForm.abc")){
               String nowPage = request.getParameter("page");
               request.setAttribute("page", nowPage);
               int board_num=Integer.parseInt(request.getParameter("board_num"));
               request.setAttribute("board_num",board_num);
               forward=new ActionForward();
               forward.setPath("/board/delete.jsp");
            }
      		else if(command.equals("/boardDeletePro.abc")){
                action = new BoardDeleteProAction();
                try{
                   forward=action.execute(request, response);
                }catch(Exception e){
                   e.printStackTrace();
                }
             }
      		else if(command.equals("/boardUpdateForm.abc")) {
           	 action = new BoardUpdateFormAction();
           	 try{
                    forward=action.execute(request, response);
                 }catch(Exception e){
                    e.printStackTrace();
                 	}
          		}
      		else if(command.equals("/boardUpdatePro.abc")) {
       			action  = new BoardUpdateProAction();
                try {
                   forward=action.execute(request, response );
                } catch (Exception e) {
                   e.printStackTrace();
                }
       		}
      		else if(command.equals("/repleform.abc")) {
      			 action  = new RepleformProAction();
                 try {
                    forward=action.execute(request, response );
                 } catch (Exception e) {
                    e.printStackTrace();
                 }
              }
      		else if(command.equals("/replePlay.abc")){
      			 action  = new ReplePlayProAction();
                 try {
                    forward=action.execute(request, response );
                 } catch (Exception e) {
                    e.printStackTrace();
                 }
              }
      		else if(command.equals("/boardReDeletePro.abc")){

      		
      			
                action =new boardReDeleteProAction();
                try {
                   forward=action.execute(request, response );
                } catch (Exception e) {
                   e.printStackTrace();
                }
               
      		}
      
      if(forward != null){
         
         if(forward.isRedirect()){
            response.sendRedirect(forward.getPath());
         }else{
            RequestDispatcher dispatcher=
                  request.getRequestDispatcher(forward.getPath());
            dispatcher.forward(request, response);
         }
         
      }
      
      
   }
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
		doProcess(request,response);
      } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
   }

   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
		doProcess(request,response);
      } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
   }

}