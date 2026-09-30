package za.co.swiftparcel;

public class QuoteService {

    public double baseRate(Zone zone) {
        return switch (zone) {
            case LOCAL -> 8.0;
            case REGIONAL -> 14.0;
            case NATIONAL -> 22.0;
        };
    }

    public double calculateQuote(Parcel parcel) {
        return baseRate(parcel.getZone()) * parcel.getWeightKg() + calculateSurcharge(parcel);
    }

    public double calculateSurcharge(Parcel parcel) {
        double surcharge = 0;
        if (parcel != null) {
            if (parcel.getWeightKg() > 0) {
                if (parcel.isFragile()) {
                    if (parcel.getDeclaredValue() > 5000) {
                        surcharge = 150;
                    } else {
                        surcharge = 60;
                    }
                } else {
                    if (parcel.getDeclaredValue() > 5000) {
                        surcharge = 90;
                    } else {
                        surcharge = 0;
                    }
                }
                if (parcel.getWeightKg() > 20) {
                    surcharge = surcharge + 40;
                }
            } else {
                throw new IllegalArgumentException("Weight must be positive");
            }
        } else {
            throw new IllegalArgumentException("Parcel is required");
        }
        return surcharge;
    }

    public double calculateParcelFragileSurchange(Parcel parcel, double surchange){
         if (parcel.getDeclaredValue() > 5000) {
             return surcharge = 150;
        } else {
           return surcharge = 60;
        }
    }

    public boolean canAccept(Parcel parcel, Depot depot) {
        if (depot.isOpen() == true && parcelWeightCheckAgainstDepotSpaces(parcel, depot) && (depotFragileStatus(parcel, depot) && depotAndParcelZone(parcel, depot))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean depotFragileStatus(Parcel parcel, Depot depot){
        return (parcel.isFragile() == false || depot.acceptsFragile() == true);
    }

    public boolean depotAndParcelZone(Parcel parcel, Depot depot){
        return (depot.getZone() == null || depot.getZone() == parcel.getZone());
    }

    public boolean parcelWeightCheckAgainstDepotSpaces(Parcel parcel, Depot depot){
        return depot.getSpacesRemaining() > 0 && parcel.getWeightKg() <= depot.getMaxWeightKg();
    }


}
