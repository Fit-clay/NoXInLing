package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

public abstract class CellBean
  implements Serializable
{
  private static final String UNKONW = "null";
  private static final long serialVersionUID=0;
  public int asu = 99;
  public String mcc = "null";
  public String mnc = "null";
  public String name = "";
  public int networkType = -1;
  public String strTime = "";
  public long time = 0L;

  public String getMCCMNC()
  {
    if ((this.mcc.equals("null")) || (this.mnc.equals("null")))
      return "未知";
    return " " + this.mcc + "-" + this.mnc;
  }

  public abstract void reset();
}
