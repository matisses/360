package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class ItemInventarioDTO {

    private int posicion;
    private Integer cantidadStock;
    private String itemNuevo;
    private boolean seleccionado = false;
    private List<SaldoItemDTO> saldos;

    private String itemCode;
    private String itemName;
    private String frgnName;
    private Short itmsGrpCod;
    private Short cstGrpCode;
    private String vatGourpSa;
    private String codeBars;
    private Character vATLiable;
    private Character prchseItem;
    private Character sellItem;
    private Character invntItem;
    private BigDecimal onHand;
    private BigDecimal isCommited;
    private BigDecimal onOrder;
    private String incomeAcct;
    private String exmptIncom;
    private BigDecimal maxLevel;
    private String dfltWH;
    private String cardCode;
    private String suppCatNum;
    private String buyUnitMsr;
    private BigDecimal numInBuy;
    private BigDecimal reorderQty;
    private BigDecimal minLevel;
    private BigDecimal lstEvlPric;
    private Date lstEvlDate;
    private BigDecimal customPer;
    private Character canceled;
    private Integer mnufctTime;
    private Character wholSlsTax;
    private Character retilrTax;
    private BigDecimal spcialDisc;
    private Short dscountCod;
    private Character trackSales;
    private String salUnitMsr;
    private BigDecimal numInSale;
    private BigDecimal consig;
    private Integer queryGroup;
    private BigDecimal counted;
    private BigDecimal openBlnc;
    private Character evalSystem;
    private Short userSign;
    private Character free;
    private String picturName;
    private Character transfered;
    private Character blncTrnsfr;
    private String userText;
    private String serialNum;
    private BigDecimal commisPcnt;
    private BigDecimal commisSum;
    private Short commisGrp;
    private Character treeType;
    private BigDecimal treeQty;
    private BigDecimal lastPurPrc;
    private String lastPurCur;
    private Date lastPurDat;
    private String exitCur;
    private BigDecimal exitPrice;
    private String exitWH;
    private Character assetItem;
    private Character wasCounted;
    private Character manSerNum;
    private BigDecimal sHeight1;
    private Short sHght1Unit;
    private BigDecimal sHeight2;
    private Short sHght2Unit;
    private BigDecimal sWidth1;
    private Short sWdth1Unit;
    private BigDecimal sWidth2;
    private Short sWdth2Unit;
    private BigDecimal sLength1;
    private Short sLen1Unit;
    private BigDecimal slength2;
    private Short sLen2Unit;
    private BigDecimal sVolume;
    private Short sVolUnit;
    private BigDecimal sWeight1;
    private Short sWght1Unit;
    private BigDecimal sWeight2;
    private Short sWght2Unit;
    private BigDecimal bHeight1;
    private Short bHght1Unit;
    private BigDecimal bHeight2;
    private Short bHght2Unit;
    private BigDecimal bWidth1;
    private Short bWdth1Unit;
    private BigDecimal bWidth2;
    private Short bWdth2Unit;
    private BigDecimal bLength1;
    private Short bLen1Unit;
    private BigDecimal blength2;
    private Short bLen2Unit;
    private BigDecimal bVolume;
    private Short bVolUnit;
    private BigDecimal bWeight1;
    private Short bWght1Unit;
    private BigDecimal bWeight2;
    private Short bWght2Unit;
    private String fixCurrCms;
    private Short firmCode;
    private Date lstSalDate;
    private Character qryGroup1;
    private Character qryGroup2;
    private Character qryGroup3;
    private Character qryGroup4;
    private Character qryGroup5;
    private Character qryGroup6;
    private Character qryGroup7;
    private Character qryGroup8;
    private Character qryGroup9;
    private Character qryGroup10;
    private Character qryGroup11;
    private Character qryGroup12;
    private Character qryGroup13;
    private Character qryGroup14;
    private Character qryGroup15;
    private Character qryGroup16;
    private Character qryGroup17;
    private Character qryGroup18;
    private Character qryGroup19;
    private Character qryGroup20;
    private Character qryGroup21;
    private Character qryGroup22;
    private Character qryGroup23;
    private Character qryGroup24;
    private Character qryGroup25;
    private Character qryGroup26;
    private Character qryGroup27;
    private Character qryGroup28;
    private Character qryGroup29;
    private Character qryGroup30;
    private Character qryGroup31;
    private Character qryGroup32;
    private Character qryGroup33;
    private Character qryGroup34;
    private Character qryGroup35;
    private Character qryGroup36;
    private Character qryGroup37;
    private Character qryGroup38;
    private Character qryGroup39;
    private Character qryGroup40;
    private Character qryGroup41;
    private Character qryGroup42;
    private Character qryGroup43;
    private Character qryGroup44;
    private Character qryGroup45;
    private Character qryGroup46;
    private Character qryGroup47;
    private Character qryGroup48;
    private Character qryGroup49;
    private Character qryGroup50;
    private Character qryGroup51;
    private Character qryGroup52;
    private Character qryGroup53;
    private Character qryGroup54;
    private Character qryGroup55;
    private Character qryGroup56;
    private Character qryGroup57;
    private Character qryGroup58;
    private Character qryGroup59;
    private Character qryGroup60;
    private Character qryGroup61;
    private Character qryGroup62;
    private Character qryGroup63;
    private Character qryGroup64;
    private Date createDate;
    private Date updateDate;
    private String exportCode;
    private BigDecimal salFactor1;
    private BigDecimal salFactor2;
    private BigDecimal salFactor3;
    private BigDecimal salFactor4;
    private BigDecimal purFactor1;
    private BigDecimal purFactor2;
    private BigDecimal purFactor3;
    private BigDecimal purFactor4;
    private String salFormula;
    private String purFormula;
    private String vatGroupPu;
    private BigDecimal avgPrice;
    private String purPackMsr;
    private BigDecimal purPackUn;
    private String salPackMsr;
    private BigDecimal salPackUn;
    private Short sCNCounter;
    private Character manBtchNum;
    private Character manOutOnly;
    private Character dataSource;
    private Character validFor;
    private Date validFrom;
    private Date validTo;
    private Character frozenFor;
    private Date frozenFrom;
    private Date frozenTo;
    private Character blockOut;
    private String validComm;
    private String frozenComm;
    private Integer logInstanc;
    private String objType;
    private String sww;
    private Character deleted;
    private Integer docEntry;
    private String expensAcct;
    private String frgnInAcct;
    private Short shipType;
    private Character gLMethod;
    private String eCInAcct;
    private String frgnExpAcc;
    private String eCExpAcc;
    private Character taxType;
    private Character byWh;
    private Character wTLiable;
    private Character itemType;
    private String warrntTmpl;
    private String baseUnit;
    private String countryOrg;
    private BigDecimal stockValue;
    private Character phantom;
    private Character issueMthd;
    private Character free1;
    private BigDecimal pricingPrc;
    private Character mngMethod;
    private BigDecimal reorderPnt;
    private String invntryUom;
    private Character planingSys;
    private Character prcrmntMtd;
    private Short ordrIntrvl;
    private BigDecimal ordrMulti;
    private BigDecimal minOrdrQty;
    private Integer leadTime;
    private Character indirctTax;
    private String taxCodeAR;
    private String taxCodeAP;
    private Integer oSvcCode;
    private Integer iSvcCode;
    private Integer serviceGrp;
    private Integer nCMCode;
    private String matType;
    private Integer matGrp;
    private String productSrc;
    private Integer serviceCtg;
    private Character itemClass;
    private Character excisable;
    private Integer chapterID;
    private String notifyASN;
    private String proAssNum;
    private BigDecimal assblValue;
    private Integer dNFEntry;
    private Short userSign2;
    private String spec;
    private String taxCtg;
    private Short series;
    private Integer number;
    private Integer fuelCode;
    private String beverTblC;
    private String beverGrpC;
    private Integer beverTM;
    private String attachment;
    private Integer atcEntry;
    private Integer toleranDay;
    private Integer ugpEntry;
    private Integer pUoMEntry;
    private Integer sUoMEntry;
    private Integer iUoMEntry;
    private Short issuePriBy;
    private String assetClass;
    private String assetGroup;
    private String inventryNo;
    private Integer technician;
    private Integer employee;
    private Integer location;
    private Character statAsset;
    private Character cession;
    private Character deacAftUL;
    private Character asstStatus;
    private Date capDate;
    private Date acqDate;
    private Date retDate;
    private Character gLPickMeth;
    private Character noDiscount;
    private Character mgrByQty;
    private String assetRmk1;
    private String assetRmk2;
    private BigDecimal assetAmnt1;
    private BigDecimal assetAmnt2;
    private String deprGroup;
    private String assetSerNo;
    private String cntUnitMsr;
    private BigDecimal numInCnt;
    private Integer iNUoMEntry;
    private Character oneBOneRec;
    private String ruleCode;
    private String scsCode;
    private String spProdType;
    private BigDecimal iWeight1;
    private Short iWght1Unit;
    private BigDecimal iWeight2;
    private Short iWght2Unit;
    private Character compoWH;
    private Character uBAIsFA;
    private Integer uBATypID;
    private Integer uBANumID;
    private BigDecimal uBALVAFrom;
    private BigDecimal uBaLva;
    private String uUMate;
    private String uGrupo;
    private String uSubGrupo;
    private String uUNomWeb;
    private String uUPicPro;
    private String uUColor;
    private String uURefPro;
    private String uURefAduana;
    private String uUDesAduana;
    private String uUCodAran;
    private String uUCarat;
    private String uUNumPart;
    private String uUColEstru;
    private String uUPalCla;
    private String uUAlt;
    private String uUHabDes;
    private Date uUActQn;
    private String udescripciona;
    private String uCuidados;
    private Integer uCojineria;
    private String uType;
    private BigDecimal uNwrScrp;
    private Short uNwrwopo;
    private Short uNwrMatrix;
    private Character uNWRAutoShipment;
    private String umateriales;
    private String uNumpartes;
    private String ufotohd;
    private String umodelo;
    private Character uescanearbot;
    private String uidyoutube;
    private String ufotosOK;
    private String utalla;
    private String uPosicionChaise;
    private String uregistrocambio;
    private String uDescCorta;
    private String uDescontinuado;
    private String uModelado;
    private Date ureprocesarfotos;
    private String uCodigoEan;
    private String uFactorRedondeo;
    private String uColeccion;
    private String uCodigoMarca;

    public ItemInventarioDTO() {
        saldos = new ArrayList<>();
    }

    public ItemInventarioDTO(String itemCode, String itemName, String uURefPro, String udescripciona) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.uURefPro = uURefPro;
        this.udescripciona = udescripciona;
    }

    public ItemInventarioDTO(String itemCode, String itemName, String frgnName, Character prchseItem, Character sellItem,
            Character invntItem, String uURefPro, String uUHabDes, String udescripciona, String umodelo, String uDescCorta,
            String uCodigoEan) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.frgnName = frgnName;
        this.prchseItem = prchseItem;
        this.sellItem = sellItem;
        this.invntItem = invntItem;
        this.uURefPro = uURefPro;
        this.uUHabDes = uUHabDes;
        this.udescripciona = udescripciona;
        this.umodelo = umodelo;
        this.uDescCorta = uDescCorta;
        this.uCodigoEan = uCodigoEan;
    }

    public ItemInventarioDTO(String itemCode, String itemName, String frgnName, Short itmsGrpCod, String picturName,
            Character qryGroup2, String exportCode, Character indirctTax, String taxCodeAR, String taxCodeAP, String productSrc, String uGrupo, String uSubGrupo, String uUNomWeb, String uUPicPro,
            String uURefPro, String uURefAduana, String uUDesAduana, String uUCodAran, String uUCarat, String uUColEstru, String uUPalCla, String uUAlt, String uUHabDes,
            Date uUActQn, String udescripciona, String uCuidados, Integer uCojineria, String uType, String umateriales, String uNumpartes, String ufotohd, String umodelo, String ufotosOK,
            String uDescCorta, String uModelado, Date ureprocesarfotos, String uCodigoEan, Integer cantidadStock, String uFactorRedondeo, String uColeccion, String uCodigoMarca,
            BigDecimal sHeight1, BigDecimal sWidth1, BigDecimal sLength1, BigDecimal sWeight1) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.frgnName = frgnName;
        this.itmsGrpCod = itmsGrpCod;
        this.picturName = picturName;
        this.qryGroup2 = qryGroup2;
        this.exportCode = exportCode;
        this.indirctTax = indirctTax;
        this.taxCodeAR = taxCodeAR;
        this.taxCodeAP = taxCodeAP;
        this.productSrc = productSrc;
        this.uGrupo = uGrupo;
        this.uSubGrupo = uSubGrupo;
        this.uUNomWeb = uUNomWeb;
        this.uUPicPro = uUPicPro;
        this.uURefPro = uURefPro;
        this.uURefAduana = uURefAduana;
        this.uUDesAduana = uUDesAduana;
        this.uUCodAran = uUCodAran;
        this.uUCarat = uUCarat;
        this.uUColEstru = uUColEstru;
        this.uUPalCla = uUPalCla;
        this.uUAlt = uUAlt;
        this.uUHabDes = uUHabDes;
        this.uUActQn = uUActQn;
        this.udescripciona = udescripciona;
        this.uCuidados = uCuidados;
        this.uCojineria = uCojineria;
        this.uType = uType;
        this.umateriales = umateriales;
        this.uNumpartes = uNumpartes;
        this.ufotohd = ufotohd;
        this.umodelo = umodelo;
        this.ufotosOK = ufotosOK;
        this.uDescCorta = uDescCorta;
        this.uModelado = uModelado;
        this.ureprocesarfotos = ureprocesarfotos;
        this.uCodigoEan = uCodigoEan;
        this.cantidadStock = cantidadStock;
        this.uFactorRedondeo = uFactorRedondeo;
        this.uColeccion = uColeccion;
        this.uCodigoMarca = uCodigoMarca;
        this.sHeight1 = sHeight1;
        this.sWidth1 = sWidth1;
        this.sLength1 = sLength1;
        this.sWeight1 = sWeight1;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getItemNuevo() {
        return itemNuevo;
    }

    public void setItemNuevo(String itemNuevo) {
        this.itemNuevo = itemNuevo;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFrgnName() {
        return frgnName;
    }

    public void setFrgnName(String frgnName) {
        this.frgnName = frgnName;
    }

    public Short getItmsGrpCod() {
        return itmsGrpCod;
    }

    public void setItmsGrpCod(Short itmsGrpCod) {
        this.itmsGrpCod = itmsGrpCod;
    }

    public Short getCstGrpCode() {
        return cstGrpCode;
    }

    public void setCstGrpCode(Short cstGrpCode) {
        this.cstGrpCode = cstGrpCode;
    }

    public String getVatGourpSa() {
        return vatGourpSa;
    }

    public void setVatGourpSa(String vatGourpSa) {
        this.vatGourpSa = vatGourpSa;
    }

    public String getCodeBars() {
        return codeBars;
    }

    public void setCodeBars(String codeBars) {
        this.codeBars = codeBars;
    }

    public Character getvATLiable() {
        return vATLiable;
    }

    public void setvATLiable(Character vATLiable) {
        this.vATLiable = vATLiable;
    }

    public Character getPrchseItem() {
        return prchseItem;
    }

    public void setPrchseItem(Character prchseItem) {
        this.prchseItem = prchseItem;
    }

    public Character getSellItem() {
        return sellItem;
    }

    public void setSellItem(Character sellItem) {
        this.sellItem = sellItem;
    }

    public Character getInvntItem() {
        return invntItem;
    }

    public void setInvntItem(Character invntItem) {
        this.invntItem = invntItem;
    }

    public BigDecimal getOnHand() {
        return onHand;
    }

    public void setOnHand(BigDecimal onHand) {
        this.onHand = onHand;
    }

    public BigDecimal getIsCommited() {
        return isCommited;
    }

    public void setIsCommited(BigDecimal isCommited) {
        this.isCommited = isCommited;
    }

    public BigDecimal getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(BigDecimal onOrder) {
        this.onOrder = onOrder;
    }

    public String getIncomeAcct() {
        return incomeAcct;
    }

    public void setIncomeAcct(String incomeAcct) {
        this.incomeAcct = incomeAcct;
    }

    public String getExmptIncom() {
        return exmptIncom;
    }

    public void setExmptIncom(String exmptIncom) {
        this.exmptIncom = exmptIncom;
    }

    public BigDecimal getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(BigDecimal maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getDfltWH() {
        return dfltWH;
    }

    public void setDfltWH(String dfltWH) {
        this.dfltWH = dfltWH;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getSuppCatNum() {
        return suppCatNum;
    }

    public void setSuppCatNum(String suppCatNum) {
        this.suppCatNum = suppCatNum;
    }

    public String getBuyUnitMsr() {
        return buyUnitMsr;
    }

    public void setBuyUnitMsr(String buyUnitMsr) {
        this.buyUnitMsr = buyUnitMsr;
    }

    public BigDecimal getNumInBuy() {
        return numInBuy;
    }

    public void setNumInBuy(BigDecimal numInBuy) {
        this.numInBuy = numInBuy;
    }

    public BigDecimal getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(BigDecimal reorderQty) {
        this.reorderQty = reorderQty;
    }

    public BigDecimal getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(BigDecimal minLevel) {
        this.minLevel = minLevel;
    }

    public BigDecimal getLstEvlPric() {
        return lstEvlPric;
    }

    public void setLstEvlPric(BigDecimal lstEvlPric) {
        this.lstEvlPric = lstEvlPric;
    }

    public Date getLstEvlDate() {
        return lstEvlDate;
    }

    public void setLstEvlDate(Date lstEvlDate) {
        this.lstEvlDate = lstEvlDate;
    }

    public BigDecimal getCustomPer() {
        return customPer;
    }

    public void setCustomPer(BigDecimal customPer) {
        this.customPer = customPer;
    }

    public Character getCanceled() {
        return canceled;
    }

    public void setCanceled(Character canceled) {
        this.canceled = canceled;
    }

    public Integer getMnufctTime() {
        return mnufctTime;
    }

    public void setMnufctTime(Integer mnufctTime) {
        this.mnufctTime = mnufctTime;
    }

    public Character getWholSlsTax() {
        return wholSlsTax;
    }

    public void setWholSlsTax(Character wholSlsTax) {
        this.wholSlsTax = wholSlsTax;
    }

    public Character getRetilrTax() {
        return retilrTax;
    }

    public void setRetilrTax(Character retilrTax) {
        this.retilrTax = retilrTax;
    }

    public BigDecimal getSpcialDisc() {
        return spcialDisc;
    }

    public void setSpcialDisc(BigDecimal spcialDisc) {
        this.spcialDisc = spcialDisc;
    }

    public Short getDscountCod() {
        return dscountCod;
    }

    public void setDscountCod(Short dscountCod) {
        this.dscountCod = dscountCod;
    }

    public Character getTrackSales() {
        return trackSales;
    }

    public void setTrackSales(Character trackSales) {
        this.trackSales = trackSales;
    }

    public String getSalUnitMsr() {
        return salUnitMsr;
    }

    public void setSalUnitMsr(String salUnitMsr) {
        this.salUnitMsr = salUnitMsr;
    }

    public BigDecimal getNumInSale() {
        return numInSale;
    }

    public void setNumInSale(BigDecimal numInSale) {
        this.numInSale = numInSale;
    }

    public BigDecimal getConsig() {
        return consig;
    }

    public void setConsig(BigDecimal consig) {
        this.consig = consig;
    }

    public Integer getQueryGroup() {
        return queryGroup;
    }

    public void setQueryGroup(Integer queryGroup) {
        this.queryGroup = queryGroup;
    }

    public BigDecimal getCounted() {
        return counted;
    }

    public void setCounted(BigDecimal counted) {
        this.counted = counted;
    }

    public BigDecimal getOpenBlnc() {
        return openBlnc;
    }

    public void setOpenBlnc(BigDecimal openBlnc) {
        this.openBlnc = openBlnc;
    }

    public Character getEvalSystem() {
        return evalSystem;
    }

    public void setEvalSystem(Character evalSystem) {
        this.evalSystem = evalSystem;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Character getFree() {
        return free;
    }

    public void setFree(Character free) {
        this.free = free;
    }

    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public Character getTransfered() {
        return transfered;
    }

    public void setTransfered(Character transfered) {
        this.transfered = transfered;
    }

    public Character getBlncTrnsfr() {
        return blncTrnsfr;
    }

    public void setBlncTrnsfr(Character blncTrnsfr) {
        this.blncTrnsfr = blncTrnsfr;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public BigDecimal getCommisPcnt() {
        return commisPcnt;
    }

    public void setCommisPcnt(BigDecimal commisPcnt) {
        this.commisPcnt = commisPcnt;
    }

    public BigDecimal getCommisSum() {
        return commisSum;
    }

    public void setCommisSum(BigDecimal commisSum) {
        this.commisSum = commisSum;
    }

    public Short getCommisGrp() {
        return commisGrp;
    }

    public void setCommisGrp(Short commisGrp) {
        this.commisGrp = commisGrp;
    }

    public Character getTreeType() {
        return treeType;
    }

    public void setTreeType(Character treeType) {
        this.treeType = treeType;
    }

    public BigDecimal getTreeQty() {
        return treeQty;
    }

    public void setTreeQty(BigDecimal treeQty) {
        this.treeQty = treeQty;
    }

    public BigDecimal getLastPurPrc() {
        return lastPurPrc;
    }

    public void setLastPurPrc(BigDecimal lastPurPrc) {
        this.lastPurPrc = lastPurPrc;
    }

    public String getLastPurCur() {
        return lastPurCur;
    }

    public void setLastPurCur(String lastPurCur) {
        this.lastPurCur = lastPurCur;
    }

    public Date getLastPurDat() {
        return lastPurDat;
    }

    public void setLastPurDat(Date lastPurDat) {
        this.lastPurDat = lastPurDat;
    }

    public String getExitCur() {
        return exitCur;
    }

    public void setExitCur(String exitCur) {
        this.exitCur = exitCur;
    }

    public BigDecimal getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(BigDecimal exitPrice) {
        this.exitPrice = exitPrice;
    }

    public String getExitWH() {
        return exitWH;
    }

    public void setExitWH(String exitWH) {
        this.exitWH = exitWH;
    }

    public Character getAssetItem() {
        return assetItem;
    }

    public void setAssetItem(Character assetItem) {
        this.assetItem = assetItem;
    }

    public Character getWasCounted() {
        return wasCounted;
    }

    public void setWasCounted(Character wasCounted) {
        this.wasCounted = wasCounted;
    }

    public Character getManSerNum() {
        return manSerNum;
    }

    public void setManSerNum(Character manSerNum) {
        this.manSerNum = manSerNum;
    }

    public BigDecimal getsHeight1() {
        return sHeight1;
    }

    public void setsHeight1(BigDecimal sHeight1) {
        this.sHeight1 = sHeight1;
    }

    public Short getsHght1Unit() {
        return sHght1Unit;
    }

    public void setsHght1Unit(Short sHght1Unit) {
        this.sHght1Unit = sHght1Unit;
    }

    public BigDecimal getsHeight2() {
        return sHeight2;
    }

    public void setsHeight2(BigDecimal sHeight2) {
        this.sHeight2 = sHeight2;
    }

    public Short getsHght2Unit() {
        return sHght2Unit;
    }

    public void setsHght2Unit(Short sHght2Unit) {
        this.sHght2Unit = sHght2Unit;
    }

    public BigDecimal getsWidth1() {
        return sWidth1;
    }

    public void setsWidth1(BigDecimal sWidth1) {
        this.sWidth1 = sWidth1;
    }

    public Short getsWdth1Unit() {
        return sWdth1Unit;
    }

    public void setsWdth1Unit(Short sWdth1Unit) {
        this.sWdth1Unit = sWdth1Unit;
    }

    public BigDecimal getsWidth2() {
        return sWidth2;
    }

    public void setsWidth2(BigDecimal sWidth2) {
        this.sWidth2 = sWidth2;
    }

    public Short getsWdth2Unit() {
        return sWdth2Unit;
    }

    public void setsWdth2Unit(Short sWdth2Unit) {
        this.sWdth2Unit = sWdth2Unit;
    }

    public BigDecimal getsLength1() {
        return sLength1;
    }

    public void setsLength1(BigDecimal sLength1) {
        this.sLength1 = sLength1;
    }

    public Short getsLen1Unit() {
        return sLen1Unit;
    }

    public void setsLen1Unit(Short sLen1Unit) {
        this.sLen1Unit = sLen1Unit;
    }

    public BigDecimal getSlength2() {
        return slength2;
    }

    public void setSlength2(BigDecimal slength2) {
        this.slength2 = slength2;
    }

    public Short getsLen2Unit() {
        return sLen2Unit;
    }

    public void setsLen2Unit(Short sLen2Unit) {
        this.sLen2Unit = sLen2Unit;
    }

    public BigDecimal getsVolume() {
        return sVolume;
    }

    public void setsVolume(BigDecimal sVolume) {
        this.sVolume = sVolume;
    }

    public Short getsVolUnit() {
        return sVolUnit;
    }

    public void setsVolUnit(Short sVolUnit) {
        this.sVolUnit = sVolUnit;
    }

    public BigDecimal getsWeight1() {
        return sWeight1;
    }

    public void setsWeight1(BigDecimal sWeight1) {
        this.sWeight1 = sWeight1;
    }

    public Short getsWght1Unit() {
        return sWght1Unit;
    }

    public void setsWght1Unit(Short sWght1Unit) {
        this.sWght1Unit = sWght1Unit;
    }

    public BigDecimal getsWeight2() {
        return sWeight2;
    }

    public void setsWeight2(BigDecimal sWeight2) {
        this.sWeight2 = sWeight2;
    }

    public Short getsWght2Unit() {
        return sWght2Unit;
    }

    public void setsWght2Unit(Short sWght2Unit) {
        this.sWght2Unit = sWght2Unit;
    }

    public BigDecimal getbHeight1() {
        return bHeight1;
    }

    public void setbHeight1(BigDecimal bHeight1) {
        this.bHeight1 = bHeight1;
    }

    public Short getbHght1Unit() {
        return bHght1Unit;
    }

    public void setbHght1Unit(Short bHght1Unit) {
        this.bHght1Unit = bHght1Unit;
    }

    public BigDecimal getbHeight2() {
        return bHeight2;
    }

    public void setbHeight2(BigDecimal bHeight2) {
        this.bHeight2 = bHeight2;
    }

    public Short getbHght2Unit() {
        return bHght2Unit;
    }

    public void setbHght2Unit(Short bHght2Unit) {
        this.bHght2Unit = bHght2Unit;
    }

    public BigDecimal getbWidth1() {
        return bWidth1;
    }

    public void setbWidth1(BigDecimal bWidth1) {
        this.bWidth1 = bWidth1;
    }

    public Short getbWdth1Unit() {
        return bWdth1Unit;
    }

    public void setbWdth1Unit(Short bWdth1Unit) {
        this.bWdth1Unit = bWdth1Unit;
    }

    public BigDecimal getbWidth2() {
        return bWidth2;
    }

    public void setbWidth2(BigDecimal bWidth2) {
        this.bWidth2 = bWidth2;
    }

    public Short getbWdth2Unit() {
        return bWdth2Unit;
    }

    public void setbWdth2Unit(Short bWdth2Unit) {
        this.bWdth2Unit = bWdth2Unit;
    }

    public BigDecimal getbLength1() {
        return bLength1;
    }

    public void setbLength1(BigDecimal bLength1) {
        this.bLength1 = bLength1;
    }

    public Short getbLen1Unit() {
        return bLen1Unit;
    }

    public void setbLen1Unit(Short bLen1Unit) {
        this.bLen1Unit = bLen1Unit;
    }

    public BigDecimal getBlength2() {
        return blength2;
    }

    public void setBlength2(BigDecimal blength2) {
        this.blength2 = blength2;
    }

    public Short getbLen2Unit() {
        return bLen2Unit;
    }

    public void setbLen2Unit(Short bLen2Unit) {
        this.bLen2Unit = bLen2Unit;
    }

    public BigDecimal getbVolume() {
        return bVolume;
    }

    public void setbVolume(BigDecimal bVolume) {
        this.bVolume = bVolume;
    }

    public Short getbVolUnit() {
        return bVolUnit;
    }

    public void setbVolUnit(Short bVolUnit) {
        this.bVolUnit = bVolUnit;
    }

    public BigDecimal getbWeight1() {
        return bWeight1;
    }

    public void setbWeight1(BigDecimal bWeight1) {
        this.bWeight1 = bWeight1;
    }

    public Short getbWght1Unit() {
        return bWght1Unit;
    }

    public void setbWght1Unit(Short bWght1Unit) {
        this.bWght1Unit = bWght1Unit;
    }

    public BigDecimal getbWeight2() {
        return bWeight2;
    }

    public void setbWeight2(BigDecimal bWeight2) {
        this.bWeight2 = bWeight2;
    }

    public Short getbWght2Unit() {
        return bWght2Unit;
    }

    public void setbWght2Unit(Short bWght2Unit) {
        this.bWght2Unit = bWght2Unit;
    }

    public String getFixCurrCms() {
        return fixCurrCms;
    }

    public void setFixCurrCms(String fixCurrCms) {
        this.fixCurrCms = fixCurrCms;
    }

    public Short getFirmCode() {
        return firmCode;
    }

    public void setFirmCode(Short firmCode) {
        this.firmCode = firmCode;
    }

    public Date getLstSalDate() {
        return lstSalDate;
    }

    public void setLstSalDate(Date lstSalDate) {
        this.lstSalDate = lstSalDate;
    }

    public Character getQryGroup1() {
        return qryGroup1;
    }

    public void setQryGroup1(Character qryGroup1) {
        this.qryGroup1 = qryGroup1;
    }

    public Character getQryGroup2() {
        return qryGroup2;
    }

    public void setQryGroup2(Character qryGroup2) {
        this.qryGroup2 = qryGroup2;
    }

    public Character getQryGroup3() {
        return qryGroup3;
    }

    public void setQryGroup3(Character qryGroup3) {
        this.qryGroup3 = qryGroup3;
    }

    public Character getQryGroup4() {
        return qryGroup4;
    }

    public void setQryGroup4(Character qryGroup4) {
        this.qryGroup4 = qryGroup4;
    }

    public Character getQryGroup5() {
        return qryGroup5;
    }

    public void setQryGroup5(Character qryGroup5) {
        this.qryGroup5 = qryGroup5;
    }

    public Character getQryGroup6() {
        return qryGroup6;
    }

    public void setQryGroup6(Character qryGroup6) {
        this.qryGroup6 = qryGroup6;
    }

    public Character getQryGroup7() {
        return qryGroup7;
    }

    public void setQryGroup7(Character qryGroup7) {
        this.qryGroup7 = qryGroup7;
    }

    public Character getQryGroup8() {
        return qryGroup8;
    }

    public void setQryGroup8(Character qryGroup8) {
        this.qryGroup8 = qryGroup8;
    }

    public Character getQryGroup9() {
        return qryGroup9;
    }

    public void setQryGroup9(Character qryGroup9) {
        this.qryGroup9 = qryGroup9;
    }

    public Character getQryGroup10() {
        return qryGroup10;
    }

    public void setQryGroup10(Character qryGroup10) {
        this.qryGroup10 = qryGroup10;
    }

    public Character getQryGroup11() {
        return qryGroup11;
    }

    public void setQryGroup11(Character qryGroup11) {
        this.qryGroup11 = qryGroup11;
    }

    public Character getQryGroup12() {
        return qryGroup12;
    }

    public void setQryGroup12(Character qryGroup12) {
        this.qryGroup12 = qryGroup12;
    }

    public Character getQryGroup13() {
        return qryGroup13;
    }

    public void setQryGroup13(Character qryGroup13) {
        this.qryGroup13 = qryGroup13;
    }

    public Character getQryGroup14() {
        return qryGroup14;
    }

    public void setQryGroup14(Character qryGroup14) {
        this.qryGroup14 = qryGroup14;
    }

    public Character getQryGroup15() {
        return qryGroup15;
    }

    public void setQryGroup15(Character qryGroup15) {
        this.qryGroup15 = qryGroup15;
    }

    public Character getQryGroup16() {
        return qryGroup16;
    }

    public void setQryGroup16(Character qryGroup16) {
        this.qryGroup16 = qryGroup16;
    }

    public Character getQryGroup17() {
        return qryGroup17;
    }

    public void setQryGroup17(Character qryGroup17) {
        this.qryGroup17 = qryGroup17;
    }

    public Character getQryGroup18() {
        return qryGroup18;
    }

    public void setQryGroup18(Character qryGroup18) {
        this.qryGroup18 = qryGroup18;
    }

    public Character getQryGroup19() {
        return qryGroup19;
    }

    public void setQryGroup19(Character qryGroup19) {
        this.qryGroup19 = qryGroup19;
    }

    public Character getQryGroup20() {
        return qryGroup20;
    }

    public void setQryGroup20(Character qryGroup20) {
        this.qryGroup20 = qryGroup20;
    }

    public Character getQryGroup21() {
        return qryGroup21;
    }

    public void setQryGroup21(Character qryGroup21) {
        this.qryGroup21 = qryGroup21;
    }

    public Character getQryGroup22() {
        return qryGroup22;
    }

    public void setQryGroup22(Character qryGroup22) {
        this.qryGroup22 = qryGroup22;
    }

    public Character getQryGroup23() {
        return qryGroup23;
    }

    public void setQryGroup23(Character qryGroup23) {
        this.qryGroup23 = qryGroup23;
    }

    public Character getQryGroup24() {
        return qryGroup24;
    }

    public void setQryGroup24(Character qryGroup24) {
        this.qryGroup24 = qryGroup24;
    }

    public Character getQryGroup25() {
        return qryGroup25;
    }

    public void setQryGroup25(Character qryGroup25) {
        this.qryGroup25 = qryGroup25;
    }

    public Character getQryGroup26() {
        return qryGroup26;
    }

    public void setQryGroup26(Character qryGroup26) {
        this.qryGroup26 = qryGroup26;
    }

    public Character getQryGroup27() {
        return qryGroup27;
    }

    public void setQryGroup27(Character qryGroup27) {
        this.qryGroup27 = qryGroup27;
    }

    public Character getQryGroup28() {
        return qryGroup28;
    }

    public void setQryGroup28(Character qryGroup28) {
        this.qryGroup28 = qryGroup28;
    }

    public Character getQryGroup29() {
        return qryGroup29;
    }

    public void setQryGroup29(Character qryGroup29) {
        this.qryGroup29 = qryGroup29;
    }

    public Character getQryGroup30() {
        return qryGroup30;
    }

    public void setQryGroup30(Character qryGroup30) {
        this.qryGroup30 = qryGroup30;
    }

    public Character getQryGroup31() {
        return qryGroup31;
    }

    public void setQryGroup31(Character qryGroup31) {
        this.qryGroup31 = qryGroup31;
    }

    public Character getQryGroup32() {
        return qryGroup32;
    }

    public void setQryGroup32(Character qryGroup32) {
        this.qryGroup32 = qryGroup32;
    }

    public Character getQryGroup33() {
        return qryGroup33;
    }

    public void setQryGroup33(Character qryGroup33) {
        this.qryGroup33 = qryGroup33;
    }

    public Character getQryGroup34() {
        return qryGroup34;
    }

    public void setQryGroup34(Character qryGroup34) {
        this.qryGroup34 = qryGroup34;
    }

    public Character getQryGroup35() {
        return qryGroup35;
    }

    public void setQryGroup35(Character qryGroup35) {
        this.qryGroup35 = qryGroup35;
    }

    public Character getQryGroup36() {
        return qryGroup36;
    }

    public void setQryGroup36(Character qryGroup36) {
        this.qryGroup36 = qryGroup36;
    }

    public Character getQryGroup37() {
        return qryGroup37;
    }

    public void setQryGroup37(Character qryGroup37) {
        this.qryGroup37 = qryGroup37;
    }

    public Character getQryGroup38() {
        return qryGroup38;
    }

    public void setQryGroup38(Character qryGroup38) {
        this.qryGroup38 = qryGroup38;
    }

    public Character getQryGroup39() {
        return qryGroup39;
    }

    public void setQryGroup39(Character qryGroup39) {
        this.qryGroup39 = qryGroup39;
    }

    public Character getQryGroup40() {
        return qryGroup40;
    }

    public void setQryGroup40(Character qryGroup40) {
        this.qryGroup40 = qryGroup40;
    }

    public Character getQryGroup41() {
        return qryGroup41;
    }

    public void setQryGroup41(Character qryGroup41) {
        this.qryGroup41 = qryGroup41;
    }

    public Character getQryGroup42() {
        return qryGroup42;
    }

    public void setQryGroup42(Character qryGroup42) {
        this.qryGroup42 = qryGroup42;
    }

    public Character getQryGroup43() {
        return qryGroup43;
    }

    public void setQryGroup43(Character qryGroup43) {
        this.qryGroup43 = qryGroup43;
    }

    public Character getQryGroup44() {
        return qryGroup44;
    }

    public void setQryGroup44(Character qryGroup44) {
        this.qryGroup44 = qryGroup44;
    }

    public Character getQryGroup45() {
        return qryGroup45;
    }

    public void setQryGroup45(Character qryGroup45) {
        this.qryGroup45 = qryGroup45;
    }

    public Character getQryGroup46() {
        return qryGroup46;
    }

    public void setQryGroup46(Character qryGroup46) {
        this.qryGroup46 = qryGroup46;
    }

    public Character getQryGroup47() {
        return qryGroup47;
    }

    public void setQryGroup47(Character qryGroup47) {
        this.qryGroup47 = qryGroup47;
    }

    public Character getQryGroup48() {
        return qryGroup48;
    }

    public void setQryGroup48(Character qryGroup48) {
        this.qryGroup48 = qryGroup48;
    }

    public Character getQryGroup49() {
        return qryGroup49;
    }

    public void setQryGroup49(Character qryGroup49) {
        this.qryGroup49 = qryGroup49;
    }

    public Character getQryGroup50() {
        return qryGroup50;
    }

    public void setQryGroup50(Character qryGroup50) {
        this.qryGroup50 = qryGroup50;
    }

    public Character getQryGroup51() {
        return qryGroup51;
    }

    public void setQryGroup51(Character qryGroup51) {
        this.qryGroup51 = qryGroup51;
    }

    public Character getQryGroup52() {
        return qryGroup52;
    }

    public void setQryGroup52(Character qryGroup52) {
        this.qryGroup52 = qryGroup52;
    }

    public Character getQryGroup53() {
        return qryGroup53;
    }

    public void setQryGroup53(Character qryGroup53) {
        this.qryGroup53 = qryGroup53;
    }

    public Character getQryGroup54() {
        return qryGroup54;
    }

    public void setQryGroup54(Character qryGroup54) {
        this.qryGroup54 = qryGroup54;
    }

    public Character getQryGroup55() {
        return qryGroup55;
    }

    public void setQryGroup55(Character qryGroup55) {
        this.qryGroup55 = qryGroup55;
    }

    public Character getQryGroup56() {
        return qryGroup56;
    }

    public void setQryGroup56(Character qryGroup56) {
        this.qryGroup56 = qryGroup56;
    }

    public Character getQryGroup57() {
        return qryGroup57;
    }

    public void setQryGroup57(Character qryGroup57) {
        this.qryGroup57 = qryGroup57;
    }

    public Character getQryGroup58() {
        return qryGroup58;
    }

    public void setQryGroup58(Character qryGroup58) {
        this.qryGroup58 = qryGroup58;
    }

    public Character getQryGroup59() {
        return qryGroup59;
    }

    public void setQryGroup59(Character qryGroup59) {
        this.qryGroup59 = qryGroup59;
    }

    public Character getQryGroup60() {
        return qryGroup60;
    }

    public void setQryGroup60(Character qryGroup60) {
        this.qryGroup60 = qryGroup60;
    }

    public Character getQryGroup61() {
        return qryGroup61;
    }

    public void setQryGroup61(Character qryGroup61) {
        this.qryGroup61 = qryGroup61;
    }

    public Character getQryGroup62() {
        return qryGroup62;
    }

    public void setQryGroup62(Character qryGroup62) {
        this.qryGroup62 = qryGroup62;
    }

    public Character getQryGroup63() {
        return qryGroup63;
    }

    public void setQryGroup63(Character qryGroup63) {
        this.qryGroup63 = qryGroup63;
    }

    public Character getQryGroup64() {
        return qryGroup64;
    }

    public void setQryGroup64(Character qryGroup64) {
        this.qryGroup64 = qryGroup64;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getExportCode() {
        return exportCode;
    }

    public void setExportCode(String exportCode) {
        this.exportCode = exportCode;
    }

    public BigDecimal getSalFactor1() {
        return salFactor1;
    }

    public void setSalFactor1(BigDecimal salFactor1) {
        this.salFactor1 = salFactor1;
    }

    public BigDecimal getSalFactor2() {
        return salFactor2;
    }

    public void setSalFactor2(BigDecimal salFactor2) {
        this.salFactor2 = salFactor2;
    }

    public BigDecimal getSalFactor3() {
        return salFactor3;
    }

    public void setSalFactor3(BigDecimal salFactor3) {
        this.salFactor3 = salFactor3;
    }

    public BigDecimal getSalFactor4() {
        return salFactor4;
    }

    public void setSalFactor4(BigDecimal salFactor4) {
        this.salFactor4 = salFactor4;
    }

    public BigDecimal getPurFactor1() {
        return purFactor1;
    }

    public void setPurFactor1(BigDecimal purFactor1) {
        this.purFactor1 = purFactor1;
    }

    public BigDecimal getPurFactor2() {
        return purFactor2;
    }

    public void setPurFactor2(BigDecimal purFactor2) {
        this.purFactor2 = purFactor2;
    }

    public BigDecimal getPurFactor3() {
        return purFactor3;
    }

    public void setPurFactor3(BigDecimal purFactor3) {
        this.purFactor3 = purFactor3;
    }

    public BigDecimal getPurFactor4() {
        return purFactor4;
    }

    public void setPurFactor4(BigDecimal purFactor4) {
        this.purFactor4 = purFactor4;
    }

    public String getSalFormula() {
        return salFormula;
    }

    public void setSalFormula(String salFormula) {
        this.salFormula = salFormula;
    }

    public String getPurFormula() {
        return purFormula;
    }

    public void setPurFormula(String purFormula) {
        this.purFormula = purFormula;
    }

    public String getVatGroupPu() {
        return vatGroupPu;
    }

    public void setVatGroupPu(String vatGroupPu) {
        this.vatGroupPu = vatGroupPu;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getPurPackMsr() {
        return purPackMsr;
    }

    public void setPurPackMsr(String purPackMsr) {
        this.purPackMsr = purPackMsr;
    }

    public BigDecimal getPurPackUn() {
        return purPackUn;
    }

    public void setPurPackUn(BigDecimal purPackUn) {
        this.purPackUn = purPackUn;
    }

    public String getSalPackMsr() {
        return salPackMsr;
    }

    public void setSalPackMsr(String salPackMsr) {
        this.salPackMsr = salPackMsr;
    }

    public BigDecimal getSalPackUn() {
        return salPackUn;
    }

    public void setSalPackUn(BigDecimal salPackUn) {
        this.salPackUn = salPackUn;
    }

    public Short getsCNCounter() {
        return sCNCounter;
    }

    public void setsCNCounter(Short sCNCounter) {
        this.sCNCounter = sCNCounter;
    }

    public Character getManBtchNum() {
        return manBtchNum;
    }

    public void setManBtchNum(Character manBtchNum) {
        this.manBtchNum = manBtchNum;
    }

    public Character getManOutOnly() {
        return manOutOnly;
    }

    public void setManOutOnly(Character manOutOnly) {
        this.manOutOnly = manOutOnly;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Character getValidFor() {
        return validFor;
    }

    public void setValidFor(Character validFor) {
        this.validFor = validFor;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Character getFrozenFor() {
        return frozenFor;
    }

    public void setFrozenFor(Character frozenFor) {
        this.frozenFor = frozenFor;
    }

    public Date getFrozenFrom() {
        return frozenFrom;
    }

    public void setFrozenFrom(Date frozenFrom) {
        this.frozenFrom = frozenFrom;
    }

    public Date getFrozenTo() {
        return frozenTo;
    }

    public void setFrozenTo(Date frozenTo) {
        this.frozenTo = frozenTo;
    }

    public Character getBlockOut() {
        return blockOut;
    }

    public void setBlockOut(Character blockOut) {
        this.blockOut = blockOut;
    }

    public String getValidComm() {
        return validComm;
    }

    public void setValidComm(String validComm) {
        this.validComm = validComm;
    }

    public String getFrozenComm() {
        return frozenComm;
    }

    public void setFrozenComm(String frozenComm) {
        this.frozenComm = frozenComm;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getSww() {
        return sww;
    }

    public void setSww(String sww) {
        this.sww = sww;
    }

    public Character getDeleted() {
        return deleted;
    }

    public void setDeleted(Character deleted) {
        this.deleted = deleted;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public String getExpensAcct() {
        return expensAcct;
    }

    public void setExpensAcct(String expensAcct) {
        this.expensAcct = expensAcct;
    }

    public String getFrgnInAcct() {
        return frgnInAcct;
    }

    public void setFrgnInAcct(String frgnInAcct) {
        this.frgnInAcct = frgnInAcct;
    }

    public Short getShipType() {
        return shipType;
    }

    public void setShipType(Short shipType) {
        this.shipType = shipType;
    }

    public Character getgLMethod() {
        return gLMethod;
    }

    public void setgLMethod(Character gLMethod) {
        this.gLMethod = gLMethod;
    }

    public String geteCInAcct() {
        return eCInAcct;
    }

    public void seteCInAcct(String eCInAcct) {
        this.eCInAcct = eCInAcct;
    }

    public String getFrgnExpAcc() {
        return frgnExpAcc;
    }

    public void setFrgnExpAcc(String frgnExpAcc) {
        this.frgnExpAcc = frgnExpAcc;
    }

    public String geteCExpAcc() {
        return eCExpAcc;
    }

    public void seteCExpAcc(String eCExpAcc) {
        this.eCExpAcc = eCExpAcc;
    }

    public Character getTaxType() {
        return taxType;
    }

    public void setTaxType(Character taxType) {
        this.taxType = taxType;
    }

    public Character getByWh() {
        return byWh;
    }

    public void setByWh(Character byWh) {
        this.byWh = byWh;
    }

    public Character getwTLiable() {
        return wTLiable;
    }

    public void setwTLiable(Character wTLiable) {
        this.wTLiable = wTLiable;
    }

    public Character getItemType() {
        return itemType;
    }

    public void setItemType(Character itemType) {
        this.itemType = itemType;
    }

    public String getWarrntTmpl() {
        return warrntTmpl;
    }

    public void setWarrntTmpl(String warrntTmpl) {
        this.warrntTmpl = warrntTmpl;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getCountryOrg() {
        return countryOrg;
    }

    public void setCountryOrg(String countryOrg) {
        this.countryOrg = countryOrg;
    }

    public BigDecimal getStockValue() {
        return stockValue;
    }

    public void setStockValue(BigDecimal stockValue) {
        this.stockValue = stockValue;
    }

    public Character getPhantom() {
        return phantom;
    }

    public void setPhantom(Character phantom) {
        this.phantom = phantom;
    }

    public Character getIssueMthd() {
        return issueMthd;
    }

    public void setIssueMthd(Character issueMthd) {
        this.issueMthd = issueMthd;
    }

    public Character getFree1() {
        return free1;
    }

    public void setFree1(Character free1) {
        this.free1 = free1;
    }

    public BigDecimal getPricingPrc() {
        return pricingPrc;
    }

    public void setPricingPrc(BigDecimal pricingPrc) {
        this.pricingPrc = pricingPrc;
    }

    public Character getMngMethod() {
        return mngMethod;
    }

    public void setMngMethod(Character mngMethod) {
        this.mngMethod = mngMethod;
    }

    public BigDecimal getReorderPnt() {
        return reorderPnt;
    }

    public void setReorderPnt(BigDecimal reorderPnt) {
        this.reorderPnt = reorderPnt;
    }

    public String getInvntryUom() {
        return invntryUom;
    }

    public void setInvntryUom(String invntryUom) {
        this.invntryUom = invntryUom;
    }

    public Character getPlaningSys() {
        return planingSys;
    }

    public void setPlaningSys(Character planingSys) {
        this.planingSys = planingSys;
    }

    public Character getPrcrmntMtd() {
        return prcrmntMtd;
    }

    public void setPrcrmntMtd(Character prcrmntMtd) {
        this.prcrmntMtd = prcrmntMtd;
    }

    public Short getOrdrIntrvl() {
        return ordrIntrvl;
    }

    public void setOrdrIntrvl(Short ordrIntrvl) {
        this.ordrIntrvl = ordrIntrvl;
    }

    public BigDecimal getOrdrMulti() {
        return ordrMulti;
    }

    public void setOrdrMulti(BigDecimal ordrMulti) {
        this.ordrMulti = ordrMulti;
    }

    public BigDecimal getMinOrdrQty() {
        return minOrdrQty;
    }

    public void setMinOrdrQty(BigDecimal minOrdrQty) {
        this.minOrdrQty = minOrdrQty;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Character getIndirctTax() {
        return indirctTax;
    }

    public void setIndirctTax(Character indirctTax) {
        this.indirctTax = indirctTax;
    }

    public String getTaxCodeAR() {
        return taxCodeAR;
    }

    public void setTaxCodeAR(String taxCodeAR) {
        this.taxCodeAR = taxCodeAR;
    }

    public String getTaxCodeAP() {
        return taxCodeAP;
    }

    public void setTaxCodeAP(String taxCodeAP) {
        this.taxCodeAP = taxCodeAP;
    }

    public Integer getoSvcCode() {
        return oSvcCode;
    }

    public void setoSvcCode(Integer oSvcCode) {
        this.oSvcCode = oSvcCode;
    }

    public Integer getiSvcCode() {
        return iSvcCode;
    }

    public void setiSvcCode(Integer iSvcCode) {
        this.iSvcCode = iSvcCode;
    }

    public Integer getServiceGrp() {
        return serviceGrp;
    }

    public void setServiceGrp(Integer serviceGrp) {
        this.serviceGrp = serviceGrp;
    }

    public Integer getnCMCode() {
        return nCMCode;
    }

    public void setnCMCode(Integer nCMCode) {
        this.nCMCode = nCMCode;
    }

    public String getMatType() {
        return matType;
    }

    public void setMatType(String matType) {
        this.matType = matType;
    }

    public Integer getMatGrp() {
        return matGrp;
    }

    public void setMatGrp(Integer matGrp) {
        this.matGrp = matGrp;
    }

    public String getProductSrc() {
        return productSrc;
    }

    public void setProductSrc(String productSrc) {
        this.productSrc = productSrc;
    }

    public Integer getServiceCtg() {
        return serviceCtg;
    }

    public void setServiceCtg(Integer serviceCtg) {
        this.serviceCtg = serviceCtg;
    }

    public Character getItemClass() {
        return itemClass;
    }

    public void setItemClass(Character itemClass) {
        this.itemClass = itemClass;
    }

    public Character getExcisable() {
        return excisable;
    }

    public void setExcisable(Character excisable) {
        this.excisable = excisable;
    }

    public Integer getChapterID() {
        return chapterID;
    }

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public String getNotifyASN() {
        return notifyASN;
    }

    public void setNotifyASN(String notifyASN) {
        this.notifyASN = notifyASN;
    }

    public String getProAssNum() {
        return proAssNum;
    }

    public void setProAssNum(String proAssNum) {
        this.proAssNum = proAssNum;
    }

    public BigDecimal getAssblValue() {
        return assblValue;
    }

    public void setAssblValue(BigDecimal assblValue) {
        this.assblValue = assblValue;
    }

    public Integer getdNFEntry() {
        return dNFEntry;
    }

    public void setdNFEntry(Integer dNFEntry) {
        this.dNFEntry = dNFEntry;
    }

    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTaxCtg() {
        return taxCtg;
    }

    public void setTaxCtg(String taxCtg) {
        this.taxCtg = taxCtg;
    }

    public Short getSeries() {
        return series;
    }

    public void setSeries(Short series) {
        this.series = series;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFuelCode() {
        return fuelCode;
    }

    public void setFuelCode(Integer fuelCode) {
        this.fuelCode = fuelCode;
    }

    public String getBeverTblC() {
        return beverTblC;
    }

    public void setBeverTblC(String beverTblC) {
        this.beverTblC = beverTblC;
    }

    public String getBeverGrpC() {
        return beverGrpC;
    }

    public void setBeverGrpC(String beverGrpC) {
        this.beverGrpC = beverGrpC;
    }

    public Integer getBeverTM() {
        return beverTM;
    }

    public void setBeverTM(Integer beverTM) {
        this.beverTM = beverTM;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getAtcEntry() {
        return atcEntry;
    }

    public void setAtcEntry(Integer atcEntry) {
        this.atcEntry = atcEntry;
    }

    public Integer getToleranDay() {
        return toleranDay;
    }

    public void setToleranDay(Integer toleranDay) {
        this.toleranDay = toleranDay;
    }

    public Integer getUgpEntry() {
        return ugpEntry;
    }

    public void setUgpEntry(Integer ugpEntry) {
        this.ugpEntry = ugpEntry;
    }

    public Integer getpUoMEntry() {
        return pUoMEntry;
    }

    public void setpUoMEntry(Integer pUoMEntry) {
        this.pUoMEntry = pUoMEntry;
    }

    public Integer getsUoMEntry() {
        return sUoMEntry;
    }

    public void setsUoMEntry(Integer sUoMEntry) {
        this.sUoMEntry = sUoMEntry;
    }

    public Integer getiUoMEntry() {
        return iUoMEntry;
    }

    public void setiUoMEntry(Integer iUoMEntry) {
        this.iUoMEntry = iUoMEntry;
    }

    public Short getIssuePriBy() {
        return issuePriBy;
    }

    public void setIssuePriBy(Short issuePriBy) {
        this.issuePriBy = issuePriBy;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getInventryNo() {
        return inventryNo;
    }

    public void setInventryNo(String inventryNo) {
        this.inventryNo = inventryNo;
    }

    public Integer getTechnician() {
        return technician;
    }

    public void setTechnician(Integer technician) {
        this.technician = technician;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Character getStatAsset() {
        return statAsset;
    }

    public void setStatAsset(Character statAsset) {
        this.statAsset = statAsset;
    }

    public Character getCession() {
        return cession;
    }

    public void setCession(Character cession) {
        this.cession = cession;
    }

    public Character getDeacAftUL() {
        return deacAftUL;
    }

    public void setDeacAftUL(Character deacAftUL) {
        this.deacAftUL = deacAftUL;
    }

    public Character getAsstStatus() {
        return asstStatus;
    }

    public void setAsstStatus(Character asstStatus) {
        this.asstStatus = asstStatus;
    }

    public Date getCapDate() {
        return capDate;
    }

    public void setCapDate(Date capDate) {
        this.capDate = capDate;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public Date getRetDate() {
        return retDate;
    }

    public void setRetDate(Date retDate) {
        this.retDate = retDate;
    }

    public Character getgLPickMeth() {
        return gLPickMeth;
    }

    public void setgLPickMeth(Character gLPickMeth) {
        this.gLPickMeth = gLPickMeth;
    }

    public Character getNoDiscount() {
        return noDiscount;
    }

    public void setNoDiscount(Character noDiscount) {
        this.noDiscount = noDiscount;
    }

    public Character getMgrByQty() {
        return mgrByQty;
    }

    public void setMgrByQty(Character mgrByQty) {
        this.mgrByQty = mgrByQty;
    }

    public String getAssetRmk1() {
        return assetRmk1;
    }

    public void setAssetRmk1(String assetRmk1) {
        this.assetRmk1 = assetRmk1;
    }

    public String getAssetRmk2() {
        return assetRmk2;
    }

    public void setAssetRmk2(String assetRmk2) {
        this.assetRmk2 = assetRmk2;
    }

    public BigDecimal getAssetAmnt1() {
        return assetAmnt1;
    }

    public void setAssetAmnt1(BigDecimal assetAmnt1) {
        this.assetAmnt1 = assetAmnt1;
    }

    public BigDecimal getAssetAmnt2() {
        return assetAmnt2;
    }

    public void setAssetAmnt2(BigDecimal assetAmnt2) {
        this.assetAmnt2 = assetAmnt2;
    }

    public String getDeprGroup() {
        return deprGroup;
    }

    public void setDeprGroup(String deprGroup) {
        this.deprGroup = deprGroup;
    }

    public String getAssetSerNo() {
        return assetSerNo;
    }

    public void setAssetSerNo(String assetSerNo) {
        this.assetSerNo = assetSerNo;
    }

    public String getCntUnitMsr() {
        return cntUnitMsr;
    }

    public void setCntUnitMsr(String cntUnitMsr) {
        this.cntUnitMsr = cntUnitMsr;
    }

    public BigDecimal getNumInCnt() {
        return numInCnt;
    }

    public void setNumInCnt(BigDecimal numInCnt) {
        this.numInCnt = numInCnt;
    }

    public Integer getiNUoMEntry() {
        return iNUoMEntry;
    }

    public void setiNUoMEntry(Integer iNUoMEntry) {
        this.iNUoMEntry = iNUoMEntry;
    }

    public Character getOneBOneRec() {
        return oneBOneRec;
    }

    public void setOneBOneRec(Character oneBOneRec) {
        this.oneBOneRec = oneBOneRec;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getScsCode() {
        return scsCode;
    }

    public void setScsCode(String scsCode) {
        this.scsCode = scsCode;
    }

    public String getSpProdType() {
        return spProdType;
    }

    public void setSpProdType(String spProdType) {
        this.spProdType = spProdType;
    }

    public BigDecimal getiWeight1() {
        return iWeight1;
    }

    public void setiWeight1(BigDecimal iWeight1) {
        this.iWeight1 = iWeight1;
    }

    public Short getiWght1Unit() {
        return iWght1Unit;
    }

    public void setiWght1Unit(Short iWght1Unit) {
        this.iWght1Unit = iWght1Unit;
    }

    public BigDecimal getiWeight2() {
        return iWeight2;
    }

    public void setiWeight2(BigDecimal iWeight2) {
        this.iWeight2 = iWeight2;
    }

    public Short getiWght2Unit() {
        return iWght2Unit;
    }

    public void setiWght2Unit(Short iWght2Unit) {
        this.iWght2Unit = iWght2Unit;
    }

    public Character getCompoWH() {
        return compoWH;
    }

    public void setCompoWH(Character compoWH) {
        this.compoWH = compoWH;
    }

    public Character getuBAIsFA() {
        return uBAIsFA;
    }

    public void setuBAIsFA(Character uBAIsFA) {
        this.uBAIsFA = uBAIsFA;
    }

    public Integer getuBATypID() {
        return uBATypID;
    }

    public void setuBATypID(Integer uBATypID) {
        this.uBATypID = uBATypID;
    }

    public Integer getuBANumID() {
        return uBANumID;
    }

    public void setuBANumID(Integer uBANumID) {
        this.uBANumID = uBANumID;
    }

    public BigDecimal getuBALVAFrom() {
        return uBALVAFrom;
    }

    public void setuBALVAFrom(BigDecimal uBALVAFrom) {
        this.uBALVAFrom = uBALVAFrom;
    }

    public BigDecimal getuBaLva() {
        return uBaLva;
    }

    public void setuBaLva(BigDecimal uBaLva) {
        this.uBaLva = uBaLva;
    }

    public String getuUMate() {
        return uUMate;
    }

    public void setuUMate(String uUMate) {
        this.uUMate = uUMate;
    }

    public String getuGrupo() {
        return uGrupo;
    }

    public void setuGrupo(String uGrupo) {
        this.uGrupo = uGrupo;
    }

    public String getuSubGrupo() {
        return uSubGrupo;
    }

    public void setuSubGrupo(String uSubGrupo) {
        this.uSubGrupo = uSubGrupo;
    }

    public String getuUNomWeb() {
        return uUNomWeb;
    }

    public void setuUNomWeb(String uUNomWeb) {
        this.uUNomWeb = uUNomWeb;
    }

    public String getuUPicPro() {
        return uUPicPro;
    }

    public void setuUPicPro(String uUPicPro) {
        this.uUPicPro = uUPicPro;
    }

    public String getuUColor() {
        return uUColor;
    }

    public void setuUColor(String uUColor) {
        this.uUColor = uUColor;
    }

    public String getuURefPro() {
        return uURefPro;
    }

    public void setuURefPro(String uURefPro) {
        this.uURefPro = uURefPro;
    }

    public String getuURefAduana() {
        return uURefAduana;
    }

    public void setuURefAduana(String uURefAduana) {
        this.uURefAduana = uURefAduana;
    }

    public String getuUDesAduana() {
        return uUDesAduana;
    }

    public void setuUDesAduana(String uUDesAduana) {
        this.uUDesAduana = uUDesAduana;
    }

    public String getuUCodAran() {
        return uUCodAran;
    }

    public void setuUCodAran(String uUCodAran) {
        this.uUCodAran = uUCodAran;
    }

    public String getuUCarat() {
        return uUCarat;
    }

    public void setuUCarat(String uUCarat) {
        this.uUCarat = uUCarat;
    }

    public String getuUNumPart() {
        return uUNumPart;
    }

    public void setuUNumPart(String uUNumPart) {
        this.uUNumPart = uUNumPart;
    }

    public String getuUColEstru() {
        return uUColEstru;
    }

    public void setuUColEstru(String uUColEstru) {
        this.uUColEstru = uUColEstru;
    }

    public String getuUPalCla() {
        return uUPalCla;
    }

    public void setuUPalCla(String uUPalCla) {
        this.uUPalCla = uUPalCla;
    }

    public String getuUAlt() {
        return uUAlt;
    }

    public void setuUAlt(String uUAlt) {
        this.uUAlt = uUAlt;
    }

    public String getuUHabDes() {
        return uUHabDes;
    }

    public void setuUHabDes(String uUHabDes) {
        this.uUHabDes = uUHabDes;
    }

    public Date getuUActQn() {
        return uUActQn;
    }

    public void setuUActQn(Date uUActQn) {
        this.uUActQn = uUActQn;
    }

    public String getUdescripciona() {
        return udescripciona;
    }

    public void setUdescripciona(String udescripciona) {
        this.udescripciona = udescripciona;
    }

    public String getuCuidados() {
        return uCuidados;
    }

    public void setuCuidados(String uCuidados) {
        this.uCuidados = uCuidados;
    }

    public Integer getuCojineria() {
        return uCojineria;
    }

    public void setuCojineria(Integer uCojineria) {
        this.uCojineria = uCojineria;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public BigDecimal getuNwrScrp() {
        return uNwrScrp;
    }

    public void setuNwrScrp(BigDecimal uNwrScrp) {
        this.uNwrScrp = uNwrScrp;
    }

    public Short getuNwrwopo() {
        return uNwrwopo;
    }

    public void setuNwrwopo(Short uNwrwopo) {
        this.uNwrwopo = uNwrwopo;
    }

    public Short getuNwrMatrix() {
        return uNwrMatrix;
    }

    public void setuNwrMatrix(Short uNwrMatrix) {
        this.uNwrMatrix = uNwrMatrix;
    }

    public Character getuNWRAutoShipment() {
        return uNWRAutoShipment;
    }

    public void setuNWRAutoShipment(Character uNWRAutoShipment) {
        this.uNWRAutoShipment = uNWRAutoShipment;
    }

    public String getUmateriales() {
        return umateriales;
    }

    public void setUmateriales(String umateriales) {
        this.umateriales = umateriales;
    }

    public String getuNumpartes() {
        return uNumpartes;
    }

    public void setuNumpartes(String uNumpartes) {
        this.uNumpartes = uNumpartes;
    }

    public String getUfotohd() {
        return ufotohd;
    }

    public void setUfotohd(String ufotohd) {
        this.ufotohd = ufotohd;
    }

    public String getUmodelo() {
        return umodelo;
    }

    public void setUmodelo(String umodelo) {
        this.umodelo = umodelo;
    }

    public Character getUescanearbot() {
        return uescanearbot;
    }

    public void setUescanearbot(Character uescanearbot) {
        this.uescanearbot = uescanearbot;
    }

    public String getUidyoutube() {
        return uidyoutube;
    }

    public void setUidyoutube(String uidyoutube) {
        this.uidyoutube = uidyoutube;
    }

    public String getUfotosOK() {
        return ufotosOK;
    }

    public void setUfotosOK(String ufotosOK) {
        this.ufotosOK = ufotosOK;
    }

    public String getUtalla() {
        return utalla;
    }

    public void setUtalla(String utalla) {
        this.utalla = utalla;
    }

    public String getuPosicionChaise() {
        return uPosicionChaise;
    }

    public void setuPosicionChaise(String uPosicionChaise) {
        this.uPosicionChaise = uPosicionChaise;
    }

    public String getUregistrocambio() {
        return uregistrocambio;
    }

    public void setUregistrocambio(String uregistrocambio) {
        this.uregistrocambio = uregistrocambio;
    }

    public String getuDescCorta() {
        return uDescCorta;
    }

    public void setuDescCorta(String uDescCorta) {
        this.uDescCorta = uDescCorta;
    }

    public String getuDescontinuado() {
        return uDescontinuado;
    }

    public void setuDescontinuado(String uDescontinuado) {
        this.uDescontinuado = uDescontinuado;
    }

    public String getuModelado() {
        return uModelado;
    }

    public void setuModelado(String uModelado) {
        this.uModelado = uModelado;
    }

    public Date getUreprocesarfotos() {
        return ureprocesarfotos;
    }

    public void setUreprocesarfotos(Date ureprocesarfotos) {
        this.ureprocesarfotos = ureprocesarfotos;
    }

    public String getuCodigoEan() {
        return uCodigoEan;
    }

    public void setuCodigoEan(String uCodigoEan) {
        this.uCodigoEan = uCodigoEan;
    }

    public String getuFactorRedondeo() {
        return uFactorRedondeo;
    }

    public void setuFactorRedondeo(String uFactorRedondeo) {
        this.uFactorRedondeo = uFactorRedondeo;
    }

    public String getuColeccion() {
        return uColeccion;
    }

    public void setuColeccion(String uColeccion) {
        this.uColeccion = uColeccion;
    }

    public String getuCodigoMarca() {
        return uCodigoMarca;
    }

    public void setuCodigoMarca(String uCodigoMarca) {
        this.uCodigoMarca = uCodigoMarca;
    }
}
