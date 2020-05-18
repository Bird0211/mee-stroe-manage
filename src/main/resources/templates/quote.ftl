<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"> <!-- utf-8 works for most cases -->
    <meta name="viewport" content="width=device-width"> <!-- Forcing initial-scale shouldn't be necessary -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge"> <!-- Use the latest (edge) version of IE rendering engine -->
    <title></title> <!-- The title tag shows in email notifications, like Android 4.4. -->
    <style type="text/css">

        /* What it does: Remove spaces around the email design added by some email clients. */
        /* Beware: It can remove the padding / margin and add a background color to the compose a reply window. */
        html,
        body {
            margin: 0;
            padding: 0;
            height: 100% !important;
            width: 100% !important;
        }

        /* What it does: Stops email clients resizing small text. */
        * {
            -ms-text-size-adjust: 100%;
            -webkit-text-size-adjust: 100%;
        }

        /* What it does: Forces Outlook.com to display emails full width. */
        .ExternalClass {
            width: 100%;
        }

        /* What it does: Stops Outlook from adding extra spacing to tables. */
        table,
        td {
            mso-table-lspace: 0pt;
            mso-table-rspace: 0pt;
        }

        /* What it does: Fixes webkit padding issue. */
        table {
            border-spacing:0 !important;
        }

        /* What it does: Fixes Outlook.com line height. */
        .ExternalClass,
        .ExternalClass * {
            line-height: 100%;
        }

        /* What it does: Fix for Yahoo mail table alignment bug. */
        table {
            border-collapse: collapse;
            margin: 0 auto;
        }

        /* What it does: Uses a better rendering method when resizing images in IE. */
        img {
            -ms-interpolation-mode:bicubic;
        }

        /* What it does: Overrides styles added when Yahoo's auto-senses a link. */
        .yshortcuts a {
            border-bottom: none !important;
        }

        /* What it does: Overrides blue, underlined link auto-detected by iOS Mail. */
        /* Create a class for every link style needed; this template needs only one for the link in the footer. */
        .mobile-link--footer a {
            color: #666666 !important;
        }

        /* What it does: Overrides styles added images. */
        img {
            border:0 !important;
            outline:none !important;
            text-decoration:none !important;
        }

        /* Media Queries */
        @media screen and (max-device-width: 600px), screen and (max-width: 600px) {

            /* What it does: Overrides email-container's desktop width and forces it into a 100% fluid width. */
            .email-container {
                width: 100% !important;
            }

            /* What it does: Forces images to resize to the width of their container. */
            img[class="fluid"],
            img[class="fluid-centered"] {
                width: 100% !important;
                max-width: 100% !important;
                height: auto !important;
                margin: auto !important;
            }
            /* And center justify these ones. */
            img[class="fluid-centered"] {
                margin: auto !important;
            }

            /* What it does: Forces table cells into full-width rows. */
            td[class="stack-column"],
            td[class="stack-column-center"] {
                display: block !important;
                width: 100% !important;
                direction: ltr !important;
            }
            /* And center justify these ones. */
            td[class="stack-column-center"] {
                text-align: center !important;
            }

            /* Data Table Styles */
            /* What it does: Hides table headers */
            td[class="data-table-th"] {
                display: none !important;
            }
            /* What it does: Change the look and layout of the remaining td's */
            td[class="data-table-td"],
            td[class="data-table-td-title"] {
                display: block !important;
                width: 100% !important;
                border: 0 !important;
            }
            /* What it does: Changes the appearance of the first td in each row */
            td[class="data-table-td-title"] {
                font-weight: bold;
                color: #333333;
                padding: 10px 0 0 0 !important;
                border-top: 2px solid #eeeeee !important;
            }
            /* What it does: Changes the appearance of the other td's in each row */
            td[class="data-table-td"] {
                padding: 5px 0 0 0 !important
            }
            /* What it does: Provides a visual divider between table rows. In this case, a bit of extra space. */
            td[class="data-table-mobile-divider"] {
                display: block !important;
                height: 20px;
            }
            /* END Data Table Styles */

        }

    </style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#f4f4f4" style="margin:0; padding:0; -webkit-text-size-adjust:none; -ms-text-size-adjust:none;">
<table cellpadding="0" cellspacing="0" border="0" height="100%" width="100%" bgcolor="#f4f4f4" style="border-collapse:collapse;"><tr><td>

            <!-- Visually Hidden Preheader Text : BEGIN -->
            <div style="display:none;font-size:1px;color:#222222;line-height:1px;max-height:0px;max-width:0px;opacity:0;overflow:hidden;mso-hide: all;">
               Quote Confirmation - Heat Pumps best service in New Zealand.
            </div>
            <!-- Visually Hidden Preheader Text : END -->

            <!-- Email wrapper : BEGIN -->
            <table border="0" width="600" cellpadding="0" cellspacing="0" align="center" style="width:600px; margin: auto;" class="email-container">
                <tr>
                    <td>

                        <!-- Logo + Links : BEGIN -->
                        <table border="0" width="100%" cellpadding="0" cellspacing="0">
                            <tr><td height="5" style="font-size: 0; line-height: 0;">&nbsp;</td></tr>
                            <tr>
                                <td valign="middle" width="150" style="padding:5px 0; text-align:left; line-height: 1;" class="stack-column-center">
                                    <img src='http://data.middle-earth.tech/img/logo.png' alt="HeatPumps" width="150" height="50" border="0" style="display: block; margin: auto;">
                                </td>
                                <!--
                                <td valign="middle" style="padding:5px 0; text-align:right; line-height:1.1; font-family: sans-serif; font-size: 13px; color: #999999;" class="stack-column-center">
                                  A mobile-friendly template with media queries.
                                </td>
                                  -->
                            </tr>
                            <tr><td height="5" style="font-size: 0; line-height: 0;">&nbsp;</td></tr>
                        </table>
                        <!-- Logo + Links : END -->

                        <!-- Main Email Body : BEGIN -->
                        <table border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="#ffffff">

                            <!-- Single Fluid Image, No Crop : BEGIN -->
                            <tr>
                                <td valign="middle" align="center">
                                    <img src="http://data.middle-earth.tech/img/background.png" alt="Heat Pumps" height="300" width="600" align="center" border="0" style="margin: auto;" class="fluid">
                                </td>
                            </tr>
                            <!-- Single Fluid Image, No Crop : END -->

                            <!-- Full Width, Fluid Column : BEGIN -->
                            <tr>
                                <td style="border-bottom: 1px solid #e5e5e5;padding: 20px; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #555555;">
                                    <h1 style="margin: 0 0 10px 0; font-family: sans-serif; font-size: 25px; line-height: 30px; color: #333333; font-weight: normal;">
                                        Dear ${firstname}
                                        <br><br>
                                    </h1>
                                    <p style="margin: 0;">
                                        Wish you have a nice day!
                                        <br><br>
                                        Welcome to Heat Pumps and we have received your application. <br>
                                        Our professional staff will contact you as soon as possible and provide you with quotation information.
                                        <br><br>
                                        Thanks,<br><br>
                                        Kind regards
                                    </p>
                                </td>
                            </tr>
                            <!-- Full Width, Fluid Column : END -->

                            <!-- Thumbnail + Text, Side by Side -> Stacked : BEGIN -->
                            <!-- Thumbnail Left, Text Right : BEGIN -->
                            <tr>
                                <td style="border-bottom: 1px solid #e5e5e5;padding: 4%;">
                                    <h1 style="margin: 0 0 10px 0; font-family: sans-serif; font-size: 25px; line-height: 30px; color: #333333; font-weight: normal;">
                                        Specials
                                    </h1>
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center" dir="ltr">
                                        <tr>
                                            <td valign="top" width="160" class="stack-column-center" dir="ltr">
                                                <img src="http://www.heatpumpsnz.com/images/special1.jpeg" alt="alt text" height="160" width="160" border="0" align="center">
                                            </td>
                                            <!-- Spacer : BEGIN -->
                                            <td width="4%" height="4%" class="stack-column">&nbsp;</td>
                                            <!-- Spacer : END -->
                                            <td valign="top" style="font-family: sans-serif; font-size: 15px; line-height: 1.3; color: #666666; text-align: left;" class="stack-column-center" dir="ltr">
                                                Mitsubishi SRK17ZMP-SET
                                                <br><br>
                                                Ideal for second bedrooms, the home office or smaller apartments
                                                <br><br>
                                                Installation cost from a low: $1500.
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <!-- Thumbnail Left, Text Right : END -->
                            <!-- Thumbnail Right, Text Left : BEGIN -->
                            <#--
                            <tr>
                                <td style="border-bottom: 1px solid #e5e5e5;padding: 4%;">
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center" dir="rtl">

                                        <tr>
                                            <td valign="top" width="160" class="stack-column-center" dir="ltr">
                                                <img src="http://data.middle-earth.tech/img/321.png" alt="alt text" height="160" width="160" border="0" align="center">
                                            </td>
                                            <!-- Spacer : BEGIN &ndash;&gt;
                                            <td width="4%" height="4%" class="stack-column">&nbsp;</td>
                                            <!-- Spacer : END &ndash;&gt;
                                            <td valign="top" style="font-family: sans-serif; font-size: 15px; line-height: 1.3; color: #666666; text-align: left;" class="stack-column-center" dir="ltr">
                                                Fujitsu 3.4kW Heat Pump / Air Conditioner
                                                <br><br>
                                                Installed Back to Back
                                                <br><br>
                                                $1,899 incl.
                                                <br><br>
                                                6 Year Warranty!
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            -->
                            <!-- Thumbnail Right, Text Left : END -->
                            <!-- Thumbnail + Text, Side by Side -> Stacked : END -->

                        </table>
                        <!-- Main Email Body : END -->

                    </td>
                </tr>

                <!-- Footer : BEGIN -->
                <tr>
                    <td style="text-align:center; padding:4% 0; font-family:sans-serif; font-size:13px; line-height:1.2; color:#666666;">
                        You received this email because you opted in to our newsletter.
                        <br><br>
                        HeatPumps &bull; <span class="mobile-link--footer">11/77 Porana Rd, Hillcrest, Auckland 0627</span> &bull; <span class="mobile-link--footer">(123) 456-7890</span>
                        <br><br>
                    </td>
                </tr>
                <!-- Footer : END -->

            </table>
            <!-- Email wrapper : END -->

        </td></tr></table>
</body>
</html>
