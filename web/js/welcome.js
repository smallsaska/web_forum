$(function () {
    $('input[name="create"]').mouseup(function () {
        $(this).css('background', '#1abc9c');
    });
    $('input[name="create"]').click(function () {
        $(this).createNewTheme();
    });
});

function createNewTheme() {

}
