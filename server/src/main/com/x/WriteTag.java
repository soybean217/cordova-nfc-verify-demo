package com.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.x.dao.DaoTag;
import com.x.protocol.ProtocolWriteNfc;

/**
 * Servlet implementation class WriteTag
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/WriteTag" })
public class WriteTag extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(WriteTag.class);

  private static final int ID_LENGTH = 8;

  public static final String EXCEPTION_OVERLOAD = "overload";

  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public WriteTag() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    LOG.debug("do get.");
  }

  /**
   * @throws IOException 
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    try {
      StringBuffer buffer = new StringBuffer();
      BufferedReader reader = request.getReader();
      String line;
      while ((line = reader.readLine()) != null) {
        buffer.append(line);
      }
      String info = buffer.toString();
      LOG.info("####receive post:\n" + info);
      LOG.info("####end:");

      if (info != null && info.length() > 0) {
        Gson gson = new Gson();
        ProtocolWriteNfc protocolWriteNfc = gson.fromJson(info, ProtocolWriteNfc.class);
        // byte[] array = new byte[infoNfc.getId().size()];
        byte[] array = new byte[ID_LENGTH];
        int m = 0;
        if (protocolWriteNfc.getTagOriInfo().getId().size() > ID_LENGTH) {
          throw new IllegalArgumentException(EXCEPTION_OVERLOAD);
        }
        for (Byte bb : protocolWriteNfc.getTagOriInfo().getId()) {
          array[m++] = bb;
        }
        // LOG.debug(bytesToLong(array));
        DaoTag daoTags = new DaoTag();
        daoTags.updateOrInsertTags(Utils.bytesToLong(array), Integer.parseInt(protocolWriteNfc.getWantStatus()));
        response.getWriter().print("{\"status\":\"success\",\"msg\":\"ok\"}");
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      response.getWriter().print("{\"status\":\"error\",\"msg\":\"" + e.getMessage() + "\"}");
    }

    // java.util.Enumeration headerNames = request.getHeaderNames();
    // while (headerNames.hasMoreElements()) {
    // String headerKey = (String) headerNames.nextElement();
    // LOG.debug(headerKey + ":" + request.getHeader(headerKey));
    // }

  }

}
