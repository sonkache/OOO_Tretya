package wsuniversallib;

public class Calculation {

    public static int getQuantityForProduct(int productType, int materialType,
                                            int count, float width, float length) {
        if (count <= 0 || width <= 0 || length <= 0) return -1;

        double productCoef;
        if (productType == 1) {
            productCoef = 1.1;
        } else if (productType == 2) {
            productCoef = 2.5;
        } else if (productType == 3) {
            productCoef = 8.43;
        } else {
            return -1;
        }

        double defect;
        if (materialType == 1) {
            defect = 0.003;
        } else if (materialType == 2) {
            defect = 0.0012;
        } else {
            return -1;
        }

        double area = (double) width * (double) length;
        double required = count * area * productCoef;
        required = required * (1.0 + defect);
        return (int) Math.ceil(required);
    }
}
