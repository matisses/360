/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function abrirModalBusqueda() {
    $('#modalBusqueda').modal('show');
}

function cerrarModalBusqueda() {
    $('#modalBusqueda').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalTransaccion() {
    $('#modalTransaccion').modal('show');
}

function abrirModalAnticipoCuenta() {
    $('#modalAnticipoCuenta').modal('show');
}

function cerrarModalAnticipoCuenta() {
    $('#modalAnticipoCuenta').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalInforme() {
    $('#modalInforme').modal('show');
}

function cerrarModalInforme() {
    $('#modalInforme').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCrearCuenta() {
    $('#modalCrearCuenta').modal('show');
}

function openPdf(window_src) {
    pdf = window.open(window_src, 'newwindow', config = 'height=' + window.screen.availHeight + ', width=' + window.screen.availWidth
            + ', toolbar=no, menubar=no, resizable=no, location=no, status=no, scrollbars=yes');
}