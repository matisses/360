package co.matisses.web.rest;

import co.matisses.b1ws.client.creditnotes.CreditNotesServiceConnector;
import co.matisses.b1ws.client.goodsissue.GoodsIssueDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueDetailDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueLocationsDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueServiceConnector;
import co.matisses.b1ws.client.goodsissue.GoodsIssueServiceException;
import co.matisses.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.b1ws.client.orders.OrdersServiceConnector;
import co.matisses.b1ws.client.payments.IncomingPaymentServiceConnector;
import co.matisses.b1ws.dto.ConstantTypes;
import co.matisses.b1ws.dto.CreditCardPaymentDTO;
import co.matisses.b1ws.dto.JournalEntryDTO;
import co.matisses.b1ws.dto.JournalEntryLineDTO;
import co.matisses.b1ws.dto.PaymentDTO;
import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.persistence.sap.entity.DocumentosExcluidos;
import co.matisses.persistence.sap.facade.DocumentosExcluidosFacade;
import co.matisses.web.dto.InformacionAlmacenDTO;
import co.matisses.web.dto.NotaCreditoResponseDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.VentaPOSDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("notacredito")
public class NotaCreditoFacadeREST {

    private static final Logger log = Logger.getLogger(NotaCreditoFacadeREST.class.getSimpleName());
    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @EJB
    private DocumentosExcluidosFacade documentosExcluidosFacade;

    private boolean validarFactura(String nroFactura) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(1) regs ");
        sb.append("from   ORIN ");
        sb.append("where  U_TipoNota = 'A' ");
        sb.append("and    NumAtCard = ");
        sb.append(nroFactura);
        try {
            Integer registros = (Integer) em.createNativeQuery(sb.toString()).getSingleResult();
            if (registros == 0) {
                return true;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo consultar si la factura ya ha sido anulada. ", e);
        }
        log.log(Level.WARNING, "No se puede anular la factura {0} porque ya ha sido anulada", nroFactura);
        return false;
    }

    private SalesDocumentDTO consultarDatosFactura(String nroFactura) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(enc.DocDate as date) DocDate, cast(enc.DocEntry as int) docEntry, enc.Series, cast(enc.CardCode as varchar(15)) cardCode, ");
        sb.append("cast(enc.Comments as varchar(254)) comments, enc.GroupNum, cast(enc.U_Vendedor1 as varchar(60)) vendedor1, ");
        sb.append("cast(enc.U_Vendedor2 as varchar(60)) vendedor2, cast(enc.U_Vendedor3 as varchar(60)) vendedor3, cast(enc.U_Vendedor4 as varchar(60)) vendedor4, ");
        sb.append("cast(enc.U_Vendedor5 as varchar(60)) vendedor5, cast(enc.U_ComVend1 as numeric(3,2)) comVend1, cast(enc.U_ComVend2 as numeric(3,2)) comVend2, ");
        sb.append("cast(enc.U_ComVend3 as numeric(3,2)) comVend3, cast(enc.U_ComVend4 as numeric(3,2)) comVend4, cast(enc.U_ComVend5 as numeric(3,2)) comVend5, ");
        sb.append("cast(enc.U_WUID as varchar(100)) wuid, enc.POSCashN, cast(enc.U_Origen as varchar(1)) origen, cast(det.LineNum as int) lineNum, cast(det.ItemCode as varchar(20)) itemCode, ");
        sb.append("det.Quantity, cast(det.WhsCode as varchar(6)) whsCode, cast(det.OcrCode2 as varchar(8)) ocrCode2, cast(det.OcrCode3 as varchar(8)) ocrCode3, ");
        sb.append("cast(det.OcrCode4 as varchar(8)) ocrCode4, cast(det.Project as varchar(20)) project, cast(det.U_EstadoP as varchar(1)) estado, bin.AbsEntry binAbs, ");
        sb.append("cast(bin.BinCode as varchar(228)) binCode, cast(binLog.Quantity as int) quantity, ");
        sb.append("(select top 1 Series from NNM1 where SeriesName = 'NC' and Locked = 'N') serie, ");
        sb.append("cast(det.price as int) price, cast(det.DiscPrcnt as numeric(5,2)) disPerc ");
        sb.append("from   OINV enc ");
        sb.append("inner join INV1 det on det.docentry = enc.docentry ");
        sb.append("inner join OILM msg on msg.DocEntry = enc.DocEntry and msg.TransType = 13 and msg.DocLineNum = det.LineNum ");
        sb.append("inner join OBTL binLog on binLog.MessageID = msg.MessageID ");
        sb.append("inner join OBIN bin on bin.AbsEntry = binLog.BinAbs ");
        sb.append("where enc.DocNum = ");
        sb.append(nroFactura);

        try {
            SalesDocumentDTO dto = new SalesDocumentDTO();
            List<Object[]> rows = em.createNativeQuery(sb.toString()).getResultList();
            if (rows != null && !rows.isEmpty()) {
                if (!DateUtils.isSameDay((Date) rows.get(0)[0], new Date())) {
                    log.log(Level.SEVERE, "No se puede realizar la anulacion de una factura de fechas pasadas. ");
                    return null;
                }
                dto.setDocEntry(((Integer) rows.get(0)[1]).longValue());
                dto.setRefDocnum(nroFactura);
                dto.setCardCode((String) rows.get(0)[3]);
                dto.setComments("Anulacion generada por 360 para la factura " + nroFactura);
                dto.setPaymentGroupCode(((Short) rows.get(0)[5]).toString());
                dto.setSource("P");
                dto.setSeriesCode(((Short) rows.get(0)[31]).toString());
                dto.setWuid((String) rows.get(0)[16]);
                dto.setPosShiftId(((Integer) rows.get(0)[17]).longValue());
                dto.setCreditNoteType("A");

                //Agrega los vendedores a la devolucion
                if (rows.get(0)[6] != null) {
                    SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();
                    salesEmp.setName((String) rows.get(0)[6]);
                    dto.addSalesEmployee(salesEmp);
                    if (rows.get(0)[7] != null) {
                        SalesEmployeeDTO salesEmp2 = new SalesEmployeeDTO();
                        salesEmp2.setName((String) rows.get(0)[7]);
                        dto.addSalesEmployee(salesEmp2);
                        if (rows.get(0)[8] != null) {
                            SalesEmployeeDTO salesEmp3 = new SalesEmployeeDTO();
                            salesEmp3.setName((String) rows.get(0)[8]);
                            dto.addSalesEmployee(salesEmp3);
                            if (rows.get(0)[9] != null) {
                                SalesEmployeeDTO salesEmp4 = new SalesEmployeeDTO();
                                salesEmp4.setName((String) rows.get(0)[9]);
                                dto.addSalesEmployee(salesEmp4);
                                if (rows.get(0)[10] != null) {
                                    SalesEmployeeDTO salesEmp5 = new SalesEmployeeDTO();
                                    salesEmp5.setName((String) rows.get(0)[10]);
                                    dto.addSalesEmployee(salesEmp5);
                                }
                            }
                        }
                    }
                }

                List<SalesDocumentLineDTO> detDtos = new ArrayList<>();
                for (Object[] cols : rows) {
                    dto.setShippingStatus((String) cols[27]);
                    dto.setSalesCostingCode((String) cols[23]);
                    dto.setLogisticsCostingCode((String) cols[24]);
                    dto.setRouteCostingCode((String) cols[25]);

                    SalesDocumentLineBinAllocationDTO binAllocDto = new SalesDocumentLineBinAllocationDTO();
                    binAllocDto.setBinAbsEntry((Integer) cols[28]);
                    binAllocDto.setQuantity((Integer) cols[30]);
                    binAllocDto.setWhsCode((String) cols[22]);

                    //SalesDocumentLineDTO lineDto = new SalesDocumentLineDTO((String) cols[20], binAllocDto.getWhsCode(), ((BigDecimal) cols[21]).intValue(), (Integer) cols[19], (Integer) cols[32]);
                    SalesDocumentLineDTO lineDto = new SalesDocumentLineDTO((String) cols[20], binAllocDto.getWhsCode(), ((Integer) cols[32]).doubleValue(), ((BigDecimal) cols[33]).doubleValue());
                    int pos = detDtos.indexOf(lineDto);
                    if (pos >= 0) {
                        lineDto = detDtos.get(pos);
                        lineDto.addBinAllocation(binAllocDto);
                        lineDto.setQuantity(lineDto.getQuantity() + ((BigDecimal) cols[21]).intValue());
                        detDtos.set(pos, lineDto);
                    } else {
                        lineDto.setQuantity(((BigDecimal) cols[21]).intValue());
                        lineDto.addBinAllocation(binAllocDto);
                        lineDto.setLineNum((Integer) cols[19]);
                        detDtos.add(lineDto);
                    }
                }
                dto.setDocumentLines(detDtos);
            }

            return dto;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar la factura. ", e);
        }

        return null;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<Object[]> consultarDatosAsientoConsignacion(Long docEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ef.DocDate RefDate, ef.DocDueDate DueDate, ef.TaxDate, cast(e.Memo+'-AJCS' as varchar(100)) Memo, cast(e.Ref1 as varchar(10)) Ref1, ");
        sb.append("cast(e.TransId as varchar(10)) Ref2, cast(e.Ref3 as varchar(10)) Ref3, 'AJCS' TransCode, ");
        sb.append("ROW_NUMBER() OVER(ORDER BY da.StockPrice, d.ShortName ASC) -1 Line_ID, ");
        sb.append("cast(case when d.ShortName=da.BalInvntAc then da.U_Cuenta_DB_Ajuste when d.ShortName=da.SaleCostAc then da.U_Cuenta_CR_Ajuste ");
        sb.append("end as varchar(20)) ShortName, cast(d.LineMemo+'-AJCS' as varchar(100)) LineMemo, cast(d.OcrCode2 as varchar(10)) OcrCode2, ");
        sb.append("cast(d.Project as varchar(10)) Project, cast(case when d.ShortName=da.BalInvntAc then ef.CardCode when d.ShortName=da.SaleCostAc ");
        sb.append("then da.Proveedor end as varchar(20)) U_InfoCo01, case when d.ShortName=da.BalInvntAc then da.StockPrice else 0 end Credit, ");
        sb.append("case when d.ShortName=da.SaleCostAc then da.StockPrice else 0 end Debit, da.DocEntry, cast(da.GLMethod as varchar(10)) GLMethod ");
        sb.append("from OJDT e inner join JDT1 d on d.TransId=e.TransId inner join (select df.DocEntry, k.GLMethod ");
        sb.append(", case k.GLMethod when 'W' then a.BalInvntAc when 'C' then g.BalInvntAc when 'L' then i.BalInvntAc else '' end BalInvntAc ");
        sb.append(", case k.GLMethod when 'W' then a.SaleCostAc when 'C' then g.SaleCostAc when 'L' then i.SaleCostAc else '' end SaleCostAc ");
        sb.append(", case k.GLMethod when 'W' then csW.U_Cuenta_DB_Ajuste when 'C' then csC.U_Cuenta_DB_Ajuste when 'L' then csL.U_Cuenta_DB_Ajuste else '' end U_Cuenta_DB_Ajuste ");
        sb.append(", case k.GLMethod when 'W' then csW.U_Cuenta_CR_Ajuste when 'C' then csC.U_Cuenta_CR_Ajuste when 'L' then csL.U_Cuenta_CR_Ajuste else '' end U_Cuenta_CR_Ajuste ");
        sb.append(", k.CardCode Proveedor, convert(int,sum(df.StockPrice*df.Quantity)) StockPrice from RIN1 df ");
        sb.append("inner join OITM k on k.ItemCode=df.ItemCode left join OWHS a on a.U_Consignacion='S' AND a.WhsCode=df.WhsCode ");
        sb.append("left join OITB g on g.U_Consignacion='S' AND g.ItmsGrpCod=k.ItmsGrpCod ");
        sb.append("left join OITW i on k.U_Consignacion='S' AND i.ItemCode=df.ItemCode AND i.WhsCode=df.WhsCode ");
        sb.append("left join [@BARU_CONSIG_MCIA] csW on k.GLMethod='W' AND csW.U_GLMethod=k.GLMethod AND LEFT(a.WhsCode,len(csW.U_WhsCode))=csW.U_WhsCode ");
        sb.append("left join [@BARU_CONSIG_MCIA] csC on k.GLMethod='C' AND csC.U_GLMethod=k.GLMethod AND csC.U_ItmsGrpCod=g.ItmsGrpCod ");
        sb.append("left join [@BARU_CONSIG_MCIA] csL on k.GLMethod='L' AND csL.U_GLMethod=k.GLMethod AND csL.U_ItemCode=i.ItemCode ");
        sb.append("where (a.whscode is not null or g.ItmsGrpCod is not null or i.ItemCode is not null) group by df.DocEntry, k.GLMethod ");
        sb.append(", case k.GLMethod when 'W' then a.BalInvntAc when 'C' then g.BalInvntAc when 'L' then i.BalInvntAc else '' end ");
        sb.append(", case k.GLMethod when 'W' then a.SaleCostAc when 'C' then g.SaleCostAc when 'L' then i.SaleCostAc else '' end ");
        sb.append(", case k.GLMethod when 'W' then csW.U_Cuenta_DB_Ajuste when 'C' then csC.U_Cuenta_DB_Ajuste when 'L' then csL.U_Cuenta_DB_Ajuste else '' end ");
        sb.append(", case k.GLMethod when 'W' then csW.U_Cuenta_CR_Ajuste when 'C' then csC.U_Cuenta_CR_Ajuste when 'L' then csL.U_Cuenta_CR_Ajuste else '' end ");
        sb.append(", k.CardCode) da on da.docentry=e.CreatedBy and (da.BalInvntAc=d.ShortName or da.SaleCostAc=d.ShortName) ");
        sb.append("inner join ORIN ef on ef.DocEntry=da.DocEntry left join (select TransId, Ref1, Ref2 from OJDT  where TransType=30) valida on valida.Ref1=e.Ref1 ");
        sb.append("where e.TransType='14' and e.CreatedBy =");
        sb.append(docEntry);
        sb.append("and isnull(valida.Ref2,'')<>convert(varchar,e.TransId) order by da.StockPrice");

        try {
            return em.createNativeQuery(sb.toString()).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar los datos de asiento para mercancia en consignacion. ", e);
            return new ArrayList<>();
        }
    }

    @POST
    @Path("anular/{usuario}/")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Response voidInvoice(@PathParam("usuario") String usuario, final VentaPOSDTO factura) {
        SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(usuario);
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new NotaCreditoResponseDTO("-1", "No fue posible iniciar una sesion en SAP B1WS. Contacte al departamento de sistemas para recibir ayuda.")).build();
        }

        if (!validarFactura(factura.getNumeroFactura())) {
            return Response.ok(new NotaCreditoResponseDTO("-1", "No se puede anular una factura que ya ha sido anulada.")).build();
        }

        //Consultar la informacion de la factura y llenar el documento completo
        try {
            SalesDocumentDTO enc = consultarDatosFactura(factura.getNumeroFactura());
            if (enc == null) {
                return Response.ok(new NotaCreditoResponseDTO("-1", "No se pudo consultar la información de la factura. ")).build();
            }
            //cancela el recibo de caja
            cancelIncomingPayment(factura.getNumeroFactura(), sesionSAPDTO);
            //crea la nota credito por anulacion
            CreditNotesServiceConnector sc = sapB1WSBean.getCreditNotesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            Long docEntry = sc.createCreditNote(enc);
            if (docEntry == null) {
                log.log(Level.SEVERE, "No se pudo registrar la nota credito en SAP.");
                return Response.ok(new NotaCreditoResponseDTO("-1", "No se pudo registrar la nota credito en SAP. ")).build();
            }
            //crea el asiento para mercancia en consignacion
            createJournalEntry(sesionSAPDTO, docEntry);
            if (needsGoodsIssue(enc)) {
                //crea la salida de mercancia de cliente
                createGoodsIssue(sesionSAPDTO, enc, factura.getNumeroFactura(), docEntry);
                //cancela la orden de venta, si aplica
                cancelSalesOrder(factura.getNumeroFactura(), sesionSAPDTO);
            }
            //registra la factura y la nota credito en la tabla de documentos excluidos para que no se visualicen en el informe de ventas
            try {
                DocumentosExcluidos fac = new DocumentosExcluidos();
                DocumentosExcluidos dev = new DocumentosExcluidos();

                long id = System.currentTimeMillis();

                fac.setCode(Long.toString(id));
                fac.setName(Long.toString(id));
                fac.setDocNum(factura.getNumeroFactura());
                fac.setFechaExclusion(new Date());
                fac.setUsuarioExcluye(usuario);
                fac.setTipoDocumento("Factura");

                dev.setCode(Long.toString(id + 1));
                dev.setName(Long.toString(id + 1));
                dev.setDocNum(factura.getNumeroFactura());
                dev.setFechaExclusion(new Date());
                dev.setUsuarioExcluye(usuario);
                dev.setTipoDocumento("Devolucion");

                documentosExcluidosFacade.create(fac);
                documentosExcluidosFacade.create(dev);
            } catch (Exception e) {
                log.log(Level.WARNING, "No fue posible excluir la factura y la nota credito del informe de ventas. ", e);
            }

            NotaCreditoResponseDTO res = new NotaCreditoResponseDTO();
            res.setCodigo("0");

            Object[] datos = getVoidedInvoiceRelatedDocuments(factura.getNumeroFactura());
            if (datos != null) {
                res.setNroNotaCredito((String) datos[0]);
                res.setNroOrdenVenta((String) datos[1]);
                res.setNroSalidaMcia((String) datos[2]);
                res.setNroReciboCaja(null);
                res.setMensaje("Nota credito creada con exito. ");
            } else {
                res.setMensaje("La nota credito se creo con exito pero no se pudieron consultar los numeros de documentos relacionados.");
            }

            return Response.ok(res).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el documento en SAP. ", e);
            return Response.ok(new NotaCreditoResponseDTO("-1", "No se pudo registrar la nota credito en SAP. " + e.getMessage())).build();
        }
    }

    private Object[] getVoidedInvoiceRelatedDocuments(String invoiceNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(dev.DocNum as varchar(20)) notaCredito, cast(ord.DocNum as varchar(20)) orden, cast(sal.DocNum as varchar(20)) salida ");
        sb.append("from OINV fac ");
        sb.append("inner join ORIN dev on dev.NumAtCard = fac.DocNum ");
        sb.append("left join ORDR ord on ord.NumAtCard = fac.DocNum ");
        sb.append("left join OIGE sal on sal.Ref2 = cast(fac.DocNum as varchar(20)) ");
        sb.append("where fac.DocNum = '");
        sb.append(invoiceNumber);
        sb.append("'");
        try {
            return (Object[]) em.createNativeQuery(sb.toString()).getSingleResult();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudieron consultar los documentos relacionados con la anulacion de la factura " + invoiceNumber, e);
            return null;
        }
    }

    private boolean needsGoodsIssue(SalesDocumentDTO orderDto) {
        if (orderDto == null || orderDto.getShippingStatus() == null || orderDto.getShippingStatus().trim().isEmpty() || !orderDto.getShippingStatus().equals("P")) {
            return false;
        }
        return true;
    }

    private void createJournalEntry(SesionSAPB1WSDTO sesionSAPDTO, Long docEntry) {
        List<Object[]> asiento = consultarDatosAsientoConsignacion(docEntry);
        if (!asiento.isEmpty()) {
            JournalEntryDTO journalEntryHeader = null;
            for (Object[] row : asiento) {
                if (journalEntryHeader == null) {
                    journalEntryHeader = new JournalEntryDTO();
                    journalEntryHeader.setRefDate((Date) row[0]);
                    journalEntryHeader.setDueDate((Date) row[1]);
                    journalEntryHeader.setTaxDate((Date) row[2]);

                    journalEntryHeader.setMemo((String) row[3]);
                    journalEntryHeader.setRef1((String) row[4]);
                    journalEntryHeader.setRef2((String) row[5]);
                    journalEntryHeader.setRef3((String) row[6]);
                    journalEntryHeader.setTransactionCode((String) row[7]);
                }

                JournalEntryLineDTO line = new JournalEntryLineDTO();
                line.setRef1((String) row[4]);
                line.setRef2((String) row[5]);
                line.setLineId(((BigInteger) row[8]).longValue());
                line.setShortName((String) row[9]);
                line.setLineMemo((String) row[10]);
                line.setOcrCode2((String) row[11]);
                line.setProject((String) row[12]);
                line.setInfoCo01((String) row[13]);
                line.setCredit(((Integer) row[14]).doubleValue());
                line.setDebit(((Integer) row[15]).doubleValue());

                journalEntryHeader.addLine(line);
            }
            try {
                JournalEntriesServiceConnector jesc = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
                Long docEntryAsiento = jesc.createJournalEntry(journalEntryHeader);
                log.log(Level.INFO, "Se creo el asiento contable con ID {0}", docEntryAsiento);
            } catch (Exception e) {
                //TODO: notificar error por correo
                log.log(Level.SEVERE, "No se pudo crear el asiento contable. ", e);
            }
        }
    }

    private void createGoodsIssue(SesionSAPB1WSDTO sesionSAPDTO, SalesDocumentDTO enc, String nroFactura, Long docEntryNC) throws GoodsIssueServiceException {
        GoodsIssueDTO document = new GoodsIssueDTO();
        document.setComments("Doc. creado con B1WS Segun Nota Credito #" + docEntryNC + " (DocEntry) para Factura #" + nroFactura);
        document.setJournalMemo("Salida de mercancia de clientes");
        document.setSeries("26"); //TODO: parametrizar numero de serie de salia de mercancias por inventario
        document.setGroupNum(String.valueOf(-1L)); //Ultimo precio de compra
        document.setInvoiceNumber(nroFactura);

        for (SalesDocumentLineDTO lineDto : enc.getDocumentLines()) {
            InformacionAlmacenDTO infoAlmacenVenta = baruAplicationBean.getInfoAlmacen(lineDto.getWhsCode());
            InformacionAlmacenDTO infoAlmacenCliente = baruAplicationBean.getInfoAlmacen(infoAlmacenVenta.getAlmacenClientes());

            GoodsIssueDetailDTO det = new GoodsIssueDetailDTO();
            for (SalesDocumentLineBinAllocationDTO binDto : lineDto.getBinAllocations()) {
                GoodsIssueLocationsDTO loc = new GoodsIssueLocationsDTO();
                loc.setBinAbs(infoAlmacenCliente.getIdUbicacionTM().toString());
                loc.setQuantity(binDto.getQuantity().toString());
                det.addLocation(loc);
            }
            det.setAccountCode("91051001"); //TODO: parametrizar nro de cuenta de mercancia de clientes
            det.setItemCode(lineDto.getItemCode());
            det.setLineNum(lineDto.getLineNum().toString());
            det.setQuantity(lineDto.getQuantity().toString());
            det.setWhsCode(infoAlmacenVenta.getAlmacenClientes());
            document.addDetail(det);
        }

        GoodsIssueServiceConnector gisc = sapB1WSBean.getGoodsIssueServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        Long docEntry = gisc.createDocument(document);
        log.log(Level.INFO, "Se creo la salida de mercancia de clientes con docEntry={0}", docEntry);
    }

    private void cancelSalesOrder(String nroFactura, SesionSAPB1WSDTO sesionSAPDTO) {
        OrdersServiceConnector osc = sapB1WSBean.getOrderServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        StringBuilder sb = new StringBuilder();
        sb.append("select DocEntry ");
        sb.append("from ORDR ");
        sb.append("where NumAtCard = '");
        sb.append(nroFactura);
        sb.append("' and DocStatus = 'O' ");

        try {
            Long docEntryOrden = ((Integer) em.createNativeQuery(sb.toString()).getSingleResult()).longValue();
            osc.cancelOrder(docEntryOrden);
            log.log(Level.INFO, "Se cancelo con exito la orden de venta {0}", docEntryOrden);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar la orden de venta. ", e);
        }
    }

    private boolean cancelIncomingPayment(String nroFactura, SesionSAPB1WSDTO sesionSAPDTO) throws Exception {
        Long docEntryRC = consultarDocEntryReciboCaja(nroFactura);
        if (docEntryRC == null) {
            return false;
        }
        IncomingPaymentServiceConnector ipsc = sapB1WSBean.getIncomingPaymentServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        ipsc.cancelPayment(docEntryRC);
        return true;
    }

    public Long consultarDocEntryReciboCaja(String nroFactura) {
        StringBuilder sb = new StringBuilder();
        sb.append("select enc.DocEntry from ORCT enc ");
        sb.append("inner join RCT2 fac on fac.DocNum = enc.DocNum ");
        sb.append("inner join OINV inv on inv.DocEntry = fac.DocEntry ");
        sb.append("where inv.DocNum = '");
        sb.append(nroFactura);
        sb.append("'");
        try {
            return ((Integer) em.createNativeQuery(sb.toString()).getSingleResult()).longValue();
        } catch (NoResultException e) {
            log.log(Level.WARNING, "No existe un recibo de caja para la factura {0}", nroFactura);
            return null;
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo consultar el recibo de caja para la factura " + nroFactura, e);
            return null;
        }
    }

    public PaymentDTO consultarReciboCaja(String nroFactura) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(enc.CashAcct as varchar(8)) cashAcct, cast(enc.CashSum as int) cashSum, ");
        sb.append("cast(det.CreditSum as int) creditSum, cast(det.CreditAcct as varchar(8)) creditCardAcct, ");
        sb.append("cast(det.CardValid as date) cardValid, cast(det.VoucherNum as int) voucherNum, ");
        sb.append("cast(det.CrTypeCode as int) crTypeCode, cast(det.NumOfPmnts as int) numOfPayments, ");
        sb.append("cast(enc.CardCode as varchar(15)) cardCode, cast(enc.NoDocSum as int) noDocSum, ");
        sb.append("cast(enc.DocTotal as int) docTotal, cast(det.CreditCard as int) creditCard, ");
        sb.append("cast(dev.DocEntry as int) devDocEntry ");
        sb.append("from   OINV inv inner join RCT2 det2 on det2.DocEntry = inv.DocEntry ");
        sb.append("inner join ORIN dev on dev.numatcard = inv.docnum ");
        sb.append("left join ORCT enc on enc.DocNum = det2.DocNum ");
        sb.append("left join RCT3 det on det.DocNum = enc.DocNum ");
        sb.append("where  inv.DocNum = '");
        sb.append(nroFactura);
        sb.append("'");
        PaymentDTO payment = null;
        try {
            List<Object[]> rows = em.createNativeQuery(sb.toString()).getResultList();
            payment = new PaymentDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] row : rows) {
                if (payment.getCardCode() == null) {
                    payment.setDocType(ConstantTypes.DocType.CREDIT_NOTE);
                    payment.setBusinessPartnerType(ConstantTypes.BPType.CUSTOMER);
                    //TODO: configurar numero de serie en base de datos
                    payment.setSeriesCode("24"); //objtype = 46
                    payment.setCardCode((String) row[8]);
                    payment.setCashAccount((String) row[0]);
                    payment.setInvoiceDocEntry(((Integer) row[12]).toString());
                    //payment.setCreditType(((Integer) row[6]).toString());
                    //TODO: configurar tipo de credito en base de datos
                    payment.setCreditType("S");
                    payment.setPaidBalance(((Integer) row[9]).toString());
                    payment.setPaidCash(((Integer) row[1]).toString());
                    payment.setPaidTotal(((Integer) row[10]).toString());
                }
                if (row[11] != null) {
                    CreditCardPaymentDTO ccPayment = new CreditCardPaymentDTO();
                    ccPayment.setCreditCardCode(((Integer) row[11]).toString());
                    ccPayment.setNumberOfPayments(((Integer) row[7]).toString());
                    ccPayment.setPaidSum(((Integer) row[2]).toString());
                    ccPayment.setValidUntil(sdf.format((Date) row[4]));
                    ccPayment.setVoucherNumber(((Integer) row[5]).toString());
                    payment.addCreditCardPayment(ccPayment);
                }
            }
            log.log(Level.INFO, "se encontro un pago. {0}", payment.toString());
            return payment;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el recibo de caja para la factura " + nroFactura, e);
            throw new Exception(e.getMessage());
        }

    }
}
