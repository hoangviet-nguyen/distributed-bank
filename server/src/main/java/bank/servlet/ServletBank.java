package bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import bank.local.LocalBank;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = "/bank")
public class ServletBank extends HttpServlet {
    
    private LocalBank bank = LocalBank.getLocalBank();
 
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        System.out.println("Received GET command: " + cmd);
        switch (cmd) {
            case "Get Account Numbers":
                getAccountNumbers(req, resp);
                break;
                
            case "Get Balance":
                getBalance(req, resp);            
                break;
            
            case "isActive":
                isActive(req, resp);
                break;
            
            default:
                PrintWriter out = resp.getWriter();
                out.println("Received Unknown Command skipping");
                out.flush();
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        System.out.println("Received POST command: " + cmd);
        PrintWriter out = resp.getWriter();
        
        if (cmd == null) {
            out.println("Received no command skipping...");
            out.flush();
            return;
        }

        switch (cmd) {
            case "Create Account":
                createAccount(req, resp);
                break;
        
            default:
                out.println("Received Unknown Command skipping");
                out.flush();
                break;
        }
    }

/*
=================================================================================================================
                                            Get Commands
=================================================================================================================
*/

    public void getAccountNumbers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Set<String> numbers = bank.getAccountNumbers();
        int size = numbers.size();
        PrintWriter out = resp.getWriter();
        out.println(size);
        for (String number : numbers) {
            out.println(number);
        }
        out.flush();
    }

    public void getBalance(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBalance'");
    }


    public void isActive(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isActive'");
    }

/*
=================================================================================================================
                                            Post Commands
=================================================================================================================
*/
    public void createAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String owner = req.getParameter("owner");
        String number = bank.createAccount(owner);
        PrintWriter out = resp.getWriter();
        out.println(number);
        out.flush();
    }

    public void closeAccount(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeAccount'");
    }

    public void transfer(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transfer'");
    }


    public void deposit(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }


    public void withdraw(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

}
