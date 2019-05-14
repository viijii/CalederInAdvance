package com.example.calenderinadvance;

public class CalenderResponse {
    String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;





    CalenderResponse.data[] data;

    public CalenderResponse.data[] getData() {
        return data;
    }

    public void setData(CalenderResponse.data[] data) {
        this.data = data;
    }
    public class data {
        //drivers_list
        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        String driver_name;
        //driver_rides
        String source;
        String destination;
        String subscribedDays;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getSubscribedDays() {
            return subscribedDays;
        }

        public void setSubscribedDays(String subscribedDays) {
            this.subscribedDays = subscribedDays;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        String totalAmount;
        String startDate;

    }
}


