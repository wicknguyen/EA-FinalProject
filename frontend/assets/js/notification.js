$(function () {

    function parseJwt (token) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    }

    console.log("564555454545545");
    let token = localStorage.getItem('access_token');
    let userName = parseJwt(token).user_name;
    let current_user = localStorage.getItem('current_user');


    getPostNotification();

    setInterval(getPostNotification, 1000);

    function getPostNotification(){
        $.ajax({
            url: "http://localhost:9999/postInfo/"+ userName,
            type: "GET",
            dataType: "json",
            success: function (data) {
                //alert(data.length);
                $('#numberMessages').text(data.length);
                $('#numbersMessages').text(data.length);
                $('#numberMessages').css("color", "red");


                Handlebars.registerHelper('fromNow', function(date) {
                    if (moment) {
                        return moment(date).fromNow();
                    } else {
                        return date;
                    }
                });

                Handlebars.registerHelper('numComments', function(comments) {
                    return comments.length;
                });

                var mydata={posts:data};

                var template = $('#handlebar1').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(mydata);
                //$('#posts1').text("");
                $('#posts1').children().last().text("");
                $('#posts1').children().last().append(html);



            }
        });
    }

});
