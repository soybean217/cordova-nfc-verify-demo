package com.x.protocol;

import java.util.List;

import com.x.info.InfoWineProduct;

public class ProtocolWineInfos {

  private List<InfoWineProduct> wineProducts;

  @Override
  public String toString() {
    return "ProtocolWineInfos [wineProducts=" + wineProducts + "]";
  }

  public List<InfoWineProduct> getWineProducts() {
    return wineProducts;
  }

  public void setWineProducts(List<InfoWineProduct> wineProducts) {
    this.wineProducts = wineProducts;
  }

}
