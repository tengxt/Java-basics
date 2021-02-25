package test.review;

public class Pizza {

    private PizzaStatus status;

    public enum PizzaStatus {
        ORDERED,
        READY,
        DELIVERED;
    }

    public boolean isDeliverable() {
        return getStatus() == PizzaStatus.READY;
    }

    public PizzaStatus getStatus() {
        return status;
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    public Integer getDeliveryTimeInDays(PizzaStatus status) {
        switch (status) {
            case ORDERED:
                return 5;
            case READY:
                return 4;
            case DELIVERED:
                return 3;
        }
        return 0;
    }
}
