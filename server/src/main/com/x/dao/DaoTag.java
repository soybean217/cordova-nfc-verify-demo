package com.x.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.common.util.ConnectionService;

public class DaoTag {

  private final static Logger LOG = Logger.getLogger(DaoTag.class);

  public String verifyById(long id){
    String result = "Unknown.";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      String sql = "SELECT currentStatus FROM tags where tagId=?;";
      ps = con.prepareStatement(sql);
      int m = 1;
      ps.setLong(m++, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getInt("currentStatus")==1){
          result = "Verified success.";
        }else{
          result = "Verified fail . Unavailable tag . ";
        }
      } else {
        result = "Verified fail . No record in database .  ";
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
  
  public String updateOrInsertTags(long id, int wantStatus) {
    String result = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      String sql = "SELECT tagId FROM tags where tagId=?;";
      ps = con.prepareStatement(sql);
      int m = 1;
      ps.setLong(m++, id);
      rs = ps.executeQuery();
      long currentTime = System.currentTimeMillis();
      if (rs.next()) {
        sql = "update tags set currentStatus=?,lastModifyTime=? where tagId=?;";
        ps = con.prepareStatement(sql);
        m = 1;
        ps.setLong(m++, wantStatus);
        ps.setLong(m++, currentTime);
        ps.setLong(m++, id);
        ps.execute();
      } else {
        sql = "insert into tags (tagId,currentStatus,addTime,lastModifyTime) values (?,?,?,?);";
        ps = con.prepareStatement(sql);
        m = 1;
        ps.setLong(m++, id);
        ps.setLong(m++, wantStatus);
        ps.setLong(m++, currentTime);
        ps.setLong(m++, currentTime);
        ps.execute();
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
