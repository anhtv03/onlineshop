/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.team3.onlineshopping.controllerCustomer;

import com.team3.onlineshopping.dal.CustomerDAO;
import com.team3.onlineshopping.dal.OrderDAO;
import com.team3.onlineshopping.dal.OrderDetailsDAO;
import com.team3.onlineshopping.model.Account;
import com.team3.onlineshopping.model.Customer;
import com.team3.onlineshopping.model.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class CusMyOrderServlet extends HttpServlet {

    CustomerDAO cus_dao = new CustomerDAO();
    OrderDetailsDAO orde_dao = new OrderDetailsDAO();
    OrderDAO or_dao = new OrderDAO();
    List<Order> order = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String type = request.getParameter("type");

        Account acc = (Account) session.getAttribute("account");
        Customer cus = cus_dao.getByAccountId(acc.getAccId());
        int cusId = cus.getCusId();

        if (type == null || type.equalsIgnoreCase("all")) {
            order = (or_dao.getByCusId(cusId));
            type = "all";
        } else if (type.equalsIgnoreCase("delivered")) {
            order = (or_dao.getByCusId(cusId, "delivered"));
        } else if (type.equalsIgnoreCase("delivering")) {
            order = (or_dao.getByCusId(cusId, "delivering"));
        } else if (type.equalsIgnoreCase("cancelled")) {
            order = (or_dao.getByCusId(cusId, "cancelled"));
        } else if (type.equalsIgnoreCase("pending")) {
            order = (or_dao.getByCusId(cusId, "pending"));
        }

        request.setAttribute("type", type);
        request.setAttribute("orders", order);
        request.getRequestDispatcher("cu_myorder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
