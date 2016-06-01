package com.x.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.common.util.ConnectionService;

import com.x.info.InfoWineProduct;

public class DaoWine {

  private final static Logger LOG = Logger.getLogger(DaoWine.class);

  public List<InfoWineProduct> getAllWines() {
    List result = new ArrayList<InfoWineProduct>();
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      String sql = "SELECT * FROM wine_products";
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        InfoWineProduct infoWineProduct = new InfoWineProduct();
        infoWineProduct.setId(rs.getLong("id"));
        infoWineProduct.setWineName(rs.getString("wineName"));
        infoWineProduct.setScore(rs.getString("score"));
        infoWineProduct.setWinePicUrl(rs.getString("winePicUrl"));
        result.add(infoWineProduct);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return result;
  }
}
