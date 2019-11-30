package by.nc.tarazenko.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GuestDTO {
    private int id;
    private String phoneNumber;
    private List<Integer> roomNumbers;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String passportNumber;
    private List<String> attendanceName;
    private List<Double> attendanceCost;
    private double bill;


   /* public GuestDTO(){
    }

    public GuestDTO(String phoneNumber, List<Integer> roomNumbers, String firstName,
                    String secondName, String thirdName, String passportNumber, List<String> attendanceName,
                    List<Double> attendanceCost, double bill) {
        this.phoneNumber = phoneNumber;
        this.roomNumbers = roomNumbers;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.passportNumber = passportNumber;
        this.attendanceName = attendanceName;
        this.attendanceCost = attendanceCost;
        this.bill = bill;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Integer> getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(List<Integer> roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public List<String> getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(List<String> attendanceName) {
        this.attendanceName = attendanceName;
    }

    public List<Double> getAttendanceCost() {
        return attendanceCost;
    }

    public void setAttendanceCost(List<Double> attendanceCost) {
        this.attendanceCost = attendanceCost;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestDTO guestDTO = (GuestDTO) o;
        return Double.compare(guestDTO.bill, bill) == 0 &&
                Objects.equals(phoneNumber, guestDTO.phoneNumber) &&
                Objects.equals(roomNumbers, guestDTO.roomNumbers) &&
                Objects.equals(firstName, guestDTO.firstName) &&
                Objects.equals(secondName, guestDTO.secondName) &&
                Objects.equals(thirdName, guestDTO.thirdName) &&
                Objects.equals(passportNumber, guestDTO.passportNumber) &&
                Objects.equals(attendanceName, guestDTO.attendanceName) &&
                Objects.equals(attendanceCost, guestDTO.attendanceCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, roomNumbers, firstName, secondName, thirdName, passportNumber, attendanceName, attendanceCost, bill);
    }

    @Override
    public String toString() {
        return "GuestDTO{" +
                "phoneNumber=" + phoneNumber +
                ", roomNumbers=" + roomNumbers +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", attendanceName=" + attendanceName +
                ", attendanceCost=" + attendanceCost +
                ", bill=" + bill +
                '}';
    }*/
}
