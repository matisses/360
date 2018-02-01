/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openRuta(window_src) {
    ruta = window.open(window_src, 'newwindow', config = 'height=' + window.screen.availHeight + ', width=' + window.screen.availWidth
            + ', toolbar=no, menubar=no, resizable=no, location=no, status=no, scrollbars=yes');
}

function abrirModalImg() {
    $('#modalImg').modal('show');
}

function abrirModalAsignarImagenes() {
    $('#modalAsignarImagenes').modal('show');
}

function cerrarModalAsignarImagenes() {
    $('#modalAsignarImagenes').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCrearItem() {
    $('#modalCrearItem').modal('show');
}

function cerrarModalCrearItem() {
    $('#modalCrearItem').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCrearNuevoItem() {
    $('#modalCrearNuevoItem').modal('show');
}

function cerrarModalCrearNuevoItem() {
    $('#modalCrearNuevoItem').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalEliminarItem() {
    $('#modalEliminarItem').modal('show');
}

function cerrarModalEliminarItem() {
    $('#modalEliminarItem').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalAgregarColumna() {
    $('#modalAgregarColumna').modal('show');
}

function cerrarModalAgregarColumna() {
    $('#modalAgregarColumna').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalContenedores() {
    $('#modalContenedores').modal('show');
}

function cerrarModalContenedores() {
    $('#modalContenedores').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCrearCuenta() {
    $('#modalCrearCuenta').modal('show');
}

function cerrarModalCrearCuenta() {
    $('#modalCrearCuenta').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalMoneda() {
    $('#modalMoneda').modal('show');
}

function cerrarModalMoneda() {
    $('#modalMoneda').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalTransaccion() {
    $('#modalTransaccion').modal('show');
}

function cerrarModalTransaccion() {
    $('#modalTransaccion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCancelarTransaccion() {
    $('#modalCancelarTransaccion').modal('show');
}

function cerrarModalCancelarTransaccion() {
    $('#modalCancelarTransaccion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalDescargar() {
    $('#modalDescargar').modal('show');
}

function cerrarModalDescargar() {
    $('#modalDescargar').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCargaSuelta() {
    $('#modalCargaSuelta').modal('show');
}

function abrirModalCantidad() {
    $('#modalCantidad').modal('show');
}

function abrirModalCierre() {
    $('#modalCierre').modal('show');
}

function cerrarModalCierre() {
    $('#modalCierre').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}