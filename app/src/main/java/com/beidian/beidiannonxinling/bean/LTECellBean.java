package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

public class LTECellBean extends CellBean
  implements Serializable
{
  public String band = "";
  public String band1 = "";
  public int cellid = -1;
  public int ci = -1;
  public int enb = -1;
  public int pci = -1;
  public int rsrp = 99;
  public int rsrq = 99;
  public int sinr = 99;
  public int tac = -1;

  public String getCellid()
  {
    if ((this.cellid > 1000) || (this.cellid < 0))
      return "";
    return String.valueOf(this.cellid);
  }

  public String getCi()
  {
    if ((this.ci >= 2147483647) || (this.ci < 0))
      return "";
    return String.valueOf(this.ci);
  }

  public String getEnb()
  {
    if ((this.enb >= 2147483647) || (this.enb < 0))
      return "";
    return String.valueOf(this.enb);
  }

  public String getPci()
  {
    if ((this.pci > 505) || (this.pci < 0))
      return "";
    return String.valueOf(this.pci);
  }

  public String getRsrp()
  {
    if ((this.rsrp > -20) || (this.rsrp < -150))
      return "";
    return String.valueOf(this.rsrp);
  }

  public String getRsrq()
  {
    if ((this.rsrq > 20) || (this.rsrq < -50))
      return "";
    return String.valueOf(this.rsrq);
  }

  public String getSinr()
  {
    if ((this.sinr > 50) || (this.sinr < -20))
      return "";
    return String.valueOf(this.sinr);
  }

  public String getTac()
  {
    if ((this.tac > 100000) || (this.tac < 0))
      return "";
    return String.valueOf(this.tac);
  }

  public void reset()
  {
  }

  @Override
  public String toString() {
    return "LTECellBean{" +
            "band='" + band + '\'' +
            ", band1='" + band1 + '\'' +
            ", cellid=" + getCellid() +
            ", ci=" + getCi() +
            ", enb=" + getEnb() +
            ", pci=" + getPci() +
            ", rsrp=" + getRsrp() +
            ", rsrq=" + getRsrq() +
            ", sinr=" + getSinr() +
            ", tac=" + getTac() +
            '}';
  }
}
