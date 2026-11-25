package ejercicio10;

import java.util.List;

/**
 * Registro inmutable de auditoría de un cambio de riesgo: qué entidades fueron
 * notificadas con éxito y cuáles reportaron incidencias.
 */
public record AuditReport(RiskChange change, List<DeliveryRecord> deliveries) {

    public AuditReport {
        deliveries = List.copyOf(deliveries);
    }

    public long successCount() {
        return deliveries.stream().filter(d -> d.status() == DeliveryStatus.SUCCESS).count();
    }

    public long failureCount() {
        return deliveries.stream().filter(d -> d.status() == DeliveryStatus.FAILED).count();
    }
}
