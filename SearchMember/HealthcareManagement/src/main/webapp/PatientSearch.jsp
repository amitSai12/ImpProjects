<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%> 
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%> 

<f:view>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h:form>
        		 <center>
                <h2><h:outputText value="- Search Patients -"/></h2>
                UhId
                <h:inputText value="#{patient.uhId}" />&nbsp;
                <h:commandButton value="Search" action="#{pImpl.getUhIdLists(patient.uhId)}" />
                &nbsp;&nbsp;
                 FirstName
                <h:inputText value="#{patient.firstName}" />&nbsp;
                <h:commandButton value="Search" action="#{pImpl.getFirstNameLists(patient.firstName)}" />
                &nbsp;&nbsp;
                 UserName
                <h:inputText value="#{patient.userName}" />&nbsp;
                <h:commandButton value="Search" action="#{pImpl.getUserNameLists(patient.userName)}" /> <br><br><br><br>

		<h:dataTable
			value="#{patientList}" var="p" border="1">
			 	<h:column>
                    <f:facet name="header">
                    	<h:outputLabel value="Customer Id" />
                    </f:facet>
                    <h:outputText value="#{p.uhId}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="First Name" />
                    </f:facet>
                    <h:outputText value="#{p.firstName}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">	
                    	<h:outputLabel value="Last Name" />
                    </f:facet>
                    <h:outputText value="#{p.lastName}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Gender" />
                    </f:facet>
                    <h:outputText value="#{p.gender}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="DOB" />
                    </f:facet>
                    <h:outputText value="#{p.dob}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Username" />
                    </f:facet>
                    <h:outputText value="#{p.userName}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Email" />
                    </f:facet>
                    <h:outputText value="#{p.email}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Status" />
                    </f:facet>
                    <h:outputText value="#{p.status}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Status" />
                    </f:facet>
                    <h:outputText value="#{p.phoneNo}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Status" />
                    </f:facet>
                    <h:outputText value="#{p.cause}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Status" />
                    </f:facet>
                    <h:outputText value="#{p.address}"/>
                </h:column>
                    <h:column>
                     <f:facet name="header">
                    	<h:outputLabel value="Status" />
                    </f:facet>
                    <h:outputText value="#{p.medHistory}"/>
                </h:column>
		</h:dataTable>    
         </center>
        </h:form>
    </body>
</html>
</f:view>