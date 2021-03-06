<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.frontPage"/>
</h2>

<fr:form action="/mobility.do?method=frontPage">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.mobility.domain.util.OfferSearch" bundle="MOBILITY_RESOURCES">
			<fr:slot name="processNumber" key="label.mobility.processIdentification" required="true">
				<fr:property name="size" value="10"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="MOBILITY_RESOURCES" key="label.mobility.submit"/>
	</html:submit>
</fr:form>
<br/>
<logic:notEmpty name="offerSearch" property="processNumber">
	<bean:message bundle="MOBILITY_RESOURCES" key="message.mobility.invalid.processIdentification"/>
</logic:notEmpty>


<%  module.mobility.domain.MobilitySystem mobilitySystem = module.mobility.domain.MobilitySystem.getInstance();
if (mobilitySystem.isManagementMember()) { %>
	<h3 class="separator mbottom15" ><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.pendingApproval"/></h3>
	<p><strong><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.pendingApproval.jobOffers"/></strong></p>
<bean:define id="offerList" name="offerSearch" property="pendingApprovalJobOfferSet" toScope="request"/>	
	<jsp:include page="jobOfferList.jsp"/>

	<p><strong><bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.pendingApproval.workerOffers"/></strong></p>
	<bean:define id="offerList" name="offerSearch" property="pendingApprovalWorkerJobOfferSet" toScope="request"/>
	<jsp:include page="workerOfferList.jsp"/>
<%}%>


