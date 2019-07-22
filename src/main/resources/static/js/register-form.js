$(document).ready(function () {
    var password = $('#pass');
    var passwordConfirm = $('#pass-confirm');
    var btnSubmit = $('#btn-submit');

    btnSubmit.on('click', function (event) {
        if(password.val() !== passwordConfirm.val()) {
            event.preventDefault();
            $('.error-password').removeClass('hidden-error');
            password.val('');
            passwordConfirm.val('');
        };
    })
});