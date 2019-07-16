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
    let current_user = localStorage.getItem('friend_user');



    let data2=  {
        "postId": "post1",
        "content": "Bao test post notificatio",
        "avatar": "10",
        "email": "email@com.com",
        "postedDate":"2019-07-14T17:27:26.575",
        "postedBy":{
            "userId": friend_user.email,
            "fullName": friend_user.fullName,
            "avatar": friend_user.avatar,
            "email": friend_user.email,
            "dob":friend_user.dob
        },
        "numOfLike":0,
        "numOfLove":0,
        "likeUsers":[],
        "loveUsers":[]
    };
    //noti(data2);
    getPostNotification();

    setInterval(getPostNotification, 1000);


    function noti(data2) {
        console.log("noti sent1111111");
        $.ajax({
            url: "http://localhost:8899/posts/add",
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data2),
            success: function( data, textStatus ){
                console.log("noti sent");

            },
            error: function( jqXhr, textStatus, errorThrown ){
                console.log( errorThrown );
            }
        });
    }

    function getPostNotification(){
        $.ajax({
            url: "http://localhost:9999/postInfo/"+ userName,
            type: "GET",
            dataType: "json",
            success: function (data) {
                //alert(data.length);
                console.log(data);
                $('#numberMessages').text(data.length);
                $('#numbersMessages').text(data.length);
                $('#numberMessages').css("color", "red");

                console.log(parseJwt(token));

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

                console.log(mydata);


                console.log(html);



            }
        });
    }

});
