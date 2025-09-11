<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">

<head>
    <style>

        .card-title1{
            font-size: 1.1rem;
            font-weight: 400;
            margin: 0;
            text-align: center;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 15%;
            top: 0;
            width: 40%; /* Full width */
            height: 100%; /* Full height */
            /* overflow: auto;  Enable scroll if needed */
            /*background-color: rgb(0,0,0); !* Fallback color *!*/
            background-color: rgba(0, 0, 0, 0.9); /* Black w/ opacity */
        }

        .modal1 {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 60%;
            top: 0;
            right: 0;
            width: 40%; /* Full width */
            height: 100%; /* Full height */
            /* overflow: auto; Enable scroll if needed */
            /*background-color: rgb(0,0,0); !* Fallback color *!*/
            background-color: rgba(0, 0, 0, 0.9); /* Black w/ opacity */
        }

        /* Modal Content (Image) */
        .modal-content {
            margin: auto;
            display: block;
            /*top: 10%;*/
            top: 10%;
            width: 80%;
            height: 60%;
            max-width: 700px;
        }
        /*Caption of Modal Image (Image Text) - Same Width as the Image */
        #caption {
            margin: auto;
            display: block;
            width: 80%;
            max-width: 700px;
            text-align: center;
            color: #ccc;
            padding: 10px 0;
            height: 150px;
        }

        /* Add Animation - Zoom in the Modal */
        .modal-content, #caption {
            animation-name: zoom;
            animation-duration: 0.6s;
        }

        @keyframes zoom {
            from {
                transform: scale(0)
            }
            to {
                transform: scale(1)
            }
        }

        /* The Close Button */
        .close {
            position: absolute;
            top: 15px;
            right: 35px;
            color: #f1f1f1;
            font-size: 40px;
            font-weight: bold;
            transition: 0.3s;
        }

        .close1 {
            position: absolute;
            top: 15px;
            right: 35px;
            color: #f1f1f1;
            font-size: 40px;
            font-weight: bold;
            transition: 0.3s;
        }

        .close:hover,
        .close:focus {
            color: #acacac;
            text-decoration: none;
            cursor: pointer;
        }

        .close1:hover,
        .close1:focus {
            color: #bbb;
            text-decoration: none;
            cursor: pointer;
        }

        /* POPUP for documents pdffunction */
        #popup {
            display: none;
            border: 1px black solid;
            /*width: 700px;*/
            height: 700px;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: white;
            z-index: 10;
            padding: 2em;
            position: absolute;
        }

        #popupc {
            display: none;
            border: 1px black solid;
            /* width: 700px;*/
            height: 700px;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: white;
            z-index: 10;
            padding: 2em;
            position: absolute;
        }

        .darken {
            background: rgba(0, 0, 0, 0.7);
        }

        #iframe {
            border: 0;
        }

        /*html, body, #page { height: 100%;}*/

        /* 100% Image Width on Smaller Screens */
        @media only screen and (max-width: 700px) {
            .modal-content {
                width: 100%;
            }

            * {
                box-sizing: border-box;
            }

            .img-zoom-container {
                position: relative;
            }

            .img-zoom-lens {
                position: absolute;
                border: 1px solid #d4d4d4;
                /*set the size of the lens:*/
                width: 40px;
                height: 40px;
            }

            .img-zoom-result {
                border: 1px solid #d4d4d4;
                /*set the size of the result div:*/
                width: 300px;
                height: 300px;
            }
        }
        #controls{
            position: fixed;
            left: 20%;
            width: 30px;
            height: 30px;
            top:85%;
        }
        #lftrot{
            top: 30px;
            left : 400px;
            position: absolute;
        }
        #rftrot{
            top:30px;
            position: absolute;
            left: 440px;
        }
        #overlap{
            top: 30px;
            left:480px;
            position: absolute;
        }
        #zoomplus {
            top: 30px;
            left: 0px;
            position: absolute;
        }
        #zoomminus{
            top: 30px;
            left:40px;
            position: absolute;
        }
        #zoomplus:hover, #zoomplus:focus{
            background-color: #ffffff;
            height: 35px;
            width: 35px;
        }
        #zoomminus:hover, #zoomminus:focus{
            background-color: #ffffff;
            height: 35px;
            width: 35px;
        }
        #rftrot:hover, #rftrot:focus{
            background-color: #ffffff;
            height: 35px;
            width: 35px;
        }
        #lftrot:hover, #lftrot:focus{
            background-color: #ffffff;
            height: 35px;
            width: 35px;
        }
        #overlap:hover, #overlap:focus{
            background-color: #ffffff;
            height: 35px;
            width: 35px;
        }
        #original{
            top: 30px;
            left:520px;
            position: absolute;
        }
        #original:hover, #original:focus{
            background-color: #ffffff;
            height: 35px;
            width: 35px;
        }
        .secondary{
            color: #1f4380;
        }


        /*
        MYLOADER*/
        .loaderWrapper { position: fixed;
            top: 0;
            width: 100%;
            height: 100%;
            background: #fff;
            z-index: 1000;
            pointer-events: none;
            display: flex;
            justify-content: center;
            align-items: center;
            animation: fadeLoader 0.6s 10s ease forwards; }

        .loader {
            display: flex;
            flex-direction: row;
            align-items: center;
            z-index: 999;
        }

        .loader .bar
        {
            width: 10px;
            height: 5px;
            background: #000000;
            margin: 2px;
            animation: bar 1s infinite linear;
        }

        .loader .bar:nth-child(1) {
            animation-delay: 0s;
        }
        .loader .bar:nth-child(2)
        { animation-delay: 0.25s;
        }
        .loader .bar:nth-child(3)
        {
            animation-delay: 0.5s;
        }
        @keyframes bar
        {
            0% {
                transform: scaleY(1) scaleX(0.5);
            } 50% {
                  transform: scaleY(10) scaleX(1);
              }
            100% { transform: scaleY(1) scaleX(0.5); }
        }
        @keyframes fadeLoader { to { opacity: 0; } }





        .frame {
            width: 250px;
            height: 200px;
            border: 2px solid #4080bf;
            background: white;
            margin: auto;
            padding: 10px 10px;

        }

        img {
            width: 100%;
            height: 100%;
        }

        .photo {
            border-radius: 6px;
            border: 2px solid #4080bf;
            width: 200px;
            height: 200px;
            margin-top: 10px;
            margin-left: 10px;
            float: left;
            overflow: hidden;
            position: relative;
        }

        .photo1 {
            border-radius: 6px;
            border: 2px solid #4080bf;
            width: 150px;
            height: 150px;
            margin-top: 10px;
            margin-left: 10px;
            float: left;
            overflow: hidden;
            position: relative;
        }

        .photo-name {
            /*margin-top: 245px;*/
            /*padding: 0.5em;*/
            position: absolute;
            width: 100%;
            text-align: left;
            background-color: #4080bf;
        }

        .box-text {
            margin-top: 50px;
            position: absolute;
            width: 100%;
            text-align: center;
            font-weight: bold;
            font-size: 40px;
            color: black;
        }

        .post {
            border-bottom: 1px solid #adb5bd;
            color: white;
            margin-bottom: 15px;
            padding-bottom: 15px;
        }

        .color-font {
            color: #1f4380;

        }

        .center {
            display: block;
            margin-left: auto;
            margin-right: auto;
            width: 50%;
        }
        .color-fonts{
            padding-left: 400px;
        }
        .values{
            float:right;
        }


    </style>
</head>

<div id="spinner" class="loaderWrapper" style="display: none;">
    <div class="loader" style="position: absolute; top: 50%;left: 40%">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
</div>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" href="plugins/sweetalert2/sweetalert2.css">

<script src="plugins/sweetalert2/sweetalert2.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>




<%--<script type="text/javascript">
    $(function () {

        $("input[name=unique]").click(function () {
            if ($(this).val() == "Unique") {
                $("#dvPassport").show();
            } else {
                $("#dvPassport").hide();
            }
        });
    });
</script>--%>





<script>

function imageZoom(imgID, resultID) {
        var img, lens, result, cx, cy;
        img = document.getElementById(imgID);
        result = document.getElementById(resultID);
        /* Create lens: */
        lens = document.createElement("DIV");
        lens.setAttribute("class", "img-zoom-lens");
        /* Insert lens: */
        img.parentElement.insertBefore(lens, img);
        /* Calculate the ratio between result DIV and lens: */
        cx = result.offsetWidth / lens.offsetWidth;
        cy = result.offsetHeight / lens.offsetHeight;
        /* Set background properties for the result DIV */
        result.style.backgroundImage = "url('" + img.src + "')";
        result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
        /* Execute a function when someone moves the cursor over the image, or the lens: */
        lens.addEventListener("mousemove", moveLens);
        img.addEventListener("mousemove", moveLens);
        /* And also for touch screens: */
        lens.addEventListener("touchmove", moveLens);
        img.addEventListener("touchmove", moveLens);

        function moveLens(e) {
            var pos, x, y;
            /* Prevent any other actions that may occur when moving over the image */
            e.preventDefault();
            /* Get the cursor's x and y positions: */
            pos = getCursorPos(e);
            /* Calculate the position of the lens: */
            x = pos.x - (lens.offsetWidth / 2);
            y = pos.y - (lens.offsetHeight / 2);
            /* Prevent the lens from being positioned outside the image: */
            if (x > img.width - lens.offsetWidth) {
                x = img.width - lens.offsetWidth;
            }
            if (x < 0) {
                x = 0;
            }
            if (y > img.height - lens.offsetHeight) {
                y = img.height - lens.offsetHeight;
            }
            if (y < 0) {
                y = 0;
            }
            /* Set the position of the lens: */
            lens.style.left = x + "px";
            lens.style.top = y + "px";
            /* Display what the lens "sees": */
            result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
        }

        function getCursorPos(e) {
            var a, x = 0, y = 0;
            e = e || window.event;
            /* Get the x and y positions of the image: */
            a = img.getBoundingClientRect();
            /* Calculate the cursor's x and y coordinates, relative to the image: */
            x = e.pageX - a.left;
            y = e.pageY - a.top;
            /* Consider any page scrolling: */
            x = x - window.pageXOffset;
            y = y - window.pageYOffset;
            return {x: x, y: y};
        }
    }
</script>
<script>
    $(document).ready(function()
    {
        $('img').bind('contextmenu', function(e){
            return false;
        });
        document.getElementById('myimage').setAttribute('draggable', false);
        document.getElementById('myimage1').setAttribute('draggable', false);
        document.getElementById('img02').setAttribute('draggable', false);
        document.getElementById('img01').setAttribute('draggable', false);
    });
</script>


<script>

    function alertHit(){
        // Swal.fire("Our First Alert");
        swal.fire({
            title: "<div>You are about to tag this case as <span style='color:red; font-style: italic; text-decoration: underline;'>HIT</span></div>",
            // text: "Once deleted, you will not be able to recover this imaginary file!",
            // icon: "warning",
            // buttons: true,
            showCancelButton: true,
            confirmButtonText: "CONFIRM",
            cancelButtonText: "CANCEL",

            // showDenyButton: true,
            // dangerMode: true,
        })
            .then((result) => {
                if (result.isConfirmed) {

                    submitHit();


                } else {
                    // swal.fire({icon: "warning", title: "cancelled"});
                    swal.fire({title: "cancelled"});
                }
            });
    }

    function alertNoHit(){

        swal.fire({
            title: "<div>You are about to tag this case as <span style='color:green; font-style: italic; text-decoration: underline;'>NO HIT</span></div>",
            // text: "Once deleted, you will not be able to recover this imaginary file!",
            // icon: "warning",
            showCancelButton: true,
            confirmButtonText: "CONFIRM",
            cancelButtonText: "CANCEL",
            // dangerMode: true,
        })
            .then((result) => {
                if (result.isConfirmed) {
                    submitNoHit();
                } else {
                    // swal.fire({icon: "warning", title: "cancelled"});
                    swal.fire({title: "cancelled"});
                }
            });
    }

</script>

<script>
    // Step 1: Push a state into the browser history when the page loads
    window.history.pushState(null, null, window.location.href);

    // Step 2: Prevent back button by listening to the popstate event
    window.onpopstate = function (event) {
        window.history.pushState(null, null, window.location.href); // Push the same URL again
    };

    // Simulate page load
    window.onload = function() {
        // Step 3: After the page is fully loaded, stop preventing back navigation
        setTimeout(() => {
            window.onpopstate = null; // Allow back navigation
        }, 3000); // Adjust this timeout to match your page load time
    };

</script>



<script>

    // jQuery(document).ready(function($) {
    //
    //     if (window.history && window.history.pushState) {
    //         alert('Back button');
    //         window.history.pushState('forward', null, '/leveloneSearchByName');
    //         $(window).on('popstate', function() {
    //             alert('Back button was pressed.');
    //             document.getElementById('leveloneform').action = "/resetProcrssStatus";
    //             document.getElementById('leveloneform').submit();
    //             console.log(window.history.scrollRestoration('manual'));
    //         });
    //         // window.history.pushState('forward', null, './#forward');
    //         // $(window).on('popstate', function() {
    //         //         alert('Back button was pressed.');
    //         //         console.log(window.history.scrollRestoration('manual'));
    //         // });
    //
    //     }
    // });

function addClass(){
  document.getElementById("spinner").style.display="block";
}

    function submitHit() {
        const form = document.getElementById('leveloneform');
        form.action = "<c:url value='saveMVSL1Result'/>";//war
        // form.action = "/saveMVSL1Result";//local
        var id = document.getElementById('pkID').value;
        var requestId = document.getElementById('requestId').value;
        var comment = document.getElementById('comment').value;
        var data = {};
        data["sno"] = id;
        data["verifyStatus"] = 'hit';
        data["requestId"] = requestId;
        data["statusComment"] = comment;
        if (comment != '' ){
            for (const key in data) {
                if (data.hasOwnProperty(key)) {
                    const hiddenField = document.createElement('input');
                    hiddenField.type = 'hidden';
                    hiddenField.name = key;
                    hiddenField.value = data[key];


                    form.appendChild(hiddenField);
                }
            }
            document.body.appendChild(form);
            form.submit();
            addClass();


            // $('#loader').addClass("loader");
            // document.getElementById('leveloneform').action = "/saveMVSL1Result?sno=" + id + "&verifyStatus=hit"+"&statusComment="+comment+"&requestId="+requestId;
            // document.getElementById('leveloneform').action = "/MVS/saveMVSL1Result?sno=" + id + "&verifyStatus=hit"+"&statusComment="+comment+"&requestId="+requestId;
            // document.getElementById('leveloneform').submit();
        }else{
            document.getElementById('comment').style.border = "1px solid red";
            // swal.fire({icon: "warning", title: "please fill the comment box"});
            swal.fire({title: "please fill the comment box"});

        }
    }

    function submitNoHit() {

        const form = document.getElementById('leveloneform');
        form.action = "<c:url value='saveMVSL1Result'/>";//war
        // form.action = "/saveMVSL1Result";//local
        var id = document.getElementById('pkID').value;
        var requestId = document.getElementById('requestId').value;
        console.log("requestId"+requestId);
        var comment = document.getElementById('comment').value;
        var data = {};
        data["sno"] = id;
        data["verifyStatus"] = 'nohit';
        data["requestId"] = requestId;
        data["statusComment"] = comment;
        if(comment != ''){

            for (const key in data) {
                if (data.hasOwnProperty(key)) {
                    const hiddenField = document.createElement('input');
                    hiddenField.type = 'hidden';
                    hiddenField.name = key;
                    hiddenField.value = data[key];


                    form.appendChild(hiddenField);
                }
            }
            // document.getElementById('leveloneform').action = "/saveMVSL1Result?sno=" + id + "&verifyStatus=nohit"+"&statusComment="+comment+"&requestId="+requestId;
            //
            document.body.appendChild(form);
            // document.getElementById('leveloneform').action = "/MVS/saveMVSL1Result?sno=" + id + "&verifyStatus=nohit"+"&requestId="+requestId+"&statusComment="+comment;
            // document.getElementById('leveloneform').submit();

            form.submit();
            addClass();

        }else{
            document.getElementById('comment').style.border = "1px solid red";
            // swal.fire({icon: "warning", title: "please fill the comment box"});
            swal.fire({title: "please fill the comment box"});
        }
    }

    // commented this script reset process to 0
    // -------------------------
    // var onBeforeUnLoadEvent = false;
    //
    // window.onunload = window.onbeforeunload = function(){
    //     if(!onBeforeUnLoadEvent){
    //         onBeforeUnLoadEvent = true;
    //         //your code here
    //         var id = document.getElementById('pkID').value;
    //         $.ajax({
    //             type: "GET",
    //             url: "/MVS/resetProcrssStatus?sno="+id // the URL of the controller action method
    //         });
    //     }
    // };
    // ---------------------------------

    // window.onbeforeunload = function() {
    //     resetProcessStatusCode();
    //
    // }
    //
    // function resetProcessStatusCode(){
    //     var id = document.getElementById('pkID').value;
    //     $.ajax({
    //         type: "GET",
    //         url: "/MVS/resetProcrssStatus?sno="+id // the URL of the controller action method
    //     });
    // }

    // window.onunload = window.onbeforeunload = function() {
    //     // $('#loader').addClass("hide-loader");
    //     var element = document.getElementById("spinner");
    //     element.classList.remove("loaderWrapper");
    // }
</script>

<section class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6"></div>
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="dashBoard" >Home</a></li>
                    <li class="breadcrumb-item active" >Operators</li>
                </ol>
            </div>
        </div>
    </div>
    <!-- /.container-fluid -->
</section>


<div class="row">
    <%--    <div class="col-sm-10"></div>--%>
    <div class="col-md-6" style="padding-left: 30px;padding-bottom: 10px;position: relative">
        <span>PROBE RID : </span>
        <%--        <span style="border: 1px solid black">${probeid}</span>--%>
        <input type="text" style="width: 222px" class="form-control-sm"  readonly value="${probeid}">
        <span style="position: absolute;top:-2px; margin-left: 10px"><i class="far fa-square fa-2x"></i></span>
    </div>
    <div class="col-md-4" style="padding-left: 10px;padding-bottom: 10px;position: relative">
        <span >CANDIDATE RID : </span>
        <%--        <span style="border: 1px solid black">${canid}</span>--%>
        <input type="text" style="width: 222px"  class="form-control-sm"   readonly value="${canid}">
        <c:choose>
            <c:when test="${psnGenerated  == true}"><span style="position: absolute;top:-2px; margin-left: 10px"><i class="far fa-check-square fa-2x"></i></span></c:when>
            <c:when test="${psnGenerated  == false}"><span style="position: absolute;top:-2px; margin-left: 10px"><i class="far fa-square fa-2x"></i></span></c:when>
        </c:choose>


    </div>
    <div class="col-md-2">
        <h6 style="text-align: center;"><span>${count}<br></span> candidates </h6>
    </div>
</div>













<%--<div class="row">--%>
<%--&lt;%&ndash;    <div class="col-sm-10"></div>&ndash;%&gt;--%>
<%--    <div class="col-md-6">--%>
<%--        <span style="padding-left: 10px; margin-left: 15px;margin-bottom: 5px;font-weight: bold;position: relative;top: 25px;">PROBE RID : </span>--%>
<%--&lt;%&ndash;        <span style="border: 1px solid black">${probeid}</span>&ndash;%&gt;--%>
<%--        <input type="text" class="form-control-sm" style="width: 250px;margin-bottom: 5px;position: relative;top:25px;" readonly value="${probeid}">--%>
<%--        <span style="position: relative;top:32px"><i class="far fa-square fa-2x"></i></span>--%>
<%--    </div>--%>
<%--    <div class="col-md-4" style="position: relative;left: 10px">--%>
<%--        <span style="margin-left: 20px;margin-bottom: 5px; font-weight: bold;position: relative;right:28px;top:35px">CANDIDATE RID : </span>--%>
<%--&lt;%&ndash;        <span style="border: 1px solid black">${canid}</span>&ndash;%&gt;--%>
<%--        <input type="text" class="form-control-sm"  style="width: 250px;margin-bottom: 5px;position: relative;left:110px;" readonly value="${canid}">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${psnGenerated  == true}"><span style="position:relative;left:112px;top:5px"><i class="far fa-check-square fa-2x"></i></span></c:when>--%>
<%--            <c:when test="${psnGenerated  == false}"><span style="position:relative;left:112px;top:5px"><i class="far fa-square fa-2x"></i></span></c:when>--%>
<%--        </c:choose>--%>
<%--    </div>--%>
<%--    <div class="col-md-2">--%>
<%--        <h6 style="text-align: center"><span style="padding-left: 10px;position:relative;top: 35px">${count}</span> candidates </h6>--%>
<%--    </div>--%>
<%--</div>--%>








<!-- Main content -->
<section class="content">
    <div class="container-fluid">
        <div class="card card-primary">

            <%--            action="/saveMVSL1Result" method="post"--%>

            <form:form id="leveloneform" modelAttribute="galleryBean" enctype="application/json">

            <div class="row">

                <div class="col-md-6">
                    <input type="hidden" id="pkID" value="${id}">
                    <input type="hidden" id="requestId" value="${requestId}">
                    <div>
                        <div class="card card-primary" style="align-items: center; background-color: #1f4380" >
                            <div class="card-header p-2">
                                <h3 class="card-title">PROBE</h3>
                            </div></div>

                        <div class="card card-primary" style="align-items: center;">
                            <div class="col-sm-4 img-magnifier-container">
                                <img id="myimage" class="example" src="${probFaceImage}" name="myimage1"
                                     width="100" height="200" alt="probe" onclick="displaymodal(this.id , this.name)">
                            </div>
                            &nbsp;</div>
                    </div></div>

                <div class="col-md-6">

                    <!-- Profile Image  height: 280px;-->
                    <div>
                        <div class="card card-primary" style="align-items: center; background-color: #1f4380">
                            <div class="card-header p-2">
                                <h3 class="card-title">CANDIDATE</h3>
                            </div></div>
                        <div>

                            <div class="card card-primary" style="align-items: center">
                                <div class="col-sm-4 img-magnifier-container">

                                    <img align="img" width=100 height=200 id="myimage1" class="example"
                                         src=" ${CanFaceImage}" name="myimage"
                                         onclick="displaymodal1(this.id , this.name)"><br>
                                    &nbsp;

                                </div></div></div></div></div></div>

                <%--            <div class="row">--%>

                <%--                <div class="col-md-12">--%>
                <%--                    <div class="card card-primary">--%>
                <%--                        <div class="card-header p-2">--%>
                <%--                            <h3 class="card-title" style="padding-left: 414px">PROBE AND CANDIDATE DEMOGRAPHIC MATCHING SCORE  </h3>--%>
                <%--                        </div>--%>
                <%--                        <div class="card-body box-profile">--%>


                <%--                            <div class="user-block">--%>

                <%--                                <span class="username"> </span> <span class="description"></span>--%>
                <%--                            </div>--%>

                <%--                            <div class="row mb-3">--%>


                <%--                                <div class="col-sm-8">--%>
                <%--                                    <div class="row">--%>
                <%--                                        <div class="col-sm-12">--%>
                <%--                                            <ul class="list-group list-group-unbordered mb-3" style="line-height: initial">--%>

                <%--                                                <u class="color-font" style="padding-left: 400px;"><b>Full Name</b></u><br>--%>
                <%--                                                <a class="color-fonts"><b>First Name</b><input class="values"  value="${uiFields.firstName}" readonly/></a><br>--%>
                <%--                                                <a class="color-fonts"><b>Middle Name</b><input class="values"   value="${uiFields.middleName}" readonly/></a><br>--%>
                <%--                                                <a class=" color-fonts"><b>Last Name</b><input class="values" value="${uiFields.lastName}" readonly/></a><br>--%>
                <%--                                                <a class="color-fonts"><b>Suffix</b><input class="values" value="${uiFields.suffix}" readonly/></a><br>--%>
                <%--                                                <a class="color-fonts"><b>Gender</b><input class="values" value="${uiFields.gender}" readonly/></a><br>--%>
                <%--                                                <u class="color-font" style="padding-left: 400px;"><b>Date Of Birth</b></u><br>--%>
                <%--                                                <a class="color-fonts"><b>Month Of Birth</b><input class="values"  value="${uiFields.monthOfBirth}" readonly/></a><br>--%>
                <%--                                                <a class="color-fonts"><b>Day Of Birth</b><input class="values"   value="${uiFields.dayOfBirth}" readonly/></a><br>--%>
                <%--                                                <a class=" color-fonts"><b>Year Of Birth</b><input class="values" value="${uiFields.yearOfBirth}" readonly/></a>--%>
                <%--                                            </ul>--%>
                <%--                                        </div></div></div></div></div>--%>
                <%--                    </div></div>--%>
                <%--            </div>--%>

            <div class="row">
                    <%--                <div class="col-md-6">--%>

                    <%--                    <div class="card card-primary">--%>
                    <%--                        <div class="card-header p-2">--%>
                    <%--                            <h3 class="card-title">PROBE DEMOGRAPHIC DETAILS</h3>--%>
                    <%--                        </div>--%>
                    <%--                        <div class="card-body box-profile">--%>


                    <%--                            <div class="user-block">--%>

                    <%--                                <span class="username"> </span> <span class="description"></span>--%>
                    <%--                            </div>--%>

                    <%--                            <div class="row mb-3">--%>


                    <%--                                <div class="col-sm-8">--%>
                    <%--                                    <div class="row">--%>
                    <%--                                        <div class="col-sm-12">--%>

                    <%--                                            <ul class="list-group list-group-unbordered mb-3" style="line-height: initial" >--%>
                    <%--                                                <a class=" color-font"><b>Place Of Birth</b><input class="values" value="${probeDemoFields.pobCountry}" readonly/></a><br>--%>

                    <%--                                                <u class=" color-font"><b>Permanent Address</b></u><br>--%>
                    <%--                                                <a class=" color-font"><b>Address Line</b><input class="values" value="${probeDemoFields.presentAddressLine1}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>Barangay</b> <input class="values" value="${probeDemoFields.presentBarangay}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>City/Municipality</b><input class="values" value="${probeDemoFields.presentCity}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>Province</b> <input class="values" value="${probeDemoFields.presentProvince}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>Country</b> <input class="values" value="${probeDemoFields.pobCountry}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>ZipCode</b><input class="values" value="${probeDemoFields.presentZipcode}" readonly/></a><br>--%>

                    <%--                                            </ul>--%>
                    <%--                                        </div>--%>

                    <%--                                    </div>--%>

                    <%--                                </div>--%>

                    <%--                            </div>--%>

                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <%--                </div>--%>
                <div class="col-md-6">

                    <div class="card card-primary">
                        <div class="card-header p-2">
                            <h3 class="card-title">PROBE DEMOGRAPHIC DETAILS</h3>
                        </div>
                        <div class="card-body box-profile">

                            <div class="user-block">

                                <span class="username"> </span> <span class="description"></span>
                            </div>

                            <div class="row mb-3">
                                <div class="col-sm-10">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <ul class="list-group list-group-unbordered mb-3" style="line-height: initial">
                                                <u class="color-font "><b>FullName</b></u><br>
                                                <a class=" color-font "><b>First Name</b><input class="values" value="${probeDemoFields.firstName}" readonly/></a><br>
                                                <a class="color-font "><b>Middle Name</b><input class="values"  value="${probeDemoFields.middleName}" readonly/></a><br>
                                                <a class="color-font "><b>Last Name</b><input class="values"  value="${probeDemoFields.lastName}" readonly/></a><br>
                                                <a class="color-font "><b>Suffix</b><input class="values"   value="${probeDemoFields.suffix}" readonly/></a><br><br>
                                                <a class=" color-font "><b>Gender</b><input class="values" value="${probeDemoFields.gender}" readonly/></a><br>
                                                <u class=" color-font "><b>Date Of Birth</b></u><br>
                                                <a class="color-font "><b>Month Of Birth</b><input class="values" value="${probeDemoFields.monthOfBirth}" readonly/></a><br>
                                                <a class="color-font "><b>Day Of Birth</b><input class="values" value="${probeDemoFields.dayOfBirth}" readonly/></a><br>
                                                <a class=" color-font "><b>Year Of Birth</b><input class="values" value="${probeDemoFields.yearOfBirth}" readonly/></a><br>
                                                <a class=" color-font"><b>Place Of Birth</b><input class="values" value="${probeDemoFields.pobCountry}" readonly/></a><br>

                                                <u class=" color-font "><b>Permanent Address</b></u><br>
<%--                                                <a class=" color-font "><b>Room/Floor/Unit No/Building Name</b><input class="values" value="${probeDemoFields.presentAddressLine1}" readonly/></a><br>--%>
<%--                                                <a class=" color-font "><b>House/Lot/Block No</b> <input class="values" value="${probeDemoFields.presentAddressLine1}" readonly/></a><br>--%>
                                                <a class=" color-font "><b>Residential Address Field</b>

                                                    <textarea class="values" style="resize: none;" cols="23" rows="5" readonly > <c:out value="${probeDemoFields.presentAddressLine1}" /> </textarea>


<%--                                                    <input class="values" style="height: " value="${probeDemoFields.presentAddressLine1}" readonly></input>--%>
                                                </a><br>
<%--                                                <a class=" color-font "><b>Subdivision</b> <input class="values" value="${probeDemoFields.subDivision}" readonly/></a><br>--%>
                                                <a class=" color-font "><b>Barangay / Purok</b> <input class="values" value="${probeDemoFields.presentBarangay}" readonly/></a><br>
                                                <a class=" color-font "><b>City / Municipality</b><input class="values" value="${probeDemoFields.presentCity}" readonly/></a><br>
                                                <a class="color-font "><b>Province</b><input class="values" value="${probeDemoFields.presentProvince}" readonly/></a><br>
                                                <a class=" color-font "><b>Country</b><input class="values" value="${probeDemoFields.presentCountry}" readonly/></a>
                                            </ul>
                                        </div></div></div></div>

                        </div></div></div>
                    <%--                <div class="col-md-6">--%>

                    <%--                    <div class="card card-primary">--%>
                    <%--                        <div class="card-header p-2">--%>
                    <%--                            <h3 class="card-title">(CANDIDATE) OTHER DEMOGRAPHIC DETAILS</h3>--%>
                    <%--                        </div>--%>
                    <%--                        <div class="card-body box-profile">--%>


                    <%--                            <div class="user-block">--%>

                    <%--                                <span class="username"> </span> <span class="description"></span>--%>
                    <%--                            </div>--%>

                    <%--                            <div class="row mb-3">--%>


                    <%--                                <div class="col-sm-8">--%>
                    <%--                                    <div class="row">--%>
                    <%--                                        <div class="col-sm-12">--%>

                    <%--                                            <ul class="list-group list-group-unbordered mb-3" style="line-height: initial">--%>
                    <%--                                                <a class=" color-font"><b>Place Of Birth</b><input class="values" value="${CanDemoFields.pobCountry}" readonly/></a><br>--%>

                    <%--                                                <u class=" color-font"><b>Permanent Address</b></u><br>--%>
                    <%--                                                <a class=" color-font"><b>Address Line</b><input class="values" value="${CanDemoFields.presentAddressLine1}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>Barangay</b> <input class="values" value="${CanDemoFields.presentBarangay}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>City/Municipality</b><input class="values" value="${CanDemoFields.presentCity}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>Province</b> <input class="values" value="${CanDemoFields.presentProvince}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>Country</b> <input class="values" value="${CanDemoFields.pobCountry}" readonly/></a><br>--%>
                    <%--                                                <a class=" color-font"><b>ZipCode</b><input class="values" value="${CanDemoFields.presentZipcode}" readonly/></a><br>--%>


                    <%--                                            </ul>--%>



                    <%--                                        </div>--%>

                    <%--                                    </div>--%>

                    <%--                                </div>--%>

                    <%--                            </div>--%>

                    <%--                        </div>--%>

                    <%--                    </div>--%>

                    <%--                </div></div>--%>
                <div class="col-md-6">

                    <div class="card card-primary">
                        <div class="card-header p-2">
                            <h3 class="card-title">CANDIDATE DEMOGRAPHIC DETAILS</h3>
                        </div>
                        <div class="card-body box-profile">


                            <div class="user-block">

                                <span class="username"> </span> <span class="description"></span>
                            </div>

                            <div class="row mb-3">


                                <div class="col-sm-10">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <ul class="list-group list-group-unbordered mb-3" style="line-height: initial">
                                                <u class="color-font "><b>FullName</b></u><br>
                                                <a class=" color-font "><b>First Name</b><input class="values"  value="${CanDemoFields.firstName}" readonly/></a><br>
                                                <a class="color-font "><b>Middle Name</b><input class="values"  value="${CanDemoFields.middleName}" readonly/></a><br>
                                                <a class="color-font "><b>Last Name</b><input class="values" value="${CanDemoFields.lastName}" readonly/></a><br>
                                                <a class="color-font "><b>Suffix</b><input class="values"  value="${CanDemoFields.suffix}" readonly/></a><br><br>
                                                <a class=" color-font "><b>Gender</b><input class= "values" value="${CanDemoFields.gender}" readonly/></a><br>
                                                <u class=" color-font "><b>Date Of Birth</b></u><br>
                                                <a class="color-font "><b>Month Of Birth</b><input class="values" value="${CanDemoFields.monthOfBirth}" readonly/></a><br>
                                                <a class="color-font "><b>Day Of Birth</b><input class="values" value="${CanDemoFields.dayOfBirth}" readonly/></a><br>
                                                <a class=" color-font "><b>Year Of Birth</b><input class="values" value="${CanDemoFields.yearOfBirth}" readonly/></a><br>
                                                <a class=" color-font"><b>Place Of Birth</b><input class="values" value="${CanDemoFields.pobCountry}" readonly/></a><br>

                                                <u class=" color-font "><b>Permanent Address</b></u><br>
<%--                                                <a class=" color-font "><b>Room/Floor/Unit No/Building Name</b><input class="values" value="${CanDemoFields.presentAddressLine1}" readonly/></a><br>--%>
<%--                                                <a class=" color-font "><b>House/Lot/Block No</b> <input class="values" value="${CanDemoFields.presentAddressLine1}" readonly/></a><br>--%>
                                                <a class=" color-font "><b>Residential Address Field</b>
                                                    <textarea class="values" style="resize: none;" cols="23" rows="5" readonly><c:out value="${CanDemoFields.presentAddressLine1}" /> </textarea></a><br>

<%--                                                    <textarea class="values" modelAttributes="${CanDemoFields.presentAddressLine1}" readonly></textarea></a><br>--%>
<%--                                                <a class=" color-font "><b>Subdivision</b> <input class="values" value="${CanDemoFields.subDivision}" readonly/></a><br>--%>
                                                <a class=" color-font "><b>Barangay / Purok</b> <input class="values" value="${CanDemoFields.presentBarangay}" readonly/></a><br>
                                                <a class=" color-font "><b>City / Municipality</b><input class="values" value="${CanDemoFields.presentCity}" readonly/></a><br>
                                                <a class="color-font "><b>Province</b><input class="values" value="${CanDemoFields.presentProvince}" readonly/></a><br>
                                                <a class=" color-font "><b>Country</b><input class="values" value="${CanDemoFields.presentCountry}" readonly/></a>
                                            </ul>
                                        </div></div></div></div>

                        </div></div></div></div>

                <%--                <div class="row">--%>
                <%--                    <div class="col-sm-6">--%>

                <%--                        <h5 class="titledoc" style="background-color:#1f4380; color: white; ">(PROBE) SUPPORTING DOCUMENTS</h5>--%>
                <%--                        <li class="list-group-item color-font"><b>Passport matched with external database</b>--%>
                <%--                        <li class="list-group-item color-font"><b>UMID ID matched with external database</b>--%>
                <%--                    </div>--%>
                <%--                    <div class="col-sm-6">--%>

                <%--                        <h5 class="titledoc" style="background-color:#1f4380; color: white; ">(CANDIDATE) SUPPORTING DOCUMENTS</h5>--%>
                <%--                        <li class="list-group-item color-font"><b>Passport matched with external database</b>--%>
                <%--                        <li class="list-group-item color-font"><b>UMID ID matched with external database</b>--%>
                <%--                    </div>--%>
                <%--                </div><br>--%>

                <%--                <div class="row">--%>
                <%--                    <div class="col-sm-6">--%>

                <%--                        <h5 class="titledoc" style="background-color:#1f4380; color: white">(PROBE) SUPPORTING DOCUMENTS FOR BIOMETRIC EXCEPTIONS</h5>--%>
                <%--                        <li class="list-group-item color-font"><b>Pic.jpeg</b>--%>
                <%--                        <li class="list-group-item color-font"><b>Operator Comments</b>--%>
                <%--                    </div>--%>
                <%--                    <div class="col-sm-6">--%>

                <%--                        <h5 class="titledoc" style="background-color:#1f4380; color: white">(CANDIDATE) SUPPORTING DOCUMENTS FOR BIOMETRIC EXCEPTIONS</h5>--%>
                <%--                        <li class="list-group-item color-font"><b>Pic.jpeg</b>--%>
                <%--                        <li class="list-group-item color-font"><b>Operator Comments</b>--%>
                <%--                    </div>--%>
                <%--                </div><br>--%>
            <div class="row mb-12">
                <div class="col-md-6">
<%--                    <c:if test="${not empty reportPDFPOI || reportPDFPOA || reportPDFPOE }">--%>
                        <div class="card card-primary">
                            <div class="card-header p-2">
                                <h3 class="card-title">PROBE DOCUMENTS</h3>
                            </div>
                        </div>
<%--                    </c:if>--%>
                        <%--<div class="card card-primary">
                            <div class="card-header p-2">
                                <h3 class="card-title">PROOF OF IDENTITY</h3>
                            </div>
                        </div>--%>
<%--                    <c:if test="${not empty reportPDFPOI || reportPDFPOA || reportPDFPOE}">--%>
                        <div class="card-body">
                            <c:if test="${not empty reportPDFPOI}">
                                <div id="page">
                                    <a href="" id="probepoi"> Proof of Identity</a>
                                    <div id="popup">
                                        <i id="closepdf" class="fa fa-times"></i>Close
                                        <iframe id="iframe" width="550" height="600" frameborder="1"></iframe>
                                    </div>
                                </div></c:if>
                            <c:if test="${not empty reportPDFPOA}">
                                <div id="page">
                                    <a href="" id="probepoa"> Proof of Address</a>
                                    <div id="popup">
                                        <i id="closepdf" class="fa fa-times"></i>Close
                                        <iframe id="iframe" width="550" height="600"></iframe>
                                    </div>
                                </div></c:if>
                            <c:if test="${not empty reportPDFPOE}">
                                <div id="page">
                                    <a href="" id="probepoe"> Proof of Exception</a>
                                    <div id="popup">
                                        <i id="closepdf" class="fa fa-times"></i>Close
                                        <iframe id="iframe" width="550" height="600"></iframe>
                                    </div>
                                </div>

                            </c:if></div>
<%--                    </c:if>--%>
                </div>

                <div class="col-md-6">
<%--                    <c:if test="${not empty reportPDFPOICan || reportPDFPOACan || reportPDFPOECan}">--%>
                        <div class="card card-primary">
                            <div class="card-header p-2">
                                <h3 class="card-title">CANDIDATE DOCUMENTS</h3>
                            </div>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty reportPDFPOICan}">
                                <div id="pagec">
                                    <a href="" id="candipoi"> Proof of Identity</a>
                                    <div id="popupc">
                                        <i id="closepdf1" class="fa fa-times"></i>Close
                                        <iframe id="iframec" width="550" height="600" frameborder="1"></iframe>
                                    </div>
                                </div></c:if>
                            <c:if test="${not empty reportPDFPOACan}">
                                <div id="pagec">
                                    <a href="" id="candipoa"> Proof of Address</a>
                                    <div id="popupc">
                                        <i id="closepdf1" class="fa fa-times"></i>Close
                                        <iframe id="iframec" width="550" height="600" frameborder="1"></iframe>
                                    </div>
                                </div></c:if>

                            <c:if test="${not empty reportPDFPOECan}">
                                <div id="pagec">
                                    <a href="" id="candipoe"> Proof of Exception</a>
                                    <div id="popupc">
                                        <i id="closepdf1" class="fa fa-times">Close</i>
                                        <iframe id="iframec" width="500" height="600" frameborder="1"></iframe>
                                    </div>
                                </div>
                            </c:if>
                        </div>
<%--    </c:if>--%>

                </div>
            </div>
            <div class="row">

                <div class="col-md-6">
                    <div class="card card-primary">
                        <div class="card-header p-2">
                            <h6 class="card-title" >PROBE BIOMETRIC SCORE  </h6>
                        </div>
                        <div class="card-body box-profile">
                            <div class="user-block">
                                <span class="username"> </span> <span class="description"></span>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-10">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <ul class="list-group list-group-unbordered mb-3" >

                                                <a class="color-font"><b>Left Iris</b><input class="values" value="${probeBioFields.leftiris}" readonly/></a><br>
                                                <a class="color-font"><b>Right Iris</b><input class="values"  value="${probeBioFields.rightiris}" readonly/></a><br>
                                                <u class="color-font" ><b>Left Hand</b></u><br>
                                                <a class="color-font"><b>Left Thumb</b><input class="values"  value="${probeBioFields.leftthumb}" readonly/></a><br>
                                                <a class="color-font"><b>Left Index</b><input class="values"   value="${probeBioFields.leftindexfinger}" readonly/></a><br>
                                                <a class=" color-font"><b>Left Middle</b><input class="values" value="${probeBioFields.leftmiddlefinger}" readonly/></a><br>
                                                <a class="color-font"><b>Left Ring</b><input class="values" value="${probeBioFields.leftringfinger}" readonly/></a><br>
                                                <a class="color-font"><b>Left Little</b><input class="values" value="${probeBioFields.leftlittlefinger}" readonly/></a><br>
                                                <u class="color-font" ><b>Right Hand</b></u><br>
                                                <a class="color-font"><b>Right Thumb</b><input class="values"  value="${probeBioFields.rightthumb}" readonly/></a><br>
                                                <a class="color-font"><b>Right Index</b><input class="values"   value="${probeBioFields.rightindexfinger}" readonly/></a><br>
                                                <a class=" color-font"><b>Right Middle</b><input class="values" value="${probeBioFields.rightmiddlefinger}" readonly/></a><br>
                                                <a class="color-font"><b>Right Ring</b><input class="values" value="${probeBioFields.rightringfinger}" readonly/></a><br>
                                                <a class="color-font"><b>Right Little</b><input class="values" value="${probeBioFields.rightlittlefinger}" readonly/></a>

                                            </ul>
                                        </div></div></div></div></div>
                    </div></div>
                <div class="col-md-6">
                    <div class="card card-primary">
                        <div class="card-header p-2">
                            <h3 class="card-title">CANDIDATE BIOMETRIC SCORE </h3>
                        </div>
                        <div class="card-body box-profile">
                            <div class="user-block">
                                <span class="username"> </span> <span class="description"></span>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-10">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <ul class="list-group list-group-unbordered mb-3" >
                                                <a class="color-font"><b>Left Iris</b><input class="values" value="${CanBioFields.leftiris}" readonly/></a><br>
                                                <a class="color-font"><b>Right Iris</b><input class="values"  value="${CanBioFields.rightiris}" readonly/></a><br>
                                                <u class="color-font" ><b>Left Hand</b></u><br>
                                                <a class="color-font"><b>Left Thumb</b><input class="values"  value="${CanBioFields.leftthumb}" readonly/></a><br>
                                                <a class="color-font"><b>Left Index</b><input class="values"   value="${CanBioFields.leftindexfinger}" readonly/></a><br>
                                                <a class=" color-font"><b>Left Middle</b><input class="values" value="${CanBioFields.leftmiddlefinger}" readonly/></a><br>
                                                <a class="color-font"><b>Left Ring</b><input class="values" value="${CanBioFields.leftringfinger}" readonly/></a><br>
                                                <a class="color-font"><b>Left Little</b><input class="values" value="${CanBioFields.leftlittlefinger}" readonly/></a><br>
                                                <u class="color-font" ><b>Right Hand</b></u><br>
                                                <a class="color-font"><b>Right Thumb</b><input class="values"  value="${CanBioFields.rightthumb}" readonly/></a><br>
                                                <a class="color-font"><b>Right Index</b><input class="values"   value="${CanBioFields.rightindexfinger}" readonly/></a><br>
                                                <a class=" color-font"><b>Right Middle</b><input class="values" value="${CanBioFields.rightmiddlefinger}" readonly/></a><br>
                                                <a class="color-font"><b>Right Ring</b><input class="values" value="${CanBioFields.rightringfinger}" readonly/></a><br>
                                                <a class="color-font"><b>Right Little</b><input class="values" value="${CanBioFields.rightlittlefinger}" readonly/></a>
                                            </ul>
                                        </div></div></div></div>
                        </div></div></div></div>
            <div class="row">

                <div class="col-md-12">
                    <div class="card card-primary" >
                        <div class="card-header p-2">
                            <h3 class="card-title1">PROBE AND CANDIDATE BIOMETRIC MATCHING SCORE  </h3>
                        </div>
                        <div class="card-body box-profile">


                            <div class="user-block">

                                <span class="username"> </span> <span class="description"></span>
                            </div>

                            <div class="row">


                                <div class="col-sm-8">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <ul class="list-group list-group-unbordered mb-3" style="line-height: initial">

                                                    <%--                                                                    <u class="color-font" style="padding-left: 400px;"><b>Full Name</b></u><br>--%>
                                                <a class="color-fonts"><b>FACE</b><input class="values"  value="${fid}" readonly/></a><br>
                                                <a class="color-fonts"><b>IRIS</b><input class="values"   value="${iir}" readonly/></a><br>
                                                <a class=" color-fonts"><b>FINGER</b><input class="values" value="${fir}" readonly/></a><br>
                                                    <%--                                                                    <a class="color-fonts"><b>Suffix</b><input class="values" value="${uiFields.suffix}" readonly/></a><br>--%>
                                                    <%--                                                                    <a class="color-fonts"><b>Gender</b><input class="values" value="${uiFields.gender}" readonly/></a><br>--%>
                                                    <%--                                                                    <u class="color-font" style="padding-left: 400px;"><b>Date Of Birth</b></u><br>--%>
                                                    <%--                                                                    <a class="color-fonts"><b>Month Of Birth</b><input class="values"  value="${uiFields.monthOfBirth}" readonly/></a><br>--%>
                                                    <%--                                                                    <a class="color-fonts"><b>Day Of Birth</b><input class="values"   value="${uiFields.dayOfBirth}" readonly/></a><br>--%>
                                                    <%--                                                                    <a class=" color-fonts"><b>Year Of Birth</b><input class="values" value="${uiFields.yearOfBirth}" readonly/></a>--%>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

                <div class="row">

                    <div class="col-md-12">

                        <div class="card card-primary">
                            <div class="card-header p-2">
                                <h3 class="card-title"><b>MANUAL ADJUDICATION DECISION AND REMARKS OPERATOR 1</b></h3>
<%--                                <div class="col-md-8 float-right">${commentABIS}</div>--%>

                            </div>
                            <input type="text" readonly="readonly" id="commentABIS" value="${commentABIS}" style="padding: 0 7em 2em 0;" />

                           </div>
                    </div></div>



                <div class="row">

                    <div class="col-md-12">

                        <div class="card card-primary">
                            <div class="card-header p-2">
                                <h3 class="card-title"><b>MANUAL ADJUDICATION DECISION AND REMARKS OPERATOR 2</b></h3>
<%--                                <div class="col-md-8 float-right">${comment1ABIS}</div>--%>
                            </div>

                            <input type="text" id="comment1ABIS" readonly="readonly" value="${comment1ABIS}" style="padding: 0 7em 2em 0;" />

                             </div>
                    </div></div>


                <div class="row">
                <!-- /.col -->
                <div class="col-md-12">
                    <div class="card card-primary">
                        <div class="card-header p-2">
                            <h3 class="card-title">MANUAL VERIFICATION REMARKS</h3>
                        </div>
                        <input type="text" id="comment"  style="padding: 0 7em 2em 0;" />
                    </div></div></div></div>

        </form:form>

        <div class="card-footer">
            <div class="spinner-border" role="status" style="display: none; transition: 4s" id="spinner">
                <span class="visually-hidden"></span>
            </div>
            <button type="submit" id="button" class="btn btn-success" onclick="alertNoHit();">No Hit</button>
            <button type="submit" class="btn btn-danger" onclick="return alertHit()">Hit</button>

        </div>



        <!-- The Modal -->
        <div id="myModal" class="modal" style="height: 100%;" ondrop="drop(event)" ondragover="allowDrop(event)">

            <!-- The Close Button -->
            <span class="close">&times;</span>

            <!-- Modal Content (The Image) -->
            <img class="modal-content" id="img01">

            <!-- Modal Caption (Image Text) -->

            <div id="caption1"></div>
            <div id="controls">
                <img src="${pageContext.servletContext.contextPath}/overlap1.png" alt="overlap" id="overlap">
                <img src="${pageContext.servletContext.contextPath}/leftRotate.png" alt="left-rotate" id="lftrot">
                <img src="${pageContext.servletContext.contextPath}/rightRotate.png" alt="rigth-rotate" id="rftrot">
                <img src="${pageContext.servletContext.contextPath}/original.png" alt="Original" id="original" disabled>
                <!--<img src="../../../../balckimage%20(1).png" alt="zoom-plus" id="zoomplus">
                <img src="../../../../balckimage%20(2).png" alt="Zoom-minus" id="zoomminus">    -->
            </div>
        </div>

        <div id="myModal1" class="modal1">

            <!-- The Close Button -->
            <span class="close1">&times;</span>

            <!-- Modal Content (The Image) -->
            <img class="modal-content" id="img02" draggable="true" ondragstart="drag(event)">

            <!-- Modal Caption (Image Text) -->
            <div id="caption2"></div>
        </div>
        <!-- /.container-fluid -->
</section>
<div id="myresult" class="img-zoom-result"></div>

<script>
    imageZoom("myimage", "myimage");
    imageZoom("myimage1", "myresult");
</script>





<script>
    function base64PDFToBlobUrl(base64){
        console.log('base64 ')
        const binStr = atob( base64 );
        const len = binStr.length;
        const arr = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
            arr[ i ] = binStr.charCodeAt( i );
        }
        const blob =  new Blob( [ arr ], { type: 'application/pdf' });
        const url = URL.createObjectURL( blob );
        // console.log('url sanitized : '+ this.sanitizer.bypassSecurityTrustResourceUrl(url+'#toolbar=0'))
        // return  this.sanitizer.bypassSecurityTrustResourceUrl(url+'#toolbar=0');
        return url; // return only this url
    }


    /* 1popup documents pdffunction  */
    //obj = document.getElementById(), then set obj.onclick
    //$(document).ready(function(){
        $("#probepoa").click(function(e){
            e.preventDefault();
            var isInit = true; // indicates if the popup already been initialized.
            var isClosed = false; // indicates the state of the popup
            document.getElementById("popup").style.display = "block";
            document.getElementById('iframe').src = base64PDFToBlobUrl("${reportPDFPOA}")+"#toolbar=0";
            document.getElementById('page').className = "darken";
            document.getElementById('closepdf').onclick = function () {
                if (isInit) {
                    isInit = false;
                    return;
                }
                if (isClosed) {
                    return;
                } //if the popup is closed, do nothing.
                document.getElementById("popup").style.display = "none";
                document.getElementById('page').className = "";
                isClosed = true;
            }
            return false;
        });
   // });
   //  document.getElementById("probepoi").onclick = function (e) {
    $("#probepoi").click(function(e){
        e.preventDefault();
        var isInit = true; // indicates if the popup already been initialized.
        var isClosed = false; // indicates the state of the popup
        document.getElementById("popup").style.display = "block";
        document.getElementById('iframe').src = base64PDFToBlobUrl("${reportPDFPOI}")+"#toolbar=0";
        document.getElementById('page').className = "darken";
        document.getElementById('closepdf').onclick = function () {
            if (isInit) {
                isInit = false;
                return;
            }
            if (isClosed) {
                return;
            } //if the popup is closed, do nothing.
            document.getElementById("popup").style.display = "none";
            document.getElementById('page').className = "";
            isClosed = true;
        }
        return false;
    });

   <%--document.getElementById("probepoa").onclick = function (e) {--%>
   <%--     e.preventDefault();--%>
   <%--     var isInit = true; // indicates if the popup already been initialized.--%>
   <%--     var isClosed = false; // indicates the state of the popup--%>
   <%--     document.getElementById("popup").style.display = "block";--%>
   <%--     document.getElementById('iframe').src = "${reportPDFPOA}#toolbar=0";--%>
   <%--     document.getElementById('page').className = "darken";--%>
   <%--     document.getElementById('closepdf').onclick = function () {--%>
   <%--         if (isInit) {--%>
   <%--             isInit = false;--%>
   <%--             return;--%>
   <%--         }--%>
   <%--         if (isClosed) {--%>
   <%--             return;--%>
   <%--         } //if the popup is closed, do nothing.--%>
   <%--         document.getElementById("popup").style.display = "none";--%>
   <%--         document.getElementById('page').className = "";--%>
   <%--         isClosed = true;--%>
   <%--     }--%>
   <%--     return false;--%>
   <%-- }--%>



    $("#probepoe").click(function(e){
        e.preventDefault();
        var isInit = true; // indicates if the popup already been initialized.
        var isClosed = false; // indicates the state of the popup
        document.getElementById("popup").style.display = "block";
        document.getElementById('iframe').src = "data:image/jpeg;base64,${reportPDFPOE}#toolbar=0";
        document.getElementById('page').className = "darken";
        document.getElementById('closepdf').onclick = function () {
            if (isInit) {
                isInit = false;
                return;
            }
            if (isClosed) {
                return;
            } //if the popup is closed, do nothing.
            document.getElementById("popup").style.display = "none";
            document.getElementById('page').className = "";
            isClosed = true;
        }
        return false;
    });



    $("#candipoi").click(function(e){
            e.preventDefault();
        var isInit = true; // indicates if the popup already been initialized.
        var isClosed = false; // indicates the state of the popup
        document.getElementById("popupc").style.display = "block";
        document.getElementById('iframec').src = base64PDFToBlobUrl("${reportPDFPOICan}")+"#toolbar=0";
        document.getElementById('pagec').className = "darken";
        document.getElementById('closepdf1').onclick = function () {
            if (isInit) {
                isInit = false;
                return;
            }
            if (isClosed) {
                return;
            } //if the popup is closed, do nothing.
            document.getElementById("popupc").style.display = "none";
            document.getElementById('pagec').className = "";
            isClosed = true;
        }
        return false;
    });

    $("#candipoa").click(function(e){
        e.preventDefault();
        var isInit = true; // indicates if the popup already been initialized.
        var isClosed = false; // indicates the state of the popup
        document.getElementById("popupc").style.display = "block";
        document.getElementById('iframec').src = base64PDFToBlobUrl("${reportPDFPOACan}")+"#toolbar=0";
        document.getElementById('pagec').className = "darken";
        document.getElementById('closepdf1').onclick = function () {
            if (isInit) {
                isInit = false;
                return;
            }
            if (isClosed) {
                return;
            } //if the popup is closed, do nothing.
            document.getElementById("popupc").style.display = "none";
            document.getElementById('pagec').className = "";
            isClosed = true;
        }
        return false;
    });
    $("#candipoe").click(function(e){
        e.preventDefault();
        var isInit = true; // indicates if the popup already been initialized.
        var isClosed = false; // indicates the state of the popup
        document.getElementById("popupc").style.display = "block";
        document.getElementById('iframec').src = "data:image/jpeg;base64,${reportPDFPOECan}#toolbar=0";
        document.getElementById('pagec').className = "darken";
        document.getElementById('closepdf1').onclick = function () {
            if (isInit) {
                isInit = false;
                return;
            }
            if (isClosed) {
                return;
            } //if the popup is closed, do nothing.
            document.getElementById("popupc").style.display = "none";
            document.getElementById('pagec').className = "";
            isClosed = true;
        }
        return false;
    });
    // Get the modal

    function displaymodal(id,nme) {
        var modal = document.getElementById("myModal");
        var modal1 = document.getElementById("myModal1");
        // Get the image and insert it inside the modal - use its "alt" text as a caption
        var img = document.getElementById(id);
        var modalImg = document.getElementById("img01");
        var captionText = document.getElementById("caption1");
        var img1 = document.getElementById(nme);
        var modalImg1 = document.getElementById("img02");
        var captionText1 = document.getElementById("caption2");

        //img.onclick = function () { single click to open model popup
        modal.style.display = "block";
        modalImg.src = img.src;
        modalImg.onmouseover = imageZoom("img01", "myresult");
        captionText.innerHTML = this.alt;
        modal1.style.display = "block";
        modalImg1.src = img1.src;
        modalImg1.onmouseover = imageZoom("img02", "myresult");
        captionText1.innerHTML = this.alt;
        //}
        // Get the <span> element that closes the modal
        modalImg1.style.top= window.center;
        modalImg1.style.display = "block";
        modalImg1.onmousedown = function (event) {
            modalImg1.style.position = 'absolute';
            modalImg1.style.zIndex = 1;
            //Negative="img1";
            document.body.append(modalImg1);
            modalImg1.style.width = 32 + '%';
            modalImg1.style.height = 52.5  + '%';
            function moveAt(pageX, pageY) {
                modalImg1.style.left = pageX - modalImg1.offsetWidth / 2 + 'px';
                modalImg1.style.top = pageY - modalImg1.offsetHeight / 2 + 'px';
            }

            moveAt(event.pageX, event.pageY);

            function onMouseMove(event) {
                moveAt(event.pageX, event.pageY);
            }

            document.addEventListener('mousemove', onMouseMove);
            modalImg1.onmouseup = function () {
                document.removeEventListener('mousemove', onMouseMove);
                modalImg1.onmouseup = null;
            };
        };
        modalImg1.ondragstart = function () {
            return false;
        };

        var span = document.getElementsByClassName("close")[0];
        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
            modal1.style.display = "none";
            modalImg1.style.display = "none";
            document.getElementById("img01").style.transform = "rotate(0deg)";
            document.getElementById("img02").style.transform = "rotate(0deg)";
            modal1.append(modalImg1);
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.filter = "opacity(100%)";
            modalImg.style.filter = "opacity(100%)";
        }
        var span1 = document.getElementsByClassName("close1")[0];
        // When the user clicks on <span> (x), close the modal
        span1.onclick = function () {
            modal.style.display = "none";
            modal1.style.display = "none";
            modalImg1.style.display = "none";
            document.getElementById("img01").style.transform = "rotate(0deg)";
            document.getElementById("img02").style.transform = "rotate(0deg)";
            modal1.append(modalImg1);
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.filter = "opacity(100%)";
            modalImg.style.filter = "opacity(100%)";
        }
        var angle = 0;
        var rftbtn=document.getElementById("rftrot");
        rftbtn.onclick = function() {
            angle = angle + 5;
            if (angle > 360) {
                angle = 5;
            }
            document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
            document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
        }
        var lftbtn = document.getElementById("lftrot");
        lftbtn.onclick = function() {
            angle = angle - 5;
            if (angle < 0) {
                anlge = -5;
            }
            document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
            document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
        }
        var overlap1= document.getElementById("overlap");
        overlap1.onclick = function() {
            document.getElementById("img02").style.filter = "contrast(200%)";
            document.getElementById("img02").style.filter = "opacity(50%)";
            document.getElementById("img01").style.filter = "invert(200%)";
            modalImg1.style.position = "absolute";
            modal.append(modalImg1);
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.width = 80 +'%';
            modalImg1.style.height = 52.4 + '%';
        }
        var original1 = document.getElementById("original");
        original1.onclick = function() {
            modal1.append(modalImg1);
            modalImg1.style.top = window.center;
            modalImg1.style.width = 80 + '%';
            modalImg1.style.height = 52.4 + '%';
            modalImg1.style.width = 80 +'%';
            modalImg1.style.height = 52.4 + '%';
            modalImg1.style.filter = "opacity(100%)";
            modalImg.style.filter = "opacity(100%)";
        }
    }

    function displaymodal1(id,nme) {
        var modal1 = document.getElementById("myModal1");
        var modal2 = document.getElementById("myModal");
        // Get the image and insert it inside the modal - use its "alt" text as a caption
        var img1 = document.getElementById(id);
        var modalImg1 = document.getElementById("img02");
        var captionText1 = document.getElementById("caption2");
        var img2 = document.getElementById(nme);
        var modalImg2 = document.getElementById("img01");
        var captionText2 = document.getElementById("caption1");
        //img1.onclick = function () {
        modal1.style.display = "block";
        modalImg1.src = img1.src;
        modalImg1.onmouseover = imageZoom("img02", "myresult"); //checkAngular
        captionText1.innerHTML = this.alt;
        modal2.style.display = "block";
        modalImg2.src = img2.src;
        modalImg2.onmouseover = imageZoom("img01", "myresult"); //checkAngular
        captionText2.innerHTML = this.alt;
        //}
        modalImg1.style.top = window.center;
        modalImg1.style.left = window.center;
        modalImg1.style.display = "block";
        modalImg1.onmousedown = function (event) {
            modalImg1.style.position = 'absolute';
            modalImg1.style.zIndex = 1;
            //Negative="img1";
            document.body.append(modalImg1);
            modalImg1.style.width = 32 + '%';
            modalImg1.style.height = 52.5  + '%';

            function moveAt(pageX, pageY) {
                modalImg1.style.left = pageX - modalImg1.offsetWidth / 2 + 'px';
                modalImg1.style.top = pageY - modalImg1.offsetHeight / 2 + 'px';
            }

            moveAt(event.pageX, event.pageY);

            function onMouseMove(event) {
                moveAt(event.pageX, event.pageY);
            }

            document.addEventListener('mousemove', onMouseMove);
            modalImg1.onmouseup = function () {
                document.removeEventListener('mousemove', onMouseMove);
                modalImg1.onmouseup = null;
            };
        };
        modalImg1.ondragstart = function () {
            return false;
        };
        // Get the <span> element that closes the modal
        var span1 = document.getElementsByClassName("close1")[0];

        // When the user clicks on <span> (x), close the modal
        span1.onclick = function () {
            modal2.style.display = "none";
            modal1.style.display = "none";
            modalImg1.style.display = "none";
            document.getElementById("img01").style.transform = "rotate(0deg)";
            document.getElementById("img02").style.transform = "rotate(0deg)";
            modal1.append(modalImg1);
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.filter = "opacity(100%)";
            modalImg2.style.filter = "opacity(100%)";
            modalImg1.style.width = 80 +'%';
            modalImg1.style.height = 52.4 + '%';
            modalImg2.style.width = 80 +'%';
            modalImg2.style.height = 60 + '%';
        }
        var span = document.getElementsByClassName("close")[0];


        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal2.style.display = "none";
            modal1.style.display = "none";
            modalImg1.style.display = "none";
            document.getElementById("img01").style.transform = "rotate(0deg)";
            document.getElementById("img02").style.transform = "rotate(0deg)";
            modal1.append(modalImg1);
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.filter = "opacity(100%)";
            modalImg2.style.filter = "opacity(100%)";
            modalImg1.style.width = 80 +'%';
            modalImg1.style.height = 52.4 + '%';
            modalImg2.style.width = 80 +'%';
            modalImg2.style.height = 60 + '%';
        }

        var angle = 0;
        var rftbtn=document.getElementById("rftrot");
        rftbtn.onclick = function() {
            angle = angle + 5;
            if (angle > 360) {
                angle = 5;
            }
            document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
            document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
        }
        var lftbtn = document.getElementById("lftrot");
        lftbtn.onclick = function() {
            angle = angle - 5;
            if (angle < 0) {
                anlge = -5;
            }
            document.getElementById("img01").style.transform = "rotate(" + angle + "deg)";
            document.getElementById("img02").style.transform = "rotate(" + angle + "deg)";
        }
        var overlap1= document.getElementById("overlap");
        overlap1.onclick = function() {
            document.getElementById("img02").style.filter = "contrast(200%)";
            document.getElementById("img02").style.filter = "opacity(50%)";
            document.getElementById("img01").style.filter = "invert(200%)";
            modalImg1.style.position = "absolute";
            modal2.append(modalImg1);
            modalImg2.style.top = 10 + '%';
            modalImg2.style.left = 0 + '%';
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.width = 80 +'%';
            modalImg1.style.height = 52.4 + '%';
            modalImg2.style.width = 80 +'%';
            modalImg2.style.height = 60 + '%';
            /*var zoomplus1= document.getElementById("zoomplus");
            zoomplus1.onclick = function(){
                var curwidth1= modalImg1.clientWidth ;
                var curheight1=modalImg1.clientHeight;
                var curwidth2=modalImg2.clientWidth;
                var curheight2=modalImg2.clientHeight;
                if(curwidth1 > 600 ){
                    alert("U reached a limit");
                }
                else{
                    modalImg1.style.width=(curwidth1 + 10) +'px';
                    modalImg1.style.height= ( curheight1 + 10) +'px';
                    modalImg2.style.width=(curwidth2 + 10) +'px';
                    modalImg2.style.height= ( curheight2 + 10) +'px';
                }
            }*/
        }
        var original1 = document.getElementById("original");
        original1.onclick = function() {
            modal1.append(modalImg1);
            //modalImg1.style.top = window.center;
            modalImg1.style.top = 21.4 + '%';
            modalImg1.style.left = 10 + '%';
            modalImg1.style.width = 80 +'%';
            modalImg1.style.height = 52.4 + '%';
            modalImg1.style.filter = "opacity(100%)";
            modalImg2.style.filter = "opacity(100%)";
        }
    }

</script>

<!-- /.content -->
</html>
