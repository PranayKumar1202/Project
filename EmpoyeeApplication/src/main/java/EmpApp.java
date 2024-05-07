import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpApp {
    @SpringBootApplication
    @RestController
    @RequestMapping("/employees")
    public class EmployeeApplication {

        private final List<Employee> employees = new ArrayList<>();

        public static void main(String[] args) {
            SpringApplication.run(EmployeeApplication.class, args);
        }

        @PostMapping("/adding the employees")
        public String addEmployee(@RequestBody Employee employee) {
            // Validate employee data
            if (!isValidEmployee(employee)) {
                throw new IllegalArgumentException("Invalid employee data");
            }

            employees.add(employee);
            return "Employee added successfully";
        }

        @GetMapping("/tax-deduction fpr the employee")
        public List<TaxDetails> getTaxDeduction() {
            // Calculate tax deduction for each employee
            List<TaxDetails> taxDetailsList = new ArrayList<>();
            for (Employee employee : employees) {
                double totalSalary = calculateTotalSalary(employee);
                double taxAmount = calculateTax(totalSalary);
                double cessAmount = calculateCess(totalSalary);
                taxDetailsList.add(new TaxDetails(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                        totalSalary, taxAmount, cessAmount));
            }
            return taxDetailsList;
        }

        private boolean isValidEmployee(Employee employee) {
            // Validate employee data
            return employee != null &&
                    employee.getEmployeeId() != null && !employee.getEmployeeId().isEmpty() &&
                    employee.getFirstName() != null && !employee.getFirstName().isEmpty() &&
                    employee.getLastName() != null && !employee.getLastName().isEmpty() &&
                    employee.getEmail() != null && !employee.getEmail().isEmpty() &&
                    employee.getPhoneNumbers() != null && !employee.getPhoneNumbers().isEmpty() &&
                    employee.getDoj() != null && !employee.getDoj().isEmpty() &&
                employee.getSalary() > 0;
        }

        private double calculateTotalSalary(Employee employee) {
            // Calculate total salary based on DOJ
            LocalDate doj = LocalDate.parse(employee.getDoj());
            LocalDate currentDate = LocalDate.now();
            int monthsWorked = (currentDate.getYear() - doj.getYear()) * 12 + currentDate.getMonthValue() - doj.getMonthValue();
            double totalSalary = employee.getSalary() * monthsWorked;
            return totalSalary;
        }

        private double calculateTax(double totalSalary) {
            // Calculate tax amount based on total salary
            double taxAmount = 0;
            if (totalSalary <= 250000) {
                taxAmount = 0;
            } else if (totalSalary <= 500000) {
                taxAmount = (totalSalary - 250000) * 0.05;
            } else if (totalSalary <= 1000000) {
                taxAmount = 12500 + (totalSalary - 500000) * 0.1;
            } else {
                taxAmount = 62500 + (totalSalary - 1000000) * 0.2;
            }
            return taxAmount;
        }

        private double calculateCess(double totalSalary) {
            // Calculate cess amount based on total salary
            double cessAmount = 0;
            if (totalSalary > 2500000) {
                cessAmount = (totalSalary - 2500000) * 0.02;
            }
            return cessAmount;
        }
    }

    class Employee {
        private String employeeId;
        private String firstName;
        private String lastName;
        private String email;
        private List<String> phoneNumbers;
        private String doj;
        private double salary;


        public Employee(String employeeId, String firstName, String lastName, String email, List<String> phoneNumbers, String doj, double salary) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumbers = phoneNumbers;
            this.doj = doj;
            this.salary = salary;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {

            this.employeeId = employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }

        public void setPhoneNumbers(List<String> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        public String getDoj() {
            return doj;
        }

        public void setDoj(String doj) {
            this.doj = doj;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }
    }

    class TaxDetails {
        private String employeeId;
        private String firstName;
        private String lastName;
        private double yearlySalary;
        private double taxAmount;
        private double cessAmount;

            public TaxDetails(String employeeId, String firstName, String lastName, double yearlySalary, double taxAmount, double cessAmount) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.yearlySalary = yearlySalary;
            this.taxAmount = taxAmount;
            this.cessAmount = cessAmount;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public double getYearlySalary() {
            return yearlySalary;
        }

        public void setYearlySalary(double yearlySalary) {
            this.yearlySalary = yearlySalary;
        }

        public double getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
        }

        public double getCessAmount() {
            return cessAmount;
        }

        public void setCessAmount(double cessAmount) {
            this.cessAmount = cessAmount;
        }
    }
}
