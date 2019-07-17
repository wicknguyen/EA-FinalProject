$(function () {

    let BASE_URL = "http://localhost:8080/api/";
    let CREATE_POST_URL = "http://localhost:8080/api/post";
    let GET_TIMELINE_URL = "http://localhost:8080/api/timeline/";
    let GET_USER_URL = "http://localhost:8080/api/user/";
    let LOAD_COMMENT_POST_URL = "http://localhost:8080/api/comment/";
    let LIKE_POST_URL = "http://localhost:8080/api/like-post";
    let LIKE_COMMENT_URL = "http://localhost:8080/api/like-comment";
    let COMMENT_URL = "http://localhost:8080/api/comment";
    let userName = localStorage.getItem("personal");
    let current_user = JSON.parse(localStorage.getItem('current_user'));

    // get friend info
    $.ajax({
        url: "http://localhost:8080/api/user/" + userName,
        type: "GET",
        dataType: "json",
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
        },
        success: function (data) {
            console.log("Load friend user successfully", data);
            friend_user = data;
            localStorage.setItem('friend_user', data);
            var template = $('#user-profile-template').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(data);
            $('#user-profile').append(html);

            Handlebars.registerHelper('firstLetter', function(name) {
                return name.charAt(0).toUpperCase();
            });

            // let template1 = $('#user-bar-template').html();
            // let templateScript1 = Handlebars.compile(template1);
            // let html1 = templateScript1(localStorage.getItem("current_user"));
            // $('#user-bar').append(html1);



            $('#btnSignout').click(function () {
                localStorage.clear();
                window.location = './login-page.html';
            });


            //
            var template = $('#handlebar-title').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(data);
            $('#title').append(html);
        }
    });

    $.ajax({
        url: "http://localhost:8080/api/timeline/" + userName,
        type: "GET",
        dataType: "json",
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
        },
        success: function (data) {
            timeline = data;
            Handlebars.registerHelper('fromNow', function(date) {
                if (moment) {
                    return moment(date).fromNow();
                } else {
                    return date;
                }
            });

            Handlebars.registerHelper('addLikeClass', function(likeUsers) {
                for (var i=0; i< likeUsers.length; i++) {
                    if (likeUsers[i].email === current_user.email) {
                        return 'like-post';
                    }
                }
                return '';
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

            // click friend profile
            $("a[idPage^=goto-]").click(function () {
                console.log('testQQQQ');
                localStorage.setItem("personal", $(this).attr("name") )
            });

            var responseData = null;
            // click open comment modal
            $("button[data-target='#kt_modal_3']").click(function () {
                postId = $(this).attr('postId');
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
                        console.log(responseData)
                        Handlebars.registerHelper('addLikeClass', function(likeUsers) {
                            for (var i=0; i< likeUsers.length; i++) {
                                if (likeUsers[i].email === current_user.email) {
                                    return 'like-post';
                                }
                            }
                            return '';
                        });

                        Handlebars.registerHelper('fromNow', function(date) {
                            console.log(date);
                            if (moment) {
                                return moment(date).fromNow();
                            } else {
                                return date;
                            }
                        });

                        // get friends
                        let template = $('#post-modal-content').html();
                        let templateScript = Handlebars.compile(template);
                        let html = templateScript(dataPostComment);

                        $('#model-post-comment-content-child').html('');
                        $('#model-post-comment-content-child').append(html);

                        // add event handle
                        $('button[idLike="like-comment-btn"]').click(function () {
                            let commentInfo = getCommentInfo(responseData.commentInfos, $(this).attr('commentId'));

                            if (isLikedByCurrentUser(commentInfo)) {
                                for (let i=0; i<commentInfo.likeUsers.length; i++) {
                                    let u = commentInfo.likeUsers[i];
                                    if (u.email == current_user.email) {
                                        commentInfo.likeUsers.splice(commentInfo.likeUsers.indexOf(u), 1);
                                        commentInfo.numOfLike--;
                                        $(this).removeClass('like-post');
                                        $(this).next().text(commentInfo.numOfLike);
                                    }
                                }
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
                                success: function (likeCommentdata) {
                                    console.log(likeCommentdata);
                                }
                            });

                        });

                        $('#commentContent').keyup(function () {
                            if($(this).val()) {
                                $('#shareCommentPost').removeAttr("disabled");
                            } else {
                                $('#shareCommentPost').attr("disabled", true);
                            }
                        });

                        $('#shareCommentPost').click(function (e) {
                            console.log('Share Comment', $('#commentContent').val());

                            let comment = {
                                commentId: '',
                                postedDate: new Date(),
                                postedBy: friend_user,
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
                                    let numCommentPostId = 'numCommentPostId="' + postId + '"';
                                    $('span['+numCommentPostId+']').text(updatedCommentRes.commentInfos.length);
                                    $('#commentContent').val('');
                                }
                            });
                        });
                    }
                });
            });

            // like post
            $('button[idLikePost="like-post-btn"]').click(function () {
                let postId = $(this).attr('postId');
                let postInfo = getPostInfo(postId);
                console.log("POST-INFO", postInfo);
                if (isLikedByCurrentUser(postInfo)) {
                    for (let i=0; i<postInfo.likeUsers.length; i++) {
                        let u = postInfo.likeUsers[i];
                        if (u.email == current_user.email) {
                            postInfo.likeUsers.splice(postInfo.likeUsers.indexOf(u), 1);
                            postInfo.numOfLike--;
                            $(this).removeClass('like-post');
                            $(this).next().text(postInfo.numOfLike);
                        }
                    }
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


            // // click friend profile
            // $("a[id^=goto-]").click(function () {
            //     localStorage.setItem("personal", $(this).attr("name") )
            // });

        }
    });

    function getPostInfo(postId) {
        post = null;
        for (let i=0; i<timeline.posts.length; i++) {
            if (timeline.posts[i].postId == postId) {
                return timeline.posts[i];
            }
        }
        return null;
    }

    function getCommentInfo(commentInfos, commentId) {
        comment = null;
        for (let i=0; i<commentInfos.length; i++) {
            if (commentInfos[i].commentId == commentId) {
                return commentInfos[i];
            }
        }
        return null;
    }

    function isLikedByCurrentUser(post) {
        if (post && post.likeUsers) {
            return post.likeUsers.some((u) => {
                return u.email === current_user.email;
            });
        }
        return false;
    }

});
