function openRuta(window_src) {
    ruta = window.open(window_src, '_blank');
}

function closeWindow() {
    window.close();
}

function abrirModalDetalle() {
    $('#modalDetalle').modal('show');
}

function cerrarModalDetalle() {
    $('#modalDetalle').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalVentas() {
    $('#modalVentas').modal('show');
}

function cerrarModalVentas() {
    $('#modalVentas').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalMovimientos() {
    $('#modalMovimientos').modal('show');
}

function cerrarModalMovimientos() {
    $('#modalMovimientos').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function abrirModalImagen() {
    $('#modalImagen').modal('show');
}

function cerrarModalImagen() {
    $('#modalImagen').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

var time;

$(document).ready(function () {
    this.time = setTimeout('location="/360"', 300000);
    $(document).mousemove(function (event) {
        clearTimeout(this.time);
        this.time = setTimeout('location="/360"', 300000);
    });
    $(document).keypress(function (event) {
        clearTimeout(this.time);
        this.time = setTimeout('location="/360"', 300000);
    });
});