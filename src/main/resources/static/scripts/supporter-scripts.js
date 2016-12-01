/**
 * Created by Ivaylo on 01-Dec-16.
 */
$(function() {
    $('#notification-message').click(function() {
        $(this).fadeOut();
    });
    setTimeout(function() {
        $('#notification-message .info').fadeOut();
    }, 3000);
});
