package ejercicio10;

/**
 * Registro inmutable del resultado de notificar a una entidad.
 */
public record DeliveryRecord(String entityName, DeliveryStatus status, String detail) {
}
