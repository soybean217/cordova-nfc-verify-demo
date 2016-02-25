package com.x;

public class ProtocolWriteNfc {
  
  private InfoNfc tagOriInfo;
  private String action;
  private String wantStatus;
  
  public InfoNfc getTagOriInfo() {
    return tagOriInfo;
  }
  public void setTagOriInfo(InfoNfc tagOriInfo) {
    this.tagOriInfo = tagOriInfo;
  }
  public String getAction() {
    return action;
  }
  public void setAction(String action) {
    this.action = action;
  }
  public String getWantStatus() {
    return wantStatus;
  }
  public void setWantStatus(String wantStatus) {
    this.wantStatus = wantStatus;
  }

}
