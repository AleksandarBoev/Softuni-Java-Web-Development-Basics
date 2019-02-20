package app.web.mbeans;

import app.services.EmployeeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named("employee_statistics")
@RequestScoped
public class EmployeesStatisticsBean {
    private BigDecimal totalMoneyNeeded;
    private BigDecimal averageMoneyNeeded;

    public EmployeesStatisticsBean() {
    }

    @Inject
    public EmployeesStatisticsBean(EmployeeService employeeService) {
        this();
        this.totalMoneyNeeded = employeeService.getSalariesSum();
        this.averageMoneyNeeded = employeeService.getSalariesAvg();
    }

    public BigDecimal getTotalMoneyNeeded() {
        return this.totalMoneyNeeded;
    }

    public void setTotalMoneyNeeded(BigDecimal totalMoneyNeeded) {
        this.totalMoneyNeeded = totalMoneyNeeded;
    }

    public BigDecimal getAverageMoneyNeeded() {
        return this.averageMoneyNeeded;
    }

    public void setAverageMoneyNeeded(BigDecimal averageMoneyNeeded) {
        this.averageMoneyNeeded = averageMoneyNeeded;
    }
}
