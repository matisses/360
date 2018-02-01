/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function abrirModalEliminar() {
    $('#modalEliminar').modal('show');
}

function cerrarModalEliminar() {
    $('#modalEliminar').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCambiarTipoSticker() {
    $('#modalCambiarTipoSticker').modal('show');
}

function cerrarModalCambiarTipoSticker() {
    $('#modalCambiarTipoSticker').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalImpresoras() {
    $('#modalImpresoras').modal('show');
}

function cerrarModalImpresoras() {
    $('#modalImpresoras').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}