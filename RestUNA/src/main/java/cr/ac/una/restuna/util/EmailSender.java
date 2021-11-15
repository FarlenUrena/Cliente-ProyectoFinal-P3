package cr.ac.una.restuna.util;

public class EmailSender {

    public EmailSender(){

    }

    public String getHtml() {
        return html;
    }

    private String html = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "\n"
                    + "  <meta charset=\"utf-8\">\n"
                    + "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "  <title>Email Receipt</title>\n"
                    + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "  <style type=\"text/css\">\n"
                    + "  /**\n"
                    + "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n"
                    + "   */\n"
                    + "  @media screen {\n"
                    + "    @font-face {\n"
                    + "      font-family: 'Source Sans Pro';\n"
                    + "      font-style: normal;\n"
                    + "      font-weight: 400;\n"
                    + "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n"
                    + "    }\n"
                    + "\n"
                    + "    @font-face {\n"
                    + "      font-family: 'Source Sans Pro';\n"
                    + "      font-style: normal;\n"
                    + "      font-weight: 700;\n"
                    + "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n"
                    + "    }\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Avoid browser level font resizing.\n"
                    + "   * 1. Windows Mobile\n"
                    + "   * 2. iOS / OSX\n"
                    + "   */\n"
                    + "  body,\n"
                    + "  table,\n"
                    + "  td,\n"
                    + "  a {\n"
                    + "    -ms-text-size-adjust: 100%; /* 1 */\n"
                    + "    -webkit-text-size-adjust: 100%; /* 2 */\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Remove extra space added to tables and cells in Outlook.\n"
                    + "   */\n"
                    + "  table,\n"
                    + "  td {\n"
                    + "    mso-table-rspace: 0pt;\n"
                    + "    mso-table-lspace: 0pt;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Better fluid images in Internet Explorer.\n"
                    + "   */\n"
                    + "  img {\n"
                    + "    -ms-interpolation-mode: bicubic;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Remove blue links for iOS devices.\n"
                    + "   */\n"
                    + "  a[x-apple-data-detectors] {\n"
                    + "    font-family: inherit !important;\n"
                    + "    font-size: inherit !important;\n"
                    + "    font-weight: inherit !important;\n"
                    + "    line-height: inherit !important;\n"
                    + "    color: inherit !important;\n"
                    + "    text-decoration: none !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Fix centering issues in Android 4.4.\n"
                    + "   */\n"
                    + "  div[style*=\"margin: 16px 0;\"] {\n"
                    + "    margin: 0 !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  body {\n"
                    + "    width: 100% !important;\n"
                    + "    height: 100% !important;\n"
                    + "    padding: 0 !important;\n"
                    + "    margin: 0 !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Collapse table borders to avoid space between cells.\n"
                    + "   */\n"
                    + "  table {\n"
                    + "    border-collapse: collapse !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  a {\n"
                    + "    color: #1a82e2;\n"
                    + "  }\n"
                    + "\n"
                    + "  img {\n"
                    + "    height: auto;\n"
                    + "    line-height: 100%;\n"
                    + "    text-decoration: none;\n"
                    + "    border: 0;\n"
                    + "    outline: none;\n"
                    + "  }\n"
                    + "  </style>\n"
                    + "\n"
                    + "</head>\n"
                    + "<body style=\"background-color: #40444F;\">\n"
                    + "\n"
                    + "  <!-- start preheader -->\n"
                    + "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n"
                    + "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n"
                    + "  </div>\n"
                    + "  <!-- end preheader -->\n"
                    + "\n"
                    + "  <!-- start body -->\n"
                    + "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                    + "\n"
                    + "    <!-- start logo -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "          <tr>\n"
                    + "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n"
                    + "              <a href=\"https://sendgrid.com\" target=\"_blank\" style=\"display: inline-block;\">\n"
                    + "                <img src=\"https://drive.google.com/thumbnail?id=1gHCE65Swhj3EcOyrSaDDOwz9fxpStVrh\" alt=\"Logo\" border=\"0\" width=\"88\" style=\"display: block; width: 148px; max-width: 148px; min-width: 48px;\">\n"
                    + "              </a>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end logo -->\n"
                    + "\n"
                    + "    <!-- start hero -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "          <tr>\n"
                    + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n"
                    + "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Gracias por su compra, pura vida!</h1>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end hero -->\n"
                    + "\n"
                    + "    <!-- start copy block -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "\n"
                    + "          <!-- start copy -->\n"
                    + "          <tr>\n"
                    + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                    + "              <p style=\"margin: 0;\">Factura generada según su orden. en caso de error puede contacte por este medio, o via telefónica al 0987654321</a>.</p>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "          <!-- end copy -->\n"
                    + "\n"
                    + "          <!-- start receipt table -->\n"
                    + "          <tr>\n"
                    + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                    + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" bgcolor=\"#40444F\" width=\"75%\" style=\"padding: 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\"><strong>ID factura</strong></td>\n"
                    + "                  <td align=\"left\" bgcolor=\"#40444F\" width=\"25%\" style=\"padding: 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\"><strong>DTO</strong></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">Fecha de facturación</td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">DTO</td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">Metodo de pago</td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">DTO</td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">ID caja</td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">DTO</td>\n"
                    + "                </tr>\n"
                    + "\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 12px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-top: 2px dashed #40444F; border-bottom: 2px dashed #40444F;\"><strong>Total</strong></td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 12px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-top: 2px dashed #40444F; border-bottom: 2px dashed #40444F;\"><strong>DTO</strong></td>\n"
                    + "                </tr>\n"
                    + "              </table>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "          <!-- end reeipt table -->\n"
                    + "\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end copy block -->\n"
                    + "\n"
                    + "    <!-- start receipt address block -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\" valign=\"top\" width=\"100%\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table align=\"center\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "          <tr>\n"
                    + "            <td align=\"center\" valign=\"top\" style=\"font-size: 0; border-bottom: 3px solid #d4dadf\">\n"
                    + "              <!--[if (gte mso 9)|(IE)]>\n"
                    + "              <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "              <tr>\n"
                    + "              <td align=\"left\" valign=\"top\" width=\"300\">\n"
                    + "              <![endif]-->\n"
                    + "\n"
                    + "              <!--[if (gte mso 9)|(IE)]>\n"
                    + "              </td>\n"
                    + "              <td align=\"left\" valign=\"top\" width=\"300\">\n"
                    + "              <![endif]-->\n"
                    + "              <div style=\"display: inline-block; width: 100%; max-width: 50%; min-width: 240px; vertical-align: top;\">\n"
                    + "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 300px;\">\n"
                    + "               \n"
                    + "                </table>\n"
                    + "              </div>\n"
                    + "              <!--[if (gte mso 9)|(IE)]>\n"
                    + "              </td>\n"
                    + "              </tr>\n"
                    + "              </table>\n"
                    + "              <![endif]-->\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end receipt address block -->\n"
                    + "\n"
                    + "    <!-- start footer -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\" style=\"padding: 24px;\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "\n"
                    + "          <!-- start permission -->\n"
                    + "          <tr>\n"
                    + "            <td align=\"center\" bgcolor=\"#40444f\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #fff;\">\n"
                    + "              <p style=\"margin: 0;\">Este correo fué enviado con el consentimiento expreso del cliente con datos proporcionados por el mismo, no pretente ser spam.</p>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "          <!-- end permission -->\n"
                    + "\n"
                    + "          <!-- start unsubscribe -->\n"
                    + "         \n"
                    + "          <!-- end unsubscribe -->\n"
                    + "\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end footer -->\n"
                    + "\n"
                    + "  </table>\n"
                    + "  <!-- end body -->\n"
                    + "\n"
                    + "</body>\n"
                    + "</html>";
}


