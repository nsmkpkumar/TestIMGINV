package com.test.service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.model.Employee;
import com.test.repository.EmployeeRepository;

// employee service class
@Service
public class EmployeeService {
	
	@Value("${spring.employee.fiscalYear_startDate}")
	String fiscalYear_startDate;
	
	@Value("${spring.employee.fiscalYear_endDate}")
	String fiscalYear_endDate;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	

	
	public List<Employee> getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList();

    	
        employeeRepository.findAll().forEach(employee -> employees.add(employee));
        //Getting each employee details
        for(Employee e: employees) {
        	double result[]= {0,0};
//        	e=EmployeeService.processData(e);
        	e=daysCount(e);
        	result=calculateTax(e.getYearSalary());
        	
        	e.setTax(result[0]);
        	e.setCess(result[1]);
        	System.out.println("Employee Details"+e.toString());
	        }
	        return employees;
    }

    public Employee getEmployeeById(long empId) {
        return employeeRepository.findById(empId).get();
    }

    public void saveOrUpdate(Employee employee) {
        employeeRepository.save(employee);
    }

   // Calculating Tax and Cess Amount
    public static double[] calculateTax(double totalSal) {
        double tax = 0;
        double cessIncome =0;
        double cess=0;
        double result[]= {0,0};

		if(totalSal <= 250000) {
			tax = 0;
		}
		else if( totalSal > 250000 && totalSal <= 500000) {
			tax = ((totalSal - 250000) * 5)/100;
		}
		else if( totalSal > 500000 && totalSal <= 1000000) {
			tax = 12500 + ((totalSal - 500000) * 10)/100;
		}
		else if( totalSal > 1000000) {
			tax = 37500 + ((totalSal - 1000000) * 20)/100;
					if(totalSal>2500000) {
					System.out.println("Income:  "+totalSal );
					cessIncome = totalSal - 2500000;
					cess= (0.02 * cessIncome);
					System.out.println("cessIncome: "+cessIncome );
					System.out.println("cess:  "+cess );
				} 
        }
         
        result[0]=tax;
        result[1]=cess;
        return result;
	}

//Finding Employee Working days and calculating Year salary
	public  Employee daysCount( Employee e)
	{
		LocalDate fiscal_date, joining_date, endFiscal_date;
		double totalSal=0;
		long daysDiff=0;
		
		
		java.sql.Date sqlDate1 = java.sql.Date.valueOf(fiscalYear_startDate);
		fiscal_date = sqlDate1.toLocalDate();

		java.sql.Date sqlDate2 = java.sql.Date.valueOf(fiscalYear_endDate);
		endFiscal_date = sqlDate2.toLocalDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
    	java.sql.Date doj=new java.sql.Date(e.getDoj().getTime());
    	joining_date=doj.toLocalDate();
		
//		System.out.println("Fiscal date: "+fiscal_date +"  Joining Date"+ joining_date +"End fiscal date"+endFiscal_date);
	 
		 
//		 System.out.println("Fiscal Date : JoiningDate" + fiscal_date+"  -- "+joining_date);

		 
		 if(joining_date.getYear()<fiscal_date.getYear()) {
			System.out.println("I joined before fiscal year");
			totalSal=e.getSalary()*12;		 
			System.out.println("My Total Salary is:  "+totalSal); 
		 }
		 else if(joining_date.getYear()>=fiscal_date.getYear()){
			 System.out.println("I Joined same year of Fiscal");
			if(joining_date.getYear()==fiscal_date.getYear()){
				System.out.print("I am in same year ");
			   
					    if(joining_date.getMonthValue() < fiscal_date.getMonthValue() ){
					    	System.out.println("I Joined same year of Fiscal,   But before fiscal Year");
					    	System.out.println("Salary"+e.getSalary());
					    	totalSal=e.getSalary()*12;
					    	System.out.println("My Total Salary is:  "+totalSal);
					    }
					    else {
					    		System.out.println(" I joined after fiscal year start");
					    		daysDiff=ChronoUnit.DAYS.between(fiscal_date,joining_date);
					    		System.out.println("The number of days between dates: " + (daysDiff+1));
					    		if((daysDiff+1)>=365) {
					    			totalSal=e.getSalary()*12;
					    			System.out.println("My Total Salary is:  "+totalSal);
					    		}
					    		else {
					    			
					    			double daySal= e.getSalary()/30;
					    			totalSal=e.getSalary()*12;
					    			double lossOfPay=((daysDiff)*daySal);
						    		totalSal=totalSal-lossOfPay;
							    	System.out.println("My Total Salary is:  "+totalSal);
					    		}
					    }
					    
			}
			else if (joining_date.getYear()>fiscal_date.getYear()){
				 System.out.println("I am at after Fiscal joining "+joining_date.getYear()+"  "+fiscal_date.getYear());
				 daysDiff=ChronoUnit.DAYS.between(fiscal_date,joining_date);
				 System.out.println("The number of days between dates: " + (daysDiff+1));
				 
				 double daySal= e.getSalary()/30;
				 totalSal=e.getSalary()*12;
				 double lossOfPay=((daysDiff)*daySal);
				 totalSal=totalSal-lossOfPay;
				 System.out.println("My Total Salary is:  "+totalSal);
			 }
		 }
		
		 
		 e.setYearSalary(totalSal);
	   return e;
	}
	public  LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}