<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.mobility.jobOfferProcessIdentification"/> - 
	<bean:write name="jobOffer" property="jobOfferProcess.processIdentification"/> 
</h2>

