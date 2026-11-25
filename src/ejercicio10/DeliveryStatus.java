package ejercicio10;

/**
 * Resultado de la notificación a una entidad.
 */
public enum DeliveryStatus {
    SUCCESS("Exitosa"),
    FAILED("Con incidencia");

    private final String label;

    DeliveryStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
