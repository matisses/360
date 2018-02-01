/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function abrirModalSaldos() {
    $('#modalSaldos').modal('show');
}

function cerrarModalSaldos() {
    $('modalSaldos').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function focusReferencia() {
    document.getElementById('formVentas:txt_referencia').focus();
}

function abrirModalCorreo() {
    $('#modalCorreo').modal('show');
}

function cerrarModalCorreo() {
    $('modalCorreo').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalFacturacion() {
    $('#modalFacturacion').modal('show');
}

function cerrarModalFacturacion() {
    $('modalFacturacion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalEliminar() {
    $('#modalEliminar').modal('show');
}

function cerrarModalEliminar() {
    $('modalEliminar').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCrearFactura() {
    $('#modalCrearFactura').modal('show');
}

function cerrarModalCrearFactura() {
    $('modalCrearFactura').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalConfirmacion() {
    $('#modalConfirmacion').modal('show');
}

function cerrarModalConfirmacion() {
    $('modalConfirmacion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalDemo() {
    $('#modalDemo').modal('show');
}

function cerrarModalDemo() {
    $('modalDemo').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalExito() {
    $('#modalExito').modal('show');
}

function cerrarModalExito() {
    $('modalExito').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCancelarDemo() {
    $('#modalCancelarDemo').modal('show');
}

function cerrarModalCancelarDemo() {
    $('modalCancelarDemo').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalFacturarDemo() {
    $('#modalFacturarDemo').modal('show');
}

function cerrarModalFacturarDemo() {
    $('modalFacturarDemo').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalNotaCredito() {
    $('#modalNotaCredito').modal('show');
}

function cerrarModalNotaCredito() {
    $('modalNotaCredito').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalConfirmacion() {
    $('#modalConfirmacion').modal('show');
}

function cerrarModalConfirmacion() {
    $('modalConfirmacion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalAsignacion() {
    $('#modalAsignacion').modal('show');
}

function cerrarModalAsignacion() {
    $('modalAsignacion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCrearNota() {
    $('#modalCrearNota').modal('show');
}

function cerrarModalCrearNota() {
    $('modalCrearNota').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalDocumentoRelacionado() {
    $('#modalDocumentoRelacionado').modal('show');
}

function cerrarModalDocumentoRelacionado() {
    $('modalDocumentoRelacionado').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function openRuta(window_src) {
    ruta = window.open(window_src, 'newwindow', config = 'height=' + window.screen.availHeight + ',width=' + window.screen.availWidth
            + ',toolbar=no,menubar=no,resizable=no,location=no,status=no,scrollbars=yes');
}