/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function abrirModalReferencias() {
    $('#modalReferencias').modal('show');
}

function cerrarModalReferencias() {
    $('#modalReferencias').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalImpresion() {
    $('#modalSticker').modal('show');
}

function cerrarModalImpresion() {
    $('#modalSticker').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalReferencia() {
    $('#modalReferencia').modal('show');
}

function cerrarModalReferencia() {
    $('#modalReferencia').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalImprimir() {
    $('#modalImprimir').modal('show');
}

function cerrarModalImprimir() {
    $('#modalImprimir').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}