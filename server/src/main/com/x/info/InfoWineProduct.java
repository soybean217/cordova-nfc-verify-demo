package com.x.info;

public class InfoWineProduct {

  private long id;
  private String wineName;
  private String score;
  private String winePicUrl;

  @Override
  public String toString() {
    return "InfoWineProduct [id=" + id + ", wineName=" + wineName + ", score=" + score + ", winePicUrl=" + winePicUrl
        + "]";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getWineName() {
    return wineName;
  }

  public void setWineName(String wineName) {
    this.wineName = wineName;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getWinePicUrl() {
    return winePicUrl;
  }

  public void setWinePicUrl(String winePicUrl) {
    this.winePicUrl = winePicUrl;
  }

}
