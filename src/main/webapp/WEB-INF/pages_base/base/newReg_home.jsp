<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('ul.list li').click(function (e)
        {
            $('ul.list li').removeClass("active");
            $(this).addClass("active");
        });
    });
</script>

<!-- User Info -->
<div>

    <div class="">
        <div class="image">
            <!--            <img src="images/user.png" width="48" height="48" alt="User" />-->
        </div>
        <div class="name"> Welcome Guest!</div>

    </div>
</div>
<div class="menu">
    <ul class="list">

        <li class="header"></li>

        <li class="active">
            <a href="login">
                <i class="material-icons" style="margin-top: 5px">home</i>
                <span>HOME</span>
            </a>
        </li>
        <li>
            <a href="companyRegistration">
                <i class="material-icons" style="margin-top: 5px">open_in_new</i>
                <span>NEW REGISTRATION</span>
            </a>
        </li>

        <li>
            <a href="viewStatus">
                <i class="material-icons" style="margin-top: 5px">remove_red_eye</i>
                <span>VIEW STATUS</span>
            </a>
        </li>
    </ul>
</div>

<!-- #Menu -->
<!-- Footer -->
<div class="legal">
    <div class="copyright">

        Copyright © 2020. All rights reserved
        <!--&copy; 2017 - 2018 <a href="javascript:void(0);">Madras Security Printers</a>.-->
    </div>
    <div class="version">
        <!--<b>Version: </b> 1.0-->
    </div>
</div>


<!-- #Footer -->
