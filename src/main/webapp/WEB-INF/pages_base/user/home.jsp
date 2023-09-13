
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" type="text/css" href="css/style.css"/>

<style type="text/css">

:root {
  --p-color: #2670b6;
  --s-color:#15159c;
 --pbg-color:#fff;
  --sbg-color:#ff0000;
  --a-color:#fff;
  --c-color::ffffff;


}

.body {
   font-family="Trebuchet MS", sans-serif;
    position: relative;
    background: var(--textColor);
    font-size: var(--fontSize);
    color: var(--textBlueColor);
    overflow-x: hidden;
    top:0 !important;
}

a {
 font-family="Trebuchet MS", sans-serif;
 color: var(--a-color) !important;
  text-decoration: none;
  background-color: transparent;
  -webkit-text-decoration-skip: objects;
}
.footer--bottom--logo{
  width: 100%;
  max-width: 350px;
}
.footer-logo{
  display: flex;
  justify-content: center;
  background-color: lightgray;

}

.mat-toolbar-row,
.mat-toolbar-single-row{
  display: flex;
  box-sizing: border-box;
  padding: 0 0px;
  width: 100%;
  flex-direction: row;
  align-items: center;
  white-space: nowrap;
  height: 140px;
}
/*  body container css*/
.wrapper {
  display: flex;
  display: -webkit-box; /* OLD - iOS 6-, Safari 3.1-6, BB7 */
  display: -ms-flexbox; /* TWEENER - IE 10 */
  display: -webkit-flex; /* NEW - Safari 6.1+. iOS 7.1+, BB10 */
  flex-direction: column;
  align-items: center;
  background-color: lightgrey !important;
}
.res-head{
  width: 100%;
}
.logoname {
  padding-bottom: 72px;
}
.space1
{
  padding-bottom: 35px;
  background-color: lightgrey;

}
.space{
  padding-bottom: 35px;

}
.stepOneheading--bottom{
  padding-bottom: 2%;
  text-align: center;
}
.stepOneHeading {
  font-size: 18px;
  /* text-align: center;*/
  font-weight: 100;
  text-align: justify;
}

.login-heading
{
  font-size: var(--heading--font-size);
  text-align: center;
  font-weight: 300;
  width: 100%;
  padding: 1rem .5rem;
  background-color: #1f4380;
}
/*.mt-4,
.my-4 {
  margin-top: 1.5rem !important;
}*/
.subHeading {
  font-size: 18px;
  text-align: center;
  font-weight: 500;
}
.blueText {
  color: #1f4380;
}
.heading {
  font-size: var(--heading--font-size);
  text-align: center;
  font-weight: 500;
  color:white;
}
.heading-color--yellow {
  color:#FCC810;
}

.curve {
  background: white;
  border-radius: 20px;
}
:root {
  --font-size : 15px;
  --primary--color : #ffffff;
  --primary--light--color : #ffffff;
  --secondary--color : #ffffff;
  --secondary--light--color : #ffffff;
  --primary--background--color : #1f4380;
  --font-h1 : calc(--font-size + 5px);
  --heading--font-size : 1.5rem;
  --white-color:white;

}
/*  header container css*/


.header--container{
  display: flex;
  box-sizing: border-box;
  padding: 0 0px;
  width: 100%;
  flex-direction: row;
  align-items: center;
  white-space: nowrap;
  height: 140px;
}
.header{


  background-color:#1f4380;
}
.header--color{
  top: 0;
  position: fixed;
  z-index: 999;
  background-color: #1f4380;
  border-color: #707070;
  border-width: 1px;
  overflow-x: hidden;
  width: 100%;
  height: 68px;
  padding: 0px 2vw;
}

.log-img{
  height: 40px;
}

.custom-nav{
  padding: 0rem 1rem !important;
}


.nav-border > ul > li:first-child {
  border-left:  1px solid gray;
}
.nav-border > ul > li{
border-right:  1px solid gray;
}
.middle-header{
  background: white;
}

header--logo.bottom-banner {
  height: 140px;
  background: linear-gradient(to right,#226da5 0%,#2872c1 18%,#163d6a 100%);
}

.top-padding-5{
  padding: 5px 0px !important;
}
.h-140{
  height: 130px;
}

.header--logo1 {
  /*// vertical-align: middle;
   cursor: pointer;*/
  height: 110px;
  width: 461px;
  margin-left: 15px;


}
.mat-container--header{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.mat-list-item {
  font-family: 'Gotham', sans-serif;
}

.mat-icon-button {
  color: var(--primary--background--color);
  background-color:var(--white-color);
  border-radius: 0px;
  margin: 0px 5px 0px 0px;
}


.mat-container--header{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.mat-sidenav-container {
  background: white;
}
.mat-sidenav {
  width: 180px;
}
.main {
  position: relative;
  margin-top: 68px;
  min-height: 5rem;
  overflow: hidden;
}
.mat-toolbar {
  top: 0;
  position: fixed;
  z-index: 999;
  background-color: #1f4380;
  border-color: #707070;
  border-width: 1px;

}
.mat-toolbar-row, .mat-toolbar-single-row {
  display: flex;
  box-sizing: border-box;
  padding: 0 0px;
  width: 100%;
  flex-direction: row;
  align-items: center;
  white-space: nowrap;
  height: 140px;

}

.mat-toolbar-old {
  top: 0;
  position: fixed;
  z-index: 999;
  background-color: #1f4380;
  border-color: #707070;
  border-width: 1px;
  overflow-x: hidden;
  width: 100%;
  height: 68px;
  padding: 0px 2vw;
}


.header--logo {
  vertical-align: middle;
  cursor: pointer;
  /* UX enhancement */
  /* padding-left: 4%;*/
  /*background-color: white;*/
  /* UX enhancement end */
}
.header--logo-old {
  vertical-align: middle;
  cursor: pointer;
  /* UX enhancement */
  /* padding-left: 4%;*/
  background-color: white;
  /* UX enhancement end */
}




.wrapper[_ngcontent-c8] {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: lightgrey;
}
.spacer {
  flex: 1 1 auto;
}
.list-item > .mat-list-item {
  color: #fff !important;
  font-size: 20px !important;
  line-height: 1.42857143 !important;
  text-decoration: none;
  text-transform: uppercase;
}
.list-item {
  display: inline-flex;
  padding: 0px;
  margin-left: 25px;
}
.icon {
  color: #fff !important;
}
.sidenav-list > .mat-list-item {
  color: black;
  text-transform: uppercase;
  font-size: 12px !important;
  line-height: 1.42857143 !important;
  letter-spacing: 2px;
}

.header__actions {
  text-transform: none !important;
}

@media (min-width: 670px) {
  .sidenav-button {
    display: none;
  }
}
@media (max-width: 670px) {
  .list-item {
    display: none;
  }
}




/*
  ##Device = Desktops
  ##Screen = 1281px to higher resolution desktops
*/

@media (min-width: 1281px) {

  /* CSS */

}

/*
  ##Device = Laptops, Desktops
  ##Screen = B/w 1025px to 1280px
*/

@media (min-width: 1025px) and (max-width: 1280px) {

  /* CSS */

}

/*
  ##Device = Tablets, Ipads (portrait)
  ##Screen = B/w 768px to 1024px
*/

@media (min-width: 768px) and (max-width: 1024px) {

  /* CSS */

  .mat-toolbar{
    max-width: calc(100% - 3%);
  }


}



/*
  ##Device = Tablets, Ipads (landscape)
  ##Screen = B/w 768px to 1024px
*/

@media (min-width: 768px) and (max-width: 1024px) and (orientation: landscape) {

  /* CSS */

}

/*
  ##Device = Low Resolution Tablets, Mobiles (Landscape)
  ##Screen = B/w 481px to 767px
*/

@media (min-width: 481px) and (max-width: 767px) {

  /* CSS */

}

/*
  ##Device = Most of the Smartphones Mobiles (Portrait)
  ##Screen = B/w 320px to 479px
*/

@media (min-width: 320px) and (max-width: 480px) {

  /* CSS */

}

@media(max-width : 670px){



  .header--logo{
    width: 120px;
    height: 30px;
    margin-top: 0px;
    margin-left: 10px;
  }

  .mat-container--header{
    background-color: #1f4380;
    height: 50px;
  }





}

.h-140 > a > img{
  height: 100%;width: 100%;
  object-fit: contain;
}

.header-middle-content{
  padding: 0px 10px ;
 }

.header-slider{
  background:#465ab3;
  height: 460px;
}

.carousel-item img{
  height: 100%;
  width: 100%;
  object-fit: cover;
}
.slider-padding{
  padding: 5px 5px;
}

.header-middle-content h6,.header-middle-content  h2{


padding: 4px 2px;
color:white;
height: 10%;
  width: 100%;
}
.img-100{height: 10%;
  width: 10%;
  object-fit: cover;}

  .main-content{
    padding: 80px 0px;
  }

  .jumbotron {
    padding: 1rem 1rem !important;
}

.content-title{
 color : var(--s-color);
}

.top-search{
  border-radius: 0px !important;
}

.nav-item > a {
  padding-left: 10px !important;
  padding-right: 10px !important;
  font-weight: italic;
}

.top-input-form{
  padding: 0px 0px 0px 10px;
}
.top-search-input{
  height: 30px !important;
}

.header-middle-content >h4{
  font-style: bold;
}
.top-hr{
  margin-top: 2px !important;
  margin-bottom: 2px !important;
  border-top: 2px solid white;
}
.header-background {
  display: flex;
  box-sizing: border-box;
  padding: 0 0px;
  width: 100%;
  flex-direction: row;
  align-items: center;
  white-space: nowrap;
  height: 140px;
  background-color: #1f4380;
}


.dropdown-item {
  color: black !important;
  display: block;
  width: 100%;
  padding: .25rem 1.5rem;
  clear: both;
  font-weight: 400;
  color: #212529;
  text-align: inherit;
  black-space: nowrap;
  hover="relative";

  border: 0;
  margin: 3px 0px;
  position: relative;


}


.dropdown-item1 {
  display: block;
  width: 100%;
  padding: .25rem 1.5rem;
  clear: both;
  font-weight: 400;
  color: #212529;
  text-align: inherit;
  black-space: nowrap;
  color: white !important;
  border: 0;
  margin: 3px 0px;
  position: relative;


}





.test {
  display: block;
  width: 100%;
  padding: .25rem 1.5rem;
  clear: both;
  font-weight: 400;
  color: #212529;
  text-align: center;
  black-space: nowrap;
  background-color: black !important;
  border: 0;
  margin: 3px 0px;
  position: relative;
}
.dropbtn {
  background-color: #4CAF50;
  color: black;
  padding: 16px;
  font-size: 16px;
  border: none;
}

/* The container <div> - needed to position the dropdown content */
.dropdown {
  position: relative;
  display: inline-block;
}

/* Dropdown Content (Hidden by Default) */
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

/* Links inside the dropdown */
.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}
a.ex1:hover, a.
/* Change color of dropdown links on hover */
.dropdown-content a:hover {background-color: #ddd;}

/* Show the dropdown menu on hover */
.dropdown:hover .dropdown-content {display: block;}

/* Change the background color of the dropdown button when the dropdown content is shown */
.dropdown:hover .dropbtn {color: black;}



.dropdown-item:focus, .dropdown-item:hover {
    color: gray !important;
  text-decoration: none;
  background-color:black!important;
}

.ti-border{
font-family: "Times New Roman", Times, serif;"
  border-bottom: 2px solid white;
   width: 22%;
}
.dropdown-menu {
  position: inherit;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: block;
  float: left;
  min-width: 10rem;
  padding:  0 !important;
  margin: .125rem 0 0;
  font-size: 1rem;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0,0,0,.15);
  border-radius: .25rem;
  hover:"right";

}

.dropdown-menu1 {
  position: inherit;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: block;
  float: left;
  min-width: 10rem;
  padding:  0 !important;
  margin: .125rem 0 0;
  font-size: 1rem;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0,0,0,.15);
  border-radius: .25rem;
  transform: translate3d(214px, -126px, 0px);

  will-change: transform;

}

.dropdown-menu2 {
  position: inherit;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: block;
  float: left;
  min-width: 10rem;
  padding:  0 !important;
  margin: .125rem 0 0;
  font-size: 1rem;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0,0,0,.15);
  border-radius: .25rem;
  transform: translate3d(214px, -42px, 0px);
  will-change: transform;

}
@media (min-width: 992px){
	.dropdown-menu .dropdown-toggle:after{
		border-top: .3em solid transparent;
	    border-right: 0;
	    border-bottom: .3em solid transparent;
      border-left: .3em solid;
      width: auto;
      height: auto;
	}
	.dropdown-menu .dropdown-menu{
		margin-left:0; margin-right: 0;
	}
	.dropdown-menu li{
		position: relative;
	}
	.nav-item .submenu{
		display: none;
		position: absolute;
		left:100%; top:-7px;
	}
	.nav-item .submenu-left{
		right:100%; left:auto;
	}
	.dropdown-menu > li:hover{ background-color: #f1f1f1 }
	.dropdown-menu > li:hover > .submenu{
		display: block;
	}
}
/* bootstab*/


/*end*/
footer-logo-space.main-footer {
  display: flex;
  justify-content: center;
  font-family: Robototaglib;
  font-size: 12px;
  padding-top: 10px;
  padding-bottom: 0px;
  position: fixed;
  bottom: -9px;
  width: 100%;
  z-index: -1;
  margin-bottom: 2px;
  background: white;
}

.footer-logo{
  display: flex;
  justify-content: center;
  background-color: lightgray;

}

a:link, a:visited {
  color: white;
  /*padding: 15px 25px;*/
  text-align: center;
  text-decoration: none;
  display: inline-block;
}
.nomargin{
  margin:0px !important;
}

.row{
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  margin-right: 15px !important;
  margin-left: 15px!important;

}

.footer-logo-space{
  max-height: 75px;
  background-color: lightgray
}
.pre-reg-version{
  font-size: 12px;
  /*font-family: "Roboto", sans-serif;*/
  font-weight: 100;
  margin-left: -100px;
  color:white;
}
.text-background{
  background-color: lightgray;
}
.version-txt{
  font-size: 20px;
  /*font-family: "Roboto", sans-serif;*/
  font-weight: 100;
  display: block;
  padding-bottom: 20px;
  padding-top: 20px;
  color:white;
}
.space{
  width:100%;
  height: 20px;
  padding: 0px 0vw;
  background-color: #F1F1F1;

}

.footer--container{
  background-image: url(https://register.philsys.gov.ph/pre-registration-ui/pre-registration/assets/img/footer.png) ;
  background-repeat :no-repeat;
  background-size: cover;
}

.nomargin{
  margin:0px !important;
}

.padding-5{
  padding-bottom: 1rem;
}

.padding-top{
  padding-top:20px;
}

.footer-logo-center{
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.footer-logo_flex{
  vertical-align: middle;
  cursor: pointer;
}

@media (max-width: 500px) {
  .version-txt{
    font-size: 8px;
    /*font-family: "Roboto", sans-serif;*/
    font-weight: 50;
    display: block;
    padding-bottom: -200px;

  }
}

/*
  ##Device = Desktops
  ##Screen = 1281px to higher resolution desktops
*/

@media (min-width: 1281px) {
  /* CSS */
}
/*
  ##Device = Laptops, Desktops
  ##Screen = B/w 1025px to 1280px
*/
@media (min-width: 1025px) and (max-width: 1280px) {
  /* CSS */
}

/*
  ##Device = Tablets, Ipads (portrait)
  ##Screen = B/w 768px to 1024px
*/

@media (min-width: 768px) and (max-width: 1024px) {
  /* CSS */
  .version-txt[_ngcontent-c8] {
    font-size: 13px;
    font-weight: 200;
    display: block;
    padding-bottom: 20px;
    padding-top: 20px;
    color: white;
  }

  .footer-logo_flex{
    max-width: 150px;
  }
}

/*
  ##Device = Tablets, Ipads (landscape)
  ##Screen = B/w 768px to 1024px
*/

@media (min-width: 768px) and (max-width: 1024px) and (orientation: landscape) {
  /* CSS */
}

/*
  ##Device = Low Resolution Tablets, Mobiles (Landscape)
  ##Screen = B/w 481px to 767px
*/

@media (min-width: 481px) and (max-width: 767px) {
  /* CSS */
  .version-txt[_ngcontent-c8] {
    font-size: 8px;
    font-weight: 50;
    display: block;
    text-align: center;
    padding-bottom: 6px;
    padding-top: 6px;
  }

}

/*
  ##Device = Most of the Smartphones Mobiles (Portrait)
  ##Screen = B/w 320px to 479px
*/

@media (min-width: 320px) and (max-width: 480px) {
  /* CSS */
  .version-txt[_ngcontent-c8] {
    font-size: 8px;
    font-weight: 50;
    display: block;
    text-align: center;
    padding-bottom: 6px;
    padding-top: 6px;
  }

}

.footer-background{
  background :#e9e9eb;
   text-align: center;
   color:wight;
}

.textcenter{
 font-family: 'Gotham', sans-serif;
   text-align: center;
   color:wight;
 }


.footer-background > .container{padding-top: 25px;}
.w-150{
  width: 150px;
  background-color:#1f4380;
  color:white;
   border: 3px solid #1f4380;
   border-radius: 5px;
    padding:10px;
  cursor:pointer;
  min-height: 50px;
}
button-p10{
  padding:10px;
  cursor:pointer;
  min-height: 50px;
  border: 3px solid #1f4380;
  box-shadow: 0 3px 1px -2px rgba(0,0,0,.2),0 2px 2px 0 rgba(0,0,0,.14),0 1px 5px 0 rgba(0,0,0,.12);
  border-radius: 5px;
}
.box-text{
border-color:blue;
}
 .page{
 hight:60%;
 width:100% ;
 }
 .text-color{
 color:#1f4380;
font-size: 20px;
font-family: 'Gotham', sans-serif;
 }
</style>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
<link rel="stylesheet" href="../assets/main.css" type="text/css" />
<link rel"="stylesheet"href="/assets/Trebuchet.woff2"type="text/css" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://ajax.googlea`c  ``pis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>



<link rel="stylesheet" type="text/css" id="theme"
	href="css/theme-default.css" />
<link href="js/plugins/sweetalert/sweetalert.css" rel="stylesheet" />

</head>

<body>
<script type="text/javascript">
window.onload = function(){
var message = document.getElementById("message").value;

if( message != null && message != "" && message != "/" ){
alert(message);
}
}


function fun() {
alert("mani");

    $.ajax({
        url : 'batch',
        method : 'POST',
        async : false,
        complete : function(data) {
            console.log(data.responseText);
        }
    });



}
</script>

<div class="header-background ">

       <!-- <div  class="row">
            <div class="col-md-4 ">-->
                <div class="mat-container--header">
                    <a>
                        <img src="../assets/footer-headr.png" class="header--logo1 header--logo">

                    </a>
                </div>



</div>


<div class="space1">
</div>

<form  action="scan" >
<br></br>
<div class="container-fluid">
  <div class="row ">
  <div class="col-md-2">
  </div>
  <div class="col-md-2 text-color">
  QR Scan Data:
  </div>
  <div class="col-md-6 page">


  <input type="hidden" id="message" name="message" value=${message} />
 <textarea row="9" cols="70" name="username" id="username" >
 </textarea>

  </div>

  <div class="col-md-2">
  </div>
  </div>
  </div>

 <br><br>
 <br><br>
 <div class="container-fluid">
  <div class="row ">
  <div class="col-md-3">
  </div>
  <div class="col-md-2">

  </div>
  <div class="col-md-4">
   <button   class="w-150 button-p10" type="submit" >
    QR Scan-Device
</button>
  </div>
  <div class="col-md-3">

  </div>
  </div>
  </div>

</form>
<form  action="batch" method = "POST">
 <div class="container-fluid">
  <div class="row ">
  <div class="col-md-3">
  </div>
  <div class="col-md-2">

  </div>
  <div class="col-md-4">
   <button   class="w-150 button-p10" id="scanner"  >
    QR Scan-Cam
</button>
  </div>
  <div class="col-md-3">

  </div>
  </div>
  </div>

</form>
<div class="">
    <div class="space1">

    </div>
    <div class="footer-logo">

        <img class="footer-logo-space" src="../assets/idnatin.png"
        >

    </div>


    <div class="footer--container padding-5">


        <div class="row nomargin">
            <div class=" col-md-2 col-sm-12 col-lg-2" style=" padding-left: 150px;">
                <div class="mx-auto">
                    <div class="version-txt">
                        <a href="https://www.philsys.gov.ph/philsys-briefer/" target="_blank">
                        <!--<a href="http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/philsys-briefer/" target="_blank">-->
                            ABOUT US</a></div>
                </div>
                <!--<hr class="clearfix w-100 d-md-none">-->
            </div>

            <div class=" col-md-2 col-sm-12 col-lg-2" style=" padding-left: 70px;">
                <div class="mx-auto">
                    <div class="version-txt">  <!--<a href="http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/contact-us/" target="_blank">-->
                        <a href="https://www.philsys.gov.ph/contact-us/" target="_blank">CONTACT US</a></div>
                </div>
                <!-- <hr class="clearfix w-100 d-md-none">-->
            </div>

            <div class=" col-md-1 col-sm-12 col-lg-1" style=" padding-left: 30px;">
                <div class="mx-auto">
                    <div class="version-txt">  <!--<a href=" http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/faq/" target="_blank">-->
                        <a href="https://www.philsys.gov.ph/faq/" target="_blank">FAQS</a></div>

                </div>
                <!--  <hr class="clearfix w-100 d-md-none">-->
            </div>

            <div class=" col-md-1 col-sm-12 col-lg-1" style=" padding-left: 50px;">
                <div class="mx-auto">
                    <div class="version-txt">  <!--<a href="http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/site-map/" target="_blank">-->
                        <a href="https://www.philsys.gov.ph/site-map/" target="_blank">SITEMAP</a></div>
                </div>
                <!--  <hr class="clearfix w-100 d-md-none">-->
            </div>

            <div class=" col-md-2 col-sm-12 col-lg-2" style=" padding-left: 80px;">
                <div class="mx-auto">
                    <div class="version-txt">  <!--<a href="http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/privacy-policy/" target="_blank">-->
                        <a href="https://www.philsys.gov.ph/privacy-policy/" target="_blank">PRIVACY POLICY</a></div>
                </div>
                <!-- <hr class="clearfix w-100 d-md-none">-->
            </div>

            <div class=" col-md-2 col-sm-12 col-lg-2" style=" padding-left: 40px;">
                <div class="mx-auto">
                    <div class="version-txt"> <!-- <a href="http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/terms-of-use/" target="_blank">-->
                        <a href="https://www.philsys.gov.ph/terms-of-use/" target="_blank">TERMS OF USE</a></div>
                </div>
                <!-- <hr class="clearfix w-100 d-md-none">-->
            </div>

            <div class=" col-md-2 col-sm-12 col-lg-2" style=" padding-left: 10px;">
                <div class="mx-auto">

                    <div class="version-txt"> <!--<a href="http://psa-webportal-alb-284888328.ap-southeast-1.elb.amazonaws.com/feedback/" target="_blank">-->
                        <a href="https://www.philsys.gov.ph/feedback/" target="_blank">FEEDBACK</a></div>
                </div>
                <!--  <hr class="clearfix w-100 d-md-none">-->
            </div>
        </div>
        <div class="footer--bottom" >
            <div class="row nomargin">
                <div class="col-md-3 col-sm-12 col-lg-3" style=" padding-left: 25px;">
                    <div class="mx-auto" style="margin-right: auto">
                        <h6 class="font-weight-bold text-uppercase mt-3 mb-4 padding-top"
                            style="color: white">
                            REPUBLIC OF THE PHILIPPINES
                        </h6>
                        <li style="color: white;list-style: none;font-size: small">
                            All content is in the public domain unless otherwise stated.
                        </li>
                    </div>
                    <!--  <hr class="clearfix w-100 d-md-none">-->
                </div>


                <div class=" col-md-3 col-sm-12 col-lg-3">
                    <div class="mx-auto">
                        <h6 class="font-weight-bold text-uppercase mt-3 mb-4 padding-top"
                            style="color: white">
                            ABOUT GOV PH
                        </h6>
                        <!--                <ul style="color: white;list-style: none">-->
                        <li style="list-style: none;color: white;font-size: small">
                            Learn more about the Philippine government,its structure,
                            how government works and the people behind it.
                        </li>

                        <!--                </ul>-->

                        <h6 class="font-weight-bold text-uppercase mt-3 mb-4 padding-top"
                            style="color: white">
                            <!-- GOV PH-->
                            <a href="http://www.gov.ph/" target="_blank">GOV.PH</a>
                        </h6>
                        <!--                <ul style="color: white;list-style: none">-->
                        <!--<li style="list-style: none;color: white;font-size: small">Open Data portal</li>-->


                        <li style="list-style: none;color: white;font-size: small"> <a href="https://data.gov.ph/" target="_blank">Open Data Portal</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://www.officialgazette.gov.ph" target="_blank">Official Gazette</a></li>
                        <!--                </ul>-->
                    </div>
                    <hr class="clearfix w-100 d-md-none">
                </div>

                <div class=" col-md-3 col-sm-12 col-lg-3">
                    <div class="mx-auto">
                        <h6 class="font-weight-bold text-uppercase mt-3 mb-4 padding-top"
                            style="color: white">
                            GOVERNMENT LINKS
                        </h6>

                        <li style="list-style: none;color: white;font-size: small"><a href="https://op-proper.gov.ph/" target="_blank" >Office of the President</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://ovp.gov.ph/" target="_blank">Office of the Vice President</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://www.senate.gov.ph/" target="_blank">Senate of the Philippines</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://www.congress.gov.ph/" target="_blank">House of Representatives</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://sc.judiciary.gov.ph/" target="_blank">Supreme Court</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://ca.judiciary.gov.ph/" target="_blank">Court of Appeals</a></li>
                        <li style="list-style: none;color: white;font-size: small"><a href="http://sb.judiciary.gov.ph/"target="_blank">Sandiganbayan</a></li>

                    </div>
                    <!-- <hr class="clearfix w-100 d-md-none">-->
                </div>

                <div class=" col-md-3 col-sm-12 col-lg-3 footer-logo-center">
                    <div class="mx-auto" >

                        <a ><img src="../assets/footer-headr.png" style="max-height: 80px;margin-left: -160px;" /></a>
                        <!-- <a _ngcontent-c1="">
                             <img _ngcontent-c1="" class="footer-logo_flex" src="assets/img/1 PSA LOGO.png"
                                  style="max-height: 70px;background-color:#1f4380 ">
                             <img _ngcontent-c1="" class="footer-logo_flex" src="assets/img/finger.png"
                                  style="max-height: 70px;background-color:#1f4380 "></a>
     -->
                        <p>

                            <span class="pre-reg-version"><strong> Version: 1.0</strong></span>
                        </p>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>



</body>

</html>
