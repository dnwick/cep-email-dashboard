<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.cep.email.ui.ConfigUtils" %>
<%@ page import="org.wso2.cep.email.ui.ESBConfigUtils" %>
<%@ page import="org.wso2.cep.email.ui.CEPConfigUtils" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
 
<div id="middle">
    <h2>Email Monitoring </h2>
 
    <div id="workArea">

        <form method="POST">
        <table style="width:100%">
            <tbody>
                <tr>
                    <td>
                        <table class="styledLeft noBorders" style="width:100%">
                            <thead>
                                    <tr>
                                        <th colspan="2"><h4>E-mail Credentials</h4></th>
                                    </tr>
                            </thead>
                            <tbody>
                                    <tr>
                                        <td style="width:10%">email address:<span class="required">*</span></td> <td><input type="text" name="emailAddress"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">email password:<span class="required">*</span></td> <td><input type="password" name="emailPassword"></td>
                                    </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="styledLeft noBorders" style="width:100%">
                            <thead>
                                    <tr>
                                        <th colspan="2"><h4>ESB Details</h4></th>
                                    </tr>
                            </thead>
                            <tbody>
                                    <tr>
                                        <td style="width:10%">ESB Username:<span class="required">*</span></td> <td><input type="text" name="esbUserName"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">ESB Password:<span class="required">*</span></td> <td><input type="password" name="esbPassword"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">ESB IP:<span class="required">*</span></td> <td><input type="text" name="esbIP"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">ESB Port:<span class="required">*</span></td> <td><input type="text" name="esbPort"></td>
                                    </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="styledLeft noBorders" style="width:100%">
                            <thead>
                                    <tr>
                                        <th colspan="2"><h4>CEP Details</h4></th>
                                    </tr>
                            </thead>
                            <tbody>
                                    <tr>
                                        <td style="width:10%">CEP Username:<span class="required">*</span></td> <td><input type="text" name="cepUserName"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">CEP Password:<span class="required">*</span></td> <td><input type="password" name="cepPassword"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">CEP IP:<span class="required">*</span></td> <td><input type="text" name="cepIP"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">CEP Port:<span class="required">*</span></td> <td><input type="text" name="cepPort"></td>
                                    </tr>
                                    <tr>
                                        <td style="width:10%">CEP Queries:<span class="required">*</span></td> <td><textarea name="cepQueries" rows="4" cols="50" style="margin: 2px; width: 415px; height: 115px;"></textarea></td>
                                    </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Save"></td>
                </tr>
            </tbody>
        </table>
        </form>

            <%
            String emailAddress = request.getParameter("emailAddress");
            String emailPassword = request.getParameter("emailPassword");
            String esbIP = request.getParameter("esbIP");
            String esbPort = request.getParameter("esbPort");
            String esbUserName = request.getParameter("esbUserName");
            String esbPassword = request.getParameter("esbPassword");
            String cepIP = request.getParameter("cepIP");
            String cepPort = request.getParameter("cepPort");
            String cepUserName = request.getParameter("cepUserName");
            String cepPassword = request.getParameter("cepPassword");
            String cepQueries = request.getParameter("cepQueries");

            ConfigUtils configUtils = new ConfigUtils();

            if(configUtils.isNotNullOrEmpty(emailAddress) && configUtils.isNotNullOrEmpty(emailPassword) &&
                 configUtils.isNotNullOrEmpty(esbIP) && configUtils.isNotNullOrEmpty(esbPort) &&
                 configUtils.isNotNullOrEmpty(esbUserName) && configUtils.isNotNullOrEmpty(esbPassword) &&
                 configUtils.isNotNullOrEmpty(cepIP) && configUtils.isNotNullOrEmpty(cepPort) &&
                 configUtils.isNotNullOrEmpty(cepUserName) && configUtils.isNotNullOrEmpty(cepPassword) && configUtils.isNotNullOrEmpty(cepQueries)){

                String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
                ConfigurationContext configContext =
                    (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);

                CEPConfigUtils cepConfigUtils = new CEPConfigUtils(backendServerURL, configContext, cepQueries, esbIP, esbPort, esbUserName, esbPassword, emailAddress);

                ESBConfigUtils esbConfigUtils = new ESBConfigUtils(esbIP, esbPort, esbUserName, esbPassword);
                esbConfigUtils.AddConfigurations(cepUserName, cepPassword, emailAddress, emailPassword, cepIP, cepPort, backendServerURL, configContext);
            %>
            <script>
                CARBON.showInfoDialog("ESB server configurations stored successfully");
            </script>
            <% }
            else if(emailAddress == "" || emailPassword == "" ||
                 esbIP == "" || esbPort == "" ||
                 esbUserName == "" || esbPassword == "" ||
                 cepIP == "" || cepPort == "" ||
                 cepUserName == "" || cepPassword == ""){%>
            <script>
                CARBON.showInfoDialog("Please fill all the detail fields!");
            </script>
            <% } %>
    </div>
</div>