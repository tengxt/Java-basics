package test.lambda1;

import java.util.Objects;

public class Employee {
    private int age;
    private String name;
    private double salary;
    private Status status;

    public Employee() {
    }

    public Employee(int age, String name, double salary) {
        this.age = age;
        this.name = name;
        this.salary = salary;
    }

    public Employee(int age, String name, double salary, Status status) {
        this.age = age;
        this.name = name;
        this.salary = salary;
        this.status = status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.salary, salary) == 0 &&
                Objects.equals(age, employee.age) &&
                Objects.equals(name, employee.name) &&
                status == employee.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, salary, status);
    }

    public enum Status{
        FREE,
        BUSY,
        VOCATION;
    }
}
