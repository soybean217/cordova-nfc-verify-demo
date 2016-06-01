package com.x;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.x.dao.DaoWine;
import com.x.protocol.ProtocolWineInfos;

/**
 * Servlet implementation class GetWineInfos
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/GetWineInfos" })
public class GetWineInfos extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public GetWineInfos() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    getWines(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    getWines(request, response);
  }

  private void getWines(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    DaoWine daoWine = new DaoWine();
    ProtocolWineInfos protocolWineInfos = new ProtocolWineInfos();
    protocolWineInfos.setWineProducts(daoWine.getAllWines());
    response.getWriter().print(gson.toJson(protocolWineInfos));
  }
}
