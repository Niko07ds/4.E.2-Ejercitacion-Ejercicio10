package ejercicio10;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Cuenta corporativa (sujeto observable de grado empresarial).
 * - La lista de observadores es segura para varios hilos y conserva el orden de registro.
 * - El cambio de riesgo es sincronizado: las notificaciones son secuenciales y nunca se mezclan.
 * - Cada observador se ejecuta aislado, incluida la obtención de su nombre, para evitar fallos en cascada.
 * - Cada cambio deja un informe inmutable en el historial de auditoría.
 */
public class CorporateAccount {

    private final String accountId;
    private RiskLevel riskLevel;
    private final CopyOnWriteArrayList<RegulatoryObserver> observers = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<AuditReport> auditTrail = new CopyOnWriteArrayList<>();

    public CorporateAccount(String accountId, RiskLevel initialLevel) {
        this.accountId = accountId;
        this.riskLevel = initialLevel;
    }

    public void addObserver(RegulatoryObserver observer) {
        if (observer != null) {
            observers.addIfAbsent(observer);
        }
    }

    public void removeObserver(RegulatoryObserver observer) {
        observers.remove(observer);
    }

    /**
     * Cambia el nivel de riesgo y notifica en orden a todos los observadores obligatorios.
     *
     * @return informe inmutable con el resultado de cada notificación
     */
    public synchronized AuditReport changeRiskLevel(RiskLevel newLevel, String reason) {
        RiskChange change = new RiskChange(accountId, riskLevel, newLevel, reason, LocalDateTime.now());
        riskLevel = newLevel;

        List<DeliveryRecord> deliveries = new ArrayList<>();
        for (RegulatoryObserver observer : observers) {
            // Si getName() también falla, se usa el nombre de la clase como identificador
            String entityName = observer.getClass().getSimpleName();
            try {
                entityName = observer.getName();
                observer.onRiskChanged(change);
                deliveries.add(new DeliveryRecord(entityName, DeliveryStatus.SUCCESS, "Procesado correctamente"));
            } catch (Exception e) {
                // Aislamiento de errores: se registra la incidencia y se continúa con la siguiente entidad
                deliveries.add(new DeliveryRecord(entityName, DeliveryStatus.FAILED,
                        e.getClass().getSimpleName() + ": " + e.getMessage()));
            }
        }

        AuditReport report = new AuditReport(change, deliveries);
        auditTrail.add(report);
        return report;
    }

    public List<AuditReport> getAuditTrail() {
        return List.copyOf(auditTrail);
    }

    public String getAccountId() {
        return accountId;
    }
}
