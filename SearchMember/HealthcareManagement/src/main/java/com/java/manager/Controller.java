package com.java.manager;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

public class Controller {
    private Patient_Enrollment patientreport;
    private PatientDAOImpl daoImpl;
    

    // Getters and setters...

    public Patient_Enrollment getPatientreport() {
		return patientreport;
	}

	public void setPatientreport(Patient_Enrollment patientreport) {
		this.patientreport = patientreport;
	}

	public PatientDAOImpl getDaoImpl() {
		return daoImpl;
	}

	public void setDaoImpl(PatientDAOImpl daoImpl) {
		this.daoImpl = daoImpl;
	}

	public String addReport() throws NamingException {
        if (addValid()) {
            return daoImpl.addReport(patientreport);
        }
        return "";
    }

	public boolean addValid() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    String testCode = "^T.*$"; // Test code pattern
	    String testName = "^.{5,}$"; // Test name pattern

	    // Regular expression for cost greater than or equal to 50
	    String cost = "^(5[0-9]+(\\.\\d+)?|100(\\.0+)?)$";

	    Pattern patternTestCode = Pattern.compile(testCode);
	    Matcher matcherTestCode = patternTestCode.matcher(patientreport.getTestCode());

	    Pattern patternTestName = Pattern.compile(testName);
	    Matcher matcherTestName = patternTestName.matcher(patientreport.getTestName());

	    Pattern patternCost = Pattern.compile(cost);
	    Matcher matcherCost = patternCost.matcher(String.valueOf(patientreport.getCost()));

	    if (!matcherTestCode.matches()) {
	        context.addMessage("form:testCode", new FacesMessage("Test code must start with 'T'"));
	        return false;
	    }
	    if (!matcherTestName.matches()) {
	        context.addMessage("form:testName", new FacesMessage("Test name must contain at least 5 characters"));
	        return false;
	    }
	    if (!matcherCost.matches()) {
	        context.addMessage("form:cost", new FacesMessage("Cost must be a number greater than or equal to 50"));
	        return false;
	    }

	    return true;
	}

}