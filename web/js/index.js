$(function () {
    $('.login').fadeToggle('slow');
    tabs();
    validate();
    $('input[type="submit"]').mousedown(function () {
        $(this).css('background', '#2ecc71');
    });
    $('input[type="submit"]').mouseup(function () {
        $(this).css('background', '#1abc9c');
    });
});

function validate() {
    $('input[name="password_r_r"]').blur(function () {

        if ($(this).val() !== $('input[name="password_r"]').val()) {
            console.log('error');
            $(".r").addClass('error');
            $(this).after('<span class="errorMessage">These passwords don\'t match. Try again.</span>');
            $(".error").find('input[type="submit"]').disable();
        }
    });
    $('input[name="password_r_r"]').focus(function () {
        $(".r").removeClass('error');
        $(".r").find('.errorMessage').remove();
        $(".r").find('.submit').enabled();
    });
}

function tabs() {
    $('#loginform').click(function () {
        $(".r").hide();
        $('.login').fadeToggle('slow');
        $(this).toggleClass('green');
    });

    $('#registerform').click(function () {
        $(".login").hide();
        $('.r').fadeToggle('slow');
        $(this).toggleClass('green');
    });
}








