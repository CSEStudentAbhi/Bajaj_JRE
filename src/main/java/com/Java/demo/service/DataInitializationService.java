package com.Java.demo.service;

import com.Java.demo.entity.Department;
import com.Java.demo.entity.Employee;
import com.Java.demo.entity.Payment;
import com.Java.demo.repository.DepartmentRepository;
import com.Java.demo.repository.EmployeeRepository;
import com.Java.demo.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class DataInitializationService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializationService.class);
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    public void initializeSampleData() {
        logger.info("Initializing sample data...");
        
        try {
            // Create departments
            Department engineeringDept = new Department("Engineering");
            Department salesDept = new Department("Sales");
            Department marketingDept = new Department("Marketing");
            Department hrDept = new Department("Human Resources");
            Department financeDept = new Department("Finance");
            
            departmentRepository.saveAll(Arrays.asList(
                engineeringDept, salesDept, marketingDept, hrDept, financeDept
            ));
            
            // Create employees
            Employee emp1 = new Employee("John", "Smith", LocalDate.of(1990, 5, 15), "M", engineeringDept);
            Employee emp2 = new Employee("Jane", "Doe", LocalDate.of(1988, 8, 22), "F", salesDept);
            Employee emp3 = new Employee("Bob", "Johnson", LocalDate.of(1992, 3, 10), "M", engineeringDept);
            Employee emp4 = new Employee("Alice", "Brown", LocalDate.of(1991, 12, 5), "F", marketingDept);
            Employee emp5 = new Employee("Charlie", "Wilson", LocalDate.of(1989, 7, 18), "M", hrDept);
            Employee emp6 = new Employee("Diana", "Miller", LocalDate.of(1993, 11, 30), "F", financeDept);
            Employee emp7 = new Employee("Edward", "Davis", LocalDate.of(1994, 4, 12), "M", engineeringDept);
            Employee emp8 = new Employee("Fiona", "Garcia", LocalDate.of(1990, 9, 25), "F", salesDept);
            
            employeeRepository.saveAll(Arrays.asList(
                emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8
            ));
            
            // Create payments
            Payment payment1 = new Payment(emp1, new BigDecimal("5000.00"), LocalDateTime.now().minusDays(30));
            Payment payment2 = new Payment(emp2, new BigDecimal("4500.00"), LocalDateTime.now().minusDays(25));
            Payment payment3 = new Payment(emp3, new BigDecimal("5200.00"), LocalDateTime.now().minusDays(20));
            Payment payment4 = new Payment(emp4, new BigDecimal("4800.00"), LocalDateTime.now().minusDays(15));
            Payment payment5 = new Payment(emp5, new BigDecimal("4200.00"), LocalDateTime.now().minusDays(10));
            Payment payment6 = new Payment(emp6, new BigDecimal("5500.00"), LocalDateTime.now().minusDays(5));
            Payment payment7 = new Payment(emp7, new BigDecimal("5100.00"), LocalDateTime.now().minusDays(3));
            Payment payment8 = new Payment(emp8, new BigDecimal("4700.00"), LocalDateTime.now().minusDays(1));
            
            // Add some historical payments
            Payment payment9 = new Payment(emp1, new BigDecimal("5000.00"), LocalDateTime.now().minusDays(60));
            Payment payment10 = new Payment(emp2, new BigDecimal("4500.00"), LocalDateTime.now().minusDays(55));
            Payment payment11 = new Payment(emp3, new BigDecimal("5200.00"), LocalDateTime.now().minusDays(50));
            Payment payment12 = new Payment(emp4, new BigDecimal("4800.00"), LocalDateTime.now().minusDays(45));
            
            paymentRepository.saveAll(Arrays.asList(
                payment1, payment2, payment3, payment4, payment5, payment6, payment7, payment8,
                payment9, payment10, payment11, payment12
            ));
            
            logger.info("Sample data initialized successfully. Created {} departments, {} employees, and {} payments.",
                departmentRepository.count(), employeeRepository.count(), paymentRepository.count());
            
        } catch (Exception e) {
            logger.error("Error initializing sample data", e);
            throw new RuntimeException("Failed to initialize sample data", e);
        }
    }
}
