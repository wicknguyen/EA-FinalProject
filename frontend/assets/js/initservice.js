$(window).ready(function () {
    let BASE_URL = "http://localhost:8080/api/";
    let CREATE_POST_URL = "http://localhost:8080/api/post";
    let GET_TIMELINE_URL = "http://localhost:8080/api/timeline/";
    let GET_USER_URL = "http://localhost:8080/api/user/";
    let token = localStorage.getItem('access_token');
    let userName = parseJwt(token).user_name;
    var postId;
    var timeline;
    var current_user;
    let LOAD_COMMENT_POST_URL = "http://localhost:8080/api/comment/";
    let LIKE_POST_URL = "http://localhost:8080/api/like-post";
    let LIKE_COMMENT_URL = "http://localhost:8080/api/like-comment";
    let COMMENT_URL = "http://localhost:8080/api/comment";

    // get user info
    $.ajax({
            url: GET_USER_URL + userName,
            type: "GET",
            dataType: "json",
            success: function (data) {
                console.log("Load current user successfully", data);
                current_user = data;
                let template = $('#user-profile-template').html();
                let templateScript = Handlebars.compile(template);
                let html = templateScript(data);
                $('#user-profile').append(html);
            }
    });



    // fetch timeline
    $.ajax({
        url: GET_TIMELINE_URL + userName,
        type: "GET",
        dataType: "json",
        success: function (data) {
            timeline = data;
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

            //get posts
            var template = $('#handlebar').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#posts').append(html);

            // get friends
            var template = $('#handlebar-friends').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#friends').append(html);

            // get following
            var template = $('#handlebar-followings').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#followings').append(html);

            // get requested
            var template = $('#handlebar-requested').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#requested').append(html);

            //accept friend
            $("button[id^=accept]").click(function () {
                acceptFriend($(this).val(), userName, "acceptFriend");
                location.reload();
            });

            //deny friend
            $("button[id^=deny]").click(function () {
                console.log("value = " + $(this).val());
                acceptFriend($(this).val(), userName, "rejectFriend");
                location.reload();
            });


            var responseData = null;
            // click open comment modal
            $("button[data-target='#kt_modal_3']").click(function () {
                let postId = $(this).attr('postId');
                // fetch post
                $.ajax({
                    url: LOAD_COMMENT_POST_URL + postId,
                    type: "GET",
                    dataType: "json",
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
                    },
                    success: function (dataPostComment) {
                        responseData = dataPostComment;
                        // get friends
                        let template = $('#post-modal-content').html();
                        let templateScript = Handlebars.compile(template);
                        let html = templateScript(dataPostComment);
                        $('#modal-post-comment-content').append(html);

                        // add event handle
                        $('#like-comment-btn').click(function () {
                            let commentInfo = getCommentInfo(responseData.commentInfos, $(this).attr('commentId'));

                            if (isLikedByCurrentUser(commentInfo)) {
                                commentInfo.likeUsers.forEach(
                                    u => {
                                    if (u.email == current_user.email) {
                                    commentInfo.likeUsers.splice(commentInfo.likeUsers.indexOf(u), 1);
                                    commentInfo.numOfLike--;
                                    $(this).removeClass('like-post');
                                    $(this).next().text(commentInfo.numOfLike);
                                }
                            });
                            } else {
                                commentInfo.likeUsers.push(current_user);
                                commentInfo.numOfLike++;
                                $(this).addClass('like-post');
                                $(this).next().text(commentInfo.numOfLike);
                            }
                            $.ajax({
                                url: LIKE_COMMENT_URL,
                                type: "POST",
                                dataType: "json",
                                contentType: 'application/json',
                                headers: {
                                    'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
                                },
                                data: JSON.stringify(commentInfo),
                                success: function (data) {
                                    console.log(data);
                                }
                            });

                        });

                        // $('#shareCommentPost').click(function (e) {
                        //     console.log('Share Comment', $('#commentContent').val());
                        //     console.log(postId);
                        // });
                    }
                });
            });

            $('#shareCommentPost').click(function (e) {
                console.log('Share Comment', $('#commentContent').val());

                let comment = {
                    commentId: '',
                    postedDate: new Date(),
                    postedBy: current_user,
                    content: $('#commentContent').val(),
                    numOfLike: 0,
                    numOfLove: 0,
                    likeUsers: [],
                    loveUsers: [],
                    commentInfos: []
                };
                responseData.commentInfos = [comment];
                $.ajax({
                    url: COMMENT_URL,
                    type: "POST",
                    dataType: "json",
                    contentType: 'application/json',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
                    },
                    data: JSON.stringify(responseData),
                    success: function (updatedCommentRes) {
                        console.log(updatedCommentRes);
                    }
                });
            });

            // like post
            $('#like-post-btn').click(function () {
                postId = $(this).attr('postId');
                postInfo = getPostInfo(postId);
                if (isLikedByCurrentUser(postInfo)) {
                    postInfo.likeUsers.forEach(
                        u => {
                            if (u.email == current_user.email) {
                                postInfo.likeUsers.splice(postInfo.likeUsers.indexOf(u), 1);
                                postInfo.numOfLike--;
                                $(this).removeClass('like-post');
                                $(this).next().text(postInfo.numOfLike);
                            }
                    });
                } else {
                    postInfo.likeUsers.push(current_user);
                    postInfo.numOfLike++;
                    $(this).addClass('like-post');
                    $(this).next().text(postInfo.numOfLike);
                }
                $.ajax({
                    url: LIKE_POST_URL,
                    type: "POST",
                    dataType: "json",
                    contentType: 'application/json',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
                    },
                    data: JSON.stringify(postInfo),
                    success: function (data) {
                        console.log(data);
                    }
                });
            });


        }
    });

    // accept or deny
    function acceptFriend(from, to, action) {
        data = {
            "a": {
                "email": from
            },
            "b": {
                "email": to
            }
        };

        $.ajax({
            url: BASE_URL + action,
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function( response ){
                console.log("post sent");
            },
            error: function( error ){
                console.log( error );
            }
        });
    }

    // Create post
    $('#post').click(function () {
        let content = $('#exampleTextarea').val();
        let data = {
            "content": content,
            "postedDate": moment( new Date()).format("YYYY-MM-DDTHH:mm:ss.SSS"),
            "postedBy": {
                "email": userName,
                "userId": 1,
                "fullName": "Bao Tran",
                "avatar": "10",
                "dob":"1977-07-07"
            },
            "numOfLike": 0,
            "numOfLove": 0,
            "likeUsers": [
            ],
            "loveUsers": [
            ],
            "commentInfos": [
            ]
        };

        $.ajax({
            url: CREATE_POST_URL,
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function( response ){
                console.log("post sent");
            },
            error: function( error ){
                console.log( error );
            }
        });

        sentNoti(data);
        location.reload();

    });


    function getPostInfo(postId) {
        post = null;
        timeline.posts.forEach(p => {
            if (p.postId == postId) {
            post = p;
            return;
        }
    });
        return post;
    }

    function getCommentInfo(commentInfos, commentId) {
        comment = null;
        commentInfos.forEach(c => {
            if (c.commentId == commentId) {
            comment = c;
            return;
        }
    });
        return comment;
    }

    function isLikedByCurrentUser(post) {
        if (post && post.likeUsers) {
            return post.likeUsers.some((u) => {
                return u.email === current_user.email;
        });
        }
        return false;
    }

    function parseJwt (token) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    }


    function sentNoti(data) {
        $.ajax({
            url: "http://localhost:8899/posts/add",
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function( res, textStatus ){
                console.log("noti sent");

            },
            error: function( error ){
                console.log( error );
            }
        });
    }

    $('#btnSignout').click(function () {
        localStorage.clear();
        window.location = './login-page.html';
    });

});
