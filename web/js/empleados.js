/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function abrirModalAusentismos() {
    $('#modalAusentismos').modal('show');
}

function cerrarModalAusentismos() {
    $('modalAusentismos').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalFormacion() {
    $('#modalFormacion').modal('show');
}

function cerrarModalFormacion() {
    $('modalFormacion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalValoracion() {
    $('#modalValoracion').modal('show');
}

function cerrarModalValoracion() {
    $('modalValoracion').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalEmpleoAnterior() {
    $('#modalEmpleoAnterior').modal('show');
}

function cerrarModalEmpleoAnterior() {
    $('modalEmpleoAnterior').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalRecibirCustodia() {
    $('#modalRecibirCustodia').modal('show');
}

function cerrarModalRecibirCustodia() {
    $('modalRecibirCustodia').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalCustodia() {
    $('#modalCustodia').modal('show');
}

function cerrarModalCustodia() {
    $('modalCustodia').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalEliminarCustodia() {
    $('#modalEliminarCustodia').modal('show');
}

function cerrarModalEliminarCustodia() {
    $('modalEliminarCustodia').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalBusqueda() {
    $('#modalBusqueda').modal('show');
}

function cerrarModalBusqueda() {
    $('modalBusqueda').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function iniciarSlider() {
    setTimeout(function () {
        $(".slider-logos").slick({
            prevArrow: '.slider-logos-container .prev',
            nextArrow: '.slider-logos-container .next',
            slidesToShow: 9,
            infinite: false,
            slidesToScroll: 3
        });
    }, 300);
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip();
});